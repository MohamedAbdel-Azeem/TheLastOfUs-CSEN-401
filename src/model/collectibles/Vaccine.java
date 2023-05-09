package model.collectibles;
import java.awt.Point;
import java.util.Collections;

import engine.Game;
import model.characters.Hero;
import model.world.CharacterCell;




public class Vaccine implements Collectible {

	
	
	public Vaccine() {
		
	}

	@Override
	public void pickUp(Hero h) {
			h.getVaccineInventory().add(this);
	}

	/*
	@Override
	public void use(Hero h) {
		h.getVaccineInventory().remove(this);
		VaccinesUsed++;
	}
	*/
	
	@Override
    public void use(Hero h) {
		h.getVaccineInventory().remove(this);
		Point location = h.getTarget().getLocation(); //gets target location
		h.getTarget().onCharacterDeath(); // remove it from list and updates map and make it empty
		Collections.shuffle(Game.availableHeroes); //shuffles the available heroes list and removes head to get a random hero
		Hero newHero = Game.availableHeroes.remove(0);
		Game.heroes.add(newHero); //spawn a new Hero
		CharacterCell updatedCell = ((CharacterCell) Game.map[location.x][location.y]);
		newHero.setLocation(location);
		updatedCell.setCharacter(newHero);
	}

}
