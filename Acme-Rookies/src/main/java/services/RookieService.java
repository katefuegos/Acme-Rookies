
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RookieRepository;
import security.Authority;
import security.UserAccount;
import domain.Rookie;

@Service
@Transactional
public class RookieService {

	// Repository-----------------------------------------------

	@Autowired
	private RookieRepository		rookieRepository;

	// Services-------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor----------------------------------------------

	public RookieService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Rookie create() {
		final Rookie rookie = new Rookie();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		rookie.setShowMessage(!this.configurationService.findDefault().isProcessExecuted());

		final Authority a = new Authority();
		a.setAuthority("COMPANY");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		rookie.setUserAccount(userAccount);

		return rookie;
	}

	public List<Rookie> findAll() {
		return this.rookieRepository.findAll();
	}

	public Rookie findOne(final Integer rookieId) {
		return this.rookieRepository.findOne(rookieId);
	}

	public Rookie save(final Rookie rookie) {
		Assert.notNull(rookie);

		final Rookie saved = this.rookieRepository.save(rookie);
		return saved;
	}

	public void delete(final Rookie rookie) {
		this.rookieRepository.delete(rookie);
	}

	// Other Methods--------------------------------------------

	public Rookie findRookieByUseraccount(final UserAccount userAccount) {
		return this.rookieRepository.findRookieByUserAccount(userAccount.getId());

	}

	public Rookie findRookieByUsername(final String username) {
		return this.rookieRepository.findRookieByUsername(username);
	}

}
