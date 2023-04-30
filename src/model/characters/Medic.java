package model.characters;

import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;



public class Medic extends Hero {
	//Heal amount  attribute - quiz idea
	

	public Medic(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}
	
	public void useSpecial() throws GameActionException{ // heals a target whereas target is a hero
		super.useSpecial();
		Character target =this.getTarget();
		if (target instanceof Hero){
			target.setCurrentHp(target.getMaxHp());
		}
		else{
			throw new InvalidTargetException("Cannot heal enemies!");
		}
	}
	

}
