package com.swingy.model.utils;

public class HeroCreator {

	private HeroTemplate heroTemplate;

	public HeroCreator(HeroTemplate heroBuild) {
		this.heroTemplate = heroBuild;
	}

	public Hero getHero() {
		return this.heroTemplate.getHero();
	}

	public void createHero() {

		this.heroTemplate.heroClass();

		this.heroTemplate.heroLevel();

		this.heroTemplate.heroExperience();

		this.heroTemplate.heroAttack();

		this.heroTemplate.heroDefense();

		this.heroTemplate.heroHitPoints();

	}

}
