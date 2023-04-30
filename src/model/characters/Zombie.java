package model.characters;

import java.awt.Point;

import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import engine.Game;


public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;
	
	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	@Override
	public void attack() throws GameActionException {
		Character target = this.getTarget();
		if (target instanceof Zombie){
			throw new InvalidTargetException("Friendly Fire is not tolerated!");
		}
		if (! this.isAdjacent()){
			throw new InvalidTargetException("Target not in range!");
		}
		target.setCurrentHp(target.getCurrentHp() - this.getAttackDmg());
		if (target.getCurrentHp() == 0){
			target.onCharacterDeath();
		}
		else{
			target.defend(this);
		}
		target.defend(this);
	}
	
	public void onCharacterDeath(){
		if (this.getCurrentHp() == 0){
			Game.zombies.remove(--ZOMBIES_COUNT);
			Point location = this.getLocation();
			Game.map[location.x][location.y] = null;
		}
	}

}


