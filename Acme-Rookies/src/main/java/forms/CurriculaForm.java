
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.Hacker;

@Access(AccessType.PROPERTY)
public class CurriculaForm {

	// Attributes------------------------------------------------------------------

	private int		id;
	private String	fullName;
	private String	statement;
	private String	phoneNumber;
	private String	githubProfile;
	private String	linkedInProfile;
	private Hacker	hacker;


	// Constructor------------------------------------------------------------------

	public CurriculaForm() {
		super();
	}

	// Getter and
	// Setters------------------------------------------------------------

	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotBlank
	@URL
	public String getGithubProfile() {
		return this.githubProfile;
	}

	public void setGithubProfile(final String githubProfile) {
		this.githubProfile = githubProfile;
	}

	@NotBlank
	@URL
	public String getLinkedInProfile() {
		return this.linkedInProfile;
	}

	public void setLinkedInProfile(final String linkedInProfile) {
		this.linkedInProfile = linkedInProfile;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public Hacker getHacker() {
		return this.hacker;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;

	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

}
