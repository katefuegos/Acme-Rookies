
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CompanyService;
import services.ConfigurationService;
import domain.Company;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Company> companys = this.companyService.findAll();

		result = new ModelAndView("company/list");
		result.addObject("companys", companys);
		result.addObject("requestURI", "company/list.do");

		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView edit(final int companyId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Company company = null;

		try {
			company = this.companyService.findOne(companyId);
			Assert.isTrue(company != null);

			result = new ModelAndView("company/display");
			result.addObject("company", company);
			result.addObject("readonly", true);

			result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/company/list.do");
			if (company == null)
				redirectAttrs.addFlashAttribute("message", "company.error.unexist");
			else
				redirectAttrs.addFlashAttribute("message", "commit.error");
		}
		return result;
	}

}
