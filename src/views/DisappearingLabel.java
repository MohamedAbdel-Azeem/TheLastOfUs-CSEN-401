package views;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DisappearingLabel extends Label {

	public DisappearingLabel(String message , Pane parent){
		setText(message);
		parent.getChildren().add(this);
		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(event -> {
            // Remove the label from its parent container
			parent.getChildren().remove(this);
        });
		pause.play();
		
		getStyleClass().add("trapMessage");
	}
	
	
	
}
