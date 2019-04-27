
package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Access(AccessType.PROPERTY)
public class PositionForm {

	// Attributes------------------------------------------------------------------
	private String	ticker;

	private String	title;
	private String	description;
	private Date	deadline;
	private String	skills;
	private String	profile;
	private String	tecnologies;
	private Double	salary;
	private boolean	draftmode;

	private int		id;


	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
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

}
