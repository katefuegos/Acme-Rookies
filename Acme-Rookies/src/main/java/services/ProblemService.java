
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProblemRepository;
import security.LoginService;
import domain.Company;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	// Repository-----------------------------------------------

	@Autowired
	private ProblemRepository	problemRepository;

	// Services-------------------------------------------------

	@Autowired
	private CompanyService		companyService;


	// Constructor----------------------------------------------

	public ProblemService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Problem create() {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("COMPANY"));
		final Problem problem = new Problem();

		problem.setDraftmode(true);

		final String title = "";
		final String statement = "";
		final String hint = "";
		final String attachments = "";

		problem.setTitle(title);
		problem.setStatement(statement);
		problem.setHint(hint);
		problem.setAttachments(attachments);

		return problem;
	}

	public List<Problem> findAll() {
		return this.problemRepository.findAll();
	}

	public Problem findOne(final Integer problemId) {
		return this.problemRepository.findOne(problemId);
	}

	public Problem save(final Problem problem) {
		Assert.notNull(problem);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("COMPANY"));
		final Company company = this.companyService.findCompanyByUseraccount(LoginService.getPrincipal());
		Assert.isTrue(problem.getPosition().getCompany().equals(company));
		final Problem saved = this.problemRepository.save(problem);
		return saved;
	}
	public void delete(final Problem problem) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("COMPANY"));
		Assert.notNull(problem);
		final Company company = this.companyService.findCompanyByUseraccount(LoginService.getPrincipal());
		Assert.isTrue(problem.getPosition().getCompany().equals(company));
		this.problemRepository.delete(problem);
	}

	public void flush() {
		this.problemRepository.flush();
	}

	// Other Methods--------------------------------------------

	public Collection<Problem> findByPositionId(final int positionId) {
		Assert.notNull(positionId);
		return this.problemRepository.findByPositionId(positionId);
	}

	public Collection<Problem> findByCompanyId(final int companyId) {
		Assert.notNull(companyId);
		return this.problemRepository.findByCompanyId(companyId);
	}

	public Collection<Problem> findByPositionIdAndFinal(final int positionId) {
		Assert.notNull(positionId);
		return this.problemRepository.findByPositionIdAndFinal(positionId);
	}
}
