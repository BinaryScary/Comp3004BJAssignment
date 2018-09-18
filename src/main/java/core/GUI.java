package core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUI implements UserInterface {
	int width;
	int height;
	Pane canvas;
	Scene scene;
	Label message;

	public GUI() {
		initGUI();
	}
	
	public void message(String mes) {
		System.out.println("called");
		message = new Label(mes);
		message.setFont(Font.font("Serif", FontWeight.NORMAL, 30));
		message.relocate(width /6 - 75, height - 50);
		canvas.getChildren().addAll(message);

	}

	public char response(String mes) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String responseFile(String mes) {
		// TODO Auto-generated method stub
		return null;
	}

	public void displayHand(Card[] hand) {
		// TODO Auto-generated method stub

	}

	public void displayDealerHand(Card[] hand) {
		// TODO Auto-generated method stub

	}

	public void outcome(char results) {
		// TODO Auto-generated method stub

	}
	
	public Pane getCanvas() {
		return canvas;
	}

	private void initGUI() {
		width = 1000;
		height = 700;

		canvas = new Pane();
		canvas.setStyle("-fx-background-color: #006400");
		
		Label label = new Label("BlackJack");
		label.setFont(Font.font("Serif", FontWeight.NORMAL, 30));
		label.relocate(width /2 - 75, 20);
		canvas.getChildren().addAll(label);
	}

}
