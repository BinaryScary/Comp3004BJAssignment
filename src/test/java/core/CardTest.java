package core;
import junit.framework.TestCase;

public class CardTest extends TestCase {
	public void testCardRank() {
		Card c1 = new Card(Card.rank.ACE,Card.suit.DIAMONDS);
		assertEquals(Card.rank.ACE, c1.getRank());
		c1.setRank(Card.rank.TEN);
		assertEquals(Card.rank.TEN, c1.getRank());
	}

	public void testCardSuit() {
		Card c1 = new Card(Card.rank.ACE,Card.suit.DIAMONDS);
		assertEquals(Card.suit.DIAMONDS, c1.getSuit());
		c1.setSuit(Card.suit.HEARTS);
		assertEquals(Card.suit.HEARTS, c1.getSuit());
	}

	public void testCardColour() {
		Card c1 = new Card(Card.rank.ACE,Card.suit.DIAMONDS);
		assertEquals('R', c1.getColour());
	}
}
