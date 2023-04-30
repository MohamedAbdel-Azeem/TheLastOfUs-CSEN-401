package model.collectibles;

import model.characters.Hero;



public class Supply implements Collectible  {

	

	
	public Supply() {
		
	}

	@Override
	public void pickUp(Hero h) {
		// TODO Auto-generated method stub
		h.getSupplyInventory().add(new Supply());
		
	}

	@Override
	public void use(Hero h) {
		// TODO Auto-generated method stub
		h.getSupplyInventory().remove(h.getSupplyInventory().size()-1);
	}


	
		
		

}
