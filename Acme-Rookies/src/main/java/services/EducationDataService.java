package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import security.LoginService;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;

@Service
@Transactional
public class EducationDataService {

	// Repository-----------------------------------------------

	@Autowired
	private EducationDataRepository educationDataRepository;

	// Services-------------------------------------------------

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private HackerService hackerService;

	// Constructor----------------------------------------------

	public EducationDataService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public EducationData create() {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString()
				.contains("HACKER"));
		final EducationData educationData = new EducationData();

		return educationData;
	}

	public List<EducationData> findAll() {
		return this.educationDataRepository.findAll();
	}

	public EducationData findOne(final Integer educationDataId) {
		return this.educationDataRepository.findOne(educationDataId);
	}

	public EducationData save(final EducationData educationData) {
		Assert.notNull(educationData);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString()
				.contains("HACKER"));
		final Hacker hacker = this.hackerService
				.findHackerByUseraccount(LoginService.getPrincipal());
		Assert.isTrue(educationData.getCurricula().getHacker().equals(hacker));

		final EducationData saved = this.educationDataRepository
				.save(educationData);
		return saved;
	}

	public void delete(final EducationData educationData) {
		Assert.notNull(educationData);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString()
				.contains("HACKER"));
		final int hackerId = this.hackerService.findHackerByUseraccount(
				LoginService.getPrincipal()).getId();
		final Collection<Curricula> curriculas = this.curriculaService
				.findByHackerId(hackerId);
		Assert.isTrue(curriculas.contains(educationData.getCurricula()));
		this.educationDataRepository.delete(educationData);
	}

	public void flush() {
		this.educationDataRepository.flush();
	}

	// Other Methods--------------------------------------------

	public EducationData copy(final int educationDataId) {
		Assert.notNull(educationDataId);
		final EducationData educationData = this.findOne(educationDataId);
		Assert.notNull(educationData);

		final EducationData educationDataCopy = new EducationData();
		educationDataCopy.setDegree(educationData.getDegree());
		educationDataCopy.setEndDate(educationData.getEndDate());
		educationDataCopy.setStartDate(educationData.getStartDate());
		educationDataCopy.setInstitution(educationData.getInstitution());
		educationDataCopy.setMark(educationData.getMark());

		return educationDataCopy;
	}

	public Collection<EducationData> findByCurriculaId(final int curriculaId) {
		Assert.notNull(curriculaId);
		return this.educationDataRepository.findByCurriculaId(curriculaId);
	}
}
