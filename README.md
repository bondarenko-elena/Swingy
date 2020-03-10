# Swingy
The goal of this project is implementation of a minimalistic text-based RPG game with the SWING framework.
The program follows the Model-View-Controller architecture and allows witching between the console view and GUI view.

# Gameplay
A player can have multiple heroes of different types. We leave it at you to name the herotypes and fine tune the different starting stats between them, When the player starts thegame he has 2 options:
  •Create a hero
  •Select a previously created hero.
In either case, the player can see the hero stats:
  •Hero name
  •Hero class
  •Level
  •Experience
  •Attack
  •Defense
  •Hit Points
After choosing a hero the actual game begins. The hero needs to navigate a squaremap with the size calculated by the formula (level-1)*5+10-(level%2)*. For example ahero of level 7 will be placed on a 39X39 map.
The initial position of the hero is in the center of the map. He wins the game if hereaches on of the borders of the map. Each turn he can move one position in one of the4 four directions:
  •North
  •East
  •South
  •West
When a map is generated, villains of varying power will be spread randomly over themap. When a hero moves to a position occupied by a villain, the hero has 2 options:
  •Fight, which engages him in a battle with the villain
  •Run, which gives him a 50% chance of returning to the previous position. If theodds aren’t on his side, he must fight the villain.
You will need to simulate the battle between the hero and monster and present theuser the outcome of the battle. We leave it at you to find a nice simulation algorythmthat decides based on the hero and monster stats, who will win. You can include a small"luck", component in the algo in order to make the game more entertaining.
If a hero looses a battle, he dies and also looses the mission.
If a hero wins a battle, he gains:
  •Experience points, based on the villain power.  Of course, he will level up if hereaches the next level experience.
  •An artifact, which he can keep or leave. Of course, winning a battle doesn’t guar-antee that an artefact will be droped and the quality of the artefact also variesdepending on the villain’s strenght.
Leveling up is based on the following formulalevel*1000+(level−1)2*450. So thenecessary experience to level up will follow this pattern:
    •Level 1 - 1000 XP
    •Level 2 - 2450 XP
    •Level 3 - 4800 XP
    •Level 4 - 8050 XP
    •Level 5 - 12200 XP
Bonus part:
    •You persist the user’s heroes in a relational database, instead of a text file.
    •You can switch between console view and GUI view at runtime, without closing thegame.
![alt tag](https://github.com/bondarenko-elena/7_Fdf/blob/master/Screen%20Shot%202018-12-02%20at%205.46.10%20PM.png)
