package tn.esprit.spring.dto;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeDTO {

	private String prenom;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;

	// @Column(unique=true)
	// @Pattern(regex=".+\@.+\..+")
	private String email;

	private String password;

	private boolean actif;

	@Enumerated(EnumType.STRING)
	// @NotNull
	private Role role;

	// @JsonBackReference
	@JsonIgnore
	@ManyToMany(mappedBy = "employes", fetch = FetchType.EAGER)
	// @NotNull
	private List<Departement> departements;

	@JsonIgnore
	// @JsonBackReference
	@OneToOne(mappedBy = "employe")
	private Contrat contrat;

	@JsonIgnore
	// @JsonBackReference
	@OneToMany(mappedBy = "employe")
	private List<Timesheet> timesheets;

}
