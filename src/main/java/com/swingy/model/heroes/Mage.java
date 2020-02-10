package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;

public class Mage extends Hero {

    public Mage( String name ) {
        setName( name );
        setHeroClass( "Mage" );
        setLevel( 1 );
        setExperience( 1000 );
        setAttack( 110 );
        setDefense( 115 );
        setHitPoints( 250 );
    }

}
