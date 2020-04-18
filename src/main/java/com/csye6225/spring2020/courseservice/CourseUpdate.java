package com.csye6225.spring2020.courseservice;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CourseUpdate implements RequestHandler<Map<String, String>, String> {

	@Override
	public String handleRequest(Map<String, String> input, Context context) {
		
		//set board id && set topicARN 
		context.getLogger().log("Input: " + input);
		if (input.get("boardId") == null || input.get("boardId").isEmpty()) {
			return "";
		}
		if (input.get("notificationTopic") == null || input.get("notificationTopic").isEmpty()) {
			return "";
		}
		String courseId=input.get("courseId");
		context.getLogger().log("courseId: " + courseId);

		context.getLogger().log("Try to get course: " + courseId);
		final Course course = getCourse(courseId);
		context.getLogger().log("Course: " + course.toString());
		if (course == null) {
			return "";
		}
		course.setBoardId(input.get("boardId"));
		course.setNotificationTopic(input.get("notificationTopic"));
		return putCourse(course);
	}

	private Course getCourse(final String courseId) {
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client
				.target("http://studentinformationsystem-env.2fju4uhtc2.us-west-2.elasticbeanstalk.com/webapi")
				.path("courses/" + courseId);
		final Course response = webTarget.request().accept(MediaType.APPLICATION_JSON).get(Course.class);
		return response;
	}

	private String putCourse(final Course course) {
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client
				.target("http://studentinformationsystem-env.2fju4uhtc2.us-west-2.elasticbeanstalk.com/webapi")
				.path("courses/" + course.getCourseId());
		final Course response = webTarget.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.put(Entity.json(course), Course.class);
		return response != null ? response.getCourseId() : "";
	}

}

class Course {
	private String courseId;
	private String courseName;
	private String professorId;
	private String taId;
	private String department;
	private String programId;
	private String boardId;
	private Set<String> roster;
	private String notificationTopic;
	public Course() {

	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getProfessorId() {
		return professorId;
	}
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}
	public String getTaId() {
		return taId;
	}
	public void setTaId(String taId) {
		this.taId = taId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public Set<String> getRoster() {
		return roster;
	}
	public void setRoster(Set<String> roster) {
		this.roster = roster;
	}
	public String getNotificationTopic() {
		return notificationTopic;
	}
	public void setNotificationTopic(String notificationTopic) {
		this.notificationTopic = notificationTopic;
	}

	
}