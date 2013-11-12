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
      ws-download-file -- download workspace objects to local files

SYNOPSIS
      ws-upload-file [OPTIONS] [WSID_1] [WSID_2] ...

DESCRIPTION
      Download workspace objects to local files given a set of Workspace IDs.
      
      -l, --list-types   list the file types available for download
      
      
      -t, --type         the file type to download the object as; required
      -w, --ws           the workspace name; required
      -r, --downloader   the loader that is used to process the object; optional; will default to the
                         default uploader for the specified workspace object
      
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


print "ws-download-file not implemented yet.\n";
exit 0;

my $opt = GetOptions (
        "list-types|l=s" => \$listtypes,
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
     print "listing types not implemented.\n";
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
     if (!defined($user)) { print "Missing required parameter --user.  Run with --help for usage.\n"; exit 1; }
     if (!defined($filetype)) { print "Missing required parameter --type.  Run with --help for usage.\n"; exit 1; }
     if (!defined($workspace)) { print "Missing required parameter --workspace.  Run with --help for usage.\n"; exit 1; }
     
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
                    print "batch upload not yet supported.\n";
                    exit 1;
               }
          }
          else {
               print "File '$filename' does not exist.\n";
               exit 1;
          }
     }
     exit 0;
}
print "unknown error.  Run with --help for usage.\n";
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
