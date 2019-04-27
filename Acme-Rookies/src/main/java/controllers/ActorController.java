/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.CompanyService;
import services.ConfigurationService;
import domain.Actor;
import domain.Company;
import forms.ActorForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ConfigurationService	configurationService;

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final ActorForm actorForm = new ActorForm();

		final Authority hacker = new Authority();
		hacker.setAuthority(Authority.HACKER);
		final Authority company = new Authority();
		company.setAuthority(Authority.COMPANY);
		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);

		actorForm.setComercialName("---");
		try {
			final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
			Assert.notNull(a);

			if (a.getUserAccount().getAuthorities().contains(hacker))
				actorForm.setAuth("HACKER");
			else if (a.getUserAccount().getAuthorities().contains(company)) {
				actorForm.setAuth("COMPANY");
				final Company comp = this.companyService.findCompanyByUseraccountId(a.getUserAccount().getId());
				actorForm.setComercialName(comp.getComercialName());
			} else if (a.getUserAccount().getAuthorities().contains(admin))
				actorForm.setAuth("ADMIN");

			else
				throw new NullPointerException();

			actorForm.setUserAccount(a.getUserAccount());
			actorForm.setId(a.getId());
			actorForm.setVersion(a.getVersion());
			actorForm.setName(a.getName());
			actorForm.setSurname(a.getSurnames());
			actorForm.setVATNumber(a.getVATNumber());
			actorForm.setPhoto(a.getPhoto());
			actorForm.setEmail(a.getEmail());
			actorForm.setPhone(a.getPhone());
			actorForm.setAddress(a.getAddress());

			actorForm.setHolderName(a.getCreditCard().getHolderName());
			actorForm.setBrandName(a.getCreditCard().getBrandName());
			actorForm.setNumber(a.getCreditCard().getNumber());
			actorForm.setExpirationMonth(a.getCreditCard().getExpirationMonth());
			actorForm.setExpirationYear(a.getCreditCard().getExpirationYear());
			actorForm.setCVVCode(a.getCreditCard().getCVVCode());

			result = this.createEditModelAndView(actorForm);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	//
	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorForm actorForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else
			try {

				this.actorService.update(actorForm);
				result = this.createEditModelAndView(actorForm, "actor.commit.ok");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorForm, "actor.commit.error");

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
		ModelAndView result;
		final Authority company = new Authority();
		company.setAuthority(Authority.COMPANY);
		if (actorForm.getUserAccount().getAuthorities().contains(company)) {
			actorForm.setAuth("COMPANY");
			final Company comp = this.companyService.findCompanyByUseraccountId(actorForm.getUserAccount().getId());
			actorForm.setComercialName(comp.getComercialName());
		}

		result = new ModelAndView("actor/edit");
		result.addObject("actorForm", actorForm);

		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "actor/edit.do");
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());

		return result;
	}

	//	@RequestMapping(value = "/show", method = RequestMethod.GET)
	//	public ModelAndView show(@RequestParam final int actorId, final RedirectAttributes redirectAttrs) {
	//		ModelAndView modelAndView = new ModelAndView("actor/edit");
	//
	//		final Actor actor = this.actorService.findOne(actorId);
	//		final Company company = this.companyService.findOne(actorId);
	//		final domain.Hacker hacker = this.hackerService.findOne(actorId);
	//
	//		try {
	//			Assert.notNull(actor);
	//			final ActorForm actorForm = new ActorForm();
	//
	//			if (company != null)
	//				actorForm.setComercialName(company.getComercialName());
	//			else if (hacker != null)
	//				actorForm.setComercialName("---");
	//
	//			actorForm.setUserAccount(actor.getUserAccount());
	//			actorForm.setName(actor.getName());
	//			actorForm.setSurname(actor.getSurnames());
	//			actorForm.setPhoto(actor.getPhoto());
	//			actorForm.setEmail(actor.getEmail());
	//			actorForm.setPhone(actor.getPhone());
	//			actorForm.setAddress(actor.getAddress());
	//
	//			modelAndView.addObject("actor", actor);
	//			modelAndView.addObject("isRead", true);
	//			modelAndView.addObject("requestURI", "/actor/administrator/show.do?actorId=" + actorId);
	//			modelAndView.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
	//			modelAndView.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
	//
	//		} catch (final Throwable e) {
	//			modelAndView = new ModelAndView("redirect:/welcome/index.do");
	//			if (actor == null)
	//				redirectAttrs.addFlashAttribute("message", "actor.error.unexist");
	//		}
	//		return modelAndView;
	//
	//	}

}
