package com.tecky.awssdkjava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.tecky.awssdkjava.service.EC2Management;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class EC2RestController {

	private final EC2Management ec2Manager;
	
	@Autowired
	private EC2RestController(EC2Management ec2Manager)
	{
		this.ec2Manager=ec2Manager;
	}
	
	@GetMapping("/launch")
	public String launchInstance() {
		return ec2Manager.launchInstance();
	}
	
	@GetMapping("/start")
	public String startInstance() {
		ec2Manager.startInstance();
		return "Started";
	}
	
	@GetMapping("/stop")
	public String stopInstance() {
		ec2Manager.stopInstance();
		return "Stopped";
	}
	
	@GetMapping("/status")
	public List<Instance> instanceStatus() {
		DescribeInstancesResult instanceRes=ec2Manager.instanceStatus();
		//String result=instanceRes.getReservations().get(0).getInstances().get(1).getState().getName();
		return instanceRes.getReservations().get(0).getInstances();
	}
}
