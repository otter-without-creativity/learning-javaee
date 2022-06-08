package com.pedantic.service;

import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import java.io.Serializable;

@Stateful
public class StatefulUserSession implements Serializable {

	public String getCurrentUsername() {
		return "";
	}

	@PrePassivate
	private void passivate() {

	}

	@PostActivate
	private void activate() {

	}
}
