package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ScoreControllerRA {

	private Long nonExistingMovieId;
	private String adminUsername, adminPassword;
	private String adminToken;

	private Map<String, Object> saveScoreInstance;

	@BeforeEach
	void setUp() throws JSONException {

		nonExistingMovieId = 100L;

		adminUsername = "maria@gmail.com";
		adminPassword = "123456";

		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);

		saveScoreInstance = new HashMap<>();
		saveScoreInstance.put("movieId", 1);
		saveScoreInstance.put("score", 4);
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {

		saveScoreInstance.put("movieId", nonExistingMovieId);
		JSONObject newScore = new JSONObject(saveScoreInstance);

		given()
				.header("Content-type", "application/json")
				.header("Authorization", "Bearer " + adminToken)
				.body(newScore.toString()).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when()
				.put("/scores")
				.then()
				.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {

		saveScoreInstance.put("movieId", null);
		JSONObject newScore = new JSONObject(saveScoreInstance);

		given()
				.header("Content-type", "application/json")
				.header("Authorization", "Bearer " + adminToken)
				.body(newScore.toString()).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when()
				.put("/scores")
				.then()
				.statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {

		saveScoreInstance.put("score", -5);
		JSONObject newScore = new JSONObject(saveScoreInstance);

		given()
				.header("Content-type", "application/json")
				.header("Authorization", "Bearer " + adminToken)
				.body(newScore.toString()).contentType(ContentType.JSON).accept(ContentType.JSON)
				.when()
				.put("/scores")
				.then()
				.statusCode(422);
	}
}
