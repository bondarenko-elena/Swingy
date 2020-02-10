package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;

public class Fighter extends Hero {

    public Fighter( String heroName ) {
        setName( heroName );
        setHeroClass( "Fighter" );
        setLevel( 1 );
        setExperience( 1000 );
        setAttack( 120 );
        setDefense( 95 );
        setHitPoints( 220 );
    }

}
