package com.freebz.sevenknight.model;

public class Hero {
	
	private int id;
	private String name;
	private int group;
	private int star;
	private int ratio;
	private int nextId;
	private int seq;
	
	private int[] normalRatio = {25000, 10000, 2500, 1000, 500, 250};
	private int[] advancedRatio = {5000, 2500, 1000, 500, 250, 100};
	private int[] sevenKnightRatio = {2500, 1000, 500, 250, 100, 10};
	
	public Hero(int id, String name, int group, int star, int ratio, int nextId, int seq) {
		this.id= id;
		this.name = name;
		this.group = group;
		this.star = star;
//		this.ratio = ratio;
		this.nextId = nextId;
		this.seq = seq;
		
		switch (group) {
		case 1:		// 세븐나이츠
		case 11:	// 특수영웅
			this.ratio = sevenKnightRatio[star - 1];
			break;
		case 2:		// 모험가들
			this.ratio = advancedRatio[star - 1];
			break;
		default:
			this.ratio = normalRatio[star - 1];
			break;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	
}
