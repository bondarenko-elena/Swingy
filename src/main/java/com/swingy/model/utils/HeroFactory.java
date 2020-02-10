package com.swingy.model.utils;

import com.swingy.model.enemies.Goblin;
import com.swingy.model.enemies.Serpent;
import com.swingy.model.heroes.Fighter;
import com.swingy.model.heroes.Mage;
import com.swingy.model.heroes.Witcher;
import org.jetbrains.annotations.Nullable;

public class HeroFactory {

    public static Hero newHero( int heroClass, @Nullable String heroName ) {
        switch ( heroClass ) {
            case 1:
                return new Witcher( heroName );
            case 2:
                return new Fighter( heroName );
            case 3:
                return new Mage( heroName );
            case 4:
                return new Serpent();
            case 5:
                return new Goblin();
            default:
                throw new IllegalStateException( "Unexpected value: " + heroClass );
        }
    }
}
