
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.CurriculaService;
import domain.Curricula;

@Component
@Transactional
public class StringToCurriculaConverter implements Converter<String, Curricula> {

	@Autowired
	CurriculaService	curriculaService;


	@Override
	public Curricula convert(final String text) {
		Curricula result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.curriculaService.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
