package com.pedantic.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class AppState {
	@PostConstruct
	private void init() {
		System.out.println("PostConstruct: Hashcode for this AppState instance" + this.hashCode());
	}

	@PreDestroy
	private void destroy() {
		System.out.println("PreDestroy: Hashcode for this AppState instance" + this.hashCode());
	}
}
