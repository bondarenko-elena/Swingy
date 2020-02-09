package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Mage implements HeroTemplate {

    private Hero hero;

    public Mage( String name ) {
        this.hero = new Hero();
        this.hero.setName( name );
    }

    @Override
    public void heroClass() {
        this.hero.setClass( "Mage" );
    }

    @Override
    public void heroLevel() {
        this.hero.setLevel( 1 );
    }

    @Override
    public void heroExperience() {
        this.hero.setExperience( 1000 );
    }

    @Override
    public void heroAttack() {
        this.hero.setAttack( 110 );
    }

    @Override
    public void heroDefense() {
        this.hero.setDefense( 115 );
    }

    @Override
    public void heroHitPoints() {
        this.hero.setHitPoints( 250 );
    }

    @Override
    public Hero getHero() {
        return this.hero;
    }
}
