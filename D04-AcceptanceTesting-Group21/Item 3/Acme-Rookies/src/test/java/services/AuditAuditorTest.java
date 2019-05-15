
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Audit;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditAuditorTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private AuditService	auditService;

	@Autowired
	private PositionService	positionService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverCreate() {
		final int positionIdMine = super.getEntityId("position3");
		final int positionIdNotMine = super.getEntityId("position2");
		final String text = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 3.2. An actor who
			 * is authenticated as an auditor must be able to manage
			 * his or her audits.: 92.4% d) analysis of data coverage
			 * - se crea audit siendo auditor1.
			 */
			{
				text, positionIdMine, "auditor1", null
			},
			/*
			 * a) Functional requirements - 3.2. An actor who is
			 * authenticated as an auditor must be able to manage
			 * his or her audits. - Create. b) Negative test -
			 * Business rule: it can't be created by a not auditor user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta crear audit siendo company1.
			 */
			{
				text, positionIdMine, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 3.2. An actor who is
			 * authenticated as an auditor must be able to manage
			 * his or her audits. - Create.
			 * b) Negative test - Business rule: it can't be created by a not auditor user c)
			 * analysis of sentence coverage: 92.4%
			 * d) analysis of data coverage - se intenta crear audit siendo company1.
			 */
			{
				text, positionIdNotMine, "auditor1", java.lang.IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateCreateAudit((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreateAudit(final String text, final int positionId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Position position = this.positionService.findOne(positionId);
			final Date date = new Date();

			final Audit audit = this.auditService.create();
			audit.setDraftMode(true);
			audit.setScore(1);
			audit.setMoment(date);
			audit.setPosition(position);
			audit.setText("test");

			this.auditService.save(audit);

			this.auditService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverEdit() {
		final int auditIdMine = super.getEntityId("audit1");
		final int auditIdNotMine = super.getEntityId("audit2");
		final String text = "test";

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 3.2. An actor who
			 * is authenticated as an auditor must be able to manage
			 * his or her audits.. - Edit. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se edita audit siendo auditor1.
			 */
			{
				text, auditIdMine, "auditor1", null
			},
			/*
			 * a) Functional requirements - 3.2. An actor who
			 * is authenticated as an auditor must be able to
			 * manage his or her audits.. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a not auditor user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar audit siendo rookie1.
			 */
			{
				text, auditIdMine, "rookie1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements -3.2. An actor who
			 * is authenticated as an auditor must be able to
			 * manage his or her audits. - Edit. b) Negative test -
			 * Business rule: it can't be edited by a foreign auditor c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar audit ajeno siendo auditor1.
			 */
			{
				text, auditIdNotMine, "auditor1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 3.2. An actor who
			 * is authenticated as an auditor must be able to
			 * manage his or her audits.. - Edit. b) Negative test -
			 * Business rule: it can't be edited without a text c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - se intenta editar audit siendo auditor1 sin atributo text.
			 */
			{
				null, auditIdMine, "auditor1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateEditAudit((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEditAudit(final String text, final int auditId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Audit audit = this.auditService.findOne(auditId);
			audit.setText(text);
			this.auditService.save(audit);

			this.auditService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverDelete() {
		final int auditIdMine = super.getEntityId("audit1");
		final int auditIdNotMine = super.getEntityId("audit2");

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 3.2. An actor who
			 * is authenticated as an auditor must be able to manage
			 * his or her audits. - Delete. b) Positive test c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se elimina audit siendo auditor1.
			 */
			{
				auditIdMine, "auditor1", null
			},
			/*
			 * a) Functional requirements - 3.2. An actor who is authenticated as
			 * an auditor must be able to manage his or her audits. - Eliminar. b) Negative test -
			 * Business rule: it can't be deleted by a not auditor user c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta eliminar audit siendo rookie1.
			 */
			{
				auditIdMine, "rookie1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 3.2. An actor who is authenticated
			 * as an auditor must be able to manage his or her audits. - Delete. b) Negative test -
			 * Business rule: it can't be deleted by a foreign auditor c)
			 * analysis of sentence coverage: 89.7% d) analysis of data coverage
			 * - se intenta eliminar audit ajeno siendo auditor1.
			 */
			{
				auditIdNotMine, "auditor1", java.lang.IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateDeleteAudit((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateDeleteAudit(final int auditId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Audit audit = this.auditService.findOne(auditId);
			this.auditService.delete(audit);

			this.auditService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
