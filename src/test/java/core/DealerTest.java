package core;

import java.io.File;

import junit.framework.TestCase;

public class DealerTest extends TestCase {
	
	public void testSplitHit() {
		System.out.println("");
		System.out.println("Dealer Split Hit test...");
		System.out.println("");

		File file = new File("src/test/resources/testDealerSplitHit");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		
		assertEquals("ACE SPADES EIGHT CLUBS", start.cardsToString(start.getDealerSplit()));
		assertEquals("ACE SPADES TEN DIAMONDS", start.cardsToString(start.getDealerHand()));
	}

	public void testDealerSplit() {
		System.out.println("");
		System.out.println("Dealer Split test...");
		System.out.println("");

		File file = new File("src/test/resources/testDealerSplit");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		
		assertEquals("ACE SPADES SEVEN CLUBS", start.cardsToString(start.getDealerSplit()));
	}

	public void testDealerWin() {
		System.out.println("");
		System.out.println("Dealer Win test...");
		System.out.println("");

		File file = new File("src/test/resources/testDealerWin");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();

		assertEquals("Dealer Won", start.getWinner());
	}

	public void testDealerPlayerBlackJack() {
		System.out.println("");
		System.out.println("Dealer Player BlackJack test...");
		System.out.println("");

		File file = new File("src/test/resources/testDealerPlayerBlackJack");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();

		assertEquals("Dealer BlackJack", start.getWinner());
	}

	public void testDealerRepeatedHit() {
		System.out.println("");
		System.out.println("Dealer Repeated Hit test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testDealerRepeatedHit");
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
	
		assertEquals("ACE SPADES FIVE SPADES SIX DIAMONDS TWO HEARTS JACK DIAMONDS", start.cardsToString(start.getDealerHand()));
	}

	public void testDealerBlackJack() {
		System.out.println("");
		System.out.println("Dealer BlackJack test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testDealerBlackJack");
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
	
		assertEquals("Dealer BlackJack", start.getWinner());
	}

	public void testDealerHardHit() {
		System.out.println("");
		System.out.println("Dealer Hard Hit test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testDealerHardHit");
		//fills deck if not enough cards are present
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
	
		assertEquals("ACE SPADES SIX SPADES FOUR DIAMONDS", start.cardsToString(start.getDealerHand()));
	}

	public void testDealerHit() {
		System.out.println("");
		System.out.println("Dealer Hit test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testDealerHit");
		//fills deck if not enough cards are present
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		//System.out.println(start.cardsToString(start.getDealerHand()));
	
		assertEquals("THREE SPADES SIX SPADES TEN DIAMONDS", start.cardsToString(start.getDealerHand()));
	}

	public void testDealerBust() {
		System.out.println("");
		System.out.println("Dealer Bust test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testDealerBust");
		//fills deck if not enough cards are present
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
	
		assertEquals("Dealer busted", start.getWinner());
	}

}
