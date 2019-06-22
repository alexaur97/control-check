package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Remark extends DomainEntity {

	
	private Company company;
	private Application application;

	private String ticker;
	private Date publicationMoment;
	private String body;
	private String picture;
	private String mode;

	@ManyToOne(optional = false)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne(optional = false)
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
	@NotBlank
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Date getPublicationMoment() {
		return publicationMoment;
	}

	public void setPublicationMoment(Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@NotBlank
	@Size(max = 100)
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@NotNull
	@Pattern(regexp = "^(DRAFT|FINAL)$")
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
