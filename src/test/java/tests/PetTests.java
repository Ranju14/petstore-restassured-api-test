package tests;

import static com.jayway.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import models.PostByName;
import requests.FindPetById;
import utils.Constants;
import utils.PathProvider;

public class PetTests extends PathProvider{
	
	PathProvider pathProvider = new PathProvider();
	
	@BeforeTest
	public void setBaseUri() {
		RestAssured.baseURI = Constants.BASE_URL;
	}
	
	@DataProvider
	public Object[][] petID() {
		return new Object[][]{
				{1},
		};
	}
	
	@Test(dataProvider="petID")
	public void displayPetNameByPetID(int id) {
		
		//get response of Find pet by ID
		FindPetById petByID = new FindPetById();		
		Response res = petByID.findPetByIdResponse(id);
		System.out.println(res.asString());
		
		//Extract pet name from the response
		String petName = petByID.getPetName(res);
		System.out.println("Pet Name By ID :"+petName);
		
	}
	
	@DataProvider 
	public Object[][] petName() {
		return new Object[][]{
				{"1","Grizz"}
		};
	}
	
	@Test(dataProvider="petName")
	public void updatePetName(String id, String petName) {
		PostByName postName= new PostByName();
		postName.setId(id);
		postName.setName(petName);
		
		Response res = given().when()
				.basePath("{resourcePath}")
				.pathParam("resourcePath", "pet")
				.contentType(ContentType.JSON)
				.body(postName)
				.put();
		
		System.out.println("put response:"+res.asString());
		String updatedPetName = res.then().contentType(ContentType.JSON).extract().path("name");
		System.out.println("Updated petName is :"+updatedPetName);
		Assert.assertEquals(updatedPetName, petName);
				
	}
	
	@DataProvider
	public Object[][] deletePetID() {
		return new Object[][]{
				{10},
		};
	}
	
	@Test(dataProvider="deletePetID")
	public void deletePet(int id) {
		Response res = given().when().delete(pathProvider.provideFindPetByIdPath(id));
		Assert.assertEquals(res.getStatusCode(), 200);
		System.out.println("deleted response:"+ res.asString());
	}
}
