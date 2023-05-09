package model.characters;


import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import engine.Game;

import java.awt.Point;
import java.util.Random;

import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;
	
	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	@Override
	public void attack() throws InvalidTargetException, NotEnoughActionsException{
		System.out.println("Entered");
		for (int i = 0; i < Game.map.length; i++) {
			for (int j = 0; j < Game.map[i].length; j++) {
				if(Game.map[i][j] instanceof CharacterCell){
					Character c = ((CharacterCell)Game.map[i][j]).getCharacter();
					
					if(c != null && c instanceof Hero && c.isAdjacent(this)){
						setTarget(c);
						super.attack();
						System.out.println("Attacked " + getTarget());
						return;
					}
				}
			}
			
		}
		
/*		for(int j=0;j<Game.heroes.size();j++){
			if(Game.heroes.get(j).isAdjacent(this)){
				setTarget(Game.heroes.get(j));
				super.attack();
				//System.out.println(this.getCurrentHp());
				break;
			}
		}*/
	}

	
	/*public void attack() throws InvalidTargetException, NotEnoughActionsException{
		int x=this.getLocation().x;
		int y=this.getLocation().y;
		if(x-1>=0&&y-1>=0){
			if(((CharacterCell) Game.map[x-1][y-1]).getCharacter() instanceof Hero){
				setTarget(((CharacterCell) Game.map[x-1][y-1]).getCharacter());
				super.attack();
				return;
			}
		}
		if(x-1>=0)//left
			if(((CharacterCell) Game.map[x-1][y]).getCharacter() instanceof Hero){
				setTarget(((CharacterCell) Game.map[x-1][y-1]).getCharacter());
				super.attack();
				return;
			}
		if(x-1>=0&&y+1<=14)//top left
			if(((CharacterCell) Game.map[x-1][y+1]).getCharacter() instanceof Hero){
				setTarget(((CharacterCell) Game.map[x-1][y-1]).getCharacter());
				super.attack();
				return;
			}
		if(y+1<=14)//top
			if(((CharacterCell) Game.map[x][y+1]).getCharacter() instanceof Hero){
				setTarget(((CharacterCell) Game.map[x-1][y-1]).getCharacter());
				super.attack();
				return;
			}
		if(x+1<=14&&y+1<=14)//top right
			if(((CharacterCell) Game.map[x+1][y+1]).getCharacter() instanceof Hero){
				setTarget(((CharacterCell) Game.map[x-1][y-1]).getCharacter());
				super.attack();
				return;
			}
		if(x+1<=14)//right
			if(((CharacterCell) Game.map[x+1][y]).getCharacter() instanceof Hero){
				setTarget(((CharacterCell) Game.map[x-1][y-1]).getCharacter());
				super.attack();
				return;
			}
		if(x+1<=14&&y-1>=0)//bottom right
			if(((CharacterCell) Game.map[x+1][y-1]).getCharacter() instanceof Hero){
				setTarget(((CharacterCell) Game.map[x-1][y-1]).getCharacter());
				super.attack();
				return;
			}
		if(y-1>=0)//bottom
			if(((CharacterCell) Game.map[x][y-1]).getCharacter() instanceof Hero){
				setTarget(((CharacterCell) Game.map[x-1][y-1]).getCharacter());
				super.attack();
				return;
			}
	}*/
	
	public void onCharacterDeath(){
		super.onCharacterDeath();
		Game.zombies.remove(this);
		if (this.getCurrentHp() == 0)
			spawnAZombie();
	}
	
	public static void spawnAZombie(){ 
		Point respawnPoint = Game.getEmptyPoint();
		CharacterCell cell = (CharacterCell) Game.map[respawnPoint.x][respawnPoint.y];
		Zombie newZombie = new Zombie();
		newZombie.setLocation(respawnPoint);
		cell.setCharacter(newZombie);
		Game.zombies.add(newZombie);
	}
	
	
}


