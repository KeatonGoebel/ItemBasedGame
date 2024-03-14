// FILE: Ranged.java
// Keaton Goebel
// 
// Implentation of the Ranged Class, a lower class of GameElement

import java.io.*;
import java.util.ArrayList;

class Ranged extends GameElement{

	private int accuracy;
	private int speed;
	private int damage;
	private int range;

	// Ranged constructors, can either create empty item or item with variables attached to it

	// If a function attempts to access a variable that doesn't exist, the program will crash. Therefore, 
	// when an item is created with the empty constructor all variables are set to default values to ensure
	// this cannot happen. The variables can then be changed with the mutator functions

	public Ranged(){
		rarity = "";
		description = "";
		name = "";
		type = "I";
		tier = "I";
		alteration = "";
		accuracy = 1;
		speed = 1;
		damage = 1;
		range = 1;
	}

	public Ranged(String rar, String des, String nam, String typ, String tie, String alt, int acc, int spe, int dam, int ran){
		rarity = rar;
		description = des;
		name = nam;
		type = typ;
		tier = tie;
		alteration = alt;
		accuracy = acc;
		speed = spe;
		damage = dam;
		range = ran;
	}

	// Accessors to access variables of Ranged objects 

	public int getAccuracy(){
		return accuracy;
	}

	public int getSpeed(){
		return speed;
	}

	public int getDamage(){
		return damage;
	}

	public int getRange(){
		return range;
	}

	// Manipulators to change varibles of Ranged objects 

	public void changeAccuracy(int newAccuracy){
		accuracy = newAccuracy;
	}

	public void changeSpeed(int newSpeed){
		speed = newSpeed;
	}

	public void changeDamage(int newDamage){
		damage = newDamage;
	} 

	public void changeRange(int newRange){
		range = newRange;
	} 
}