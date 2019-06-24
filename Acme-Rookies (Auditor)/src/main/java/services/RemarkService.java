
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
import domain.Remark;

@Service
@Transactional
public class RemarkService {

	//Managed repository -------------------

	@Autowired
	private RemarkRepository	remarkRepository;

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private Validator			validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public RemarkService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Remark create() {
		Remark result;

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
		Assert.isTrue(remark.getAudit().getAuditor().equals(remark.getAuditor()));
		if (remark.getId() != 0) {
			final Remark remarkDB = this.findOne(remark.getId());
			System.out.println(remarkDB.getMode());
			Assert.isTrue(remarkDB.getMode().equals("DRAFT"));
		}
		this.remarkRepository.save(remark);
	}

	public void delete(final Remark remark) {
		Assert.notNull(remark);
		Assert.isTrue(remark.getMode().equals("DRAFT"));
		this.remarkRepository.delete(remark);
	}

	public Collection<Remark> findByAuditor() {
		final Auditor auditor = this.auditorService.findByPrincipal();
		final Collection<Remark> res = this.remarkRepository.findByAuditor(auditor.getId());
		return res;
	}

	public Remark reconstruct(final Remark remark, final BindingResult binding) {
		final Remark res = remark;

		final Auditor auditor = this.auditorService.findByPrincipal();
		res.setAuditor(auditor);
		final Date date = new Date();
		res.setMoment(date);
		if (res.getTicker() == null) {
			final Date aux = date;
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			final String ticker = dateFormat.format(aux);
			res.setTicker(ticker);
		}
		this.validator.validate(res, binding);
		System.out.println(binding);
		return res;
	}
	public Collection<Remark> findByAudit(final int auditId) {
		final Collection<Remark> res = this.remarkRepository.findByAudit(auditId);
		return res;
	}

	public Collection<Remark> findByAuditFinal(final int auditId) {
		final Collection<Remark> res = this.remarkRepository.findByAuditFinal(auditId);
		return res;
	}
}
