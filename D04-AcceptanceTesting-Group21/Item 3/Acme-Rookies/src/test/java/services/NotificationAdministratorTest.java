package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Notification;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NotificationAdministratorTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private NotificationService notificationService;

	// Tests ------------------------------------------------------------------
	@Test
	public void driverManage() {

		final Object testingData[][] = {
		/*
		 * a) Functional requirements - Send notification (Security Breach). b)
		 * Positive test c) analysis of sentence coverage: 93.1% d) analysis of data
		 * coverage - se envía notification para notificar brecha de seguridad).
		 */
		{ 1, "admin", null },
		/*
		 * a) Functional requirements - Send notification (Security Breach). b)
		 * Negative test - Business rule: It can not be send if it's not from an
		 * admin. c) analysis of sentence coverage: 93.1% d) analysis of data
		 * coverage se intenta enviar notification siendo company1.
		 */
		{ 1, "company1", java.lang.IllegalArgumentException.class },
		/*
		 * a) Functional requirements - Send notification (Security Breach). b)
		 * Negative test - Business rule: It can not be send if it's not from an
		 * admin. c) analysis of sentence coverage: 93.1% d) analysis of data
		 * coverage se intenta enviar notification sin estar logeado.
		 */
		{ 1, null, java.lang.IllegalArgumentException.class },
		/*
		 * a) Functional requirements - Send notification (Security Breach). b)
		 * Negative test - Business rule: No body. c) analysis of sentence
		 * coverage: 93.1% d) analysis of data coverage se intenta enviar
		 * notification sin body.
		 */
		{ 0, "admin", javax.validation.ConstraintViolationException.class } };

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateNotificate((int) testingData[i][0],
						(String) testingData[i][1],
						(Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateNotificate(final int body, final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			Notification notification = notificationService.create();
			notification.setSubject("TESTSUBJECT");
			if (body == 1) {
				notification.setBody("TESTBODY");
			}
			this.notificationService.broadcast(notification);

			super.unauthenticate();
			this.notificationService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
