package tn.esprit.spring.dto;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Employe;


@Getter
@Setter
@AllArgsConstructor
public class TimesheetDTO {

    @EmbeddedId
	private TimesheetPK timesheetPK;
	
	//idMission est a la fois primary key et foreign key
	@ManyToOne
    @JoinColumn(name = "idMission", referencedColumnName = "id", insertable=false, updatable=false)
	private Mission mission;
	
	//idEmploye est a la fois primary key et foreign key
	
	@ManyToOne
    @JoinColumn(name = "idEmploye", referencedColumnName = "id", insertable=false, updatable=false)
	private Employe employe;

    
}
