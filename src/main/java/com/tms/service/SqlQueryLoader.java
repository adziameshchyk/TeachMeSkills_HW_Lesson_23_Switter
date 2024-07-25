package com.tms.service;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SqlQueryLoader {

    public static final String FAILED_TO_LOAD_FILE_MESSAGE = "Failed to load file with sql query.";

    public static String loadQuery(String fileName) {
        StringBuilder queryBuilder = new StringBuilder();
        try (InputStream inputStream = SqlQueryLoader.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                queryBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException | NullPointerException e) {
            System.out.println(FAILED_TO_LOAD_FILE_MESSAGE);
            e.printStackTrace();
        }

        return queryBuilder.toString().trim();
    }

}
