package com.batch.service;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AService implements IService {
	
	@Autowired
    private StepBuilderFactory factory;
	
	private String name;
	
	public AService() {
		this.name = "A";
	}
	
	public String getName() {
		return this.name;
	}
	
	public Step step1() {
		return this.factory.get("step1").tasklet(task1()).build();
	}
	
	public Step step2() {
		return this.factory.get("step2").tasklet(task2()).build();
	}
	
	public Step step3() {
		return this.factory.get("step3").tasklet(task3()).build();
	}
		
	private Tasklet task1() {
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext ctx) {
				System.out.println("A task 1");
				return RepeatStatus.FINISHED;
			}
		};	
	}
	
	private Tasklet task2() {
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext ctx) {
				System.out.println("A task 2");
				return RepeatStatus.FINISHED;
			}
		};	
	}
	
	private Tasklet task3() {
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext ctx) {
				System.out.println("A task 3");
				return RepeatStatus.FINISHED;
			}
		};	
	}
}
