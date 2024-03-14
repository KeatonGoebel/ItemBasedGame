// FILE: Melee.java
// Keaton Goebel
// 
// Implentation of the Melee Class, a lower class of GameElement

import java.io.*;
import java.util.ArrayList;

class Item extends GameElement{

	private String stats; 

	// Item constructors, can either create empty item or item with variables attached to it

	// If a function attempts to access a variable that doesn't exist, the program will crash. Therefore, 
	// when an item is created with the empty constructor all variables are set to default values to ensure
	// this cannot happen. The variables can then be changed with the mutator functions

	public Item(){
		rarity = "";
		description = "";
		name = "";
		type = "I";
		tier = "I";
		alteration = "";
		stats = "";
	}

	public Item(String rar, String des, String nam, String typ, String tie, String alt, String stat){
		rarity = rar;
		description = des;
		name = nam;
		type = typ;
		tier = tie;
		alteration = alt;
		stats = stat;
	}

	// Accessors to access variables of Melee objects 

	public String getStats(){
		return stats;
	}

	// Manipulators to change varibles of Item objects 

	public void changeStats(String newStats){
		stats = newStats;
	}
}