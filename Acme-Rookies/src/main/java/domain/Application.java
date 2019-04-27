
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(name = "ID1", columnList = "status")

})
public class Application extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private Date	publicationMoment;
	private Date	submissionMoment;
	private String	status;
	private String	textAnswer;
	private String	linkAnswer;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getSubmissionMoment() {
		return this.submissionMoment;
	}

	public void setSubmissionMoment(final Date submissionMoment) {
		this.submissionMoment = submissionMoment;
	}

	@NotBlank
	@Pattern(regexp = "^((PENDING)|(SUBMITTED)|(ACCEPTED)|(REJECTED))$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getTextAnswer() {
		return this.textAnswer;
	}

	public void setTextAnswer(final String textAnswer) {
		this.textAnswer = textAnswer;
	}

	@URL
	public String getLinkAnswer() {
		return this.linkAnswer;
	}

	public void setLinkAnswer(final String linkAnswer) {
		this.linkAnswer = linkAnswer;
	}


	// Relationships ---------------------------------------------------------
	private Problem		problem;
	private Position	position;
	private Curricula	curricula;
	private Hacker		hacker;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(final Problem problem) {
		this.problem = problem;
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
	@Valid
	@ManyToOne(optional = false)
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return this.hacker;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}

}
