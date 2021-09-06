package com.tecky.awssdkjava.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.SecurityGroup;

@Service
public class EC2InstanceConfig {
	
	private final AmazonEC2 awsec2;

	@Autowired
	public EC2InstanceConfig(AmazonEC2 awsec2)
	{
		this.awsec2=awsec2;
	}
	
	public AmazonEC2 instanceWithConfig()
	{
//		DescribeSecurityGroupsRequest secGrpReq=new DescribeSecurityGroupsRequest().withGroupNames("awssdksec");
//		DescribeSecurityGroupsResult secGrpRes=awsec2.describeSecurityGroups(secGrpReq);
		SecurityGroup sg=new SecurityGroup().withGroupName("awssdksec");
		boolean checkSG=awsec2.describeSecurityGroups().getSecurityGroups().stream().anyMatch(s->{
			return s.getGroupName().equalsIgnoreCase(sg.getGroupName());
		});
		if(!checkSG) {
			CreateSecurityGroupRequest securityGroup=new CreateSecurityGroupRequest().withGroupName("awssdksec")
					.withDescription("Security Group for aws sdk");
			awsec2.createSecurityGroup(securityGroup);
			
			IpRange ipRange1=new IpRange().withCidrIp("0.0.0.0/0");
			
			IpPermission ipPermission1=new IpPermission().withIpv4Ranges(Arrays.asList(new IpRange[] {ipRange1}))
			       .withIpProtocol("tcp")
			       .withFromPort(80)
			       .withToPort(80);
			IpPermission ipPermission2=new IpPermission().withIpv4Ranges(Arrays.asList(new IpRange[] {ipRange1}))
			.withIpProtocol("tcp")
			.withFromPort(22)
			.withToPort(22);
			
			AuthorizeSecurityGroupIngressRequest authorizeSecIngressReq=new AuthorizeSecurityGroupIngressRequest()
			.withGroupName("awssdksec")
			.withIpPermissions(ipPermission1,ipPermission2);
			
			awsec2.authorizeSecurityGroupIngress(authorizeSecIngressReq);
		}
		
		//Create KeyPair
		KeyPair keyPair=new KeyPair().withKeyName("awssdkkeypair");
		boolean keyCheck=awsec2.describeKeyPairs().getKeyPairs().stream().anyMatch(k->{
			return k.getKeyName().equalsIgnoreCase(keyPair.getKeyName());
		});
		if(!keyCheck)
		{
			CreateKeyPairRequest createKeyPairReq=new CreateKeyPairRequest()
					.withKeyName("awssdkkeypair");
					
			CreateKeyPairResult createKeyPairRes=awsec2.createKeyPair(createKeyPairReq);
		}
		return awsec2;
	}

}
