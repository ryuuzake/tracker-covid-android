package com.trackercovid.retrofit;

import com.trackercovid.MockResponseFileReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.net.HttpURLConnection.HTTP_OK;

@RunWith(MockitoJUnitRunner.class)
public class HistoricalServiceTest {

    private MockResponseFileReader fileReader = new MockResponseFileReader();
    private MockWebServer server = new MockWebServer();
    private HistoricalService service;

    @Before
    public void setUp() throws Exception {
        server.start();

        service = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(HistoricalService.class);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void getHistoricalData_verifyEqualsJsonMapping() {
        // Assign
        String json = fileReader.readJson("all_countries_response.json");
        assert json != null;
        MockResponse expectedResponse = new MockResponse()
                .setResponseCode(HTTP_OK)
                .setBody(json);
        server.enqueue(expectedResponse);

        // Act
//        Response<List<CountryResponse>> response = service.getHistoricalData(tCountryName).execute();

        // Assert
//        assertNotNull(response.body());
//        for (int i = 0; i < expected.size(); i++) {
//            assertEquals(expected.get(i), response.body().get(i));
//        }
    }
}