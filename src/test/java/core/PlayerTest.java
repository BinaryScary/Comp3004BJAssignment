package core;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {

	public void testSplitHit() {
		System.out.println("");
		System.out.println("Player Split Hit test...");
		System.out.println("");

		File file = new File("src/test/resources/testPlayerSplitHit");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		
		assertEquals("ACE HEARTS TWO HEARTS FOUR CLUBS FOUR DIAMONDS", start.cardsToString(start.getPlayerSplit()));
		assertEquals("ACE SPADES FIVE DIAMONDS", start.cardsToString(start.getPlayerHand()));
	}

	public void testPlayerWin() {
		System.out.println("");
		System.out.println("Player Win test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testPlayerWin");
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
	
		assertEquals("Player Won", start.getWinner());
	}

	public void testPlayerBlackJack() {
		System.out.println("");
		System.out.println("Player BlackJack test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testPlayerBlackJack");
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
	
		assertEquals("Player BlackJack", start.getWinner());
	}

	public void testPlayerBust() {
		System.out.println("");
		System.out.println("Player Bust test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testPlayerBust");
		//fills deck if not enough cards are present
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
	
		assertEquals("Player busted", start.getWinner());
	}

	public void testRepeatedHit() {
		System.out.println("");
		System.out.println("Repeated Hit test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testRepeatedHit");
		//fills deck if not enough cards are present
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
	
		assertEquals(4, start.size(start.getPlayerHand()));
	}

	public void testHit() {
		System.out.println("");
		System.out.println("Hit Test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testHit");
		//if not enough cards are given hands are counted as so
	
		Card[] cArr = new Card[11];
		cArr[0] = new Card(Card.rank.ACE, Card.suit.SPADES);
		cArr[1] = new Card(Card.rank.ACE, Card.suit.HEARTS);
		cArr[2] = new Card(Card.rank.SEVEN, Card.suit.DIAMONDS);
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		assertArrayEquals(start.getPlayerHand(), cArr);
	}

	public void testSplit() {
		System.out.println("");
		System.out.println("Split Test...");
		System.out.println("");
	
		File file = new File("src/test/resources/testSplit");
	
		Card[] cArr = new Card[11];
		cArr[0] = new Card(Card.rank.ACE, Card.suit.SPADES);
		cArr[1] = new Card(Card.rank.TEN, Card.suit.DIAMONDS);
		Card[] cArrSplit = new Card[11];
		cArrSplit[0] = new Card(Card.rank.ACE, Card.suit.HEARTS);
		cArrSplit[1] = new Card(Card.rank.FIVE, Card.suit.CLUBS);
	
		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		assertArrayEquals(start.getPlayerHand(), cArr);
		assertArrayEquals(start.getPlayerSplit(), cArrSplit);
	}
}
