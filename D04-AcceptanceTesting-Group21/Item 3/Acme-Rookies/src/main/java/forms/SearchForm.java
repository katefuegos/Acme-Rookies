
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

@Access(AccessType.PROPERTY)
public class SearchForm {

	// Attributes------------------------------------------------------------------

	private String	keyword;


	// Constructor------------------------------------------------------------------

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	// Getter and Setters------------------------------------------------------------

	public SearchForm() {
		super();
	}

	public String getKeyword() {
		return this.keyword;
	}

}
