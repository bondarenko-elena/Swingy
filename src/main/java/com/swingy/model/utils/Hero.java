package com.swingy.model.utils;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Hero implements HeroStatistics {

    @NotNull( message = "Hero name can not be null" )
    @NotBlank( message = "Hero name can not be blank" )
    @Length( min = 3, max = 10, message = "Hero name has to be between 3 and 10 characters long" )
    private String heroName;

    @NotNull( message = "Can not have a blank class" )
    @NotBlank( message = "Class can not be blank" )
    private String heroClass;

    @Size( min = 1, max = 7, message = "Level capacity 7" )
    private int level;

    @Size( min = 1000, max = 200000, message = "Xp capacity" )
    private int experience;

    @Size( min = 50, max = 1000, message = "Max attack points" )
    private int attack;

    @Size( min = 25, max = 900, message = "Max defense" )
    private int defense;

    @Size( min = 50, max = 2000, message = "Max HP" )
    private int hp;

    @Override
    public void setName( String heroName ) {
        this.heroName = heroName;
    }

    public String getHeroName() {
        return this.heroName;
    }

    @Override
    public void setClass( String heroClass ) {
        this.heroClass = heroClass;
    }

    public String getHeroClass() {
        return this.heroClass;
    }

    @Override
    public void setLevel( int level ) {
        this.level = level;
    }

    public int getHeroLevel() {
        return this.level;
    }

    @Override
    public void setExperience( int experience ) {
        this.experience = experience;
    }

    public int getExperience() {
        return this.experience;
    }

    @Override
    public void setAttack( int attack ) {
        this.attack = attack;
    }

    public int getAttack() {
        return this.attack;
    }

    @Override
    public void setDefense( int defense ) {
        this.defense = defense;
    }

    public int getDefense() {
        return this.defense;
    }

    @Override
    public void setHitPoints( int hp ) {
        this.hp = hp;
    }

    public int getHitPoints() {
        return this.hp;
    }


}
