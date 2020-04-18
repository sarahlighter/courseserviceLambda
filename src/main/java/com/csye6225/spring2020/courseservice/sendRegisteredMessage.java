package com.csye6225.spring2020.courseservice;

import java.util.Map;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class sendRegisteredMessage implements RequestHandler<Map<String, String>, String> {

    @Override
    public String handleRequest(Map<String, String> input, Context context) {
//		ProfileCredentialsProvider creds = new ProfileCredentialsProvider();
//		creds.getCredentials();
//    	ClientConfiguration cfg = new ClientConfiguration();
//		AmazonSNS snsClient=AmazonSNSClient.builder().withCredentials(creds).withRegion(Regions.US_WEST_2).withClientConfiguration(cfg).build();
//    	
//    	// Publish a message to an Amazon SNS topic.
    	AmazonSNS snsClient=AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    	context.getLogger().log(input.toString());
    	final String msg = "Course "+input.get("courseName")+" has been registered";
    	final String TopicArn = input.get("notificationTopic");
    	context.getLogger().log(msg);
    	context.getLogger().log(context.toString());
    	context.getLogger().log("TopicARN:"+TopicArn);
		final PublishRequest publishRequest = new PublishRequest(TopicArn, msg);
    	final PublishResult publishResponse = snsClient.publish(publishRequest);

    	// Print the MessageId of the message.
    	System.out.println("MessageId: " + publishResponse.getMessageId());
        return "Hello from Lambda!";
    }

}
