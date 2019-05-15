package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.NotificationService;
import domain.Actor;
import domain.Notification;
import forms.NotificationForm;

@Controller
@RequestMapping("/notification/actor")
public class NotificationController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructor---------------------------------------------------------

	public NotificationController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			Actor actor = actorService.findActorByUsername(LoginService
					.getPrincipal().getUsername());
			Assert.notNull(actor);

			Collection<Notification> notifications = notificationService
					.findByActorId(actor.getId());

			result = new ModelAndView("notification/list");
			result.addObject("notifications", notifications);
			result.addObject("requestURI", "notification/actor/list.do");
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
	public ModelAndView display(final int notificationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final NotificationForm notificationForm = new NotificationForm();
		Actor actor = null;
		Notification notification = null;
		try {
			actor = actorService.findActorByUsername(LoginService
					.getPrincipal().getUsername());
			Assert.notNull(actor);
			notification = notificationService.findOne(notificationId);
			Assert.notNull(notification);
			Assert.isTrue(notification.getActor().equals(actor));
			notificationForm.setId(notification.getId());
			notificationForm.setSubject(notification.getSubject());
			notificationForm.setBody(notification.getBody());
			notificationForm.setMoment(notification.getMoment());

			result = this.displayModelAndView(notificationForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/notification/actor/list.do");
			if (actor == null) {
				redirectAttrs.addFlashAttribute("message", "commit.error");
			} else if (notification == null) {
				redirectAttrs.addFlashAttribute("message",
						"notification.error.unexists");
			} else if (!notification.getActor().equals(actor)) {
				redirectAttrs.addFlashAttribute("message",
						"notification.error.notYours");
			}
		}
		return result;
	}

	// MODEL
	protected ModelAndView displayModelAndView(
			final NotificationForm notificationForm) {
		ModelAndView result;
		result = this.displayModelAndView(notificationForm, null);
		return result;
	}

	protected ModelAndView displayModelAndView(
			final NotificationForm notificationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("notification/display");

		result.addObject("message", message);
		result.addObject("requestURI",
				"notification/actor/display.do?notificationId="
						+ notificationForm.getId());
		result.addObject("notificationForm", notificationForm);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}
}
