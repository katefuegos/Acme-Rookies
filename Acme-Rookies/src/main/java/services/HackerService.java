package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HackerRepository;
import security.Authority;
import security.UserAccount;
import domain.Hacker;

@Service
@Transactional
public class HackerService {

	// Repository-----------------------------------------------

	@Autowired
	private HackerRepository hackerRepository;

	// Services-------------------------------------------------

	// Constructor----------------------------------------------

	public HackerService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Hacker create() {
		final Hacker hacker = new Hacker();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority("COMPANY");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		hacker.setUserAccount(userAccount);

		return hacker;
	}

	public List<Hacker> findAll() {
		return this.hackerRepository.findAll();
	}

	public Hacker findOne(final Integer hackerId) {
		return this.hackerRepository.findOne(hackerId);
	}

	public Hacker save(final Hacker hacker) {
		Assert.notNull(hacker);

		final Hacker saved = this.hackerRepository.save(hacker);
		return saved;
	}

	public void delete(final Hacker hacker) {
		this.hackerRepository.delete(hacker);
	}

	// Other Methods--------------------------------------------

	public Hacker findHackerByUseraccount(final UserAccount userAccount) {
		return this.hackerRepository.findHackerByUserAccount(userAccount
				.getId());

	}

	public Hacker findHackerByUsername(final String username) {
		return this.hackerRepository.findHackerByUsername(username);
	}

}
