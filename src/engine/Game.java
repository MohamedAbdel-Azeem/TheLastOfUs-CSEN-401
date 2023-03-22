package engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.world.Cell;
import model.characters.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Game {

	public static ArrayList<Hero> availableHeroes;
	public static ArrayList<Hero> heroes;
	public static ArrayList<Zombie> zombies;
	public static Cell [][] map;
	
	
	public static void loadHeroes(String filePath) throws IOException , FileNotFoundException{	
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		availableHeroes = new ArrayList<Hero>();
		String line = br.readLine();
		while (line != null){
			String[] lineValues = line.split(",");
			switch(lineValues[1]){
			case "FIGH":availableHeroes.add(new Fighter(lineValues[0],Integer.parseInt(lineValues[2]),Integer.parseInt(lineValues[4]),Integer.parseInt(lineValues[3]))) ;break;
			case "EXP": availableHeroes.add(new Explorer(lineValues[0],Integer.parseInt(lineValues[2]),Integer.parseInt(lineValues[4]),Integer.parseInt(lineValues[3])))  ;break;
			case "MED": availableHeroes.add(new Medic(lineValues[0],Integer.parseInt(lineValues[2]),Integer.parseInt(lineValues[4]),Integer.parseInt(lineValues[3]))) ;
			}
			line = br.readLine();
		}
	}
	
	
}
