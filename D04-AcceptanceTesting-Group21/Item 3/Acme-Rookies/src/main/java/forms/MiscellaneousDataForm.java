
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.Curricula;

@Access(AccessType.PROPERTY)
public class MiscellaneousDataForm {

	// Attributes------------------------------------------------------------------

	private int			id;
	private String		text;
	private String		attachments;

	private Curricula	curricula;


	// Constructor------------------------------------------------------------------

	public MiscellaneousDataForm() {
		super();
	}

	// Getter and
	// Setters------------------------------------------------------------

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@NotBlank
	@URL
	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Curricula getCurricula() {
		return this.curricula;
	}
	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;

	}

}
