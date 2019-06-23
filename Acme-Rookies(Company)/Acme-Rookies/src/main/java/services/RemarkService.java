
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RemarkRepository;
import domain.Auditor;
import domain.Company;
import domain.Remark;

@Service
@Transactional
public class RemarkService {

	@Autowired
	private RemarkRepository	remarkRepository;

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private Validator			validator;


	//COnstructors -------------------------
	public RemarkService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Remark create() {
		Remark result;

		this.companyService.findByPrincipal();

		result = new Remark();

		return result;
	}

	public Collection<Remark> findAll() {
		Collection<Remark> result;

		result = this.remarkRepository.findAll();

		return result;
	}

	public Remark findOne(final int remarkId) {
		Remark result;

		result = this.remarkRepository.findOne(remarkId);

		return result;
	}

	public void save(final Remark remark) {
		Assert.notNull(remark);
		this.companyService.findByPrincipal();
		if (remark.getId() != 0) {
			final Remark remarkDB = this.findOne(remark.getId());
			Assert.isTrue(remarkDB.getMode().equals("DRAFT"));
		}
		this.remarkRepository.save(remark);
	}

	public void delete(final Remark remark) {
		Assert.notNull(remark);
		Assert.isTrue(remark.getMode().equals("DRAFT"));
		this.remarkRepository.delete(remark);
	}

	public Collection<Remark> findByCompany() {
		final Company company = this.companyService.findByPrincipal();
		final Collection<Remark> res = this.remarkRepository.findByCompany(company.getId());
		return res;
	}

	public Collection<Remark> findAllFinalMode() {
		final Auditor aud = this.auditorService.findByPrincipal();
		final Collection<Remark> res = this.remarkRepository.findAllFinalMode(aud.getId());
		return res;
	}

	public Collection<Remark> findByAudit(final Integer id) {
		final Collection<Remark> res = this.remarkRepository.findByAudit(id);
		return res;
	}

	public Remark reconstruct(final Remark remark, final BindingResult binding) {
		this.companyService.findByPrincipal();
		final Remark res = remark;

		final Date date = new Date();
		res.setMoment(date);

		if (remark.getId() == 0) {

			final Date aux = date;
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			final String ticker = dateFormat.format(aux);
			res.setTicker(ticker);
		}

		res.setTicker(remark.getTicker());

		this.validator.validate(res, binding);
		System.out.println(binding);
		return res;
	}

	//Other Methods-------
}
