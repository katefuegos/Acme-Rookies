
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.Curricula;
import domain.Position;

@Access(AccessType.PROPERTY)
public class ApplicationForm {

	// Attributes------------------------------------------------------------------
	private int			id;
	private String		textAnswer;
	private String		linkAnswer;
	private Position	position;
	private Curricula	curricula;


	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	public String getTextAnswer() {
		return this.textAnswer;
	}

	public void setTextAnswer(final String textAnswer) {
		this.textAnswer = textAnswer;
	}

	@URL
	@NotBlank
	public String getLinkAnswer() {
		return this.linkAnswer;
	}

	public void setLinkAnswer(final String linkAnswer) {
		this.linkAnswer = linkAnswer;
	}

	@NotNull
	@Valid
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@NotNull
	@Valid
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

}
