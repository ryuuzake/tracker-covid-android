package com.trackercovid.retrofit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trackercovid.MockResponseFileReader;
import com.trackercovid.api_response.CountryResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CountryServiceTest {

    private MockResponseFileReader fileReader = new MockResponseFileReader();
    private MockWebServer server = new MockWebServer();
    private CountryService service;

    @Before
    public void setUp() throws Exception {
        server.start();

        service = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(CountryService.class);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void getAllCountries_verifyJsonMapping() throws IOException {
        // Assign
        String json = fileReader.readJson("all_countries_response.json");
        assert json != null;
        MockResponse expectedResponse = new MockResponse()
                .setResponseCode(HTTP_OK)
                .setBody(json);
        server.enqueue(expectedResponse);

        // Act
        Response<List<CountryResponse>> response = service.getAllCountries(null).execute();

        // Assert
        assertNotNull(response.body());
    }

    @Test
    public void getAllCountries_verifyEqualsGsonMapping() throws IOException {
        // Assign
        String json = fileReader.readJson("all_countries_response.json");
        assert json != null;
        MockResponse expectedResponse = new MockResponse()
                .setResponseCode(HTTP_OK)
                .setBody(json);
        server.enqueue(expectedResponse);
        Type type = new TypeToken<List<CountryResponse>>() {
        }.getType();
        List<CountryResponse> expected = new Gson().fromJson(json, type);

        // Act
        Response<List<CountryResponse>> response = service.getAllCountries(null).execute();

        // Assert
        assertNotNull(response.body());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), response.body().get(i));
        }
    }
}