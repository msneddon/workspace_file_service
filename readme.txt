Service for up/downloading files to the workspace

Currently designed to handle small files (eg <1gb) because it uses json rpc.  May be
extended to handle large files in the future by implementing custom clients that
support chunked uploads and continuation of interrupted uploads/downloads.
