package com.swingy.model.enemies;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Goblin implements HeroTemplate {

	private Hero goblin;

	public Goblin(){
		this.goblin = new Hero();
		this.goblin.setName("Gobby");
	}

	public void heroClass() {
	goblin.setClass("Goblin");
	}

	public void heroLevel() {
	goblin.setLevel(1);
	}

	public void heroExperience() {
	goblin.setExperience(650);
	}

	public void heroAttack() {
	goblin.setAttack(125);
	}

	public void heroDefense() {
	goblin.setDefense(60);
	}

	public void heroHitPoints() {
	goblin.setHitPoints(300);
	}

	public Hero getHero() {
		return goblin;
	}
}
