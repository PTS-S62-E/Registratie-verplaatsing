package dto;

import entities.Translocation;

public class InternalTranslocationDto extends TranslocationDto {

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public InternalTranslocationDto(){
		super();
	}

	public InternalTranslocationDto(Translocation translocation){
		super(translocation);
		this.id = translocation.getId();
	}
}
