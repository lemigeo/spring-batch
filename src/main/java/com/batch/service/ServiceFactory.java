package com.batch.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {
	
	@Autowired
	private List<IService> services;
	
	private static final Map<String, IService> serviceMap = new ConcurrentHashMap<>();
	
	@PostConstruct
	public void init() {
		for(IService svc : services) {
			serviceMap.put(svc.getName(), svc);
		}
	}
	
	public static IService getService(String name) {
		return serviceMap.get(name);
	}
}
