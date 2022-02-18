package tn.esprit.spring;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IDepartementService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EntrepriseServiceImplTest {
	@Autowired
	IEntrepriseService entrepriseService;
	@Autowired
	IDepartementService depService;
	
	

	@Test
	public void testAddDepartement() {
		log.info("********************************Start Method Test Add Departement ******************************************************");
	
		log.debug("add new departement");
	Departement d = new Departement();
	int expected=depService.getAllDepartements().size();
	d.setName("Recherche et developement");
	Entreprise e = new Entreprise();
	e.setName("Vermeg");
	e.setRaisonSocial("123");
	int entrId = entrepriseService.ajouterEntreprise(e);
	Entreprise entreprise = entrepriseService.getEntrepriseById(entrId);
	if(entreprise == null ) {
		log.error("we don't have an entreprise with this " + entrId);
	}
	d.setEntreprise(entreprise);
	int departementId = entrepriseService.ajouterDepartement(d);
	assertNotNull(entrepriseService.getEntrepriseById(departementId));
	//assertEquals(expected+1, depService.getAllDepartements().size());
	log.trace("departement added sucessfuly !");
	
	
	
	log.info("we have " + depService.getAllDepartements().size() +" departement in the database");	
	
	}
	@Test
	public void testAddEntreprise() {

		log.info("********************************Start Method Test Add Entreprise ******************************************************");
		//logging debug
		log.debug("add new entreprise");
	Entreprise e = new Entreprise();
	e.setName("Vermeg");
	e.setRaisonSocial("123");
	int entrepriseId = entrepriseService.ajouterEntreprise(e);
	log.warn("entreprise created without departement");
	//assertion 
	assertNotNull(entrepriseService.getEntrepriseById(entrepriseId));
	//logging trace
	log.trace("entreprise added sucessfuly !");
	//logging trace
	log.debug("affect departement to entreprise ");
	int depId = 1;
	if(!depService.getAllDepartements().stream().filter(d -> d.getId()==depId).findAny().isPresent()) {
		log.error("we don't have departement with this " + depId);
	}
	entrepriseService.affecterDepartementAEntreprise(depId,1);
	log.trace("departement affected sucessfuly !");
	log.info("this entreprise have " + entrepriseService.getAllDepartementsNamesByEntreprise(entrepriseId) +" departement in the database");	
	
	}
	
	
	@Test
	public void testAffectDepartementToEntreprise() {
		log.info("********************************Start Method Test Affecting Departement to Entreprise ********************************************");
	int depId =1;
	if(!depService.getAllDepartements().stream().filter(d -> d.getId()==depId).findAny().isPresent()) {
		log.error("we don't have departement with this " + depId);
	}
	entrepriseService.affecterDepartementAEntreprise(depId,1);
	log.trace("departement affected sucessfuly !");
	
	}
	

}
