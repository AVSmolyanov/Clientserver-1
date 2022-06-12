package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        try {
            Socket socket = new Socket("127.0.0.1", 8083);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(
                         new OutputStreamWriter(socket.getOutputStream()), true);
                 Scanner scanner = new Scanner(System.in)) {

                System.out.println("Client started");
                out.println(String.format("Hi, it's <User1>"));
                String answer;
                while (true) {
                    String question = in.readLine();
                    String isFinish = in.readLine();
                    System.out.println("SERVER: " + question);
                    switch (isFinish) {
                        case "Yes":
                            answer = "end";
                            break;
                        default:
                            System.out.print("YOU: ");
                            answer = scanner.nextLine();
                    }
                    if ("end".equals(answer)) {
                        break;
                    }
                    out.println(String.format(answer));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
