package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class HttpSender {
  int numRuns;
  int numThreads;
  String endpoint;
  String[] headers;
  String[] form;

  HttpSender(int numRuns, int numThreads, String endpoint, String[] headers, String[] form) {
    this.numRuns = numRuns;
    this.numThreads = numThreads;
    this.endpoint = endpoint;
    this.headers = headers;
    this.form = form;
  }
  HttpSender() { }

  public void testConnection() throws IOException {
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
    for (int runs = 0; runs < numRuns; runs++) {

      for (int threads = 0; threads < numThreads; threads++) {
        // Call an HTTP Request sendAsync helper function numThreads times
        try {
          this.createRequest(this.form, runs, threads);
        } catch (MalformedURLException e) {
          System.err.println(e.getMessage());
        }
      }
    }
  }

  private void createRequest(String[] formDataMap, int runs, int threads) throws MalformedURLException {
    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
      .setType(MultipartBody.FORM);

    // Add all form fields to the post request
    if (form.length != 0) {
      for (String data : formDataMap) {
      /* addFormDataPart syntax:
        requestBodyBuilder
        .addFormDataPart("name", "chris")
        .addFormDataPart("email", "test@test.com");
       */
        if (data.contains(":")) {
          String fieldName = data.substring(0, data.indexOf(":"));
          String valueName = data.substring(data.indexOf(":") + 1);
          requestBodyBuilder.addFormDataPart(fieldName, valueName);
        }
      }
    } else {
      requestBodyBuilder.addFormDataPart("testField", "testValue");
    }
    this.sendRequest(requestBodyBuilder.build(), runs, threads);
  }

  private void sendRequest(RequestBody requestBody, int runs, int threads) throws MalformedURLException {
    OkHttpClient client = new OkHttpClient();
    Request.Builder requestBuilder = new okhttp3.Request.Builder()
      .url(this.endpoint)
      .post(requestBody);

    // Add all HTTP Headers to the request
    for (String header : headers) {
      if (header.contains(":")) {
        String headerName = header.substring(0, header.indexOf(":"));
        String headerValue = header.substring(header.indexOf(":") + 1);
        requestBuilder.addHeader(headerName, headerValue);
      }
    }

    Request request = requestBuilder.build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        System.out.println(call.toString() + e);
        call.cancel();
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        System.out.println("RUN: " + runs + ", THREADS: " + threads + ", CODE: " + response.code());
        response.close();
        call.cancel();



      }
    });

    //System.out.println("Run: " + runs + ", Thread: " + threads + " returned code: " + response.code());
    //System.out.println(response);


  }
}

