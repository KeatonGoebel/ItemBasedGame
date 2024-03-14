// FILE: game.java
// Keaton Goebel
// 
// Main Driver Program

import java.io.*;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Collections;
import java.util.Random;


public class Game{

	public static void main(String[] args){

		Game gameObject = new Game();
		ArrayList<GameElement> itemBox = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a Command (Display, Change, Rand, List, help or Exit)");
		System.out.print(">:");
		String userInput = scanner.nextLine();

		// Drop Rates Based on Rarity (80%, 60%, and 40%): 
		double COMMON_DROP_RATE = 0.8;
		double RARE_DROP_RATE = 0.6;
		double ULTRA_RARE_DROP_RATE = 0.4;

		// Opening a new file to be the change log, all changes that happen throughout a gameplay session will go in here
		FileWriter file = null;
		try {
			file = new FileWriter("changeLog.txt");
			int changeLogIndex = 1;

			while(!userInput.equals("Exit")){
				Boolean commandFound = false;
			
				// dividing csv file into tokens, creating objects, and loading them into item box 
				try (BufferedReader reader = new BufferedReader(new FileReader("Big_Ass_List_of_Items.csv"))) {

	        		String line, name, tier, type, rarity, description, alteration, stats;
	            	while ((line = reader.readLine()) != null) {
	                	String[] tokens = line.split(",");

	                	name = tokens[0];
	                	tier  = tokens[1];
	                	type = tokens[2];
	                	rarity = tokens[3];
	                	description = tokens[4];
	                	alteration = tokens[6];

	                	// creating item objects and loading them into itemBox, stats is just a string for them 
	                	if(type.equals("I")){
	                		stats = tokens[5];
	                		GameElement item = new Item(rarity, description, name, type, tier, alteration, stats);
	                		itemBox.add(item);
	                	}

	                	// creating melee weapon objects and loading into itembox, stats are slash, stab, and speed, stats must be parsed 
	                	// and pluses and minuses must be translated into numbers 
	                	if(type.equals("M ")){
	                		stats = tokens[5];	// stats is "slash ++ stabb ++ speed ++," statList is the tokenized version
	                		int slash = 0;
	                		int stabb = 0;
	                		int speed = 0;
	                		String effect = "";
	                		String[] statList = stats.split("(?<=\\+|\\-|\\.|0)\\s+");

	                		for(int i = 0; i < statList.length; i++){
	                			String stat = statList[i];
	                			int plusCount = 0;
	                			int minusCount = 0;

	         					for(int j = 0; j < stat.length(); j++){
	         						if(stat.substring(j,j+1).equals("+")){
	         							plusCount++;
	         						}
	         						if(stat.substring(j,j+1).equals("-")){
	         							minusCount++;
	         						}
	         					}
	         					if(stat.contains("Slashing")){
	         						slash = 0;
	         						if(plusCount > minusCount){
	         							slash = plusCount;
	         						}
	         						else if(minusCount > plusCount){
	         							slash = minusCount * -1;
	         						}
	         						else 
	         							slash = 0;
	         					}
	         					if(stat.contains("Stabbing")){
	         						stabb = 0;
	         						if(plusCount > minusCount){
	         							stabb = plusCount;
	         						}
	         						else if(minusCount > plusCount){
	         							stabb = minusCount * -1;
	         						}
	         						else 
	         							stabb = 0;
	         					}
	         					if(stat.contains("Speed")){
	         						speed = 0;
	         						if(plusCount > minusCount){
	         							speed = plusCount;
	         						}
	         						else if(minusCount > plusCount){
	         							speed = minusCount * -1;
	         						}
	         						else 
	         							speed = 0;
	         					}
	         					if(!(stat.contains("Slashing") || stat.contains("Stabbing") || stat.contains("Speed"))){
	         						effect = stat;
	         					}
	                		}
	                		GameElement melee = new Melee(rarity, description, name, type, tier, alteration, slash, stabb, speed, effect);
	                		itemBox.add(melee);
	                	}

	                	// creating ranged weapon objects and loading into itemBox, special stats are accuracy, speed, damage, and range, pluses and 
	                	// minus must be translated into numbers
	                	if(type.equals("R")){
	                		stats = tokens[5];
	                		int accuracy = 0;
	                		int rangedSpeed = 0;
	                		int damage = 0;
	                		int range = 0;
	                		String[] statList = stats.split("(?<=\\+|\\-|0)\\s+");

	                		for(int i = 0; i < statList.length; i++){
	                			String stat = statList[i];
	                			int plusCount = 0;
	                			int minusCount = 0;

	         					for(int j = 0; j < stat.length(); j++){
	         						if(stat.substring(j,j+1).equals("+")){
	         							plusCount++;
	         						}
	         						if(stat.substring(j,j+1).equals("-")){
	         							minusCount++;
	         						}
	         					}
	         					if(stat.contains("Accuracy")){
	         						accuracy = 0;
	         						if(plusCount > minusCount){
	         							accuracy = plusCount;
	         						}
	         						else if(minusCount > plusCount){
	         							accuracy = minusCount * -1;
	         						}
	         						else 
	         							accuracy = 0;
	         					}
	         					if(stat.contains("Speed")){
	         						rangedSpeed = 0;
	         						if(plusCount > minusCount){
	         							rangedSpeed = plusCount;
	         						}
	         						else if(minusCount > plusCount){
	         							rangedSpeed = minusCount * -1;
	         						}
	         						else 
	         							rangedSpeed = 0;
	         					}
	         					if(stat.contains("Damage")){
	         						damage = 0;
	         						if(plusCount > minusCount){
	         							damage = plusCount;
	         						}
	         						else if(minusCount > plusCount){
	         							damage = minusCount * -1;
	         						}
	         						else 
	         							damage = 0;
	         					}
	         					if(stat.contains("Range")){
	         						range = 0;
	         						if(plusCount > minusCount){
	         							range = plusCount;
	         						}
	         						else if(minusCount > plusCount){
	         							range = minusCount * -1;
	         						}
	         						else 
	         							range = 0;
	         					}
	                		}
	                		GameElement ranged = new Ranged(rarity, description, name, type, tier, alteration, accuracy, rangedSpeed, damage, range);
	                		itemBox.add(ranged);
	             	
	                	}

	            	}

	            	// Programing List function, format is "List" or "List itemType," either prints everything or everything from just one type
	            	ArrayList<String> tempList = new ArrayList<>();
	            	if (userInput.equals("List")){
	            		for(int i = 0; i < itemBox.size(); i++){
	            			if(!tempList.contains(itemBox.get(i).getName())){
	            				tempList.add(itemBox.get(i).getName());
	            			}
	            		}
	          			for(int i = 0; i < tempList.size(); i++){
	          				System.out.println(tempList.get(i));
	          			}
	            		commandFound = true; 
	        		}

		        	if (userInput.equals("List Melee")){ 
		            	for(int i = 0; i < itemBox.size(); i++){
		            		if ((itemBox.get(i) instanceof Melee) && (!tempList.contains(itemBox.get(i).getName()))){
		            			tempList.add(itemBox.get(i).getName());
		            		}
		            	}
		            	for(int i = 0; i < tempList.size(); i++){
	          				System.out.println(tempList.get(i));
	          			}
		            	commandFound = true;
		        	}

		        	if (userInput.equals("List Ranged")){ 
		            	for(int i = 0; i < itemBox.size(); i++){
		            		if ((itemBox.get(i) instanceof Ranged) && (!tempList.contains(itemBox.get(i).getName()))){
		            			tempList.add(itemBox.get(i).getName());
		            		}
		            	}
		            	for(int i = 0; i < tempList.size(); i++){
	          				System.out.println(tempList.get(i));
	          			}
		            	commandFound = true;
		        	}

		        	if (userInput.equals("List Item")){ 
		            	for(int i = 0; i < itemBox.size(); i++){
		            		if ((itemBox.get(i) instanceof Item) && (!tempList.contains(itemBox.get(i).getName()))){
		            			tempList.add(itemBox.get(i).getName());
		            		}
		            	}
		            	for(int i = 0; i < tempList.size(); i++){
	          				System.out.println(tempList.get(i));
	          			}
		            	commandFound = true;
		        	}
				}
				catch(FileNotFoundException e){
					System.out.println("Error: File Not Found");
					System.exit(1);
				}
				catch(NullPointerException e){
					System.out.println("Error: Invalid Data Inputted");
					System.exit(1);
				}
				catch(IOException e){
					System.out.println("Error: IOException Found");
					System.exit(1);
				}

				// Programing "Display" function, format is "Display GameElement," differenties between GameElements and displays all available information if they are not empty
				if(userInput.startsWith("Display ")){
					for(int i = 0; i < itemBox.size(); i++){

						if(userInput.substring(8).equalsIgnoreCase(itemBox.get(i).getName())){

							System.out.println("Item: " +itemBox.get(i).getName());
							System.out.println("Type: " +itemBox.get(i).getType());
							System.out.println("Tier: " +itemBox.get(i).getTier());
							System.out.println("Rarity: " +itemBox.get(i).getRarity());
							System.out.println("Description: " +itemBox.get(i).getDescription());
							if(!(itemBox.get(i).getAlteration().equals("Nil"))){
								System.out.println("Alteration: " +itemBox.get(i).getAlteration());
							}

							if(itemBox.get(i) instanceof Item){
								Item item = (Item) itemBox.get(i);
								if(!(item.getStats().equals(""))){	
									System.out.println("GameElement: Item ");
									System.out.println("Stats: " +item.getStats());
								}
							}

							if(itemBox.get(i) instanceof Melee){
								Melee meleeWeapon = (Melee) itemBox.get(i);
								System.out.println("GameElement: Melee ");
								System.out.println("Slash: " +meleeWeapon.getSlash());
								System.out.println("Stab: " +meleeWeapon.getStab());
								System.out.println("Sweep: " +meleeWeapon.getSweep());
							}

							if(itemBox.get(i) instanceof Ranged){
								Ranged rangedWeapon = (Ranged) itemBox.get(i);
								System.out.println("GameElement: Ranged ");
								System.out.println("Accuracy: " +rangedWeapon.getAccuracy());
								System.out.println("Speed: " +rangedWeapon.getSpeed());
								System.out.println("Damage: " +rangedWeapon.getDamage());
								System.out.println("Range: " +rangedWeapon.getRange());
							}
							commandFound = true;
							break;
						}
					}
				}

				// Programming Change function, must first check for the word Change to get past this point
				if((userInput.startsWith("Change")) && (userInput.length() > 6)){
					int firstSpace = userInput.indexOf(' ');
	           		int lastSpace = userInput.lastIndexOf(' ');
	           		String oldItem = "";
	           		String newItem = "";
					if((firstSpace != -1) && (lastSpace != -1) && (firstSpace != lastSpace)){

						// Now determining type of change 

						// ChangeName
						if(userInput.substring(6).startsWith("Name")){
	                		oldItem = userInput.substring(firstSpace + 1, lastSpace);
	                		newItem = userInput.substring(lastSpace + 1);
	                		for(int i = 0; i < itemBox.size();i++){
	                			if(itemBox.get(i).getName().equalsIgnoreCase(oldItem)){
	                				itemBox.get(i).changeName(newItem);
	                				file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s " + "Name has been changed to " + newItem + " \n");
	                				changeLogIndex ++;
	                				file.flush();
	                				System.out.println("Item Changed!");
	                				commandFound = true;
	                				break;
	                			}
	                		}
						}
						//ChangeRarity must both be in quotes
						if(userInput.substring(6).startsWith("Rarity")){
							String remainingInput = userInput.substring(12); 
							String inputParts[] = remainingInput.split("\"");
	        				if(inputParts.length >= 4){
	        					oldItem = inputParts[1].trim();
	        					newItem = inputParts[3].trim();
	        				}

	        				for (int i = 0; i < itemBox.size(); i++) {
	            				if (itemBox.get(i).getName().equalsIgnoreCase(oldItem)) {
	                				itemBox.get(i).changeRarity(newItem);
	                				file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s Rarity has been changed to " + newItem  + " \n");
	                				changeLogIndex ++;
	                				file.flush();
	                				System.out.println("Item Changed!");
	                				commandFound = true;
	                				break;
	            				}
	        				}
						}
						//ChangeTier
						if(userInput.substring(6).startsWith("Tier")){
	                		oldItem = userInput.substring(firstSpace + 1, lastSpace);
	                		newItem = userInput.substring(lastSpace + 1);
	                		for(int i = 0; i < itemBox.size();i++){
	                			if(itemBox.get(i).getName().equalsIgnoreCase(oldItem)){
	                				itemBox.get(i).changeTier(newItem);
	                				file.write("Change " + changeLogIndex + ": " +  oldItem + " has successfully been changed. " + oldItem + "'s " + "Tier has been changed to " + newItem  + " \n");
	                				changeLogIndex ++;
	                				file.flush();
	                				System.out.println("Item Changed!");
	                				commandFound = true;
	                				break;
	                			}
	                		}
						}
						//ChangeDesc, must both be in quotes
						if(userInput.substring(6).startsWith("Desc")){
							String remainingInput = userInput.substring(11); 
							String inputParts[] = remainingInput.split("\"");
	        				if(inputParts.length >= 4){
	        					oldItem = inputParts[1].trim();
	        					newItem = inputParts[3].trim();
	        				}

	        				for (int i = 0; i < itemBox.size(); i++) {
	            				if (itemBox.get(i).getName().equalsIgnoreCase(oldItem)) {
	                				itemBox.get(i).changeDescription(newItem);
	                				file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s Description has been changed to " + newItem  + " \n");
	                				changeLogIndex ++;
	                				file.flush();
	                				System.out.println("Item Changed!");
	                				commandFound = true;
	                				break;
	            				}
	        				}
						}
						//ChangeAlt, must both be in quotes 
						if(userInput.substring(6).startsWith("Alt")){
							String remainingInput = userInput.substring(10); 
							String inputParts[] = remainingInput.split("\"");
							if(inputParts.length >= 4){
	        					oldItem = inputParts[1].trim();
	        					newItem = inputParts[3].trim();
	        				}

	        				for (int i = 0; i < itemBox.size(); i++) {
	            				if (itemBox.get(i).getName().equalsIgnoreCase(oldItem)) {
	                				itemBox.get(i).changeAlteration(newItem);
	                				file.write("Change " + changeLogIndex + ": " +  oldItem + " has successfully been changed. " + oldItem + "'s Alteration has been changed to " + newItem  + " \n");
	                				changeLogIndex ++;
	                				file.flush();
	                				System.out.println("Item Changed!");
	                				commandFound = true;
	                				break;
	            				}
	        				}
						}
						// ChangeStats, Item class only, must both be in quotes 
						if(userInput.substring(6).startsWith("Stats")){
							String remainingInput = userInput.substring(12); 
							String inputParts[] = remainingInput.split("\"");
							if(inputParts.length >= 4){
	        					oldItem = inputParts[1].trim();
	        					newItem = inputParts[3].trim();
	        				}

	        				for (int i = 0; i < itemBox.size(); i++) {
	        					if(itemBox.get(i) instanceof Item){
	        						Item item = (Item) itemBox.get(i);
		            				if (item.getName().equalsIgnoreCase(oldItem)) {
		                				item.changeStats(newItem);
		                				file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s Stats has been changed to " + newItem  + " \n");
		                				changeLogIndex ++;
		                				file.flush();
		                				System.out.println("Item Changed!");
		                				commandFound = true;
		                				break;
		            				}
		            			}	
	        				}
						}
						// ChangeSlash, Melee class only 
						if(userInput.substring(6).startsWith("Slash")){
		                	oldItem = userInput.substring(firstSpace + 1, lastSpace);
		                	newItem = userInput.substring(lastSpace + 1);
		                	int tempNewItem = Integer.parseInt(newItem);
		                	for(int i = 0; i < itemBox.size();i++){
		                		if((itemBox.get(i).getName().equalsIgnoreCase(oldItem)) && (itemBox.get(i) instanceof Melee)){
		                			Melee melee = (Melee) itemBox.get(i);
		                			melee.changeSlash(tempNewItem);
		                			file.write("Change " + changeLogIndex + ": " +  oldItem + " has successfully been changed. " + oldItem + "'s " + "Slash has been changed to " + newItem  + " \n");
		                			changeLogIndex ++;
		                			file.flush();
		                			System.out.println("Item Changed!");
		                			commandFound = true;
		                			break;
		                		}
		                	}
						}
						// ChangeStab, Melee class only 
						if(userInput.substring(6).startsWith("Stab")){
		                	oldItem = userInput.substring(firstSpace + 1, lastSpace);
		                	newItem = userInput.substring(lastSpace + 1);
		                	int tempNewItem = Integer.parseInt(newItem);
		                	for(int i = 0; i < itemBox.size();i++){
		                		if((itemBox.get(i).getName().equalsIgnoreCase(oldItem)) && (itemBox.get(i) instanceof Melee)){
		                			Melee melee = (Melee) itemBox.get(i);
		                			melee.changeStab(tempNewItem);
		                			file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s " + "Stab has been changed to " + newItem + " \n");
		                			changeLogIndex ++;
		                			file.flush();
		                			System.out.println("Item Changed!");
		                			commandFound = true;
		                			break;
		                		}
		                	}
						}
						// ChangeSweep, Melee class only 
						if(userInput.substring(6).startsWith("Sweep")){
		                	oldItem = userInput.substring(firstSpace + 1, lastSpace);
		                	newItem = userInput.substring(lastSpace + 1);
		                	int tempNewItem = Integer.parseInt(newItem);
		                	for(int i = 0; i < itemBox.size();i++){
		                		if((itemBox.get(i).getName().equalsIgnoreCase(oldItem)) && (itemBox.get(i) instanceof Melee)){
		                			Melee melee = (Melee) itemBox.get(i);
		                			melee.changeSweep(tempNewItem);
		                			file.write("Change " + changeLogIndex + ": " +  oldItem + " has successfully been changed. " + oldItem + "'s " + "Sweep has been changed to " + newItem + " \n");
		                			changeLogIndex ++;
		                			file.flush();
		                			System.out.println("Item Changed!");
		                			commandFound = true;
		                			break;
		                		}
		                	}
						}
						// ChangeAccuracy, Ranged class only 
						if(userInput.substring(6).startsWith("Accuracy")){
		                	oldItem = userInput.substring(firstSpace + 1, lastSpace);
		                	newItem = userInput.substring(lastSpace + 1);
		                	int tempNewItem = Integer.parseInt(newItem);
		                	for(int i = 0; i < itemBox.size();i++){
		                		if((itemBox.get(i).getName().equalsIgnoreCase(oldItem)) && (itemBox.get(i) instanceof Ranged)){
		                			Ranged ranged = (Ranged) itemBox.get(i);
		                			ranged.changeAccuracy(tempNewItem);
		                			file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s " + "Accuracy has been changed to " + newItem + " \n");
		                			changeLogIndex ++;
		                			file.flush();
		                			System.out.println("Item Changed!");
		                			commandFound = true;
		                			break;
		                		}
		                	}
						}
						// ChangeSpeed, Ranged class only
						if(userInput.substring(6).startsWith("Speed")){
		                	oldItem = userInput.substring(firstSpace + 1, lastSpace);
		                	newItem = userInput.substring(lastSpace + 1);
		                	int tempNewItem = Integer.parseInt(newItem);
		                	for(int i = 0; i < itemBox.size();i++){
		                		if((itemBox.get(i).getName().equalsIgnoreCase(oldItem)) && (itemBox.get(i) instanceof Ranged)){
		                			Ranged ranged = (Ranged) itemBox.get(i);
		                			ranged.changeSpeed(tempNewItem);
		                			file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s " + "Speed has been changed to " + newItem + " \n");
		                			changeLogIndex ++;
		                			file.flush();
		                			System.out.println("Item Changed!");
		                			commandFound = true;
		                			break;
		                		}
		                	}
						}
						// ChangeDamage, Ranged class only
						if(userInput.substring(6).startsWith("Damage")){
		                	oldItem = userInput.substring(firstSpace + 1, lastSpace);
		                	newItem = userInput.substring(lastSpace + 1);
		                	int tempNewItem = Integer.parseInt(newItem);
		                	for(int i = 0; i < itemBox.size();i++){
		                		if((itemBox.get(i).getName().equalsIgnoreCase(oldItem)) && (itemBox.get(i) instanceof Ranged)){
		                			Ranged ranged = (Ranged) itemBox.get(i);
		                			ranged.changeDamage(tempNewItem);
		                			file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s " + "Damage has been changed to " + newItem + " \n");
		                			changeLogIndex ++;
		                			file.flush();
		                			System.out.println("Item Changed!");
		                			commandFound = true;
		                			break;
		                		}
		                	}
						}
						// ChangeRange, Ranged class only
						if(userInput.substring(6).startsWith("Range")){
		                	oldItem = userInput.substring(firstSpace + 1, lastSpace);
		                	newItem = userInput.substring(lastSpace + 1);
		                	int tempNewItem = Integer.parseInt(newItem);
		                	for(int i = 0; i < itemBox.size();i++){
		                		if((itemBox.get(i).getName().equalsIgnoreCase(oldItem)) && (itemBox.get(i) instanceof Ranged)){
		                			Ranged ranged = (Ranged) itemBox.get(i);
		                			ranged.changeRange(tempNewItem);
		                			file.write("Change " + changeLogIndex + ": " + oldItem + " has successfully been changed. " + oldItem + "'s " + "Range has been changed to " + newItem + " \n");
		                			changeLogIndex ++; 
		                			file.flush();
		                			System.out.println("Item Changed!");
		                			commandFound = true;
		                			break;
		                		}
		                	}
						}
					}
				}

					// Programming Rand function
					if (userInput.startsWith("Rand ") && userInput.length() > 5) {

		    			String pull = userInput.substring(4);
		    			String[] pullList = pull.split(",");
			    		int itemCount = Integer.parseInt(pullList[0].trim());
			    		ArrayList<String> tierList = new ArrayList<>();
	        			ArrayList<String> rarityList = new ArrayList<>();
	        			ArrayList<GameElement> fullList = new ArrayList<>();
	        			ArrayList<GameElement> finalList = new ArrayList<>();

	        			// checking if inputted value is a number or not 
			    		for (int i = 1; i < pullList.length; i++) {
			        		if (pullList[i].matches("-?\\d+")) {
			            		tierList.add(pullList[i]);
			        		} else {
			            		rarityList.add(pullList[i]);
			        		}
			    		}

			    		// translating rarities
			    		for(int i = 0; i < rarityList.size();i++){
			    			if(rarityList.get(i).equals("C")){
								rarityList.set(i,"Common (Grey)");
							}
							if(rarityList.get(i).equals("R")){
								rarityList.set(i,"Rare (Green)");
							}
							if(rarityList.get(i).equals("UR")){
								rarityList.set(i,"Ultra Rare (Purple)");
							}
						}

						// translating tiers
						for(int i = 0; i < tierList.size();i++){
			    			if(tierList.get(i).equals("1")){
								tierList.set(i,"I");
							}
							if(tierList.get(i).equals("2")){
								tierList.set(i,"II");
							}
							if(tierList.get(i).equals("3")){
								tierList.set(i,"III");
							}
						}

			    		System.out.println("Count: " +itemCount);
			    		for(int i = 0; i < tierList.size(); i++){
			    			System.out.println("Tier: " +tierList.get(i));
			    		}
			    		for(int i = 0; i < rarityList.size(); i++){
			    			System.out.println("Rarity: " +rarityList.get(i));
			    		}
			    		System.out.print("\n");

						fullList.clear();

						//if (rarityList.isEmpty()){
							//System.out.println("Error: A Rarity must be Specified");

						//}

						// Error Checking, One of the lists must be populated
						if (!rarityList.isEmpty()){
							commandFound = true;

							// creating a list of items that fit the search critera 
							for (int i = 0; i < itemBox.size(); i++) {
							    boolean meetsRarityCriteria = rarityList.contains(itemBox.get(i).getRarity());
							    boolean meetsTierCriteria = tierList.isEmpty() || tierList.contains(itemBox.get(i).getTier());
							   	
							   	// checking if a duplicate exists in the fullList
							   	boolean duplicate = false;
							   	for(int j = 0; j < fullList.size(); j++){
							   		if(fullList.get(j).getName().equals(itemBox.get(i).getName())){
							   			duplicate = true;
							   		}
							   	}

							    if (meetsRarityCriteria && meetsTierCriteria && !duplicate) {
							        fullList.add(itemBox.get(i));
							    }
							}

							finalList.clear();
							boolean tooMany = false;
							int itemsPicked = 0;
							while (itemsPicked < itemCount){

								Collections.shuffle(fullList);
								Random random = new Random();

								for(int i = 0; i < fullList.size(); i++){
									double randomInt = random.nextDouble();
									if(fullList.get(i).getRarity().equals("Common (Grey)")){
										if (randomInt < COMMON_DROP_RATE){

											// checking if a duplicate exists in the finalList
										   	boolean duplicate = false;
										   	for(int j = 0; j < finalList.size(); j++){
										   		if(finalList.get(j).getName().equals(fullList.get(i).getName())){
										   			duplicate = true;
										   		}
							   				}

							   				if(!duplicate){
												finalList.add(fullList.get(i));
												itemsPicked ++;
												break;
											}
										}
									}

									else if(fullList.get(i).getRarity().equals("Rare (Green)")){
										if (randomInt < RARE_DROP_RATE){

											boolean duplicate = false;
										   	for(int j = 0; j < finalList.size(); j++){
										   		if(finalList.get(j).getName().equals(fullList.get(i).getName())){
										   			duplicate = true;
										   		}
							   				}

							   				if(!duplicate){
												finalList.add(fullList.get(i));
												itemsPicked ++;
												break;
											}
										}
										
									}

									else if(fullList.get(i).getRarity().equals("Ultra Rare (Purple)")){
										if (randomInt < ULTRA_RARE_DROP_RATE){

											boolean duplicate = false;
										   	for(int j = 0; j < finalList.size(); j++){
										   		if(finalList.get(j).getName().equals(fullList.get(i).getName())){
										   			duplicate = true;
										   		}
							   				}

											if(!duplicate){
												finalList.add(fullList.get(i));
												itemsPicked ++;
												break;
											}
										}
										
									}
								}
								// all the elements of fullList are now in fullList, since we have removed duplicates we need to break out
								if(finalList.size() == fullList.size()){
									System.out.println("Error: To many items requested, not enough available with those specifics");
									tooMany = true;
									break;
								}
							}
							if(!tooMany){
								for(int i = 0; i < finalList.size(); i++){
									System.out.println(finalList.get(i).getName());
								}
							}
						}
						else {
							System.out.println("Error: A Rarity must be Specified");
							commandFound = true;
						}
		    		}

		    		// Programming List functions full lists and specific lists
		    		if (userInput.startsWith("List ") && userInput.length() > 4) {

		    			if(!(userInput.equals("List") || userInput.equals("List Melee") || userInput.equals("List Ranged") || userInput.equals("List Item")) || userInput.equals("List")){

			    			String list = userInput.substring(4);
			    			String[] listRequirements = list.split(",");
				    		ArrayList<String> tierList = new ArrayList<>();
		        			ArrayList<String> rarityList = new ArrayList<>();
		        			ArrayList<GameElement> fullList = new ArrayList<>();

		        			// checking if inputted value is a number or not 
				    		for (int i = 0; i < listRequirements.length; i++) {
				    			System.out.println(listRequirements[i]);
				        		if (listRequirements[i].matches("-?\\d+")) {
				            		tierList.add(listRequirements[i]);
				        		} else {
				            		rarityList.add(listRequirements[i]);
				        		}
				    		}

				    		// translating rarities
				    		for(int i = 0; i < rarityList.size();i++){
				    			if(rarityList.get(i).trim().equals("C")){
									rarityList.set(i,"Common (Grey)");
								}
								if(rarityList.get(i).trim().equals("R")){
									rarityList.set(i,"Rare (Green)");
								}
								if(rarityList.get(i).trim().equals("UR")){
									rarityList.set(i,"Ultra Rare (Purple)");
								}
							}

							// translating tiers
							for(int i = 0; i < tierList.size();i++){
				    			if(tierList.get(i).trim().equals("1")){
									tierList.set(i,"I");
								}
								if(tierList.get(i).trim().equals("2")){
									tierList.set(i,"II");
								}
								if(tierList.get(i).trim().equals("3")){
									tierList.set(i,"III");
								}
							}

			    			for(int i = 0; i < tierList.size(); i++){
			    				System.out.println("Tier: " +tierList.get(i));
			    			}
			    			for(int i = 0; i < rarityList.size(); i++){
			    				System.out.println("Rarity: " +rarityList.get(i));
			    			}
			    			System.out.print("\n");

							// Error Checking, One of the lists must be populated
							if (!(tierList.isEmpty() && rarityList.isEmpty())){
								commandFound = true;

								// creating a list of items that fit the search critera 
								for (int i = 0; i < itemBox.size(); i++) {
								    boolean meetsRarityCriteria = rarityList.isEmpty() || rarityList.contains(itemBox.get(i).getRarity());
								    boolean meetsTierCriteria = tierList.isEmpty() || tierList.contains(itemBox.get(i).getTier());
								   	
								   	// checking if a duplicate exists in the fullList
								   	boolean duplicate = false;
								   	for(int j = 0; j < fullList.size(); j++){
								   		if(fullList.get(j).getName().equals(itemBox.get(i).getName())){
								   			duplicate = true;
								   		}
								   	}

								    if (meetsRarityCriteria && meetsTierCriteria && !duplicate) {
								    	//System.out.println(itemBox.get(i).getName());
								        fullList.add(itemBox.get(i));
								    }
								}
							}
							for(int i = 0; i < fullList.size(); i++){
								System.out.println(fullList.get(i).getName());
							}
						}

					}

					// calling help function 
					if (userInput.startsWith("help")){
						gameObject.help(userInput);
						commandFound = true;
					}
		    		 
		    	
			    if (!commandFound) {
				    System.out.println("ERROR: No Command Found");
				}
				System.out.println("Enter a Command (Display, Change, Rand, List, help, or Exit)");
				System.out.print(">:");
				userInput = scanner.nextLine();	
			}

		}
		catch(IOException e){
			System.out.println("Error: IOException Found");
			System.exit(1);
		}

	}

