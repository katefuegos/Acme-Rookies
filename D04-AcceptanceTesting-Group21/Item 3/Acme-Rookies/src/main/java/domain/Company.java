
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS

	private String	comercialName;
	private Double	auditScore;


	@NotBlank
	public String getComercialName() {
		return this.comercialName;
	}

	public void setComercialName(final String comercialName) {
		this.comercialName = comercialName;
	}

	public Double getAuditScore() {
		return this.auditScore;
	}

	public void setAuditScore(final Double auditScore) {
		this.auditScore = auditScore;
	}

	// Relationships ---------------------------------------------------------

}
