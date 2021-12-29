package com.kanrisoft.kanri.domain;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "created_date")
	private Instant createdDate;
	
	@Column(name = "status")
	private Status status;
	
	@Column(name = "verified")
	private boolean verified;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "password")
	private String password;
	
	@Lob
	@Column(name = "user_image")
	private byte[] userImage;

	@Column(name = "user_image_content_type")
	private String userImageContentType;
	
	  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinTable(name = "USER_ROLES",
	            joinColumns = {
	            @JoinColumn(name = "USER_ID")
	            },
	            inverseJoinColumns = {
	            @JoinColumn(name = "ROLE_ID") })
	    private Set<Role> roles;
	
	
//	reportsTo*
//	spaceId*

}
