package com.tecky.awssdkjava.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;

@Service
public class EC2Management {
	
	private AmazonEC2 instance;
	
	private String instanceId=null;
	
	@Autowired
	private EC2Management(EC2InstanceConfig instanceCofig) {
		this.instance=instanceCofig.instanceWithConfig();
	}
	
	public String launchInstance() {
		try {
			RunInstancesRequest runInstanceRequest=new RunInstancesRequest().withImageId("ami-0a23ccb2cdd9286bb")
					.withInstanceType(InstanceType.T2Micro)
					.withMinCount(1)
					.withMaxCount(1)
					.withKeyName("awssdkkeypair")
					.withSecurityGroups("awssdksec");

			instanceId=instance.runInstances(runInstanceRequest).getReservation().getInstances().get(0).getInstanceId();
		}
		catch(AmazonServiceException e)
		{
			throw e;
		}
		
		return instanceId;
	}
	
	public void startInstance() {
		if(instanceId==null)
		{
			Optional<Instance> oldinstance=instance.describeInstances().getReservations().get(0).getInstances().stream().filter(s->s.getInstanceId().equalsIgnoreCase("i-01cf7a4da840ffbc7")).findFirst();
			instanceId=oldinstance.get().getInstanceId();
		}
		try {
			StartInstancesRequest startInstReq=new StartInstancesRequest()
					.withInstanceIds(instanceId);
			instance.startInstances(startInstReq);
		}
		catch(AmazonServiceException e)
		{
			throw e;
		}
	}
	
	public void stopInstance() {
		if(instanceId==null)
		{
			Optional<Instance> oldinstance=instance.describeInstances().getReservations().get(0).getInstances().stream().filter(s->s.getInstanceId().equalsIgnoreCase("i-01cf7a4da840ffbc7")).findFirst();
			instanceId=oldinstance.get().getInstanceId();
		}
		try {
			StopInstancesRequest stopInstReq=new StopInstancesRequest()
					.withInstanceIds(instanceId);
			instance.stopInstances(stopInstReq);
		}
		catch(AmazonServiceException e)
		{
			throw e;
		}
	}
	
	public DescribeInstancesResult instanceStatus() {
		DescribeInstancesResult response;
		try {
			DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
	        response = instance.describeInstances(describeInstancesRequest);
		}
		catch(AmazonServiceException e)
		{
			throw e;
		}
		
        return response;
	}
}
