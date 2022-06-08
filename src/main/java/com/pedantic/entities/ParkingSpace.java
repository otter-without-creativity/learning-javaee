package com.pedantic.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ParkingSpace extends AbstractEntity {

	private String parkingLotNumber;

	@OneToOne
	private Employee employee;
}
