package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

	public static void main(String[] args) throws IOException {
		String input_file_name = "data/uncompressed.txt";
		String output_file_name = "data/recompressed.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		int[] symbol_counts = new int[256];
		for (int i = 0; i < symbol_counts.length; i++) {
			symbol_counts[i] = 0;
		}

		int num_symbols = 0;

		// Read in each symbol (i.e. byte) of input file and
		// update appropriate count value in symbol_counts
		// Should end up with total number of symbols
		// (i.e., length of file) as num_symbols

		for (int i = 0; i < fis.available(); i++) {
			char current = (char) fis.read();
			int asChar = (int) current;
			num_symbols++;
			symbol_counts[asChar]++;
		}

		// Close input file
		fis.close();

		// Create array of symbol values

		int[] symbols = new int[256];
		for (int i = 0; i < 256; i++) {
			symbols[i] = i;
		}

		// Create encoder using symbols and their associated counts from file.

		HuffmanEncoder encoder = new HuffmanEncoder(symbols, symbol_counts);

		// Open output stream.
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// Write out code lengths for each symbol as 8 bit value to output file.
		for (int i = 0; i < 256; i++) {
			String strLength = encoder.getCode(i);

			// convert to 8-bit byte
			// byte[] bytes = strLength.getBytes();
			// fos.write(bytes);
			encoder.encode(i, bit_sink);
		}

		// Write out total number of symbols as 32 bit value.
		String result = Long.toBinaryString(Integer.toUnsignedLong(num_symbols) | 0x100000000L).substring(1);
		byte[] b = result.getBytes();
		fos.write(b);

		// Reopen input file.
		fis = new FileInputStream(input_file_name);

		// Go through input file, read each symbol (i.e. byte),
		// look up code using encoder.getCode() and write code
		// out to output file.

		for (int i = 0; i < num_symbols; i++) {
			char current;
			int symVal;
			current = (char) fis.read();
			symVal = (int) current;
			encoder.encode(symVal, bit_sink);
		}

		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
	}
}
