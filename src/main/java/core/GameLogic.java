package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GameLogic {
	private Card deck[];
	private int deckPos;
	
	private File testFile = null;

	private Card playerHand[];
	private Card playerSplit[];
	private Card dealerHand[];
	private Card dealerSplit[];
	
	private char playerMove[];

	private UserInterface ui;

	char[] polar = {'y','n'};
	
	public GameLogic(char chosenUI) {
		if(chosenUI == 'c') {
			ui = new CLI();
		}else if(chosenUI == 'g') {
			
		}
	}
	
	public void gameInit() {
		playerMove = new char[10];
		deckPos = 0;
		char choice;
		int err = 0;

		ui.message("Welcome to BlackJack");

		if(testFile == null) {
			while(!hasChar(choice = ui.response("Do you have a file you would like to import? (y,n):  "), polar)){
				ui.message("*ERROR choice invalid");
			}
			if(choice == 'y') {
				File file = new File(ui.responseFile("Enter your filename: "));
				if(initDeck(file) == -1) {
					return;
				}
			}else {
				initDeck();
			}
		}else {
			initDeck(testFile);
		}

		initHands();
		deal();
		
		if(hasBlackjack(dealerHand)) {
			ui.outcome('d');
			return;
		}else if(hasBlackjack(playerHand) && getHandValue(dealerHand) > 16) {
			ui.outcome('p');
			return;
		}

		if(size(playerMove) > 0) {

			err = fileTurn();
			if(err == -1) {
				ui.outcome('d');
			}else if(err == -2) {
				return;
			}
		}else if(playerTurn() == -1) {
			ui.outcome('d');
		}

		//TODO Create test cases
		//TODO dealer ai
	}
	
	private int fileTurn() {
		if(playerMove[0] == 'D') {
			if(playerHand[0].getRank() == playerHand[1].getRank()) {
				playerSplit[0] = playerHand[1];
				playerHand[1] = null;

				if((playerHand[size(playerHand)] = deck[deckPos]) == null) {
					ui.message("ERROR not enough cards in file");
					return -2;
				}
				deckPos++;
				if((playerSplit[size(playerSplit)] = deck[deckPos]) == null) {
					ui.message("ERROR not enough cards in file");
					return -2;
				}
				deckPos++;

				ui.message("Player hands: ");
				ui.displayHand(playerHand);
				ui.displayHand(playerSplit);

				ui.message("First hand: ");
				for(char m : playerMove) {
					switch(m) {
						case 'H':
							if((playerHand[size(playerHand)] = deck[deckPos]) == null) {
								ui.message("ERROR not enough cards in file");
								return -2;
							}
							deckPos++;
							ui.displayHand(playerHand);

							if(getHandValue(playerHand) > 21){
								ui.message("You have busted");
								return -1;
							}
							m = 'X';
							break;
						case 'S':
							m = 'X';
							break;
					}
				}
				ui.displayHand(playerHand);
				
				ui.message("Second hand: ");
				for(char m : playerMove ) {
					switch(m) {
						case 'H':
							if((playerSplit[size(playerSplit)] = deck[deckPos]) == null) {
								ui.message("ERROR not enough cards in file");
								return -2;
							}
							deckPos++;
							ui.displayHand(playerSplit);

							if(getHandValue(playerSplit) > 21){
								ui.message("You have busted");
								return -2;
							}
							break;
						case 'S':
							ui.displayHand(playerSplit);
							return 0;
					}
				}
			}else {
				ui.message("ERROR card ranks are not the same, cannot split");
				return -2;
			}
		}else {
			for(char m : playerMove) {
				switch(m) {
					case 'H':
						if((playerHand[size(playerHand)] = deck[deckPos]) == null) {
								ui.message("ERROR not enough cards in file");
								return -2;
						}
						deckPos++;
						ui.displayHand(playerHand);

						if(getHandValue(playerHand) > 21){
							ui.message("You have busted");
							return -1;
						}
						break;
					case 'S':
						return 0;
						
				}
			}
		}
		
		return 0;
	}
	
	public Card[] getPlayerSplit() {
		return playerSplit;
	}

	public Card[] getPlayerHand() {
		return playerHand;
	}
	
	public void setTestCase(File f) {
		testFile = f;
	}
	
	private int playerTurn() {
		char choice = '*';
		int i = 0;
		
		if(playerHand[0].getRank() == playerHand[1].getRank()) {
			while(!hasChar(choice = ui.response("Would you like to split?(y,n): "), polar)) {
				ui.message("*ERROR* choice invalid");
			}
			if(choice == 'y') {
				playerSplit[0] = playerHand[1];
				playerHand[1] = null;

				playerHand[size(playerHand)] = deck[deckPos];
				deckPos++;
				playerSplit[size(playerSplit)] = deck[deckPos];
				deckPos++;

				ui.message("Player hands: ");
				ui.displayHand(playerHand);
				ui.displayHand(playerSplit);
				

				ui.message("First Hand");
				i += hitStand(playerHand);
				ui.message("Second Hand");
				i += hitStand(playerSplit);

				if( i == -2) {
					return -1;
				}else {
					return 0;
				}
			}
		}

		if(hitStand(playerHand) == -1) {
			return -1;
		}
		return 0;
	}
	
	private int hitStand(Card[] cards) {
		char[] choices = {'h','s'};
		char choice = '*';
		while(choice != 's') {
			while(!hasChar(choice = ui.response("Hit(h) or Stand(s)?: "), choices)) {
				ui.message("*ERROR* choice invalid");
			}
			
			if(choice == 'h') {
				cards[size(cards)] = deck[deckPos];
				deckPos++;
				ui.displayHand(cards);
			}
			if(getHandValue(cards) > 21){
				ui.message("You have busted");
				return -1;
			}
		}

		return 0;
	}
	
	private int size(Card[] cards) {
		int count = 0;
		for(Card c: cards) {
			if(c != null) {
				count++;
			}
		}
		return count;
	}
	
	private boolean hasChar(char c, char[] chars) {
		if(new String(chars).indexOf(c) == -1) {
			return false;
		}else {
			return true;
		}
	}
	
	private boolean hasBlackjack(Card[] hand) {
		if(hasAce(hand) > 0) {
			if(getHandValue(hand) == 11) {
				return true;
			}
		}
		if(getHandValue(hand) == 21) {
			return true;
		}else {
			return false;
		}
	}
	
	private int hasAce(Card[] hand) {
		int numAce = 0;
		for(int i = 0; i<size(hand); i++) {
			if(hand[i].getRank() == Card.rank.ACE) {
				numAce++;
			}
		}
		return numAce;
	}
	
	private int getHandValue(Card[] hand) {
		int total = 0;
		
		for(int i = 0; hand[i] != null; i++) {
			total += hand[i].getRank().getVal();
		}

		return total;
	}
	
	private void initHands() {
		playerHand = new Card[11];
		playerSplit = new Card[11];
		dealerHand = new Card[11];
		dealerSplit = new Card[11];
	}
	
	private void deal() {
		playerHand[0] = deck[0];
		playerHand[1] = deck[1];
		dealerHand[0] = deck[2];
		dealerHand[1] = deck[3];
		deckPos += 4;
		
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
	
	private int initDeck(File file) {
		deck = new Card[52];
		BufferedReader br;
		String cards;
		String cardsArr[];
		char[] choices = {'H','S','D'};
		
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			ui.message("File not found");
			return -1;
		}
		try {
			cards = br.readLine();
		} catch (IOException e) {
			ui.message("File does not follow correct format, using default deck setup");
			initDeck();

			try {
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				ui.message("Could not close BufferedReader");
			}

			return -1;
		}
		if(closeBR(br) == -1) {
			return -1;
		}
		
		System.out.println(cards);
		if(!cards.isEmpty()) {
			cardsArr = cards.split("\\s+");
			
			for(String s : cardsArr) {
				if(s.length() > 1) {
					if((deck[size(deck)] = stringToCard(s)) == null) {
						ui.message("Invalid card " + s);
						return -1;
					}
				}else {
					if(!hasChar(playerMove[size(playerMove)] = s.charAt(0), choices)) {
						ui.message("Invalid move " + s.charAt(0));
						return -1;
					}
				}
			}
		}else {
			ui.message("No cards in file");
			return -1;
		}
		
		
		return 0;
	}
	
	private Card stringToCard(String cardStr) {
		Card c = new Card();
		
		for (Card.suit s : Card.suit.values()) {
			if(cardStr.charAt(0) == s.getName()) {
				c.setSuit(s);
			}
		}
		if(c.getSuit() == null) {
			return null;
		}
		for (Card.rank r : Card.rank.values()) {
			if(cardStr.charAt(1) == r.getName()) {
				c.setRank(r);
			}
		}
		if(c.getRank() == null) {
			return null;
		}

		return c;
	}
	
	private int closeBR(BufferedReader br) {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			ui.message("Could not close BufferedReader");
			return -1;
		}
		return 0;
	}
	private int size(char[] chars) {
		int count = 0;
		for(char c: chars) {
			if(c != 0) {
				count++;
			}
		}
		return count;
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

}
