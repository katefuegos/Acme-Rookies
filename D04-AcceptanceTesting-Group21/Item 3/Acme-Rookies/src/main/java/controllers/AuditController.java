package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.AuditService;
import services.ConfigurationService;
import domain.Audit;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private AuditService auditService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructor---------------------------------------------------------

	public AuditController() {
		super();
	}

	@RequestMapping(value = "/listByPosition", method = RequestMethod.GET)
	public ModelAndView list(final int positionId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			Collection<Audit> audits = auditService.findByPositionId(positionId);
			result = new ModelAndView("audit/list2");
			result.addObject("audits", audits);
			result.addObject("requestURI", "audit/listByPosition.do?positionId=" + positionId);
			result.addObject("banner", this.configurationService.findAll()
					.iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll()
					.iterator().next().getSystemName());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}
}
