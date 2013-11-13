package Bio::KBase::WorkspaceFileHandler::Client;

use JSON::RPC::Client;
use strict;
use Data::Dumper;
use URI;
use Bio::KBase::Exceptions;
use Bio::KBase::AuthToken;

# Client version should match Impl version
# This is a Semantic Version number,
# http://semver.org
our $VERSION = "0.1.0";

=head1 NAME

Bio::KBase::WorkspaceFileHandler::Client

=head1 DESCRIPTION


Service for uploading files (of supported types) to the Workspace as typed objects, and downloading
those typed objects as a file.


=cut

sub new
{
    my($class, $url, @args) = @_;
    
    if (!defined($url))
    {
	$url = 'http://kbase.us/services/workspace_file_service/';
    }

    my $self = {
	client => Bio::KBase::WorkspaceFileHandler::Client::RpcClient->new,
	url => $url,
    };

    #
    # This module requires authentication.
    #
    # We create an auth token, passing through the arguments that we were (hopefully) given.

    {
	my $token = Bio::KBase::AuthToken->new(@args);
	
	if (!$token->error_message)
	{
	    $self->{token} = $token->token;
	    $self->{client}->{token} = $token->token;
	}
    }

    my $ua = $self->{client}->ua;	 
    my $timeout = $ENV{CDMI_TIMEOUT} || (30 * 60);	 
    $ua->timeout($timeout);
    bless $self, $class;
    #    $self->_validate_version();
    return $self;
}




=head2 getAllFileTypes

  $return = $obj->getAllFileTypes()

=over 4

=item Parameter and return types

=begin html

<pre>
$return is a reference to a list where each element is a WorkspaceFileHandler.FileType
FileType is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filetype_name
	description has a value which is a string
	url has a value which is a string
	valid_extensions has a value which is a reference to a list where each element is a string
	default_uploader has a value which is a WorkspaceFileHandler.loader_id
	uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
	default_downloader has a value which is a WorkspaceFileHandler.loader_id
	downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
filetype_id is a string
filetype_name is a string
loader_id is a string

</pre>

=end html

=begin text

$return is a reference to a list where each element is a WorkspaceFileHandler.FileType
FileType is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filetype_name
	description has a value which is a string
	url has a value which is a string
	valid_extensions has a value which is a reference to a list where each element is a string
	default_uploader has a value which is a WorkspaceFileHandler.loader_id
	uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
	default_downloader has a value which is a WorkspaceFileHandler.loader_id
	downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
filetype_id is a string
filetype_name is a string
loader_id is a string


=end text

=item Description

Fetch a list of all file types that are known, note that not all will support both upload and
download.

=back

=cut

sub getAllFileTypes
{
    my($self, @args) = @_;

# Authentication: none

    if ((my $n = @args) != 0)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getAllFileTypes (received $n, expecting 0)");
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.getAllFileTypes",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'getAllFileTypes',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getAllFileTypes",
					    status_line => $self->{client}->status_line,
					    method_name => 'getAllFileTypes',
				       );
    }
}



=head2 getUploadableFileTypes

  $return = $obj->getUploadableFileTypes()

=over 4

=item Parameter and return types

=begin html

<pre>
$return is a reference to a list where each element is a WorkspaceFileHandler.FileType
FileType is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filetype_name
	description has a value which is a string
	url has a value which is a string
	valid_extensions has a value which is a reference to a list where each element is a string
	default_uploader has a value which is a WorkspaceFileHandler.loader_id
	uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
	default_downloader has a value which is a WorkspaceFileHandler.loader_id
	downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
filetype_id is a string
filetype_name is a string
loader_id is a string

</pre>

=end html

=begin text

$return is a reference to a list where each element is a WorkspaceFileHandler.FileType
FileType is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filetype_name
	description has a value which is a string
	url has a value which is a string
	valid_extensions has a value which is a reference to a list where each element is a string
	default_uploader has a value which is a WorkspaceFileHandler.loader_id
	uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
	default_downloader has a value which is a WorkspaceFileHandler.loader_id
	downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
