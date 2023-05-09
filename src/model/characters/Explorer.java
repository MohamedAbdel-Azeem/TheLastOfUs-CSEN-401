package model.characters;

import model.world.Cell;
import engine.Game;
import exceptions.GameActionException;


public class Explorer extends Hero {
	

	public Explorer(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}
	
	public void useSpecial() throws GameActionException{
		super.useSpecial();
		
		for(int i = 0 ; i < Game.map.length ; i++){
			for (int j = 0 ; j < Game.map[i].length ; j++){
				Game.map[i][j].setVisible(true);
			}
		}
		
	}

	
	

	
}
