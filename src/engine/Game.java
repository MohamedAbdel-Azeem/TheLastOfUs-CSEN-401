package engine;


import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import exceptions.GameActionException;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Game {
	
	public static Cell [][] map = new Cell[15][15]; ;
	public static ArrayList <Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList <Hero> heroes =  new ArrayList<Hero>();
	public static ArrayList <Zombie> zombies =  new ArrayList<Zombie>();
	
	
	
		
	public static void loadHeroes(String filePath)  throws IOException {
		
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero=null;
			switch (content[1]) {
			case "FIGH":
				hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			case "MED":  
				hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3])) ;
				break;
			case "EXP":  
				hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			}
			availableHeroes.add(hero);
			line = br.readLine();
			
			
		}
		br.close();
	}
	
	public static Point getEmptyPoint(){ //gets an empty character cell and returns it
		Random rand = new Random();
        int x = rand.nextInt(15);
        int y = rand.nextInt(15);
        Cell cell = Game.map[x][y];
        while (cell instanceof TrapCell || cell instanceof CollectibleCell){
        	x = rand.nextInt(15);
        	y = rand.nextInt(15);
        	cell = Game.map[x][y];
        }
        CharacterCell charcell = (CharacterCell) cell;
        if (map[x][y] == null || charcell.getCharacter() == null){
        	return new Point(x,y);
        }
        else{
        	return getEmptyPoint();
        }
	}

	
	public static void startGame(Hero h){
		//map = new Cell[15][15];
		for (int i = 0; i < 15 ; i++){ //initializes all cells with character cells
			for (int j = 0 ; j < 15 ; j++){
				map[i][j] = new CharacterCell(null);
			}
		}
		
		heroes.add(h); // handles first hero by adding him in point (0,0) and updating heroes lists
		availableHeroes.remove(h);
		CharacterCell initialCell = (CharacterCell) map[0][0];
		initialCell.setCharacter(h);
		h.setLocation(new Point(0,0));
		
		
		for (int i = 0 ; i < 5 ; i++){ //to put 5 Vaccines Randomly
			Point tmp = getEmptyPoint();
			map[tmp.x][tmp.y] = new CollectibleCell(new Vaccine());
		}
		
		for (int i = 0 ; i < 5 ; i++){ //to put 5 Supplies Randomly
			Point tmp = getEmptyPoint();
			map[tmp.x][tmp.y] = new CollectibleCell(new Supply());
		}
		
		for (int i = 0 ; i < 5 ; i++){ //to put 5 Traps Randomly
			Point tmp = getEmptyPoint();
			map[tmp.x][tmp.y] = new TrapCell();
		}
		for (int i = 0 ; i < 10 ; i++){ //to put 10 Zombies Randomly
			Zombie.spawnAZombie();
		}
		
		Game.updateVisibility(h);
	}

	
	public static void updateVisibility(Hero h){
		Point location = h.getLocation();
		int x = location.x;
		int y = location.y;
		if(x-1>=0&&y-1>=0)//bottom left
			Game.map[x-1][y-1].setVisible(true);
		if(x-1>=0)//left
			Game.map[x-1][y].setVisible(true);
		if(x-1>=0&&y+1<=14)//top left
			Game.map[x-1][y+1].setVisible(true);
		if(y+1<=14)//top
			Game.map[x][y+1].setVisible(true);
		if(x+1<=14&&y+1<=14)//top right
			Game.map[x+1][y+1].setVisible(true);
		if(x+1<=14)//right
			Game.map[x+1][y].setVisible(true);
		if(x+1<=14&&y-1>=0)//bottom right
			Game.map[x+1][y-1].setVisible(true);
		if(y-1>=0)//bottom
			Game.map[x][y-1].setVisible(true);
		Game.map[x][y].setVisible(true);
	}
	
	
	public static boolean checkWin(){

		int vaccineInventory = getVaccineInventory();
		int vaccinesPicked = getVaccinesPicked();
		
		return heroes.size() >= 5 && (vaccinesPicked == 5 && vaccineInventory == 0);
	}
	

	
	public static int getVaccinesPicked(){
		int vaccinesOnGround = 0;
		for (int i = 0 ; i < 15 ; i++){ // map length is always 15
			for (int j = 0 ; j < 15 ; j++){ // map width is also always 15
				if (map[i][j] instanceof CollectibleCell){
					if(((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine)
						vaccinesOnGround++;
				}
			}
		}
		return 5 - vaccinesOnGround; //Total no. of Vaccines - Vaccines still on ground = Vaccines Picked
	}

	public static int getVaccineInventory(){
		int vaccineInventory = 0;
		for (int i = 0 ; i < heroes.size() ; i++){
			vaccineInventory+= heroes.get(i).getVaccineInventory().size();
		}
		return vaccineInventory;
	}
	
	public static boolean checkGameOver(){

		int vaccineInventory = getVaccineInventory();
		int vaccinesPicked = getVaccinesPicked();
		int vaccinesLeft = 5 - vaccinesPicked;
		return (heroes.size() == 0) || (vaccineInventory == 0 && vaccinesLeft == 0) ;
	}
	
	public static void endTurn() throws GameActionException{
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++)
				Game.map[i][j].setVisible(false);
				
		for(int i=0;i<zombies.size();i++){
			Zombie z=zombies.get(i);
			z.attack();
			z.setTarget(null);	
		}
		
		for(int i=0;i<heroes.size();i++){ // this loop updates Visibility , actions av. , special actions , targets
			updateVisibility(heroes.get(i));
			heroes.get(i).setActionsAvailable(heroes.get(i).getMaxActions());
			heroes.get(i).setSpecialAction(false);
			heroes.get(i).setTarget(null);
		}
		Zombie.spawnAZombie();
	}
	
	

}
