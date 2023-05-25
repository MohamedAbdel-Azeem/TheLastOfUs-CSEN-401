package views;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import engine.Game;
import engine.Main;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LobbyPage {

	private static final String IMAGE_URL = "lobby.png";
    
    Game game;
	
    int listIndex = 0;
    
    private AnchorPane root = new AnchorPane();
    private VBox heroHolder = new VBox();
	private VBox attributesHolder = new VBox();
    
    
    ArrayList<Hero> availableHeroes = Game.availableHeroes;
    Hero selected;
    
    private static final String AUDIO_URL = "menuAudio.mp3";
    private MediaPlayer audioPlayer;
    
    public LobbyPage() {
		
    	
    	// background image
    	Image image = new Image(getClass().getResource(IMAGE_URL).toExternalForm());
        ImageView imageView = new ImageView(image);	        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        imageView.setFitWidth(screenBounds.getWidth());
        imageView.setFitHeight(screenBounds.getHeight()+80);
	        
        //initial hero added
        Image heroImg = new Image(getClass().getResource(availableHeroes.get(0).getName()+".png").toExternalForm());
        ImageView heroView = new ImageView(heroImg);
        
        Image attributeImg = new Image(getClass().getResource(availableHeroes.get(0).getName()+"-attributes.png").toExternalForm());
        ImageView attributeView = new ImageView(attributeImg);
        
	        
        heroView.setFitHeight(700);
        heroView.setFitWidth(600);
        attributeView.setFitHeight(500);
        attributeView.setFitWidth(900);
        
        
        heroHolder.getChildren().add(heroView);
        attributesHolder.getChildren().add(attributeView);
        
        AnchorPane.setBottomAnchor(heroHolder, 450.0);
        AnchorPane.setRightAnchor(heroHolder, 700.0);
        AnchorPane.setBottomAnchor(attributesHolder, 90.0);
	    AnchorPane.setRightAnchor(attributesHolder, 550.0);
 	        
        
        //Buttons: Pick Hero , LeftHero , RightHero        
        Button pickHero = new Button("Pick Hero");
        pickHero.setPrefSize(100, 100);
        Button leftHero = new Button();
        Button rightHero = new Button();
	        		      
        leftHero.setOnAction(event -> leftArrow());
        rightHero.setOnAction(event -> rightArrow());
	    pickHero.setOnAction(event -> pickOnClick());
        
        //AnchorPane Setup
	       
        root.getChildren().add(imageView);
        
        
        root.getChildren().add(attributesHolder);
        root.getChildren().add(heroHolder);
        root.getChildren().add(leftHero);
        root.getChildren().add(rightHero);
        root.getChildren().add(pickHero);
        // styling pick hero button
	        
        AnchorPane.setBottomAnchor(pickHero, 75.0);
        AnchorPane.setRightAnchor(pickHero, 885.0);
        pickHero.getStyleClass().add("pick-button");
	        
        // styling right & left buttons
	        
        AnchorPane.setBottomAnchor(leftHero, 700.0);
        AnchorPane.setLeftAnchor(leftHero, 670.0);
        AnchorPane.setBottomAnchor(rightHero, 700.0);
        AnchorPane.setRightAnchor(rightHero, 770.0);
        leftHero.getStyleClass().add("triangle-button"); leftHero.getStyleClass().add("left-triangle");
        rightHero.getStyleClass().add("triangle-button"); rightHero.getStyleClass().add("right-triangle");
	    
	    
	}
	
	public AnchorPane getRoot(){
		return root;
	}
	
	public void updateHero(Hero curr){
		heroHolder.getChildren().remove(0);
		attributesHolder.getChildren().remove(0);
		
		Image heroImg = new Image(getClass().getResource(curr.getName()+".png").toExternalForm());
        ImageView heroView = new ImageView(heroImg);
        
        Image attributeImg = new Image(getClass().getResource(curr.getName()+"-attributes.png").toExternalForm());
        ImageView attributeView = new ImageView(attributeImg);
        
        heroView.setFitHeight(700);
        heroView.setFitWidth(600);
        
        attributeView.setFitHeight(500);
        attributeView.setFitWidth(900);
        
        heroHolder.getChildren().add(heroView);
        attributesHolder.getChildren().add(attributeView);
        
	}
	

	
	
	public void rightArrow(){
		if (listIndex==availableHeroes.size()-1)
			listIndex=0;
		else 
			listIndex++;
		updateHero(availableHeroes.get(listIndex));
		
	}
	
	public void leftArrow(){
		if (listIndex==0)
			listIndex=availableHeroes.size()-1;
		else 
			listIndex--;
		updateHero(availableHeroes.get(listIndex));
	}
	
	public void pickOnClick(){
		for (Node element : root.getChildren()){
			if (element instanceof Button)
				element.setVisible(false);
		}
		
		selected = availableHeroes.get(listIndex);
		
		if (selected.getName().equals("David")){
			Node tmp = heroHolder.getChildren().remove(0);
			
			Image gif = new Image(getClass().getResource("jimmyAttacksgif.gif").toExternalForm());
			ImageView gifView = new ImageView(gif);
			gifView.setFitHeight(600);
			gifView.setFitWidth(470);
			AnchorPane.setLeftAnchor(heroHolder, 660.0);
			AnchorPane.setBottomAnchor(heroHolder, 460.0);
			heroHolder.getChildren().add(gifView);
		}
		
		Media audio = new Media(getClass().getResource("onStartAudio.mp3").toExternalForm());
		
		Main.audioPlayer.stop();
		Main.audioPlayer = new MediaPlayer(audio);
		Main.audioPlayer.play();
		Main.audioPlayer.setOnEndOfMedia(()->{
			Game.startGame(selected);
				GamePage gamePage = new GamePage();
				Main.scene.setRoot(gamePage.getRoot());
			});
	}
	
	
	
	
		
}
