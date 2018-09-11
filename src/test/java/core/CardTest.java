package core;
import junit.framework.TestCase;

public class cardTest extends TestCase {
	public void testCardRank() {
		card c1 = new card(card.rank.ACE,card.suit.DIAMONDS);
		assertEquals(card.rank.ACE, c1.getRank());
		c1.setRank(card.rank.TEN);
		assertEquals(card.rank.TEN, c1.getRank());
	}

	public void testCardSuit() {
		card c1 = new card(card.rank.ACE,card.suit.DIAMONDS);
		assertEquals(card.suit.DIAMONDS, c1.getSuit());
		c1.setSuit(card.suit.HEARTS);
		assertEquals(card.suit.HEARTS, c1.getSuit());
	}

	public void testCardColour() {
		card c1 = new card(card.rank.ACE,card.suit.DIAMONDS);
		assertEquals('R', c1.getColour());
	}
}
