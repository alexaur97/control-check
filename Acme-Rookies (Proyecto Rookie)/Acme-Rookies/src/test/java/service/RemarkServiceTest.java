package service;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Application;
import domain.Company;
import domain.Remark;
import services.ApplicationService;
import services.CompanyService;
import services.RemarkService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RemarkServiceTest extends AbstractTest {

	@Autowired
	private RemarkService	remarkService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CompanyService companyService;
	
	// Test positivo: Una Company puede crear un Remark.

	@Test
	public void testCreateRemark() {
		super.authenticate("company1");
		Company principal = this.companyService.findByPrincipal();
		
		Integer applicationId = super.getEntityId("application1");
		final Remark remark = this.remarkService.create(applicationId);
		Application application = this.applicationService.findOne(applicationId);
		remark.setApplication(application);
		remark.setBody("TEST");
		remark.setCompany(principal);
		remark.setMode("FINAL");
		remark.setPublicationMoment(new Date());
		remark.setTicker("tickerTEST");
		remark.setPicture("https://www.TEST.com");
		this.remarkService.save(remark);
		super.unauthenticate();
	}

	// Test negativo:
	// Para el caso negativo estamos intentando que una Company cree un Remark
	// en una Application que no es suya.
	
	// El sistema, al invocar al método create() del servicio RemarkService, comprueba
	// que la aplicación que se pasa como parámetro no se encuentra entre las aplicaciones
	// que pertenecen al actor logueado, y se produce un error.

	@Test(expected=IllegalArgumentException.class)
	public void testCreateRemarkNegativeTest() {
		super.authenticate("company2");
		Company principal = this.companyService.findByPrincipal();
		
		Integer applicationId = super.getEntityId("application1");
		final Remark remark = this.remarkService.create(applicationId);
		Application application = this.applicationService.findOne(applicationId);
		remark.setApplication(application);
		remark.setBody("TEST");
		remark.setCompany(principal);
		remark.setMode("FINAL");
		remark.setPublicationMoment(new Date());
		remark.setTicker("tickerTEST");
		remark.setPicture("https://www.TEST.com");
		this.remarkService.save(remark);
		super.unauthenticate();
	}

	
}
