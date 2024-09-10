package org.example;

import java.io.*;
import java.net.*;

public class Main {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 8080);

    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


    out.println("Hello from client");

    String response = in.readLine();
    System.out.println("Server says: " + response);
    socket.close();
  }
}