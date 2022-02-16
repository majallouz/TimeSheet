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
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EntrepriseServiceImplTest {
	@Autowired
	IEntrepriseService entrepriseService;
	
	@Test
	public void testAddEntreprise() {
		log.info("********************************Start Method Test Add Entreprise **************************************************");
	Entreprise e = new Entreprise();
	e.setName("Vermeg");
	e.setRaisonSocial("123");
	int entrepriseId = entrepriseService.ajouterEntreprise(e);
	assertNotNull(entrepriseService.getEntrepriseById(entrepriseId));
	log.info("this entreprise have " + entrepriseService.getAllDepartementsNamesByEntreprise(entrepriseId) +" departement in the database");	
	}
	

}
