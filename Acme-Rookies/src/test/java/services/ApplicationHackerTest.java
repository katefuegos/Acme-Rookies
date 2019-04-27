package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;
import domain.Curricula;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationHackerTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CurriculaService curriculaService;

	// Tests ------------------------------------------------------------------
	@Test
	public void driverManage() {
		final int applicationId = super.getEntityId("application1");
		final int positionIdFinal = super.getEntityId("position1");
		final int positionIdDraft = super.getEntityId("position2");

		final Object testingData[][] = {
				/*
				 * a) Functional requirements - 10.1. An actor who is
				 * authenticated as a hacker must be able to manage his or her
				 * applications - Create b) Positive test c) analysis of
				 * sentence coverage: 95.8% d) analysis of data coverage - se crea
				 * application siendo hacker1.
				 */
				{ 1, 0, positionIdFinal, "hacker1", null },
				/*
				 * a) Functional requirements - 10.1. An actor who is
				 * authenticated as a hacker must be able to manage his or her
				 * applications - Edit b) Positive test c) analysis of sentence
				 * coverage: 95.8% d) analysis of data coverage - se edita
				 * application siendo hacker1.
				 */
				{ 0, applicationId, 0, "hacker1", null },
				/*
				 * a) Functional requirements - 10.1. An actor who is
				 * authenticated as a hacker must be able to manage his or her
				 * applications - Create b) Negative test - Business rule: It
				 * can created if you are not a hacker. c) analysis of sentence
				 * coverage: 95.8% d) analysis of data coverage - se crea
				 * application siendo company1.
				 */
				{ 1, 0, positionIdFinal, "company1",
						java.lang.IllegalArgumentException.class },
				/*
				 * a) Functional requirements - 10.1. An actor who is
				 * authenticated as a hacker must be able to manage his or her
				 * applications - Edit b) Negative test - Business rule: It can
				 * created if you are not a hacker. c) analysis of sentence
				 * coverage: 95.8% d) analysis of data coverage - se edita
				 * application siendo company2.
				 */
				{ 0, applicationId, 0, "company2",
						java.lang.IllegalArgumentException.class },
				/*
				 * a) Functional requirements - 10.1. An actor who is
				 * authenticated as a hacker must be able to manage his or her
				 * applications - Edit b) Negative test - Business rule: It can
				 * be edited if it's not from the hacker. c) analysis of
				 * sentence coverage: 95.8% d) analysis of data coverage - se edita
				 * application ajena.
				 */
				{ 0, applicationId, 0, "hacker2",
						java.lang.IllegalArgumentException.class },
				/*
				 * a) Functional requirements - 10.1. An actor who is
				 * authenticated as a hacker must be able to manage his or her
				 * applications - Create b) Negative test - Business rule: It
				 * can be created in position in draftMode. c) analysis of
				 * sentence coverage: 95.8% d) analysis of data coverage - se crea
				 * application con position en draftMode.
				 */
				{ 1, 0, positionIdDraft, "hacker1",
						java.lang.IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateNotificate((int) testingData[i][0],
						(int) testingData[i][1], (int) testingData[i][2],
						(String) testingData[i][3],
						(Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateNotificate(final int createEdit,
			final int applicationId, final int positionId,
			final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		Curricula curricula = null;
		try {
			if (username != null) {
				super.authenticate(username);
			} else
				super.unauthenticate();

			Application application = applicationService.findOne(applicationId);

			int curriculaId = super.getEntityId("curricula3");
			curricula = curriculaService.findOne(curriculaId);

			if (createEdit == 1) {
				if (curricula == null) {

				}
				application = applicationService.create(positionId,
						curricula.getId());
				applicationService.save(application);
			} else {
				application.setTextAnswer("TEST");
				applicationService.save(application);
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
