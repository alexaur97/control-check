
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AuditService;
import services.AuditorService;
import services.RemarkService;
import utilities.AbstractTest;
import domain.Audit;
import domain.Auditor;
import domain.Remark;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RemarkServiceTest extends AbstractTest {

	@Autowired
	private AuditService	auditService;
	@Autowired
	private AuditorService	auditorService;
	@Autowired
	private RemarkService	remarkService;


	@Test
	public void testCreateRemark() {
		super.authenticate("auditor1");
		final Remark remark = new Remark();
		final int IdAudit = super.getEntityId("audit1");
		final Audit audit = this.auditService.findOne(IdAudit);
		final Auditor auditor = this.auditorService.findByPrincipal();
		remark.setAudit(audit);
		remark.setAuditor(auditor);
		remark.setBody("TEST");
		remark.setMode("FINAL");
		final Remark remarkFinal = this.remarkService.reconstruct(remark, null);
		this.remarkService.save(remarkFinal);
		super.unauthenticate();
	}

	//	Para el caso negativo estamos intentando que un Auditor cree una remark en una auditoria que no es suya
	//esto debe provocar un fallo en el sistema porque este solo puede crear remarks en sus auditorias
	//Análisis del sentence coverage: el sistema al llamar al validate comprueba
	// que las restricciones del dominio se cumplen.
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRemarkError() {
		super.authenticate("auditor1");
		final Remark remark = new Remark();
		final int IdAudit = super.getEntityId("audit3");
		final Audit audit = this.auditService.findOne(IdAudit);
		final Auditor auditor = this.auditorService.findByPrincipal();
		remark.setAudit(audit);
		remark.setAuditor(auditor);
		remark.setBody("TEST");
		remark.setMode("FINAL");
		remark.setPicture("https://as.com/");
		final Remark remarkFinal = this.remarkService.reconstruct(remark, null);
		this.remarkService.save(remarkFinal);
		super.unauthenticate();
	}
}