filetype_id is a string
filetype_name is a string
loader_id is a string


=end text

=item Description

Fetch a list of supported file types that can be uploaded as a Workspace object.

=back

=cut

sub getUploadableFileTypes
{
    my($self, @args) = @_;

# Authentication: none

    if ((my $n = @args) != 0)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getUploadableFileTypes (received $n, expecting 0)");
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.getUploadableFileTypes",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'getUploadableFileTypes',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getUploadableFileTypes",
					    status_line => $self->{client}->status_line,
					    method_name => 'getUploadableFileTypes',
				       );
    }
}



=head2 getDownloadableFileTypes

  $return = $obj->getDownloadableFileTypes()

=over 4

=item Parameter and return types

=begin html

<pre>
$return is a reference to a list where each element is a WorkspaceFileHandler.FileType
FileType is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filetype_name
	description has a value which is a string
	url has a value which is a string
	valid_extensions has a value which is a reference to a list where each element is a string
	default_uploader has a value which is a WorkspaceFileHandler.loader_id
	uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
	default_downloader has a value which is a WorkspaceFileHandler.loader_id
	downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
filetype_id is a string
filetype_name is a string
loader_id is a string

</pre>

=end html

=begin text

$return is a reference to a list where each element is a WorkspaceFileHandler.FileType
FileType is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filetype_name
	description has a value which is a string
	url has a value which is a string
	valid_extensions has a value which is a reference to a list where each element is a string
	default_uploader has a value which is a WorkspaceFileHandler.loader_id
	uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
	default_downloader has a value which is a WorkspaceFileHandler.loader_id
	downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
filetype_id is a string
filetype_name is a string
loader_id is a string


=end text

=item Description

Fetch a list of supported file types that can be downloaded as a file.

=back

=cut

sub getDownloadableFileTypes
{
    my($self, @args) = @_;

# Authentication: none

    if ((my $n = @args) != 0)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getDownloadableFileTypes (received $n, expecting 0)");
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.getDownloadableFileTypes",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'getDownloadableFileTypes',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getDownloadableFileTypes",
					    status_line => $self->{client}->status_line,
					    method_name => 'getDownloadableFileTypes',
				       );
    }
}



=head2 getFileType

  $return = $obj->getFileType($ids)

=over 4

=item Parameter and return types

=begin html

<pre>
$ids is a reference to a list where each element is a WorkspaceFileHandler.filetype_id
$return is a reference to a list where each element is a WorkspaceFileHandler.FileType
filetype_id is a string
FileType is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filetype_name
	description has a value which is a string
	url has a value which is a string
	valid_extensions has a value which is a reference to a list where each element is a string
	default_uploader has a value which is a WorkspaceFileHandler.loader_id
	uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
	default_downloader has a value which is a WorkspaceFileHandler.loader_id
	downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
filetype_name is a string
loader_id is a string

</pre>

=end html

=begin text

$ids is a reference to a list where each element is a WorkspaceFileHandler.filetype_id
$return is a reference to a list where each element is a WorkspaceFileHandler.FileType
filetype_id is a string
FileType is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filetype_name
	description has a value which is a string
	url has a value which is a string
	valid_extensions has a value which is a reference to a list where each element is a string
	default_uploader has a value which is a WorkspaceFileHandler.loader_id
	uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
	default_downloader has a value which is a WorkspaceFileHandler.loader_id
	downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
filetype_name is a string
loader_id is a string


=end text

=item Description

Given a list of filetype_ids, return a list of the FileType information if the type exists.

=back

=cut

sub getFileType
{
    my($self, @args) = @_;

# Authentication: none

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getFileType (received $n, expecting 1)");
    }
    {
	my($ids) = @args;

	my @_bad_arguments;
        (ref($ids) eq 'ARRAY') or push(@_bad_arguments, "Invalid type for argument 1 \"ids\" (value was \"$ids\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getFileType:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getFileType');
	}
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.getFileType",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'getFileType',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getFileType",
					    status_line => $self->{client}->status_line,
					    method_name => 'getFileType',
				       );
    }
}



