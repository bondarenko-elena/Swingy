package com.swingy.controller;

import com.swingy.Main;
import com.swingy.model.utils.Hero;
import com.swingy.view.GuiInterface;
import com.swingy.model.map.Maps;
import com.swingy.database.DBMethods;
import com.swingy.model.utils.HeroCreator;
import com.swingy.model.utils.HeroFactory;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {
    private Hero hero;
    private static Controller instance;

    public static Controller getInstance() {
        if ( instance == null ) {
            instance = new Controller();
        }
        return instance;
    }

    private Hero createEnemy() {
        String[] enemies = new String[]{ "Serpent", "Goblin" };
        int heroClass = (int) ( Math.random() * 2 + 4 );
        String enemyName = enemies[ThreadLocalRandom.current().nextInt( enemies.length )];
        HeroCreator heroCreator = HeroFactory.newHero( heroClass, enemyName );
        assert heroCreator != null;
        heroCreator.createHero();
        Hero enemy = heroCreator.getHero();
        System.out.println( "This guy is your enemy." );
//        displayStatistics( enemy );
        return enemy;
    }

    private void attacks( Hero enemy ) {
        System.out.println( "hero hp: " + this.hero.getHitPoints() + "\n" );
        System.out.println( "enemy hp: " + enemy.getHitPoints() + "\n" );

        if ( ThreadLocalRandom.current().nextInt( 0, 10 ) >= 4 ) {
            if ( this.hero.getAttack() > enemy.getDefense() ) {
                enemy.setHitPoints( enemy.getHitPoints() - ( this.hero.getAttack() - enemy.getDefense() ) );
                System.out.println( this.hero.getAttack() );
                System.out.println( "enemy attacked 1\n" );
            } else if ( ThreadLocalRandom.current().nextInt( 0, 10 ) <= 3 ) {
                enemy.setHitPoints( enemy.getHitPoints() - this.hero.getAttack() );
                System.out.println( "enemy attacked 2\n" );
            }
        } else {
            if ( enemy.getAttack() > this.hero.getDefense() ) {
                this.hero.setHitPoints( this.hero.getHitPoints() - ( enemy.getAttack() - this.hero.getDefense() ) );
                System.out.println( enemy.getAttack() );
                System.out.println( "hero attacked 1\n" );
            } else if ( ThreadLocalRandom.current().nextInt( 0, 10 ) <= 2 ) {
                this.hero.setHitPoints( this.hero.getHitPoints() - enemy.getAttack() );
                System.out.println( "hero attacked 2\n" );
            }
        }

        System.out.println( "hero hp: " + this.hero.getHitPoints() + "\n" );
        System.out.println( "enemy hp: " + enemy.getHitPoints() + "\n" );
    }

    private int clashOfHeroes() {
        int toReturn = 2;
        Hero enemy = createEnemy();
        attacks( enemy );
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

    public void onFightYesButtonButtonPressed( Maps map ) {
        int toReturn = clashOfHeroes();
        if ( toReturn == 1 ) {
            GuiInterface gui = new GuiInterface();
            map.levelUPGui();
            gui.continueGameLevelUp( map );
        } else {
            GuiInterface gui = new GuiInterface();
            gui.endGame( "Fight is over. You lost. GG WP" );
//                System.exit( 0 );
        }
    }

    public void onFightNoButtonButtonPressed( Maps map ) {
        int toReturn;
        Random random = new Random();
        random.nextInt( 2 );
        int rand = random.nextInt( 2 );
        if ( rand == 1 ) {
//            System.out.println( "Sorry, the odds aren’t on your side, you must fight the villain." );
            toReturn = clashOfHeroes();
            if ( toReturn == 1 ) {
                GuiInterface gui = new GuiInterface();
                map.levelUPGui();
                gui.continueGameLevelUp( map );
            } else {
                GuiInterface gui = new GuiInterface();
                gui.endGame( "Fight is over. You lost. GG WP" );
//                    System.exit( 0 );
            }
        }
    }

    public void onNorthButtonPressed( Maps map ) {
        String move = ( map.moveGui( "n" ) );
        if ( move.equalsIgnoreCase( "end" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.endGame( "Game Over" );
        }
        if ( move.equalsIgnoreCase( "fight" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.fight( map );
        }
        if ( move.equalsIgnoreCase( "continue" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.continueGame( map );
        }
    }

    public void onSouthButtonPressed( Maps map ) {
        String move = map.moveGui( "s" );
        if ( move.equalsIgnoreCase( "end" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.endGame( "Game Over" );
        }
        if ( move.equalsIgnoreCase( "fight" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.fight( map );
        }
        if ( move.equalsIgnoreCase( "continue" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.continueGame( map );
        }
    }

    public void onWestButtonPressed( Maps map ) {
        String move = map.moveGui( "w" );
        if ( move.equalsIgnoreCase( "end" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.endGame( "Game Over" );
        }
        if ( move.equalsIgnoreCase( "fight" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.fight( map );
        }
        if ( move.equalsIgnoreCase( "continue" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.continueGame( map );
        }
    }

    public void onEastButtonPressed( Maps map ) {
        String move = map.moveGui( "e" );
        if ( move.equalsIgnoreCase( "end" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.endGame( "Game Over" );
        }
        if ( move.equalsIgnoreCase( "fight" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.fight( map );
        }
        if ( move.equalsIgnoreCase( "continue" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.continueGame( map );
        }
    }

    public void onOkButtonButtonPressed( Maps map ) {
        GuiInterface gui = new GuiInterface();
        gui.continueGame( map );
    }

    public void onHeroCreateButtonPressed() {
        GuiInterface gui = new GuiInterface();
        gui.displayHeroCreate();
    }

    public void onSwitchViewButtonPressed() {

        GuiInterface.switchView();
    }

    public void onHeroSelectSaveButtonPressed( Hero hero ) {
        this.hero = hero;
        GuiInterface gui = new GuiInterface();
        gui.displayHeroSelected( hero.getHeroName() );
    }

    public void onHeroSelectButtonPressed() {
        GuiInterface gui = new GuiInterface();
        hero = gui.displayHeroSelect();
    }

    public void onStartGameButtonPressed() {
        GuiInterface gui = new GuiInterface();
        gui.startGame( hero );
    }

    public void onHeroSaveButtonPressed(
            JTextField heroClass,
            JTextField heroName,
            DBMethods dbData
    ) {
        HeroCreator heroCreator = HeroFactory.newHero(
                Integer.parseInt( heroClass.getText() ),
                heroName.getText()
        );
        assert heroCreator != null;
        Main.hero = heroCreator.getHero();
        dbData.addHero(
                Main.hero.getHeroName(),
                Main.hero.getHeroClass(),
                Main.hero.getHeroLevel(),
                Main.hero.getExperience(),
                Main.hero.getHitPoints(),
                Main.hero.getAttack(),
                Main.hero.getDefense()
        );
    }
}
