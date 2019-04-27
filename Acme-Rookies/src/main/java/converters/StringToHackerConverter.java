
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.HackerRepository;
import domain.Hacker;

@Component
@Transactional
public class StringToHackerConverter implements Converter<String, Hacker> {

	@Autowired
	HackerRepository	hackerRepository;


	@Override
	public Hacker convert(final String text) {
		Hacker result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.hackerRepository.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
