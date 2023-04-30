package model.characters;

import java.awt.Point;
import java.util.ArrayList;

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

	
		public void attack() throws GameActionException{
			Character target = this.getTarget();
			if (actionsAvailable < 0){
				throw new NotEnoughActionsException("You have used all your actions!");
			}
			if (target instanceof Hero){
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
			actionsAvailable--;
		}
		
		
		public void onCharacterDeath(){
			if (this.getCurrentHp() == 0){
				Game.heroes.remove(this);
				Game.availableHeroes.add(this);
				Point location = this.getLocation();
				Game.map[location.x][location.y] = null;
			}
		}
		
		
		
		public void useSpecial() throws GameActionException{
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
			
			vaccineInventory.get(vaccineInventory.size()-1).use(this);
			this.getTarget().setCurrentHp(0);
			this.getTarget().onCharacterDeath(); // to decrease Zombie count , remove it from list
			Hero newHero = Game.availableHeroes.remove(0);
			Point location = this.getTarget().getLocation(); //gets target location
			Game.heroes.add(newHero); //spawn a new Hero
			CharacterCell updatedCell = ((CharacterCell) Game.map[location.x][location.y]);
			updatedCell.setCharacter(newHero); //updates cell on map from Zombie to Hero
			this.setActionsAvailable(this.getActionsAvailable()-1);
		}
		
		
		
		public void move(Direction d) throws GameActionException{
			if (this.getActionsAvailable() <= 0){
				throw new NotEnoughActionsException("You have used all your actions!");
			}
			
			Point location = this.getLocation();
			switch(d){
			case UP: 
				if (location.y == 14){
					throw new MovementException("Reached map limit!");
				}
				else if (Game.map[location.x][location.y+1] instanceof CharacterCell){
					throw new MovementException("Cell already occupied!");
				}
				else{
					location.y++;
				};break;
			case DOWN:
				if (location.y == 0){
					throw new MovementException("Reached map limit!");
				}
				else if (Game.map[location.x][location.y-1] instanceof CharacterCell){
					throw new MovementException("Cell already occupied!");
				}
				else{
					location.y--;
				};break;
			case LEFT:
				if (location.x == 0){
					throw new MovementException("Reached map limit!");
				}
				else if (Game.map[location.x-1][location.y] instanceof CharacterCell){
					throw new MovementException("Cell already occupied!");
				}
				else{
					location.x--;
				};break;
			case RIGHT:
				if (location.x == 14){
					throw new MovementException("Reached map limit!");
				}
				else if (Game.map[location.x+1][location.y] instanceof CharacterCell){
					throw new MovementException("Cell already occupied!");
				}
				else{
					location.x++;
				};break;
			}
			
			//Collectible Cell pickup and update Map
			
			//Trap cell update Hero HP and update Map
			
			this.setActionsAvailable(getActionsAvailable()-1);
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
