package controllers.Hacker;

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
import security.UserAccount;
import services.ConfigurationService;
import services.CurriculaService;
import services.HackerService;
import services.PositionDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Hacker;
import domain.PositionData;
import forms.PositionDataForm;

@Controller
@RequestMapping("/positionData/hacker")
public class PositionDataController extends AbstractController {

	// Services-----------------------------------------------------------
	@Autowired
	private PositionDataService positionDataService;

	@Autowired
	private HackerService hackerService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private CurriculaService curriculaService;

	// Constructor---------------------------------------------------------

	public PositionDataController() {
		super();
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int curriculaId,
			final RedirectAttributes redirectAttrs) {
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

			final PositionDataForm positionDataForm = new PositionDataForm();
			positionDataForm.setId(0);
			positionDataForm.setCurricula(curricula);

			result = this.createModelAndView(positionDataForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message",
						"curricula.commit.error");
			else if (curricula == null)
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.unexist");
			else if (curricula.isCopy() == true)
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.isCopy");
			else if (!curricula.getHacker().equals(hacker))
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.notFromHacker");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PositionDataForm positionDataForm,
			final BindingResult binding) {
		ModelAndView result;
		Hacker hacker = null;

		if (binding.hasErrors())
			result = this.createModelAndView(positionDataForm,
					"positionData.commit.error");
		else
			try {
				final UserAccount userAccount = LoginService.getPrincipal();
				hacker = this.hackerService
						.findHackerByUseraccount(userAccount);
				Assert.notNull(hacker);

				final PositionData positionData = this.positionDataService
						.create();
				positionData.setTitle(positionDataForm.getTitle());
				positionData.setDescription(positionDataForm.getDescription());
				positionData.setStartDate(positionDataForm.getStartDate());
				positionData.setEndDate(positionDataForm.getEndDate());
				positionData.setCurricula(positionDataForm.getCurricula());

				this.positionDataService.save(positionData);

				result = new ModelAndView(
						"redirect:/curricula/hacker/listData.do?curriculaId="
								+ positionData.getCurricula().getId());
			} catch (final Throwable oops) {
				result = this.createModelAndView(positionDataForm,
						"positionData.commit.error");
			}
		return result;
	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int positionDataId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		PositionData positionData = null;
		Hacker hacker = null;
		Curricula curricula = null;

		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			positionData = this.positionDataService.findOne(positionDataId);
			Assert.notNull(positionData);
			curricula = positionData.getCurricula();
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final PositionDataForm positionDataForm = new PositionDataForm();
			positionDataForm.setId(positionData.getId());
			positionDataForm.setTitle(positionData.getTitle());
			positionDataForm.setDescription(positionData.getDescription());
			positionDataForm.setStartDate(positionData.getStartDate());
			positionDataForm.setEndDate(positionData.getEndDate());
			positionDataForm.setCurricula(positionData.getCurricula());

			result = this.editModelAndView(positionDataForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message",
						"curricula.commit.error");
			else if (positionData == null)
				redirectAttrs.addFlashAttribute("message",
						"positionData.error.unexist");
			else if (curricula == null)
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.unexist");
			else if (curricula.isCopy() == true)
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.isCopy");
			else if (!curricula.getHacker().equals(hacker))
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.notFromHacker");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final PositionDataForm positionDataForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(positionDataForm,
					"positionData.commit.error");
		else
			try {
				final PositionData positionData = this.positionDataService
						.findOne(positionDataForm.getId());
				Assert.notNull(positionData);

				positionData.setTitle(positionDataForm.getTitle());
				positionData.setDescription(positionDataForm.getDescription());
				positionData.setEndDate(positionDataForm.getEndDate());
				positionData.setStartDate(positionDataForm.getStartDate());

				this.positionDataService.save(positionData);
				result = new ModelAndView(
						"redirect:/curricula/hacker/listData.do?curriculaId="
								+ positionData.getCurricula().getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(positionDataForm,
						"positionData.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final PositionDataForm positionDataForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(positionDataForm,
					"positionData.commit.error");
		else
			try {

				final PositionData positionData = this.positionDataService
						.findOne(positionDataForm.getId());
				Assert.notNull(positionData);

				this.positionDataService.delete(positionData);

				result = new ModelAndView(
						"redirect:/curricula/hacker/listData.do?curriculaId="
								+ positionData.getCurricula().getId());

			} catch (final Throwable oops) {
				result = this.editModelAndView(positionDataForm,
						"positionData.commit.error");
			}
		return result;
	}

	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(final int positionDataId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		PositionData positionData = null;
		Hacker hacker = null;
		Curricula curricula = null;

		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			positionData = this.positionDataService.findOne(positionDataId);
			Assert.notNull(positionData);
			curricula = positionData.getCurricula();
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final PositionDataForm positionDataForm = new PositionDataForm();
			positionDataForm.setId(positionData.getId());
			positionDataForm.setTitle(positionData.getTitle());
			positionDataForm.setDescription(positionData.getDescription());
			positionDataForm.setStartDate(positionData.getStartDate());
			positionDataForm.setEndDate(positionData.getEndDate());

			result = this.ShowModelAndView(positionDataForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message",
						"curricula.commit.error");
			else if (positionData == null)
				redirectAttrs.addFlashAttribute("message",
						"positionData.error.unexist");
			else if (curricula == null)
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.unexist");
			else if (curricula.isCopy() == true)
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.isCopy");
			else if (!curricula.getHacker().equals(hacker))
				redirectAttrs.addFlashAttribute("message",
						"curricula.error.notFromHacker");
		}

		return result;
	}

	// MODEL
	protected ModelAndView createModelAndView(
			final PositionDataForm positionDataForm) {
		ModelAndView result;
		result = this.createModelAndView(positionDataForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(
			final PositionDataForm positionDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("positionData/create");

		result.addObject("message", message);
		result.addObject("requestURI", "positionData/hacker/create.do");
		result.addObject("positionDataForm", positionDataForm);
		result.addObject("isRead", false);
		result.addObject("id", positionDataForm.getId());
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView editModelAndView(
			final PositionDataForm positionDataForm) {
		ModelAndView result;
		result = this.editModelAndView(positionDataForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(
			final PositionDataForm positionDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("positionData/edit");

		result.addObject("message", message);
		result.addObject("requestURI",
				"positionData/hacker/edit.do?positionDataId="
						+ positionDataForm.getId());
		result.addObject("positionDataForm", positionDataForm);
		result.addObject("isRead", false);
		result.addObject("id", positionDataForm.getId());
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView ShowModelAndView(
			final PositionDataForm positionDataForm) {
		ModelAndView result;
		result = this.ShowModelAndView(positionDataForm, null);
		return result;
	}

	protected ModelAndView ShowModelAndView(
			final PositionDataForm positionDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("positionData/show");
		result.addObject("message", message);
		result.addObject("requestURI",
				"positionData/hacker/show.do?positionDataId="
						+ positionDataForm.getId());
		result.addObject("positionDataForm", positionDataForm);
		result.addObject("isRead", true);
		result.addObject("id", positionDataForm.getId());
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}
}
