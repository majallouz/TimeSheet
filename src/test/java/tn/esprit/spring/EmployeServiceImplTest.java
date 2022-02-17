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
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IDepartementService;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmployeServiceImplTest {
	@Autowired
	IEmployeService service;
	
	@Test
	public void testAddEmployee() {
		log.warn("********************************Start Method Test Add Employee ******************************************************");
	
	Employe e = new Employe();
	e.setEmail("majallouz@vermeg.com");
	e.setNom("JALLOUZ");
	e.setPrenom("Mohamed Ali");
	int employeeId = service.addOrUpdateEmploye(e);
	log.trace("trace : emplye added sucessfuly !");
	log.debug("debug : emplye added sucessfuly  ");
	int id = 1;
	if( "".equals(service.getEmployePrenomById(employeeId).trim())) 	
	log.error("we don't have departement with this " + id);
	
	assertNotNull(service.getEmployePrenomById(employeeId));
	log.info("this is one employee of  " + service.getAllEmployes().size() +" employees");	
	log.warn("********************************End Method Test Add Employee ******************************************************");

	}
	
	@Test
	public void testAffecterEmployeADepartement() {
		log.warn("********************************Start Method Affecter Employe A Departement ******************************************************");
		log.info("id employee = 1 id Departement= 1");
		log.trace("invoking service !");
		service.affecterEmployeADepartement(1, 1);
		log.debug("debug : affect employe to dept ");
		Employe e = service.getEmployeById(1);
		if(e.getDepartements().isEmpty()) {
			log.error("no depts for emp " + e.getId());
		}
		log.warn("********************************End Method Test Affecter Employe A Departement ******************************************************");


		
	}
	

}
