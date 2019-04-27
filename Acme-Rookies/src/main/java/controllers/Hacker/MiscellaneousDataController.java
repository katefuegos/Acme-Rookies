
package controllers.Hacker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import security.UserAccount;
import services.ConfigurationService;
import services.CurriculaService;
import services.HackerService;
import services.MiscellaneousDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Hacker;
import domain.MiscellaneousData;
import forms.MiscellaneousDataForm;

@Controller
@RequestMapping("/miscellaneousData/hacker")
public class MiscellaneousDataController extends AbstractController {

	// Services-----------------------------------------------------------
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private HackerService				hackerService;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private CurriculaService			curriculaService;


	// Constructor---------------------------------------------------------

	public MiscellaneousDataController() {
		super();
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam("curriculaId") final Integer curriculaId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Hacker hacker = null;
		Curricula curricula = null;

		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			curricula = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final MiscellaneousDataForm miscellaneousDataForm = new MiscellaneousDataForm();
			miscellaneousDataForm.setId(0);
			miscellaneousDataForm.setCurricula(curricula);

			result = this.createModelAndView(miscellaneousDataForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message", "curricula.commit.error");
			else if (curricula == null)
				redirectAttrs.addFlashAttribute("message", "curricula.error.unexist");
			else if (curricula.isCopy() == true)
				redirectAttrs.addFlashAttribute("message", "curricula.error.isCopy");
			else if (!curricula.getHacker().equals(hacker))
				redirectAttrs.addFlashAttribute("message", "curricula.error.notFromHacker");
		}
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousDataForm miscellaneousDataForm, final BindingResult binding) {
		ModelAndView result;
		Hacker hacker = null;

		if (binding.hasErrors())
			result = this.createModelAndView(miscellaneousDataForm, "miscellaneousData.commit.error");
		else
			try {
				final UserAccount userAccount = LoginService.getPrincipal();
				hacker = this.hackerService.findHackerByUseraccount(userAccount);
				Assert.notNull(hacker);

				final MiscellaneousData miscellaneousData = this.miscellaneousDataService.create();
				miscellaneousData.setText(miscellaneousDataForm.getText());
				miscellaneousData.setAttachments(miscellaneousDataForm.getAttachments());
				miscellaneousData.setCurricula(miscellaneousDataForm.getCurricula());

				this.miscellaneousDataService.save(miscellaneousData);

				result = new ModelAndView("redirect:/curricula/hacker/listData.do?curriculaId=" + miscellaneousData.getCurricula().getId());

			} catch (final Throwable oops) {
				result = this.createModelAndView(miscellaneousDataForm, "miscellaneousData.commit.error");
			}
		return result;
	}
	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int miscellaneousDataId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		MiscellaneousData miscellaneousData = null;
		Hacker hacker = null;
		Curricula curricula = null;

