package model.characters;

import java.awt.Point;

import exceptions.GameActionException;


public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;

	
	public Character() {
	}
	

	public Character(String name, int maxHp, int attackDmg) {
		this.name=name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}
		
	
	abstract public void attack() throws GameActionException;
		
	
	public boolean isAdjacent(){
		Point point1 = this.location;
		Point point2 = target.location;
		
		int x1 = point1.x;
		int y1 = point1.y;
		int x2 = point2.x;
		int y2 = point2.y;
		
		if (x1==x2 && Math.abs(y1-y2) == 1)
			return true;
		if (y1==y2 && Math.abs(x1-x2) == 1)
			return true;
		if(Math.abs(x1-x2) == 1)
			if( Math.abs(y1-y2) ==1)
				return true;
		return false;
		
	}
	
	
	
	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}
	
	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp < 0) 
			this.currentHp = 0;
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
	

}
