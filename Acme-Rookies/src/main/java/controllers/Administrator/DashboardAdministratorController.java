
package controllers.Administrator;

import java.text.DecimalFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services-----------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor-------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Dashboard---------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView modelAndView = new ModelAndView("administrator/dashboard");
		final DecimalFormat df = new DecimalFormat("0.00");

		final String nulo = "n/a";

		// QueryC1
		final Object[] result = this.administratorService.queryC1();

		final Double avgC1 = (Double) result[0];
		final Double minC1 = (Double) result[1];
		final Double maxC1 = (Double) result[2];
		final Double stddevC1 = (Double) result[3];

		if (avgC1 != null)
			modelAndView.addObject("avgC1", df.format(avgC1));
		else
			modelAndView.addObject("avgC1", nulo);

		if (maxC1 != null)
			modelAndView.addObject("maxC1", df.format(maxC1));
		else
			modelAndView.addObject("maxC1", nulo);

		if (minC1 != null)
			modelAndView.addObject("minC1", df.format(minC1));
		else
			modelAndView.addObject("minC1", nulo);

		if (stddevC1 != null)
			modelAndView.addObject("stddevC1", df.format(stddevC1));
		else
			modelAndView.addObject("stddevC1", nulo);

		// QueryC2
		final Object[] resultC2 = this.administratorService.queryC2();

		final Double avgC2 = (Double) resultC2[0];
		final Double minC2 = (Double) resultC2[1];
		final Double maxC2 = (Double) resultC2[2];
		final Double stddevC2 = (Double) resultC2[3];

		if (avgC2 != null)
			modelAndView.addObject("avgC2", df.format(avgC2));
		else
			modelAndView.addObject("avgC2", nulo);

		if (maxC2 != null)
			modelAndView.addObject("maxC2", df.format(maxC2));
		else
			modelAndView.addObject("maxC2", nulo);

		if (minC2 != null)
			modelAndView.addObject("minC2", df.format(minC2));
		else
			modelAndView.addObject("minC2", nulo);

		if (stddevC2 != null)
			modelAndView.addObject("stddevC2", df.format(stddevC2));
		else
			modelAndView.addObject("stddevC2", nulo);

		//QueryC3 - 
		final Collection<forms.QueryAuxForm> queryC3 = this.administratorService.queryC3();
		modelAndView.addObject("queryC3", queryC3);

		//QueryC4 - 
		final Collection<forms.QueryAuxForm> queryC4 = this.administratorService.queryC4();
		modelAndView.addObject("queryC4", queryC4);

		// QueryC5
		final Object[] resultC5 = this.administratorService.queryC5();

		final Double avgC5 = (Double) resultC5[0];
		final Double minC5 = (Double) resultC5[1];
		final Double maxC5 = (Double) resultC5[2];
		final Double stddevC5 = (Double) resultC5[3];

		if (avgC5 != null)
			modelAndView.addObject("avgC5", df.format(avgC5));
		else
			modelAndView.addObject("avgC5", nulo);

		if (maxC5 != null)
			modelAndView.addObject("maxC5", df.format(maxC5));
		else
			modelAndView.addObject("maxC5", nulo);

		if (minC5 != null)
			modelAndView.addObject("minC5", df.format(minC5));
		else
			modelAndView.addObject("minC5", nulo);

		if (stddevC5 != null)
			modelAndView.addObject("stddevC5", df.format(stddevC5));
		else
			modelAndView.addObject("stddevC5", nulo);

		//QueryC6 - 
		final Collection<domain.Position> queryC6 = this.administratorService.queryC6();
		modelAndView.addObject("queryC6", queryC6);

		// QueryB1
		final Object[] resultB1 = this.administratorService.queryB1();

		final Double avgB1 = (Double) resultB1[0];
		final Double minB1 = (Double) resultB1[1];
		final Double maxB1 = (Double) resultB1[2];
		final Double stddevB1 = (Double) resultB1[3];

		if (avgB1 != null)
			modelAndView.addObject("avgB1", df.format(avgB1));
		else
			modelAndView.addObject("avgB1", nulo);

		if (maxB1 != null)
			modelAndView.addObject("maxB1", df.format(maxB1));
		else
			modelAndView.addObject("maxB1", nulo);

		if (minB1 != null)
			modelAndView.addObject("minB1", df.format(minB1));
		else
			modelAndView.addObject("minB1", nulo);

		if (stddevB1 != null)
			modelAndView.addObject("stddevB1", df.format(stddevB1));
		else
			modelAndView.addObject("stddevB1", nulo);

		// QueryB1
		final Object[] resultB2 = this.administratorService.queryB2();

		final Double avgB2 = (Double) resultB2[0];
		final Double minB2 = (Double) resultB2[1];
		final Double maxB2 = (Double) resultB2[2];
		final Double stddevB2 = (Double) resultB2[3];

		if (avgB2 != null)
			modelAndView.addObject("avgB2", df.format(avgB2));
		else
			modelAndView.addObject("avgB2", nulo);

		if (maxB2 != null)
			modelAndView.addObject("maxB2", df.format(maxB2));
		else
			modelAndView.addObject("maxB2", nulo);

		if (minB2 != null)
			modelAndView.addObject("minB2", df.format(minB2));
		else
			modelAndView.addObject("minB2", nulo);

		if (stddevB2 != null)
			modelAndView.addObject("stddevB2", df.format(stddevB2));
		else
			modelAndView.addObject("stddevB2", nulo);

		// QueryB3
		final Object[] resultB3 = this.administratorService.queryB3();

		final Double ratioNotEmpty = (Double) resultB3[0];
		final Double ratioEmpty = (Double) resultB3[1];

		if (ratioNotEmpty != null)
			modelAndView.addObject("ratioNotEmpty", df.format(ratioNotEmpty));
		else
			modelAndView.addObject("ratioNotEmpty", nulo);

		if (ratioEmpty != null)
			modelAndView.addObject("ratioEmpty", df.format(ratioEmpty));
		else
			modelAndView.addObject("ratioEmpty", nulo);

		// --------------------------------
		modelAndView.addObject("banner", this.configurationService.findAll().iterator().next().getBanner());
		modelAndView.addObject("systemName", this.configurationService.findAll().iterator().next().getSystemName());
		return modelAndView;
	}
}
