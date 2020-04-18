package com.csye6225.spring2020.courseservice;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class BoardCreation implements RequestHandler<Map<String, String>, String> {
	@Override
	public String handleRequest(Map<String, String> input, Context context) {
		context.getLogger().log("Input: " + input);
		final Board board = new Board();
		board.setCourseId(input.get("courseId"));
		board.setAnnouncement("Creating the course");
		board.setPostDate(new Date().toString());
		String boardId=postBoard(board);
		context.getLogger().log("BoardId: " + boardId);
		return boardId;
	}

	private String postBoard(final Board board) {
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client
				.target("http://studentinformationsystem-env.2fju4uhtc2.us-west-2.elasticbeanstalk.com/webapi")
				.path("boards");
		final Board response = webTarget.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(board), Board.class);
		System.out.println(response.toString());
		return response != null ? response.getBoardId() : "";
	}
}

class Board {
    private String boardId;
    private String announcement;
    private String postDate;
    private String courseId;

	public Board() {

	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

}