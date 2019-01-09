package assignmentApple;


import java.io.FileNotFoundException;
import java.io.FileReader;

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.jayway.restassured.path.json.JsonPath;
//import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.synth.SynthStyle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
//Question 1: Print all the blue Teslas received in the web service response. Also print the notes
//Question 2: Return all cars which have the lowest per day rental cost for both cases:
//a. Price only
//b. Price after discounts
//Question 3: Find the highest revenue generating car. year over year maintenance cost + depreciation is the total expense per car for the full year for the rental car company.
//The objective is to find those cars that produced the highest profit in the last year
/*Extract response as string and return response*/
public class Assignment1 {
	/*Extract response as string and return response*/
	
	
	
	@Test
	public void printBlueTesla() throws ParseException, FileNotFoundException, IOException  {
		JSONParser jsonParser = new JSONParser()	;
		System.out.println("==================================================================");
		System.out.println("********Question1******");
		System.out.println("==================================================================");
        Object obj = jsonParser.parse(new FileReader("/Users/sangitaraniamatya/eclipse-workspace/assignmentApple/src/test/java/assignmentApple/json.txt"));
        JSONObject jsonObject = (JSONObject) obj;
//        System.out.println(jsonObject);
        JSONArray car= (JSONArray) jsonObject.get("Car");
		Iterator i = car.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONObject jsonChildObject = (JSONObject)innerObj.get("metadata");
			if(innerObj.get("make").equals("Tesla") && jsonChildObject.get("Color").equals("Blue")) {
				System.out.println("make::: "+ (innerObj.get("make")) );
				System.out.println("colour:::::"+jsonChildObject.get("Color"));
				System.out.println("Notes:::::"+jsonChildObject.get("Notes"));

			}
		}


					
	}
	//Question 2: Return all cars which have the lowest per day rental cost for both cases:
	//a. Price only
	//b. Price after discounts
	@Test
	public void printLowestRateCar() throws ParseException, FileNotFoundException, IOException  {
		System.out.println("==================================================================");
		System.out.println("********Question2.1******");
		System.out.println("==================================================================");

		List<String>  lowestCar =new ArrayList<String>();
		Long lowestrent=0l;
		
		JSONParser jsonParser = new JSONParser()	;

        Object obj = jsonParser.parse(new FileReader("/Users/sangitaraniamatya/Desktop/Json.txt"));
        JSONObject jsonObject = (JSONObject) obj;
//        System.out.println(jsonObject);
        JSONArray car= (JSONArray) jsonObject.get("Car");
		Iterator i = car.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONObject jsonChildObject = (JSONObject)innerObj.get("perdayrent");
			if (lowestrent >= (Long) jsonChildObject.get("Price") || lowestrent==0 ) {
				lowestrent=(Long) jsonChildObject.get("Price");
			}
			}
		i = car.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONObject jsonChildObject = (JSONObject)innerObj.get("perdayrent");
			if (lowestrent == jsonChildObject.get("Price")) {
				System.out.println(innerObj);
			}
			}
		
		}

	@Test
	public void printDiscountedCar() throws ParseException, FileNotFoundException, IOException  {
		List<String>  lowestCar =new ArrayList<String>();
		System.out.println("==================================================================");
		System.out.println("********Question2.2******");
		System.out.println("==================================================================");

		Long lowestrent=0l;
		JSONParser jsonParser = new JSONParser()	;
        Object obj = jsonParser.parse(new FileReader("/Users/sangitaraniamatya/Desktop/Json.txt"));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray car= (JSONArray) jsonObject.get("Car");
		Iterator i = car.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONObject jsonChildObject = (JSONObject)innerObj.get("perdayrent");
			Long price=(Long) jsonChildObject.get("Price");
			Long discount=(Long) jsonChildObject.get("Discount");
			Long priceAfterDiscount=price-(price*discount/100);
			if (lowestrent >= priceAfterDiscount || lowestrent==0 ) {
				lowestrent=priceAfterDiscount;
			}
			}
		i = car.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONObject jsonChildObject = (JSONObject)innerObj.get("perdayrent");
			Long price=(Long) jsonChildObject.get("Price");
			Long discount=(Long) jsonChildObject.get("Discount");
			Long priceAfterDiscount=price-(price*discount/100);
			if (lowestrent == priceAfterDiscount) {
				System.out.println(innerObj);
			}
			}
		
		}
	///Question 3: Find the highest revenue generating car. year over year maintenance cost + depreciation is the total expense per car for the full year for the rental car company.
	//The objective is to find those cars that produced the highest profit in the last year	
	@Test
	public void printHighestRevenueCar() throws ParseException, FileNotFoundException, IOException  {
		List<String>  lowestCar =new ArrayList<String>();
		System.out.println("==================================================================");
		System.out.println("********Question3******");
		System.out.println("==================================================================");

		Double HighestProfit=(Double) 0.0;
		JSONParser jsonParser = new JSONParser()	;
        Object obj = jsonParser.parse(new FileReader("/Users/sangitaraniamatya/Desktop/Json.txt"));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray car= (JSONArray) jsonObject.get("Car");
		Iterator i = car.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONObject jsonPriceChildObject = (JSONObject)innerObj.get("perdayrent");
			Long price=(Long) jsonPriceChildObject.get("Price");
			Long discount=(Long) jsonPriceChildObject.get("Discount");
			Long priceAfterDiscount=price-(price*discount/100);

			JSONObject jsonMetricsChildObject = (JSONObject)innerObj.get("metrics");
			Double yoymaintenancecost=(Double) jsonMetricsChildObject.get("yoymaintenancecost");
			Double depreciation=(Double) jsonMetricsChildObject.get("depreciation");
			
			JSONObject rentalCountObject=(JSONObject) jsonMetricsChildObject.get("rentalcount");
			Long noofDays=(Long) rentalCountObject.get("yeartodate");
			Double totalIncome=(noofDays*priceAfterDiscount)-(yoymaintenancecost+depreciation);
			
			if (HighestProfit <= totalIncome || HighestProfit==0 ) {
				HighestProfit=totalIncome;
			}
			}
			i = car.iterator();
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				JSONObject jsonPriceChildObject = (JSONObject)innerObj.get("perdayrent");
				Long price=(Long) jsonPriceChildObject.get("Price");
				Long discount=(Long) jsonPriceChildObject.get("Discount");
				Long priceAfterDiscount=price-(price*discount/100);

				JSONObject jsonMetricsChildObject = (JSONObject)innerObj.get("metrics");
				Double yoymaintenancecost=(Double) jsonMetricsChildObject.get("yoymaintenancecost");
				Double depreciation=(Double) jsonMetricsChildObject.get("depreciation");
				
				JSONObject rentalCountObject=(JSONObject) jsonMetricsChildObject.get("rentalcount");
				Long noofDays=(Long) rentalCountObject.get("yeartodate");
				Double totalIncome=(noofDays*priceAfterDiscount)-(yoymaintenancecost+depreciation);
				int retval = Double.compare(totalIncome, HighestProfit);

				if (retval == 0) {
					System.out.println(innerObj);
				}
				}
				
			
		}

}


