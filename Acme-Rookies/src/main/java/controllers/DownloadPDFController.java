package controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.AdministratorService;
import services.CompanyService;
import services.ConfigurationService;
import services.CurriculaService;
import services.HackerService;
import domain.Administrator;
import domain.Company;
import domain.Curricula;
import domain.Hacker;

@Controller
@RequestMapping("/data")
public class DownloadPDFController {

	@Autowired
	CompanyService companyService;

	@Autowired
	HackerService hackerService;

	@Autowired
	AdministratorService administratorService;

	@Autowired
	ConfigurationService configurationService;

	@Autowired
	ActorService actorService;

	@Autowired
	CurriculaService curriculaService;

	@Autowired
	UserAccountService userAccountService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView modelAndView;

		modelAndView = new ModelAndView("misc/data");
		modelAndView.addObject("requestURI", "/data/list.do");
		modelAndView.addObject("banner", this.configurationService.findAll()
				.iterator().next().getBanner());
		modelAndView.addObject("systemName", this.configurationService
				.findAll().iterator().next().getSystemName());

		return modelAndView;
	}

	@RequestMapping(value = "/downloadPersonalData")
	public void downloadPersonalData(HttpServletResponse response)
			throws IOException {

		String csvFileName = "personalData.csv";
		response.setContentType("text/csv");

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount.getAuthorities().toString().contains("COMPANY")) {

			Company c1 = companyService.findCompanyByUseraccountId(userAccount
					.getId());

			List<Company> listCompany = Arrays.asList(c1);

			String[] header = { "name", "surnames", "VATNumber", "photo",
					"email", "phone", "address", "comercialName" };

			csvWriter.writeHeader(header);

			for (Company company : listCompany) {
				csvWriter.write(company, header);
			}

		} else if (userAccount.getAuthorities().toString().contains("HACKER")) {

			Hacker h1 = hackerService.findHackerByUseraccount(userAccount);

			List<Hacker> listHacker = Arrays.asList(h1);

			String[] header = { "name", "surnames", "VATNumber", "photo",
					"email", "phone", "address" };

			csvWriter.writeHeader(header);

			for (Hacker hacker : listHacker) {
				csvWriter.write(hacker, header);
			}

		} else if (userAccount.getAuthorities().toString().contains("ADMIN")) {

			Administrator a1 = administratorService
					.findByUseraccount(userAccount);

			List<Administrator> listAdministrator = Arrays.asList(a1);

			String[] header = { "name", "surnames", "VATNumber", "photo",
					"email", "phone", "address" };

			csvWriter.writeHeader(header);

			for (Administrator administrator : listAdministrator) {
				csvWriter.write(administrator, header);
			}
		}

		csvWriter.close();
	}

	@RequestMapping(value = "/deletePersonalData", method = RequestMethod.GET)
	public ModelAndView deletePersonalData(
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		UserAccount userAccount = LoginService.getPrincipal();

		try {
			Assert.notNull(userAccount);
			if (userAccount.getAuthorities().toString().contains("COMPANY")) {
				Company c = companyService
						.findCompanyByUseraccountId(userAccount.getId());
				Assert.notNull(c);
				c.setAddress(null);
				c.setEmail("null@null.null");
				c.setName("null");
				c.setSurnames("null");
				c.setVATNumber("null");
				c.setPhone(null);
				c.setPhoto(null);
				c.setComercialName("null");
				userAccount.setEnabled(false);
				companyService.save(c);
				userAccountService.save(userAccount);

			} else if (userAccount.getAuthorities().toString()
					.contains("HACKER")) {
				Hacker h = hackerService.findHackerByUseraccount(userAccount);
				Assert.notNull(h);
				h.setAddress(null);
				h.setEmail("null@null.null");
				h.setName("null");
				h.setSurnames("null");
				h.setVATNumber("null");
				h.setPhone(null);
				h.setPhoto(null);
				userAccount.setEnabled(false);
				hackerService.save(h);
				userAccountService.save(userAccount);
				Collection<Curricula> curriculas = curriculaService
						.findNoCopies(h.getId());
				if (!curriculas.isEmpty()) {
					for (Curricula c : curriculas) {
						curriculaService.delete(c);
					}
				}
			} else {
				Assert.isTrue(false);
			}

			result = new ModelAndView("redirect:/j_spring_security_logout");

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/data/list.do");

			if (LoginService.getPrincipal().getAuthorities().toString()
					.contains("ADMIN"))
				redirectAttrs.addFlashAttribute("message",
						"misc.error.adminNotDelete");
			else
				redirectAttrs.addFlashAttribute("message", "commit.error");
		}
		return result;
	}
}