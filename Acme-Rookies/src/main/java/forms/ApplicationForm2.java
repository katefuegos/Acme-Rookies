package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Company;
import domain.Curricula;
import domain.Hacker;
import domain.Position;
import domain.Problem;

@Access(AccessType.PROPERTY)
public class ApplicationForm2 {

	// Attributes------------------------------------------------------------------
	private int id;
	private String textAnswer;
	private String linkAnswer;
	private Position position;
	private Curricula curricula;
	private Hacker hacker;
	private Company company;
	private Problem problem;
	private Date publicationMoment;
	private Date submissionMoment;
	private String status;

	@Valid
	@NotNull
	public Hacker getHacker() {
		return hacker;
	}

	public void setHacker(Hacker hacker) {
		this.hacker = hacker;
	}

	@Valid
	@NotNull
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Valid
	@NotNull
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getPublicationMoment() {
		return publicationMoment;
	}

	public void setPublicationMoment(Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getSubmissionMoment() {
		return submissionMoment;
	}

	public void setSubmissionMoment(Date submissionMoment) {
		this.submissionMoment = submissionMoment;
	}

	@NotBlank
	@Pattern(regexp = "^((PENDING)|(SUBMITTED)|(ACCEPTED)|(REJECTED))$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
