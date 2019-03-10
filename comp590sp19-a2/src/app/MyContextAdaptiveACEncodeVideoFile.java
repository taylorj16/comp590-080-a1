package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ac.ArithmeticEncoder;
import io.OutputStreamBitSink;

public class MyContextAdaptiveACEncodeVideoFile {

	public static void main(String[] args) throws IOException {
		String input_file_name = "data/out.dat";
		String output_file_name = "data/mySchemeCompression.dat";
		FileInputStream fis = new FileInputStream(input_file_name);

		int range_bit_width = 40;

		System.out.println("Encoding text file: " + input_file_name);
		System.out.println("Output file: " + output_file_name);
		System.out.println("Range Register Bit Width: " + range_bit_width);

		int num_symbols = (int) new File(input_file_name).length();

		// to exploit temporal coherence, since pixel values will be similar from one
		// frame to the next
		// i will attempt to encode the first 4096 bytes (pixels) because there would
		// likely be
		// little to no change in pixel values to the next frame

		// use the model of the previous pixel in the previous frame as the model.

		Integer[] symbols = new Integer[256];
		for (int i = 0; i < 256; i++) {
			symbols[i] = i;
		}

		// Create 256 models. Model chosen depends on value of symbol prior to
		// symbol being encoded.

		// Using own freqcount class

		FreqCountIntegerSymbolModel2[] models = new FreqCountIntegerSymbolModel2[256];

		for (int i = 0; i < 256; i++) {
			// Create new model with default count of 1 for all symbols
			models[i] = new FreqCountIntegerSymbolModel2(symbols);
		}

		ArithmeticEncoder<Integer> encoder = new ArithmeticEncoder<Integer>(range_bit_width);

		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// First 4 bytes are the number of symbols encoded
		bit_sink.write(num_symbols, 32);

		// Next byte is the width of the range registers
		bit_sink.write(range_bit_width, 8);

		// Now encode the input
		// FileInputStream fis = new FileInputStream(input_file_name);

		// Use model 0 as initial model.
		FreqCountIntegerSymbolModel2 model = models[0];
		int[] priorSymPix = new int[4096];

		for (int i = 0; i < num_symbols; i++) {
			int next_symbol = fis.read();
			encoder.encode(next_symbol, model, bit_sink);

			// for temporal coherance, read in first 4096 bytes and then use those for
			// initial models, updating
			// the model to the symbol of previous frame pixel

			if (num_symbols < 4097) {

				// reads in first 4096 bytes to set up temporal coherence
				priorSymPix[i] = next_symbol;

				// Update model used
				model.addToCount(next_symbol);

				// update model to be next symbol from previous pixel per usual
				model = models[next_symbol];
			} else {

				// update model used
				model.addToCount(next_symbol);

				// Set up next model based on symbol encoded from previous frame (prior-
				// adaptive coding to exploit temporal coherance)

				// use symbol from prior sym pix as model then update
				model = models[priorSymPix[i % 4096]];
				priorSymPix[i % 4096] = next_symbol;

			}
		}

		fis.close();

		// Finish off by emitting the middle pattern
		// and padding to the next word

		encoder.emitMiddle(bit_sink);
		bit_sink.padToWord();
		fos.close();

		System.out.println("Done");
	}
}
