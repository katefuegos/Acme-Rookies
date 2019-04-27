
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import security.LoginService;
import domain.Curricula;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	// Repository-----------------------------------------------

	@Autowired
	private PositionDataRepository	positionDataRepository;

	// Services-------------------------------------------------

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private HackerService			hackerService;


	// Constructor----------------------------------------------

	public PositionDataService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public PositionData create() {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("HACKER"));
		final PositionData positionData = new PositionData();

		return positionData;
	}

	public List<PositionData> findAll() {
		return this.positionDataRepository.findAll();
	}

	public PositionData findOne(final Integer positionDataId) {
		return this.positionDataRepository.findOne(positionDataId);
	}

	public PositionData save(final PositionData positionData) {
		Assert.notNull(positionData);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("HACKER"));
		final int hackerId = this.hackerService.findHackerByUseraccount(LoginService.getPrincipal()).getId();
		final Collection<Curricula> curriculas = this.curriculaService.findByHackerId(hackerId);
		Assert.isTrue(curriculas.contains(positionData.getCurricula()));
		final PositionData saved = this.positionDataRepository.save(positionData);
		return saved;
	}

	public void delete(final PositionData positionData) {
		Assert.notNull(positionData);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("HACKER"));
		final int hackerId = this.hackerService.findHackerByUseraccount(LoginService.getPrincipal()).getId();
		final Collection<Curricula> curriculas = this.curriculaService.findByHackerId(hackerId);
		Assert.isTrue(curriculas.contains(positionData.getCurricula()));
		this.positionDataRepository.delete(positionData);
	}

	public void flush() {
		this.positionDataRepository.flush();
	}

	// Other Methods--------------------------------------------

	public PositionData copy(final int positionDataId) {
		Assert.notNull(positionDataId);
		final PositionData positionData = this.findOne(positionDataId);
		Assert.notNull(positionData);

		final PositionData positionDataCopy = new PositionData();
		positionDataCopy.setDescription(positionData.getDescription());
		positionDataCopy.setEndDate(positionData.getEndDate());
		positionDataCopy.setStartDate(positionData.getStartDate());
		positionDataCopy.setTitle(positionData.getTitle());

		return positionDataCopy;
	}

	public Collection<PositionData> findByCurriculaId(final int curriculaId) {
		Assert.notNull(curriculaId);
		return this.positionDataRepository.findByCurriculaId(curriculaId);
	}
}
