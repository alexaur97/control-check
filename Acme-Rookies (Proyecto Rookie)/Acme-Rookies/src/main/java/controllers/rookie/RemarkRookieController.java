package controllers.rookie;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Remark;
import services.RemarkService;

@Controller
@RequestMapping("/remark/rookie")
public class RemarkRookieController extends AbstractController {

	@Autowired
	private RemarkService remarkService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			result = new ModelAndView("remark/list");
			Collection<Remark> remarks = this.remarkService.findAllByRookie();
			result.addObject("remarks", remarks);
			result.addObject("requestURI", "remark/rookie/list.do");
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);
			
			final Date d = new Date();
			final Long date = d.getTime();
			result.addObject("date", date);
			
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
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
}