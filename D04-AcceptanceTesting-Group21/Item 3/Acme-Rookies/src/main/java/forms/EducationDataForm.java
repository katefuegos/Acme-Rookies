
package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Curricula;

@Access(AccessType.PROPERTY)
public class EducationDataForm {

	// Attributes------------------------------------------------------------------

	private int			id;
	private String		degree;
	private String		institution;
	private int			mark;
	private Date		startDate;
	private Date		endDate;

	private Curricula	curricula;


	// Constructor------------------------------------------------------------------

	public EducationDataForm() {
		super();
	}

	// Getter and
	// Setters------------------------------------------------------------

	@NotBlank
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(final String degree) {
		this.degree = degree;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getMark() {
		return this.mark;
	}

	public void setMark(final int mark) {
		this.mark = mark;
	}

	public Curricula getCurricula() {
		return this.curricula;
	}
	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;

	}

}
