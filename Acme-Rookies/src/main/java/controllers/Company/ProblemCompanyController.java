package controllers.Company;

import java.util.ArrayList;
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
import domain.Problem;
import forms.ProblemForm;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	// Services-----------------------------------------------------------
	@Autowired
	private ProblemService problemService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private PositionService positionService;

	// Constructor---------------------------------------------------------

	public ProblemCompanyController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {

			final Integer companyId = this.companyService
					.findCompanyByUseraccount(LoginService.getPrincipal())
					.getId();
			Assert.notNull(this.companyService.findOne(companyId));

			result = new ModelAndView("problem/list");
			final Collection<Problem> problems = this.problemService
					.findByCompanyId(companyId);

			result.addObject("problems", problems);
			result.addObject("requestURI", "problem/company/list.do?companyId="
					+ companyId);
			result.addObject("banner", this.configurationService.findAll()
					.iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll()
					.iterator().next().getSystemName());

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final RedirectAttributes redirectAttrs) {
		ModelAndView result = null;
		Collection<Position> positions = new ArrayList<Position>();
		try {
			final Company b = this.companyService
					.findCompanyByUseraccount(LoginService.getPrincipal());
			positions = this.positionService
					.findDraftByCompany(b);
			Assert.isTrue(!positions.isEmpty());
			final ProblemForm problemForm = new ProblemForm();
			problemForm.setId(0);

			result = this.createModelAndView(problemForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/problem/company/list.do");
			if (positions.isEmpty())
				redirectAttrs.addFlashAttribute("message",
						"problem.error.noPositions");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProblemForm problemForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(problemForm,
					"problem.commit.error");
		else
			try {
				final Problem problem = this.problemService.create();
				problem.setTitle(problemForm.getTitle());
				problem.setStatement(problemForm.getStatement());
				problem.setHint(problemForm.getHint());
				problem.setAttachments(problemForm.getAttachments());
				problem.setDraftmode(problemForm.isDraftMode());
				problem.setPosition(problemForm.getPosition());

				this.problemService.save(problem);

				result = new ModelAndView("redirect:/problem/company/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(problemForm,
						"problem.commit.error");
			}
		return result;
	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int problemId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Problem problem;
		final Company b = this.companyService
				.findCompanyByUseraccountId(LoginService.getPrincipal().getId());
		try {
			problem = this.problemService.findOne(problemId);
			Assert.notNull(problem);
			Assert.isTrue(this.problemService.findOne(problemId).getPosition()
					.getCompany().equals(b));
			Assert.isTrue(this.problemService.findOne(problemId).isDraftmode());

			final ProblemForm problemForm = new ProblemForm();
			problemForm.setId(problem.getId());
			problemForm.setTitle(problem.getTitle());
			problemForm.setStatement(problem.getStatement());
			problemForm.setHint(problem.getHint());
			problemForm.setAttachments(problem.getAttachments());
			problemForm.setDraftMode(problem.isDraftmode());
			problemForm.setPosition(problem.getPosition());

			result = this.editModelAndView(problemForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/problem/company/list.do");
			if (this.problemService.findOne(problemId) == null)
				redirectAttrs.addFlashAttribute("message",
						"problem.error.unexist");
			else if (this.problemService.findOne(problemId).isDraftmode() == false)
				redirectAttrs.addFlashAttribute("message",
						"problem.error.notDraftMode");
			else if (!this.problemService.findOne(problemId).getPosition()
					.getCompany().equals(b))
				redirectAttrs.addFlashAttribute("message",
						"problem.error.notFromActor");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final ProblemForm problemForm,
			final BindingResult binding) {
		ModelAndView result;
		final Company b = this.companyService
				.findCompanyByUseraccountId(LoginService.getPrincipal().getId());
		if (binding.hasErrors())
			result = this.editModelAndView(problemForm, "problem.commit.error");
		else
			try {
				Assert.notNull(problemForm);
				final Problem problem = this.problemService.findOne(problemForm
						.getId());
				Assert.isTrue(problem.getPosition().getCompany().equals(b));
				problem.setHint(problemForm.getHint());
				problem.setDraftmode(problemForm.isDraftMode());
				problem.setAttachments(problemForm.getAttachments());
				problem.setStatement(problemForm.getStatement());
				problem.setTitle(problemForm.getTitle());
				// problem.setPosition(problemForm.getPosition());

				this.problemService.save(problem);

				result = new ModelAndView("redirect:/problem/company/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(problemForm,
						"problem.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final ProblemForm problemForm,
			final BindingResult binding) {
		ModelAndView result;
		final Company b = this.companyService
				.findCompanyByUseraccountId(LoginService.getPrincipal().getId());
		if (binding.hasErrors())
			result = this.editModelAndView(problemForm, "problem.commit.error");
		else
			try {
				Assert.notNull(problemForm);
				final Problem problem = this.problemService.findOne(problemForm
						.getId());
				Assert.isTrue(problem.getPosition().getCompany().equals(b));
				Assert.isTrue(problem.isDraftmode());

				this.problemService.delete(this.problemService
						.findOne(problemForm.getId()));

				result = new ModelAndView("redirect:/problem/company/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(problemForm,
						"problem.commit.error");
			}
		return result;
	}

	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(final int problemId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Problem problem = null;
		final Company b = this.companyService
				.findCompanyByUseraccountId(LoginService.getPrincipal().getId());
		try {
			problem = this.problemService.findOne(problemId);
			Assert.notNull(problem);
			Assert.isTrue(problem.getPosition().getCompany().getId() == b
					.getId());

			final ProblemForm problemForm = new ProblemForm();
			problemForm.setId(problem.getId());
			problemForm.setDraftMode(problem.isDraftmode());
			problemForm.setHint(problem.getHint());
			problemForm.setTitle(problem.getTitle());
			problemForm.setStatement(problem.getStatement());
			problemForm.setAttachments(problem.getAttachments());

			result = this.ShowModelAndView(problemForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/problem/company/list.do");
			if (this.problemService.findOne(problemId) == null)
				redirectAttrs.addFlashAttribute("message",
						"problem.error.unexist");
			else if (this.problemService.findOne(problemId).isDraftmode() == false)
				redirectAttrs.addFlashAttribute("message",
						"problem.error.notDraftMode");
			else if (!this.problemService.findOne(problemId).getPosition()
					.getCompany().equals(b))
				redirectAttrs.addFlashAttribute("message",
						"problem.error.notFromActor");
		}
		return result;
	}

	// MODEL
	protected ModelAndView createModelAndView(final ProblemForm problemForm) {
		ModelAndView result;
		result = this.createModelAndView(problemForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final ProblemForm problemForm,
			final String message) {
		final ModelAndView result;

		result = new ModelAndView("problem/create");

		final Company b = this.companyService
				.findCompanyByUseraccount(LoginService.getPrincipal());
		final Collection<Position> positions = this.positionService
				.findDraftByCompany(b);

		result.addObject("message", message);
		result.addObject("requestURI", "problem/company/create.do");
		result.addObject("problemForm", problemForm);
		result.addObject("isRead", false);
		result.addObject("id", 0);
		result.addObject("positions", positions);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView editModelAndView(final ProblemForm problemForm) {
		ModelAndView result;
		result = this.editModelAndView(problemForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final ProblemForm problemForm,
			final String message) {
		final ModelAndView result;

		result = new ModelAndView("problem/edit");

		final Company b = this.companyService
				.findCompanyByUseraccount(LoginService.getPrincipal());
		final Collection<Position> positions = this.positionService
				.findByCompanyIdSingle(b.getId());
		
		result.addObject("message", message);
		result.addObject("requestURI", "problem/company/edit.do?problemId="
				+ problemForm.getId());
		result.addObject("problemForm", problemForm);
		result.addObject("positions", positions);
		result.addObject("id", problemForm.getId());
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView ShowModelAndView(final ProblemForm problemForm) {
		ModelAndView result;
		result = this.ShowModelAndView(problemForm, null);
		return result;
	}

	protected ModelAndView ShowModelAndView(final ProblemForm problemForm,
			final String message) {
		final ModelAndView result;

		final Company b = this.companyService
				.findCompanyByUseraccount(LoginService.getPrincipal());
		final Collection<Position> positions = this.positionService
				.findByCompanyIdSingle(b.getId());
		
		result = new ModelAndView("problem/show");
		result.addObject("message", message);
		result.addObject("requestURI", "problem/company/show.do?problemId="
				+ problemForm.getId());
		result.addObject("problemForm", problemForm);
		result.addObject("id", problemForm.getId());
		result.addObject("positions", positions);
		result.addObject("isRead", true);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}
}
