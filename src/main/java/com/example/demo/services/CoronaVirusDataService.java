package com.example.demo.services;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.model.CoronaModel;

@Service
public class CoronaVirusDataService {

	public static List<CoronaModel> allStats;
	public static Integer totalCases;
	public static Integer newCases;

	public static String URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";

	@Scheduled(fixedRate = 24*60*60*1000)
	public void fetchCoronaData() throws IOException, InterruptedException {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(URL)).build();
		HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		Reader in = new StringReader(response.body());
		List<CoronaModel> list = new ArrayList<>();
		totalCases = 0;
		newCases = 0;
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			CoronaModel model = new CoronaModel();
			model.setState(record.get(6));
			model.setCountry(record.get(7));
			model.setTotalCase(Integer.parseInt(record.get(record.size() - 1)));
			totalCases += Integer.parseInt(record.get(record.size() - 1));
			newCases += Integer.parseInt(record.get(record.size() - 1))
					- Integer.parseInt(record.get(record.size() - 2));
			model.setNewCase(
					Integer.parseInt(record.get(record.size() - 1)) - Integer.parseInt(record.get(record.size() - 2)));
			list.add(model);
		}
		allStats = list;
	}
}
