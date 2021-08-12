package requests;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import utils.PathProvider;

public class FindPetById {
	
public PathProvider pathProvider = new PathProvider();
	

public Response findPetByIdResponse(int id) {
	Response res = given()
				.log()
				.all()
				.when()
				.get(pathProvider.provideFindPetByIdPath(id))
				.then()
				.assertThat()
				.statusCode(200)
				.and()
				.extract()
				.response();
	return res;
	}

public String getPetName(Response res) {
	String petName = res.then()
						.contentType(ContentType.JSON)
						.extract()
						.path("name");
	return petName;
	}

}
