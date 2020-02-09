package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Witcher implements HeroTemplate {

	private Hero hero;

	public Witcher(String name) {
		this.hero = new Hero();
		hero.setName(name);
	}

	public void heroClass() {
		hero.setClass("Witcher");
	}

	public void heroLevel() { hero.setLevel(1); }

	public void heroExperience() {
		hero.setExperience(1000);
	}

	public void heroAttack() {
		hero.setAttack(150);
	}

	public void heroDefense() {
		hero.setDefense(120);
	}

	public void heroHitPoints() {
		hero.setHitPoints(270);
	}

	public Hero getHero() {
		return hero;
	}
}
