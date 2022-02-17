package tn.esprit.spring.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.Employe;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reference;
	private Date dateDebut;

	private String typeContrat;
	@OneToOne
	private Employe employe;

	private float salaire;
}
