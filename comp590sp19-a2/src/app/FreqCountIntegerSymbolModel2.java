package app;

import ac.SourceModel;

public class FreqCountIntegerSymbolModel2 implements SourceModel<Integer> {

	private Integer[] _symbols;
	private int _primarySymbol;
	private int[] _counts;
	private int _total_count;

	public FreqCountIntegerSymbolModel2(Integer[] symbols, int[] counts) {
		assert symbols != null;
		assert symbols.length > 1;
		if (counts != null) {
			assert symbols.length == counts.length;
		} else {
			counts = new int[symbols.length];
			for (int i = 0; i < counts.length; i++) {
				counts[i] = 1;
			}
		}

		_total_count = 0;
		for (int i = 0; i < symbols.length; i++) {
			assert symbols[i] != null;
			assert counts[i] >= 0;
			_total_count += counts[i];
		}

		_symbols = symbols.clone();
		_counts = counts.clone();
	}

	public FreqCountIntegerSymbolModel2(Integer[] symbols) {
		this(symbols, null);
	}

	public void addToCount(int symbol) {
		_counts[symbol]++;
		_total_count++;
	}

	@Override
	public int size() {
		return _symbols.length;
	}

	@Override
	public Integer get(int index) {
		assert index >= 0 && index < size();

		return _symbols[index];
	}

	public double cdfLow(int index) {
		assert index >= 0 && index < size();

		int cumulative_count = 0;

		for (int i = 0; i < index; i++) {
			cumulative_count += _counts[i];
		}

		return (1.0 * cumulative_count) / (1.0 * _total_count);
	}
}
