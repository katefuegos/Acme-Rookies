package controllers.Administrator;

import java.util.Date;

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
import services.AdministratorService;
import services.ConfigurationService;
import services.NotificationService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Notification;
import forms.NotificationForm;

@Controller
@RequestMapping("/notification/administrator")
public class NotificationAdministratorController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructor---------------------------------------------------------

	public NotificationAdministratorController() {
		super();
	}

	// NOTIFY
	@RequestMapping(value = "/notifyBreach", method = RequestMethod.GET)
	public ModelAndView notifyBreach(final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final NotificationForm notificationForm = new NotificationForm();
		Administrator admin = null;
		try {
			admin = administratorService.findByUseraccount(LoginService
					.getPrincipal());
			Assert.notNull(admin);
			notificationForm.setId(0);
			notificationForm.setMoment(new Date(System.currentTimeMillis() - 1000));
			notificationForm
					.setBody("Se ha detectado una brecha de seguridad que afecta a todos los usuarios / We have detected a security breach who affects all users");
			notificationForm
					.setSubject("Brecha de seguridad / Security breach");

			result = this.notifyModelAndView(notificationForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/hacker/list.do");
			if (admin == null) {
				redirectAttrs.addFlashAttribute("message", "commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/notifyBreach", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final NotificationForm notificationForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.notifyModelAndView(notificationForm,
					"commit.error");
		else
			try {
				Notification notification = this.notificationService.create();
				notification.setBody(notificationForm.getBody());
				notification.setSubject(notificationForm.getSubject());

				this.notificationService.broadcast(notification);

				result = new ModelAndView(
						"redirect:/notification/actor/list.do");
			} catch (final Throwable oops) {
				result = this.notifyModelAndView(notificationForm,
						"commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/notifyBreachHackers", method = RequestMethod.GET)
	public ModelAndView notifyBreachHackers(
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final NotificationForm notificationForm = new NotificationForm();
		Administrator admin = null;
		try {
			admin = administratorService.findByUseraccount(LoginService
					.getPrincipal());
			Assert.notNull(admin);
			notificationForm.setId(0);
			notificationForm.setMoment(new Date(System.currentTimeMillis() - 1000));
			notificationForm
					.setBody("Se ha detectado una brecha de seguridad que afecta a los hackers / We have detected a security breach who affects hackers");
			notificationForm
					.setSubject("Brecha de seguridad hackers / Security breach hackers");

			result = this.notifyHackersModelAndView(notificationForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/hacker/list.do");
			if (admin == null) {
				redirectAttrs.addFlashAttribute("message", "commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/notifyBreachHackers", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final NotificationForm notificationForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.notifyHackersModelAndView(notificationForm,
					"commit.error");
		else
			try {
				Notification notification = this.notificationService.create();
				notification.setBody(notificationForm.getBody());
				notification.setSubject(notificationForm.getSubject());

				this.notificationService.broadcastHackers(notification);

				result = new ModelAndView(
						"redirect:/notification/actor/list.do");
			} catch (final Throwable oops) {
				result = this.notifyHackersModelAndView(notificationForm,
						"commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/notifyBreachCompanies", method = RequestMethod.GET)
	public ModelAndView notifyBreachCompanies(
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final NotificationForm notificationForm = new NotificationForm();
		Administrator admin = null;
		try {
			admin = administratorService.findByUseraccount(LoginService
					.getPrincipal());
			Assert.notNull(admin);
			notificationForm.setId(0);
			notificationForm.setMoment(new Date(System.currentTimeMillis() - 1000));
			notificationForm
					.setBody("Se ha detectado una brecha de seguridad que afecta a las empresas / We have detected a security breach who affects companies");
			notificationForm
					.setSubject("Brecha de seguridad empresas / Security breach companies");

			result = this.notifyCompaniesModelAndView(notificationForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/hacker/list.do");
			if (admin == null) {
				redirectAttrs.addFlashAttribute("message", "commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/notifyBreachCompanies", method = RequestMethod.POST, params = "save")
	public ModelAndView save3(@Valid final NotificationForm notificationForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.notifyCompaniesModelAndView(notificationForm,
					"commit.error");
		else
			try {
				Notification notification = this.notificationService.create();
				notification.setBody(notificationForm.getBody());
				notification.setSubject(notificationForm.getSubject());

				this.notificationService.broadcastCompanies(notification);

				result = new ModelAndView(
						"redirect:/notification/actor/list.do");
			} catch (final Throwable oops) {
				result = this.notifyCompaniesModelAndView(notificationForm,
						"commit.error");
			}
		return result;
	}

	// MODEL
	protected ModelAndView notifyModelAndView(
			final NotificationForm notificationForm) {
		ModelAndView result;
		result = this.notifyModelAndView(notificationForm, null);
		return result;
	}

	protected ModelAndView notifyModelAndView(
			final NotificationForm notificationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("notification/broadcast");

		result.addObject("message", message);
		result.addObject("requestURI",
				"notification/administrator/notifyBreach.do");
		result.addObject("notificationForm", notificationForm);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView notifyHackersModelAndView(
			final NotificationForm notificationForm) {
		ModelAndView result;
		result = this.notifyHackersModelAndView(notificationForm, null);
		return result;
	}

	protected ModelAndView notifyHackersModelAndView(
			final NotificationForm notificationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("notification/broadcastHackers");

		result.addObject("message", message);
		result.addObject("requestURI",
				"notification/administrator/notifyBreachHackers.do");
		result.addObject("notificationForm", notificationForm);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView notifyCompaniesModelAndView(
			final NotificationForm notificationForm) {
		ModelAndView result;
		result = this.notifyCompaniesModelAndView(notificationForm, null);
		return result;
	}

	protected ModelAndView notifyCompaniesModelAndView(
			final NotificationForm notificationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("notification/broadcastCompanies");

		result.addObject("message", message);
		result.addObject("requestURI",
				"notification/administrator/notifyBreachCompanies.do");
		result.addObject("notificationForm", notificationForm);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}
}
