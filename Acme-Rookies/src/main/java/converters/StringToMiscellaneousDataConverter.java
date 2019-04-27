
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MiscellaneousDataRepository;
import domain.MiscellaneousData;

@Component
@Transactional
public class StringToMiscellaneousDataConverter implements Converter<String, MiscellaneousData> {

	@Autowired
	MiscellaneousDataRepository	miscellaneousDataRepository;


	@Override
	public MiscellaneousData convert(final String text) {
		MiscellaneousData result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.miscellaneousDataRepository.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
