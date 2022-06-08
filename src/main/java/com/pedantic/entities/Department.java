package com.pedantic.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Entity
@NamedQuery(name = Department.FIND_BY_ID, query = "select d from Department d where d.id = :id and d.userEmail = :email")
@NamedQuery(name = Department.FIND_BY_NAME, query = "select d from Department d where d.departmentName = :name and d.userEmail = :email")
@NamedQuery(name = Department.LIST_DEPARTMENTS, query = "select d from Department d where  d.userEmail = :email")
@Access(AccessType.FIELD)
public class Department extends AbstractEntity {
	public static final String FIND_BY_ID = "Department.findById";
	public static final String FIND_BY_NAME = "Department.findByName";
	public static final String LIST_DEPARTMENTS = "Department.listDepartments";

	@NotEmpty(message = "Department name must be set")
	private String departmentName;

	@OneToMany(mappedBy = "department")
	@OrderBy("basicSalary DESC, dateOfBirth desc")
	private Collection<Employee> employees = new ArrayList<>();

	@OneToMany
	@MapKey(name = "id")
	@JoinTable(
			name = "department_to_employees",
			inverseJoinColumns = @JoinColumn(name = "employee_id"))
	private Map<Long, Employee> employeesById = new HashMap<>();

	@ElementCollection
	@CollectionTable(name = "department_employee_rank")
	@MapKeyJoinColumn(name = "employeeee_id")
	@Column(name = "rank")
	private Map<Employee, Integer> rankPerEmployee = new HashMap<>();

	@Transient
	private String departmentCode;


	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Collection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}
}
