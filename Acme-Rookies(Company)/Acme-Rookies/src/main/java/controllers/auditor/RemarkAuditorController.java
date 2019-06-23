
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.RemarkService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Remark;

@Controller
@RequestMapping("/remark/auditor/")
public class RemarkAuditorController extends AbstractController {

	//Servicio
	@Autowired
	private AuditorService	auditorService;

	//Servicios
	@Autowired
	private RemarkService	remarkService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Remark> remarks;
			final Boolean a = true;

			this.auditorService.findByPrincipal();
			remarks = this.remarkService.findAllFinalMode();
			result = new ModelAndView("remark/list");
			result.addObject("requestURI", "remark/auditor/list.do");
			result.addObject("remarks", remarks);
			result.addObject("a", a);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int remarkId) {
		ModelAndView result;
		final Remark remark;

		try {
			final Auditor auditor = this.auditorService.findByPrincipal();
			Assert.notNull(remarkId);
			remark = this.remarkService.findOne(remarkId);
			Assert.isTrue(remark.getAudit().getAuditor().equals(auditor));

			result = new ModelAndView("remark/show");
			result.addObject("remark", remark);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
