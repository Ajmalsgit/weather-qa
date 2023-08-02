package com.assignment.weather.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {
	private static final String API_BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly";
	private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int choice;

		do {
			displayMenu();
			choice = scanner.nextInt();

			switch (choice) {
				case 1:
					getTemperature(scanner);
					break;
				case 2:
					getWindSpeed(scanner);
					break;
				case 3:
					getPressure(scanner);
					break;
				case 0:
					System.out.println("Exiting the program.");
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		} while (choice != 0);

		scanner.close();
	}

	private static void displayMenu() {
		System.out.println("Choose an option:");
		System.out.println("1. Get weather");
		System.out.println("2. Get Wind Speed");
		System.out.println("3. Get Pressure");
		System.out.println("0. Exit");
	}


	private static void getTemperature(Scanner scanner) {
		System.out.println("Enter the date (YYYY-MM-DD): ");
		String date = scanner.next();
		String endpoint = API_BASE_URL + "?q=London,us&appid=" + API_KEY;
		JSONObject response = getApiResponse(endpoint);


		if (response != null) {
			JSONArray list = response.getJSONArray("list");
			for (int i = 0; i < list.length(); i++) {
				JSONObject entry = list.getJSONObject(i);
				String dt_txt = entry.getString("dt_txt");
				if (dt_txt.startsWith(date)) {
					double temperature = entry.getJSONObject("main").getDouble("temp");
					System.out.println("Temperature on " + dt_txt + ": " + temperature + "K");
				}
			}
		}
	}

	private static void getWindSpeed(Scanner scanner) {
		System.out.println("Enter the date (YYYY-MM-DD): ");
		String date = scanner.next();
		String endpoint = API_BASE_URL + "?q=London,us&appid=" + API_KEY;
		JSONObject response = getApiResponse(endpoint);

		if (response != null) {
			JSONArray list = response.getJSONArray("list");
			for (int i = 0; i < list.length(); i++) {
				JSONObject entry = list.getJSONObject(i);
				String dt_txt = entry.getString("dt_txt");
				if (dt_txt.startsWith(date)) {
					double windSpeed = entry.getJSONObject("wind").getDouble("speed");
					System.out.println("Wind Speed on " + dt_txt + ": " + windSpeed + " m/s");
				}
			}
		}else{
			System.out.println("data not available");
		}
	}

	private static void getPressure(Scanner scanner) {
		System.out.println("Enter the date (YYYY-MM-DD): ");
		String date = scanner.next();
		String endpoint = API_BASE_URL + "?q=London,us&appid=" + API_KEY;
		JSONObject response = getApiResponse(endpoint);

		if (response != null) {
			JSONArray list = response.getJSONArray("list");
			for (int i = 0; i < list.length(); i++) {
				JSONObject entry = list.getJSONObject(i);
				String dt_txt = entry.getString("dt_txt");
				if (dt_txt.startsWith(date)) {
					double pressure = entry.getJSONObject("main").getDouble("pressure");
					System.out.println("Pressure on " + dt_txt + ": " + pressure + " hPa");
				}
			}
		}else{
			System.out.println("data not available");
		}
	}

	private static JSONObject getApiResponse(String endpoint) {
		try {
			URL url = new URL(endpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			reader.close();
			conn.disconnect();

			// Parse the JSON response
			return new JSONObject(response.toString());
		} catch (IOException e) {
			System.out.println("Error while fetching data from the API.");
			e.printStackTrace();
			return null;
		}
	}
}
