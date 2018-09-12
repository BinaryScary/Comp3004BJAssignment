package core;

import java.util.Scanner;

public class CLI implements UserInterface {

	public void message(String mes) {
		System.out.println(mes);
	}

	public char response(String mes) {
		System.out.println(mes);
		
		Scanner userInput = new Scanner(System.in);
		char u = userInput.next().charAt(0);
		userInput.close();

		return u;
	}

	public void displayHand(Card[] hand) {
		String cliHand = "";
		for(int i = 0; i < hand.length; i++) {
			cliHand += "[" + hand[i].getRank() + " of " + hand[i].getSuit() + "] ";
		}
		
		System.out.println(cliHand);
	}

	public void outcome(char results) {
		if(results == 'd') {
			System.out.println("The Dealer has won!");
		}else if(results == 'p') {
			System.out.println("The Player has won!");
		}else if(results == 't') {
			System.out.println("It is a draw!");
		}
	}

	public void displayDealerHand(Card[] hand) {
		String cliHand = "";
		if(hand.length > 0) {
			cliHand += "[" + hand[0].getRank() + " of " + hand[0].getSuit() + "] [***** of *****]";
		}	

		System.out.println(cliHand);
	}

}
