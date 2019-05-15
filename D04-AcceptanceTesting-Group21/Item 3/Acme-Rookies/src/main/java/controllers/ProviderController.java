package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ConfigurationService;
import services.ItemService;
import services.ProviderService;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructor---------------------------------------------------------

	public ProviderController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			Collection<Provider> providers = providerService.findAll();
			result = new ModelAndView("provider/list");
			result.addObject("providers", providers);
			result.addObject("requestURI", "providers/list.do");
			result.addObject("banner", this.configurationService.findAll()
					.iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll()
					.iterator().next().getSystemName());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}
	
	@RequestMapping(value = "/listByItem", method = RequestMethod.GET)
	public ModelAndView listByItem(final int itemId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			Collection<Provider> providers = new ArrayList<Provider>();
			Item item = itemService.findOne(itemId);
			Assert.notNull(itemId);
			Provider provider = item.getProvider();
			Assert.notNull(provider);
			providers.add(provider);
			result = new ModelAndView("provider/list");
			result.addObject("providers", providers);
			result.addObject("requestURI", "providers/list.do?itemId="+itemId);
			result.addObject("banner", this.configurationService.findAll()
					.iterator().next().getBanner());
			result.addObject("systemName", this.configurationService.findAll()
					.iterator().next().getSystemName());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}
}
