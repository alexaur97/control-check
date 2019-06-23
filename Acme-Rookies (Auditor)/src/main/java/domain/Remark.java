
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
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

	private Auditor	auditor;
	private Audit	audit;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Audit getAudit() {
		return this.audit;
	}

	public void setAudit(final Audit audit) {
		this.audit = audit;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return this.auditor;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	@NotBlank
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String date) {

		this.ticker = date;

	}

	@Size(min = 1, max = 100)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	@Pattern(regexp = "^(DRAFT|FINAL)$")
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
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

}
