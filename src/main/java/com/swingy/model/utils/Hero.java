package com.swingy.model.utils;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Hero implements HeroStatistics {

	@NotBlank(message = "hero name cannot be blank")
	@NotNull(message = "Hero name cannot be null")
	@Length(min = 3, max = 15, message = "hero name has to be between 3 and 15 characters long")
	private String heroName;

	@NotBlank(message = "Class cannot be blank")
	@NotNull(message = "Can't have a blank class")
	private String heroClass;
	@Size(min = 1, max = 7, message =  "hero cap is level 7")
	private int level;
	@Size(min = 1000, max = 200000, message = "Xp cap")
	private int experience;
	@Size(min = 50, max = 1000, message = "max attack points")
	private int attack;
	@Size(min = 25, max = 900, message = "Max defense")
	private int defense;
	@Size(min = 50, max = 2000, message = "Max HP")
	private int hp;


	public void setName(String heroName) {
		this.heroName = heroName;
	}
	
	public String getHeroName() {
		return heroName;
	}

	public void setClass(String heroClass) {
		this.heroClass = heroClass;
	}
	
	public String getHeroClass() {
		return heroClass;
	}


	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getHeroLevel() { return level; }

	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public int getExperience() {
		return experience;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getAttack() {
		return attack;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getDefense() {
		return defense;
	}

	public void setHitPoints(int hp) {
		this.hp = hp;
	}

	public int getHitPoints() {
		return hp;
	}

}
