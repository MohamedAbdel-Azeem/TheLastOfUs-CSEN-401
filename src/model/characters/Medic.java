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
		if (target instanceof Zombie){
			throw new InvalidTargetException("Cannot heal enemies!");
		}
		if (!this.isAdjacent() && this != this.getTarget()){ // this != this.getTarget() checks if the medic is healing himself through same refrence
			throw new InvalidTargetException("Target out of range!");
		}
		if (target instanceof Hero){
			target.setCurrentHp(target.getMaxHp());
		}	
	}
	

}
