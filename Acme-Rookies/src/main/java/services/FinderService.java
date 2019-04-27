
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Configuration;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class FinderService {

	// Repository-----------------------------------------------

	@Autowired
	private FinderRepository		finderRepository;

	// Services-------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private HackerService			hackerService;

	@Autowired
	private PositionService			positionService;


	// Constructor----------------------------------------------

	public FinderService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public Finder create() {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		final Authority hacker = new Authority();
		hacker.setAuthority("HACKER");
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(hacker), "finder.error.no.hacker");

		final Finder res = new Finder();
		// final Date lastUpdate = new Date();

		final Collection<domain.Position> positions = new ArrayList<domain.Position>();

		res.setKeyword("");
		res.setMinSalary(0);

		res.setLastUpdate(null);
		res.setPositions(positions);

		final Date current = new Date();
		res.setDeadlineMin(current);
		res.setDeadlineMax(new Date(current.getTime() + 315360000000L * 2));

		return res;
	}

	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final Integer finderId) {
		Assert.notNull(finderId);
		final Finder finder = this.finderRepository.findOne(finderId);
		this.check(finder);
		return finder;
	}

	public Finder save(Finder finder) {
		Assert.notNull(finder);
		this.check(finder);

		finder.setLastUpdate(this.updateTime());
		finder = this.updateFinder(finder);

		final Finder saved = this.finderRepository.save(finder);
		if (finder.getId() == 0) {
			final UserAccount userAccount = LoginService.getPrincipal();
			final Hacker hacker = this.hackerService.findHackerByUseraccount(userAccount);
			hacker.setFinder(saved);
			this.hackerService.save(hacker);
		}
		return saved;
	}

	public Finder clear(final Finder f) {
		this.check(f);
		final Date currentDate = new Date();
		f.setKeyword("");
		f.setMinSalary(0);
		f.setDeadlineMin(currentDate);
		f.setDeadlineMax(new Date(currentDate.getTime() + 315360000000L * 2));
		f.setLastUpdate(new Date(currentDate.getTime() - 1000));

		final Collection<domain.Position> positions = this.positionService.findFinalNotCancelled();

		f.setPositions(positions);

		this.save(f);

		return f;

	}

	// Other Methods--------------------------------------------

	private Date updateTime() {
		final Date currentDate = new Date();
		final Configuration configuration = this.configurationService.findDefault();
		final Date updateFinder = new Date(currentDate.getTime() - configuration.getFinderCacheTime() * 1000 * 60 * 60);
		final Date lastUpdate = new Date(updateFinder.getTime() - 1000);

		return lastUpdate;
	}

	public Finder updateFinder(final Finder finder) {
		this.check(finder);
		final Finder result = this.checkPrincipal(finder);

		final Configuration configuration = this.configurationService.findAll().iterator().next();

		final Date currentDate = new Date();
		final Date updateFinder = new Date(currentDate.getTime() - configuration.getFinderCacheTime() * 1000 * 60 * 60);
		final Date lastUpdate = new Date(currentDate.getTime() - 1000);

		if (!finder.getLastUpdate().after(updateFinder) || finder.getId() == 0) {
			result.setPositions(this.searchPosition(finder, configuration.getFinderMaxResults()));
			result.setLastUpdate(lastUpdate);
			// result = this.finderRepository.save(result);
		}

		return result;
	}

	private void check(final Finder f) {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		final Authority hackerAuthority = new Authority();
		hackerAuthority.setAuthority("HACKER");

		final Finder finder = this.findFinderByHackerId(actor.getId());

		Assert.isTrue(											//No es necesaria esta comprobacion
			actor.getUserAccount().getAuthorities().contains(hackerAuthority), "finder.error.no.hacker");

		Assert.isTrue(f.equals(finder) || (f.getId() == 0 && finder == null), "finder.error.owner");

	}

	private Finder checkPrincipal(final Finder f) {
		Finder result;

		final Date currentDate = new Date();

		if (f.getKeyword() == null)
			f.setKeyword("");

		if (f.getMinSalary() == null)
			f.setMinSalary(0);

		if (f.getDeadlineMin() == null)
			f.setDeadlineMin(currentDate);

		if (f.getDeadlineMax() == null)
			f.setDeadlineMax(new Date(currentDate.getTime() + 315360000000L * 2));// 315360000000L son 10 años en milisegundos

		result = f;

		return result;
	}

	public Collection<domain.Position> searchPosition(final Finder f, final int maxResult) {
		List<Position> result = new ArrayList<>();
		final Finder finder = this.checkPrincipal(f);

		final Page<Position> p;
		p = this.finderRepository.searchPositions(finder.getKeyword(), finder.getDeadlineMin(), finder.getDeadlineMax(), finder.getMinSalary(), new PageRequest(0, maxResult));

		if (p.getContent() != null)
			result = new ArrayList<>(p.getContent());

		return result;
	}

	public Finder findFinderByHackerId(final int hackerId) {
		return this.finderRepository.findByHackerId(hackerId);
	}

	public Finder findFinder() {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Hacker hacker = this.hackerService.findHackerByUseraccount(userAccount);

		Finder finder = this.findFinderByHackerId(hacker.getId());

		if (finder == null)
			finder = this.save(this.create());

		return finder;

	}
}
