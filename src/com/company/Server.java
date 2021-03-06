package com.company;

// Использован Blocking способ взаимодействия по причине небольшого количества данных
// для обмена

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static String name;

    static String ask(BufferedReader in, PrintWriter out, String question, String isFinish) throws IOException {
        String answer = "";
        out.println(String.format(question));
        out.println(String.format(isFinish));
        System.out.println("SERVER: " + question);
        answer = in.readLine();
        System.out.println("CLIENT: " + answer);
        return answer;
    }

    public static void main(String[] args) throws IOException {
        int port = 8083;
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            System.out.println("Server started.");
            while (true) {
                System.out.println("Awaiting the connection...");
                try (
                        Socket clientSocket = serverSocket.accept(); // ждем подключения
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

                    System.out.println(String.format("New connection accepted by port %d", clientSocket.getPort()));
                    String answer = in.readLine();

                    System.out.println("CLIENT: " + answer);
                    String data = null;
                    Integer n = null;
                    Boolean flag = false;
                    do {
                        if (!flag) data = ask(in, out, "Input N", "No");
                        else data = ask(in, out, "Incorrect input. Input numeric N", "No");
                        if (data == null) {
                            System.out.println("Incorrect answer from Client or it's session terminated.");
                            break;
                        }
                        if (data.matches("[-+]?\\d+")) n = Integer.parseInt(data);
                        else flag = true;
                    } while (n == null);

                    if (n !=null) {
                        Long fibonacci = Fibonacci.calculateWithFor(n);
                        ask(in, out, "Fibonacci number for " + n + " is " + fibonacci, "Yes");
                        System.out.println("Session finished. Connection closed");
                    }
                    clientSocket.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
