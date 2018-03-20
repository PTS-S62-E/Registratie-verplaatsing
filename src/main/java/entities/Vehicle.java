package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Vehicle implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true,
			nullable = false)
	private String licensePlate;
	private String merk;
	private String type;
	private String catergorie;
	@OneToMany
	private List<Ownership> owners;
	@OneToMany(mappedBy = "vehicle")
	private List<Translocation> translocations;
	private String hardwareSN;
}
