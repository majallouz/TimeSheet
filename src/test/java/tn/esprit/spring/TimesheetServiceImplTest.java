package tn.esprit.spring;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.IDepartementService;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.ITimesheetService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class TimesheetServiceImplTest {

	@Autowired
	ITimesheetService timesheetService;
	@Autowired
	IEmployeService employeeService;
	@Autowired
	EmployeRepository employerep;
	@Autowired
	MissionRepository missionrep;
	@Autowired
	TimesheetRepository timesheetrep;
	@Autowired
	IDepartementService departementService;
	
	
	
	@Test
	public void testajouterMission() {
		log.info("********************************Start Method Test Add Mission ******************************************************");
	
		//création d'une mission
		Mission m = new Mission();
		m.setName("Mission Mise en Prod");
		m.setDescription("Description de la mission de mise en Prod");
		int missionID = timesheetService.ajouterMission(m);
		assertNotNull(timesheetService.getAllEmployeByMission(missionID));
	
		//logging
		log.trace("The number of Employees affected to the mission " + m.getName() + "is " + timesheetService.getAllEmployeByMission(missionID).size());	
		log.info("********************************End Method Test Add Mission ******************************************************");

		}
	
	
	
	@Test
	public void testajouterTimesheet() {
		log.info("********************************Start Method Test Add Timesheet******************************************************");

		
		//création d'une mission
				Mission m = new Mission();
				m.setName("Mission Mise en Prod");
				m.setDescription("Description de la mission de mise en Prod");
				int missionID = timesheetService.ajouterMission(m);
				assertNotNull(timesheetService.getAllEmployeByMission(missionID));
				
		//création d'un employee
				Employe e = new Employe();
				e.setEmail("mgara@vermeg.com");
				e.setNom("Gara");
				e.setPrenom("Malek");
				e.setRole(Role.TECHNICIEN);
				int employeeID = employeeService.addOrUpdateEmploye(e);
		
		Date startDate = new Date(1997,06,12);
		Date endDate = new Date(2021,06,12);
		timesheetService.ajouterTimesheet(missionID, employeeID, startDate, endDate);
		
		log.trace("This is One TimeSheet of " + employeeService.getTimesheetsByMissionAndDate(e, m, startDate, endDate).size() + "Timesheets" );
		log.info("********************************End Method Test Add Timesheet ******************************************************");

	}
	
	
	
	 @Test
	public void testvaliderTimesheet() {
		
		log.info("********************************Start Method Test Validate Timesheet******************************************************");

		Date startDate = new Date(1997,06,12);
		Date endDate = new Date(2021,06,12);
		
		//création d'une mission
		Mission m = new Mission();
		m.setName("Mission Mise en Prod");
		m.setDescription("Description de la mission de mise en Prod");
		int missionID = timesheetService.ajouterMission(m);
		assertNotNull(timesheetService.getAllEmployeByMission(missionID));
		
		//création d'un employee
		Employe e = new Employe();
		e.setEmail("mgara@vermeg.com");
		e.setNom("Gara");
		e.setPrenom("Malek");
		e.setRole(Role.CHEF_DEPARTEMENT);
		int employeeID = employeeService.addOrUpdateEmploye(e);
		
		//création d'un departement
		Departement d = new Departement();
		d.setName("Operations");
		int DepartementID = departementService.ajouterDepartement(d);
		
		employeeService.affecterEmployeADepartement(employeeID, DepartementID);
		//employeeService.affecterEmployeADepartement(employeeID, 2);
		//employeeService.affecterEmployeADepartement(employeeID, 3);
		
		if (e.getRole() != Role.CHEF_DEPARTEMENT)
		{
			log.warn("Attention l'employee n'est pas chef de departement, Veuillez vérifier le rôle !!!");
		}
		
		
		//création d'un département
		timesheetService.affecterMissionADepartement(missionID,DepartementID);
		
		//création d'un timesheet
		timesheetService.ajouterTimesheet(missionID, employeeID, startDate, endDate);

		//validation du timesheet
		timesheetService.validerTimesheet(missionID, employeeID, startDate, endDate, employeeID);
		log.info("********************************End Method Test Validate TimeSheet ******************************************************");

				
	}
	
	
	

}
