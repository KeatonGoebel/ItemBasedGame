# RolePlayingGame
This is a Java command line interface program meant to support an item-based interactive role playing game. The idea of the game is that players will have the ability to imagine their characters in an imaginary setting. In the game, they will be given a wide variety of items with certain statistics that will be important in combat. This program will make the development and play of the game easier by automating certain tasks. Like for example, displaying item statistics, listing out certain types of items, and randomly picking out certain types of items. When this program is running, the user will see ">:" indicating that it is awaiting input from the user. Users must input commands exactly as is into the terminal. This program was written alongside other people who helped design the game rules and the items. 

## RolePlayingGame Commands

* help: The help commands prints out information about every command and how to execute them

* Display: The Display Command prints any available information about a particular item to the screen.

* Change: The Change Command allows the user to change the statistics about a particular item for the current session. While the change is not permanent, it will be saved to an external file. To ensure changes are saved correctly, use the Exit command to exit the program. 

* Rand: The Rand Command allows the user to produce a random list of items depending on the users specifications

* List: The List Command allows the user to list out all the items or all of the items based on the users specifications

* Exit: The Exit command Exits the program

For more information, check the help descriptions inside the program. 

### RolePlayingGame Example Inputs and Outputs 

This is an example of how to randomly select one common tier three item. 

```
Enter a Command (Display, Change, Rand, List, help or Exit)
>:Rand 1,C,3
Count: 1
Tier: III
Rarity: Common (Grey)

Tenugui
Enter a Command (Display, Change, Rand, List, help, or Exit)
>:
```


