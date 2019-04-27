package controllers.Company;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.ApplicationService;
import services.CompanyService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Application;
import domain.Company;
import forms.ApplicationForm2;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructor---------------------------------------------------------

	public ApplicationCompanyController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			int companyId = companyService.findCompanyByUseraccountId(
					LoginService.getPrincipal().getId()).getId();
			Assert.notNull(companyService.findOne(companyId));

			Collection<Application> applications = applicationService
					.findByCompanyId(companyId);

			Collection<Application> applicationsPending = new ArrayList<Application>();
			Collection<Application> applicationsRejected = new ArrayList<Application>();
			Collection<Application> applicationsAccepted = new ArrayList<Application>();
			Collection<Application> applicationsSubmitted = new ArrayList<Application>();

			for (Application a : applications) {
				if (a.getStatus().equals("PENDING")) {
					applicationsPending.add(a);
				} else if (a.getStatus().equals("ACCEPTED")) {
					applicationsAccepted.add(a);
				} else if (a.getStatus().equals("REJECTED")) {
					applicationsRejected.add(a);
				} else if (a.getStatus().equals("SUBMITTED")) {
					applicationsSubmitted.add(a);
				}
			}

			result = new ModelAndView("application/listCompany");
			result.addObject("applicationsPending", applicationsPending);
			result.addObject("applicationsRejected", applicationsRejected);
			result.addObject("applicationsAccepted", applicationsAccepted);
			result.addObject("applicationsSubmitted", applicationsSubmitted);
			result.addObject("requestURI", "application/company/list.do");
			result.addObject("banner", this.configurationService.findAll()
					.iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll()
					.iterator().next().getSystemName());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// DISPLAY
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int applicationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final ApplicationForm2 applicationForm = new ApplicationForm2();
		Company company = null;
		Application application = null;
		try {
			company = companyService.findCompanyByUseraccount(LoginService
					.getPrincipal());
			Assert.notNull(company);
			application = applicationService.findOne(applicationId);
			Assert.notNull(application);
			Assert.isTrue(application.getPosition().getCompany()
					.equals(company));
			applicationForm.setId(application.getId());
			applicationForm.setCurricula(application.getCurricula());
			applicationForm.setLinkAnswer(application.getLinkAnswer());
			applicationForm.setPosition(application.getPosition());
			applicationForm.setTextAnswer(application.getTextAnswer());
			applicationForm.setCompany(application.getPosition().getCompany());
			applicationForm.setHacker(application.getHacker());
			applicationForm.setProblem(application.getProblem());
			applicationForm.setPublicationMoment(application
					.getPublicationMoment());
			applicationForm.setStatus(application.getStatus());
			applicationForm.setSubmissionMoment(application
					.getSubmissionMoment());

			result = this.displayModelAndView(applicationForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/company/list.do");
			if (company == null) {
				redirectAttrs.addFlashAttribute("message", "commit.error");
			} else if (application == null) {
				redirectAttrs.addFlashAttribute("message",
						"application.error.unexists");
			} else if (!application.getPosition().getCompany().equals(company)) {
				redirectAttrs.addFlashAttribute("message",
						"application.error.notYours");
			}
		}
		return result;
	}

	// ACCEPT
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(final int applicationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Company company = null;
		Application application = null;
		try {
			company = companyService.findCompanyByUseraccount(LoginService
					.getPrincipal());
			Assert.notNull(company);
			application = applicationService.findOne(applicationId);
			Assert.notNull(application);
			Assert.isTrue(application.getPosition().getCompany()
					.equals(company));
			Assert.isTrue(application.getStatus().equals("SUBMITTED"));
			applicationService.accept(application, company);

			result = new ModelAndView("redirect:/application/company/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/company/list.do");
			if (company == null) {
				redirectAttrs.addFlashAttribute("message", "commit.error");
			} else if (application == null) {
				redirectAttrs.addFlashAttribute("message",
						"application.error.unexists");
			} else if (!application.getPosition().getCompany().equals(company)) {
				redirectAttrs.addFlashAttribute("message",
						"application.error.notYours");
			} else if (!application.getStatus().equals("SUBMITTED")) {
				redirectAttrs.addFlashAttribute("message",
						"application.error.notSubmitted");
			}
		}
		return result;
	}

	// REJECT
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(final int applicationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Company company = null;
		Application application = null;
		try {
			company = companyService.findCompanyByUseraccount(LoginService
					.getPrincipal());
			Assert.notNull(company);
			application = applicationService.findOne(applicationId);
			Assert.notNull(application);
			Assert.isTrue(application.getPosition().getCompany()
					.equals(company));
			Assert.isTrue(application.getStatus().equals("SUBMITTED"));
			applicationService.reject(application, company);

			result = new ModelAndView("redirect:/application/company/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/company/list.do");
			if (company == null) {
				redirectAttrs.addFlashAttribute("message", "commit.error");
			} else if (application == null) {
				redirectAttrs.addFlashAttribute("message",
						"application.error.unexists");
			} else if (!application.getPosition().getCompany().equals(company)) {
				redirectAttrs.addFlashAttribute("message",
						"application.error.notYours");
			} else if (!application.getStatus().equals("SUBMITTED")) {
				redirectAttrs.addFlashAttribute("message",
						"application.error.notSubmitted");
			}
		}
		return result;
	}

	// MODEL
	protected ModelAndView displayModelAndView(
			final ApplicationForm2 applicationForm) {
		ModelAndView result;
		result = this.displayModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView displayModelAndView(
			final ApplicationForm2 applicationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/display");

		result.addObject("message", message);
		result.addObject("requestURI",
				"application/company/display.do?applicationId="
						+ applicationForm.getId());
		result.addObject("applicationForm", applicationForm);
		result.addObject("isRead", true);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}
}
