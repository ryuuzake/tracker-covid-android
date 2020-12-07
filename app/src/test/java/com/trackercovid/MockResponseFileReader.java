package com.trackercovid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MockResponseFileReader {

    ClassLoader classLoader;

    public MockResponseFileReader() {
        classLoader = this.getClass().getClassLoader();
    }

    public String readJson(String path) {
        StringBuilder json = new StringBuilder();
        try {
            assert classLoader != null;
            InputStream resourceAsStream = classLoader.getResourceAsStream(path);
            assert resourceAsStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
