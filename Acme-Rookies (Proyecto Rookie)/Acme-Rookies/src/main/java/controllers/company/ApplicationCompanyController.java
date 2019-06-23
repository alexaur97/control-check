
package controllers.company;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import services.RemarkService;
import controllers.AbstractController;
import domain.Application;
import domain.Company;
import domain.Remark;

@Controller
@RequestMapping("/application/company/")
public class ApplicationCompanyController extends AbstractController {

	//Repository
	@Autowired
	private CompanyService		companyService;

	//Servicios

	@Autowired
	private ApplicationService	applicationService;
	
	@Autowired
	private RemarkService remarkService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		ModelAndView result;
		try {
			final Company company = this.companyService.findByPrincipal();
			final Application application = this.applicationService.findOne(applicationId);
			Assert.isTrue(company.equals(application.getPosition().getCompany()));
			final Boolean b = application.getProblem().getAttachments().isEmpty();
			result = new ModelAndView("application/show");
			result.addObject("application", application);
			result.addObject("b", b);
		
			//remark --------------------
		
			Collection<Remark> remarks = this.remarkService.findAllByApplication(applicationId);
			result.addObject("remarks", remarks);
			
			Collection<Application> applications = this.applicationService.findByPrincipalCompany(company.getId());
			
			Boolean bool = applications.contains(application);
			result.addObject("bool", bool);
			
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);
			
			final Date d = new Date();
			final Long date = d.getTime();
			result.addObject("date", date);
			
			//---------------------------
		
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Company company = this.companyService.findByPrincipal();
			final Collection<Application> applications;
			final Collection<Application> applicationsPending;
			applications = this.applicationService.findApplicationsCompany(company.getId());
			applicationsPending = this.applicationService.findApplicationsPendingByCompany(company.getId());
			result = new ModelAndView("application/list");
			result.addObject("requestURI", "application/company/list.do");
			result.addObject("applications", applications);
			result.addObject("applicationsPending", applicationsPending);
			final String s = "SUBMITTED";
			result.addObject("s", s);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView result;
		final Collection<Application> applications;
		final Collection<Application> applicationsPending;
		final String s = "SUBMITTED";
		try {
			final Application application = this.applicationService.acceptApplicationChanges(applicationId);
			this.applicationService.saveCompany(application);
			result = new ModelAndView("redirect:/application/company/list.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView result;
		final Collection<Application> applications;
		final Collection<Application> applicationsPending;
		final String s = "SUBMITTED";
		try {
			final Application application = this.applicationService.rejectApplicationChanges(applicationId);
			this.applicationService.saveCompany(application);
			final Company company = this.companyService.findByPrincipal();
			applications = this.applicationService.findApplicationsCompany(company.getId());
			applicationsPending = this.applicationService.findApplicationsPendingByCompany(company.getId());

			result = new ModelAndView("application/list");
			result.addObject("requestURI", "application/company/list.do");
			result.addObject("applications", applications);
			result.addObject("applicationsPending", applicationsPending);
			result.addObject("s", s);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

}
