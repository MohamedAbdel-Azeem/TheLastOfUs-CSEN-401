package engine;

import java.io.IOException;

import model.characters.Direction;
import views.LobbyPage;
import views.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

	
    private static final String CSS_URL = "styles.css";
	private static final String AUDIO_URL = "menuAudio.mp3";
	
	static Pane root;
	public static Scene scene;
	public static MediaPlayer audioPlayer;
	private static double stageHeight;
	
	public Game game;
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		Main main = new Main();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		game = new Game();
		Menu menuUI = new Menu();
		root = menuUI.getRoot();
		Game.loadHeroes("Heroes.csv");
		
		// adding background audio
		Media audio = new Media(getClass().getResource(AUDIO_URL).toExternalForm());
        audioPlayer = new MediaPlayer(audio);
        audioPlayer.play();
        audioPlayer.setOnEndOfMedia(() -> audioPlayer.seek(javafx.util.Duration.ZERO));
		
		scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource(CSS_URL).toExternalForm());
		stage.setScene(scene);
		stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("ALT+F4"));
        stage.show();
        stageHeight = stage.getHeight();
	}
		
	
	public static double getStageHeight(){
		return stageHeight;
	}
	
}
