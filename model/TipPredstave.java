package model;

public enum TipPredstave {
	
	DRAMA, OPERA, BALET;
	
	private String [] opis = {"drama", "opera", "balet"};
	
	@Override
	public String toString() {
		return opis[this.ordinal()];
	}
}
