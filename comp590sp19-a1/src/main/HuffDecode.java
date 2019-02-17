package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws InsufficientBitsLeftException, IOException {
		String input_file_name = "data/compressed.dat";
		String output_file_name = "data/reuncompressed.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();

		// Read in huffman code lengths from input file and associate them with each
		// symbol as a
		// SymbolWithCodeLength object and add to the list symbols_with_length.

		// Array for calculation purposes
		int codelength[] = new int[256];

		// adding symbolswithlengths to list
		for (int i = 0; i < 256; i++) {

			int bytes = bit_source.next(8);
			SymbolWithCodeLength symbols = new SymbolWithCodeLength(i, bytes);
			symbols_with_length.add(symbols);

			codelength[i] = bytes;

		}

		// Then sort the symbols.

		Collections.sort(symbols_with_length);

		// for (SymbolWithCodeLength s : symbols_with_length) {
		//
		// System.out.println("Value is " + s.value() + " Length is " + s.codeLength());
		// }

		// Now construct the canonical huffman tree

		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(symbols_with_length);

		int num_symbols = 0;

		// Read in the next 32 bits from the input file the number of symbols

		num_symbols = bit_source.next(32);

		try {

			// Open up output file.

			FileOutputStream fos = new FileOutputStream(output_file_name);

			// for symbol count in calculation
			int symbolcount[] = new int[256];
			for (int i = 0; i < 256; i++) {
				symbolcount[i] = 0;
			}

			// actual decoding of file
			for (int i = 0; i < num_symbols; i++) {
				// Decode next symbol using huff_tree and write out to file.
				int b = huff_tree.decode(bit_source);
				symbolcount[b]++;
				fos.write(b);
			}

			// math for actual entropy
			double probs[] = new double[256];
			for (int i = 0; i < 256; i++) {
				double prob = (double) symbolcount[i] / num_symbols;
				probs[i] = prob;
			}

			double actual = 0;
			for (int i = 0; i < 256; i++) {
				double entropycalc = probs[i] * (Math.log(2) * codelength[i]);
				actual += entropycalc;

			}

			System.out.println("Actual Entropy is " + actual);

			// Flush output and close files.

			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
