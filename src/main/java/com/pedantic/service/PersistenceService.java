package com.pedantic.service;

import com.pedantic.entities.Department;
import com.pedantic.entities.Employee;
import com.pedantic.entities.ParkingSpace;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

//@DataSourceDefinition(
//		name = "java:app/Payroll/MyDS",
//		className = "org.postgresql.Driver",
//		databaseName = "d6as7pke7dh208",
//		url = "jdbc:postgresql://ec2-63-35-156-160.eu-west-1.compute.amazonaws.com:5432/d6as7pke7dh208",
//		user = "",
//		password = "")
@Stateless
public class PersistenceService {

	@Inject
	EntityManager entityManager;

	@Inject
	StatelessQueryService queryService;

	//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveDepartment(final Department department) {
		entityManager.persist(department);
	}

	public void saveEmployee(final Employee employee) {
		entityManager.persist(employee);
	}

	public void saveEmployee(final Employee employee, final ParkingSpace parkingSpace) {
		employee.setParkingSpace(parkingSpace);
		entityManager.persist(employee);
	}

	public void removeParkingSpace(final ParkingSpace parkingSpace) {
		entityManager.remove(parkingSpace);
	}

	public void removeParkingSpace(final long employeeId) {
		Employee employee = queryService.findEmployeeById(employeeId);
		ParkingSpace parkingSpace = employee.getParkingSpace();
		employee.setParkingSpace(null);

		entityManager.remove(parkingSpace);
	}

	public void updateDepartment(final Department department) {
		entityManager.merge(department);
	}
}
