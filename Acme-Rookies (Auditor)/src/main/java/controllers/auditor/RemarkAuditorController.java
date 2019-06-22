
package controllers.auditor;

import java.util.Collection;
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
import services.AuditorService;
import services.RemarkService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Remark;

@Controller
@RequestMapping("/remark/auditor/")
public class RemarkAuditorController extends AbstractController {

	@Autowired
	private AuditService	auditService;
	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private RemarkService	remarkService;


	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			this.auditorService.findByPrincipal();
			final Collection<Remark> remarks = this.remarkService.findByAuditor();

			result = new ModelAndView("remark/myList");
			result.addObject("requestURI", "remark/auditor/myList.do");
			result.addObject("remarks", remarks);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		try {
			Remark remark;
			remark = new Remark();

			result = this.createEditModelAndView(remark);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int remarkId) {
		ModelAndView res;
		try {

			final Remark remark = this.remarkService.findOne(remarkId);
			Assert.notNull(remark);

			Assert.isTrue(remark.getMode().equals("DRAFT"));
			final Collection<Remark> remarks = this.remarkService.findByAuditor();
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
		try {
			Assert.notNull(remark.getAudit());

			remark = this.remarkService.reconstruct(remark, binding);

			if (binding.hasErrors())
				res = this.createEditModelAndView(remark);
			else
				try {

					this.remarkService.save(remark);
					res = new ModelAndView("redirect:/remark/auditor/myList.do");

				} catch (final Throwable oops) {

					res = this.createEditModelAndView(remark, "remark.commit.error");

				}
		} catch (final Throwable oops) {

			res = this.createEditModelAndView(remark, "remark.commit.error");

		}
		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int remarkId) {
		ModelAndView res;
		try {

			final Remark remark = this.remarkService.findOne(remarkId);
			Assert.notNull(remark);

			final Collection<Remark> remarks = this.remarkService.findByAuditor();
			Assert.isTrue(remarks.contains(remark));
			res = new ModelAndView("remark/show");
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
			res.addObject("remark", remark);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Remark remark) {
		return this.createEditModelAndView(remark, null);
	}

	protected ModelAndView createEditModelAndView(final Remark remark, final String messageCode) {
		final Auditor auditor = this.auditorService.findByPrincipal();

		final Collection<Audit> audits = this.auditService.findByAuditor();
		final ModelAndView res;
		res = new ModelAndView("remark/edit");
		res.addObject("remark", remark);
		res.addObject("audits", audits);
		res.addObject("message", messageCode);

		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int remarkId) {
		ModelAndView res;
		try {

			final Remark remark = this.remarkService.findOne(remarkId);
			Assert.notNull(remark);

			Assert.isTrue(remark.getMode().equals("DRAFT"));
			final Collection<Remark> remarks = this.remarkService.findByAuditor();
			Assert.isTrue(remarks.contains(remark));
			this.remarkService.delete(remark);
			res = new ModelAndView("redirect:/remark/auditor/myList.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}
}
