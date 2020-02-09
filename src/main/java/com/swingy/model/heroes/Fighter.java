package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Fighter implements HeroTemplate {

    private Hero hero;

    public Fighter( String heroName ) {
        this.hero = new Hero();
        this.hero.setName( heroName );
    }

    @Override
    public Hero getHero() {
        return this.hero;
    }

    @Override
    public void heroClass() {
        this.hero.setClass( "Fighter" );
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
        this.hero.setAttack( 120 );
    }

    @Override
    public void heroDefense() {
        this.hero.setDefense( 95 );
    }

    @Override
    public void heroHitPoints() {
        this.hero.setHitPoints( 220 );
    }
}