=head2 upload

  $return = $obj->upload($parameters)

=over 4

=item Parameter and return types

=begin html

<pre>
$parameters is a WorkspaceFileHandler.UploadParams
$return is a WorkspaceFileHandler.ws_obj_reference
UploadParams is a reference to a hash where the following keys are defined:
	type has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filename
	content has a value which is a WorkspaceFileHandler.filecontent
	ws_name has a value which is a WorkspaceFileHandler.ws_name
	uploader has a value which is a WorkspaceFileHandler.loader_id
	metadata has a value which is a reference to a hash where the key is a string and the value is a string
filetype_id is a string
filename is a string
filecontent is a string
ws_name is a string
loader_id is a string
ws_obj_reference is a string

</pre>

=end html

=begin text

$parameters is a WorkspaceFileHandler.UploadParams
$return is a WorkspaceFileHandler.ws_obj_reference
UploadParams is a reference to a hash where the following keys are defined:
	type has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filename
	content has a value which is a WorkspaceFileHandler.filecontent
	ws_name has a value which is a WorkspaceFileHandler.ws_name
	uploader has a value which is a WorkspaceFileHandler.loader_id
	metadata has a value which is a reference to a hash where the key is a string and the value is a string
filetype_id is a string
filename is a string
filecontent is a string
ws_name is a string
loader_id is a string
ws_obj_reference is a string


=end text

=item Description



=back

=cut

sub upload
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function upload (received $n, expecting 1)");
    }
    {
	my($parameters) = @args;

	my @_bad_arguments;
        (ref($parameters) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"parameters\" (value was \"$parameters\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to upload:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'upload');
	}
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.upload",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'upload',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method upload",
					    status_line => $self->{client}->status_line,
					    method_name => 'upload',
				       );
    }
}



=head2 upload_batch

  $return = $obj->upload_batch($paramameters_list)

=over 4

=item Parameter and return types

=begin html

<pre>
$paramameters_list is a reference to a list where each element is a WorkspaceFileHandler.UploadParams
$return is a reference to a list where each element is a WorkspaceFileHandler.ws_obj_reference
UploadParams is a reference to a hash where the following keys are defined:
	type has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filename
	content has a value which is a WorkspaceFileHandler.filecontent
	ws_name has a value which is a WorkspaceFileHandler.ws_name
	uploader has a value which is a WorkspaceFileHandler.loader_id
	metadata has a value which is a reference to a hash where the key is a string and the value is a string
filetype_id is a string
filename is a string
filecontent is a string
ws_name is a string
loader_id is a string
ws_obj_reference is a string

</pre>

=end html

=begin text

$paramameters_list is a reference to a list where each element is a WorkspaceFileHandler.UploadParams
$return is a reference to a list where each element is a WorkspaceFileHandler.ws_obj_reference
UploadParams is a reference to a hash where the following keys are defined:
	type has a value which is a WorkspaceFileHandler.filetype_id
	name has a value which is a WorkspaceFileHandler.filename
	content has a value which is a WorkspaceFileHandler.filecontent
	ws_name has a value which is a WorkspaceFileHandler.ws_name
	uploader has a value which is a WorkspaceFileHandler.loader_id
	metadata has a value which is a reference to a hash where the key is a string and the value is a string
filetype_id is a string
filename is a string
filecontent is a string
ws_name is a string
loader_id is a string
ws_obj_reference is a string


=end text

=item Description



=back

=cut

sub upload_batch
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function upload_batch (received $n, expecting 1)");
    }
    {
	my($paramameters_list) = @args;

	my @_bad_arguments;
        (ref($paramameters_list) eq 'ARRAY') or push(@_bad_arguments, "Invalid type for argument 1 \"paramameters_list\" (value was \"$paramameters_list\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to upload_batch:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'upload_batch');
	}
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.upload_batch",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'upload_batch',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method upload_batch",
					    status_line => $self->{client}->status_line,
					    method_name => 'upload_batch',
				       );
    }
}



=head2 download

  $return = $obj->download($parameters)

