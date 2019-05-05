
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.UserAccount;
import domain.Auditor;
import domain.Position;

@Service
@Transactional
public class AuditorService {

	//Repository-----------------------------------------------

	@Autowired
	private AuditorRepository	auditorRepository;


	//Services-------------------------------------------------

	//Constructor----------------------------------------------

	public AuditorService() {

		super();
	}

	//Simple CRUD----------------------------------------------

	public Auditor create() {
		final Auditor auditor = new Auditor();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Collection<Position> positions = new ArrayList<Position>();

		final Authority a = new Authority();
		a.setAuthority("AUDITOR");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		auditor.setUserAccount(userAccount);
		auditor.setPositions(positions);

		return auditor;
	}
	public List<Auditor> findAll() {
		return this.auditorRepository.findAll();
	}

	public Auditor findOne(final Integer auditorId) {
		return this.auditorRepository.findOne(auditorId);
	}

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);

		final Auditor saved = this.auditorRepository.save(auditor);
		return saved;
	}
	public void delete(final Auditor entity) {
		this.auditorRepository.delete(entity);
	}

	//Other Methods--------------------------------------------

	public Auditor findByUseraccount(final UserAccount userAccount) {
		return this.auditorRepository.findAuditorByUserAccount(userAccount.getId());

	}

	public Auditor findAdminByUsername(final String username) {
		return this.auditorRepository.findAuditorByUsername(username);
	}

}
