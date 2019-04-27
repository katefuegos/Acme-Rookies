package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;
import domain.Company;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationCompanyTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private CompanyService companyService;

	// Tests ------------------------------------------------------------------
	@Test
	public void driverManage() {
		final int applicationIdPending = super.getEntityId("application1");
		final int applicationIdSubmitted = super.getEntityId("application2");

		final Object testingData[][] = {
				/*
				 * a) Functional requirements - 9.3. An actor who is
				 * authenticated as a company must be able to manage the
				 * applications to their positions - Accept. b) Positive test c)
				 * analysis of sentence coverage: 95.6% d) analysis of data coverage
				 * - se acepta application siendo company1.
				 */
				{ 1, applicationIdSubmitted, "company1", null },
				/*
				 * a) Functional requirements - 9.3. An actor who is
				 * authenticated as a company must be able to manage the
				 * applications to their positions - Decline. b) Positive test
				 * c) analysis of sentence coverage: 95.6% d) analysis of data
				 * coverage - se rechaza application siendo company1.
				 */
				{ 2, applicationIdSubmitted, "company1", null },
				/*
				 * a) Functional requirements - 9.3. An actor who is
				 * authenticated as a company must be able to manage the
				 * applications to their positions - Accept b) Negative test -
				 * Business rule: It can not be accepted if it's not from an
				 * company. c) analysis of sentence coverage: 95.6% d) analysis of
				 * data coverage se intenta aceptar application siendo hacker1.
				 */
				{ 1, applicationIdSubmitted, "hacker1",
						java.lang.IllegalArgumentException.class },
				/*
				 * a) Functional requirements - 9.3. An actor who is
				 * authenticated as a company must be able to manage the
				 * applications to their positions - Decline b) Negative test -
				 * Business rule: It can not be refused if it's not from an
				 * company. c) analysis of sentence coverage: 95.6% d) analysis of
				 * data coverage se intenta rechazar application siendo hacker1.
				 */
				{ 2, applicationIdSubmitted, "hacker1",
						java.lang.IllegalArgumentException.class },
				/*
				 * a) Functional requirements - 9.3. An actor who is
				 * authenticated as a company must be able to manage the
				 * applications to their positions - Accept b) Negative test -
				 * Business rule: It can not be accepted if it's not in
				 * SUBMITTED status. c) analysis of sentence coverage: 95.6% d)
				 * analysis of data coverage se intenta aceptar application con
				 * PENDING STATUS.
				 */
				{ 1, applicationIdPending, "company1",
						java.lang.IllegalArgumentException.class },
				/*
				 * a) Functional requirements - 9.3. An actor who is
				 * authenticated as a company must be able to manage the
				 * applications to their positions - Decline b) Negative test -
				 * Business rule: It can not be accepted if it's not in
				 * SUBMITTED status. c) analysis of sentence coverage: 95.6% d)
				 * analysis of data coverage se intenta rechazar application con
				 * PENDING STATUS.
				 */
				{ 2, applicationIdPending, "company1",
						java.lang.IllegalArgumentException.class },
				/*
				 * a) Functional requirements - 9.3. An actor who is
				 * authenticated as a company must be able to manage the
				 * applications to their positions - Accept b) Negative test -
				 * Business rule: It can not be accepted if it's not from
				 * company. c) analysis of sentence coverage: 95.6% d) analysis of
				 * data coverage se intenta aceptar application ajena.
				 */
				{ 1, applicationIdSubmitted, "company2",
						java.lang.IllegalArgumentException.class },
				/*
				 * a) Functional requirements - 9.3. An actor who is
				 * authenticated as a company must be able to manage the
				 * applications to their positions - Decline b) Negative test -
				 * Business rule: It can not be accepted if it's not from
				 * company. c) analysis of sentence coverage: 95.6% d) analysis of
				 * data coverage se intenta rechazar application ajena.
				 */
				{ 2, applicationIdSubmitted, "company2",
				java.lang.IllegalArgumentException.class } 
				};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateNotificate((int) testingData[i][0],
						(int) testingData[i][1], (String) testingData[i][2],
						(Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateNotificate(final int acceptDecline,
			final int applicationId, final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			Application application = applicationService.findOne(applicationId);
			Company company = companyService.findCompanyByUsername(username);
			
			if (acceptDecline == 1) {
				applicationService.accept(application, company);
			} else {
				applicationService.reject(application, company);
			}
			
			super.unauthenticate();
			this.applicationService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
