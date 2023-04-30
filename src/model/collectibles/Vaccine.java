package model.collectibles;

import model.characters.Hero;



public class Vaccine implements Collectible {

	public Vaccine() {
		
	}

	@Override
	public void pickUp(Hero h) {
		h.getVaccineInventory().add(new Vaccine());
		
	}

	@Override
	public void use(Hero h) {
		// TODO Auto-generated method stub
		h.getVaccineInventory().remove(h.getVaccineInventory().size()-1);
	}

}
