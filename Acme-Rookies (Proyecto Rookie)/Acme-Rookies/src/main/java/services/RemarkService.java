
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RemarkRepository;
import domain.Application;
import domain.Company;
import domain.Remark;
import domain.Rookie;

@Service
@Transactional
public class RemarkService {

	@Autowired
	private RemarkRepository remarkRepository;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private RookieService rookieService;

	@Autowired
	private Validator validator;

	public Collection<Remark> findAllByApplication(final int applicationId) {
		return this.remarkRepository.findAllByApplication(applicationId);
	}

	public Remark create(int applicationId) {
		Company principal = this.companyService.findByPrincipal();

		Application application = this.applicationService.findOne(applicationId);
		Collection<Application> applications = this.applicationService.findByPrincipalCompany(principal.getId());
		
		Assert.isTrue(applications.contains(application));
		
		final Remark result = new Remark();

		return result;
	}

	public Remark findOne(final int remarkId) {
		final Remark result = this.remarkRepository.findOne(remarkId);
		return result;
	}

	public Remark save(final Remark remark) {
		Company principal = this.companyService.findByPrincipal();
		if (remark.getId() != 0) {
			final Remark old = this.findOne(remark.getId());
			Assert.isTrue(old.getMode().equals("DRAFT"));
			
			Collection<Remark> remarks = this.remarkRepository.findAllByPrincipal(principal.getId());
			Assert.isTrue(remarks.contains(old));
		}
		final Remark result = this.remarkRepository.save(remark);
		return result;
	}
	
	public Remark reconstructCreate(final Remark remark, final BindingResult binding) {
		Company principal = this.companyService.findByPrincipal();
		final Remark result = remark;

		Date date = new Date();
		result.setCompany(principal);
		if (remark.getMode().equals("FINAL")) {
			result.setPublicationMoment(date);
		}
		
		String ticker = this.generateTicker(date);
		result.setTicker(ticker);
		this.validator.validate(result, binding);
		System.out.println(binding);
		return result;

	}

	public Remark reconstructEdit(final Remark remark, final BindingResult binding) {
		this.companyService.findByPrincipal();

		final Remark old = this.remarkRepository.findOne(remark.getId());
		final Remark result = remark;

		result.setApplication(old.getApplication());
		result.setCompany(old.getCompany());
		if (remark.getMode().equals("FINAL")) {
			result.setPublicationMoment(new Date());
		}
		result.setTicker(old.getTicker());
		this.validator.validate(result, binding);
		System.out.println(binding);
		return result;

	}

	private String generateTicker(final Date date) {
		final Integer year = date.getYear() % 100;
		final Integer month = date.getMonth()+1;
		final Integer day = date.getDate();

		String yy = "" + year;
		String mm = "" + month;
		String dd = "" + day;

		if (year < 10)
			yy = "0" + yy;

		if (month < 10)
			mm = "0" + mm;
		if (day < 10)
			dd = "0" + dd;
		final String result = yy + mm + dd;
		return result;
	}

	public void delete(final Remark remark) {
		Company principal = this.companyService.findByPrincipal();
		Assert.isTrue(remark.getMode().equals("DRAFT"));
		Collection<Remark> remarks = this.remarkRepository.findAllByPrincipal(principal.getId());
		Assert.isTrue(remarks.contains(remark));
		this.remarkRepository.delete(remark);
	}

	public Collection<Remark> findAllFinal(final int applicationId) {
		return this.remarkRepository.findAllFinal(applicationId);
	}

	public Collection<Remark> findAllByPrincipal() {
		final Company principal = this.companyService.findByPrincipal();
		return this.remarkRepository.findAllByPrincipal(principal.getId());
	}

	public Collection<Remark> findAllByRookie() {
		final Rookie principal = this.rookieService.findByPrincipal();
		return this.remarkRepository.findAllByRookie(principal.getId());
	}

}
