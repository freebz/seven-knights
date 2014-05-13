package com.freebz.sevenknight.model;

public class Card {

	private int _id;
	private int heroId;
	private int level;
	private int bonus;
	
	public void levelUp() {
		if (level < 5) {
			level++;
		}
		bonus = 0;
	}
	
	public void addBonus(int bonus) {
		this.bonus += bonus;
	}
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
}
