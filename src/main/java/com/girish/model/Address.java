package com.girish.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="address")
public class Address { 
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int addressID;
	
	@Column(name = "door_no")
    private String doorNo;
	
	@Column(name = "street")
    private String street;
	
	@Column(name = "city")
    private String city;
	
	@Column(name = "employeeid")
    private String employeeId;		
	

}