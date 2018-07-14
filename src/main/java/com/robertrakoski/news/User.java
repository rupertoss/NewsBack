package com.robertrakoski.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	private Long id;
	
	@Size(min = 6, max = 32)
//	@NotBlank
	@JsonProperty("username")
	private String username;
	
	@Size(min = 6, max = 32)
	@JsonIgnore
	private String password;
	
	@Column(name = "fav_category")
	@JsonProperty("favouriteCategory")
	private String favouriteNewsCategory;
	
	User() {
	}

	User(Long id, String username, String password, String favouriteNewsCategory) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.favouriteNewsCategory = favouriteNewsCategory;
	}

	Long getId() {
		return id;
	}

	String getUsername() {
		return username;
	}

	String getPassword() {
		return password;
	}

	String getFavouriteNewsCategory() {
		return favouriteNewsCategory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", favouriteNewsCategory="
				+ favouriteNewsCategory + "]";
	}
}
