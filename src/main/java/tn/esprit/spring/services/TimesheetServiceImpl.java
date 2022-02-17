package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
@Slf4j
public class TimesheetServiceImpl implements ITimesheetService {
	

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		return mission.getId();
	}
    
	  public void affecterMissionADepartement(int missionId, int depId) {
	        Mission mission = null;
	        Departement dep = null;
	        Optional<Mission> missionR = missionRepository.findById(missionId);
	        Optional<Departement> deptR = deptRepoistory.findById(depId);
	        if (missionR.isPresent()) {
	            mission = missionR.get();
	        }
	        if (deptR.isPresent()) {
	            dep = deptR.get();
	        }
	        assert mission != null;
	        mission.setDepartement(dep);
	        missionRepository.save(mission);

			if(mission.getDepartement()==dep) {
				log.trace("Mission affectée au departement avec succés !!!");
	
			}
	    }
	
	

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		
	}

	
	 public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
	        Employe validateur = new Employe();
	        Mission mission = new Mission();
	        Optional<Employe> employeR = employeRepository.findById(validateurId);
	        Optional<Mission> missionR = missionRepository.findById(missionId);
	        if (employeR.isPresent() && missionR.isPresent()) {
	            validateur = employeR.get();
	            mission = missionR.get();
	        }
		//verifier s'il est un chef de departement (interet des enum)
		if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			log.warn("l'employe doit etre chef de departement pour valider une feuille de temps !");
			log.error("Timesheet is Invalid");
			return;
		}
		//verifier s'il est le chef de departement de la mission en question
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			log.warn("l'employe doit etre chef de departement de la mission en question");
			log.error("Timesheet is Invalid");
			return;
		}
//
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		log.info("Timesheet is Valid");
		timesheet.setValide(true);
		
		//Comment Lire une date de la base de données
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		log.info("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
		
	}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		return timesheetRepository.getAllEmployeByMission(missionId);
	}

}
