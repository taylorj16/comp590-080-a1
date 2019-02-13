package main;

import java.io.IOException;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffmanDecodeTree {

	private HuffmanNode _root;

	public HuffmanDecodeTree(List<SymbolWithCodeLength> symbols_with_code_lengths) {

		// Create empty internal root node.

		_root = new InternalHuffmanNode();

		// Insert each symbol from list into tree

		for (int i = 0; i < symbols_with_code_lengths.size(); i++) {

			SymbolWithCodeLength symbol = symbols_with_code_lengths.get(i);
			_root.insertSymbol(symbol.codeLength(), symbol.value());

		}
		// System.out.println("Finished");

		// If all went well, your tree should be full
		// System.out.println("" + _root.isFull());
		assert _root.isFull();
	}

	public int decode(InputStreamBitSource bit_source) throws IOException, InsufficientBitsLeftException {

		// Start at the root
		HuffmanNode n = _root;

		while (!n.isLeaf()) {
			// Get next bit and walk either left or right,
			int b = bit_source.next(1);
			if (b == 0) {
				n = n.left();
			} else {
				n = n.right();
			}

			// updating n to be as appropriate

		}
		// System.out.println("return symbol");

		// Return symbol at leaf
		return n.symbol();
	}

}
