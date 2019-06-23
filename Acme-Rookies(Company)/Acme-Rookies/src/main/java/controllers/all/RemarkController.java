
package controllers.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RemarkService;
import controllers.AbstractController;
import domain.Remark;

@Controller
@RequestMapping("/remark")
public class RemarkController extends AbstractController {

	@Autowired
	private RemarkService	remarkService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int remarkId) {
		ModelAndView result;
		try {
			final Remark remark = this.remarkService.findOne(remarkId);
			result = new ModelAndView("remark/show");
			result.addObject("remark", remark);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
}
