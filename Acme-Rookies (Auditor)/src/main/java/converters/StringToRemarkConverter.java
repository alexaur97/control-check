
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RemarkRepository;
import domain.Remark;

@Component
@Transactional
public class StringToRemarkConverter implements Converter<String, Remark> {

	@Autowired
	RemarkRepository	remarkRepository;


	@Override
	public Remark convert(final String text) {
		Remark result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.remarkRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
