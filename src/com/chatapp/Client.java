package com.chatapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Client extends JFrame {
    private static final int TCP_PORT = 1502;
    private static final int UDP_PORT = 1502;
    private static final String BROADCAST_ADDRESS = "233.0.0.1";

    private JTextArea chatArea;
    private JTextField inputField;
    private Socket tcpSocket;
    private PrintWriter out;

    public Client() {
        setTitle("Чат");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });
        add(inputField, BorderLayout.SOUTH);

        setVisible(true);

        connectToServer();
        startUDPListener();
    }

    private void connectToServer() {
        try {
            tcpSocket = new Socket("localhost", TCP_PORT);
            out = new PrintWriter(tcpSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void startUDPListener() {
        new Thread(() -> {
            try (MulticastSocket socket = new MulticastSocket(UDP_PORT)) {
                InetAddress group = InetAddress.getByName(BROADCAST_ADDRESS);
                socket.joinGroup(group);

                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                    chatArea.append(receivedMessage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}