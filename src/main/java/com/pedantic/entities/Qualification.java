package com.pedantic.entities;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Qualification {

	private String school;
	private LocalDate completion;
	private String title;
}
