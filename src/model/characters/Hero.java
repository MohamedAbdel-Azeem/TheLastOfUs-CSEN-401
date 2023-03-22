package model.characters;

import java.util.ArrayList;

import model.collectibles.Supply;
import model.collectibles.Vaccine;

public class Hero extends Character{
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Supply> supplyInventory;
	private ArrayList<Vaccine> vaccineInventory;
	
	public Hero(String name, int maxHp, int attackDmg, int maxActions){
		super(name,maxHp,attackDmg);
		this.maxActions = maxActions;
		this.vaccineInventory = new ArrayList<Vaccine>();
		this.supplyInventory = new ArrayList<Supply>();
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

}