=over 4

=item Parameter and return types

=begin html

<pre>
$parameters is a WorkspaceFileHandler.DownloadParams
$return is a WorkspaceFileHandler.DownloadedFile
DownloadParams is a reference to a hash where the following keys are defined:
	type has a value which is a WorkspaceFileHandler.filetype_id
	ref has a value which is a WorkspaceFileHandler.ws_obj_reference
	downloader has a value which is a WorkspaceFileHandler.loader_id
filetype_id is a string
ws_obj_reference is a string
loader_id is a string
DownloadedFile is a reference to a hash where the following keys are defined:
	src_obj has a value which is a WorkspaceFileHandler.ws_obj_reference
	filename has a value which is a string
	content has a value which is a string

</pre>

=end html

=begin text

$parameters is a WorkspaceFileHandler.DownloadParams
$return is a WorkspaceFileHandler.DownloadedFile
DownloadParams is a reference to a hash where the following keys are defined:
	type has a value which is a WorkspaceFileHandler.filetype_id
	ref has a value which is a WorkspaceFileHandler.ws_obj_reference
	downloader has a value which is a WorkspaceFileHandler.loader_id
filetype_id is a string
ws_obj_reference is a string
loader_id is a string
DownloadedFile is a reference to a hash where the following keys are defined:
	src_obj has a value which is a WorkspaceFileHandler.ws_obj_reference
	filename has a value which is a string
	content has a value which is a string


=end text

=item Description



=back

=cut

sub download
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function download (received $n, expecting 1)");
    }
    {
	my($parameters) = @args;

	my @_bad_arguments;
        (ref($parameters) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"parameters\" (value was \"$parameters\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to download:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'download');
	}
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.download",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'download',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method download",
					    status_line => $self->{client}->status_line,
					    method_name => 'download',
				       );
    }
}



=head2 download_batch

  $return = $obj->download_batch($parameters)

=over 4

=item Parameter and return types

=begin html

<pre>
$parameters is a reference to a list where each element is a WorkspaceFileHandler.DownloadParams
$return is a reference to a hash where the key is a WorkspaceFileHandler.ws_obj_reference and the value is a WorkspaceFileHandler.DownloadedFile
DownloadParams is a reference to a hash where the following keys are defined:
	type has a value which is a WorkspaceFileHandler.filetype_id
	ref has a value which is a WorkspaceFileHandler.ws_obj_reference
	downloader has a value which is a WorkspaceFileHandler.loader_id
filetype_id is a string
ws_obj_reference is a string
loader_id is a string
DownloadedFile is a reference to a hash where the following keys are defined:
	src_obj has a value which is a WorkspaceFileHandler.ws_obj_reference
	filename has a value which is a string
	content has a value which is a string

</pre>

=end html

=begin text

$parameters is a reference to a list where each element is a WorkspaceFileHandler.DownloadParams
$return is a reference to a hash where the key is a WorkspaceFileHandler.ws_obj_reference and the value is a WorkspaceFileHandler.DownloadedFile
DownloadParams is a reference to a hash where the following keys are defined:
	type has a value which is a WorkspaceFileHandler.filetype_id
	ref has a value which is a WorkspaceFileHandler.ws_obj_reference
	downloader has a value which is a WorkspaceFileHandler.loader_id
filetype_id is a string
ws_obj_reference is a string
loader_id is a string
DownloadedFile is a reference to a hash where the following keys are defined:
	src_obj has a value which is a WorkspaceFileHandler.ws_obj_reference
	filename has a value which is a string
	content has a value which is a string


=end text

=item Description



=back

=cut

sub download_batch
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function download_batch (received $n, expecting 1)");
    }
    {
	my($parameters) = @args;

	my @_bad_arguments;
        (ref($parameters) eq 'ARRAY') or push(@_bad_arguments, "Invalid type for argument 1 \"parameters\" (value was \"$parameters\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to download_batch:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'download_batch');
	}
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.download_batch",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'download_batch',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method download_batch",
					    status_line => $self->{client}->status_line,
					    method_name => 'download_batch',
				       );
    }
}



