
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.CompanyService;
import services.ConfigurationService;
import services.PositionService;
import domain.Company;
import domain.Position;
import forms.PositionForm;
import forms.SearchForm;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CompanyService			companyService;


	// Constructor---------------------------------------

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Position> positions = this.positionService.findFinalMode();
		result = new ModelAndView("position/list");
		result.addObject("positions", positions);

		result.addObject("searchForm", new SearchForm());
		result.addObject("requestURI", "position/list.do");
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		return result;
	}

	@RequestMapping(value = "/listByCompany", method = RequestMethod.GET)
	public ModelAndView listByCompany(final int companyId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		domain.Company company = null;

		try {
			company = this.companyService.findOne(companyId);
			Assert.isTrue(company != null);

			final Collection<Position> positions = this.positionService.findFinalByCompany(company);

			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("searchForm", new SearchForm());
			result.addObject("requestURI", "position/listByCompany.do");
			result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/position/list.do");
			if (company == null)
				redirectAttrs.addFlashAttribute("message", "company.error.unexist");
			else
				redirectAttrs.addFlashAttribute("message", "commit.error");
		}

		return result;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SearchForm searchForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {

				final Collection<Position> positions = this.positionService.search(searchForm.getKeyword());

				result = new ModelAndView("position/list");

				result.addObject("searchForm", searchForm);
				result.addObject("positions", positions);
				result.addObject("requestURI", "position/list.do");
				result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
				result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

			} catch (final Throwable oops) {

				result = new ModelAndView("redirect:/welcome/index.do");
				result.addObject("message", "message.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView edit(final int positionId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Position position = null;
		final PositionForm positionForm = new PositionForm();

		try {
			position = this.positionService.findOne(positionId);
			Assert.isTrue(position != null);

			final Company company = this.companyService.findCompanyByUseraccount(LoginService.getPrincipal());
			if (position.getCompany() != company)
				Assert.isTrue(!position.isDraftmode(), "position.error.draftmode");

			positionForm.setTicker(position.getTicker());
			positionForm.setId(position.getId());
			positionForm.setDeadline(position.getDeadline());
			positionForm.setDescription(position.getDescription());
			positionForm.setDraftmode(position.isDraftmode());
			positionForm.setProfile(position.getProfile());
			positionForm.setSalary(position.getSalary());
			positionForm.setSkills(position.getSkills());
			positionForm.setTecnologies(position.getTecnologies());
			positionForm.setTitle(position.getTitle());

			result = new ModelAndView("position/display");
			result.addObject("positionForm", positionForm);
			result.addObject("readonly", true);
			result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/position/list.do");
			if (position == null)
				redirectAttrs.addFlashAttribute("message", "position.error.unexist");
			else if (position.isDraftmode())
				redirectAttrs.addFlashAttribute("message", "position.error.display");
			else
				redirectAttrs.addFlashAttribute("message", "commit.error");
		}
		return result;
	}

}
