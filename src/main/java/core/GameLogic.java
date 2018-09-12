package core;

import java.util.Random;

public class GameLogic {
	private Card deck[];
	private Card playerHand[];
	private Card playerSplit[];
	private Card dealerHand[];
	private Card dealerSplit[];
	private UserInterface ui;
	
	public GameLogic(char chosenUI) {
		if(chosenUI == 'c') {
			ui = new CLI();
		}else if(chosenUI == 'g') {
			
		}
		
		ui.message("Welcome to BlackJack");
		initDeck();
		//TODO initDeck(file);
		initHands();
		deal();
		
		if(getHandValue(dealerHand) == 21) {
			ui.outcome('d');
		}else{
			
		}
		
//		Card[] h = new Card[2];
//		h[0]= new Card(Card.rank.ACE, Card.suit.CLUBS);
//		h[1]= new Card(Card.rank.TEN, Card.suit.CLUBS);

	}
	
	private boolean hasAce(Card[] hand) {
		for(int i = 0; i<hand.length; i++) {
			if(hand[i].getRank() == Card.rank.ACE) {
				return true;
			}
		}
		return false;
	}
	
	private int getHandValue(Card[] hand) {
		int total = 0;
		
		for(int i = 0; i<hand.length; i++) {
			total += hand[i].getRank().getVal();
		}

		return total;
	}
	
	private void initHands() {
		playerHand = new Card[2];
		dealerHand = new Card[2];
	}
	
	private void deal() {
		playerHand[0] = deck[0];
		dealerHand[0] = deck[1];
		playerHand[1] = deck[2];
		dealerHand[1] = deck[3];
		
		ui.message("Dealer hand: ");
		ui.displayDealerHand(dealerHand);
		ui.message("Player hand: ");
		ui.displayHand(playerHand);
	}
	private void shuffle() {
		Random rNum = new Random();
		int rPos;
		Card c;

		for(int i=0; i<deck.length; i++) {
			rPos = rNum.nextInt(deck.length);
			c = deck[rPos];
			deck[rPos] = deck[i];
			deck[i] = c;
		}
	}
	
	private void initDeck() {
		deck = new Card[52];
		int i = 0;
		
		for (Card.suit s : Card.suit.values()) {
			for (Card.rank r : Card.rank.values()) {
				Card c = new Card(r,s);
				deck[i] = c;
				i++;
			}
		}

		shuffle();
	}

	//TODO Create test cases
}
