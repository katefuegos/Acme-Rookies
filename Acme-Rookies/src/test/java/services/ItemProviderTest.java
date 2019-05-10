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
import domain.Item;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemProviderTest extends AbstractTest {

	// System Under Test ------------------------------------------------------

	@Autowired
	private ItemService	itemService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driverManageItem() {

		final String name = "Nuevo item";
		final String description = "...";
		final String link = "https://www.oneLink.com";
		final String picture = "https://www.instagram.com";

		final Object testingData[][] = {

			/*
			 * a) Functional requirements - 10.1 - Create a new Item by provider
			 * b) Positive tests -
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "Nuevo item", description="...", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				name, description, link, picture, "provider1", null, "CREATE", null
			},
			/*
			 * a) Functional requirements - 10.1 - Create a new Item by provider
			 * b) Negative tests - the name can not be blank
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "", description="...", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				"", description, link, picture, "provider1", null, "CREATE", javax.validation.ConstraintViolationException.class
			},
			/*
			 * a) Functional requirements - 10.1 - Create a new Item by provider
			 * b) Negative tests - the description can not be blank
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "Nuevo item", description="", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				"Nuevo item", "", link, picture, "provider1", null, "CREATE", javax.validation.ConstraintViolationException.class
			},
			/*
			 * a) Functional requirements - 10.1 - Edit a Item by provider
			 * b) Positive tests -
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "Nuevo item", description="...", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				name, description, link, picture, "provider1", "item1", "EDIT", null
			},
			/*
			 * a) Functional requirements - 10.1 - Edit a Item by provider
			 * b) Negative tests - the description can not be blank
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "Nuevo item", description="...", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				name, "", link, picture, "provider1", "item1", "EDIT", javax.validation.ConstraintViolationException.class
			},
			/*
			 * a) Functional requirements - 10.1 - Edit a Item by provider
			 * b) Negative tests - Only the owner can modify his item
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "Nuevo item", description="...", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				name, "", link, picture, "provider1", "item2", "EDIT", IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 10.1 - Edit a Item by provider
			 * b) Positive tests -
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "Nuevo item", description="...", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				name, description, link, picture, "provider1", "item1", "DELETE", null
			},
			/*
			 * a) Functional requirements - 10.1 - Delete a Item by provider
			 * b) Negative tests - You can not delete a null item
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "Nuevo item", description="...", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				name, "", link, picture, "provider1", null, "DELETE", IllegalArgumentException.class
			},
			/*
			 * a) Functional requirements - 10.1 - Delete a Item by provider
			 * b) Negative tests - Only the owner can modify his item
			 * c) analysis of sentence coverage:
			 * d) analysis of data coverage. The item is being modified with the
			 * following data: name= "Nuevo item", description="...", link="https://www.oneLink.com", picture="https://www.instagram.com"
			 * The actor in charge is: provider1
			 */
			{
				name, description, link, picture, "provider1", "item2", "DELETE", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateManage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(Class<?>) testingData[i][7]);
			} catch (final Throwable oops) {
				//System.out.println(oops.getMessage());
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateManage(final String name, final String description, final String link, final String picture, final String username, final String nameItem, final String action, final Class<?> expected) {
		Class<?> caught;
		final int itemId;

		caught = null;
		try {
			if (username != null)
				super.authenticate(username);
			else
				super.unauthenticate();

			Item item = null;

			if (nameItem != null) {
				itemId = super.getEntityId(nameItem);
				item = this.itemService.findOne(itemId);
			}

			switch (action) {
			case "CREATE":
				item = this.itemService.create();
				item.setName(name);
				item.setDescription(description);
				item.setLink(link);
				item.setPicture(picture);

				this.itemService.save(item);
				break;
			case "EDIT":
				item.setName(name);
				item.setDescription(description);
				item.setLink(link);
				item.setPicture(picture);

				this.itemService.save(item);
				break;
			case "DELETE":
				this.itemService.delete(item);

				break;
			default:
				break;
			}

			super.unauthenticate();
			this.itemService.flush();

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
