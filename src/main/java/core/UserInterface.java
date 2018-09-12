package core;

public interface UserInterface {
	public void message(String mes);
	
	public char response(String mes);
	
	public void displayHand(Card[] hand);

	public void displayDealerHand(Card[] hand);
	
	public void outcome(char results);
}
