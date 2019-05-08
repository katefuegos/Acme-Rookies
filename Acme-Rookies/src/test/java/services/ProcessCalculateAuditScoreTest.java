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
public class ProcessCalculateAuditScoreTest extends AbstractTest {

	// System Under Test ------------------------------------------------------
	@Autowired
	private CompanyService	companyService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverProcessCalculateAuditScore() {

		final Object testingData[][] = {
			/*
			 * a) Functional requirements 4.3. An actor who is authenticated as an administrator must be able to launch a process.
			 * b) Negative tests - Business rule: Only administrators can execute it.
			 * c) analysis of sentence coverage: 81.4% with eclemma
			 * d) analysis of data coverage.
			 */
			{
				null, IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements 4.3. An actor who is authenticated as an administrator must be able to launch a process.
			 * b) Positive tests -
			 * c) analysis of sentence coverage: 81.4% with eclemma
			 * d) analysis of data coverage.
			 * The actor in charge is: company1
			 */
			{
				"admin", null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			this.companyService.calculateAllAuditScore();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
