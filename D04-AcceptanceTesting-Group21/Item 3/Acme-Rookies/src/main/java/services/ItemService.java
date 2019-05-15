
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import security.LoginService;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	// Repository-----------------------------------------------

	@Autowired
	private ItemRepository	itemRepository;

	// Services-------------------------------------ItemService.java------------

	@Autowired
	private ProviderService	providerService;


	// Constructor----------------------------------------------

	public ItemService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Item create() {
		final Item item = new Item();

		item.setProvider(this.providerService.findByUseraccount(LoginService.getPrincipal()));

		return item;
	}

	public List<Item> findAll() {
		return this.itemRepository.findAll();
	}

	public Item findOne(final Integer itemId) {
		return this.itemRepository.findOne(itemId);
	}

	public Item save(final Item item) {
		Assert.notNull(item);
		this.check(item);
		final Item saved = this.itemRepository.save(item);
		return saved;
	}

	public void delete(final Item item) {
		Assert.notNull(item);
		this.check(item);
		this.itemRepository.delete(item);
	}

	public void flush() {
		this.itemRepository.flush();
	}

	public Collection<Item> findByProviderId(final int providerId) {
		Assert.notNull(providerId);
		return this.itemRepository.findByProviderId(providerId);
	}

	public void check(final Item item) {
		final Provider provider = this.providerService.findByUseraccount(LoginService.getPrincipal());
		Assert.notNull(provider);
		Assert.notNull(item);
		Assert.isTrue(item.getProvider().equals(provider));
	}

}
