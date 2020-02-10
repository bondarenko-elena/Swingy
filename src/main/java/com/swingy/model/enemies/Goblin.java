package com.swingy.model.enemies;

import com.swingy.model.utils.Hero;

public class Goblin extends Hero {

    public Goblin() {
        setName( "Goblin" );
        setHeroClass( "Goblin" );
        setLevel( 1 );
        setExperience( 650 );
        setAttack( 135 );
        setDefense( 50 );
        setHitPoints( 290 );
    }

}
