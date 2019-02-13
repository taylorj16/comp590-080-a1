package Huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CanonicalHuffDecoder {

	// for the first 256 codeword lengths, each correspond to 0 through 256, it has
	// a number that gives the byte length(the amount of
	// bits in a byte
	// Does it give the actual bytes?
	// From there, create the Canonical Huffman tree
	// Trace tree to leaf nodes to get the byte code
	// index of the array is the corresponding symbol
	// 1) sort array so that the smallest lengths come first
	// break length ties by symbol order (smaller symbols first)
	// look at javas comparator or comparable interfaces create compare or
	// comparetoo method
	// create new nodes(objects) in tree that hold nothing

	// How do I read in a file?

	// MAybe creat a class that encapsulates the symbol and the length
	// together(implements comparable)

	private long[] codeLengths;
	private FileReader file;

	public CanonicalHuffDecoder(File file) {

		this.codeLengths = new long[256];

		// find file to read in bytes
		try {
			this.file = new FileReader("data/compressed.dat");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

	}

	public long findLengths() {
		// changed from long[]
		return 0;
	}
}
