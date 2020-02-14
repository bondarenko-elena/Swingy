package com.swingy.view;

import com.swingy.database.DBMethods;
import com.swingy.model.map.Maps;
import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ConsoleInterface implements Display {
    private Hero[] hero = null;
    private final DBMethods dbData = new DBMethods();


    private void displayHeroClasses() {
        // TODO collapse into one
        System.out.println( "Choose Hero class:" );
        System.out.println( "   Class       Attack      Defense     HP" );
        System.out.println( "1. Witcher     150         120          250" );
        System.out.println( "2. Mage         110         100          250" );
        System.out.println( "3. Fighter       140         100          300" );
    }

    private boolean checkUniqueHeroName( final String heroName ) {
/*        return dbData.selectAllHeroNames().stream()
                     .filter( name -> name.equalsIgnoreCase( heroName ) )
                     .findAny()
                     .isPresent();*/
        ArrayList<String> listNames = dbData.selectAllHeroNames();
        for ( String name : listNames ) {
            if ( name.equalsIgnoreCase( heroName ) ) {
                System.out.println( heroName + " is already in use." );
                return true;
            }
        }
        return false;
    }

    @Override
    public void displayHeroCreate( int tumbler ) {
        int heroClass = 0;
        String heroName = "";
        BufferedReader br = null;

        try {
            br = new BufferedReader( new InputStreamReader( System.in ) );
            while ( ( heroName.length() < 3 || heroName.length() > 10 )
                    || ( checkUniqueHeroName( heroName ) ) ) {
                System.out.println( "Enter hero's name (should be 3 chars min and 10 chars max) :" );
                heroName = br.readLine();
            }
            displayHeroClasses();
            while ( heroClass != 1 && heroClass != 2 && heroClass != 3 ) {
                heroClass = parseString( heroClass, br );
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
            hero[0] = HeroFactory.newHero( heroClass, heroName );
            dbData.addHero( hero[0] );
            System.out.println( "Hero created successfully" );
        }
    }

    private void displayChooseOptions() {
        // todo collapse into one
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
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        displayChooseOptions();
        try {
            while ( optionChoice != 1 && optionChoice != 2 && optionChoice != 3 ) {
                optionChoice = parseString( optionChoice, br );
            }
            if ( optionChoice == 1 ) {
                System.out.println( "Here is a list of saved heroes. Choose a hero by id." );
                dbData.selectAll();
                int heroCount = dbData.selectCountSavedHeroes();
                String exceptionMessage = "There is no hero with this ID. Choose a hero from the list above.";
                // todo (optionally) rethink logic
                while ( heroChoice < 1 || heroChoice > heroCount ) {
                    try {
                        heroChoice = Integer.parseInt( br.readLine() );
                        if ( heroChoice > 0 && heroChoice <= heroCount ) {
                            break;
                        }
                        System.out.println( exceptionMessage );
                    } catch ( NumberFormatException ex ) {
                        System.out.println( exceptionMessage );
                    }
                }
                hero[0] = dbData.getHerodb( heroChoice );
            } else if ( optionChoice == 2 ) {
                displayHeroCreate( 0 );
            } else {
                System.out.println( "Gui view is opened" );
                GuiInterface gui = new GuiInterface();
                gui.runGame( hero );
                return null;
            }
        } catch ( Exception ex ) {
            System.out.println( "ERROR: Invalid input" );
            ex.printStackTrace();
            try {
                br.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            System.exit( 1 );
        }
        System.out.println( "Here is chosen hero." );
        System.out.println( hero[0] );
        return hero[0];
    }

    private int parseString( int variable, BufferedReader br ) throws IOException {
        try {
            variable = Integer.parseInt( br.readLine() );
            if ( variable != 1 && variable != 2 && variable != 3 ) {
                System.out.println( "Choose 1, 2 or 3." );
            }
        } catch ( NumberFormatException e ) {
            System.out.println( "Choose 1, 2 or 3." );
        }
        return variable;
    }

    private boolean clashOfHeroes() {
        Hero enemy = createEnemy();
        while ( enemy.getHitPoints() > 0 && hero[0].getHitPoints() > 0 ) {
            attacks( enemy );
        }
        return ( enemy.getHitPoints() <= 0 ) && ( hero[0].getHitPoints() > 0 );
    }

    @Override
    public void runGame( Hero heroToRun[] ) {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        this.hero[0] = heroToRun[0];
        System.out.println( "Here is your map." );
        Maps map = new Maps( this.hero[0], Maps.View.CONSOLE );
        while ( true ) {
            try {
                System.out.println(
                        "You can move through the map by keyboard: N - north, S - south, E - east, W - west. Choose direction: " );
                String readMove = br.readLine();
                List<String> moves = Arrays.asList( "n", "s", "e", "w" );
                while ( !moves.contains( readMove.toLowerCase() ) ) {
                    System.out.println( "Use N, S, E or W for direction. Choose direction: " );
                    readMove = br.readLine();
                }
                String move = map.move( readMove, Maps.View.CONSOLE );
                if ( move.equalsIgnoreCase( "end" ) ) {
                    System.out.println( "You finished the game. GG WP." );
                    break;
                }
                if ( move.equalsIgnoreCase( "fight" ) ) {
                    System.out.println(
                            "Do you want to fight? Choose Y - yes, N - no. Make your choice:" );
                    String fight = br.readLine();
                    while ( !( fight.equalsIgnoreCase( "y" ) || fight.equalsIgnoreCase(
                            "n" ) ) ) {
                        System.out.println( "Use Y - yes, N - no. Make your choise:" );
                        fight = br.readLine();
                    }
                    if ( fight.equalsIgnoreCase( "y" ) ) {
                        doFight( map );
                    } else if ( fight.equalsIgnoreCase( "n" ) ) {
                        Random random = new Random();
                        if ( random.nextInt( 2 ) == 0 ) {
                            System.out.println(
                                    "Sorry, the odds aren’t on your side, you must fight the enemy." );
                            doFight( map );
                        } else {
                            System.out.println( "You stayed on the same place." );
                        }
                    }
                }
            } catch ( IOException ioe ) {
                ioe.printStackTrace();
            }
        }
        try {
            br.close();
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }
    }

    private void doFight( Maps map ) {
        if ( clashOfHeroes() ) {
            System.out.println( "Fight is over. You won." );
            map.levelUP( Maps.View.CONSOLE );
        } else {
            System.out.println( "Fight is over. You lost. GG WP" );
            System.exit( 0 );
        }
    }

    private Hero createEnemy() {
        String[] enemies = new String[]{ "Serpent", "Goblin" };
        int heroClass = (int) ( Math.random() * 2 + 4 );
        String enemyName = enemies[ThreadLocalRandom.current().nextInt( enemies.length )];
        Hero enemy = HeroFactory.newHero( heroClass, enemyName );
        System.out.println( "This guy is your enemy:" );
        System.out.println( enemy );
        return enemy;
    }

    private void enemyAttacked( Hero enemy ) {
        if ( this.hero[0].getAttack() > enemy.getDefense() ) {
            enemy.setHitPoints( enemy.getHitPoints() - ( this.hero[0].getAttack() - enemy.getDefense() ) );
        }
        if ( ThreadLocalRandom.current().nextInt( 0, 10 ) <= 3 ) {
            enemy.setHitPoints( enemy.getHitPoints() - this.hero[0].getAttack() );
        }
    }

    private void heroAttacked( Hero enemy ) {
        if ( enemy.getAttack() > this.hero[0].getDefense() ) {
            this.hero[0].setHitPoints( this.hero[0].getHitPoints() - ( enemy.getAttack() - this.hero[0]
                    .getDefense() ) );
        } else if ( ThreadLocalRandom.current().nextInt( 0, 10 ) <= 2 ) {
            this.hero[0].setHitPoints( this.hero[0].getHitPoints() - enemy.getAttack() );
        }
    }

    private void attacks( Hero enemy ) {
        if ( ThreadLocalRandom.current().nextInt( 0, 10 ) >= 4 ) {
            enemyAttacked( enemy );
        } else {
            heroAttacked( enemy );
        }
    }
}
