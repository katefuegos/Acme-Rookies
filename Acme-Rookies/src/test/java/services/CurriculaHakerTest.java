
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curricula;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculaHakerTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private CurriculaService	curriculaService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverCreate() {
		final String fullName = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula. - Create. b) Positive test c) analysis of sentence
			 * coverage: 88.2% d) analysis of data coverage - se crea curricula
			 * siendo hacker1.
			 */
			{

				fullName, "hacker1", null
			},
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula. -Create. b) Negative test - Business rule: it
			 * can't be created by a not hacker user c) analysis of sentence
			 * coverage: 88.2% d) analysis of data coverage - se intenta crear
			 * problem siendo company1.
			 */
			{
				fullName, "company1", java.lang.IllegalArgumentException.class
			},

			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula. - Create. b) Negative test - Business rule: it
			 * can't be created without a fullName c) analysis of sentence
			 * coverage: 88.2% d) analysis of data coverage - se intenta crear
			 * curricula siendo hacker1 sin atributo fullName.
			 */
			{
				null, "hacker1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateCreateCurricula((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreateCurricula(final String fullName, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Curricula curricula = this.curriculaService.create();
			curricula.setGithubProfile("http://www.test.com");
			curricula.setLinkedinprofile("http://www.test.com");
			curricula.setPhoneNumber("test");
			curricula.setStatement("test");
			curricula.setFullName(fullName);

			this.curriculaService.save(curricula);

			this.curriculaService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	// -------------------------Edit--------------------------

	@Test
	public void driverEdit() {
		final int curriculaIdMine = super.getEntityId("curricula1");
		final int curriculaIdNotMine = super.getEntityId("curricula2");
		final String fullName = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula. - Edit. b) Positive test c) analysis of sentence
			 * coverage: 92.4% d) analysis of data coverage - se edita curricula
			 * siendo hacker1.
			 */
			{
				fullName, curriculaIdMine, "hacker1", null
			},
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula.- Edit. b) Negative test - Business rule: it can't
			 * be edited by a not hacker user c) analysis of sentence
			 * coverage: 92.4% d) analysis of data coverage - se intenta editar
			 * curricula siendo company1.
			 */
			{
				fullName, curriculaIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula. - Edit. b) Negative test - Business rule: it can't
			 * be edited by a foreign c) analysis of sentence coverage: 92.4% d)
			 * analysis of data coverage - se intenta editar curricula ajeno
			 * siendo hacker1.
			 */
			{
				fullName, curriculaIdNotMine, "hacker1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula. - Create. b) Negative test - Business rule: it
			 * can't be created without a fullName c) analysis of sentence
			 * coverage: 92.4% d) analysis of data coverage - se intenta crear
			 * curricula siendo hacker1 sin atributo fullName.
			 */
			{
				null, curriculaIdMine, "hacker1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateEditCurricula((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEditCurricula(final String fullName, final int curriculaId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			curricula.setFullName(fullName);
			this.curriculaService.save(curricula);

			this.curriculaService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	// ------------------------------Delete----------------------------------

	@Test
	public void driverDelete() {
		final int curriculaIdMine = super.getEntityId("curricula1");
		final int curriculaIdNotMine = super.getEntityId("curricula2");

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula. - Delete. b) Positive test c) analysis of sentence
			 * coverage: 89.7% d) analysis of data coverage - se elimina
			 * curricula siendo hacker1.
			 */
			{
				curriculaIdMine, "hacker1", null
			},
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula.. - Eliminar. b) Negative test - Business rule: it
			 * can't be deleted by a not company user c) analysis of
			 * sentence coverage: 89.7% d) analysis of data coverage - se
			 * intenta eliminar curricula siendo company1.
			 */
			{
				curriculaIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 17.1. An actor who is
			 * authenticated as a hacker must be able to manage his or her
			 * curricula. - Delete. b) Negative test - Business rule: it
			 * can't be deleted by a foreign company c) analysis of sentence
			 * coverage: 89.7% d) analysis of data coverage - se intenta
			 * eliminar curricula ajeno siendo hacker1.
			 */
			{
				curriculaIdNotMine, "hacker1", java.lang.IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateDeleteCurricula((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateDeleteCurricula(final int curriculaId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			this.curriculaService.delete(curricula);

			this.curriculaService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
