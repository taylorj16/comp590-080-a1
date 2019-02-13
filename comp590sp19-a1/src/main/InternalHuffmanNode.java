package main;

public class InternalHuffmanNode implements HuffmanNode {

	private HuffmanNode right;
	private HuffmanNode left;
	private int count;
	private boolean traverse;

	public InternalHuffmanNode() {
		this.right = null;
		this.left = null;
		this.count = 0;
		this.traverse = false;

	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		// sum of the count associated with its children
		// not used when creating canonical tree
		return 0;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int symbol() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Internal Node can't have symbol");
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		HuffmanNode n = this;
		boolean leftPathFull = false;
		boolean rightPathFull = false;

		while (n.left() != null) {
			n = n.left();
			n.isFull();
			// if it does equal an internal node
			if (n instanceof LeafHuffmanNode) {
				leftPathFull = true;
				break;
			}
		}
		// reintialize n to current node internal node

		n = this;
		while (n.right() != null) {

			n = n.right();
			n.isFull();
			if (n instanceof LeafHuffmanNode) {
				rightPathFull = true;
				break;
			}

		}

		if (leftPathFull && rightPathFull) {
			return true;
		} else {
			return false;
		}

	}

	public void setTraverse() {
		this.traverse = true;

	}

	public boolean haveTraversed() {

		return this.traverse;

	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		// TODO Auto-generated method stub

		if (length != 1) {
			if (this.left == null) {
				// insert internal node

				InternalHuffmanNode node = new InternalHuffmanNode();
				this.setLeft(node);
				node.insertSymbol(length - 1, symbol);
				// System.out.println("Recur 1");

			} else if (this.left instanceof InternalHuffmanNode) {

				if (this.left.isFull()) {
					if (this.right == null) {

						InternalHuffmanNode node = new InternalHuffmanNode();
						this.setRight(node);
						node.insertSymbol(length - 1, symbol);
						// System.out.println("Recur 2");

					} else if (this.right instanceof InternalHuffmanNode) {

						InternalHuffmanNode node = (InternalHuffmanNode) this.right;
						node.insertSymbol(length - 1, symbol);
						// System.out.println("Recur 3");
					}
				} else {
					InternalHuffmanNode node = (InternalHuffmanNode) this.left;
					node.insertSymbol(length - 1, symbol);
					// System.out.println("Recur 4");
				}
				// if there is a leaf node return false
			} else if (this.right == null) {
				// insert internal node
				InternalHuffmanNode node = new InternalHuffmanNode();
				this.setRight(node);
				node.insertSymbol(length - 1, symbol);
				// System.out.println("Recur 5");

			} else if (this.right instanceof InternalHuffmanNode) {
				InternalHuffmanNode node = (InternalHuffmanNode) this.right;
				node.insertSymbol(length - 1, symbol);
				// System.out.println("Recur 6");

			} else if (this.isFull() == true) {
				return false;
			}

			// when length is now 1
		} else if (this.left == null) {
			this.setLeft(new LeafHuffmanNode(new SymbolWithCodeLength(symbol, length)));

			// System.out.println("Leaf Inserted");
			return true;
		} else if (this.right == null) {

			this.setRight(new LeafHuffmanNode(new SymbolWithCodeLength(symbol, length)));
			// System.out.println("Leaf Inserted");
			return true;
		}
		return false;
	}

	public void setLeft(HuffmanNode node) {

		this.left = node;
		this.count += node.count();
	}

	public void setRight(HuffmanNode node) {

		this.right = node;
		this.count += node.count();
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		return this.left;
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		return this.right;
	}

}
