package tn.esprit.spring;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.IDepartementService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DepartementServiceImplTest {
	@Autowired
	IDepartementService service;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	DepartementRepository deptRepoistory;
		
	
	@Test
	public void testAddDepartement () {
		log.info("**************** Start Method Add Deaprtment Test ****************");
		Departement d = new Departement();
		d.setName("R&D");
		service.ajouterDepartement(d);
		Assert.assertTrue(service.ajouterDepartement(d) > 0);
		log.info("************* End Method Add Department Test *****************");
	}
	
	@Test
	public void affecterDepartementAEntreprise() {
		log.info("****************** Start Method affecterDepartementAEntreprise ******************");
		Departement d = new Departement();
		d.setName("R&D");
		ientrepriseservice.ajouterDepartement(d);
		Entreprise e = new Entreprise();
		e.setName("Vermeg");
		ientrepriseservice.ajouterEntreprise(e);
		Assert.assertNull(d.getEntreprise());
		ientrepriseservice.affecterDepartementAEntreprise(d.getId(), e.getId());
		Assert.assertNotNull(ientrepriseservice.getAllDepartementsNamesByEntreprise(e.getId()));
		log.info("*************** End Method affecterDepartementAEntreprise****************");
	}
	
	@Test
	public void deleteDepartementById() {
		log.info("**************** StartMethodSeleteDepartementById ************");
		Departement d = new Departement();
		d.setName("R&D");
		d.setId(ientrepriseservice.ajouterDepartement(d));
		ientrepriseservice.deleteDepartementById(d.getId());
		Assert.assertFalse(deptRepoistory.findById(d.getId()).isPresent());
		log.info("******************* EndDeleteDepartementById ********************");
		}

}
