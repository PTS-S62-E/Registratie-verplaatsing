package model;

import entities.Translocation;

import java.util.ArrayList;
import java.util.List;

public class Journey {

	List<Translocation> translocations;

	public Journey(){
		this.translocations = new ArrayList<>();
	}

	public List<Translocation> getTranslocations() {
		return translocations;
	}

	public void setTranslocations(List<Translocation> translocations) {
		this.translocations = translocations;
	}

	public void addTranslocation(Translocation translocation){
		this.translocations.add(translocation);
	}
}
