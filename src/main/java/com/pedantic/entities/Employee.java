/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedantic.entities;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * @author Seeraj
 */
@Entity
@NamedQuery(name = Employee.FIND_BY_ID, query = "select e from Employee e where e.id = :id and e.userEmail = :email")
@NamedQuery(name = Employee.FIND_BY_NAME, query = "select e from Employee e where e.fullName = :name and e.userEmail " +
		"= :email")
@NamedQuery(name = Employee.LIST_EMPLOYEES, query = "select  e from Employee e where e.userEmail = :email order by e" +
		".fullName")
@NamedQuery(name = Employee.FIND_PAST_PAYSLIP_BY_ID,
		query = "select p from Employee e join e.pastPayslips p where e.id = :employeeId and e.userEmail =:email and" +
				" " +
				"p" +
				".id =:payslipId and p.userEmail = :email")
@NamedQuery(name = Employee.GET_PAST_PAYSLIPS, query = "select p from Employee e inner join e.pastPayslips p where e" +
		".id = :employeeId and e.userEmail=:email")
//@Table(name = "Employee", schema = "HR")
public class Employee extends AbstractEntity {

	public static final String FIND_BY_ID = "Employee.findById";
	public static final String FIND_BY_NAME = "Employee.findByName";
	public static final String LIST_EMPLOYEES = "Employee.listEmployees";
	public static final String FIND_PAST_PAYSLIP_BY_ID = "Employee.findPastPayslipById";
	public static final String GET_PAST_PAYSLIPS = "Employee.getPastPayslips";

	private int age;

	@NotNull(message = "Basic salary must be set")
	private BigDecimal basicSalary;

	@Past(message = "Date of birth must be in the past")
	@JsonbDateFormat(value = "yyyy-MM-dd")
	private LocalDate dateOfBirth; //yyyy-MM-dd

	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType;

	@NotEmpty(message = "Name cannot be empty")
	@Basic
	private String fullName;

	@NotNull(message = "Hired date must be set")
	@JsonbDateFormat(value = "yyyy-MM-dd")
	@PastOrPresent(message = "Hired date must be in the past or present")
	private LocalDate hiredDate;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] picture;

	@Embedded
	private Address address;

	@ElementCollection
	@CollectionTable(name = "employee_to_phone_numbers")
	@MapKeyColumn(name = "phone_number_type")
	@Column(name = "phone_number")
	private Map<String, Integer> phoneNumbers = new HashMap<>();

	@ElementCollection
	@CollectionTable(name = "employee_to_phone_numbers_typed")
	@MapKeyColumn(name = "phone_number_type")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "phone_number")
	private Map<PhoneType, Integer> phoneNumbersTyped = new HashMap<>();

	@ElementCollection
	@CollectionTable(
			name = "employee_to_qualifications",
			joinColumns = @JoinColumn(name = "emp_id")
	)
	private Collection<Qualification> qualifications = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "employee_to_nicknames")
	@Column(name = "nickies")
	private Collection<String> nicknames = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "dept_id")
	private Department department;

	@ManyToOne
	private Employee reportsTo;

	@OneToMany
	@JoinTable(
			name = "employee_to_subordinates",
			joinColumns = @JoinColumn(name = "boss_id"),
			inverseJoinColumns = @JoinColumn(name = "subordinate_id")
	)
	private Set<Employee> subordinates = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "current_payslip_id")
	private Payslip currentPayslip;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.PERSIST)
	private ParkingSpace parkingSpace;

	@ManyToMany(mappedBy = "employees")
	private Collection<Project> projects = new ArrayList<>();

	@OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinTable(
			name = "employee_to_allowances",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "allowance_id")
	)
	private Set<Allowance> employeeAllowances = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Payslip> pastPayslips = new ArrayList<>();


	@PrePersist
	private void init() {
		this.age = Period.between(dateOfBirth, LocalDate.now()).getYears();
	}

	public Employee getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(Employee reportsTo) {
		this.reportsTo = reportsTo;
	}

	public Set<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(Set<Employee> subordinates) {
		this.subordinates = subordinates;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public EmploymentType getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Payslip getCurrentPayslip() {
		return currentPayslip;
	}

	public void setCurrentPayslip(Payslip currentPayslip) {
		this.currentPayslip = currentPayslip;
	}

	public Collection<Payslip> getPastPayslips() {
		return pastPayslips;
	}

	public void setPastPayslips(Collection<Payslip> pastPayslips) {
		this.pastPayslips = pastPayslips;
	}

	public LocalDate getHiredDate() {
		return hiredDate;
	}

	public void setHiredDate(LocalDate hiredDate) {
		this.hiredDate = hiredDate;
	}

	public Set<Allowance> getEmployeeAllowances() {
		return employeeAllowances;
	}

	public void setEmployeeAllowances(Set<Allowance> employeeAllowances) {
		this.employeeAllowances = employeeAllowances;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Map<String, Integer> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(Map<String, Integer> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public Map<PhoneType, Integer> getPhoneNumbersTyped() {
		return phoneNumbersTyped;
	}

	public void setPhoneNumbersTyped(Map<PhoneType, Integer> phoneNumbersTyped) {
		this.phoneNumbersTyped = phoneNumbersTyped;
	}

	public Collection<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(Collection<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public Collection<String> getNicknames() {
		return nicknames;
	}

	public void setNicknames(Collection<String> nicknames) {
		this.nicknames = nicknames;
	}

	public ParkingSpace getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(ParkingSpace parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public Collection<Project> getProjects() {
		return projects;
	}

	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}
}
