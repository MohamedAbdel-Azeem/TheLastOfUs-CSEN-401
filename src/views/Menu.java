package views;

import java.io.File;

import engine.Game;
import engine.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Menu {
	
	private static final String MEDIA_URL = "BackgroundVideo.mp4";

    private static final String AUDIO_URL = "menuAudio.mp3";
    private StackPane root;    
    
    public StackPane getRoot(){
    	return root;
    }

	public Menu() throws Exception {
		
		
		// adding Background video
        Media Video = new Media(getClass().getResource(MEDIA_URL).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(Video);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);
        
        
        Description descPage = new Description();

        
        
        Button startButton = new Button("Start Game");
        startButton.getStyleClass().add("styled-button");
        
        Button descriptionButton = new Button("Game Description");
        descriptionButton.getStyleClass().add("styled-button");
       
        
        
        Button ExitButton = new Button("Exit Game");
        ExitButton.getStyleClass().add("styled-button");
        ExitButton.setOnAction(event -> Platform.exit());
        
        root = new StackPane();
        VBox buttons = new VBox();
        
        
        
        buttons.getStyleClass().add("buttons");
        
        root.getChildren().add(mediaView);
        buttons.getChildren().add(startButton);
        buttons.getChildren().add(descriptionButton);
        buttons.getChildren().add(ExitButton);
        root.getChildren().add(buttons);
        root.getChildren().add(descPage.getVBox());
        
        descriptionButton.setOnAction(event -> descPage.Open(buttons));
        descPage.getButton().setOnAction(event -> descPage.close(buttons));
        
       
        startButton.setOnAction(event -> {
            LobbyPage lobby = new LobbyPage();
			Main.scene.setRoot(lobby.getRoot());
        });
       
        
        
    }
   
}
