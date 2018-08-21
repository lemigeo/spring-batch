package com.batch.service;

import org.springframework.batch.core.Step;

public interface IService {
	Step step1();
	Step step2();
	Step step3();	
	String getName();
}
