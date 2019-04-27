
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(name = "ID1", columnList = "draftmode"), @Index(name = "ID2", columnList = "cancelled")

})
public class Position extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String	ticker;
	private String	title;
	private String	description;
	private Date	deadline;
	private String	skills;
	private String	profile;
	private String	tecnologies;
	private Double	salary;
	private boolean	draftmode;

	private Boolean	cancelled;


	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^\\w{4}-\\d{4}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@NotBlank
	public String getSkills() {
		return this.skills;
	}

	public void setSkills(final String skills) {
		this.skills = skills;
	}

	@NotBlank
	public String getProfile() {
		return this.profile;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}

	@NotBlank
	public String getTecnologies() {
		return this.tecnologies;
	}

	public void setTecnologies(final String tecnologies) {
		this.tecnologies = tecnologies;
	}

	@NotNull
	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(final Double salary) {
		this.salary = salary;
	}

	@NotNull
	public boolean isDraftmode() {
		return this.draftmode;
	}

	public void setDraftmode(final boolean draftmode) {
		this.draftmode = draftmode;
	}

	public Boolean getCancelled() {
		return this.cancelled;
	}

	public void setCancelled(final Boolean cancelled) {
		this.cancelled = cancelled;
	}


	// Relationships ---------------------------------------------------------
	private Company	company;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}
}
