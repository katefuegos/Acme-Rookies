
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import security.LoginService;
import domain.Curricula;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService {

	// Repository-----------------------------------------------

	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	// Services-------------------------------------------------
	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private RookieService				rookieService;


	// Constructor----------------------------------------------

	public MiscellaneousDataService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public MiscellaneousData create() {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("ROOKIE"));
		final MiscellaneousData miscellaneousData = new MiscellaneousData();

		return miscellaneousData;
	}

	public List<MiscellaneousData> findAll() {
		return this.miscellaneousDataRepository.findAll();
	}

	public MiscellaneousData findOne(final Integer miscellaneousDataId) {
		return this.miscellaneousDataRepository.findOne(miscellaneousDataId);
	}

	public MiscellaneousData save(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("ROOKIE"));
		final int rookieId = this.rookieService.findRookieByUseraccount(LoginService.getPrincipal()).getId();
		final Collection<Curricula> curriculas = this.curriculaService.findByRookieId(rookieId);
		Assert.isTrue(curriculas.contains(miscellaneousData.getCurricula()));
		final MiscellaneousData saved = this.miscellaneousDataRepository.save(miscellaneousData);
		return saved;
	}

	public void delete(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("ROOKIE"));
		final int rookieId = this.rookieService.findRookieByUseraccount(LoginService.getPrincipal()).getId();
		final Collection<Curricula> curriculas = this.curriculaService.findByRookieId(rookieId);
		Assert.isTrue(curriculas.contains(miscellaneousData.getCurricula()));
		this.miscellaneousDataRepository.delete(miscellaneousData);
	}

	public void flush() {
		this.miscellaneousDataRepository.flush();
	}

	// Other Methods--------------------------------------------

	public MiscellaneousData copy(final int miscellaneousDataId) {
		Assert.notNull(miscellaneousDataId);
		final MiscellaneousData miscellaneousData = this.findOne(miscellaneousDataId);
		Assert.notNull(miscellaneousData);

		final MiscellaneousData miscellaneousDataCopy = new MiscellaneousData();
		miscellaneousDataCopy.setText(miscellaneousData.getText());
		miscellaneousDataCopy.setAttachments(miscellaneousData.getAttachments());

		return miscellaneousDataCopy;
	}

	public Collection<MiscellaneousData> findByCurriculaId(final int curriculaId) {
		Assert.notNull(curriculaId);
		return this.miscellaneousDataRepository.findByCurriculaId(curriculaId);
	}
}
