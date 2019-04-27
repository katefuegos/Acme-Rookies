package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Access(AccessType.PROPERTY)
public class ActorForm {

	// Attributes------------------------------------------------------------------

	private int id;
	private int version;

	private UserAccount userAccount;
	private boolean checkTerms;
	private String auth;

	private String name;
	private String surname;
	private String VATNumber;
	private String photo;
	private String email;
	private String phone;
	private String address;

	// Creditcard
	private String holderName;
	private String brandName;
	private String number;
	private int expirationMonth;
	private int expirationYear;
	private int CVVCode;

	// Atributos necesarios para company
	private String comercialName;

	// Constructor------------------------------------------------------------------

	public ActorForm() {
		super();
	}

	// Getter and
	// Setters------------------------------------------------------------

	public String getAuth() {
		return this.auth;
	}

	public void setAuth(final String auth) {
		this.auth = auth;
	}

	public boolean getCheckTerms() {
		return this.checkTerms;
	}

	public void setCheckTerms(final boolean checkTerms) {
		this.checkTerms = checkTerms;
	}

	@NotNull
	@Valid
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	public String getVATNumber() {
		return this.VATNumber;
	}

	public void setVATNumber(final String vATNumber) {
		this.VATNumber = vATNumber;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	public String getComercialName() {
		return this.comercialName;
	}

	public void setComercialName(final String comercialName) {
		this.comercialName = comercialName;
	}

	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@Pattern(regexp = "^(VISA|MASTER|DINNERS|AMEX)$")
	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@NotBlank
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	@NotNull
	public int getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@Range(min = 2019, max = 3000)
	@NotNull
	public int getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public int getCVVCode() {
		return this.CVVCode;
	}

	public void setCVVCode(final int cVVCode) {
		this.CVVCode = cVVCode;
	}

}
