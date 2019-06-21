
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.RemarkService;
import controllers.AbstractController;
import domain.Audit;
import domain.Remark;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	@Autowired
	private AuditService	auditService;

	@Autowired
	private RemarkService	remarkService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		ModelAndView result;
		try {
			final Audit audit = this.auditService.findOne(auditId);
			final Collection<Remark> remarks = this.remarkService.findByAudit(audit.getId());
			final Collection<Remark> remarksFinal = this.remarkService.findByAuditFinal(audit.getId());
			result = new ModelAndView("audit/display");
			result.addObject("audit", audit);
			result.addObject("remarks", remarks);
			result.addObject("remarksFinal", remarksFinal);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
}
