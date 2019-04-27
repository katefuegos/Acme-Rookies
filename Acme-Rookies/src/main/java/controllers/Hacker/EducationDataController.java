
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
import services.EducationDataService;
import services.HackerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import forms.EducationDataForm;

@Controller
@RequestMapping("/educationData/hacker")
public class EducationDataController extends AbstractController {

	// Services-----------------------------------------------------------
	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CurriculaService		curriculaService;


	// Constructor---------------------------------------------------------

	public EducationDataController() {
		super();
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int curriculaId, final RedirectAttributes redirectAttrs) {

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

			final EducationDataForm educationDataForm = new EducationDataForm();
			educationDataForm.setId(0);
			educationDataForm.setCurricula(curricula);

			result = this.createModelAndView(educationDataForm);

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
	public ModelAndView save(@Valid final EducationDataForm educationDataForm, final BindingResult binding) {
		ModelAndView result;
		Hacker hacker = null;

		if (binding.hasErrors())
			result = this.createModelAndView(educationDataForm, "educationData.commit.error");
		else
			try {
				final UserAccount userAccount = LoginService.getPrincipal();
				hacker = this.hackerService.findHackerByUseraccount(userAccount);
				Assert.notNull(hacker);

				final EducationData educationData = this.educationDataService.create();
				educationData.setDegree(educationDataForm.getDegree());
				educationData.setMark(educationDataForm.getMark());
				educationData.setInstitution(educationDataForm.getInstitution());
				educationData.setStartDate(educationDataForm.getStartDate());
				educationData.setEndDate(educationDataForm.getEndDate());
				educationData.setCurricula(educationDataForm.getCurricula());

				this.educationDataService.save(educationData);

				result = new ModelAndView("redirect:/curricula/hacker/listData.do?curriculaId=" + educationData.getCurricula().getId());
			} catch (final Throwable oops) {
				result = this.createModelAndView(educationDataForm, "educationData.commit.error");
			}
		return result;
	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int educationDataId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		EducationData educationData = null;
		Hacker hacker = null;
		Curricula curricula = null;
		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			educationData = this.educationDataService.findOne(educationDataId);
			Assert.notNull(educationData);
			curricula = educationData.getCurricula();
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final EducationDataForm educationDataForm = new EducationDataForm();
			educationDataForm.setId(educationData.getId());
			educationDataForm.setDegree(educationData.getDegree());
			educationDataForm.setInstitution(educationData.getInstitution());
			educationDataForm.setMark(educationData.getMark());
			educationDataForm.setStartDate(educationData.getStartDate());
			educationDataForm.setEndDate(educationData.getEndDate());
			educationDataForm.setCurricula(educationData.getCurricula());

			result = this.editModelAndView(educationDataForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message", "curricula.commit.error");
			else if (educationData == null)
				redirectAttrs.addFlashAttribute("message", "educationData.error.unexist");
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
	public ModelAndView save2(@Valid final EducationDataForm educationDataForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(educationDataForm, "educationData.commit.error");
		else
			try {
				final EducationData educationData = this.educationDataService.findOne(educationDataForm.getId());
				Assert.notNull(educationData);
				educationData.setDegree(educationDataForm.getDegree());
				educationData.setInstitution(educationDataForm.getInstitution());
				educationData.setMark(educationDataForm.getMark());
				educationData.setEndDate(educationDataForm.getEndDate());
				educationData.setStartDate(educationDataForm.getStartDate());

				this.educationDataService.save(educationData);

				result = new ModelAndView("redirect:/curricula/hacker/listData.do?curriculaId=" + educationData.getCurricula().getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(educationDataForm, "educationData.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final EducationDataForm educationDataForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(educationDataForm, "educationData.commit.error");
		else
			try {
				final EducationData educationData = this.educationDataService.findOne(educationDataForm.getId());
				Assert.notNull(educationData);

				this.educationDataService.delete(this.educationDataService.findOne(educationDataForm.getId()));

				result = new ModelAndView("redirect:/curricula/hacker/listData.do?curriculaId=" + educationData.getCurricula().getId());
			} catch (final Throwable oops) {

				result = this.editModelAndView(educationDataForm, "educationData.commit.error");
			}
		return result;
	}

	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(final int educationDataId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		EducationData educationData = null;
		Hacker hacker = null;
		Curricula curricula = null;

		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			educationData = this.educationDataService.findOne(educationDataId);
			Assert.notNull(educationData);
			curricula = educationData.getCurricula();
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final EducationDataForm educationDataForm = new EducationDataForm();
			educationDataForm.setId(educationData.getId());
			educationDataForm.setDegree(educationData.getDegree());
			educationDataForm.setInstitution(educationData.getInstitution());
			educationDataForm.setMark(educationData.getMark());
			educationDataForm.setStartDate(educationData.getStartDate());
			educationDataForm.setEndDate(educationData.getEndDate());

			result = this.ShowModelAndView(educationDataForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message", "curricula.commit.error");
			else if (educationData == null)
				redirectAttrs.addFlashAttribute("message", "educationData.error.unexist");
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
	protected ModelAndView createModelAndView(final EducationDataForm educationDataForm) {
		ModelAndView result;
		result = this.createModelAndView(educationDataForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final EducationDataForm educationDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("educationData/create");

		result.addObject("message1", message);
		result.addObject("requestURI", "educationData/hacker/create.do");
		result.addObject("educationDataForm", educationDataForm);
		result.addObject("isRead", false);
		result.addObject("id", educationDataForm.getId());
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView editModelAndView(final EducationDataForm educationDataForm) {
		ModelAndView result;
		result = this.editModelAndView(educationDataForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final EducationDataForm educationDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("educationData/edit");

		result.addObject("message", message);
		result.addObject("requestURI", "educationData/hacker/edit.do?educationDataId=" + educationDataForm.getId());
		result.addObject("educationDataForm", educationDataForm);
		result.addObject("isRead", false);
		result.addObject("id", educationDataForm.getId());
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView ShowModelAndView(final EducationDataForm educationDataForm) {
		ModelAndView result;
		result = this.ShowModelAndView(educationDataForm, null);
		return result;
	}

	protected ModelAndView ShowModelAndView(final EducationDataForm educationDataForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("educationData/show");
		result.addObject("message", message);
		result.addObject("requestURI", "educationData/hacker/show.do?educationDataId=" + educationDataForm.getId());
		result.addObject("educationDataForm", educationDataForm);
		result.addObject("isRead", true);
		result.addObject("id", educationDataForm.getId());
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}
}
