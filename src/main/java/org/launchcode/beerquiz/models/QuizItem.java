package org.launchcode.beerquiz.models;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.launchcode.BreweryDb;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


/*
 * Represents one quiz item with relevant fields for guessing & answering --> a random beer from the breweryDb API
 */

public class QuizItem {
	
	private final String item, beerName, abv, ibu, description, breweryName, lat, lon, website, img, styleId, styleName;
	
	static JsonParser jp = new JsonParser();
	
	// size of http://api.brewerydb.com/v2/styles
    static final int STYLE_MIN = 1;
    static final int STYLE_MAX = 170;
	
	
	public QuizItem(int item) throws JsonIOException, JsonSyntaxException, IOException {
		
		if (item == 0) this.item = "A"; 
		else if (item == 1) this.item = "B"; 
		else if (item == 2) this.item = "C"; 
		else this.item = "D";
		
		int randomStyleId = QuizItem.STYLE_MIN + (int) (Math.random() * (QuizItem.STYLE_MAX - QuizItem.STYLE_MIN) + 1);
		
		// represents query for 1 random beer of the random style w/ brewery info included
		URL url = new URL("http://api.brewerydb.com/v2/beers?key=" + BreweryDb.API_KEY + "&styleId=" + randomStyleId
				+ "&order=random&randomCount=1&withBreweries=Y");
		
		JsonElement json = jp.parse(new InputStreamReader(url.openStream()));
		
		// beer info in "data" list --> get first item
		JsonObject beer = json.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
		
		// brewery info in "beer" object --> get first item of brewery list (usually only one result per brewery)
		JsonObject brewery = beer.get("breweries").getAsJsonArray().get(0).getAsJsonObject();
		
		// get to parsing --> cheers!
		beerName = beer.get("name").getAsString();
		abv = beer.get("abv").getAsString().isEmpty() ? "n/a" :beer.get("abv").getAsString();
		ibu = beer.get("ibu").getAsString().isEmpty() ? "n/a" :beer.get("ibu").getAsString();
		description = beer.get("description").getAsString();
		breweryName = brewery.get("name").getAsString();
		lat = brewery.get("locations").getAsJsonArray().get(0).getAsJsonObject().get("latitude").getAsString();
		lon = brewery.get("locations").getAsJsonArray().get(0).getAsJsonObject().get("longitude").getAsString();
		website = brewery.get("website").getAsString();
		img = brewery.get("images").getAsJsonObject().get("mediumSquare").getAsString();
		styleId = Integer.toString(randomStyleId);
		styleName = beer.get("style").getAsJsonObject().get("shortName").getAsString();	
	}

	public String getBeerName() {return beerName;}

	public String getAbv() {return abv;}

	public String getIbu() {return ibu;}

	public String getDescription() {return description;}

	public String getBreweryName() {return breweryName;}

	public String getLat() {return lat;}

	public String getLon() {return lon;}

	public String getWebsite() {return website;}

	public String getImg() {return img;}

	public String getStyleId() {return styleId;}

	public String getStyleName() {return styleName;}

}
