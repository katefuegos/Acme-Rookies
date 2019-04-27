package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NotificationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Company;
import domain.Hacker;
import domain.Notification;

@Service
@Transactional
public class NotificationService {

	// Repository-------------------------------------------------------------------------

	@Autowired
	private NotificationRepository notificationRepository;

	// Services---------------------------------------------------------------------------

	@Autowired
	ActorService actorService;

	@Autowired
	HackerService hackerService;

	@Autowired
	CompanyService companyService;

	// Constructor------------------------------------------------------------------------

	public NotificationService() {
		super();
	}

	// Simple
	// CRUD------------------------------------------------------------------------

	public Notification create() {
		final Notification notification = new Notification();
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount,
				"Debe estar logeado en el sistema para crear una carpeta");

		final String subject = "";
		final String body = "";

		notification.setSubject(subject);
		notification.setBody(body);
		return notification;
	}

	public Collection<Notification> findAll() {
		final Collection<Notification> notifications = this.notificationRepository
				.findAll();
		Assert.notNull(notifications);
		return notifications;
	}

	public Notification findOne(final Integer notificationId) {
		return this.notificationRepository.findOne(notificationId);
	}

	public Notification save(final Notification notification) {
		Assert.notNull(notification);
		notification.setMoment(new Date(System.currentTimeMillis() - 1000));
		Notification saved = this.notificationRepository.save(notification);

		return saved;
	}

	public void delete(final Notification notification) {

		this.notificationRepository.delete(notification);

	}

	public void flush() {

		this.notificationRepository.flush();

	}

	public void broadcast(final Notification notification) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString()
				.contains("ADMIN"));
		Assert.notNull(notification);
		Collection<Actor> actors = actorService.findAll();
		if (!actors.isEmpty()) {
			for (Actor a : actors) {
				Notification n = this.create();
				n.setActor(a);
				n.setBody(notification.getBody());
				n.setSubject(notification.getSubject());
				this.save(n);
			}
		}
	}

	public void broadcastHackers(final Notification notification) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString()
				.contains("ADMIN"));
		Assert.notNull(notification);
		Collection<Hacker> hackers = hackerService.findAll();
		if (!hackers.isEmpty()) {
			for (Hacker h : hackers) {
				Notification n = this.create();
				n.setActor(h);
				n.setBody(notification.getBody());
				n.setSubject(notification.getSubject());
				this.save(n);
			}
		}
	}

	public void broadcastCompanies(final Notification notification) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().toString()
				.contains("ADMIN"));
		Assert.notNull(notification);
		Collection<Company> companys = companyService.findAll();
		if (!companys.isEmpty()) {
			for (Company c : companys) {
				Notification n = this.create();
				n.setActor(c);
				n.setBody(notification.getBody());
				n.setSubject(notification.getSubject());
				this.save(n);
			}
		}
	}

	// Other
	// Methods---------------------------------------------------------------------------

	public Collection<Notification> findByActorId(final int actorId) {
		Assert.notNull(actorId);

		final Collection<Notification> result = this.notificationRepository
				.findByActorId(actorId);

		return result;

	}
}
