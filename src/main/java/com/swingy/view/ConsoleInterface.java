package com.swingy.view;

import com.swingy.database.DBMethods;
import com.swingy.model.map.Maps;
import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ConsoleInterface implements Display {
    private Hero hero = null;

    public Hero getHero() {
        return hero;
    }

    public void setHero( Hero hero ) {
        this.hero = hero;
    }

    private void displayHeroClasses() {
        System.out.println( "Choose Hero class:" );
        System.out.println( "   Class       Attack      Defense     HP" );
        System.out.println( "1. Witcher     150         120          250" );
        System.out.println( "2. Mage         110         100          250" );
        System.out.println( "3. Fighter       140         100          300" );
    }

    private void addHeroToDB( Hero hero, DBMethods dbData ) {
        dbData.addHero(
                hero.getName(),
                hero.getHeroClass(),
                hero.getLevel(),
                hero.getExperience(),
                hero.getHitPoints(),
                hero.getAttack(),
                hero.getDefense()
        );
    }

    @Override
    public void displayHeroCreate( int tumbler ) {
        int heroClass = 0;
        String heroName = "";
        DBMethods dbData = new DBMethods();
        BufferedReader br = null;

        try {
            br = new BufferedReader( new InputStreamReader( System.in ) );
            while ( heroName.length() < 3 || heroName.length() > 10 ) {
                System.out.println( "Enter hero's name (should be 3 chars min and 10 chars max) :" );
                heroName = br.readLine();
            }
            displayHeroClasses();
            while ( heroClass != 1 && heroClass != 2 && heroClass != 3 ) {
                heroClass = parseString( heroClass, br, "Choose 1, 2 or 3." );
            }
        } catch ( Exception e ) {
            System.out.println( "ERROR: Invalid input for hero class." );
            try {
                if ( br != null ) {
                    br.close();
                }
            } catch ( IOException ioe ) {
                ioe.printStackTrace();
            }
            System.exit( 1 );
        } finally {
            hero = HeroFactory.newHero( heroClass, heroName );
            addHeroToDB( hero, dbData );
            System.out.println( "Hero created successfully" );
        }
    }

    private void displayChooseOptions() {
        System.out.println( "---WELCOME TO THE GAME---" );
        System.out.println( "Choose an option:" );
        System.out.println( "1. Select a previously created hero" );
        System.out.println( "2. Create a hero" );
        System.out.println( "3. Switch view" );
    }

    @Override
    public Hero displayStart() {
        int optionChoice = 0;
        int heroChoice = 0;
        DBMethods dbData = new DBMethods();
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        displayChooseOptions();
        try {
            while ( optionChoice != 1 && optionChoice != 2 && optionChoice != 3 ) {
                optionChoice = parseString( optionChoice, br, "Choose 1, 2 or 3." );
            }
            if ( optionChoice == 1 ) {
                System.out.println( "Here is a list of saved heroes. Choose a hero by id." );
                dbData.selectAll();
                int heroCount = dbData.selectCountSavedHeroes();
                String exceptionMessage = "There is no hero with this ID. Choose a hero from the list above.";
                while ( heroChoice < 1 || heroChoice > heroCount ) {
                    try {
                        heroChoice = Integer.parseInt( br.readLine() );
                        if ( heroChoice > 0 && heroChoice < heroCount ) {
                            break;
                        }
                        System.out.println( exceptionMessage );
                    } catch ( NumberFormatException ex ) {
                        System.out.println(
                                "There is no hero with this ID. Choose a hero from the list above." );
                    }
                }
                hero = dbData.getHerodb( heroChoice );
                System.out.println( "Here is choosen hero." );
                System.out.println( hero.toString() );
                return hero;
            } else if ( optionChoice == 2 ) {
                displayHeroCreate( 0 );
                System.out.println( "Here is choosen hero." );
                System.out.println( hero.toString() );
                return hero;
            } else if ( optionChoice == 3 ) {
                System.out.println( "Gui view is opened" );
                GuiInterface gui = new GuiInterface();
                gui.runGame();
                return null;
            }
        } catch ( Exception ex ) {
            System.out.println( "ERROR: Invalid input" );
            System.out.println( ex.getMessage() );
            System.exit( 1 );
        }
        try {
            if ( br != null ) {
                br.close();
            }
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }
        return null;
    }

    private int parseString( int variable, BufferedReader br, String msg ) throws IOException {
        try {
            variable = Integer.parseInt( br.readLine() );
            if ( variable != 1 && variable != 2 && variable != 3 ) {
                System.out.println( msg );
            }
        } catch ( NumberFormatException e ) {
            System.out.println( msg );
        }
        return variable;
    }

    //TODO check logic, remove superfluous
    private int clashOfHeroes() {
        int toReturn = 2;
        Hero enemy = createEnemy();
//        attacks( enemy );
        boolean fight;
        while ( enemy.getHitPoints() > 0 && this.hero.getHitPoints() > 0 ) {
            attacks( enemy );
        }
        fight = this.hero.getHitPoints() > 0 && enemy.getHitPoints() > 0;
        while ( fight ) {
            attacks( enemy );
            while ( enemy.getHitPoints() > 0 && this.hero.getHitPoints() > 0 ) {
                attacks( enemy );
            }
            fight = this.hero.getHitPoints() > 0 && enemy.getHitPoints() > 0;
        }
        if ( this.hero.getHitPoints() <= 0 && ( enemy.getHitPoints() > 0 ) ) {
            toReturn = 0;
        } else if ( ( enemy.getHitPoints() <= 0 ) && ( this.hero.getHitPoints() > 0 ) ) {
            toReturn = 1;
        }
        return toReturn;
    }

    @Override
    public void runGame() {
    }

    public void runGame( Hero heroToRun ) {
        String move;
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        int toReturn;

        this.hero = heroToRun;
        if ( heroToRun != null ) {
            System.out.println( "Here is your map." );
            Maps map = new Maps( this.hero, Maps.View.CONSOLE );
            while ( true ) {
                try {
                    System.out.println(
                            "You can move through the map by keyboard: N - north, S - south, E - east, W - west. Choose direction: " );
                    String readMove = br.readLine();
                    while ( !( readMove.equalsIgnoreCase( "n" ) || readMove.equalsIgnoreCase( "s" ) || readMove
                            .equalsIgnoreCase( "e" ) || readMove.equalsIgnoreCase( "w" ) ) ) {
                        System.out.println(
                                "Use N, S, E or W for direction. Choose direction: " );
                        readMove = br.readLine();
                    }
                    move = map.move( readMove, Maps.View.CONSOLE );
                    if ( move.equalsIgnoreCase( "end" ) ) {
                        System.out.println( "You finished the game. GG WP." );
                        break;
                    }
                    if ( move.equalsIgnoreCase( "fight" ) ) {
                        System.out.println(
                                "Do you want to fight? Choose Y - yes, N - no. Make your choise:" );
                        String fight = br.readLine();
                        while ( !( fight.equalsIgnoreCase( "y" ) || fight.equalsIgnoreCase(
                                "n" ) ) ) {
                            System.out.println( "Use Y - yes, N - no. Make your choise:" );
                            fight = br.readLine();
                        }
                        if ( fight.equalsIgnoreCase( "y" ) ) {
                            toReturn = clashOfHeroes();
                            if ( toReturn == 1 ) {
                                System.out.println( "Fight is over. You won." );
                                map.levelUP( Maps.View.CONSOLE );
                            } else {
                                System.out.println( "Fight is over. You lost. GG WP" );
                                System.exit( 0 );
                            }
                        } else if ( fight.equalsIgnoreCase( "n" ) ) {
                            Random random = new Random();
                            int rand = random.nextInt( 2 );
                            if ( rand == 0 ) {
                                System.out.println(
                                        "Sorry, the odds aren’t on your side, you must fight the enemy." );
                                toReturn = clashOfHeroes();
                                if ( toReturn == 1 ) {
                                    System.out.println( "Fight is over. You won." );
                                    map.levelUP( Maps.View.CONSOLE );
                                } else {
                                    System.out.println( "Fight is over. You lost. GG WP" );
                                    System.exit( 0 );
                                }
                            } else if ( rand == 1 ) {
                                System.out.println( "You stayed on the same place." );
                            }
                        }
                    }
                } catch ( IOException ioe ) {
                    ioe.printStackTrace();
                }
            }
            try {
                if ( br != null ) {
                    br.close();
                }
            } catch ( IOException ioe ) {
                ioe.printStackTrace();
            }
        }
        System.exit( 1 );
    }

    private Hero createEnemy() {
        String[] enemies = new String[]{ "Serpent", "Goblin" };
        int heroClass = (int) ( Math.random() * 2 + 4 );
        String enemyName = enemies[ThreadLocalRandom.current().nextInt( enemies.length )];
        Hero enemy = HeroFactory.newHero( heroClass, enemyName );
        System.out.println( "This guy is your enemy:" );
        System.out.println( enemy.toString() );
        return enemy;
    }

    private void displayHP( Hero enemy, int tumbler ) {
        if ( tumbler == 0 ) {
            System.out.println( "HP before attack" );
        } else {
            System.out.println( "HP after attack" );
        }
        System.out.println( "Hero HP -> " + this.hero.getHitPoints() );
        System.out.println( "Enemy HP -> " + enemy.getHitPoints() );
    }

    private void enemyAttacked( Hero enemy ) {
        System.out.println("Fight is starting!");
        if ( this.hero.getAttack() > enemy.getDefense() ) {
            enemy.setHitPoints( enemy.getHitPoints() - ( this.hero.getAttack() - enemy.getDefense() ) );
            System.out.println( "Enemy has been attacked!" );
        } else if ( ThreadLocalRandom.current().nextInt( 0, 10 ) <= 3 ) {
            enemy.setHitPoints( enemy.getHitPoints() - this.hero.getAttack() );
            System.out.println( "Enemy has been attacked!" );
        }
    }

    private void heroAttacked( Hero enemy ) {
        if ( enemy.getAttack() > this.hero.getDefense() ) {
            this.hero.setHitPoints( this.hero.getHitPoints() - ( enemy.getAttack() - this.hero.getDefense() ) );
            System.out.println( "Hero has been attacked!" );
        } else if ( ThreadLocalRandom.current().nextInt( 0, 10 ) <= 2 ) {
            this.hero.setHitPoints( this.hero.getHitPoints() - enemy.getAttack() );
            System.out.println( "Hero has been attacked!" );
        }
    }

    //TODO check attack statistic
    private void attacks( Hero enemy ) {
        displayHP( enemy, 0 );
        if ( ThreadLocalRandom.current().nextInt( 0, 10 ) >= 4 ) {
            enemyAttacked( enemy );
        } else {
            heroAttacked( enemy );
        }
        displayHP( enemy, 1 );
    }
}
