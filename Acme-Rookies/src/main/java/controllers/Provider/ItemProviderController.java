
package controllers.Provider;

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
import services.ConfigurationService;
import services.ItemService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Item;
import domain.Provider;
import forms.ItemForm;

@Controller
@RequestMapping("/item/provider")
public class ItemProviderController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ItemService				itemService;

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public ItemProviderController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			final int providerId = this.providerService.findByUseraccount(LoginService.getPrincipal()).getId();
			Assert.notNull(this.providerService.findOne(providerId));
			final Collection<Item> items = this.itemService.findByProviderId(providerId);

			result = new ModelAndView("item/list");
			result.addObject("items", items);
			result.addObject("requestURI", "item/provider/list.do");
			result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final ItemForm itemForm = new ItemForm();
		Provider provider = null;
		try {
			provider = this.providerService.findByUseraccount(LoginService.getPrincipal());
			Assert.notNull(provider);
			itemForm.setProvider(provider);
			itemForm.setId(0);

			result = this.createModelAndView(itemForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/item/provider/list.do");
			if (provider == null)
				redirectAttrs.addFlashAttribute("message", "commit.error");

		}
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ItemForm itemForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(itemForm, "commit.error");
		else
			try {
				final Item item = this.itemService.create();
				item.setName(itemForm.getName());
				item.setDescription(itemForm.getDescription());
				item.setLink(itemForm.getLink());
				item.setPicture(itemForm.getPicture());
				item.setProvider(itemForm.getProvider());

				this.itemService.save(item);

				result = new ModelAndView("redirect:/item/provider/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(itemForm, "commit.error");
			}
		return result;
	}

	// EDIT
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int itemId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final ItemForm itemForm = new ItemForm();
		Provider provider = null;
		Item item = null;
		try {
			provider = this.providerService.findByUseraccount(LoginService.getPrincipal());
			Assert.notNull(provider);
			item = this.itemService.findOne(itemId);
			Assert.notNull(item);
			Assert.isTrue(item.getProvider().equals(provider));

			itemForm.setId(item.getId());

			itemForm.setProvider(item.getProvider());
			itemForm.setName(item.getName());
			itemForm.setDescription(item.getDescription());
			itemForm.setLink(item.getLink());
			itemForm.setPicture(item.getPicture());

			result = this.editModelAndView(itemForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/item/provider/list.do");
			if (provider == null)
				redirectAttrs.addFlashAttribute("message", "commit.error");
			else if (item == null)
				redirectAttrs.addFlashAttribute("message", "item.error.unexists");
			else if (!item.getProvider().equals(provider))
				redirectAttrs.addFlashAttribute("message", "item.error.notYours");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final ItemForm itemForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(itemForm, "commit.error");
		else
			try {
				final Item item = this.itemService.findOne(itemForm.getId());
				item.setName(itemForm.getName());
				item.setDescription(itemForm.getDescription());
				item.setLink(itemForm.getLink());
				item.setPicture(itemForm.getPicture());
				item.setProvider(itemForm.getProvider());

				this.itemService.save(item);

				result = new ModelAndView("redirect:/item/provider/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(itemForm, "commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final ItemForm itemForm, final BindingResult binding) {
		ModelAndView result;
		final Provider b = this.providerService.findByUseraccount(LoginService.getPrincipal());
		if (binding.hasErrors())
			result = this.editModelAndView(itemForm, "item.commit.error");
		else
			try {
				Assert.notNull(itemForm);
				final Item item = this.itemService.findOne(itemForm.getId());
				Assert.isTrue(item.getProvider().equals(b));

				this.itemService.delete(this.itemService.findOne(itemForm.getId()));

				result = new ModelAndView("redirect:/item/provider/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(itemForm, "item.commit.error");
			}
		return result;
	}

	// SHOW
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(final int itemId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Item item = null;
		final Provider b = this.providerService.findByUseraccount(LoginService.getPrincipal());

		try {
			item = this.itemService.findOne(itemId);
			Assert.notNull(item);
			final ItemForm itemForm = new ItemForm();
			itemForm.setId(item.getId());

			itemForm.setProvider(item.getProvider());
			itemForm.setName(item.getName());
			itemForm.setDescription(item.getDescription());
			itemForm.setLink(item.getLink());
			itemForm.setPicture(item.getPicture());

			result = this.ShowModelAndView(itemForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/item/provider/list.do");
			if (this.itemService.findOne(itemId) == null)
				redirectAttrs.addFlashAttribute("message", "item.error.unexist	");
			else if (!this.itemService.findOne(itemId).getProvider().equals(b))
				redirectAttrs.addFlashAttribute("message", "item.error.notFromHacker");
		}
		return result;
	}

	// MODEL
	protected ModelAndView createModelAndView(final ItemForm itemForm) {
		ModelAndView result;
		result = this.createModelAndView(itemForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final ItemForm itemForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("item/create");

		result.addObject("message", message);
		result.addObject("requestURI", "item/provider/create.do");
		result.addObject("itemForm", itemForm);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView editModelAndView(final ItemForm itemForm) {
		ModelAndView result;
		result = this.editModelAndView(itemForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final ItemForm itemForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("item/edit");

		result.addObject("message", message);
		result.addObject("requestURI", "item/provider/edit.do?itemId=" + itemForm.getId());
		result.addObject("itemForm", itemForm);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

	protected ModelAndView ShowModelAndView(final ItemForm itemForm) {
		ModelAndView result;
		result = this.ShowModelAndView(itemForm, null);
		return result;
	}

	protected ModelAndView ShowModelAndView(final ItemForm itemForm, final String message) {
		final ModelAndView result;

		final Provider b = this.providerService.findByUseraccount(LoginService.getPrincipal());

		result = new ModelAndView("item/show");
		result.addObject("message", message);
		result.addObject("requestURI", "item/provider/show.do?itemId=" + itemForm.getId());
		result.addObject("itemForm", itemForm);
		result.addObject("id", itemForm.getId());
		result.addObject("isRead", true);
		result.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		result.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return result;
	}

}
