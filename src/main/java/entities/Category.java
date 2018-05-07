package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name="Category.getCategories",
				query="SELECT c FROM Category c"),
})
public class Category {

	@Id
	String name;
	String description;

	public Category(){}

	public Category(String name){
		this.name = name;
	}

	public Category(String name, String description){
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
