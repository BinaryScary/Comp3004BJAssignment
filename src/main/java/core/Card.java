package core;
public class Card {
	public static enum suit{ 
		DIAMONDS,HEARTS,SPADES,CLUBS;
	}

	public static enum rank{ 
		ACE(1),TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),NINE(9),TEN(10),JACK(10),QUEEN(10),KING(10);
		private final int val;

		rank(int val){
			this.val = val;
		}
		public int getVal() {
			return this.val;
		}
	}
	
	private rank cardRank;
	private suit cardSuit;
	
	public Card(rank r, suit s){
		cardRank = r;
		cardSuit = s;
	}
	
	public void setSuit(suit s) {
		cardSuit = s;
	}

	public void setRank(rank r) {
		cardRank = r;
	}
	
	public suit getSuit() {
		return cardSuit;
	}

	public rank getRank() {
		return cardRank;
	}
	
	public char getColour() {
		if(cardSuit == suit.DIAMONDS || cardSuit == suit.HEARTS) {
			return 'R';
		}else {
			return 'B';
		}
	}
}
