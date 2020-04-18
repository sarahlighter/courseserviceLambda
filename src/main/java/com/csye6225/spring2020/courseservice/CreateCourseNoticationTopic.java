package com.csye6225.spring2020.courseservice;
import java.util.Map;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeResult;


public class CreateCourseNoticationTopic implements RequestHandler<Map<String, String>, String>  {
	@Override
	public String handleRequest(Map<String, String> input, Context context) {
//		ProfileCredentialsProvider creds = new ProfileCredentialsProvider();
//		creds.getCredentials();
//    	ClientConfiguration cfg = new ClientConfiguration();
//		AmazonSNS snsClient=AmazonSNSClient.builder().withCredentials(creds).withRegion(Regions.US_WEST_2).withClientConfiguration(cfg).build();
		AmazonSNS snsClient=AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
		String courseId=input.get("courseId");
		context.getLogger().log("CourseId:"+courseId);
		context.getLogger().log("Creating Topic");
		
		final CreateTopicRequest createTopicRequest = new CreateTopicRequest(courseId+"CourseEnrollNotification");
		final CreateTopicResult createTopicResult = snsClient.createTopic(createTopicRequest);
        context.getLogger().log("Created Result:"+createTopicResult.toString());
        return createTopicResult.getTopicArn();
    }

}
