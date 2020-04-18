package com.csye6225.spring2020.courseservice;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

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
import com.amazonaws.services.sns.model.SubscribeRequest;

public class subscribeToCourseService implements RequestHandler<Map<String, String>, String> {

    @Override
    public String handleRequest(Map<String, String> input, Context context) {
		// Subscribe an email endpoint to an Amazon SNS topic.
    	String professorId = input.get("professorId");
    	Professor prof=getProfessor(professorId);
    	if(prof==null) {
    		return "CANNOT FIND PROFESSOR";
    	}
    	String email=getProfessorEmail(prof);
    	subscribe(email,context);
        return "Send subscribe Notification";
    }
    
    private Professor getProfessor(String professorId) {
    	Professor prof=new Professor();
    	Client client = ClientBuilder.newClient();
    	WebTarget webTarget = client
				.target("http://studentinformationsystem-env.2fju4uhtc2.us-west-2.elasticbeanstalk.com/webapi")
				.path("professors/" + professorId);
    	Professor response = webTarget.request().accept(MediaType.APPLICATION_JSON).get(Professor.class);
    	System.out.println(response);
		return response;
    }

    private String getProfessorEmail(Professor prof) {
    	return prof.getEmail();
    }
    
    private void subscribe(String email,Context context) {
    	final SubscribeRequest subscribeRequest = new SubscribeRequest(context.getInvokedFunctionArn(), "email", email);
//    	ProfileCredentialsProvider creds = new ProfileCredentialsProvider();
//		creds.getCredentials();
//    	ClientConfiguration cfg = new ClientConfiguration();
//		AmazonSNS snsClient=AmazonSNSClient.builder().withCredentials(creds).withRegion(Regions.US_WEST_2).withClientConfiguration(cfg).build();
    	AmazonSNS snsClient=AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
		
		snsClient.subscribe(subscribeRequest);

    	// Print the request ID for the SubscribeRequest action.
    	System.out.println("SubscribeRequest: " + snsClient.getCachedResponseMetadata(subscribeRequest));
    	
    	System.out.println("To confirm the subscription, check your email.");
    }

}
class Professor {
    private String professorId;
    private String firstName;
    private String lastName;
    private String department;
    private String joiningDate;
    private String email;
	public String getProfessorId() {
		return professorId;
	}
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
}

