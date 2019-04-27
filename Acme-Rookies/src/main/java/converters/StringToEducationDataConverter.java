
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.EducationDataRepository;
import domain.EducationData;

@Component
@Transactional
public class StringToEducationDataConverter implements Converter<String, EducationData> {

	@Autowired
	EducationDataRepository	educationDataRepository;


	@Override
	public EducationData convert(final String text) {
		EducationData result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.educationDataRepository.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
