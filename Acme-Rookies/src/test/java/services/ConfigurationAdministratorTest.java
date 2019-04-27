package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Configuration;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationAdministratorTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private ConfigurationService configurationService;

	// Tests ------------------------------------------------------------------
	@Test
	public void driverManage() {

		final Object testingData[][] = {
		/*
		 * a) Functional requirements - Edit configuration. b)
		 * Positive test c) analysis of sentence coverage: 93.2% d) analysis of data
		 * coverage - se edita configuración del sistema siendo admin.
		 */
		{ 1, "admin", null },
		/*
		 * a) Functional requirements - Edit configuration. b)
		 * Negative test - Business rule: It can not be edited if it's not from an
		 * admin. c) analysis of sentence coverage: 93.2% d) analysis of data
		 * coverage se intenta editar configuracion siendo company1.
		 */
		{ 1, "company1", java.lang.IllegalArgumentException.class },
		/*
		 * a) Functional requirements - Edit configuration. b)
		 * Negative test - Business rule: It can not be edited if it's not from an
		 * admin. c) analysis of sentence coverage: 93.2% d) analysis of data
		 * coverage se intenta editar configuracion sin estar logeado.
		 */
		{ 1, null, java.lang.IllegalArgumentException.class },
		/*
		 * a) Functional requirements - Edit configuration. b)
		 * Negative test - Business rule: It can not be edited if it's not from an
		 * admin. c) analysis of sentence coverage: 93.2% d) analysis of data
		 * coverage se intenta editar configuracion sin systemName.
		 */
		{ 0, "admin", javax.validation.ConstraintViolationException.class } };

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateManage((int) testingData[i][0],
						(String) testingData[i][1],
						(Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateManage(final int systemName, final String username,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			Configuration configuration = configurationService.findOne();
			
			if (systemName == 1) {
				configuration.setSystemName("TEST");
			} else {
				configuration.setSystemName(null);
			}
			this.configurationService.save(configuration);

			super.unauthenticate();
			this.configurationService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
