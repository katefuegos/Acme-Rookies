
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
import domain.EducationData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EducationDataHackerTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private EducationDataService	educationDataService;

	@Autowired
	private CurriculaService		curriculaService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverCreate() {
		final int curriculaIdMine = super.getEntityId("curricula1");
		final int curriculaIdNotMine = super.getEntityId("curricula2");
		final String degree = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Create. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se crea educationData siendo hacker1.
			 */
			{
				degree, curriculaIdMine, "hacker1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Create. b) Negative test -
			 * Business rule: it can't be created by a not company user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear educationData siendo company1.
			 */
			{
				degree, curriculaIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Create. b) Negative test -
			 * Business rule: it can't be created for a foreign curricula c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear educationData siendo hacker1 a una
			 * curricula ajena.
			 */
			{
				degree, curriculaIdNotMine, "hacker", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Create. b) Negative test -
			 * Business rule: it can't be created without a degree c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear educationData siendo company1 sin atributo
			 * degree.
			 */
			{
				null, curriculaIdMine, "hacker1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateCreateEducationData((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreateEducationData(final String degree, final int curriculaId, final String username, final Class<?> expected) {
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

			final EducationData educationData = this.educationDataService.create();
			educationData.setInstitution("test");
			educationData.setCurricula(curricula);
			educationData.setDegree(degree);
			educationData.setMark(5);
			educationData.setStartDate(start);
			educationData.setEndDate(end);

			this.educationDataService.save(educationData);

			this.educationDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEdit() {
		final int educationDataIdMine = super.getEntityId("educationData1");
		final int educationDataIdNotMine = super.getEntityId("educationData2");
		final String degree = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Edit. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se edita educationData siendo hacker1.
			 */
			{
				degree, educationDataIdMine, "hacker1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a not company user c)
			 * analysis of sentence coverage: % d) analysis of data coverage
			 * - se intenta editar educationData siendo company1.
			 */
			{
				degree, educationDataIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a foreign company c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar educationData ajeno siendo hacker1.
			 */
			{
				degree, educationDataIdNotMine, "hacker1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Edit. b) Negative test -
			 * Business rule: it can't be edited without a degree c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar educationData siendo company1 sin
			 * atributo degree.
			 */
			{
				null, educationDataIdMine, "hacker1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateEditEducationData((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEditEducationData(final String degree, final int educationDataId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final EducationData educationData = this.educationDataService.findOne(educationDataId);
			educationData.setDegree(degree);
			this.educationDataService.save(educationData);

			this.educationDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverDelete() {
		final int educationDataIdMine = super.getEntityId("educationData1");
		final int educationDataIdNotMine = super.getEntityId("educationData2");

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Delete. b) Positive test c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se elimina educationData siendo hacker1.
			 */
			{
				educationDataIdMine, "hacker1", null
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Eliminar. b) Negative test -
			 * Business rule: it can't be deleted by a not company user c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta eliminar educationData siendo company1.
			 */
			{
				educationDataIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 9.2. An actor who is
			 * authenticated as a company must be able to manage their
			 * database of educationDatas. - Delete. b) Negative test -
			 * Business rule: it can't be deleted by a foreign company c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta eliminar educationData ajeno siendo hacker1.
			 */
			{
				educationDataIdNotMine, "hacker1", java.lang.IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateDeleteEducationData((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateDeleteEducationData(final int educationDataId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final EducationData educationData = this.educationDataService.findOne(educationDataId);
			this.educationDataService.delete(educationData);

			this.educationDataService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
