
package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.RemarkService;
import utilities.AbstractTest;
import domain.Remark;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RemarkServiceTest extends AbstractTest {

	@Autowired
	private RemarkService	remarkService;


	@Test
	public void testCreateRemarkGood() throws ParseException {
		super.authenticate("company1");
		final Remark r = this.remarkService.create();
		r.setBody("Remark de prueba");
		r.setMode("DRAFT");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		final String stringFecha = "02-09-2020 12:30";
		final Date fecha = sdf.parse(stringFecha);
		r.setMoment(fecha);
		r.setPicture("http://foto.jpg");
		r.setTicker("2019-06-06");

		this.remarkService.save(r);
		super.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRemarkError() throws ParseException {
		super.authenticate("auditor1");
		final Remark r = this.remarkService.create();
		r.setBody("Remark de prueba");
		r.setMode("DRAFT");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		final String stringFecha = "02-09-2020 12:30";
		final Date fecha = sdf.parse(stringFecha);
		r.setMoment(fecha);
		r.setPicture("http://foto.jpg");
		r.setTicker("2019-06-06");

		this.remarkService.save(r);
		super.unauthenticate();

	}
}
