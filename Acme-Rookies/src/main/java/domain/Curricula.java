package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String fullName;
	private String statement;
	private String phoneNumber;
	private String githubProfile;
	private String linkedinprofile;
	private boolean copy;

	@NotBlank
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	@NotBlank
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotBlank
	@URL
	public String getGithubProfile() {
		return githubProfile;
	}

	public void setGithubProfile(String githubProfile) {
		this.githubProfile = githubProfile;
	}

	@NotBlank
	@URL
	public String getLinkedinprofile() {
		return linkedinprofile;
	}

	public void setLinkedinprofile(String linkedinprofile) {
		this.linkedinprofile = linkedinprofile;
	}

	@NotNull
	public boolean isCopy() {
		return copy;
	}

	public void setCopy(boolean copy) {
		this.copy = copy;
	}

	// Relationships ---------------------------------------------------------
	private Hacker hacker;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return hacker;
	}

	public void setHacker(Hacker hacker) {
		this.hacker = hacker;
	}

}
