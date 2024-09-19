package org.example;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class Main {
  public static void main(String[] args) throws IOException {
    Properties mainProperties = new Properties();

    FileInputStream file;
    String path = "./loadTestConfig.properties"; // file needs to be in project root
    file = new FileInputStream(path);
    mainProperties.load(file);
    file.close();

    //String configProperties = mainProperties.getProperty("");
    int numRuns;
    int numThreads;
    try {
      numRuns = Integer.parseInt(mainProperties.getProperty("run_count").trim());
      numThreads = Integer.parseInt(mainProperties.getProperty("thread_count").trim());
    } catch (NumberFormatException e) {
      numRuns = 1;
      numThreads = 1;
    }
    String endpoint = mainProperties.getProperty("endpoint");
    String[] headers = mainProperties.getProperty("header_list").split(",");
    String[] formData = mainProperties.getProperty("form_post_data").split(",");

    // Accounting for the case where formData is a single empty string - throws errors in http data field
    if (formData.length == 1 && formData[0].equals("")) {
      formData = new String[0];
    }

    HttpSender httpSender = new HttpSender(numRuns, numThreads, endpoint, headers, formData);
    httpSender.run();
  }
}