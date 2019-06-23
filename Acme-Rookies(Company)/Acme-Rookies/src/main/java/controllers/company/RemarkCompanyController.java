
package controllers.company;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.CompanyService;
import services.RemarkService;
import controllers.AbstractController;
import domain.Audit;
import domain.Company;
import domain.Remark;

@Controller
@RequestMapping("/remark/company/")
public class RemarkCompanyController extends AbstractController {

	//Servicio
	@Autowired
	private CompanyService	companyService;

	//Servicios
	@Autowired
	private RemarkService	remarkService;

	@Autowired
	private AuditService	auditService;


	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView MyList() {
		ModelAndView result;
		try {
			this.companyService.findByPrincipal();
			Collection<Remark> remarks;
			final Date fecha = new Date();
			final Long date = fecha.getTime();
			final Boolean c = true;

			remarks = this.remarkService.findByCompany();
			result = new ModelAndView("remark/list");
			result.addObject("requestURI", "remark/company/list.do");
			result.addObject("remarks", remarks);
			result.addObject("date", date);
			result.addObject("c", c);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		try {
			final Remark remark = new Remark();
			this.companyService.findByPrincipal();

			result = this.createEditModelAndView(remark);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int remarkId) {
		ModelAndView res;
		try {

			this.companyService.findByPrincipal();
			final Remark remark = this.remarkService.findOne(remarkId);
			Assert.notNull(remark);
			Assert.isTrue(remark.getMode().equals("DRAFT"));
			final Collection<Remark> remarks = this.remarkService.findByCompany();
			Assert.isTrue(remarks.contains(remark));
			res = this.createEditModelAndView(remark);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("remark") Remark remark, final BindingResult binding) {
		ModelAndView res;
		remark = this.remarkService.reconstruct(remark, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(remark);
		else
			try {
				this.remarkService.save(remark);
				res = new ModelAndView("redirect:/remark/company/myList.do");

			} catch (final Throwable oops) {

				res = this.createEditModelAndView(remark, "remark.commit.error");

			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Remark remark, final BindingResult binding) {
		ModelAndView result;
		final Remark res = this.remarkService.findOne(remark.getId());
		try {

			final Company company = this.companyService.findByPrincipal();
			Assert.isTrue(remark.getAudit().getPosition().getCompany().equals(company));
			this.remarkService.delete(res);
			result = new ModelAndView("redirect:/remark/company/myList.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	//	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Position position, final BindingResult binding) {
	//		ModelAndView result;
	//		final Position pos = this.positionService.findOne(position.getId());
	//		try {
	//
	//			this.positionService.delete(pos);
	//			result = new ModelAndView("redirect:/position/company/myList.do");
	//
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(pos, "position.commit.error");
	//
	//			final String msg = oops.getMessage();
	//			if (msg.equals("positioncannotDelete")) {
	//				final Boolean positioncannotDelete = true;
	//				result.addObject("positioncannotDelete", positioncannotDelete);
	//
	//			}
	//		}
	//
	//		return result;
	//	}
	protected ModelAndView createEditModelAndView(final Remark remark) {
		return this.createEditModelAndView(remark, null);
	}
	protected ModelAndView createEditModelAndView(final Remark remark, final String messageCode) {
		final ModelAndView res;
		final Collection<Audit> audits = this.auditService.findByCompany();
		res = new ModelAndView("remark/edit");
		res.addObject("remark", remark);
		res.addObject("audits", audits);
		res.addObject("message", messageCode);

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int remarkId) {
		ModelAndView result;
		final Remark remark;

		try {
			final Company company = this.companyService.findByPrincipal();
			Assert.notNull(remarkId);
			remark = this.remarkService.findOne(remarkId);
			Assert.isTrue(remark.getAudit().getPosition().getCompany().equals(company));

			result = new ModelAndView("remark/show");

			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);
			result.addObject("remark", remark);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	public ModelAndView display(@RequestParam final int remarkId) {
	//		ModelAndView result;
	//		final Remark remark;
	//
	//		try {
	//			final Company company = this.companyService.findByPrincipal();
	//			Assert.notNull(remarkId);
	//			remark = this.remarkService.findOne(remarkId);
	//			Assert.isTrue(remark.getAudit().getPosition().getCompany().equals(company));
	//
	//			result = new ModelAndView("remark/display");
	//			result.addObject("remark", remark);
	//
	//		} catch (final Exception e) {
	//			result = new ModelAndView("redirect:/#");
	//		}
	//
	//		return result;
	//	}

}
