/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import domain.Actor;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Services

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");

		try {
			final boolean showMessage = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).isShowMessage();
			result.addObject("processExecuted", this.configurationService.findDefault().isProcesoEjecutado());
			result.addObject("showMessage", showMessage);

		} catch (final Exception e) {
			result.addObject("processExecuted", false);
			result.addObject("showMessage", false);

		}

		result.addObject("name", name);

		result.addObject("moment", moment);
		result.addObject("welomeMessage", this.configurationService.findAll().iterator().next().getWelcomeMessage().get(LocaleContextHolder.getLocale().toString().toUpperCase()));
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		return result;
	}

	@RequestMapping(value = "/notShowMessage")
	public ModelAndView notShowMessage() {

		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		if (actor.isShowMessage()) {
			actor.setShowMessage(false);
			this.actorService.save(actor);
		}

		final ModelAndView result = this.index(null);
		return result;
	}

	@RequestMapping(value = "/terms")
	public ModelAndView terms() {

		ModelAndView result;
		final String lang = LocaleContextHolder.getLocale().getLanguage().toString().toUpperCase();
		result = new ModelAndView("misc/terms");
		result.addObject("lang", lang);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		return result;
	}
}
