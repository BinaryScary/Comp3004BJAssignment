package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.layout.Pane;

public class GameLogic {
	private Card deck[];
	private int deckPos;
	
	private File testFile;

	private Card playerHand[];
	private Card playerSplit[];
	private Card dealerHand[];
	private Card dealerSplit[];
	
	private char playerMove[];
	private String winner;

	private UserInterface ui;

	char[] polar = {'y','n'};
	
	public GameLogic(char chosenUI) {
		if(chosenUI == 'c') {
			ui = new CLI();
		}else if(chosenUI == 'g') {
			ui = new GUI();
		}
		
		// set all variables that have getters to null so no nullpointer errors occur
		playerHand = null;
		playerSplit = null;	
		dealerHand = null;	
		dealerSplit = null;	
		testFile = null;
		winner = null;
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
				if(initDeck(file) <= -1) {
					return;
				}
			}else {
				initDeck();
			}
		}else {
			if(initDeck(testFile) <= -1) {
				return;
			}
		}

		initHands();
		deal();
		
		if(hasBlackjack(dealerHand)) {
			ui.message("-Dealer has blackjack-");
			displayScore();
			ui.outcome('d');
			winner = "Dealer BlackJack";
			return;
		}else if(hasBlackjack(playerHand)) {
			ui.message("Dealer hand: ");
			ui.displayHand(dealerHand);
			ui.message("-Player has blackjack-");
			displayScore();
			ui.outcome('p');
			winner = "Player BlackJack";
			return;
		}

		if(size(playerMove) > 0) {
			err = fileTurn();
			if(err == -1) {
				outcome();
				return;
			}else if(err == -2) {
				return;
			}
		}else if(playerTurn() == -1) {
			outcome();
			return;
		}

		ui.message("Dealers hand: ");
		err = dealerTurn();
		if(err == -1) {
			outcome();
			return;
		}else if(err == -2) {
			outcome();
			return;
		}
		
		outcome();
	}
	
	public String getWinner() {
		return winner;
	}
	
	public Card[] getDealerSplit() {
		return dealerSplit;
	}

	public Card[] getDealerHand() {
		return dealerHand;
	}

	public Card[] getPlayerSplit() {
		return playerSplit;
	}

	public Card[] getPlayerHand() {
		return playerHand;
	}
	
	public File getTestFile() {
		return testFile;
	}
	
	public void setTestCase(File f) {
		if(f != null) {
			testFile = f;
		}
	}
	
	public char[] getPlayerMoves() {
		if(size(playerMove) == 0) {
			return null;
		}
		return playerMove;
	}
	
	public Card[] getDeck() {
		return deck;
	}

	private void displayScore() {
		if(bestHandValue(dealerHand, dealerSplit) != 0) {
			ui.message("Dealers score: " + bestHandValue(dealerHand, dealerSplit));
		}
		if(bestHandValue(playerHand, playerSplit) != 0) {
			ui.message("Players score: " + bestHandValue(playerHand, playerSplit));
		}
	}
	
	private int fileTurn() {
		int moveReWrite = -1; //foreach iterator can't explicitly access array variable
		
		if(playerMove[0] == 'D') {
			if(playerHand[0].getRank() == playerHand[1].getRank()) {
				playerSplit[0] = playerHand[1];
				playerHand[1] = null;
	
				if((playerHand[size(playerHand)] = deck[deckPos]) == null) {
					nextDeckCheck();
					playerHand[size(playerHand)] = deck[deckPos];
					//return -2;
				}
				deckPos++;
				if((playerSplit[size(playerSplit)] = deck[deckPos]) == null) {
					nextDeckCheck();
					playerSplit[size(playerSplit)] = deck[deckPos];
					//return -2;
				}
				deckPos++;
	
				ui.message("Player hands: ");
				ui.displayHand(playerHand);
				ui.displayHand(playerSplit);
	
				ui.message("First hand: ");
				for(char m : playerMove) {
					moveReWrite++;
					switch(m) {
						case 'H':
							if((playerHand[size(playerHand)] = deck[deckPos]) == null) {
								nextDeckCheck();
								playerHand[size(playerHand)] = deck[deckPos];
								//return -2;
							}
							deckPos++;
							ui.displayHand(playerHand);
	
							if(getHandValue(playerHand) > 21){
								playerMove[moveReWrite] = 'X';
								break;
							}else {
								playerMove[moveReWrite] = 'X';
								continue;
							}
						case 'S':
							playerMove[moveReWrite] = 'X';
							break;
						case 'D':
							playerMove[moveReWrite] = 'X';
							continue;
					}
					break;
				}
				ui.displayHand(playerHand);
				
				ui.message("Second hand: ");
				for(char m : playerMove ) {
					switch(m) {
						case 'H':
							if((playerSplit[size(playerSplit)] = deck[deckPos]) == null) {
								nextDeckCheck();
								playerSplit[size(playerSplit)] = deck[deckPos];
								//return -2;
							}
							deckPos++;
							ui.displayHand(playerSplit);
	
							if(getHandValue(playerSplit) > 21){
								ui.message("Player have busted");
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
								nextDeckCheck();
								playerHand[size(playerHand)] = deck[deckPos];
								//return -2;
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
	
	private void outcome() {
		if(bestHandValue(playerHand, playerSplit) == 0  || bestHandValue(playerHand, playerSplit) > 21) {
			ui.message("-Player busted-");
			displayScore();
			ui.outcome('d');
			winner = "Player busted";
		}else if(bestHandValue(dealerHand, dealerSplit) == 0 || bestHandValue(dealerHand, dealerSplit) > 21) {
			ui.message("-Dealer busted-");
			displayScore();
			ui.outcome('p');
			winner = "Dealer busted";
		}else if(bestHandValue(dealerHand,dealerSplit) >= bestHandValue(playerHand, playerSplit)){
			displayScore();
			ui.outcome('d');
			winner = "Dealer Won";
		}else if(bestHandValue(dealerHand,dealerSplit) < bestHandValue(playerHand, playerSplit)){
			displayScore();
			ui.outcome('p');
			winner = "Player Won";
		}
	}
	
	private int bestHandValue(Card[] hand1, Card[] hand2) {
		int values[] = {getHighestValue(hand1),getHighestValue(hand2), getHandValue(hand1), getHandValue(hand2)};
		int best = 0;
		for(int i : values) {
			if(i > best && i < 22) {
				best = i;
			}
		}
		return best;
	}
	
	public String cardsToString(Card[] hand) {
		String str = "";
		
		for(Card c : hand) {
			if(c != null) {
				str += c.toString() + " ";
			}
		}
		str = str.substring(0, str.length() - 1);

		return str;
	}
	
	public int getHighestValue(Card[] hand) {
		if(hasAce(hand) > 0 && (getHandValue(hand) + 10) <= 21) {
			return (getHandValue(hand)+10);
		}
		return getHandValue(hand);
	}
	
	private int dealerTurn() {
		// return -1 if both bust
		int bust = 0;
		if(dealerHand[0].getRank() != dealerHand[1].getRank()) {
			dealerHitStand(dealerHand);
		}else if(getHandValue(dealerHand) <= 17) {
			dealerSplit[0] = dealerHand[1];
			dealerHand[1] = null;

			dealerHand[size(dealerHand)] = deck[deckPos];
			if(dealerHand == null) {
				nextDeckCheck();
				dealerHand[size(dealerHand)] = deck[deckPos];
			}
			deckPos++;
			dealerSplit[size(dealerSplit)] = deck[deckPos];
			if(dealerSplit == null) {
				nextDeckCheck();
				dealerSplit[size(dealerSplit)] = deck[deckPos];
			}
			deckPos++;
			ui.message("First Hand: ");
			bust += dealerHitStand(dealerHand);
			ui.message("Second Hand: ");
			bust += dealerHitStand(dealerSplit);
			if(bust == -2) {
				return -1;
			}
		}
		return 0;
	}
	private int dealerHitStand(Card[] hand) {
		ui.displayHand(hand);
		while(dealerHit(hand)) {
			if(deckPos >= size(deck)) {
				nextDeckCheck();
				//return -2;
			}
			hand[size(hand)] = deck[deckPos];
			deckPos++;
			ui.displayHand(hand);
		}
		if(getHandValue(hand) > 21) {
			return -1;
		}
		return 0;
	}
	
	private boolean dealerHit(Card[] hand) {
		//no need to check for 17 because getHandValue always checks hard case
		if(getHighestValue(hand) < 17) {
			return true;
		}else if(getHighestValue(hand) == 17 && hasAce(hand) > 0) {
			return true;
		}
		
		
		return false;
	}

	private int playerTurn() {
		char choice = '*';
		int i = 0;
		
		if(playerHand[0].getRank() == playerHand[1].getRank()) {
			while(!hasChar(choice = ui.response("Would you like to split?(y,n): "), polar)) {
				ui.message("*ERROR choice invalid");
			}
			if(choice == 'y') {
				playerSplit[0] = playerHand[1];
				playerHand[1] = null;

				playerHand[size(playerHand)] = deck[deckPos];
				if(playerHand == null) {
					nextDeckCheck();
					playerHand[size(playerHand)] = deck[deckPos];
				}
				deckPos++;
				playerSplit[size(playerSplit)] = deck[deckPos];
				if(playerSplit == null) {
					nextDeckCheck();
					playerSplit[size(playerSplit)] = deck[deckPos];
				}
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
				ui.message("*ERROR choice invalid");
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
	
	public int size(Card[] cards) {
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
		nextDeckCheck();
		playerHand[0] = deck[0];
		deckPos++;
		nextDeckCheck();
		playerHand[1] = deck[1];
		deckPos++;
		nextDeckCheck();
		dealerHand[0] = deck[2];
		deckPos++;
		nextDeckCheck();
		dealerHand[1] = deck[3];
		deckPos++;
		
		ui.message("Dealer hand: ");
		ui.displayDealerHand(dealerHand);
		ui.message("Player hand: ");
		ui.displayHand(playerHand);
	}
	
	private void nextDeckCheck() {
		if(deck[deckPos] == null) {
			ui.message("ERROR not enough cards in file, filling deck");
			initDeck();
		}
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
		String cards = null;
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
		if(cards != null && !cards.isEmpty()) {
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
			if(size(deck) == 0) {
				ui.message("No cards given, filling deck...");
				initDeck();
			}
		}else {
			ui.message("No cards or movements in file");
			return -2;
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

	public void initDeck() {
		deck = new Card[52];
		deckPos = 0;
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
