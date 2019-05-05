
package controllers.Auditor;

import java.util.ArrayList;
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
import services.AuditService;
import services.AuditorService;
import services.ConfigurationService;
import services.PositionService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Position;
import forms.AuditForm;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private AuditService			auditService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public AuditAuditorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			final int auditorId = this.auditorService.findByUseraccount(LoginService.getPrincipal()).getId();
			Assert.notNull(this.auditorService.findOne(auditorId));
			final Collection<Audit> audits = this.auditService.findByAuditorId(auditorId);

			result = new ModelAndView("audit/list");
			result.addObject("audits", audits);
			result.addObject("requestURI", "audit/auditor/list.do");
			result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/listPositions", method = RequestMethod.GET)
	public ModelAndView listPositions(final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final Auditor auditor = this.auditorService.findByUseraccount(LoginService.getPrincipal());
		final Collection<Audit> audits = this.auditService.findByAuditorId(LoginService.getPrincipal().getId());
		Collection<Position> freepositions = null;
		try {
			final int auditorId = this.auditorService.findByUseraccount(LoginService.getPrincipal()).getId();
			Assert.notNull(this.auditorService.findOne(auditorId));
			final Collection<Position> positions = auditor.getPositions();
			freepositions = this.positionService.findAllNoAuditor();

			result = new ModelAndView("audit/listPositions");
			result.addObject("positions", positions);
			result.addObject("freepositions", freepositions);
			result.addObject("requestURI", "audit/auditor/listPositions.do");
			result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/asign", method = RequestMethod.GET)
	public ModelAndView asign(final Position position, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final Auditor auditor = this.auditorService.findByUseraccount(LoginService.getPrincipal());
		final Collection<Position> auditorpositions = new ArrayList<>(auditor.getPositions());
		final AuditForm auditForm = new AuditForm();
		Position p;

		p = this.positionService.findOne(position.getId());
		Assert.notNull(p);
		auditorpositions.add(p);
		this.auditorService.save(auditor);
		result = this.createModelAndView(auditForm);

		return result;
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final AuditForm auditForm = new AuditForm();
		Collection<Position> freepositions = new ArrayList<>();
		Auditor auditor = null;
		try {
			auditor = this.auditorService.findByUseraccount(LoginService.getPrincipal());
			Assert.notNull(auditor);
			freepositions = this.positionService.findAllNoAuditor();
			auditForm.setId(0);

			result = this.createModelAndView(auditForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/audit/auditor/list.do");
			if (auditor == null)
				redirectAttrs.addFlashAttribute("message", "commit.error");
			else if (freepositions.isEmpty())
				redirectAttrs.addFlashAttribute("message", "audit.error.NoPositions");

		}
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AuditForm auditForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(auditForm, "commit.error");
		else
			try {
				Audit audit = this.auditService.create();
				audit.setMoment(auditForm.getMoment());
				audit.setText(auditForm.getText());
				audit.setScore(auditForm.getScore());
				audit.setDraftMode(auditForm.isDraftMode());

				audit = this.auditService.save(audit);

				result = new ModelAndView("redirect:/audit/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(auditForm, "commit.error");
			}
		return result;
	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int auditId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final AuditForm auditForm = new AuditForm();
		Auditor auditor = null;
		Audit audit = null;
		try {
			auditor = this.auditorService.findByUseraccount(LoginService.getPrincipal());
			Assert.notNull(auditor);
			audit = this.auditService.findOne(auditId);
			Assert.notNull(audit);
			Assert.isTrue(audit.getAuditor().equals(auditor));

			auditForm.setId(audit.getId());
			auditForm.setPosition(audit.getPosition());
			auditForm.setText(audit.getText());
			auditForm.setMoment(audit.getMoment());
			auditForm.setScore(audit.getScore());
			auditForm.setDraftMode(audit.isDraftMode());

			result = this.editModelAndView(auditForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/audit/auditor/list.do");
			if (auditor == null)
				redirectAttrs.addFlashAttribute("message", "commit.error");
			else if (audit == null)
				redirectAttrs.addFlashAttribute("message", "audit.error.unexists");
			else if (!audit.getAuditor().equals(auditor))
				redirectAttrs.addFlashAttribute("message", "audit.error.notYours");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final AuditForm auditForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(auditForm, "commit.error");
		else
			try {
				Audit audit = this.auditService.findOne(auditForm.getId());
				audit.setMoment(auditForm.getMoment());
				audit.setText(auditForm.getText());
				audit.setScore(auditForm.getScore());
				audit.setDraftMode(auditForm.isDraftMode());

				audit = this.auditService.save(audit);

				result = new ModelAndView("redirect:/audit/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(auditForm, "commit.error");
			}
		return result;
	}

	// MODEL
	protected ModelAndView createModelAndView(final AuditForm auditForm) {
		ModelAndView result;
		result = this.createModelAndView(auditForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final AuditForm auditForm, final String message) {
		final ModelAndView result;

		final Collection<Position> positions = this.positionService.findFinalMode();

		result = new ModelAndView("audit/create");

		result.addObject("message", message);
		result.addObject("requestURI", "audit/auditor/create.do");
		result.addObject("auditForm", auditForm);
		result.addObject("isRead", false);
		result.addObject("positions", positions);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView editModelAndView(final AuditForm auditForm) {
		ModelAndView result;
		result = this.editModelAndView(auditForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final AuditForm auditForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("audit/edit");

		result.addObject("message", message);
		result.addObject("requestURI", "audit/auditor/edit.do?auditId=" + auditForm.getId());
		result.addObject("auditForm", auditForm);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

}
