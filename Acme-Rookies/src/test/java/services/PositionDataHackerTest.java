
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curricula;
import domain.PositionData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionDataHackerTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private CurriculaService	curriculaService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverCreate() {
		final int curriculaIdMine = super.getEntityId("curricula1");
		final int curriculaIdNotMine = super.getEntityId("curricula2");
		final String title = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Create. b) Positive test c)
			 * analysis of sentence coverage: 92.4 % d) analysis of data coverage
			 * - se crea positionData siendo hacker1.
			 */
			{
				title, curriculaIdMine, "hacker1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Create. b) Negative test -
			 * Business rule: it can't be created by a not company user c)
			 * analysis of sentence coverage: 92.4 % d) analysis of data coverage
			 * - se intenta crear positionData siendo company1.
			 */
			{
				title, curriculaIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Create. b) Negative test -
			 * Business rule: it can't be created for a foreign curricula c)
			 * analysis of sentence coverage: 92.4 % d) analysis of data coverage
			 * - se intenta crear positionData siendo hacker1 a una curricula ajena.
			 */
			{
				title, curriculaIdNotMine, "hacker1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Create. b) Negative test -
			 * Business rule: it can't be created without a title c)
			 * analysis of sentence coverage: 92.4 % d) analysis of data coverage
			 * - se intenta crear positionData siendo hacker1 sin atributo title.
			 */
			{
				null, curriculaIdMine, "hacker1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateCreatePositionData((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreatePositionData(final String title, final int curriculaId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			final Date start = new Date();
			final Date end = new Date();

			final PositionData positionData = this.positionDataService.create();
			positionData.setDescription("test");
			positionData.setCurricula(curricula);
			positionData.setTitle(title);
			positionData.setStartDate(start);
			positionData.setEndDate(end);

			this.positionDataService.save(positionData);

			this.positionDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEdit() {
		final int positionDataIdMine = super.getEntityId("positionData1");
		final int positionDataIdNotMine = super.getEntityId("positionData2");
		final String title = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Edit. b) Positive test c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se edita positionData siendo hacker1.
			 */
			{
				title, positionDataIdMine, "hacker1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a not company user c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta editar positionData siendo company1.
			 */
			{
				title, positionDataIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a foreign company c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta editar positionData ajeno siendo hacker1.
			 */
			{
				title, positionDataIdNotMine, "hacker1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited without a title c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta editar positionData siendo hacker1 sin atributo title.
			 */
			{
				null, positionDataIdMine, "hacker1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateEditPositionData((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEditPositionData(final String title, final int positionDataId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final PositionData positionData = this.positionDataService.findOne(positionDataId);
			positionData.setTitle(title);
			this.positionDataService.save(positionData);

			this.positionDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverDelete() {
		final int positionDataIdMine = super.getEntityId("positionData1");
		final int positionDataIdNotMine = super.getEntityId("positionData2");

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Delete. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se elimina positionData siendo hacker1.
			 */
			{
				positionDataIdMine, "hacker1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Eliminar. b) Negative test -
			 * Business rule: it can't be deleted by a not company user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta eliminar positionData siendo company1.
			 */
			{
				positionDataIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is authenticated as a company must
			 * be able to manage their database of positionDatas. - Delete. b) Negative test -
			 * Business rule: it can't be deleted by a foreign company c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta eliminar positionData ajeno siendo hacker1.
			 */
			{
				positionDataIdNotMine, "hacker1", java.lang.IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateDeletePositionData((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateDeletePositionData(final int positionDataId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final PositionData positionData = this.positionDataService.findOne(positionDataId);
			this.positionDataService.delete(positionData);

			this.positionDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
