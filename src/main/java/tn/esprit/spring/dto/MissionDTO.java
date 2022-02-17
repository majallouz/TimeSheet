package tn.esprit.spring.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Timesheet;


@Getter
@Setter
@AllArgsConstructor
public class MissionDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String description;
    
	@JsonIgnore
	@ManyToOne
	private Departement departement;
	
    @JsonIgnore
	@OneToMany(mappedBy="mission")
	private  List<Timesheet> timesheets;
}