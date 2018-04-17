package model;

import entities.Translocation;

import java.util.List;

public class Journey {

	List<Translocation> translocations;

	public List<Translocation> getTranslocations() {
		return translocations;
	}

	public void setTranslocations(List<Translocation> translocations) {
		this.translocations = translocations;
	}
}
