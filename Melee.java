// FILE: Melee.java
// Keaton Goebel
// 
// Implentation of the Melee Class, a lower class of Weapon

import java.io.*;
import java.util.ArrayList;

class Melee extends GameElement{

	private int slash;
	private int stab;
	private int sweep;
	private String effect; 

	// Melee constructors, can either create empty item or item with variables attached to it

	// If a function attempts to access a variable that doesn't exist, the program will crash. Therefore, 
	// when an item is created with the empty constructor all variables are set to default values to ensure
	// this cannot happen. The variables can then be changed with the mutator functions

	public Melee(){
		rarity = "";
		description = "";
		name = "";
		type = "I";
		tier = "I";
		alteration = "";
		slash = 1;
		stab = 1;
		sweep = 1;
		effect = "";
	}

	public Melee(String rar, String des, String nam, String typ, String tie, String alt, int sla, int sta, int swe, String eff){
		rarity = rar;
		description = des;
		name = nam;
		type = typ;
		tier = tie;
		alteration = alt;
		slash = sla;
		stab = sta;
		sweep = swe;
		effect = eff;
	}

	// Accessors to access variables of Melee objects 

	public int getSlash(){
		return slash;
	}

	public int getStab(){
		return stab;
	}

	public int getSweep(){
		return sweep;
	}

	public String getEffect(){
		return effect;
	}

	// Manipulators to change varibles of Melee objects 

	public void changeSlash(int newSlash){
		slash = newSlash;
	}

	public void changeStab(int newStab){
		stab = newStab;
	}

	public void changeSweep(int newSweep){
		sweep = newSweep;
	} 

	public void changeEffect(String newEffect){
		effect = newEffect;
	} 
}