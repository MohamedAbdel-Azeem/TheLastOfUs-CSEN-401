package views;

import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HeroInfo extends VBox {

	
	private Hero curr;
	private boolean isSelected; 
	private Image heart = new Image(getClass().getResource("hp.png").toExternalForm());
	private ProgressBar hpBar = new ProgressBar(1);
	
	public HeroInfo(Hero curr) {
		this.curr = curr;
		
		if (GamePage.selected != null)
			isSelected =  (curr.getName().equals(GamePage.selected.getName()));
		
		
		Label name = new Label("Name: "+curr.getName());
		
		String typeString = "";
		if (curr instanceof Fighter){
			typeString = "Fighter";
		}
		else if (curr instanceof Medic)
			typeString = "Medic";
		else
			typeString = "Explorer";
		
		Label type = new Label("Type: "+typeString);
		
		Label pts = new Label("Actions: "+curr.getActionsAvailable());
		
		Label vaccine = new Label("Vaccines: "+curr.getVaccineInventory().size());
		Label supply = new Label("supply: "+curr.getSupplyInventory().size());
		
		
		Image hero = new Image(getClass().getResource(curr.getName()+".png").toExternalForm());
        ImageView heroView = new ImageView(hero);	     
		heroView.setFitHeight(130);
		heroView.setFitWidth(170);
        
		hpBar.setPrefWidth(80);
		hpBar.setPrefHeight(20);
		double progress = curr.getCurrentHp() / (double) curr.getMaxHp();
		hpBar.setProgress(progress);
		
		if (progress >= 0.6)
			hpBar.setStyle("-fx-accent: green;");
		else if (progress >= 0.3)
			hpBar.setStyle("-fx-accent: orange;");
		else
			hpBar.setStyle("-fx-accent: red;");
		
		
		HBox hpBox = new HBox(2); // int value for spacing
		ImageView hpHeart = new ImageView(heart);
		hpHeart.setFitHeight(30);
		hpHeart.setFitWidth(30);
		
		hpBox.setAlignment(Pos.CENTER_LEFT);
        hpBox.getChildren().addAll(hpHeart, hpBar);
		
        hpBox.setCache(true);
        
        getChildren().add(heroView);
		getChildren().add(name);
		getChildren().add(type);
		getChildren().add(hpBox);
		getChildren().add(pts);
		getChildren().add(vaccine);
		getChildren().add(supply);
		
		
		
		
		getStyleClass().add("heroInfo");
		getStyleClass().add(isSelected? "selectedHero" : "");
		
		if (isSelected && curr.isSpecialAction() && curr instanceof Fighter)
			getStyleClass().add(isSelected? "selectedHeroFighterSpecial" : "");
		
		
		
		
		setPrefWidth(200);
		
	}
	
	
	public Hero getHero(){
		return curr;
	}
	
}
