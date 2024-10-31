package com.chatapp;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int TCP_PORT = 1502;
    private static final int UDP_PORT = 1502;
    private static final String BROADCAST_ADDRESS = "233.0.0.1";
    private static final int MESSAGE_INTERVAL = 10000; // 10 секунд

    private List<String> messageBuffer = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
                System.out.println("Сервер запущен на порту " + TCP_PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new ClientHandler(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                DatagramSocket udpSocket = new DatagramSocket();
                InetAddress group = InetAddress.getByName(BROADCAST_ADDRESS);

                while (true) {
                    Thread.sleep(MESSAGE_INTERVAL);
                    if (!messageBuffer.isEmpty()) {
                        String broadcastMessage = String.join("\n", messageBuffer);
                        byte[] buffer = broadcastMessage.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, UDP_PORT);
                        udpSocket.send(packet);
                        System.out.println("Отправлено сообщение: " + broadcastMessage);
                        messageBuffer.clear();
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String message;
                while ((message = in.readLine()) != null) {
                    synchronized (messageBuffer) {
                        messageBuffer.add(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}