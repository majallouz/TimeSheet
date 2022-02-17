package tn.esprit.spring.services;

import java.text.MessageFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;


@Service
@Slf4j
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	@Override
	public Employe authenticate(String login, String password) {
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	@Override
	public int addOrUpdateEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}


	 public void mettreAjourEmailByEmployeId(String email, int employeId) {
	        Optional<Employe> empl = employeRepository.findById(employeId);
	        Employe employe = null;
	        if (empl.isPresent()) {
	            employe = empl.get();
	            log.info(MessageFormat.format("Employe Id,{0}", employe.getId()));
	            employe.setEmail(email);
	            log.info(MessageFormat.format("Employe Id   {0}", employe));
	            employeRepository.save(employe);
	        } else {
	            log.info(MessageFormat.format("Employe doesn''t exist{0}", employe));
	            }
	        }


	 @Transactional
	    public void affecterEmployeADepartement(int employeId, int depId) {
	        log.info(MessageFormat.format("Department Id {0}", depId));
	        log.info(MessageFormat.format("Employe Id {0}", employeId));
	        Departement depManagedEntity = null;
	        Employe employeManagedEntity = null;
	        Optional<Employe> empl = employeRepository.findById(employeId);
	        Optional<Departement> dep = deptRepoistory.findById(depId);
	        if (dep.isPresent() && empl.isPresent()) {
	            depManagedEntity = dep.get();
	            log.debug(MessageFormat.format("Department Id  {0}", depId));
	            employeManagedEntity = empl.get();
	            log.debug(MessageFormat.format("Employe Id, {0}", employeId));


		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);
		}

	
		deptRepoistory.save(depManagedEntity); 
		}

	}
	 
	 @Transactional
	    public void desaffecterEmployeDuDepartement(int employeId, int depId) {
	        log.info(MessageFormat.format("Department Id{0}", depId));
	        log.info(MessageFormat.format("Employe Id , {0}", employeId));
	        Departement dep = null;
	        Optional<Employe> empl = employeRepository.findById(employeId);
	        Optional<Departement> departement = deptRepoistory.findById(employeId);
	        if (departement.isPresent() && empl.isPresent()) {
	            dep = departement.get();
	            log.debug(MessageFormat.format("Department Id{0}", depId));
		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;
			}
		}}
	} 
	
	// Tablesapce (espace disque) 

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	 public void affecterContratAEmploye(int contratId, int employeId) {
	        log.info(MessageFormat.format("Employe Id {0}", employeId));
	        log.info(MessageFormat.format("Contrat Id {0}", contratId));
	        Contrat contratManagedEntity = null;
	        Employe employeManagedEntity = null;
	        Optional<Employe> empl = employeRepository.findById(employeId);
	        Optional<Contrat> contrat = contratRepoistory.findById(employeId);
	        if (contrat.isPresent() && empl.isPresent()) {
	            contratManagedEntity = contrat.get();
	            log.debug(MessageFormat.format("Contrat Id{0}", contratId));
	            employeManagedEntity = empl.get();
	            log.debug(MessageFormat.format("Employe Id{0}", employeId));

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		}

	}

	 public String getEmployePrenomById(int employeId) {
	        Employe employe = null;
	        Optional<Employe> empl = employeRepository.findById(employeId);
	        if (empl.isPresent()) {
	            employe = empl.get();
	            return employe.getPrenom();
	        } else {
	            log.info("Employe doesn't exist");
	            return null;
	        }
	    }
	 
	 public void deleteEmployeById(int employeId) {
	        Employe employe = null;
	        Optional<Employe> empl = employeRepository.findById(employeId);
	        if (empl.isPresent()) {
	            employe = empl.get();
	            //Desaffecter l'employe de tous les departements
	            //c'est le bout master qui permet de mettre a jour
	            //la table d'association
	            for (Departement dep : employe.getDepartements()) {
	                dep.getEmployes().remove(employe);
	            }
	            employeRepository.delete(employe);
	        } else {
	            log.info("Employe doesn't exist");
	        }
	    }


	 

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}

	@Override
	public Employe getEmployeById(int id) {
		
		return employeRepository.findById(id).orElse(null);
	}
	  public void deleteContratById(int contratId) {
	        log.info(MessageFormat.format("Contrat Id{0}", contratId));
	        Optional<Contrat> contrat = contratRepoistory.findById(contratId);
	        Contrat contratManagedEntity = null;
	        if (contrat.isPresent()) {
	            contratManagedEntity = contrat.get();
	            contratRepoistory.delete(contratManagedEntity);
	            log.info(MessageFormat.format("Contrat Id deleted{0}", contratId));
	        } else {
	            log.info("Contrat doesn't exist");
	        }
	    }

	@Override
	public int getContractById(int reference) {
		// TODO Auto-generated method stub
		return 0;
	}
}
