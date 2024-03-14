// FILE: GameElement.java
// Keaton Goebel
// 
// Implentation of the GameElement Class, a superclass of weapon, ranged, and item 

import java.io.*;
import java.util.ArrayList;

public class GameElement{

	protected String rarity;
	protected String tier;
	protected String description;
	protected String name;
	protected String alteration;
	protected String type;

	// Accessors to access variables of GameElements

	public String getName(){
		return name;
	}

	public String getRarity(){
		return rarity;
	}

	public String getTier(){
		return tier;
	}

	public String getDescription(){
		return description;
	}

	public String getAlteration(){
		return alteration;
	}

	public String getType(){
		return type;
	}


// Manipulators to change varibles of GameElememts

	public void changeName(String newName){
		name = newName;
	}

	public void changeRarity(String newRate){
		rarity = newRate;
	}

	public void changeTier(String newTier){
		tier = newTier;
	}

	public void changeDescription(String newD){
		description = newD;
	}

	public void changeAlteration(String newA){
		alteration = newA;
	}

	public void changeType(String newType){
		type = newType;
	}
}


	