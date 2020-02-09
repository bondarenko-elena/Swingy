package com.swingy.model.enemies;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Serpent implements HeroTemplate {

    private Hero enemy;

    public Serpent() {
        this.enemy = new Hero();
        this.enemy.setName( "Serpent" );
    }

    @Override
    public void heroClass() {
        this.enemy.setClass( "Serpent" );
    }

    @Override
    public void heroLevel() {
        this.enemy.setLevel( 1 );
    }

    @Override
    public void heroExperience() {
        this.enemy.setExperience( 800 );
    }

    @Override
    public void heroAttack() {
        this.enemy.setAttack( 120 );
    }

    @Override
    public void heroDefense() {
        this.enemy.setDefense( 140 );
    }

    @Override
    public void heroHitPoints() {
        this.enemy.setHitPoints( 250 );
    }

    @Override
    public Hero getHero() {
        return this.enemy;
    }
}
