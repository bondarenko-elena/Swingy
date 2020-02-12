package com.swingy.controller;

import com.swingy.Main;
import com.swingy.database.DBMethods;
import com.swingy.model.map.Maps;
import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroFactory;
import com.swingy.view.GuiInterface;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//TODO check conditions and sout
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
        int heroClass = (int) ( Math.random() * 2 + 4 );
        Hero enemy = HeroFactory.newHero( heroClass, null );
//        System.out.println( "This guy is your enemy." );
//        displayStatistics( enemy );
        return enemy;
    }

    private void attacks( Hero enemy ) {
        System.out.println("HP before attack:");
        System.out.println( "Hero HP -> " + this.hero.getHitPoints() + "\n" );
        System.out.println( "Enemy HP -> " + enemy.getHitPoints() + "\n" );

        ThreadLocalRandom random = ThreadLocalRandom.current();
        if ( random.nextInt( 0, 10 ) >= 4 ) {
            if ( this.hero.getAttack() > enemy.getDefense() ) {
                enemy.setHitPoints( enemy.getHitPoints() - ( this.hero.getAttack() - enemy.getDefense() ) );
                System.out.println( this.hero.getAttack() );
                System.out.println( "Enemy has been attacked!" );
                System.out.println();
            } else if ( random.nextInt( 0, 10 ) <= 3 ) {
                enemy.setHitPoints( enemy.getHitPoints() - this.hero.getAttack() );
                System.out.println( "Enemy has been attacked!" );
                System.out.println();
            }
        } else {
            if ( enemy.getAttack() > this.hero.getDefense() ) {
                this.hero.setHitPoints( this.hero.getHitPoints() - ( enemy.getAttack() - this.hero.getDefense() ) );
                System.out.println( enemy.getAttack() );
                System.out.println( "Hero has been attacked!" );
                System.out.println();
            } else if ( random.nextInt( 0, 10 ) <= 2 ) {
                this.hero.setHitPoints( this.hero.getHitPoints() - enemy.getAttack() );
                System.out.println( "Hero has been attacked!" );
                System.out.println();
            }
        }

        System.out.println("HP after attack:");
        System.out.println( "Hero HP -> " + this.hero.getHitPoints() );
        System.out.println();
        System.out.println( "Enemy HP -> " + enemy.getHitPoints() );
        System.out.println();
    }

    private int clashOfHeroes() {
        Hero enemy = createEnemy();
        while ( this.hero.getHitPoints() > 0 && enemy.getHitPoints() > 0 ) {
            attacks( enemy );
        }
        return this.hero.getHitPoints() <= 0 ? 0 : 1;
    }

    public void onFightYesButtonButtonPressed( Maps map ) {
        if ( clashOfHeroes() == 1 ) {
            GuiInterface gui = new GuiInterface();
            map.levelUP( Maps.View.GUI );
            gui.continueGameLevelUp( map );
        } else {
            GuiInterface gui = new GuiInterface();
            gui.endGame( "Fight is over. You lost. GG WP" );
        }
    }

    //TODO fix logic for no fight
    public void onFightNoButtonButtonPressed( Maps map ) {
        Random random = new Random();
        GuiInterface gui = new GuiInterface();
        if ( random.nextInt( 2 ) == 1 ) {
            //TODO show info for user
//            System.out.println( "Sorry, the odds aren’t on your side, you must fight the villain." );
            if ( clashOfHeroes() == 1 ) {
                map.levelUP( Maps.View.GUI );
                gui.continueGameLevelUp( map );
            } else {
                gui.endGame( "Fight is over. You lost. GG WP" );
            }
        }
        else {
            gui.continueGame( map );
        }
    }

    public void onNorthButtonPressed( Maps map ) {
        String move = map.move( "n", Maps.View.GUI );
        doMove( map, move );
    }

    public void onSouthButtonPressed( Maps map ) {
        String move = map.move( "s", Maps.View.GUI );
        doMove( map, move );
    }

    public void onWestButtonPressed( Maps map ) {
        String move = map.move( "w", Maps.View.GUI );
        doMove( map, move );
    }

    public void onEastButtonPressed( Maps map ) {
        String move = map.move( "e", Maps.View.GUI );
        doMove( map, move );
    }

    private void doMove( Maps map, String move ) {
        GuiInterface gui = new GuiInterface();
        if ( move.equalsIgnoreCase( "end" ) ) {
            gui.endGame( "You reached on of the borders of the map and win. Game completed. GG WP" );
        }
        if ( move.equalsIgnoreCase( "fight" ) ) {
            gui.fight( map );
        }
        if ( move.equalsIgnoreCase( "continue" ) ) {
            gui.continueGame( map );
        }
    }

    public void onOkButtonButtonPressed( Maps map ) {
        GuiInterface gui = new GuiInterface();
        gui.continueGame( map );
    }

    public void onHeroCreateButtonPressed() {
        GuiInterface gui = new GuiInterface();
        gui.displayHeroCreate( 0 );
    }

    public void onSwitchViewButtonPressed() {
        GuiInterface.switchView();
    }

    private boolean validateHeroSelect( Hero hero ) {
        if ( hero == null ) {
            return false;
        }
        return true;
    }

    public void onHeroSelectSaveButtonPressed( Hero hero, int heroId ) {
        this.hero = hero;
        //TODO check bounds and str
        if (validateHeroSelect( hero) == false || (heroId < 1 || heroId > 100)) {
            GuiInterface gui = new GuiInterface();
            gui.displayHeroSelect();
        }
        else {
            GuiInterface gui = new GuiInterface();
            gui.displayHeroSelected( hero.getName() );
        }
    }

    public void onHeroSelectButtonPressed() {
        GuiInterface gui = new GuiInterface();
        hero = gui.displayHeroSelect();
    }

    public void onStartGameButtonPressed() {
        GuiInterface gui = new GuiInterface();
        gui.startGame( hero );
    }

    private boolean validateFields( String heroClass, String heroName ) {
        if ( ( !( ( heroClass.equalsIgnoreCase( "1" ) || heroClass.equalsIgnoreCase( "2" ) || heroClass
                .equalsIgnoreCase( "3" ) ) ) ) || ( heroName.length() < 3 || heroName.length() > 10 ) ) {
            return false;
        }
        return true;
    }

    public void onHeroSaveButtonPressed(
            JTextField heroClass,
            JTextField heroName,
            DBMethods dbData
    ) {
        if ( validateFields( heroClass.getText(), heroName.getText() ) == false ) {
            GuiInterface gui = new GuiInterface();
            gui.displayHeroCreate( 1 );
        } else {
            Main.hero = HeroFactory.newHero(
                    Integer.parseInt( heroClass.getText() ),
                    heroName.getText()
            );
            dbData.addHero(
                    Main.hero.getName(),
                    Main.hero.getHeroClass(),
                    Main.hero.getLevel(),
                    Main.hero.getExperience(),
                    Main.hero.getHitPoints(),
                    Main.hero.getAttack(),
                    Main.hero.getDefense()
            );
            GuiInterface gui = new GuiInterface();
            hero = gui.displayHeroSelect();
        }
    }
}
