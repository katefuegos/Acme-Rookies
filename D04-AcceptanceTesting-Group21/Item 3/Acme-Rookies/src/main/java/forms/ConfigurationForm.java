
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Access(AccessType.PROPERTY)
public class ConfigurationForm {

	//Attributes------------------------------------------------------------------

	private int					id;

	private int					countryCode;

	private int					finderCacheTime;

	private int					finderMaxResults;

	private String				bannerr;

	private String				welcomeMessageES;

	private String				welcomeMessageEN;
	
	private String 				systemName;

	//Constructor------------------------------------------------------------------

	public ConfigurationForm() {

		super();

	}

	//Getter and Setters------------------------------------------------------------

	@NotBlank
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	public int getId() {

		return this.id;

	}

	public void setId(final int id) {

		this.id = id;

	}

	@Range(min = 0, max = 999)
	public int getCountryCode() {

		return this.countryCode;

	}

	public void setCountryCode(final int countryCode) {

		this.countryCode = countryCode;

	}

	@Range(min = 1, max = 24)
	public int getFinderCacheTime() {

		return this.finderCacheTime;

	}

	public void setFinderCacheTime(final int finderCacheTime) {

		this.finderCacheTime = finderCacheTime;

	}

	@Range(min = 0, max = 100)
	public int getFinderMaxResults() {

		return this.finderMaxResults;

	}

	public void setFinderMaxResults(final int finderMaxResults) {

		this.finderMaxResults = finderMaxResults;

	}

	@NotBlank
	@URL
	public String getBannerr() {

		return this.bannerr;

	}

	public void setBannerr(final String bannerr) {

		this.bannerr = bannerr;

	}

	@NotBlank
	public String getWelcomeMessageES() {

		return this.welcomeMessageES;

	}

	public void setWelcomeMessageES(final String welcomeMessageES) {

		this.welcomeMessageES = welcomeMessageES;

	}

	@NotBlank
	public String getWelcomeMessageEN() {

		return this.welcomeMessageEN;

	}

	public void setWelcomeMessageEN(final String welcomeMessageEN) {

		this.welcomeMessageEN = welcomeMessageEN;

	}
}
