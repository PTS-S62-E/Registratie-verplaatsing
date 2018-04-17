package dto;

import java.util.ArrayList;
import java.util.List;

public class JourneyDto {

	List<TranslocationDto> translocationDtos;

	public JourneyDto(){
		this.translocationDtos = new ArrayList<>();
	}

	public List<TranslocationDto> getTranslocations() {
		return translocationDtos;
	}

	public void setTranslocations(List<TranslocationDto> translocationDtos) {
		this.translocationDtos = translocationDtos;
	}

	public void addTranslocation(TranslocationDto translocationDto){
		this.translocationDtos.add(translocationDto);
	}
}
