package com.swingy.model.enemies;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Goblin implements HeroTemplate {

    private Hero enemy;

    public Goblin() {
        this.enemy = new Hero();
        this.enemy.setName( "Goblin" );
    }

    @Override
    public void heroClass() {
        this.enemy.setClass( "Goblin" );
    }

    @Override
    public void heroLevel() {
        this.enemy.setLevel( 1 );
    }

    @Override
    public void heroExperience() {
        this.enemy.setExperience( 650 );
    }

    @Override
    public void heroAttack() {
        this.enemy.setAttack( 135 );
    }

    @Override
    public void heroDefense() {
        this.enemy.setDefense( 50 );
    }

    @Override
    public void heroHitPoints() {
        this.enemy.setHitPoints( 290 );
    }

    @Override
    public Hero getHero() {
        return this.enemy;
    }
}
