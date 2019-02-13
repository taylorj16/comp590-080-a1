package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.OutputStreamBitSink;

public class HuffmanEncoder {

	private Map<Integer, String> _code_map;

	public HuffmanEncoder(int[] symbols, int[] symbol_counts) {
		assert symbols.length == symbol_counts.length;

		// Given symbols and their associated counts, create initial
		// Huffman tree. Replacing codelengths in symbolwithcodelength for now
		// to keep nodes together, will create new leaf nodes later with correct lengths
		List<SymbolWithCodeLength> initial_list = new ArrayList<SymbolWithCodeLength>();

		for (int i = 0; i < 256; i++) {
			SymbolWithCodeLength leaf = new SymbolWithCodeLength(symbols[i], symbol_counts[i]);
			initial_list.add(leaf);
		}

		Collections.sort(initial_list);

		for (SymbolWithCodeLength s : initial_list) {

			System.out.println("Value is " + s.value() + " Length is " + s.codeLength());
		}

		// now create Huffman tree

		// Use that tree to get code lengths associated
		// with each symbol.

		// Create canonical tree using code lengths.

		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes

		List<HuffmanNode> node_list = new ArrayList<HuffmanNode>();

		for (int i = 0; i < 256; i++) {
			LeafHuffmanNode leaf = new LeafHuffmanNode(new SymbolWithCodeLength(symbols[i], symbol_counts[i]));
			node_list.add(leaf);
		}

		// Create a leaf node for each symbol, encapsulating the
		// frequency count information into each leaf.

		// Sort the leaf nodes
		node_list.sort(null);

		// While you still have more than one node in your list...
		while (node_list.size() > 1) {
			// Remove the two nodes associated with the smallest counts
			//// since i sort from smallest to largest, smallest will always be the first
			// two nodes
			HuffmanNode small_1 = node_list.get(0);
			HuffmanNode small_2 = node_list.get(1);
			node_list.remove(0);
			node_list.remove(1);

			// Create a new internal node with those two nodes as children.
			InternalHuffmanNode node = new InternalHuffmanNode();
			node.setLeft(small_1);
			node.setRight(small_2);

			// Add the new internal node back into the list
			node_list.add(node);

			// Resort

			node_list.sort(null);
		}

		// Create a temporary empty mapping between symbol values and their code strings
		Map<Integer, String> cmap = new HashMap<Integer, String>();

		// Start at root and walk down to each leaf, forming code string along the
		// way (0 means left, 1 means right). Insert mapping between symbol value and
		// code string into cmap when each leaf is reached.

		HuffmanNode node = node_list.get(0);

		for (int i = 0; i < 256; i++) {
			String bitMapping = "";
			while (!node.isLeaf()) {

				if (node.left() instanceof InternalHuffmanNode) {
					if (((InternalHuffmanNode) node.left()).haveTraversed()) {
						continue;
					} else {
						bitMapping += "0";
						((InternalHuffmanNode) node).setTraverse();
					}

				} else if (node.right() instanceof InternalHuffmanNode) {
					if (((InternalHuffmanNode) node.right()).haveTraversed()) {
						continue;
					} else {
						bitMapping += "1";
						((InternalHuffmanNode) node).setTraverse();
					}
				} else if (node.left() instanceof LeafHuffmanNode) {
					bitMapping += "0";
					node = node.left();
				} else if (node.right() instanceof LeafHuffmanNode) {
					bitMapping += "1";
					node = node.right();
				}
			}
			cmap.put(node.symbol(), bitMapping);
		}

		// Create empty list of SymbolWithCodeLength objects
		List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();

		// For each symbol value, find code string in cmap and create new
		// SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you found in
		// cmap).

		for (int i = 0; i < 256; i++) {
			String strlength = this.getCode(i);
			int length = strlength.length();
			SymbolWithCodeLength sym = new SymbolWithCodeLength(i, length);
			sym_with_length.add(sym);
		}

		// Sort sym_with_lenght
		Collections.sort(sym_with_length);

		// Now construct the canonical tree as you did in HuffmanDecodeTree constructor

		InternalHuffmanNode canonical_root = new InternalHuffmanNode();
		for (int i = 0; i < sym_with_length.size(); i++) {

			SymbolWithCodeLength symbol = sym_with_length.get(i);
			canonical_root.insertSymbol(symbol.codeLength(), symbol.value());
		}

		// If all went well, tree should be full.
		assert canonical_root.isFull();

		// Create code map that encoder will use for encoding

		_code_map = new HashMap<Integer, String>();

		// Walk down canonical tree forming code strings as you did before and
		// insert into map.
	}

	public String getCode(int symbol) {
		return _code_map.get(symbol);
	}

	// public SymbolWithCodeLength()

	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(_code_map.get(symbol));
	}

}
