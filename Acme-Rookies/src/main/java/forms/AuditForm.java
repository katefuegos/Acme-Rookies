
package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Position;

@Access(AccessType.PROPERTY)
public class AuditForm {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String		text;
	private int			score;
	private Date		moment;
	private boolean		draftMode;
	private int			id;

	// Relationships ---------------------------------------------------------
	private Position	position;


	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@NotNull
	@Range(min = 0, max = 10)
	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

}