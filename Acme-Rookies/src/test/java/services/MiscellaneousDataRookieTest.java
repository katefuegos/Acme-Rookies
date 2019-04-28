
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curricula;
import domain.MiscellaneousData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MiscellaneousDataRookieTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private CurriculaService			curriculaService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverCreate() {
		final int curriculaIdMine = super.getEntityId("curricula1");
		final int curriculaIdNotMine = super.getEntityId("curricula2");
		final String text = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Create. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se crea miscellaneousData siendo company1.
			 */
			{
				text, curriculaIdMine, "rookie1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Create. b) Negative test -
			 * Business rule: it can't be created by a not company user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear miscellaneousData siendo rookie1.
			 */
			{
				text, curriculaIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Create. b) Negative test -
			 * Business rule: it can't be created for a foreign curricula c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear miscellaneousData siendo company1 a una curricula ajena.
			 */
			{
				text, curriculaIdNotMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Create. b) Negative test -
			 * Business rule: it can't be created without a text c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear miscellaneousData siendo company1 sin atributo text.
			 */
			{
				null, curriculaIdMine, "rookie1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateCreateMiscellaneousData((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreateMiscellaneousData(final String text, final int curriculaId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Curricula curricula = this.curriculaService.findOne(curriculaId);

			final MiscellaneousData miscellaneousData = this.miscellaneousDataService.create();
			miscellaneousData.setAttachments("http://www.test.com");
			miscellaneousData.setCurricula(curricula);
			miscellaneousData.setText(text);

			this.miscellaneousDataService.save(miscellaneousData);

			this.miscellaneousDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEdit() {
		final int miscellaneousDataIdMine = super.getEntityId("miscellaneousData1");
		final int miscellaneousDataIdNotMine = super.getEntityId("miscellaneousData3");
		final String text = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Edit. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se edita miscellaneousData siendo rookie1.
			 */
			{
				text, miscellaneousDataIdMine, "rookie1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a not company user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar miscellaneousData siendo company1.
			 */
			{
				text, miscellaneousDataIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a foreign company c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar miscellaneousData ajeno siendo rookie1.
			 */
			{
				text, miscellaneousDataIdNotMine, "rookie1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited without a text c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar miscellaneousData siendo company1 sin atributo text.
			 */
			{
				null, miscellaneousDataIdMine, "rookie1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateEditMiscellaneousData((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEditMiscellaneousData(final String text, final int miscellaneousDataId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
			miscellaneousData.setText(text);
			this.miscellaneousDataService.save(miscellaneousData);

			this.miscellaneousDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverDelete() {
		final int miscellaneousDataIdMine = super.getEntityId("miscellaneousData1");
		final int miscellaneousDataIdNotMine = super.getEntityId("miscellaneousData3");

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Delete. b) Positive test c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se elimina miscellaneousData siendo rookie1.
			 */
			{
				miscellaneousDataIdMine, "rookie1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Eliminar. b) Negative test -
			 * Business rule: it can't be deleted by a not company user c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta eliminar miscellaneousData siendo company1.
			 */
			{
				miscellaneousDataIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of miscellaneousDatas. - Delete. b) Negative test -
			 * Business rule: it can't be deleted by a foreign company c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta eliminar miscellaneousData ajeno siendo rookie1.
			 */
			{
				miscellaneousDataIdNotMine, "rookie1", java.lang.IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateDeleteMiscellaneousData((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateDeleteMiscellaneousData(final int miscellaneousDataId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final MiscellaneousData miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
			this.miscellaneousDataService.delete(miscellaneousData);

			this.miscellaneousDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
