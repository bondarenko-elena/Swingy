package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Witcher implements HeroTemplate {

    private Hero hero;

    public Witcher( String name ) {
        this.hero = new Hero();
        this.hero.setName( name );
    }

    @Override
    public void heroClass() {
        this.hero.setClass( "Witcher" );
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
        this.hero.setAttack( 150 );
    }

    @Override
    public void heroDefense() {
        this.hero.setDefense( 120 );
    }

    @Override
    public void heroHitPoints() {
        this.hero.setHitPoints( 270 );
    }

    @Override
    public Hero getHero() {
        return hero;
    }
}
