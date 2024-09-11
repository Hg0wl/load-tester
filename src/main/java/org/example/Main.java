package org.example;

import java.io.*;
import java.util.Properties;

public class Main {
  public static void main(String[] args) throws IOException {
    Properties mainProperties = new Properties();

    FileInputStream file;
    String path = "./loadTestConfig.properties"; // file needs to be in project root
    file = new FileInputStream(path);
    mainProperties.load(file);
    file.close();

    // TODO: work with imported properties
    //String configProperties = mainProperties.getProperty("");
    int numRuns = Integer.parseInt(mainProperties.getProperty("run_count"));


    HttpSender httpSender = new HttpSender();

  }
}