=head2 getDownloader

  $return = $obj->getDownloader($ids)

=over 4

=item Parameter and return types

=begin html

<pre>
$ids is a reference to a list where each element is a WorkspaceFileHandler.loader_id
$return is a reference to a list where each element is a WorkspaceFileHandler.Downloader
loader_id is a string
Downloader is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.loader_id
	name has a value which is a WorkspaceFileHandler.loader_name
	description has a value which is a string
	input_wstype has a value which is a WorkspaceFileHandler.ws_typedefname
	output_filetype has a value which is a WorkspaceFileHandler.filetype_id
loader_name is a string
ws_typedefname is a string
filetype_id is a string

</pre>

=end html

=begin text

$ids is a reference to a list where each element is a WorkspaceFileHandler.loader_id
$return is a reference to a list where each element is a WorkspaceFileHandler.Downloader
loader_id is a string
Downloader is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.loader_id
	name has a value which is a WorkspaceFileHandler.loader_name
	description has a value which is a string
	input_wstype has a value which is a WorkspaceFileHandler.ws_typedefname
	output_filetype has a value which is a WorkspaceFileHandler.filetype_id
loader_name is a string
ws_typedefname is a string
filetype_id is a string


=end text

=item Description



=back

=cut

sub getDownloader
{
    my($self, @args) = @_;

# Authentication: none

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getDownloader (received $n, expecting 1)");
    }
    {
	my($ids) = @args;

	my @_bad_arguments;
        (ref($ids) eq 'ARRAY') or push(@_bad_arguments, "Invalid type for argument 1 \"ids\" (value was \"$ids\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getDownloader:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getDownloader');
	}
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.getDownloader",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'getDownloader',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getDownloader",
					    status_line => $self->{client}->status_line,
					    method_name => 'getDownloader',
				       );
    }
}



=head2 getUploader

  $return = $obj->getUploader($ids)

=over 4

=item Parameter and return types

=begin html

<pre>
$ids is a reference to a list where each element is a WorkspaceFileHandler.loader_id
$return is a reference to a list where each element is a WorkspaceFileHandler.Uploader
loader_id is a string
Uploader is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.loader_id
	name has a value which is a WorkspaceFileHandler.loader_name
	description has a value which is a string
	input_filetype has a value which is a WorkspaceFileHandler.filetype_id
	output_wstype has a value which is a WorkspaceFileHandler.ws_typedefname
loader_name is a string
filetype_id is a string
ws_typedefname is a string

</pre>

=end html

=begin text

$ids is a reference to a list where each element is a WorkspaceFileHandler.loader_id
$return is a reference to a list where each element is a WorkspaceFileHandler.Uploader
loader_id is a string
Uploader is a reference to a hash where the following keys are defined:
	id has a value which is a WorkspaceFileHandler.loader_id
	name has a value which is a WorkspaceFileHandler.loader_name
	description has a value which is a string
	input_filetype has a value which is a WorkspaceFileHandler.filetype_id
	output_wstype has a value which is a WorkspaceFileHandler.ws_typedefname
loader_name is a string
filetype_id is a string
ws_typedefname is a string


=end text

=item Description



=back

=cut

sub getUploader
{
    my($self, @args) = @_;

# Authentication: none

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getUploader (received $n, expecting 1)");
    }
    {
	my($ids) = @args;

	my @_bad_arguments;
        (ref($ids) eq 'ARRAY') or push(@_bad_arguments, "Invalid type for argument 1 \"ids\" (value was \"$ids\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getUploader:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getUploader');
	}
    }

    my $result = $self->{client}->call($self->{url}, {
	method => "WorkspaceFileHandler.getUploader",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{code},
					       method_name => 'getUploader',
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getUploader",
					    status_line => $self->{client}->status_line,
					    method_name => 'getUploader',
				       );
    }
}



