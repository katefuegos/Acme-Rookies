/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.Rookie;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ConfigurationService;
import services.FinderService;
import services.RookieService;
import controllers.AbstractController;
import domain.Finder;
import domain.Rookie;
import domain.Position;

@Controller
@RequestMapping("/finder/rookie")
public class FinderRookieController extends AbstractController {

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public FinderRookieController() {
		super();
	}

	// Update finder ---------------------------------------------------------------		

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateFinder() {
		ModelAndView result;

		final Finder finder = this.finderService.findFinder();

		result = new ModelAndView("finder/rookie/update");
		result.addObject("finder", finder);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, params = "save")
	public ModelAndView updateFinder(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("finder/rookie/update");
			result.addObject("finder", finder);
		} else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:listPositions.do");
			} catch (final Exception e) {

				result = new ModelAndView("finder/rookie/update");
				result.addObject("finder", finder);
				result.addObject("message", "commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, params = "clean")
	public ModelAndView cleanFinder(final Finder finder, final BindingResult binding) {
		ModelAndView result;

		try {
			this.finderService.clear(finder);
			result = new ModelAndView("redirect:listPositions.do");
		} catch (final Exception e) {

			result = new ModelAndView("finder/rookie/update");
			result.addObject("finder", finder);
			result.addObject("message", "commit.error");
		}
		return result;
	}

	// List result finder ---------------------------------------------------------------		

	@RequestMapping("/listPositions")
	public ModelAndView listPositions() {
		ModelAndView result;
		Finder finder = this.finderService.findFinder();
		//Comprobar fecha ultima actualización
		finder = this.finderService.updateFinder(finder);
		//Obtener resultados fixuptasks de finder
		final Collection<Position> positions = finder.getPositions();
		final Rookie rookie = this.rookieService.findRookieByUseraccount(LoginService.getPrincipal());
		result = new ModelAndView("finder/rookie/listPositions");
		result.addObject("positions", positions);
		result.addObject("rookieId", rookie.getId());
		result.addObject("requestURI", "finder/rookie/listPositions.do");
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}
}
