
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.ActorService;
import services.ConfigurationService;
import domain.Actor;
import forms.ActorForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	// Register hacker and company
	@RequestMapping(value = "/actor", method = RequestMethod.GET)
	public ModelAndView createHackerAndCompany(@RequestParam(required = false, defaultValue = "default") final String authority) {
		ModelAndView modelAndView;
		final ActorForm actorForm = new ActorForm();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();

		try {
			switch (authority) {
			case "HACKER":
				a.setAuthority(Authority.HACKER);
				actorForm.setAuth("HACKER");
				actorForm.setComercialName("---");
				break;
			case "COMPANY":
				a.setAuthority(Authority.COMPANY);
				actorForm.setAuth("COMPANY");
				break;
			default:
				throw new NullPointerException();
			}

			authorities.add(a);
			userAccount.setAuthorities(authorities);
			userAccount.setEnabled(true);
			actorForm.setUserAccount(userAccount);

			modelAndView = this.createEditModelAndView(actorForm);

		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/welcome/index.do");
		}
		return modelAndView;
	}

	// Save
	@RequestMapping(value = "/actor", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorForm actorForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else
			try {

				Assert.isTrue(actorForm.getCheckTerms(), "actor.check.true");
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				actorForm.getUserAccount().setPassword(encoder.encodePassword(actorForm.getUserAccount().getPassword(), null));
				this.actorService.update(actorForm);

				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				final Actor test = this.actorService.findActorByUsername(actorForm.getUserAccount().getUsername());

				if (test != null)
					result = this.createEditModelAndView(actorForm, "actor.userExists");
				else if (oops.getMessage() == "actor.check.true")
					result = this.createEditModelAndView(actorForm, oops.getMessage());
				else if (oops.getMessage() == "actor.creditcard.error.date.invalid")
					result = this.createEditModelAndView(actorForm, oops.getMessage());

				else
					result = this.createEditModelAndView(actorForm, "message.commit.error");

			}
		return result;
	}

	// CreateModelAndView
	protected ModelAndView createEditModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		ModelAndView result = null;

		// TODO faltan actores
		final Collection<Authority> authorities = actorForm.getUserAccount().getAuthorities();
		final Authority company = new Authority();
		company.setAuthority("COMPANY");
		final Authority hacker = new Authority();
		hacker.setAuthority("HACKER");

		if (authorities.contains(company))
			result = new ModelAndView("register/company");
		else if (authorities.contains(hacker))
			result = new ModelAndView("register/hacker");
		else
			throw new NullPointerException();

		result.addObject("actorForm", actorForm);

		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "register/actor.do");
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

}
