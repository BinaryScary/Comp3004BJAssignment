package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class Card {
	public static enum suit{ 
		DIAMONDS('D'),HEARTS('H'),SPADES('S'),CLUBS('C');
		private final char name;
		
		suit(char name){
			this.name = name;
		}
		
		public char getName() {
			return this.name;
		}
	}

	public static enum rank{ 
		ACE(1,'A'),TWO(2,'2'),THREE(3,'3'),FOUR(4,'4'),FIVE(5,'5'),SIX(6,'6'),SEVEN(7,'7'),EIGHT(8,'8'),NINE(9,'9'),TEN(10,'1'),JACK(10,'J'),QUEEN(10,'Q'),KING(10,'K');
		private final int val;
		private final char name;

		rank(int val, char name){
			this.val = val;
			this.name = name;
		}
		public int getVal() {
			return this.val;
		}
		public char getName() {
			return this.name;
		}
	}
	
	private rank cardRank;
	private suit cardSuit;
	private Image img;
	
	public Card(rank r, suit s){
		cardRank = r;
		cardSuit = s;
		img = null;
//		try {
//			img = new Image(new FileInputStream("src/main/resources/Cards/"+s.getName()+r.getName()+".png"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			System.out.println("Card Image not found");
//		}
	}
	
	public Card() {
		cardRank = null;
		cardSuit = null;
	}

	public void setSuit(suit s) {
		cardSuit = s;
	}

	public void setRank(rank r) {
		cardRank = r;
	}
	
	public Image getImage() {
		return img;
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
	
	@Override
	public String toString() {
		return cardRank +" "+ cardSuit;
	}

	@Override
	public boolean equals(Object c) {
		if(this.cardRank == ((Card)c).cardRank && this.cardSuit == ((Card)c).cardSuit) {
			return true;
		}else {
			return false;
		}
	}
}
