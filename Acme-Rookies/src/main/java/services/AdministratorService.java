
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Administrator;
import forms.QueryAuxForm;

@Service
@Transactional
public class AdministratorService {

	//Repository-----------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;


	//Services-------------------------------------------------

	//Constructor----------------------------------------------

	public AdministratorService() {

		super();
	}

	//Simple CRUD----------------------------------------------

	public Administrator create() {
		final Administrator administrator = new Administrator();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority("ADMIN");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		administrator.setUserAccount(userAccount);
		return administrator;
	}
	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final Integer administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		final Administrator saved = this.administratorRepository.save(administrator);
		return saved;
	}
	public void delete(final Administrator entity) {
		this.administratorRepository.delete(entity);
	}

	//Other Methods--------------------------------------------

	public Administrator findByUseraccount(final UserAccount userAccount) {
		return this.administratorRepository.findAdministratorByUserAccount(userAccount.getId());

	}

	public Administrator findAdminByUsername(final String username) {
		return this.administratorRepository.findAdminByUsername(username);
	}

	public Object[] queryC1() {
		Object[] result = null;

		result = this.administratorRepository.queryC1();

		return result;
	}

	public Object[] queryC2() {
		Object[] result = null;

		result = this.administratorRepository.queryC2();

		return result;
	}

	public Collection<forms.QueryAuxForm> queryC3() {
		final Collection<forms.QueryAuxForm> result = new ArrayList<>();

		Page<Object[]> p = null;

		p = this.administratorRepository.queryC3(new PageRequest(0, 3));

		if (p.getContent() != null)
			for (final Object[] objects : p.getContent()) {
				final forms.QueryAuxForm q = new QueryAuxForm();
				q.setId((int) objects[0]);
				q.setName((String) objects[1]);
				q.setCount((Long) objects[2]);

				result.add(q);
			}

		return result;
	}

	public Collection<forms.QueryAuxForm> queryC4() {
		final Collection<forms.QueryAuxForm> result = new ArrayList<>();

		Page<Object[]> p = null;

		p = this.administratorRepository.queryC4(new PageRequest(0, 3));

		if (p.getContent() != null)
			for (final Object[] objects : p.getContent()) {
				final forms.QueryAuxForm q = new QueryAuxForm();
				q.setId((int) objects[0]);
				q.setName((String) objects[1]);
				q.setCount((Long) objects[2]);

				result.add(q);
			}

		return result;
	}

	public Object[] queryC5() {
		Object[] result = null;

		result = this.administratorRepository.queryC5();
		return result;
	}

	public Collection<domain.Position> queryC6() {
		Collection<domain.Position> result = null;

		result = this.administratorRepository.queryC6();
		return result;
	}

	public Object[] queryB1() {
		Object[] result = null;

		result = this.administratorRepository.queryB1();
		return result;
	}

	public Object[] queryB2() {
		Object[] result = null;

		result = this.administratorRepository.queryB2();
		return result;
	}

	public Object[] queryB3() {
		Object[] result = null;

		result = this.administratorRepository.queryB3();
		return result;
	}

}