	public void help(String command){

		System.out.println(command);
		boolean helpExists = false;
		if(command.equals("help")){
			System.out.println("The help Command prints out information about every command and how to execute them." + "\n" + 
				"There is a help command for Display, Change, every possible type of Change, Rand, List, and Exit" + "\n" + "The format for the help command is " + "\n" + ">:help <command>");
			helpExists = true;
			return;
		}

		command = command.substring(5);

		if (command.equals("Display")){
			System.out.println("The Display Command prints any available information about a particular GameElement to the screen. You can display a Melee, Ranged, or Item."
				+ "\n" + "The format for the Display command is " + "\n"  + ">:Display <GameElement>");
			helpExists = true;
		}

		if (command.equals("Change")){
			System.out.println("The Change Command allows you to change an aspect of a GameElement for the current session. These changes will be saved for the current session but lost when you close the program. However, all changes will be written to a text file called changeLog.txt."
				+ " ChangeLog.txt will be saved in the same directory as the files for this program. To Ensure changes are properly saved, use the exit Command to exit the program." + "\n" + " There is a different change command for every stat. Because of the different types of game elements, some commands are item specific. Additionally because some stats require multiple words, they must be in quotations"
				+ "\n" + " Here is the full list of change commands: ChangeName, ChangeRarity(must be in quotes), ChangeTier, ChangeDesc(must be in quotes), ChangeAlt(must be in quotes), ChangeStats(Item Specific, must be in quotes), ChangeSlash(Melee Specific), ChangeStab(Melee Specific), ChangeSweep(Melee Specific), ChangeAccuracy(Ranged Specific)"
				+ ", ChangeSpeed(Ranged Specific), ChangeDamage(Ranged Specific), ChangeRange(Ranged Specific)");
			helpExists = true;
		}

		if (command.equals("ChangeName")){
			System.out.println("ChangeName allows the user to change the name of any game element and does not need to be in quotations" + "\n" + "The format for ChangeName is " + "\n" + 
				">:ChangeName <oldName> <NewName>");
		
			helpExists = true;
		}

		if (command.equals("ChangeRarity")){
			System.out.println("ChangeRarity allows the user to change the rarity of any game element and does need to be in quotations" + "\n" + "The format for ChangeRarity is " + "\n" + 
				">:ChangeRarity '<GameElement>' '<NewRarity>'");
		
			helpExists = true;
		}

		if (command.equals("ChangeTier")){
			System.out.println("ChangeTier allows the user to change the tier of any game element and does not need to be in quotations" + "\n" + "The format for ChangeTier is " + "\n" + 
				">:ChangeName <GameElement> <NewTier>");
		
			helpExists = true;
		}

		if (command.equals("ChangeDesc")){
			System.out.println("ChangeDesc allows the user to change the description of any game element and does need to be in quotations" + "\n" + "The format for ChangeDesc is " + "\n" + 
				">:ChangeDesc '<GameElement>' '<NewDesc>'");
		
			helpExists = true;
		}

		if (command.equals("ChangeAlt")){
			System.out.println("ChangeAlt allows the user to change the alteration of any game element and does need to be in quotations" + "\n" + "The format for ChangeAlt is " + "\n" + 
				">:ChangeAlt '<GameElement>' '<NewAlt>'");
		
			helpExists = true;
		}

		if (command.equals("ChangeStats")){
			System.out.println("ChangeStats allows the user to change the stats of only Item game elements and does need to be in quotations" + "\n" + "The format for ChangeStats is " + "\n" + 
				">:ChangeStats '<Item>' '<NewStat>'");
		
			helpExists = true;
		}

		if (command.equals("ChangeSlash")){
			System.out.println("ChangeSlash allows the user to change the name of only Melee game elements and does not need to be in quotations" + "\n" + "The format for ChangeSlash is " + "\n" + 
				">:ChangeSlash <Melee> <NewSlash>");
		
			helpExists = true;
		}

		if (command.equals("ChangeStab")){
			System.out.println("ChangeStab allows the user to change the name of only Melee game elements and does not need to be in quotations" + "\n" + "The format for ChangeStab is " + "\n" + 
				">:ChangeStab <Melee> <NewStab>");
		
			helpExists = true;
		}

		if (command.equals("ChangeSweep")){
			System.out.println("ChangeSweep allows the user to change the name of only Melee game elements and does not need to be in quotations" + "\n" + "The format for ChangeSweep is " + "\n" + 
				">:ChangeSweep <Melee> <NewStab>");
		
			helpExists = true;
		}

		if (command.equals("ChangeAccuracy")){
			System.out.println("ChangeAccuracy allows the user to change the name of only Ranged game elements and does not need to be in quotations" + "\n" + "The format for ChangeAccuracy is " + "\n" + 
				">:ChangeAccuracy <Ranged> <NewAccuracy>");
		
			helpExists = true;
		}

		if (command.equals("ChangeSpeed")){
			System.out.println("ChangeSpeed allows the user to change the name of only Ranged game elements and does not need to be in quotations" + "\n" + "The format for ChangeSpeed is " + "\n" + 
				">:ChangeSpeed <Ranged> <NewSpeed>");
		
			helpExists = true;
		}

		if (command.equals("ChangeDamage")){
			System.out.println("ChangeDamage allows the user to change the name of only Ranged game elements and does not need to be in quotations" + "\n" + "The format for ChangeDamage is " + "\n" + 
				">:ChangeDamage <Ranged> <NewDamage>");
		
			helpExists = true;
		}

		if (command.equals("ChangeRange")){
			System.out.println("ChangeRange allows the user to change the name of only Ranged game elements and does not need to be in quotations" + "\n" + "The format for ChangeRange is " + "\n" + 
				">:ChangeRange <Ranged> <NewRange>");
		
			helpExists = true;
		}

		if (command.equals("Rand")){
			System.out.println("Rand produces a random list of GameElements. The user can specify how many GameElements will be in the list, and which rarieties and tiers the GameElements will come from. The user must specify which rarity the items will come from but a tier is completely optional. "
				+ "The user must also first put a number specifing how many GameElements should be in the list. Rand will also fail if the user asks for more items than are available total in the rarities and tiers specified. The user can then put letters specifying the raritys and numbers specifing the tiers. C represents Common (Grey), R represents Rare (Green) and "
				+ "UR represents Ulra Rare (Purple)." + " 1 represents Tier I, 2 represents Tier II and 3 represents Tier III. The user must also put commas seperating the values. " + "\n"
				+ "An example function call to Rand is " + "\n" + ">:Rand 5,C,2" + "\n" + "This represents a random list of 5 common tier two items" + "\n" + "The format of Rand is " + "\n" + ">:Rand <GameElementCount> <ListOfRarities> <ListOfTiers>");
			helpExists = true;	
		}

		if (command.equals("List")){
			System.out.println("List can be used to produce a full list of certain GameElements, or a more specific list. There are four ways list can be used to produce a full list: List, List Melee, List Ranged, and List Item. These commands will list out the entire list of gameElements of that particular type. " 
				+ " The user can also use List to specify which rarieties and tiers the GameElements will come from. "
				+ " The user must put letters specifying the raritys and numbers specifing the tiers. C represents Common (Grey), R represents Rare (Green) and "
				+ "UR represents Ulra Rare (Purple)." + " 1 represents Tier I, 2 represents Tier II and 3 represents Tier III. The user must also put commas seperating the values. " + "\n"
				+ "An example function call to List is " + "\n" + ">:List C,2" + "\n" + "This function call would list out all common tier two items" + "\n" + "The format of List is " + "\n" + ">:List  <ListOfRarities> <ListOfTiers>" + "\n" + "List can also be used with these commands: List, List Melee, List Ranged, List Item"
				+ "\n" + "For example, List Melee would list out the full List of Melee items");
			helpExists = true;	
		}

		if (command.equals("Exit")){
			System.out.println("Exit is used to Exit the program" + "\n" + "The format of Exit is " + "\n" + ">:help Exit");
			helpExists = true;
		}

		if (!helpExists){
			System.out.println("No Help Topics Match");
		}
	}
}

