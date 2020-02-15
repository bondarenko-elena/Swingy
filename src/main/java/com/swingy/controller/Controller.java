package com.swingy.controller;

import com.swingy.database.DBMethods;
import com.swingy.model.map.Maps;
import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroFactory;
import com.swingy.view.GuiInterface;

import javax.swing.*;
import java.util.ArrayList;
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
        int heroClass = (int) ( Math.random() * 2 + 4 );
        return HeroFactory.newHero( heroClass, null );
    }

    private void attacks( Hero enemy ) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if ( random.nextInt( 0, 10 ) >= 4 ) {
            if ( this.hero.getAttack() > enemy.getDefense() ) {
                enemy.setHitPoints( enemy.getHitPoints() - ( this.hero.getAttack() - enemy.getDefense() ) );
            }
        } else {
            if ( enemy.getAttack() > this.hero.getDefense() ) {
                this.hero.setHitPoints( this.hero.getHitPoints() - ( enemy.getAttack() - this.hero.getDefense() ) );
            }
        }
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

    public void onFightNoButtonButtonPressed( Maps map, JFrame frame ) {
        Random random = new Random();
        GuiInterface gui = new GuiInterface();
        if ( random.nextInt( 2 ) == 1 ) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Sorry, the odds are not on your side, you must fight the enemy."
            );
            if ( clashOfHeroes() == 1 ) {
                map.levelUP( Maps.View.GUI );
                gui.continueGameLevelUp( map );
            } else {
                gui.endGame( "Fight is over. You lost. GG WP" );
            }
        } else {
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
            gui.endGame(
                    "You reached on of the borders of the map and win. Game completed. GG WP" );
        }
        if ( move.equalsIgnoreCase( "fight" ) ) {
            gui.fight( map );
        }
        if ( move.equalsIgnoreCase( "continue" ) ) {
            gui.continueGame( map );
        }
    }

    public void onHeroCreateButtonPressed() {
        GuiInterface gui = new GuiInterface();
        gui.displayHeroCreate( 0 );
    }

    public void onSwitchViewButtonPressed() {
        GuiInterface.switchView();
    }

    private boolean validateHeroSelect( Hero hero ) {
        return hero != null;
    }

    public void onHeroSelectSaveButtonPressed( DBMethods dbData, JTextField heroSelectId ) {
        int heroId = 0;
        try {
            heroId = Integer.parseInt( heroSelectId.getText() );
            if ( heroId < 1 || heroId > dbData.selectCountSavedHeroes() ) {
                GuiInterface gui = new GuiInterface();
                gui.displayHeroSelect();
            }
        } catch ( NumberFormatException e ) {
            GuiInterface gui = new GuiInterface();
            gui.displayHeroSelect();
        }

        this.hero = dbData.getHerodb( heroId );
        GuiInterface gui = new GuiInterface();
        if ( !validateHeroSelect( hero ) || ( heroId < 1 || heroId > 100 ) ) {
            gui.displayHeroSelect();
        } else {
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
        return ( heroClass.equalsIgnoreCase( "1" ) || heroClass.equalsIgnoreCase( "2" ) || heroClass
                .equalsIgnoreCase( "3" ) ) && ( heroName.length() >= 3 && heroName.length() <= 10 );
    }

    private boolean checkUniqueHeroName( String heroName, DBMethods dbData ) {
        ArrayList<String> listNames = dbData.selectAllHeroNames();
        for ( String name : listNames ) {
            if ( name.equalsIgnoreCase( heroName ) ) {
                return true;
            }
        }
        return false;
    }

    public void onHeroSaveButtonPressed(
            JTextField heroClass,
            JTextField heroName,
            DBMethods dbData
    ) {
        if ( !validateFields( heroClass.getText(), heroName.getText() ) ) {
            GuiInterface gui = new GuiInterface();
            gui.displayHeroCreate( 1 );
        }
        if ( checkUniqueHeroName( heroName.getText(), dbData ) ) {
            GuiInterface gui = new GuiInterface();
            gui.displayHeroCreate( 2 );
        }
        if ( ( validateFields(
                heroClass.getText(),
                heroName.getText()
        ) ) && ( !checkUniqueHeroName( heroName.getText(), dbData ) ) ) {
            hero = HeroFactory.newHero(
                    Integer.parseInt( heroClass.getText() ),
                    heroName.getText()
            );
            dbData.addHero( hero );
            GuiInterface gui = new GuiInterface();
            hero = gui.displayHeroSelect();
        }
    }
}
