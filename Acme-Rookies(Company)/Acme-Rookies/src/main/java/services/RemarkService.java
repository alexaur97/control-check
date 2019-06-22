
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RemarkRepository;
import domain.Remark;

@Service
@Transactional
public class RemarkService {

	@Autowired
	private RemarkRepository	remarkRepository;


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

	//	public Remark reconstruct(final Remark remark, final BindingResult binding) {
	//		final Remark res = remark;
	//
	//		final Auditor auditor = this.auditorService.findByPrincipal();
	//		res.setAuditor(auditor);
	//		final Date date = new Date();
	//		res.setMoment(date);
	//
	//		this.validator.validate(res, binding);
	//		System.out.println(binding);
	//		return res;
	//	}

	//Other Methods-------
}
