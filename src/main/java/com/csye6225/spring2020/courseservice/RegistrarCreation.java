package com.csye6225.spring2020.courseservice;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RegistrarCreation implements RequestHandler<Map<String, String>, String> {

	@Override
	public String handleRequest(Map<String, String> input, Context context) {
		context.getLogger().log("Input: " + input);
		final Registrar registrar = new Registrar();
		registrar.setRegistrationId(input.get("courseId"));
		registrar.setOfferingId(input.get("courseId"));
		registrar.setOfferingType("Course");
		registrar.setDepartment(input.get("department"));
		registrar.setPerUnitPrice(3000);
		return postRegistrar(registrar);
	}

	private String postRegistrar(final Registrar registrar) {
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client
				.target("http://studentinformationsystem-env.2fju4uhtc2.us-west-2.elasticbeanstalk.com/webapi")
				.path("registerOffering");
		final Registrar response = webTarget.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(registrar), Registrar.class);
		return response != null ? response.getRegistrationId() : "";
	}
}

class Registrar {
	private String registrationId;
	private String offeringId;
	private String offeringType;
	private String department;
	private Integer perUnitPrice;

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	public String getOfferingType() {
		return offeringType;
	}

	public void setOfferingType(String offeringType) {
		this.offeringType = offeringType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getPerUnitPrice() {
		return perUnitPrice;
	}

	public void setPerUnitPrice(Integer perUnitPrice) {
		this.perUnitPrice = perUnitPrice;
	}

}