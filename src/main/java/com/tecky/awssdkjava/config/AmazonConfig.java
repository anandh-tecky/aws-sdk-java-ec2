package com.tecky.awssdkjava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;

@Configuration
public class AmazonConfig {
	
	@Value("${AWS_ACCESS_KEY_ID}")
	private String access_key;
	
	@Value("${AWS_SECRET_ACCESS_KEY}")
	private String secret_key;
	
	@Bean
	public AmazonEC2 awsec2() {
		AWSCredentials awsCredentials=new BasicAWSCredentials(this.access_key, this.secret_key);
		AmazonEC2 ec2Client=AmazonEC2ClientBuilder.standard()
							.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
							.withRegion(Regions.AP_SOUTH_1)
							.build();
		return ec2Client;
	}
	
	

}
