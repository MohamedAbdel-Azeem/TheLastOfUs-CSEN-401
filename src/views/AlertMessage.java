package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AlertMessage extends VBox {

	private Image info = new Image(getClass().getResource("info.png").toExternalForm());

	
	public AlertMessage(String message , Pane parent) {

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
	
	public AlertMessage(Pane parent){
		ImageView infoView = new ImageView(info);
		infoView.setFitHeight(270);
		Button close = new Button("Close");
		
		close.setOnAction(event -> {
			parent.getChildren().remove(this);
		});
		setMaxSize(700.0, 400.0);
		setMinSize(500.0, 400.0);
		
		setAlignment(Pos.CENTER);
		
		getChildren().add(infoView);
		getChildren().add(close);
		
		getStyleClass().add("alertMessage");
		getStyleClass().add("alertMessageButton");
		setVisible(true);
	}
	
}
