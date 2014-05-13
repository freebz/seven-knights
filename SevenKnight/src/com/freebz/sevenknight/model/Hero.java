package com.freebz.sevenknight.model;

public class Hero {
	
	private int id;
	private String name;
	private int group;
	private int star;
	private int ratio;
	private int nextId;
	private int seq;
	
	public Hero(int id, String name, int group, int star, int ratio, int nextId, int seq) {
		this.id= id;
		this.name = name;
		this.group = group;
		this.star = star;
		this.ratio = ratio;
		this.nextId = nextId;
		this.seq = seq;
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
