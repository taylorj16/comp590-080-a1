package main;

/* SymbolWithCodeLength
 * 
 * Class that encapsulates a symbol value along with the length of the code
 * associated with that symbol. Used to build the canonical huffman tree.
 * Implements Comparable in order to sort first by code length and then by symbol value.
 */

public class SymbolWithCodeLength implements Comparable<SymbolWithCodeLength> {

	// Instance fields should be declared here.
	private int value;
	private int code_length;

	// Constructor
	public SymbolWithCodeLength(int value, int code_length) {
		this.value = value;
		this.code_length = code_length;

	}

	// codeLength() should return the code length associated with this symbol
	public int codeLength() {
		// Needs implementation

		return this.code_length;
	}

	// value() returns the symbol value of the symbol
	public int value() {
		// Needs implementation
		return this.value;
	}

	// compareTo implements the Comparable interface
	// First compare by code length and then by symbol value.
	public int compareTo(SymbolWithCodeLength other) {
		// Needs implementation

		if (this.codeLength() < other.codeLength()) {

			return -1;

		} else if (this.codeLength() > other.codeLength()) {

			return 1;

		} else {
			// case that codelengths are the same
			// break ties by comparing symbol values

			if (this.value() < other.value()) {
				return -1;
			} else {
				return 1;
			}

		}

	}

	public String toString() {

		return "The value is " + this.value() + " The length is " + this.codeLength();
	}
}
