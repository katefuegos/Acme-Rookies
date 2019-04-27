
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProblemCompanyTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private PositionService	positionService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverCreate() {
		final int positionIdMine = super.getEntityId("position1");
		final int positionIdNotMine = super.getEntityId("position3");
		final String title = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Create. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se crea problem siendo company1.
			 */
			{
				title, positionIdMine, "company1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Create. b) Negative test -
			 * Business rule: it can't be created by a not company user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear problem siendo hacker1.
			 */
			{
				title, positionIdMine, "hacker1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Create. b) Negative test -
			 * Business rule: it can't be created for a foreign position c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear problem siendo company1 a una position ajena.
			 */
			{
				title, positionIdNotMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Create. b) Negative test -
			 * Business rule: it can't be created without a title c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear problem siendo company1 sin atributo title.
			 */
			{
				null, positionIdMine, "company1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateCreateProblem((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreateProblem(final String title, final int positionId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Position position = this.positionService.findOne(positionId);

			final Problem problem = this.problemService.create();
			problem.setAttachments("http://www.test.com");
			problem.setDraftmode(false);
			problem.setHint("test");
			problem.setPosition(position);
			problem.setStatement("test");
			problem.setTitle(title);

			this.problemService.save(problem);

			this.problemService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEdit() {
		final int problemIdMine = super.getEntityId("problem3");
		final int problemIdNotMine = super.getEntityId("problem5");
		final String title = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Edit. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se edita problem siendo company1.
			 */
			{
				title, problemIdMine, "company1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a not company user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar problem siendo hacker1.
			 */
			{
				title, problemIdMine, "hacker1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a foreign company c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar problem ajeno siendo company1.
			 */
			{
				title, problemIdNotMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Edit. b) Negative test -
			 * Business rule: it can't be edited without a title c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar problem siendo company1 sin atributo title.
			 */
			{
				null, problemIdMine, "company1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateEditProblem((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEditProblem(final String title, final int problemId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Problem problem = this.problemService.findOne(problemId);
			problem.setTitle(title);
			this.problemService.save(problem);

			this.problemService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverDelete() {
		final int problemIdMine = super.getEntityId("problem3");
		final int problemIdNotMine = super.getEntityId("problem5");

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Delete. b) Positive test c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se elimina problem siendo company1.
			 */
			{
				problemIdMine, "company1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Eliminar. b) Negative test -
			 * Business rule: it can't be deleted by a not company user c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta eliminar problem siendo hacker1.
			 */
			{
				problemIdMine, "hacker1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of problems. - Delete. b) Negative test -
			 * Business rule: it can't be deleted by a foreign company c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta eliminar problem ajeno siendo company1.
			 */
			{
				problemIdNotMine, "company1", java.lang.IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateDeleteProblem((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateDeleteProblem(final int problemId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Problem problem = this.problemService.findOne(problemId);
			this.problemService.delete(problem);

			this.problemService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
