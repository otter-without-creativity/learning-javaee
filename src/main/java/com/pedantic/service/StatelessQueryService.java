package com.pedantic.service;

import com.pedantic.entities.Department;
import com.pedantic.entities.Employee;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class StatelessQueryService {

	@Inject
	private EntityManager entityManager;


	@PostConstruct
	private void init() {
		System.out.println("PostConstruct: Hashcode for this StatelessQueryService instance: " + this.hashCode());
	}

	@PreDestroy
	private void destroy() {
		System.out.println("PreDestroy: Hashcode for this StatelessQueryService instance: " + this.hashCode());
	}

	public Department findDepartmentById(final long id) {
		return entityManager.find(Department.class, id);
	}

	public Employee findEmployeeById(final long id) {
		return entityManager.find(Employee.class, id);
	}

	public List<Employee> getEmployees() {
		return null;
	}

	public List<Department> getDepartments() {
		return null;
	}
}
