
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CompanyRepository;
import security.Authority;
import security.UserAccount;
import domain.Company;

@Service
@Transactional
public class CompanyService {

	// Repository-----------------------------------------------

	@Autowired
	private CompanyRepository	companyRepository;


	// Services-------------------------------------------------

	// Constructor----------------------------------------------

	public CompanyService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Company create() {
		final Company company = new Company();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

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

}
