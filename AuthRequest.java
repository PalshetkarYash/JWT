package com.example.project.dto;
import java.util.ArrayList;
import java.util.List;

import com.example.project.model.Address;
import com.example.project.model.LoyaltyStatus;
import com.example.project.model.Role;

public class AuthRequest {
    private String username; // or email
    private String password;
    private String email;
    private String phoneNo;
    private Integer loyaltyPoints;
    private Role role;
    private LoyaltyStatus loyalty;
    private List<Address> addresses = new ArrayList<>();
    

    public AuthRequest() {
        // default constructor
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public LoyaltyStatus getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(LoyaltyStatus loyalty) {
		this.loyalty = loyalty;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}
