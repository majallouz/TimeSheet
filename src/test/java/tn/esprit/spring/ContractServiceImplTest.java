package tn.esprit.spring;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.services.IContratService;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ContractServiceImplTest {
	@Autowired
	IEmployeService employeService;
	IContratService contratService;
	@Test
	public void testAddContract() {
		log.info("********************************Start Method Test Add Contract ******************************************************");
	Contrat contrat = new Contrat();
	contrat.setSalaire(15000);
	contrat.setDateDebut(new Date(Calendar.DAY_OF_MONTH));
	contrat.setTypeContrat("CDI");
	int entrepriseId = employeService.ajouterContrat(contrat);
	//employeService.deleteContratById(contrat.getReference());
	//assertNotNull(employeService.getContractById(contrat.getReference()));
	log.warn("********************Contract is added********************");
	log.info(" ********************Contract type " +contrat.getTypeContrat() +" added ********************");

	
	}
	@Test
	public void testDeleteContract() {
		log.info("********************************Start Method Test Delete Contract ******************************************************");
	Contrat contrat = new Contrat();
	contrat.setSalaire(25000);
	contrat.setDateDebut(new Date(Calendar.DAY_OF_MONTH));
	contrat.setTypeContrat("CDD");
	assertNotNull(contrat);
	int entrepriseId = employeService.ajouterContrat(contrat);
	employeService.deleteContratById(contrat.getReference());
	//assertNotNull(employeService.getContractById(contrat.getReference()));
	log.info(" ********************Contract type " +contrat.getTypeContrat() +" deleted ********************");
    log.info("********************************Start Method Test Delete non-existent Contract ******************************************************");
	employeService.deleteContratById(999);
	log.info("********************************End Method ******************************************************");

	}
	
}
