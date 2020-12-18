package com.trackercovid.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.HistoricalResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class HistoricalServiceTest {

    private MockResponseFileReader fileReader = new MockResponseFileReader();
    private MockWebServer server = new MockWebServer();
    private HistoricalService service;

    @Before
    public void setUp() throws Exception {
        server.start();

        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat("MM/dd/yy")
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();
        service = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(HistoricalService.class);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void getHistoricalData_verifyEqualsJsonMapping() throws IOException {
        // Assign
        String json = fileReader.readJson("historical_response.json");
        assert json != null;
        MockResponse expectedResponse = new MockResponse()
                .setResponseCode(HTTP_OK)
                .setBody(json);
        server.enqueue(expectedResponse);

        // Act
        String tCountryName = "Spain";
        Response<HistoricalResponse> response = service.getHistoricalData(tCountryName).execute();

        // Assert
        assertNotNull(response.body());
    }
}