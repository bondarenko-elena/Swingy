package com.swingy.model.heroes;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Fighter implements HeroTemplate {

	private Hero hero;

	public Fighter(String heroNNme){
		this.hero = new Hero();
		hero.setName(heroNNme);
	}

	public Hero getHero() {
		return hero;
	}

	public void heroClass() {
		hero.setClass("Fighter");
	}

	public void heroLevel() {
		hero.setLevel(1);
	}

	public void heroExperience() {
		hero.setExperience(1000);
	}

	public void heroAttack() {
		hero.setAttack(120);
	}

	public void heroDefense() {
		hero.setDefense(95);
	}

	public void heroHitPoints() {
		hero.setHitPoints(220);
	}
}
