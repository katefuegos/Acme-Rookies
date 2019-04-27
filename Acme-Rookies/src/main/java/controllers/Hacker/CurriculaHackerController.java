package controllers.Hacker;

import java.util.Collection;

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
import services.MiscellaneousDataService;
import services.PositionDataService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PositionData;
import forms.CurriculaForm;

@Controller
@RequestMapping("/curricula/hacker")
public class CurriculaHackerController extends AbstractController {

	// Services-----------------------------------------------------------
	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private HackerService hackerService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private PositionDataService positionDataService;

	@Autowired
	private EducationDataService educationDataService;

	@Autowired
	private MiscellaneousDataService miscellaneousDataService;

	// Constructor---------------------------------------------------------

	public CurriculaHackerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Integer hackerId = null;
		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hackerId = this.hackerService.findHackerByUseraccount(userAccount)
					.getId();
			Assert.notNull(this.hackerService.findOne(hackerId));
			final Collection<Curricula> curriculas = this.curriculaService
					.findNoCopies(hackerId);

			result = new ModelAndView("curricula/list");
			result.addObject("curriculas", curriculas);
			result.addObject("requestURI", "curricula/list.do?hackerId="
					+ hackerId);
			result.addObject("banner", this.configurationService.findAll()
					.iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll()
					.iterator().next().getSystemName());

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/curricula/hacker/list.do");
		}

		return result;
	}

	@RequestMapping(value = "/listData", method = RequestMethod.GET)
	public ModelAndView listData(final int curriculaId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Hacker hacker = null;
		Curricula curricula = null;

		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			curricula = curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final Collection<PositionData> positiondatas = this.positionDataService
					.findByCurriculaId(curriculaId);
			final Collection<EducationData> educationdatas = this.educationDataService
					.findByCurriculaId(curriculaId);
			final Collection<MiscellaneousData> miscellaneousdatas = this.miscellaneousDataService
					.findByCurriculaId(curriculaId);

			result = new ModelAndView("curricula/listData");
			result.addObject("positiondatas", positiondatas);
			result.addObject("educationdatas", educationdatas);
			result.addObject("miscellaneousdatas", miscellaneousdatas);
			result.addObject("requestURI",
					"curricula/hacker/listData.do?curriculaId=" + curriculaId);
			result.addObject("banner", this.configurationService.findAll()
					.iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll()
					.iterator().next().getSystemName());

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

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final RedirectAttributes redirectAttrs) {
		ModelAndView result = null;
		Hacker hacker = null;

		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			
			final CurriculaForm curriculaForm = new CurriculaForm();
			curriculaForm.setId(0);
			curriculaForm.setHacker(hacker);

			result = this.createModelAndView(curriculaForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/curricula/hacker/list.do");
			if (hacker == null)
				redirectAttrs.addFlashAttribute("message",
						"curricula.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CurriculaForm curriculaForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(curriculaForm,
					"curricula.commit.error");
		else
			try {
				final Curricula curricula = this.curriculaService.create();
				curricula.setFullName(curriculaForm.getFullName());
				curricula.setStatement(curriculaForm.getStatement());
				curricula.setPhoneNumber(curriculaForm.getPhoneNumber());
				curricula.setGithubProfile(curriculaForm.getGithubProfile());
				curricula
						.setLinkedinprofile(curriculaForm.getLinkedInProfile());
				curricula.setHacker(curriculaForm.getHacker());

				this.curriculaService.save(curricula);

				result = new ModelAndView("redirect:/curricula/hacker/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(curriculaForm,
						"curricula.commit.error");
			}

		return result;
	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int curriculaId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Curricula curricula = null;
		Hacker hacker = null;

		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			hacker = this.hackerService.findHackerByUseraccount(userAccount);
			Assert.notNull(hacker);
			curricula = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			Assert.isTrue(curricula.isCopy() == false);
			Assert.isTrue(curricula.getHacker().equals(hacker));

			final CurriculaForm curriculaForm = new CurriculaForm();
			curriculaForm.setId(curricula.getId());
			curriculaForm.setFullName(curricula.getFullName());
			curriculaForm.setStatement(curricula.getStatement());
			curriculaForm.setPhoneNumber(curricula.getPhoneNumber());
			curriculaForm.setGithubProfile(curricula.getGithubProfile());
			curriculaForm.setLinkedInProfile(curricula.getLinkedinprofile());
			curriculaForm.setHacker(curricula.getHacker());

			result = this.editModelAndView(curriculaForm);

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

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final CurriculaForm curriculaForm,
			final BindingResult binding) {
		ModelAndView result;
		Hacker hacker = null;
		Curricula curricula = null;

		if (binding.hasErrors())
			result = this.editModelAndView(curriculaForm,
					"curricula.commit.error");
		else
			try {
				final UserAccount userAccount = LoginService.getPrincipal();
				hacker = this.hackerService
						.findHackerByUseraccount(userAccount);
				Assert.notNull(hacker);
				curricula = this.curriculaService
						.findOne(curriculaForm.getId());
				Assert.notNull(curricula);
				Assert.isTrue(curricula.isCopy() == false);
				Assert.isTrue(curricula.getHacker().equals(hacker));

				curricula.setFullName(curriculaForm.getFullName());
				curricula.setStatement(curriculaForm.getStatement());
				curricula.setPhoneNumber(curriculaForm.getPhoneNumber());
				curricula.setGithubProfile(curriculaForm.getGithubProfile());
				curricula
						.setLinkedinprofile(curriculaForm.getLinkedInProfile());
				curricula.setHacker(curriculaForm.getHacker());

				this.curriculaService.save(curricula);

				result = new ModelAndView("redirect:/curricula/hacker/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(curriculaForm,
						"curricula.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final CurriculaForm curriculaForm,
			final BindingResult binding) {
		ModelAndView result;
		Hacker hacker = null;
		Curricula curricula = null;

		if (binding.hasErrors())
			result = this.editModelAndView(curriculaForm,
					"curricula.commit.error");
		else
			try {
				final UserAccount userAccount = LoginService.getPrincipal();
				hacker = this.hackerService
						.findHackerByUseraccount(userAccount);
				Assert.notNull(hacker);
				curricula = this.curriculaService
						.findOne(curriculaForm.getId());
				Assert.notNull(curricula);
				Assert.isTrue(curricula.isCopy() == false);
				Assert.isTrue(curricula.getHacker().equals(hacker));

				this.curriculaService.delete(curricula);

				result = new ModelAndView("redirect:/curricula/hacker/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(curriculaForm,
						"curricula.commit.error");
			}

		return result;
	}

	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(final int curriculaId,
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

			final CurriculaForm curriculaForm = new CurriculaForm();
			curriculaForm.setId(curricula.getId());
			curriculaForm.setFullName(curricula.getFullName());
			curriculaForm.setStatement(curricula.getStatement());
			curriculaForm.setPhoneNumber(curricula.getPhoneNumber());
			curriculaForm.setGithubProfile(curricula.getGithubProfile());
			curriculaForm.setLinkedInProfile(curricula.getLinkedinprofile());
			curriculaForm.setHacker(curricula.getHacker());

			result = this.ShowModelAndView(curriculaForm);

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

	// MODEL
	protected ModelAndView createModelAndView(final CurriculaForm curriculaForm) {
		ModelAndView result;
		result = this.createModelAndView(curriculaForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(
			final CurriculaForm curriculaForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("curricula/create");

		result.addObject("message", message);
		result.addObject("requestURI", "curricula/hacker/create.do");
		result.addObject("curriculaForm", curriculaForm);
		result.addObject("isRead", false);
		result.addObject("id", curriculaForm.getId());
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView editModelAndView(final CurriculaForm curriculaForm) {
		ModelAndView result;
		result = this.editModelAndView(curriculaForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final CurriculaForm curriculaForm,
			final String message) {
		final ModelAndView result;

		result = new ModelAndView("curricula/edit");
		result.addObject("message", message);
		result.addObject("requestURI", "curricula/hacker/edit.do?curriculaId="
				+ curriculaForm.getId());
		result.addObject("curriculaForm", curriculaForm);
		result.addObject("id", curriculaForm.getId());
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView ShowModelAndView(final CurriculaForm curriculaForm) {
		ModelAndView result;
		result = this.ShowModelAndView(curriculaForm, null);
		return result;
	}

	protected ModelAndView ShowModelAndView(final CurriculaForm curriculaForm,
			final String message) {
		final ModelAndView result;

		result = new ModelAndView("curricula/show");
		result.addObject("message", message);
		result.addObject("requestURI", "curricula/hacker/show.do?curriculaId="
				+ curriculaForm.getId());
		result.addObject("curriculaForm", curriculaForm);
		result.addObject("id", curriculaForm.getId());
		result.addObject("isRead", true);
		result.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll()
				.iterator().next().getSystemName());
		return result;
	}
}