sub version {
    my ($self) = @_;
    my $result = $self->{client}->call($self->{url}, {
        method => "WorkspaceFileHandler.version",
        params => [],
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(
                error => $result->error_message,
                code => $result->content->{code},
                method_name => 'getUploader',
            );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(
            error => "Error invoking method getUploader",
            status_line => $self->{client}->status_line,
            method_name => 'getUploader',
        );
    }
}

sub _validate_version {
    my ($self) = @_;
    my $svr_version = $self->version();
    my $client_version = $VERSION;
    my ($cMajor, $cMinor) = split(/\./, $client_version);
    my ($sMajor, $sMinor) = split(/\./, $svr_version);
    if ($sMajor != $cMajor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Major version numbers differ.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor < $cMinor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Client minor version greater than Server minor version.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor > $cMinor) {
        warn "New client version available for Bio::KBase::WorkspaceFileHandler::Client\n";
    }
    if ($sMajor == 0) {
        warn "Bio::KBase::WorkspaceFileHandler::Client version is $svr_version. API subject to change.\n";
    }
}

=head1 TYPES



=head2 ws_obj_reference

=over 4



=item Description

A Ws ID (TODO: import this typedef from module Workspace)
@id ws


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 ws_name

=over 4



=item Description

the name of a WS (TODO: import this typedef from module Workspace)


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 filetype_id

=over 4



=item Description

The unique id that can identify a specific file type (e.g. TXT, SBML3.v1.core)


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 filetype_name

=over 4



=item Description

The human readable name of a file type (e.g. 'Text File', 'SBML Level 3 Version 1 core')


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 loader_id

=over 4



=item Description

The unique id of an uploader/downloader that is available


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 loader_name

=over 4



=item Description

The human readable name of an uploader/downloader that is available


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 ws_typedefname

=over 4



=item Description

The name of a type definition available in the Workspace, i.e. "ModuleName.TypeName"


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 FileType

=over 4



=item Description

Information about a particular file type supported for upload or download from the workspace.
  id                  - the unique ID of the FileType
  name                - humand readable name of the file type (likely for display)
  description         - brief (usually one-line) description of the filetype
  url                 - webpage that provides more details on the file type
  valid_extensions    - list of filename extensions which we support for this type
  default_uploader    - id of the default uploader for this file type, empty if none defined
  uploaders           - list of all uploaders registered for this filetype
  default_downloader  - id of the default downloader for this filetype, empty if none defined
  downloaders         - list of all downloaders registered for this filetype


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a WorkspaceFileHandler.filetype_id
name has a value which is a WorkspaceFileHandler.filetype_name
description has a value which is a string
url has a value which is a string
valid_extensions has a value which is a reference to a list where each element is a string
default_uploader has a value which is a WorkspaceFileHandler.loader_id
uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
default_downloader has a value which is a WorkspaceFileHandler.loader_id
downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a WorkspaceFileHandler.filetype_id
name has a value which is a WorkspaceFileHandler.filetype_name
description has a value which is a string
url has a value which is a string
valid_extensions has a value which is a reference to a list where each element is a string
default_uploader has a value which is a WorkspaceFileHandler.loader_id
uploaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id
default_downloader has a value which is a WorkspaceFileHandler.loader_id
downloaders has a value which is a reference to a list where each element is a WorkspaceFileHandler.loader_id


=end text

=back



=head2 Downloader

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a WorkspaceFileHandler.loader_id
name has a value which is a WorkspaceFileHandler.loader_name
description has a value which is a string
input_wstype has a value which is a WorkspaceFileHandler.ws_typedefname
output_filetype has a value which is a WorkspaceFileHandler.filetype_id

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a WorkspaceFileHandler.loader_id
name has a value which is a WorkspaceFileHandler.loader_name
description has a value which is a string
input_wstype has a value which is a WorkspaceFileHandler.ws_typedefname
output_filetype has a value which is a WorkspaceFileHandler.filetype_id


=end text

=back



=head2 Uploader

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a WorkspaceFileHandler.loader_id
name has a value which is a WorkspaceFileHandler.loader_name
description has a value which is a string
input_filetype has a value which is a WorkspaceFileHandler.filetype_id
output_wstype has a value which is a WorkspaceFileHandler.ws_typedefname

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a WorkspaceFileHandler.loader_id
name has a value which is a WorkspaceFileHandler.loader_name
description has a value which is a string
input_filetype has a value which is a WorkspaceFileHandler.filetype_id
output_wstype has a value which is a WorkspaceFileHandler.ws_typedefname


=end text

=back



=head2 filename

=over 4



=item Description

The name of a specific file (e.g. comments.txt, myModel.sbml)


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 filecontent

=over 4



=item Description

The content of a file, serialized as a string. (TODO: define encoding or encoding options)


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 UploadParams

=over 4



=item Description

@optional uploader
@optional metadata


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
type has a value which is a WorkspaceFileHandler.filetype_id
name has a value which is a WorkspaceFileHandler.filename
content has a value which is a WorkspaceFileHandler.filecontent
ws_name has a value which is a WorkspaceFileHandler.ws_name
uploader has a value which is a WorkspaceFileHandler.loader_id
metadata has a value which is a reference to a hash where the key is a string and the value is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
type has a value which is a WorkspaceFileHandler.filetype_id
name has a value which is a WorkspaceFileHandler.filename
content has a value which is a WorkspaceFileHandler.filecontent
ws_name has a value which is a WorkspaceFileHandler.ws_name
uploader has a value which is a WorkspaceFileHandler.loader_id
metadata has a value which is a reference to a hash where the key is a string and the value is a string


=end text

=back



=head2 DownloadParams

=over 4



=item Description

@optional downloader


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
type has a value which is a WorkspaceFileHandler.filetype_id
ref has a value which is a WorkspaceFileHandler.ws_obj_reference
downloader has a value which is a WorkspaceFileHandler.loader_id

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
type has a value which is a WorkspaceFileHandler.filetype_id
ref has a value which is a WorkspaceFileHandler.ws_obj_reference
downloader has a value which is a WorkspaceFileHandler.loader_id


=end text

=back



=head2 DownloadedFile

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
src_obj has a value which is a WorkspaceFileHandler.ws_obj_reference
filename has a value which is a string
content has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
src_obj has a value which is a WorkspaceFileHandler.ws_obj_reference
filename has a value which is a string
content has a value which is a string


=end text

=back



=cut

package Bio::KBase::WorkspaceFileHandler::Client::RpcClient;
use base 'JSON::RPC::Client';

#
# Override JSON::RPC::Client::call because it doesn't handle error returns properly.
#

sub call {
    my ($self, $uri, $obj) = @_;
    my $result;

    if ($uri =~ /\?/) {
       $result = $self->_get($uri);
    }
    else {
        Carp::croak "not hashref." unless (ref $obj eq 'HASH');
        $result = $self->_post($uri, $obj);
    }

    my $service = $obj->{method} =~ /^system\./ if ( $obj );

    $self->status_line($result->status_line);

    if ($result->is_success) {

        return unless($result->content); # notification?

        if ($service) {
            return JSON::RPC::ServiceObject->new($result, $self->json);
        }

        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    elsif ($result->content_type eq 'application/json')
    {
        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    else {
        return;
    }
}


sub _post {
    my ($self, $uri, $obj) = @_;
    my $json = $self->json;

    $obj->{version} ||= $self->{version} || '1.1';

    if ($obj->{version} eq '1.0') {
        delete $obj->{version};
        if (exists $obj->{id}) {
            $self->id($obj->{id}) if ($obj->{id}); # if undef, it is notification.
        }
        else {
            $obj->{id} = $self->id || ($self->id('JSON::RPC::Client'));
        }
    }
    else {
        # $obj->{id} = $self->id if (defined $self->id);
	# Assign a random number to the id if one hasn't been set
	$obj->{id} = (defined $self->id) ? $self->id : substr(rand(),2);
    }

    my $content = $json->encode($obj);

    $self->ua->post(
        $uri,
        Content_Type   => $self->{content_type},
        Content        => $content,
        Accept         => 'application/json',
	($self->{token} ? (Authorization => $self->{token}) : ()),
    );
}



1;
