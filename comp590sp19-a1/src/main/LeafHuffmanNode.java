package main;

public class LeafHuffmanNode implements HuffmanNode {

	private SymbolWithCodeLength symbol;
	private int height;
	private int count;

	public LeafHuffmanNode(SymbolWithCodeLength symbol) {

		this.symbol = symbol;
		this.height = symbol.codeLength();
		this.count = symbol.codeLength();

	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		// count associated with the symbol of the leaf
		// not used when creating canonical tree
		return count;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int symbol() {
		// TODO Auto-generated method stub
		return this.symbol.value();
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Can't insert child on leaf node");
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		return null;
		// throw new RuntimeException("Leaf node has no child");
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		return null;
		// throw new RuntimeException("Leaf node has no child");
	}

}
