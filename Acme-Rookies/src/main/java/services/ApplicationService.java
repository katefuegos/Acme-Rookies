
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Application;
import domain.Company;
import domain.Curricula;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class ApplicationService {

	// Repository-----------------------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	// Services-------------------------------------------------

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private CompanyService			companyService;


	// Constructor----------------------------------------------

	public ApplicationService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Application create(final int positionId, final int curriculaId) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("HACKER"));
		Assert.notNull(positionId);
		Assert.notNull(curriculaId);
		final Application application = new Application();
		final Position position = this.positionService.findOne(positionId);
		Assert.notNull(position);
		Assert.isTrue(position.isDraftmode() == false);

		application.setPosition(position);

		final Collection<Problem> problems = this.problemService.findByPositionIdAndFinal(position.getId());
		final Problem problem = (Problem) this.getRandomObject(problems);
		application.setProblem(problem);

		final Curricula curricula = this.curriculaService.findOne(curriculaId);
		Assert.notNull(curricula);
		application.setCurricula(curricula);

		application.setStatus("PENDING");

		application.setHacker(this.hackerService.findHackerByUseraccount(LoginService.getPrincipal()));

		return application;
	}

	public List<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final Integer applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		if (LoginService.getPrincipal().getAuthorities().toString().contains("HACKER")) {
			Assert.isTrue(application.getHacker().equals(this.hackerService.findHackerByUseraccount(LoginService.getPrincipal())));
			Assert.isTrue(application.getPosition().isDraftmode() == false);
		} else {
			Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("COMPANY"));
			Assert.isTrue(application.getPosition().getCompany().equals(this.companyService.findCompanyByUseraccount(LoginService.getPrincipal())));
		}

		if (application.getId() == 0) {
			application.setPublicationMoment(new Date(System.currentTimeMillis() - 1000));
			final Curricula copy = this.curriculaService.copy(application.getCurricula().getId());
			application.setCurricula(copy);
		}

		if ((application.getStatus().equals("PENDING")) && (application.getTextAnswer() != null) && (application.getLinkAnswer() != null) && (application.getTextAnswer().trim().length() > 0) && (application.getLinkAnswer().trim().length() > 0))
			application.setStatus("SUBMITTED");

		if (application.getStatus() == "SUBMITTED")
			application.setSubmissionMoment(new Date(System.currentTimeMillis() - 1000));

		Assert.isTrue(application.getCurricula().isCopy() == true);

		final Application saved = this.applicationRepository.save(application);
		return saved;
	}

	public void delete(final Application application) {
		this.applicationRepository.delete(application);
	}

	public void flush() {
		this.applicationRepository.flush();
	}

	// Other Methods--------------------------------------------

	private Object getRandomObject(final Collection<Problem> problems) {
		final Random rnd = new Random();
		final int i = rnd.nextInt(problems.size());
		return problems.toArray()[i];
	}

	public Collection<Application> findByCompanyId(final int companyId) {
		Assert.notNull(companyId);
		return this.applicationRepository.findByCompanyId(companyId);
	}

	public Collection<Application> findByHackerId(final int hackerId) {
		Assert.notNull(hackerId);
		return this.applicationRepository.findByHackerId(hackerId);
	}

	public Collection<Application> findPendingByHackerId(final int hackerId) {
		Assert.notNull(hackerId);
		return this.applicationRepository.findPendingByHackerId(hackerId);
	}

	public Collection<Application> findRejectedByHackerId(final int hackerId) {
		Assert.notNull(hackerId);
		return this.applicationRepository.findRejectedByHackerId(hackerId);
	}

	public Collection<Application> findAcceptedByHackerId(final int hackerId) {
		Assert.notNull(hackerId);
		return this.applicationRepository.findAcceptedByHackerId(hackerId);
	}

	public Collection<Application> findSubmittedByHackerId(final int hackerId) {
		Assert.notNull(hackerId);
		return this.applicationRepository.findSubmittedByHackerId(hackerId);
	}

	public void reject(final Application application, final Company company) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("COMPANY"));
		Assert.notNull(company);
		Assert.notNull(application);
		Assert.isTrue(application.getPosition().getCompany().equals(company));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		application.setStatus("REJECTED");
		this.save(application);
	}

	public void accept(final Application application, final Company company) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("COMPANY"));
		Assert.notNull(company);
		Assert.notNull(application);
		Assert.isTrue(application.getPosition().getCompany().equals(company));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		application.setStatus("ACCEPTED");
		this.save(application);
	}
}
