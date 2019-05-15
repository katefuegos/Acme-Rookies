
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String	keyword;
	private Integer	minSalary;
	private Date	deadlineMin;
	private Date	deadlineMax;
	private Date	lastUpdate;


	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public Integer getMinSalary() {
		return this.minSalary;
	}

	public void setMinSalary(final Integer minSalary) {
		this.minSalary = minSalary;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDeadlineMin() {
		return this.deadlineMin;
	}

	public void setDeadlineMin(final Date deadlineMin) {
		this.deadlineMin = deadlineMin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDeadlineMax() {
		return this.deadlineMax;
	}

	public void setDeadlineMax(final Date deadlineMax) {
		this.deadlineMax = deadlineMax;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Past
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	// Relationships ---------------------------------------------------------
	private Collection<Position>	positions;


	@NotNull
	@Valid
	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

}