		try {

			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
			Assert.notNull(miscellaneousData);
			curricula = miscellaneousData.getCurricula();
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final MiscellaneousDataForm miscellaneousDataForm = new MiscellaneousDataForm();
			miscellaneousDataForm.setId(miscellaneousData.getId());
			miscellaneousDataForm.setText(miscellaneousData.getText());
			miscellaneousDataForm.setAttachments(miscellaneousData.getAttachments());
			miscellaneousDataForm.setCurricula(miscellaneousData.getCurricula());

			result = this.editModelAndView(miscellaneousDataForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message", "curricula.commit.error");
			else if (miscellaneousData == null)
				redirectAttrs.addFlashAttribute("message", "miscellaneousData.error.unexist");
			else if (curricula == null)
				redirectAttrs.addFlashAttribute("message", "curricula.error.unexist");
			else if (curricula.isCopy() == true)
				redirectAttrs.addFlashAttribute("message", "curricula.error.isCopy");
			else if (!curricula.getHacker().equals(hacker))
				redirectAttrs.addFlashAttribute("message", "curricula.error.notFromHacker");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final MiscellaneousDataForm miscellaneousDataForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(miscellaneousDataForm, "miscellaneousData.commit.error");
		else
			try {

				final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataForm.getId());
				Assert.notNull(miscellaneousData);
				miscellaneousData.setText(miscellaneousDataForm.getText());
				miscellaneousData.setAttachments(miscellaneousDataForm.getAttachments());

				this.miscellaneousDataService.save(miscellaneousData);

				result = new ModelAndView("redirect:/curricula/hacker/listData.do?curriculaId=" + miscellaneousData.getCurricula().getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(miscellaneousDataForm, "miscellaneousData.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final MiscellaneousDataForm miscellaneousDataForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(miscellaneousDataForm, "miscellaneousData.commit.error");
		else
			try {
				final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataForm.getId());
				Assert.notNull(miscellaneousData);

				this.miscellaneousDataService.delete(this.miscellaneousDataService.findOne(miscellaneousDataForm.getId()));

				result = new ModelAndView("redirect:/curricula/hacker/listData.do?curriculaId=" + miscellaneousData.getCurricula().getId());
			} catch (final Throwable oops) {

				result = this.editModelAndView(miscellaneousDataForm, "miscellaneousData.commit.error");
			}
		return result;
	}

	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(final int miscellaneousDataId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		MiscellaneousData miscellaneousData = null;
		Hacker hacker = null;
		Curricula curricula = null;

		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
			Assert.notNull(miscellaneousData);
			curricula = miscellaneousData.getCurricula();
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final MiscellaneousDataForm miscellaneousDataForm = new MiscellaneousDataForm();
			miscellaneousDataForm.setId(miscellaneousData.getId());
			miscellaneousDataForm.setText(miscellaneousData.getText());
			miscellaneousDataForm.setAttachments(miscellaneousData.getAttachments());

			result = this.ShowModelAndView(miscellaneousDataForm);

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message", "curricula.commit.error");
			else if (miscellaneousData == null)
				redirectAttrs.addFlashAttribute("message", "miscellaneousData.error.unexist");
			else if (curricula == null)
				redirectAttrs.addFlashAttribute("message", "curricula.error.unexist");
			else if (curricula.isCopy() == true)
				redirectAttrs.addFlashAttribute("message", "curricula.error.isCopy");
			else if (!curricula.getHacker().equals(hacker))
				redirectAttrs.addFlashAttribute("message", "curricula.error.notFromHacker");
		}

		return result;
	}

	// MODEL
	protected ModelAndView createModelAndView(final MiscellaneousDataForm miscellaneousDataForm) {
		ModelAndView result;
		result = this.createModelAndView(miscellaneousDataForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final MiscellaneousDataForm miscellaneousDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("miscellaneousData/create");

		result.addObject("message", message);
		result.addObject("requestURI", "miscellaneousData/hacker/create.do");
		result.addObject("miscellaneousDataForm", miscellaneousDataForm);
		result.addObject("isRead", false);
		result.addObject("id", miscellaneousDataForm.getId());
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView editModelAndView(final MiscellaneousDataForm miscellaneousDataForm) {
		ModelAndView result;
		result = this.editModelAndView(miscellaneousDataForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final MiscellaneousDataForm miscellaneousDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("miscellaneousData/edit");

		result.addObject("message", message);
		result.addObject("requestURI", "miscellaneousData/hacker/edit.do?miscellaneousDataId=" + miscellaneousDataForm.getId());
		result.addObject("id", miscellaneousDataForm.getId());
		result.addObject("miscellaneousDataForm", miscellaneousDataForm);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView ShowModelAndView(final MiscellaneousDataForm miscellaneousDataForm) {
		ModelAndView result;
		result = this.ShowModelAndView(miscellaneousDataForm, null);
		return result;
	}

	protected ModelAndView ShowModelAndView(final MiscellaneousDataForm miscellaneousDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("miscellaneousData/show");
		result.addObject("message", message);
		result.addObject("requestURI", "miscellaneousData/hacker/show.do?miscellaneousDataId=" + miscellaneousDataForm.getId());
		result.addObject("miscellaneousDataForm", miscellaneousDataForm);
		result.addObject("id", miscellaneousDataForm.getId());
		result.addObject("isRead", true);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}
}
