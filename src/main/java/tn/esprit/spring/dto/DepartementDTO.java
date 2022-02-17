package tn.esprit.spring.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;

@Getter
@Setter
@AllArgsConstructor
public class DepartementDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	//@JsonManagedReference 
	@JsonIgnore
	@ManyToMany
	private List<Employe> employes;
	
	@OneToMany(mappedBy="departement")
	private List<Mission> missions;
	
	@ManyToOne
	private Entreprise entreprise;

}
