#!/usr/bin/env perl
use strict;
use warnings;
use Getopt::Long;
use Term::ReadKey;
use File::Slurp;
use Data::Dumper;
use File::Basename;
use Bio::KBase::WorkspaceFileHandler::Client;
use Bio::KBase::Tree::Util qw(getTreeURL);

my $DESCRIPTION =
"
NAME
      ws-upload-file -- load local files to the workspace

SYNOPSIS
      ws-upload-file [OPTIONS] [FILE_1] [FILE_2] ...

DESCRIPTION
      Load local files of a specific type.  Files are saved to Workspace objects with the same
      name as the filename. If an object by that name already exists in the Workspace specified,
      this method will overwrite it.  Upon sucessful upload, the WS ID of the saved objects is
      printed out.
      
      -l, --list-types   list the file types available for upload
      
      -t, --type         the file type to upload; required
      -w, --ws           the workspace name; required
      -r, --uploader     the loader that is used to process the file; optional; will default to the
                         default uploader for the specified file type
      
      -b, --batch        batch the upload by reading all files and sending a single upload request
                         to the workspace; recommended if you have many small files of the same
                         type to upload
      
      -u, --user         the user name; required
      -p, --password     user password; if not given, you will be prompted to provide it
      
      -e, --url          the url of the service; optional
      
      -h, --help         display this help message, ignore all arguments

AUTHOR
     Michael Sneddon (LBL, mwsneddon\@lbl.gov)
";
      
# first parse options; only one here is help
my $filetype;
my $uploader;
my $workspace;
my $user;
my $password;
my $url = "http://localhost:7110";

my $listtypes;
my $batch;
my $help;

my $opt = GetOptions (
        "list-types|l" => \$listtypes,
        "type|t=s" => \$filetype,
        "uploader|r=s" => \$uploader,
        "workspace|w=s" => \$workspace,
        "user|u=s" => \$user,
        "password|p=s" => \$password,
        "url|e=s" => \$url,
        "batch|b" => \$batch,
        "help|h" => \$help,
        );

# print help if requested
if(defined($help)) {
     print $DESCRIPTION;
     exit 0;
}

# list available types if requested
if (defined($listtypes)) {
     my $wfh = Bio::KBase::WorkspaceFileHandler::Client->new($url);
     my $filetypes = $wfh->getUploadableFileTypes();
     foreach my $ft (@$filetypes) {
          print $ft->{id} ."\t".$ft->{name}."\tuploaders:[";
          my $isFirst = 1;
          foreach my $d (@{$ft->{uploaders}}) {
               if ($isFirst) { $isFirst=0; }
               else { print ", "; }
               print $d;
               if (defined($ft->{default_uploader})) {
                    if ($d eq $ft->{default_uploader}) {
                         print "(default)";
                    }
               }
          }
          print "]\n";
     }
     exit 0;
}


#check that a file was provided
my $n_args = $#ARGV+1;
if($n_args==0) {
     print "no file specified.  Run with --help for usage.\n";
     exit 1;
}     
elsif($n_args>=1) {
     # make sure all the other necessary arguments are defined
     if (!defined($user)) { print  STDERR "Missing required parameter --user.  Run with --help for usage.\n"; exit 1; }
     if (!defined($filetype)) { print  STDERR "Missing required parameter --type.  Run with --help for usage.\n"; exit 1; }
     if (!defined($workspace)) { print  STDERR "Missing required parameter --workspace.  Run with --help for usage.\n"; exit 1; }
     
     # make sure we have a password
     if (!defined($password)) { $password = get_pass(); }
     
     # create our WS connection
     my $wfh = Bio::KBase::WorkspaceFileHandler::Client->new($url,user_id=>$user,password=>$password);

     # go through every file in the list
     my @files = @ARGV;
     foreach my $filename (@files) {
          # make sure it exists
          if (-e $filename) {
               my $content = read_file( $filename );
               if (!defined($batch)) {
                    my $upload_params = {};
                    $upload_params->{name} = basename($filename);
                    $upload_params->{content} = $content;
                    $upload_params->{type} = $filetype;
                    $upload_params->{ws_name} = $workspace;
                    $upload_params->{metadata} = {};
                    if (defined($uploader)) {
                         $upload_params->{uploader} = $uploader;
                    }
                    my $wsid = $wfh->upload($upload_params);
                    print $filename."\t".$wsid."\n";
               } else {
                    print  STDERR "batch upload not yet supported.\n";
                    exit 1;
               }
          }
          else {
               print  STDERR "File '$filename' does not exist.\n";
               exit 1;
          }
     }
     exit 0;
}
print  STDERR "unknown error.  Run with --help for usage.\n";
exit 1;


# copied from kbase-login...
sub get_pass {
    my $key  = 0;
    my $pass = ""; 
    print "Password: ";
    ReadMode(4);
    while ( ord($key = ReadKey(0)) != 10 ) {
        # While Enter has not been pressed
        if (ord($key) == 127 || ord($key) == 8) {
            chop $pass;
            print "\b \b";
        } elsif (ord($key) < 32) {
            # Do nothing with control chars
        } else {
            $pass .= $key;
            print "*";
        }
    }
    ReadMode(0);
    print "\n";
    return $pass;
}
