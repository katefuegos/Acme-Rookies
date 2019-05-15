
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

@Access(AccessType.PROPERTY)
public class QueryAuxForm {

	// Attributes------------------------------------------------------------------
	private int		id;
	private String	name;
	private Long	count;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Long getCount() {
		return this.count;
	}

	public void setCount(final Long count) {
		this.count = count;
	}

}
