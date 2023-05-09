package model.characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import engine.Game;
import model.world.CollectibleCell;
import model.world.CharacterCell;
import model.world.TrapCell;

public abstract class Hero extends Character {
	

		private int actionsAvailable;
		private int maxActions;
		private ArrayList<Vaccine> vaccineInventory;
		private ArrayList<Supply> supplyInventory;
		private boolean specialAction;
	
		
		
		public Hero(String name,int maxHp, int attackDmg, int maxActions) {
			super(name,maxHp, attackDmg);
			this.maxActions = maxActions;
			this.actionsAvailable = maxActions;
			this.vaccineInventory = new ArrayList<Vaccine>();
			this.supplyInventory=new ArrayList<Supply>();
			this.specialAction=false;
		
		}

	
		public void attack() throws InvalidTargetException, NotEnoughActionsException{
			if (getTarget() instanceof Hero){
				throw new InvalidTargetException("Friendly fire not tolerated");
			}
			if (this instanceof Hero && getTarget() == null){
				throw new InvalidTargetException("No target Locked");
			}
			if (!this.isAdjacent(getTarget())){
				throw new InvalidTargetException("Target not in range!");
			}
			
			Character target = this.getTarget();
			if (actionsAvailable <= 0){
				throw new NotEnoughActionsException("You have used all your actions!");
			}
			super.attack();
			actionsAvailable--;
		}
		
		
		public void onCharacterDeath(){ //it is only called when target hp is zero
				super.onCharacterDeath();
				Game.heroes.remove(this);
		}
		
		
		
		public void useSpecial() throws GameActionException{
			if(specialAction)
				return;
			if (this instanceof Medic && this.getTarget() instanceof Zombie){
				throw new InvalidTargetException("Cannot heal Zombies!");
			}
			if (supplyInventory.isEmpty()){
				throw new NoAvailableResourcesException("Supply Inventory is empty!");
			}
			supplyInventory.get(supplyInventory.size()-1).use(this);
			specialAction = true;
		}
		
		
		public void cure() throws GameActionException{
			if (vaccineInventory.isEmpty()){
				throw new NoAvailableResourcesException("No vaccines in inventory!");
			}
			if (this.getActionsAvailable() == 0){
				throw new NotEnoughActionsException("No Action points available!");
			}
			if (! (this.getTarget() instanceof Zombie)){
				throw new InvalidTargetException("You cannot cure a hero!");
			}
			if (!this.isAdjacent()){
				throw new InvalidTargetException("Target out of range!");
			}
			
			vaccineInventory.get(vaccineInventory.size()-1).use(this);  //updates cell on map from Zombie to Hero
			this.setActionsAvailable(this.getActionsAvailable()-1);
		}
		

		
		public void move(Direction d) throws GameActionException{
			if (this.getActionsAvailable() <= 0)
				throw new NotEnoughActionsException("You have used all your actions!");
			Point currLocation = this.getLocation();
			Point newLocation = new Point(currLocation.x,currLocation.y);
			switch(d){
			case UP: 
				if (currLocation.x == 14)
					throw new MovementException("Out of Bounds!");
				if (Game.map[currLocation.x+1][currLocation.y] instanceof CharacterCell){
					CharacterCell newCell = (CharacterCell) Game.map[currLocation.x+1][currLocation.y];
					if (newCell.getCharacter() != null)
						throw new MovementException("Cell Occupied!");
				}
				newLocation.x++;break;
			case DOWN:
				if (currLocation.x == 0)
					throw new MovementException("Out of Bounds!");
				if (Game.map[currLocation.x-1][currLocation.y] instanceof CharacterCell){
					CharacterCell newCell = (CharacterCell) Game.map[currLocation.x-1][currLocation.y];
					if (newCell.getCharacter() != null)
						throw new MovementException("Cell Occupied!");
				}
				newLocation.x--;break;
			case LEFT:
				if (currLocation.y == 0)
					throw new MovementException("Out of Bounds!");
				if (Game.map[currLocation.x][currLocation.y-1] instanceof CharacterCell){
					CharacterCell newCell = (CharacterCell) Game.map[currLocation.x][currLocation.y-1];
					if (newCell.getCharacter() != null)
						throw new MovementException("Cell Occupied!");
				}
				newLocation.y--;break;
			case RIGHT:
				if (currLocation.y == 14)
					throw new MovementException("Out of Bounds!");
				if (Game.map[currLocation.x][currLocation.y+1] instanceof CharacterCell){
					CharacterCell newCell = (CharacterCell) Game.map[currLocation.x][currLocation.y+1];
					if (newCell.getCharacter() != null)
						throw new MovementException("Cell Occupied!");
				}
				newLocation.y++;break;
			}
			
			this.setActionsAvailable(getActionsAvailable()-1);
			
			if (Game.map[newLocation.x][newLocation.y] instanceof CollectibleCell){
				CollectibleCell cell = (CollectibleCell) Game.map[newLocation.x][newLocation.y];
				cell.getCollectible().pickUp(this);
				Game.map[newLocation.x][newLocation.y] = new CharacterCell(null);
			}

			if (Game.map[newLocation.x][newLocation.y] instanceof TrapCell){
				TrapCell cell = (TrapCell) Game.map[newLocation.x][newLocation.y];
				int dmg = cell.getTrapDamage();
				this.setCurrentHp(this.getCurrentHp() - dmg);
				Game.map[newLocation.x][newLocation.y] = new CharacterCell(null);
			}
			
			this.setLocation(newLocation);
			CharacterCell oldCell = (CharacterCell) Game.map[currLocation.x][currLocation.y];
			CharacterCell newCell = (CharacterCell) Game.map[newLocation.x][newLocation.y];
			newCell.setCharacter(this);
			oldCell.setCharacter(null);
			
			
			if (this.getCurrentHp() == 0){
				this.onCharacterDeath();
				return;
			}
			Game.updateVisibility(this);

		}
		
		public boolean isSpecialAction() {
			return specialAction;
		}



		public void setSpecialAction(boolean specialAction) {
			this.specialAction = specialAction;
		}



		public int getActionsAvailable() {
			return actionsAvailable;
		}



		public void setActionsAvailable(int actionsAvailable) {
			this.actionsAvailable = actionsAvailable;
		}



		public int getMaxActions() {
			return maxActions;
		}



		public ArrayList<Vaccine> getVaccineInventory() {
			return vaccineInventory;
		}


		public ArrayList<Supply> getSupplyInventory() {
			return supplyInventory;
		}



		

	
}
