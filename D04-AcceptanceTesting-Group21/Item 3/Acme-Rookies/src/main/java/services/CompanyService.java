
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;

@Service
@Transactional
public class CompanyService {

	// Repository-----------------------------------------------

	@Autowired
	private CompanyRepository		companyRepository;

	// Services-------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditService			auditService;


	// Constructor----------------------------------------------

	public CompanyService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Company create() {
		final Company company = new Company();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		company.setShowMessage(!this.configurationService.findDefault().isProcesoEjecutado());

		final Authority a = new Authority();
		a.setAuthority("COMPANY");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		company.setUserAccount(userAccount);

		return company;
	}

	public Collection<Company> findAll() {
		return this.companyRepository.findAll();
	}

	public Company findOne(final Integer companyId) {
		return this.companyRepository.findOne(companyId);
	}

	public Company save(final Company company) {
		Assert.notNull(company);

		final Company saved = this.companyRepository.save(company);
		return saved;
	}

	public void delete(final Company company) {
		this.companyRepository.delete(company);
	}

	// Other Methods--------------------------------------------

	public Company findCompanyByUseraccount(final UserAccount userAccount) {
		return this.companyRepository.findCompanyByUserAccount(userAccount.getId());

	}

	public Company findCompanyByUsername(final String username) {
		return this.companyRepository.findCompanyByUsername(username);
	}

	public Company findCompanyByUseraccountId(final int id) {
		return this.companyRepository.findCompanyByUserAccount(id);
	}

	public void calculateAllAuditScore() {

		Assert.isTrue(this.administratorService.findByUseraccount(LoginService.getPrincipal()) != null);

		final Collection<domain.Company> companies = this.companyRepository.findAll();
		final Collection<Company> companies2 = new LinkedList<>();
		for (final Company company : companies) {

			company.setAuditScore(this.auditService.calculateScoreCompany(company.getId()));
			companies2.add(company);

		}

		this.companyRepository.save(companies2);

	}

}
