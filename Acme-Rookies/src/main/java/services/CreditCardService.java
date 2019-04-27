
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	// Repository-----------------------------------------------

	@Autowired
	private CreditCardRepository	creditCardRepository;


	// Services-------------------------------------------------

	// Constructor----------------------------------------------

	public CreditCardService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public domain.CreditCard create() {
		final domain.CreditCard creditCard = new CreditCard();

		return creditCard;
	}

	public List<CreditCard> findAll() {
		return this.creditCardRepository.findAll();
	}

	public CreditCard findOne(final Integer creditCardId) {
		return this.creditCardRepository.findOne(creditCardId);
	}

	public domain.CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);

		final Date currentDate = new Date();
		@SuppressWarnings("deprecation")
		final int month = currentDate.getMonth() + 1;
		@SuppressWarnings("deprecation")
		final int year = currentDate.getYear() + 1900;

		@SuppressWarnings("unused")
		final boolean b = (creditCard.getExpirationYear() > year) || (creditCard.getExpirationYear() == year && creditCard.getExpirationMonth() > month);
		Assert.isTrue((creditCard.getExpirationYear() > year) || (creditCard.getExpirationYear() == year && creditCard.getExpirationMonth() > month), "actor.creditcard.error.date.invalid");

		final CreditCard saved = this.creditCardRepository.save(creditCard);
		return saved;
	}

	public void delete(final CreditCard creditCard) {
		this.creditCardRepository.delete(creditCard);
	}

	// Other Methods--------------------------------------------

}
