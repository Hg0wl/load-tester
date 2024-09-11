package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.List;

public class HttpSender {
  int numRuns;
  int numThreads;
  URI endpoint;
  List<String> headers;
  Form form;

  HttpSender(int numRuns, int numThreads, URI endpoint, List<String> headers, Form form) {
    this.numRuns = numRuns;
    this.numThreads = numThreads;
    this.endpoint = endpoint;
    this.headers = headers;
    this.form = form;
  }

  HttpSender() {}

  public void simpleConnect() throws IOException {
    Socket socket = new Socket("localhost", 8080);

    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    out.println("Hello from client");

    String response = in.readLine();
    System.out.println("Server says: " + response);
    socket.close();
  }

  public void run() {
    // Create a loop that iterates numRuns times, where each run creates numThreads threads
    // to send a POST request to the endpoint at each of the headers with the data in the
    // Form object


  }



}