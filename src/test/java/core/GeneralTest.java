package core;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.After;

import junit.framework.TestCase;

public class GeneralTest extends TestCase {
	
	public void testIncorrectFile() {
		System.out.println("");
		System.out.println("Score test...");
		System.out.println("");
		
		File file = new File("src/test/resources/");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		
		assertNull(start.getPlayerMoves());
	}

	public void testScore() {
		System.out.println("");
		System.out.println("Score test...");
		System.out.println("");

		File file = new File("src/test/resources/testScore");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();

		assertEquals(start.getHighestValue(start.getPlayerHand()), 12);
		assertEquals(start.getHighestValue(start.getDealerHand()), 20);
	}

	public void testStand() {
		System.out.println("");
		System.out.println("Stand test...");
		System.out.println("");

		File file = new File("src/test/resources/testStand");
		//fills deck if not enough cards are present

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();

		assertNotNull(start.getPlayerHand());
	}

	public void testPlayerCards() {
		System.out.println("");
		System.out.println("Player Cards Test...");
		System.out.println("");

		File file = new File("src/test/resources/testPlayerCards");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		assertNotNull(start.getPlayerHand());
	}
	
	public void testDealerCards() {
		System.out.println("");
		System.out.println("Dealer Cards Test...");
		System.out.println("");

		File file = new File("src/test/resources/testDealerCards");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		assertNotNull(start.getDealerHand());
	}

//	public void testFileChoice() {
//		System.out.println("");
//		System.out.println("File Choice Test...");
//		System.out.println("");
//
//		GameLogic start = new GameLogic('c');
//		start.gameInit();
//		assertNotNull(start.getTestFile());
//	}

	public void testConsole() {
		System.out.println("");
		System.out.println("Console Test...");
		System.out.println("");

		File file = new File("src/test/resources/testConsole");

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		assertNull(start.getPlayerHand());
	}

	public void testFile() {
		System.out.println("");
		System.out.println("File Test...");
		System.out.println("");

		File file = new File("src/test/resources/testFile");
		//if no movements are give player action is requested

		Card[] cArr = new Card[11];
		cArr[0] = new Card(Card.rank.ACE, Card.suit.SPADES);
		cArr[1] = new Card(Card.rank.ACE, Card.suit.HEARTS);

		GameLogic start = new GameLogic('c');
		start.setTestCase(file);
		start.gameInit();
		assertArrayEquals(start.getPlayerHand(), cArr);
	}
}
