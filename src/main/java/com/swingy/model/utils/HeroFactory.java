package com.swingy.model.utils;

import com.swingy.model.heroes.Fighter;
import com.swingy.model.heroes.Mage;
import com.swingy.model.heroes.Witcher;
import com.swingy.model.enemies.Goblin;
import com.swingy.model.enemies.Serpent;

public class HeroFactory {
    public static HeroCreator newHero( int heroClass, String heroName ) {
        HeroTemplate heroTemplate;
        HeroCreator toReturn;
        if ( heroClass == 1 ) {
            heroTemplate = new Witcher( heroName );
            toReturn = new HeroCreator( heroTemplate );
        } else if ( heroClass == 2 ) {
            heroTemplate = new Fighter( heroName );
            toReturn = new HeroCreator( heroTemplate );
        } else if ( heroClass == 3 ) {
            heroTemplate = new Mage( heroName );
            toReturn = new HeroCreator( heroTemplate );
        } else if (heroClass == 4 ) {
            heroTemplate = new Serpent();
            toReturn = new HeroCreator( heroTemplate );
        } else if (heroClass == 5 ) {
            heroTemplate = new Goblin();
            toReturn = new HeroCreator( heroTemplate );
        } else {
            throw new IllegalStateException( "Unexpected value: " + heroClass );
        }
        assert toReturn != null;
        toReturn.createHero();
        return toReturn;
    }
}
