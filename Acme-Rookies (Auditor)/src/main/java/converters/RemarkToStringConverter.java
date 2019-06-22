
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Remark;

@Component
@Transactional
public class RemarkToStringConverter implements Converter<Remark, String> {

	@Override
	public String convert(final Remark remark) {
		String result;
		if (remark == null)
			result = null;
		else
			result = String.valueOf(remark.getId());
		return result;
	}

}
