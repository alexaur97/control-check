
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Remark extends DomainEntity {

	private String	ticker;
	private Date	moment;
	private String	body;
	private String	picture;
	private String	mode;


	@NotBlank
	@Pattern(regexp = "yyyy-MM-dd")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String date) {

		this.ticker = date;

	}

	@Size(min = 1, max = 1000)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	@Pattern(regexp = "^DRAFT|FINAL$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getMode() {
		return this.mode;
	}

	public void setMode(final String mode) {
		this.mode = mode;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

}
