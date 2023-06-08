package views;

import java.util.ArrayList;

import com.sun.javafx.scene.traversal.Direction;

import model.characters.Explorer;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import engine.Game;
import engine.Main;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;

public class GamePage {

	private Game game;
	private StackPane map;
	private BorderPane root;
	private GridPane mapCell;
	private AnchorPane hud;
	static Hero selected;
	private InfoHolder heroesHud;
	public static boolean didAzombieAttack = false;
	
	private ArrayList<ImageView> ZombieBtnsImages = new ArrayList<ImageView>(); 
	
	private static final String attack_audio = "gunShot.mp3";
	private static final String fighterSpecial_audio = "fighterSpecial.mp3";
	private static final String explorerSpecial_audio = "explorerSpecial.mp3";
	private static final String medicSpecial_audio = "medicSpecial.mp3";
	private static final String clicker_audio = "clicker.mp3";
	
	private Button attackedZombiePointer = null; 
	
	Image normal = new Image(getClass().getResource("Normal.png").toExternalForm());
	Image supplyImg = new Image(getClass().getResource("Supply.png").toExternalForm());
	Image vaccineImg = new Image(getClass().getResource("Vaccine.png").toExternalForm());
	Image fogImg = new Image(getClass().getResource("Fog.png").toExternalForm());
	Image zombie = new Image(getClass().getResource("zombie.png").toExternalForm());
	Image zombieAttacked = new Image(getClass().getResource("zombie-attacked.png").toExternalForm());
	Image indicator = new Image(getClass().getResource("indicatorZombie.png").toExternalForm());

	
	Image Bill = new Image(getClass().getResource("Bill.png").toExternalForm());
	Image Joel = new Image(getClass().getResource("Joel Miller.png").toExternalForm());
	Image Ellie = new Image(getClass().getResource("Ellie Williams.png").toExternalForm());
	Image Tess = new Image(getClass().getResource("Tess.png").toExternalForm());
	Image Riley = new Image(getClass().getResource("Riley Abel.png").toExternalForm());
	Image Tommy = new Image(getClass().getResource("Tommy Miller.png").toExternalForm());
	Image David = new Image(getClass().getResource("David.png").toExternalForm());
	Image Henry = new Image(getClass().getResource("Henry Burell.png").toExternalForm());
	
	
	Media zombieDead = new Media(getClass().getResource("zombieDeadSound.mp3").toExternalForm());
	Media attackAudio = new Media(getClass().getResource(attack_audio).toExternalForm());
	
	
	public GamePage(){
		root = new BorderPane();
		map = new StackPane();
		mapCell = new GridPane();
		map.getChildren().add(mapCell);
		hud = new AnchorPane();
		updateMap();
		root.setCenter(map);
		hud.setVisible(true);
		mapCell.setAlignment(Pos.CENTER_LEFT);
		mapCell.setGridLinesVisible(true);
		mapCell.getStyleClass().add("custom-grid");
		heroesHud = new InfoHolder();
		
		Image hudImage = new Image(getClass().getResource("hudImage.jpg").toExternalForm());
		ImageView hudView = new ImageView(hudImage);
		hudView.setFitWidth(570.0);
		hudView.setFitHeight(1080.0);
		hud.getChildren().add(hudView);
		hud.setPrefWidth(570.0);
		Button endTurnButton = new Button("End Turn");
		Button helpButton = new Button("Help");
		endTurnButton.getStyleClass().add("endturn");
		helpButton.getStyleClass().add("endturn");
		helpButton.setPrefSize(160, endTurnButton.getHeight());
		hud.getChildren().add(endTurnButton);
		hud.getChildren().add(helpButton);
		hud.getChildren().add(heroesHud.getRoot());
		
		endTurnButton.setOnAction(event -> {
			try {
				Game.endTurn();
				updateMap();
				heroesHud.updateInfo();
			
				if (didAzombieAttack){
					Media audio = new Media(getClass().getResource(clicker_audio).toExternalForm());
			        Main.audioPlayer = new MediaPlayer(audio);
			        Main.audioPlayer.play();
			        didAzombieAttack = false;
			}
				
				
			} catch (Exception e) {
				
			}
			if (Game.checkGameOver()){
				Main.scene.setRoot(new EndGame(false));
				System.out.println("Game Lost!");
			}
		});
		
		helpButton.setOnAction(event -> {
			AlertMessage help = new AlertMessage(map);
			map.getChildren().add(help);
		});
		
		AnchorPane.setBottomAnchor(helpButton, 170.0);
		AnchorPane.setRightAnchor(helpButton, 200.0);
		AnchorPane.setBottomAnchor(endTurnButton, 100.0);
		AnchorPane.setRightAnchor(endTurnButton, 200.0);
		onKeyPress();
		root.setRight(hud);
	}
	
	
	public void onKeyPress(){
		root.setOnKeyPressed(event -> {
			KeyCode keyCode = event.getCode();
			if (selected == null){
				AlertMessage alert = new AlertMessage("No hero selected to take action", map);
				if (map.getChildren().size()<2)
					map.getChildren().add(alert);
			}
			else {
			Media Trapaudio = new Media(getClass().getResource("trap-sound.mp3").toExternalForm());
			if (keyCode == KeyCode.W){
				try{
					selected.move(model.characters.Direction.RIGHT); // Right corresponds to Upwards on screen
					updateMap();
					if (selected.trapActivated){
						Main.audioPlayer.stop();
						Main.audioPlayer = new MediaPlayer(Trapaudio);
						Main.audioPlayer.play();
						DisappearingLabel trapNotification = new DisappearingLabel("Ouch, you stepped into a trap!", map);
						StackPane.setAlignment(trapNotification, javafx.geometry.Pos.TOP_CENTER);
						StackPane.setMargin(trapNotification, new javafx.geometry.Insets(35));
					}
					
				}
				catch (Exception e){
					if (e instanceof GameActionException){
						AlertMessage alert = new AlertMessage(e.getMessage(), map);
						if (map.getChildren().size()<2)
							map.getChildren().add(alert);
					
				}
					}
			}
			if (keyCode == KeyCode.S){
				try{
					selected.move(model.characters.Direction.LEFT); // Left corresponds to Down on screen
					updateMap();
					if (selected.trapActivated){
						Main.audioPlayer.stop();
						Main.audioPlayer = new MediaPlayer(Trapaudio);
						Main.audioPlayer.play();
						DisappearingLabel trapNotification = new DisappearingLabel("Ouch, you stepped into a trap!", map);
						StackPane.setAlignment(trapNotification, javafx.geometry.Pos.TOP_CENTER);
						StackPane.setMargin(trapNotification, new javafx.geometry.Insets(35));
					}
				}
				catch (Exception e){
					if (e instanceof GameActionException){
						AlertMessage alert = new AlertMessage(e.getMessage(), map);
						if (map.getChildren().size()<2)
							map.getChildren().add(alert);
					
				}
				}
			}
			if (keyCode == KeyCode.A){
				try{
					selected.move(model.characters.Direction.DOWN); // Down corresponds to Left on screen
					updateMap();
					if (selected.trapActivated){
						Main.audioPlayer.stop();
						Main.audioPlayer = new MediaPlayer(Trapaudio);
						Main.audioPlayer.play();
						DisappearingLabel trapNotification = new DisappearingLabel("Ouch, you stepped into a trap!", map);
						StackPane.setAlignment(trapNotification, javafx.geometry.Pos.TOP_CENTER);
						StackPane.setMargin(trapNotification, new javafx.geometry.Insets(35));
					}
						
				}
				catch (Exception e){
					if (e instanceof GameActionException){
						AlertMessage alert = new AlertMessage(e.getMessage(), map);
						if (map.getChildren().size()<2)
							map.getChildren().add(alert);
					
				}
				}
			}
			if (keyCode == KeyCode.D){
				try{
					selected.move(model.characters.Direction.UP); // UP corresponds to right on screen
					updateMap();
					if (selected.trapActivated){
						Main.audioPlayer.stop();
						Main.audioPlayer = new MediaPlayer(Trapaudio);
						Main.audioPlayer.play();
						DisappearingLabel trapNotification = new DisappearingLabel("Ouch, you stepped into a trap!", map);
						StackPane.setAlignment(trapNotification, javafx.geometry.Pos.TOP_CENTER);
						StackPane.setMargin(trapNotification, new javafx.geometry.Insets(35));
					}
				}
				catch (Exception e){
					if (e instanceof GameActionException){
						AlertMessage alert = new AlertMessage(e.getMessage(), map);
						if (map.getChildren().size()<2)
							map.getChildren().add(alert);
					
				}
				}
			}
			if (keyCode == KeyCode.Q){
				try {
					Zombie z = (Zombie) selected.getTarget();
					if (z.getCurrentHp() != 0){
						selected.attack();
						
				        Main.audioPlayer = new MediaPlayer(attackAudio);
				        Main.audioPlayer.play();
				        
				        ImageView old = (ImageView) attackedZombiePointer.getGraphic();
				        ImageView attacked = new ImageView(zombieAttacked);
				        attacked.setFitHeight(72);
				        attacked.setFitWidth(90);
				        
				        Timeline timeline = new Timeline(
				        	    new KeyFrame(Duration.ZERO, e -> attackedZombiePointer.setGraphic(attacked)),
				        	    new KeyFrame(Duration.seconds(0.25), e -> attackedZombiePointer.setGraphic(old))
				        	);
				        timeline.play();
				        
						if (selected.getCurrentHp() == 0){
							 ImageView normalView = new ImageView(normal);
							normalView.setFitHeight(72);
					        normalView.setFitWidth(90);
					        mapCell.add(normalView, selected.getLocation().x , 14- selected.getLocation().y);
					        
						}
					}
					else{ // zombie is dead
						ImageView normalView = new ImageView(normal);
						normalView.setFitHeight(72);
				        normalView.setFitWidth(90);
				        mapCell.add(normalView, z.getLocation().x , 14-z.getLocation().y);
						selected.setTarget(null);
				        Main.audioPlayer = new MediaPlayer(zombieDead);
				        Main.audioPlayer.play();
					}
				} catch (Exception e) {
					if (selected.getTarget() == null){
						AlertMessage alert = new AlertMessage("No Target Selected!", map);
						if (map.getChildren().size()<2)
							map.getChildren().add(alert);
					}
						
					if (e instanceof GameActionException){
						AlertMessage alert = new AlertMessage(e.getMessage(), map);
						if (map.getChildren().size()<2)
							map.getChildren().add(alert);
					
				}
				}
				if (Game.checkGameOver()){
					Main.scene.setRoot(new EndGame(false));
					System.out.println("Game Over");
				}
			}
			
			if (keyCode == KeyCode.E){
				try {
					selected.cure();
					updateMap();
					
					if (Game.checkWin()){
						Main.scene.setRoot(new EndGame(true));
					}
					
				} catch (Exception e) {
					if (e instanceof GameActionException){
						AlertMessage alert = new AlertMessage(e.getMessage(), map);
						if (map.getChildren().size()<2)
							map.getChildren().add(alert);
					
				}
			}
			}
			
			if (keyCode == KeyCode.R){
				try {
					selected.useSpecial();
					Media audio = new Media(getClass().getResource(explorerSpecial_audio).toExternalForm());
					if (selected instanceof Explorer){
						updateMap();
						audio = new Media(getClass().getResource(explorerSpecial_audio).toExternalForm());
					}
					else {
						if (selected instanceof Medic){
							audio = new Media(getClass().getResource(medicSpecial_audio).toExternalForm());
						}
						else{
							audio = new Media(getClass().getResource(fighterSpecial_audio).toExternalForm());
						}
					}
			        Main.audioPlayer = new MediaPlayer(audio);
			        Main.audioPlayer.play();
						
				} catch (Exception e) {
					if (e instanceof GameActionException){
						AlertMessage alert = new AlertMessage(e.getMessage(), map);
						if (map.getChildren().size()<2)
							map.getChildren().add(alert);		
					}
				}
			}
			}
			
			heroesHud.updateInfo();
			});
	}

	
	
	
	public  void updateMap(){
		ZombieBtnsImages.clear();
		mapCell.getChildren().clear();
		int height = 72;
		int width = 90;
		for (int i = 0 ; i < Game.map.length ; i++){
			for (int j = 0 ; j < Game.map[i].length ; j++){
				if (!Game.map[i][j].isVisible()){
			        ImageView fogView = new ImageView(fogImg);
			        fogView.setFitHeight(height);
			        fogView.setFitWidth(width);
					mapCell.add(fogView, i, 14-j);
				}else{ // it's Visible
					Cell cell = Game.map[i][j];
					if (cell instanceof CollectibleCell){ // either Vaccine or Supply
						if (((CollectibleCell) cell).getCollectible() instanceof Vaccine){	
					        ImageView vaccineView = new ImageView(vaccineImg);
					        vaccineView.setFitHeight(height);
					        vaccineView.setFitWidth(width);
							mapCell.add(vaccineView, i, 14-j);
						}
						else{ // else Supply
					        ImageView supplyView = new ImageView(supplyImg);
					       supplyView.setFitHeight(height);
					        supplyView.setFitWidth(width);
							mapCell.add(supplyView, i, 14-j);
						}
					}
					else{					
						if (cell instanceof TrapCell){
				        ImageView normalView = new ImageView(normal);
						normalView.setFitHeight(height);
				        normalView.setFitWidth(width);
				        mapCell.add(normalView, i, 14-j);
					}
					else{ // Character Cell
				        ImageView normalView = new ImageView(normal);
						normalView.setFitHeight(height);
				        normalView.setFitWidth(width);
						if (((CharacterCell) cell).getCharacter() == null){ // empty Cell
							mapCell.add(normalView, i, 14-j);
						}else{
							StackPane currCell = new StackPane();
							currCell.getChildren().add(normalView);
							if (((CharacterCell) cell).getCharacter() instanceof Hero){ // hero
								Button theHero = new Button();
						        
						        ImageView heroImageView = getHeroImageView(((CharacterCell) cell).getCharacter().getName());                
						        heroImageView.setFitWidth(width+35);
						        heroImageView.setFitHeight(height+35);
						        theHero.setGraphic(heroImageView);
						        theHero.setMinHeight(height);
						        theHero.setMinWidth(width);
						        theHero.setMaxHeight(height);
						        theHero.setMaxWidth(width);
						        theHero.setStyle("-fx-background-color: transparent;");
						        currCell.getChildren().add(theHero); // made the Hero cell a button so when on pressed something occurs
						        
						        theHero.setOnMouseClicked(event -> {
						        		if (event.getButton() == MouseButton.PRIMARY) {
						        			// Left-click action
						        			selected = (Hero) ((CharacterCell) cell).getCharacter();
						    				heroesHud.updateInfo();
						    				
						        		} else if (event.getButton() == MouseButton.SECONDARY) {
						        			// Right-click action
						        			if (selected != null){
						        				selected.setTarget(((CharacterCell) cell).getCharacter());
						        			}
						        			else{
						        				AlertMessage alert = new AlertMessage("No hero selected to set target", map);
						    					map.getChildren().add(alert);
						        			}
						        		}
						        	});
							}
							else{ // Zombie
						        ImageView zombieView = new ImageView(zombie);
						        Button theZombie = new Button();
						        ZombieBtnsImages.add(zombieView);
								zombieView.setFitHeight(height);
						        zombieView.setFitWidth(width);
						        theZombie.setGraphic(zombieView);
						        theZombie.setMinHeight(height);
						        theZombie.setMinWidth(width);
						        theZombie.setMaxHeight(height);
						        theZombie.setMaxWidth(width);
						        theZombie.setStyle("-fx-background-color: transparent;");
						        currCell.getChildren().add(theZombie);
						        	theZombie.setOnMouseClicked(event -> {
						        	if (event.getButton() == MouseButton.SECONDARY) {
						                // Right-click action
						            	if (selected != null){
						            		for (ImageView z : ZombieBtnsImages)
						            			z.setImage(zombie);
						            		selected.setTarget(((CharacterCell) cell).getCharacter());
						            		zombieView.setImage(indicator);
						            		attackedZombiePointer = theZombie;
						            	}
						            	else{
					        				AlertMessage alert = new AlertMessage("No hero selected to set target", map);
					    					map.getChildren().add(alert);
						            	}
						            }
						        	});
							}
							mapCell.add(currCell, i, 14-j);
						}

					}
				}
			}
		}
		}
	}
	
	
	public ImageView getHeroImageView(String s){
		if (s.contains("Bill"))
			return new ImageView(Bill);
		if (s.contains("Ellie"))
			return new ImageView(Ellie);
		if (s.contains("Joel"))
			return new ImageView(Joel);
		if (s.contains("David"))
			return new ImageView(David);
		if (s.contains("Riley"))
			return new ImageView(Riley);
		if (s.contains("Tess"))
			return new ImageView(Tess);
		if (s.contains("Tommy"))
			return new ImageView(Tommy);
		return new ImageView(Henry);
	}
	
	
	public BorderPane getRoot(){
		return root;
	}
	
}
