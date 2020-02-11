package com.swingy.model.utils;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Hero {

    @NotNull( message = "Hero name can not be null" )
    @NotBlank( message = "Hero name can not be blank" )
    @Length( min = 3, max = 10, message = "Hero name has to be between 3 and 10 characters long" )
    private String name;

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
    private int hitPoints;

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", heroClass='" + heroClass + '\'' +
                ", level=" + level +
                ", experience=" + experience +
                ", attack=" + attack +
                ", defense=" + defense +
                ", hitPoints=" + hitPoints +
                '}';
    }
}
