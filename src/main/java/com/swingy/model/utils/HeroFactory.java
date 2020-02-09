package com.swingy.model.utils;

import com.swingy.model.enemies.Goblin;
import com.swingy.model.enemies.Serpent;
import com.swingy.model.heroes.Fighter;
import com.swingy.model.heroes.Mage;
import com.swingy.model.heroes.Witcher;
import org.jetbrains.annotations.Nullable;

public class HeroFactory {

    public static HeroCreator newHero( int heroClass, @Nullable String heroName ) {
        HeroCreator toReturn;
        switch ( heroClass ) {
            case 1: {
                toReturn = new HeroCreator( new Witcher( heroName ) );
                break;
            }
            case 2: {
                toReturn = new HeroCreator( new Fighter( heroName ) );
                break;
            }
            case 3: {
                toReturn = new HeroCreator( new Mage( heroName ) );
                break;
            }
            case 4: {
                toReturn = new HeroCreator( new Serpent() );
                break;
            }
            case 5: {
                toReturn = new HeroCreator( new Goblin() );
                break;
            }
            default: {
                throw new IllegalStateException( "Unexpected value: " + heroClass );
            }
        }
        toReturn.createHero();
        return toReturn;
    }
}
