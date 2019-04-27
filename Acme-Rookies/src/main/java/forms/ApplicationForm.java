package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import domain.Curricula;
import domain.Position;

@Access(AccessType.PROPERTY)
public class ApplicationForm {

	// Attributes------------------------------------------------------------------
	private int id;
	private String textAnswer;
	private String linkAnswer;
	private Position position;
	private Curricula curricula;

	@NotNull
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}

	@URL
	public String getLinkAnswer() {
		return linkAnswer;
	}

	public void setLinkAnswer(String linkAnswer) {
		this.linkAnswer = linkAnswer;
	}

	@NotNull
	@Valid
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@NotNull
	@Valid
	public Curricula getCurricula() {
		return curricula;
	}

	public void setCurricula(Curricula curricula) {
		this.curricula = curricula;
	}

}
