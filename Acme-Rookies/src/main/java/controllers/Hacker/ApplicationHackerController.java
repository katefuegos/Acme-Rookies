
package controllers.Hacker;

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
import services.ApplicationService;
import services.ConfigurationService;
import services.CurriculaService;
import services.HackerService;
import services.PositionService;
import controllers.AbstractController;
import domain.Application;
import domain.Curricula;
import domain.Hacker;
import domain.Position;
import forms.ApplicationForm;
import forms.ApplicationForm2;

@Controller
@RequestMapping("/application/hacker")
public class ApplicationHackerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public ApplicationHackerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			final int hackerId = this.hackerService.findHackerByUseraccount(LoginService.getPrincipal()).getId();
			Assert.notNull(this.hackerService.findOne(hackerId));

			final Collection<Application> applicationsPending = this.applicationService.findPendingByHackerId(hackerId);
			final Collection<Application> applicationsRejected = this.applicationService.findRejectedByHackerId(hackerId);
			final Collection<Application> applicationsAccepted = this.applicationService.findAcceptedByHackerId(hackerId);
			final Collection<Application> applicationsSubmitted = this.applicationService.findSubmittedByHackerId(hackerId);

			//			final Collection<Application> applications = this.applicationService.findByHackerId(hackerId);
			//
			//			Collection<Application> applicationsPending = new ArrayList<Application>();
			//			Collection<Application> applicationsRejected = new ArrayList<Application>();
			//			Collection<Application> applicationsAccepted = new ArrayList<Application>();
			//			Collection<Application> applicationsSubmitted = new ArrayList<Application>();
			//
			//			for (Application a : applications) {
			//				if (a.getStatus().equals("PENDING")) {
			//					applicationsPending.add(a);
			//				} else if (a.getStatus().equals("ACCEPTED")) {
			//					applicationsAccepted.add(a);
			//				} else if (a.getStatus().equals("REJECTED")) {
			//					applicationsRejected.add(a);
			//				} else if (a.getStatus().equals("SUBMITTED")) {
			//					applicationsSubmitted.add(a);
			//				}
			//			}

			result = new ModelAndView("application/listHacker");
			result.addObject("applicationsPending", applicationsPending);
			result.addObject("applicationsRejected", applicationsRejected);
			result.addObject("applicationsAccepted", applicationsAccepted);
			result.addObject("applicationsSubmitted", applicationsSubmitted);
			result.addObject("requestURI", "application/hacker/list.do");
			result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final ApplicationForm applicationForm = new ApplicationForm();
		Collection<Curricula> curriculas = new ArrayList<Curricula>();
		final Collection<Position> positions = this.positionService.findFinalMode();
		Hacker hacker = null;
		try {
			Assert.isTrue(!positions.isEmpty());
			hacker = this.hackerService.findHackerByUseraccount(LoginService.getPrincipal());
			Assert.notNull(hacker);
			curriculas = this.curriculaService.findNoCopies(hacker.getId());
			Assert.isTrue(!curriculas.isEmpty());
			applicationForm.setId(0);

			result = this.createModelAndView(applicationForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message", "commit.error");
			else if (positions.isEmpty())
				redirectAttrs.addFlashAttribute("message", "application.error.NoPositions");
			else if (curriculas.isEmpty())
				redirectAttrs.addFlashAttribute("message", "application.error.NoCurricula");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ApplicationForm applicationForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(applicationForm, "commit.error");
		else
			try {
				Application application = this.applicationService.create(applicationForm.getPosition().getId(), applicationForm.getCurricula().getId());
				application.setLinkAnswer(applicationForm.getLinkAnswer());
				application.setTextAnswer(applicationForm.getTextAnswer());

				application = this.applicationService.save(application);

				result = new ModelAndView("redirect:/application/hacker/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(applicationForm, "commit.error");
			}
		return result;
	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int applicationId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final ApplicationForm applicationForm = new ApplicationForm();
		Hacker hacker = null;
		Application application = null;
		try {
			hacker = this.hackerService.findHackerByUseraccount(LoginService.getPrincipal());
			Assert.notNull(hacker);
			application = this.applicationService.findOne(applicationId);
			Assert.notNull(application);
			Assert.isTrue(application.getHacker().equals(hacker));
			Assert.isTrue(application.getStatus().equals("PENDING"));
			applicationForm.setId(application.getId());
			applicationForm.setCurricula(application.getCurricula());
			applicationForm.setLinkAnswer(application.getLinkAnswer());
			applicationForm.setPosition(application.getPosition());
			applicationForm.setTextAnswer(application.getTextAnswer());

			result = this.editModelAndView(applicationForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message", "commit.error");
			else if (application == null)
				redirectAttrs.addFlashAttribute("message", "application.error.unexists");
			else if (!application.getHacker().equals(hacker))
				redirectAttrs.addFlashAttribute("message", "application.error.notYours");
			else if (!application.getStatus().equals("PENDING"))
				redirectAttrs.addFlashAttribute("message", "application.error.notPending");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final ApplicationForm applicationForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(applicationForm, "commit.error");
		else
			try {
				Application application = this.applicationService.findOne(applicationForm.getId());
				application.setLinkAnswer(applicationForm.getLinkAnswer());
				application.setTextAnswer(applicationForm.getTextAnswer());

				application = this.applicationService.save(application);

				result = new ModelAndView("redirect:/application/hacker/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(applicationForm, "commit.error");
			}
		return result;
	}

	// DISPLAY
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int applicationId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final ApplicationForm2 applicationForm = new ApplicationForm2();
		Hacker hacker = null;
		Application application = null;
		try {
			hacker = this.hackerService.findHackerByUseraccount(LoginService.getPrincipal());
			Assert.notNull(hacker);
			application = this.applicationService.findOne(applicationId);
			Assert.notNull(application);
			Assert.isTrue(application.getHacker().equals(hacker));
			applicationForm.setId(application.getId());
			applicationForm.setCurricula(application.getCurricula());
			applicationForm.setLinkAnswer(application.getLinkAnswer());
			applicationForm.setPosition(application.getPosition());
			applicationForm.setTextAnswer(application.getTextAnswer());
			applicationForm.setCompany(application.getPosition().getCompany());
			applicationForm.setHacker(application.getHacker());
			applicationForm.setProblem(application.getProblem());
			applicationForm.setPublicationMoment(application.getPublicationMoment());
			applicationForm.setStatus(application.getStatus());
			applicationForm.setSubmissionMoment(application.getSubmissionMoment());

			result = this.displayModelAndView(applicationForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message", "commit.error");
			else if (application == null)
				redirectAttrs.addFlashAttribute("message", "application.error.unexists");
			else if (!application.getHacker().equals(hacker))
				redirectAttrs.addFlashAttribute("message", "application.error.notYours");
		}
		return result;
	}

	// MODEL
	protected ModelAndView createModelAndView(final ApplicationForm applicationForm) {
		ModelAndView result;
		result = this.createModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final ApplicationForm applicationForm, final String message) {
		final ModelAndView result;

		final Collection<Curricula> curriculas = this.curriculaService.findNoCopies(this.hackerService.findHackerByUseraccount(LoginService.getPrincipal()).getId());
		final Collection<Position> positions = this.positionService.findFinalMode();

		result = new ModelAndView("application/create");

		result.addObject("message", message);
		result.addObject("requestURI", "application/hacker/create.do");
		result.addObject("applicationForm", applicationForm);
		result.addObject("isRead", false);
		result.addObject("curriculas", curriculas);
		result.addObject("positions", positions);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView editModelAndView(final ApplicationForm applicationForm) {
		ModelAndView result;
		result = this.editModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final ApplicationForm applicationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/edit");

		result.addObject("message", message);
		result.addObject("requestURI", "application/hacker/edit.do?applicationId=" + applicationForm.getId());
		result.addObject("applicationForm", applicationForm);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView displayModelAndView(final ApplicationForm2 applicationForm) {
		ModelAndView result;
		result = this.displayModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView displayModelAndView(final ApplicationForm2 applicationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/display");

		result.addObject("message", message);
		result.addObject("requestURI", "application/hacker/display.do?applicationId=" + applicationForm.getId());
		result.addObject("applicationForm", applicationForm);
		result.addObject("isRead", true);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}
}
