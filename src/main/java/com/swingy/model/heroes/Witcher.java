package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;

public class Witcher extends Hero {

    public Witcher( String name ) {
        setName( name );
        setHeroClass( "Witcher" );
        setLevel( 1 );
        setExperience( 1000 );
        setAttack( 150 );
        setDefense( 120 );
        setHitPoints( 270 );
    }

}
