package controllers.Company;

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
import services.ProblemService;
import controllers.AbstractController;
import domain.Company;
import domain.Position;
import forms.PositionForm;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private PositionService positionService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProblemService problemService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructor---------------------------------------------------------

	public PositionCompanyController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Company company = this.companyService
				.findCompanyByUseraccount(LoginService.getPrincipal());

		final Collection<domain.Position> positionsDraft = this.positionService
				.findDraftByCompany(company);
		final Collection<domain.Position> positionsFinal = this.positionService
				.findFinalByCompany(company);

		result = new ModelAndView("position/company/list");

		result.addObject("positionsDraft", positionsDraft);
		result.addObject("positionsFinal", positionsFinal);
		result.addObject("requestURI", "position/company/list.do");

		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final PositionForm positionForm = new PositionForm();
		positionForm.setDraftmode(true);
		positionForm.setId(0);
		result = this.createModelAndView(positionForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PositionForm positionForm,
			final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createModelAndView(positionForm, "commit.error");
		else
			try {
				Position position = this.positionService.create();

				position = this.positionService.reconstruct(position,
						positionForm);

				this.positionService.save(position);
				result = new ModelAndView("redirect:/position/company/list.do");

			} catch (final Throwable oops) {
				if (oops.getMessage() == "position.error.deadline")
					result = this.createModelAndView(positionForm,
							oops.getMessage());
				else
					result = this.createModelAndView(positionForm,
							"commit.error");
			}
		return result;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int positionId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Position position = null;
		final PositionForm positionForm = new PositionForm();
		Company company = null;
		try {
			position = this.positionService.findOne(positionId);
			Assert.isTrue(position != null);

			company = this.companyService.findCompanyByUseraccount(LoginService
					.getPrincipal());
			Assert.notNull(company);
			Assert.isTrue(position.getCompany().getId() == company.getId());
			Assert.isTrue(position.isDraftmode());

			positionForm.setId(position.getId());
			positionForm.setDeadline(position.getDeadline());
			positionForm.setDescription(position.getDescription());
			positionForm.setDraftmode(position.isDraftmode());
			positionForm.setProfile(position.getProfile());
			positionForm.setSalary(position.getSalary());
			positionForm.setSkills(position.getSkills());
			positionForm.setTecnologies(position.getTecnologies());
			positionForm.setTitle(position.getTitle());

			result = new ModelAndView("position/edit");
			result.addObject("positionForm", positionForm);

			result.addObject("banner", this.configurationService.findAll()
					.iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll()
					.iterator().next().getSystemName());

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/position/company/list.do");
			if (position == null)
				redirectAttrs.addFlashAttribute("message",
						"position.error.unexist");

			else if (!(position.getCompany().getId() == company.getId()))
				redirectAttrs.addFlashAttribute("message",
						"position.error.notYours");
			else if (position.isDraftmode())
				redirectAttrs.addFlashAttribute("message",
						"position.error.finalmode");
			else
				redirectAttrs.addFlashAttribute("message", "commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final PositionForm positionForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(positionForm);
		else
			try {
				Position position = this.positionService.findOne(positionForm
						.getId());

				final Company company = this.companyService
						.findCompanyByUseraccount(LoginService.getPrincipal());
				Assert.notNull(company);
				Assert.isTrue(position.getCompany().getId() == company.getId());
				if (positionForm.isDraftmode() == false)
					Assert.isTrue(
							this.problemService.findByPositionId(
									positionForm.getId()).size() >= 2,
							"position.error.noProblem");
				position = this.positionService.reconstruct(position,
						positionForm);

				this.positionService.save(position);
				result = new ModelAndView("redirect:/position/company/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage() == "position.error.used")
					result = this.editModelAndView(positionForm,
							oops.getMessage());
				else if (oops.getMessage() == "position.error.noProblem")
					result = this.editModelAndView(positionForm,
							oops.getMessage());
				else if (oops.getMessage() == "position.error.deadline")
					result = this.editModelAndView(positionForm,
							oops.getMessage());
				else
					result = this
							.editModelAndView(positionForm, "commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(final PositionForm positionForm,
			final BindingResult binding) {
		ModelAndView result;

		try {
			final Position position = this.positionService.findOne(positionForm
					.getId());
			this.positionService.delete(position);

			result = new ModelAndView("redirect:/position/company/list.do");
		} catch (final Throwable oops) {
			// if (oops.getMessage() == "position.error.used")
			// result = this.editModelAndView(positionForm, oops.getMessage());
			// else
			result = this.editModelAndView(positionForm, "commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(final int positionId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			final domain.Position position = this.positionService
					.findOne(positionId);
			this.positionService.cancel(position);

			result = new ModelAndView("redirect:/position/company/list.do");

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/position/list.do");
			if (e.getMessage() == "position.error.draftmode")
				redirectAttrs.addFlashAttribute("message",
						"position.error.draftmode");
			else
				redirectAttrs.addFlashAttribute("message", "commit.error");
		}
		return result;
	}

	// AUXILIARY METHODS

	protected ModelAndView createModelAndView(final PositionForm positionForm) {
		ModelAndView result;
		result = this.createModelAndView(positionForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final PositionForm positionForm,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("position/create");
		result.addObject("message", message);
		result.addObject("requestURI", "position/company/create.do");
		result.addObject("positionForm", positionForm);
		result.addObject("idPosition", positionForm.getId());
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());

		return result;
	}

	protected ModelAndView editModelAndView(final PositionForm positionForm) {
		ModelAndView result;
		result = this.editModelAndView(positionForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final PositionForm positionForm,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("position/edit");
		result.addObject("message", message);
		result.addObject("requestURI", "position/company/edit.do");
		result.addObject("positionForm", positionForm);

		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());

		return result;
	}

}
