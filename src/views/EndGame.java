package views;


import model.characters.Hero;
import engine.Game;
import engine.Main;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class EndGame extends AnchorPane{

	
	Image GameOver = new Image(getClass().getResource("GameOver.png").toExternalForm());
	Image Victory = new Image(getClass().getResource("victory.png").toExternalForm());
 	ImageView image = new ImageView(GameOver);
	
 	private String url_audioLose = "audioLose.mp3";
 	private String url_audioWin = "audioWin.mp3";
 	
 	
	public EndGame(boolean didWin){ // didWin = true -> Game Win , didWin = false -> Game losed
	
		Button exitGame = new Button("Exit Game!");

		exitGame.getStyleClass().add("styled-button");
		
		
		Media audio = new Media(getClass().getResource(didWin? url_audioWin : url_audioLose).toExternalForm());
        Main.audioPlayer.stop();
		Main.audioPlayer = new MediaPlayer(audio);
        Main.audioPlayer.play();
        Main.audioPlayer.setOnEndOfMedia(() -> Main.audioPlayer.seek(javafx.util.Duration.ZERO));
		
		
		if (!didWin){ // lost game
		image = new ImageView(GameOver);
		image.setFitHeight(Main.scene.getHeight());
		image.setFitWidth(Main.scene.getWidth());
		getChildren().add(image);
		AnchorPane.setBottomAnchor(exitGame, (double) 150);
		AnchorPane.setLeftAnchor(exitGame, (double) 150);
		
	}
		else{ //won game
			setStyle("-fx-background-color: black;");
			image = new ImageView(Victory);
			getChildren().add(image);
			AnchorPane.setTopAnchor(image, 90.0);
			AnchorPane.setLeftAnchor(image, 515.0);
		
			GridPane heroes = new GridPane(); 
			int i = 0;
			for (Hero h : Game.heroes){
				Image hero = new Image(getClass().getResource(h.getName()+".png").toExternalForm());
				ImageView heroImg = new ImageView(hero);
				heroImg.setFitHeight(400);
				heroImg.setFitWidth(300);
				heroes.add(heroImg, i, 0);
				i++;
			}
		
			AnchorPane.setBottomAnchor(heroes, 300.0);
			AnchorPane.setLeftAnchor(heroes, 70.0);
			getChildren().add(heroes);
			AnchorPane.setBottomAnchor(exitGame, (double) 150);
			AnchorPane.setLeftAnchor(exitGame, (double) 870);
		}

	

	

		exitGame.setOnAction(event -> Platform.exit());
	

		getChildren().add(exitGame);

	

	
		setVisible(true);
	}
	
	
}
