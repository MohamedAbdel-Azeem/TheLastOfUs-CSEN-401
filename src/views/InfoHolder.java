package views;

import model.characters.Hero;
import model.characters.Medic;
import engine.Game;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class InfoHolder{
	
	private GridPane root;
	
	public InfoHolder() {
		root = new GridPane();
		updateInfo();
	}
	
	
	public void updateInfo(){
		
		int i = 0;
		int j = 0;
		root.getChildren().clear();
		for (Hero h : Game.heroes){
			HeroInfo heroInfo = new HeroInfo(h);
			root.add(heroInfo, i, j);
			i++;
			if (i == 3){
				i = 0;
				j++;
			}
			
		}
		

		
	}
	
	public GridPane getRoot(){
		return root;
	}
	
}
