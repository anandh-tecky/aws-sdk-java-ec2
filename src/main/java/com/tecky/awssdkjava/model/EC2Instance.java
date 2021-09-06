package com.tecky.awssdkjava.model;

import java.util.List;

import com.amazonaws.services.ec2.model.Instance;

public class EC2Instance {
	
	private List<Instance> instances;

	public List<Instance> getInstances() {
		return instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}
	
	

}
