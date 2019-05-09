
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Auditor;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditAsignAuditorTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private PositionService	positionService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverManage() {
		final int positionIdFree = super.getEntityId("position1");
		final int positionIdNotFree = super.getEntityId("position3");

		final Object testingData[][] = {
			/*
			 * a) Functional requirements - 3.1. An actor who is authenticated as an auditor
			 * must be able to self-assign a position to audit it. - Asign. b) Positive test c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - Asignar una position libre siendo auditor
			 */
			{
				positionIdFree, "auditor1", null
			},
			/*
			 * a) Functional requirements - 3.1. An actor who is authenticated as an auditor
			 * must be able to self-assign a position to audit it.. - Create. b) Negative test -
			 * Business rule: it can't be created by a not company user c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - Asignar una position libre siendo company.Error
			 */
			{
				positionIdFree, "company1", java.lang.IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements -3.1. An actor who is authenticated as an auditor
			 * must be able to self-assign a position to audit it. - Create. b) Negative test -
			 * Business rule: it can't be created for a foreign position c)
			 * analysis of sentence coverage: 92.4% d) analysis of data coverage
			 * - Asignar una position que no está libre siendo auditor.Error
			 */
			{
				positionIdNotFree, "auditor1", java.lang.IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateAsign((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateAsign(final int positionId, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			final Auditor auditor = this.auditorService.findAdminByUsername(username);
			final Position position = this.positionService.findOne(positionId);
			this.auditService.asign(position, auditor);
			super.unauthenticate();
			this.auditService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
