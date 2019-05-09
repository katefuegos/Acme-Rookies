
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import security.LoginService;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Service
@Transactional
public class AuditService {

	// Repository-----------------------------------------------

	@Autowired
	private AuditRepository	auditRepository;

	// Services-------------------------------------AuditService.java------------

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private PositionService	positionService;


	// Constructor----------------------------------------------

	public AuditService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Audit create() {
		final Audit audit = new Audit();

		audit.setDraftMode(true);
		audit.setMoment(new Date(System.currentTimeMillis() - 1000));
		audit.setAuditor(this.auditorService.findByUseraccount(LoginService.getPrincipal()));

		return audit;
	}

	public List<Audit> findAll() {
		return this.auditRepository.findAll();
	}

	public Audit findOne(final Integer auditId) {
		return this.auditRepository.findOne(auditId);
	}

	public Audit save(final Audit audit) {
		Assert.notNull(audit);
		if (audit.getId() == 0)
			audit.setMoment(new Date(System.currentTimeMillis() - 1000));
		final Audit saved = this.auditRepository.save(audit);
		return saved;
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(audit.isDraftMode(), "audit.error.draftmode");

		this.auditRepository.delete(audit);
	}

	public void flush() {
		this.auditRepository.flush();
	}

	public Collection<Audit> findByAuditorId(final int auditorId) {
		Assert.notNull(auditorId);
		return this.auditRepository.findByAuditorId(auditorId);
	}

	public Collection<Audit> findByPositionId(final int positionId) {
		Assert.notNull(positionId);
		return this.auditRepository.findByPositionId(positionId);
	}

	public Double calculateScoreCompany(final int companyId) {
		Double result = null;

		final Integer cantidad = this.auditRepository.countByCompany(companyId);

		if (cantidad > 0)
			result = (this.auditRepository.sumByCompany(companyId) * 1.0) / (cantidad * 1.0);

		return result;

	}

	public void asign(final Position position, final Auditor auditor) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString().contains("AUDITOR"));
		Assert.notNull(auditor);
		Assert.notNull(position);
		Assert.isTrue(position.isDraftmode()); // TODO ¿Es necesario que esté en draft mode?
		auditor.getPositions().add(position);
		this.auditorService.save(auditor);
	}

}
