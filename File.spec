/*
Definitions of typed object representations of basic file types.
*/
module File {

	/*
		@searchable ws_subset filename
	*/
	typedef structure {
		string filename;
		string content;
	} TextFile;




	/*
		A single entry in a FASTA file, which includes the header line and a sequence.
	*/
	typedef structure {
		string header;
		string sequence;
	} FastaRow;
	/*
		@searchable ws_subset filename
	*/
	typedef structure {
		string filename;
		list <FastaRow> content;
	} FastaFile;




};