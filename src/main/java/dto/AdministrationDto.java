package dto;

import model.Journey;

import java.util.List;

public class AdministrationDto {
	List<Journey> journeys;

	public AdministrationDto(List<Journey> journeys){
		this.journeys = journeys;
	}

	public List<Journey> getJourneys() {
		return journeys;
	}

	public void setJourneys(List<Journey> journeys) {
		this.journeys = journeys;
	}
}
