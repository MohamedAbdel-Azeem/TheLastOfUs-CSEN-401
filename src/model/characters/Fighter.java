package model.characters;

import exceptions.GameActionException;


public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}
	
	public void attack() throws GameActionException{
		super.attack();	
		if (this.isSpecialAction()){
			this.setActionsAvailable(this.getActionsAvailable()+1);
		}
	}

	

	
	
	
	

}
