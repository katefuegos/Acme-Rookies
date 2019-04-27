
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.Position;

@Access(AccessType.PROPERTY)
public class ProblemForm {

	// Attributes------------------------------------------------------------------

	private int			id;
	private String		title;
	private String		statement;
	private String		hint;
	private String		attachments;
	private boolean		draftMode;

	private Position	position;


	// Constructor------------------------------------------------------------------

	public ProblemForm() {
		super();
	}

	// Getter and
	// Setters------------------------------------------------------------

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getHint() {
		return this.hint;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	@NotBlank
	@URL
	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotNull
	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Position getPosition() {
		return this.position;
	}
	public void setPosition(final Position position) {
		this.position = position;

	}

}
