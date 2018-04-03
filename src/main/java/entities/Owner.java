package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = "Owner.getOwners",
				query = "SELECT o FROM Owner o WHERE id IN :ids"),
})
public class Owner implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String password;
	private String address;
	private String city;
	@OneToMany(mappedBy = "owner")
	private List<Ownership> ownership;

	public Owner(){}

	public long getId() {
		return id;
	}

	public List<Ownership> getOwnership() {
		return ownership;
	}

	public void setOwnership(List<Ownership> ownership) {
		this.ownership = ownership;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
