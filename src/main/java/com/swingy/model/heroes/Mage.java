package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Mage implements HeroTemplate {

	private Hero hero;

	public Mage(String name){
		this.hero = new Hero();
		hero.setName(name);
	}

	public void heroClass() {
		hero.setClass("Mage");
	}

	public void heroLevel() { hero.setLevel(1); }

	public void heroExperience() {
		hero.setExperience(1000);
	}

	public void heroAttack() {
		hero.setAttack(110);
	}

	public void heroDefense() {
		hero.setDefense(115);
	}

	public void heroHitPoints() {
		hero.setHitPoints(280);
	}

	public Hero getHero() {
		return this.hero;
	}
}
