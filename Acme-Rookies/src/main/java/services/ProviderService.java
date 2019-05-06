
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import security.Authority;
import security.UserAccount;
import domain.Provider;

@Service
@Transactional
public class ProviderService {

	//Repository-----------------------------------------------

	@Autowired
	private ProviderRepository		providerRepository;

	//Services-------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	//Constructor----------------------------------------------

	public ProviderService() {

		super();
	}

	//Simple CRUD----------------------------------------------

	public Provider create() {
		final Provider provider = new Provider();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		provider.setShowMessage(!this.configurationService.findDefault().isProcessExecuted());

		final Authority a = new Authority();
		a.setAuthority("PROVIDER");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		provider.setUserAccount(userAccount);
		return provider;
	}
	public List<Provider> findAll() {
		return this.providerRepository.findAll();
	}

	public Provider findOne(final Integer providerId) {
		return this.providerRepository.findOne(providerId);
	}

	public Provider save(final Provider provider) {
		Assert.notNull(provider);

		final Provider saved = this.providerRepository.save(provider);
		return saved;
	}
	public void delete(final Provider entity) {
		this.providerRepository.delete(entity);
	}

	//Other Methods--------------------------------------------

	public Provider findByUseraccount(final UserAccount userAccount) {
		return this.providerRepository.findProviderByUserAccount(userAccount.getId());

	}

	public Provider findAdminByUsername(final String username) {
		return this.providerRepository.findProviderByUsername(username);
	}

}
