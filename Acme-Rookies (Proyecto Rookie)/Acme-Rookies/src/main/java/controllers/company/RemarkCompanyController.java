package controllers.company;

import java.util.Collection;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Application;
import domain.Remark;
import services.ApplicationService;
import services.RemarkService;

@Controller
@RequestMapping("/remark/company")
public class RemarkCompanyController extends AbstractController {

	@Autowired
	private RemarkService remarkService;

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			result = new ModelAndView("remark/list");
			Collection<Remark> remarks = this.remarkService.findAllByPrincipal();
			result.addObject("remarks", remarks);
			result.addObject("requestURI", "remark/company/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int applicationId) {
		ModelAndView result;
		try {
			Remark remark = this.remarkService.create(applicationId);
			Application application = this.applicationService.findOne(applicationId);
			result = this.createEditModelAndView(remark);
			result.addObject("application", application);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int remarkId) {
		ModelAndView result;
		try {
			Remark remark = this.remarkService.findOne(remarkId);
			result = this.createEditModelAndView(remark);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int remarkId) {
		ModelAndView result;
		try {
			result = new ModelAndView("remark/show");
			Remark remark = this.remarkService.findOne(remarkId);
			result.addObject("remark", remark);
			Assert.assertNotNull(remark);
			
			Collection<Remark> remarks = this.remarkService.findAllByPrincipal();
			Boolean bool = remarks.contains(remark);
			result.addObject("bool",bool);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("remark") Remark remark, @RequestParam int applicationId,
			final BindingResult binding) {
		ModelAndView result;
		Application application = this.applicationService.findOne(applicationId);
		remark.setApplication(application);
		remark = this.remarkService.reconstructCreate(remark, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(remark);
			result.addObject("application", application);
		} else
			try {
				final Remark saved = this.remarkService.save(remark);
				result = new ModelAndView("redirect:/remark/company/show.do?remarkId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(remark, "remark.commit.error");
				result.addObject("application",application);
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("remark") Remark remark, final BindingResult binding) {
		ModelAndView result;
		remark = this.remarkService.reconstructEdit(remark, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(remark);
		else
			try {
				final Remark saved = this.remarkService.save(remark);
				result = new ModelAndView("redirect:/remark/company/show.do?remarkId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(remark, "remark.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("remark") Remark remark) {
		ModelAndView result;
		remark = this.remarkService.findOne(remark.getId());
		try {
			Application app = remark.getApplication();
			this.remarkService.delete(remark);
			result = new ModelAndView("redirect:/application/company/show.do?applicationId=" + app.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(remark, "remark.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Remark remark) {
		return this.createEditModelAndView(remark, null);
	}

	protected ModelAndView createEditModelAndView(final Remark remark, final String messageCode) {
		ModelAndView result;
		if (remark.getId() == 0)
			result = new ModelAndView("remark/create");
		else
			result = new ModelAndView("remark/edit");
		result.addObject("remark", remark);
		result.addObject("message", messageCode);
		return result;
	}

}
