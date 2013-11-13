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
      
      -o, --outdir       if given, files are saved to the specified output directory. 
      -t, --type         the file type to download the object as; required
      -w, --ws           the workspace name; required
      -r, --downloader   the loader that is used to process the object; optional; will default to the
                         default uploader for the specified workspace object
                         
      -f, --force        if set, this method overwrites any existing files of the same name
      -n, --long-name    if set, file names include the workspace id of the object they were downloaded from
      
      -u, --user         the user name; required
      -p, --password     user password; if not given, you will be prompted to provide it
      
      -e, --url          the url of the service; optional
      
      -h, --help         display this help message, ignore all arguments

AUTHOR
     Michael Sneddon (LBL, mwsneddon\@lbl.gov)
";
      
# first parse options; only one here is help
my $filetype;
my $downloader;
my $outdir;
my $force;
my $longname;

my $user;
my $password;
my $url = "http://localhost:7110";

my $listtypes;
my $help;

my $opt = GetOptions (
        "list-types|l=s" => \$listtypes,
        "type|t=s" => \$filetype,
        "downloader|r=s" => \$downloader,
        "outdir|o=s" => \$outdir,
        "force|f" => \$force,
        "longname|n" => \$longname,
        "user|u=s" => \$user,
        "password|p=s" => \$password,
        "url|e=s" => \$url,
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
     if (!defined($user)) { print STDERR "Missing required parameter --user.  Run with --help for usage.\n"; exit 1; }
     if (!defined($filetype)) { print STDERR "Missing required parameter --type.  Run with --help for usage.\n"; exit 1; }
     if (defined($outdir)) {
          if(!-f $outdir) {
               print STDERR "Output dir specified is not a directory.  Run with --help for usage.\n"; exit 1;
          }
     }

     
     
     # make sure we have a password
     if (!defined($password)) { $password = get_pass(); }
     
     # create our WS connection
     my $wfh = Bio::KBase::WorkspaceFileHandler::Client->new($url,user_id=>$user,password=>$password);

     # go through every file in the list
     my @objRefs = @ARGV;
     foreach my $objRef (@objRefs) {
          
          # actually fetch the data
          my $download_params = {};
          $download_params->{"type"} = $filetype;
          $download_params->{"ref"} = $objRef;
          if (defined($downloader)) {
               $download_params->{"downloader"} = $downloader;
          }
          my $fileData = $wfh->download($download_params);
          
          #determine where we should download this file to
          my $filename = $fileData->{"filename"};
          if (defined($outdir)) { $filename = $outdir ."/". $filename; }
          if (defined($longname)) { $filename .= "-".$fileData->{"src_obj"}; }
          if (!defined($force)) {
               if (-e $filename) {
                    print STDERR "Download of file $filename would overwrite an existing file.  Rerun with -f to overwrite.\n"; exit 1;
               }
          }
          
          # actually save the data
          open(my $fh, '>', $filename);
          print $fh $fileData->{"content"};
          close $fh;
          
          my $filesize = -s $filename;
          print "downloaded:\t".$objRef."\tto\t".$filename."\t[$filesize bytes]\n";
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
