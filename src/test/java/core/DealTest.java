package core;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import junit.framework.TestCase;

public class DealTest extends TestCase {
	public void testHit() {
		File file = new File("src/main/resources/testHit");
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
		File file = new File("src/main/resources/testSplit");
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
