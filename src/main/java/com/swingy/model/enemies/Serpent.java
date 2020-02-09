package com.swingy.model.enemies;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroTemplate;

public class Serpent implements HeroTemplate {
	private Hero serpent;
	public Serpent(){
		this.serpent = new Hero();
		this.serpent.setName("Slither");
	}
	@Override
	public void heroClass() {
	serpent.setClass("Serpent");
	}

	@Override
	public void heroLevel() {
	serpent.setLevel(1);
	}

	@Override
	public void heroExperience() {
	serpent.setExperience(900);
	}

	@Override
	public void heroAttack() {
	serpent.setAttack(130);
	}

	@Override
	public void heroDefense() {
	serpent.setDefense(135);
	}

	@Override
	public void heroHitPoints() {
	serpent.setHitPoints(250);
	}

	@Override
	public Hero getHero() {
		return serpent;
	}
}
