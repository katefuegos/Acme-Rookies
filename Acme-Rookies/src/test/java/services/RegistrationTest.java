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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import forms.ActorForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegistrationTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private UserAccountService	accountService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverEditPersonalData() {

		final Integer id = this.getEntityId("company1");

		final domain.Company company = this.companyService.findOne(id);

		final ActorForm actorForm1 = this.constructActor(company.getUserAccount(), company.getId(), "COMPANY", company.getName(), company.getSurnames(), company.getVATNumber(), company.getPhoto(), company.getEmail(), company.getPhone(),
			company.getAddress(), "PP", company.getCreditCard().getHolderName(), company.getCreditCard().getBrandName(), company.getCreditCard().getNumber(), company.getCreditCard().getExpirationMonth(), company.getCreditCard().getExpirationYear(),
			company.getCreditCard().getCVVCode());

		final ActorForm actorForm2 = this.constructActor(company.getUserAccount(), company.getId(), "COMPANY", company.getName(), company.getSurnames(), company.getVATNumber(), company.getPhoto(), company.getEmail(), company.getPhone(),
			company.getAddress(), null, company.getCreditCard().getHolderName(), company.getCreditCard().getBrandName(), company.getCreditCard().getNumber(), company.getCreditCard().getExpirationMonth(), company.getCreditCard().getExpirationYear(),
			company.getCreditCard().getCVVCode());

		final Object testingData[][] = {
			/*
			 * a) Functional requirements 9.2 - Edit personal data b)
			 * Positive tests c) analysis of sentence coverage: 93% with
			 * eclemma d) analysis of data coverage. The actor company1
			 * is being modified with the following data: comercialName="PP"The
			 * actor in charge is: company1
			 */
			{
				actorForm1, "company1", null
			},
			/*
			 * a) Functional requirements 9.2 - Edit personal data b)
			 * Negative tests - Business rule: Attribute comercialName must not be
			 * null c) analysis of sentence coverage: 93% with eclemma d)
			 * analysis of data coverage. The actor company1 is being
			 * modified with the following data: area=area1, title=null The
			 * actor in charge is: company1
			 */
			{
				actorForm2, "company1", javax.validation.ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((ActorForm) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void driverCompany() {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		UserAccount userAccount = null;
		// Company
		userAccount = this.accountService.create("Joseph", encoder.encodePassword("jojo12345", null), "COMPANY");
		final ActorForm actorFormB1 = this.constructActor(userAccount, 0, "COMPANY", "Joseph", "Joestar", "ES00005", "http://www.photo.com", "jojo@hotmail.com", "654789321", "Calle falsa 123", "JoJo's Bizarre Adventure", "JOJO", "VISA",
			"1111222233334444", 9, 2020, 123);

		userAccount = this.accountService.create("Joseph", encoder.encodePassword("jojo12345", null), "COMPANY");
		final ActorForm actorFormB2 = this.constructActor(userAccount, 0, "COMPANY", "Joseph", "Joestar", "ES00005", "http://www.photo.com", "jojo@hotmail.com", "654789321", "Calle falsa 123", null, "JOJO", "VISA", "1111222233334444", 9, 2020, 123);

		final Object testingData[][] = {
			/*
			 * a) Functional requirements 7.1 - Register as a company
			 * b) Positive tests
			 * c) analysis of sentence coverage: 93% with eclemma
			 * d) analysis of data coverage. The new company is being modified with
			 * the following data: useraccount.username=Joseph,
			 * useraccount.password=jojo12345,
			 * useraccount.authority=COMPANY
			 * ,name="Joseph",middleName="Joestar",surname= "Joestar",photo=
			 * "http://www.photo.com",email= "jojo@hotmail.com",phone=
			 * "654789321",address= "Calle falsa 123",title=
			 * "JoJo's Bizarre Adventure"
			 * The actor in charge is: unauthenticate
			 */
			{
				actorFormB1, null, null
			},
			/*
			 * a) Functional requirements 7.1 - Register as a company
			 * b) Negative tests - Business rule: Attribute title must not be null
			 * c) analysis of sentence coverage: 93% with eclemma
			 * d) analysis of data coverage. The new company is being modified with the
			 * following data: useraccount.username=Joseph,
			 * useraccount.password=jojo12345,
			 * useraccount.authority=COMPANY
			 * ,name="Joseph",middleName="Joestar",surname= "Joestar",photo=
			 * "http://www.photo.com",email= "jojo@hotmail.com",phone=
			 * "654789321",address= "Calle falsa 123",title= null
			 * The actor in charge is: unauthenticate
			 */
			{
				actorFormB2, null, javax.validation.ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((ActorForm) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void driverHacker() {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		UserAccount userAccount = null;

		// HAcker
		userAccount = this.accountService.create("Joseph", encoder.encodePassword("jojo12345", null), "HACKER");
		final ActorForm actorFormM1 = this.constructActor(userAccount, 0, "HACKER", "Joseph", "Joestar", "ES00005", "http://www.photo.com", "jojo@hotmail.com", "654789321", "Calle falsa 123", "JoJo's Bizarre Adventure", "JOJO", "VISA", "1111222233334444",
			9, 2020, 123);

		userAccount = this.accountService.create("Joseph", encoder.encodePassword("jojo12345", null), "HACKER");
		final ActorForm actorFormM2 = this.constructActor(userAccount, 0, "HACKER", "Joseph", "Joestar", "ES00005", "photo.com", "jojo@hotmail.com", "654789321", "Calle falsa 123", ".", "JOJO", "VISA", "1111222233334444", 9, 2020, 123);

		final Object testingData[][] = {

			/*
			 * a) Functional requirements 7.1 - Register as a hacker
			 * b) Positive tests
			 * c) analysis of sentence coverage: 93% with eclemma
			 * d) analysis of data coverage. The new company is being modified with
			 * the following data: useraccount.username=Joseph,
			 * useraccount.password=jojo12345,
			 * useraccount.authority=HACKER
			 * ,name="Joseph",middleName="Joestar",surname= "Joestar",photo=
			 * "http://www.photo.com",email= "jojo@hotmail.com",phone=
			 * "654789321",address= "Calle falsa 123",title=
			 * "JoJo's Bizarre Adventure"
			 * The actor in charge is: unauthenticate
			 */
			{
				actorFormM1, null, null
			},

			/*
			 * a) Functional requirements 7.1 - Register as a hacker
			 * b) Negative tests - Business rule: Attribute photo must be url
			 * c) analysis of sentence coverage: 93% with eclemma
			 * d) analysis of data coverage. The new hacker is being modified with the
			 * following data: useraccount.username=Joseph,
			 * useraccount.password=jojo12345,
			 * useraccount.authority=HACKER
			 * ,name="Joseph",middleName="Joestar",surname= "Joestar",photo=photo.com",email= "jojo@hotmail.com",phone=
			 * "654789321",address= "Calle falsa 123",title= "."
			 * The actor in charge is: unauthenticate
			 */
			{
				actorFormM2, null, javax.validation.ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((ActorForm) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void driverAdministrator() {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		UserAccount userAccount = null;

		// Administrator
		userAccount = this.accountService.create("Joseph", encoder.encodePassword("jojo12345", null), "ADMIN");
		final ActorForm actorFormA1 = this.constructActor(userAccount, 0, "ADMIN", "Joseph", "Joestar", "ES00005", "http://www.photo.com", "jojo@hotmail.com", "654789321", "Calle falsa 123", "JoJo's Bizarre Adventure", "JOJO", "VISA", "1111222233334444",
			9, 2020, 123);

		//		userAccount = this.accountService.create("Joseph", encoder.encodePassword("jojo12345", null), "ADMIN");
		//		final ActorForm actorFormA2 = this.constructActor(userAccount, 0, "ADMIN", "Joseph", "Joestar", "ES00005", "http://www.photo.com", "jojo@hotmail.com", "654789321", "Calle falsa 123", "JoJo's Bizarre Adventure", "JOJO", "VISA", "1111222233334444",
		//			9, 2020, 123);

		final Object testingData[][] = {

			/*
			 * a) Functional requirements 11.1 - Create a user accounts for
			 * a new administrator b) Positive tests c) analysis of sentence
			 * coverage: 93% with eclemma d) analysis of data coverage. The
			 * new admin is being modified with the following data:
			 * useraccount.username=Joseph,
			 * useraccount.password=jojo12345,
			 * useraccount.authority=HACKER
			 * ,name="Joseph",middleName="Joestar",surname= "Joestar",photo=
			 * "http://www.photo.com",email= "jojo@hotmail.com",phone=
			 * "654789321",address= "Calle falsa 123",title=
			 * "JoJo's Bizarre Adventure"
			 * The actor in charge is: admin
			 */
			{
				actorFormA1, "admin", null
			},
			/*
			 * a) Functional requirements 11.1 - Create a user accounts for
			 * a new administrator b) Positive tests c) analysis of sentence
			 * coverage: 93% with eclemma d) analysis of data coverage. The
			 * new admin is being modified with the following data:
			 * useraccount.username=Joseph,
			 * useraccount.password=jojo12345,
			 * useraccount.authority=HACKER
			 * ,name="Joseph",middleName="Joestar",surname= "Joestar",photo=
			 * "http://www.photo.com",email= "jojo@hotmail.com",phone=
			 * "654789321",address= "Calle falsa 123",title=
			 * "JoJo's Bizarre Adventure"
			 * The actor in charge is: unauthenticate
			 */

			{
				actorFormA1, null, IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((ActorForm) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final ActorForm actor, final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			this.actorService.update(actor);
			super.unauthenticate();
			this.actorService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	private ActorForm constructActor(final UserAccount userAccount, final int id, final String auth, final String name, final String surname, final String VATNumber, final String photo, final String email, final String phone, final String address,
		final String comercialName, final String holderName, final String brandName, final String number, final int expirationMonth, final int expirationYear, final int cVVCode) {
		final ActorForm result = new ActorForm();

		final int version = 0;

		result.setId(id);
		result.setVersion(version);
		result.setUserAccount(userAccount);
		result.setAuth(auth);

		result.setName(name);
		result.setSurname(surname);
		result.setVATNumber(VATNumber);
		result.setPhoto(photo);
		result.setEmail(email);
		result.setPhone(phone);
		result.setAddress(address);
		result.setComercialName(comercialName);

		result.setHolderName(holderName);
		result.setBrandName(brandName);
		result.setNumber(number);
		result.setExpirationMonth(expirationMonth);
		result.setExpirationYear(expirationYear);
		result.setCVVCode(cVVCode);

		return result;
	}

}
