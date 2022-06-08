package com.pedantic.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.Collection;

@Entity
public class Project extends AbstractEntity {

	private String projectName;
	private LocalDate startDate;
	private LocalDate endDate;

	@ManyToMany
	@JoinTable(
			name = "PROJECTS_TO_EMPLOYEES",
			joinColumns = @JoinColumn(name = "PROJ_ID"),
			inverseJoinColumns = @JoinColumn(name = "EMP_ID"))
	private Collection<Employee> employees;
}
