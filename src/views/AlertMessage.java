package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AlertMessage extends VBox {

	private String message;
	
	public AlertMessage(String message , Pane parent) {
		this.message = message;
		
		Label l = new Label(message);
		Button close = new Button("Close");
		
		close.setOnAction(event -> {
			parent.getChildren().remove(this);
		});
		
		setMaxSize(700.0, 200.0);
		setMinSize(500.0, 200.0);
		
		setAlignment(Pos.CENTER);
		
		setVisible(true);
		getChildren().add(l);
		getChildren().add(close);
		
		getStyleClass().add("alertMessage");
		getStyleClass().add("alertMessage .label");
		getStyleClass().add("alertMessageButton");
	}
	
}
