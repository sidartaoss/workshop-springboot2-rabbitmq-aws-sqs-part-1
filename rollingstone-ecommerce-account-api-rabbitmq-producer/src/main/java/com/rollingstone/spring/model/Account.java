package com.rollingstone.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SqlResultSetMapping(name = "accountPartialMapping", classes = {
		@ConstructorResult(targetClass = AccountDTO.class, columns = {
				@ColumnResult(name = "id", type = Long.class),
				@ColumnResult(name = "account_number"),
				@ColumnResult(name = "account_name"),
				@ColumnResult(name = "user_name"),
				@ColumnResult(name = "first_name"),
				@ColumnResult(name = "last_name"),
				@ColumnResult(name = "sex"),
				@ColumnResult(name = "member_type"),
				@ColumnResult(name = "registration_date")
		})
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "AccountDaoRepository.getAccountByID",
			query = "select acc.id, acc.account_number, acc.account_name, u.user_name, "
					+ "u.first_name, u.last_name, u.sex, u.member_type, "
					+ "u.registration_date "
					+ "from rollingstone_account acc, "
					+ "		rollingstone_user u "
					+ "where "
					+ "		acc.user_profile_id = u.id "
					+ "and acc.id = :id ", resultSetMapping = "accountPartialMapping")
})
@Entity
@Table(name = "ROLLINGSTONE_ACCOUNT", schema = "rsecommerce_cqrs")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "ACCOUNT_NUMBER", nullable = false)
	private String accountNumber;
	
	@Column(name = "ACCOUNT_NAME", nullable = false)
	private String accountName;
	
	@OneToOne
	@JoinColumn(name = "USER_PROFILE_ID", nullable = false)
	private User user;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private Set<Address> addresses = new HashSet<>();
	
	public Account() {
		super();
	}

	public Account(long id, String accountNumber, String accountName, User user, Set<Address> addresses) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.accountName = accountName;
		this.user = user;
		this.addresses = addresses;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNumber=" + accountNumber + ", accountName=" + accountName + ", user="
				+ user + ", addresses=" + addresses + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
