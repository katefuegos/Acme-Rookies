
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.CreditCard;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	// Repository-----------------------------------------------

	@Autowired
	private ActorRepository			actorRepository;

	// Services-------------------------------------------------

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private CreditCardService		creditCardService;


	// Constructor----------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public Actor create(final String authority) {
		final Actor actor = new Actor();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority(authority);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		actor.setUserAccount(userAccount);
		return actor;
	}

	public List<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final Integer actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		final Actor saved = this.actorRepository.save(actor);
		return saved;
	}

	public void delete(final Actor actor) {
		this.actorRepository.delete(actor);
	}

	// Other Methods--------------------------------------------

	public Actor findActorByUsername(final String username) {
		final Actor actor = this.actorRepository.findActorByUsername(username);
		return actor;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findByUserAccountId(userAccount.getId());
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.actorRepository.findByUserAccountId(userAccount.getId());
	}

	public Actor findByUserAccountId(final int id) {
		return this.actorRepository.findByUserAccountId(id);
	}

	public void update(final ActorForm actorform) {

		Assert.notNull(actorform);

		final Collection<Authority> authorities = actorform.getUserAccount().getAuthorities();
		final Authority hacker = new Authority();
		hacker.setAuthority(Authority.HACKER);
		final Authority company = new Authority();
		company.setAuthority(Authority.COMPANY);

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);

		if (authorities.contains(hacker)) {
			domain.Hacker hack = null;
			if (actorform.getId() != 0)
				hack = this.hackerService.findOne(actorform.getId());
			else {
				hack = this.hackerService.create();
				hack.setUserAccount(actorform.getUserAccount());
				// Assert.isTrue(LoginService.getPrincipal() == null);
				Assert.isTrue(this.serviceUtils.checkAuthorityBoolean(null));
			}
			hack.setId(actorform.getId());
			hack.setVersion(actorform.getVersion());
			hack.setName(actorform.getName());
			hack.setSurnames(actorform.getSurname());
			hack.setAddress(actorform.getAddress());
			hack.setEmail(actorform.getEmail());
			hack.setPhone(actorform.getPhone());
			hack.setPhoto(actorform.getPhoto());
			hack.setVATNumber(actorform.getVATNumber());

			final domain.CreditCard creditCard = this.saveCreditCard(actorform);

			hack.setCreditCard(creditCard);

			this.hackerService.save(hack);

		} else if (authorities.contains(company)) {
			domain.Company compa = null;
			if (actorform.getId() != 0)
				compa = this.companyService.findOne(actorform.getId());
			else {
				compa = this.companyService.create();
				compa.setUserAccount(actorform.getUserAccount());
				// Assert.isTrue(LoginService.getPrincipal() == null);
				Assert.isTrue(this.serviceUtils.checkAuthorityBoolean(null));
			}

			compa.setId(actorform.getId());
			compa.setVersion(actorform.getVersion());
			compa.setName(actorform.getName());
			compa.setSurnames(actorform.getSurname());
			compa.setAddress(actorform.getAddress());
			compa.setEmail(actorform.getEmail());
			compa.setPhone(actorform.getPhone());
			compa.setPhoto(actorform.getPhoto());
			compa.setVATNumber(actorform.getVATNumber());

			compa.setComercialName(actorform.getComercialName());

			final domain.CreditCard creditCard = this.saveCreditCard(actorform);
			compa.setCreditCard(creditCard);

			this.companyService.save(compa);

		} else if (authorities.contains(admin)) {
			Administrator administrator = null;

			Assert.isTrue(this.serviceUtils.checkAuthorityBoolean("ADMIN"));

			if (actorform.getId() != 0)
				administrator = this.administratorService.findOne(actorform.getId());
			else {
				administrator = this.administratorService.create();
				administrator.setUserAccount(actorform.getUserAccount());

			}

			administrator.setId(actorform.getId());
			administrator.setVersion(actorform.getVersion());
			administrator.setName(actorform.getName());
			administrator.setSurnames(actorform.getSurname());
			administrator.setAddress(actorform.getAddress());
			administrator.setEmail(actorform.getEmail());
			administrator.setPhone(actorform.getPhone());
			administrator.setPhoto(actorform.getPhoto());
			administrator.setVATNumber(actorform.getVATNumber());

			final domain.CreditCard creditCard = this.saveCreditCard(actorform);
			administrator.setCreditCard(creditCard);

			this.administratorService.save(administrator);

		}

	}

	private domain.CreditCard saveCreditCard(final ActorForm f) {
		final CreditCard creditCard;
		if (f.getId() != 0)
			creditCard = this.findOne(f.getId()).getCreditCard();
		else
			creditCard = this.creditCardService.create();

		creditCard.setHolderName(f.getHolderName());
		creditCard.setBrandName(f.getBrandName());
		creditCard.setNumber(f.getNumber());
		creditCard.setExpirationMonth(f.getExpirationMonth());
		creditCard.setExpirationYear(f.getExpirationYear());
		creditCard.setCVVCode(f.getCVVCode());

		final CreditCard result = this.creditCardService.save(creditCard);

		return result;
	}

	public void flush() {
		this.actorRepository.flush();
	}
}
