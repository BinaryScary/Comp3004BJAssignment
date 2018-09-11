package core;
public class card {
	public static enum suit{ 
		DIAMONDS,HEARTS,SPADES,CLUBS;
	}

	public static enum rank{ 
		ACE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING;
	}
	
	private rank cardRank;
	private suit cardSuit;
	
	public card(rank r, suit s){
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
	//TODO add junit test then git submit
}
