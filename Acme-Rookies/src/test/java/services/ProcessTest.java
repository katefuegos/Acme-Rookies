/*
 * RegistrationTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProcessTest extends AbstractTest {

	// System Under Test ------------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;


	// Tests ------------------------------------------------------------------

	// 4.1. An actor who is authenticated as an administrator must be able to run a procedure.
	// To notify the existing users of the rebranding. 
	// The system must guarantee that the process is run only once.
	@Test
	public void driverProcessRunOnlyOnce() {

		final Object testingData[][] = {
			/*
			 * a) Functional requirements 4.1 - To notify the existing users of the rebranding.
			 * b) Negative tests - Business rule: Only administrators can execute it.
			 * c) analysis of sentence coverage: 88.4% with eclemma
			 * d) analysis of data coverage. Attribute processExecuted = True
			 */
			{
				null, 1, IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements 4.1 - To notify the existing users of the rebranding.
			 * b) Positive tests -
			 * c) analysis of sentence coverage: 88.4% with eclemma
			 * d) analysis of data coverage. Attribute processExecuted = True
			 * The actor in charge is: company1
			 */
			{
				"admin", 1, null
			},
			/*
			 * a) Functional requirements 4.1 - To notify the existing users of the rebranding.
			 * b) Negative tests - Business rule: The system must guarantee that the process is run only once.
			 * c) analysis of sentence coverage: 88.4% with eclemma
			 * d) analysis of data coverage. Attribute processExecuted = True
			 */
			{
				"admin", 2, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String username, final int numExecutions, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			for (int i = 0; i < numExecutions; i++)
				this.configurationService.launchProcessOnlyOnce();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
