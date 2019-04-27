
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PositionData;

@Component
@Transactional
public class PositionDataToStringConverter implements Converter<PositionData, String> {

	@Override
	public String convert(final PositionData p) {
		String result;

		if (p == null)
			result = null;
		else
			result = String.valueOf(p.getId());

		return result;
	}

}
