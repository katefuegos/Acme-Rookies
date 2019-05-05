
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

@Service
@Transactional
public class AuditService {

	// Repository-----------------------------------------------

	@Autowired
	private AuditRepository	auditRepository;

	// Services-------------------------------------AuditService.java------------

	@Autowired
	private AuditorService	auditorService;


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

}
