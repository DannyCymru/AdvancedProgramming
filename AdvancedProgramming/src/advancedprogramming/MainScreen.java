/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package advancedprogramming;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author dm5376y
 */
public class MainScreen extends javax.swing.JFrame {

    String filepath1 = "IDs.txt";
    File file = new File(filepath1);
    public String ID;
    String listPort = "";
    String conAddr = "";
    String uID;
    String username, address;
    ArrayList<String> users = new ArrayList();
    int port = 7721;
    Boolean isConnected = false;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    int a = 0;
    Boolean Connected = false;
    String Ipnew = "";

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public void userAdd(String data) {
        users.add(data);
    }

    public void userRemove(String data) {
        display.append(data + " is now offline.\n");
    }

    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token : tempList) {
            //users.append(token + "\n");
        }
    }

    public void sendDisconnect() {
        String disconnectString;
        try {
            String encryptSignature = Encryption.Signature(input.getText());
            disconnectString = username + ": :Disconnect" + ":" + NetInterface.IpUser() + ":" + encryptSignature;
            writer.println(disconnectString);
            writer.flush();
        } catch (Exception e) {
            display.append(currTime() + "Could not send Disconnect message.\n");
        }
    }

    public void Disconnect() {
        try {

            display.append(currTime() + "Disconnected.\n");
            sock.close();
        } catch (Exception ex) {
            display.append(currTime() + "Failed to disconnect. \n");
        }
        isConnected = false;

    }

    public MainScreen() {

        initComponents();

    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            String[] data;

            String people[];

            String[] ips;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat", hdisconnect = "has disconnected";

            try {
                address = "";
                int i = 0;
                int x = 0;
                while ((stream = reader.readLine()) != null) {
                    System.out.println("i: " + i);

                    data = stream.split(":");
                    people = stream.split(":");

                    if (data[2].equals(chat) && data[1].equals(hdisconnect)) {

                        List<String> list = new ArrayList<String>();

                        Scanner inFile1 = new Scanner(new File("ips.txt")).useDelimiter(":");
                        String joinedString = String.join(":", data);

                        ips = joinedString.split(":");
                        String op = ips[3];

                        // Original answer used LinkedList, but probably preferable to use ArrayList in most cases
                        // List<String> temps = new LinkedList<String>();
                        List<String> temps = new ArrayList<String>();
                        String token1 = "";
                        Boolean writ = true;
                        // while loop
                        if (tf_address.getText().equals(Ipnew)) {
                            isConnected = false;
                            display.append("Server closed down! Trying to connect to next coordinator!" + "\n");
                        }
                        while (inFile1.hasNext()) {
                            // find next line
                            token1 = inFile1.next();
                            temps.add(token1);
                        }
                        inFile1.close();

                        String[] tempsArray = temps.toArray(new String[0]);

                        for (String s : tempsArray) {
                            String[] hel = tempsArray;

                            list = Arrays.asList(hel);
                            System.out.println(Arrays.toString(hel));

                            for (String str : list) {

                                if (str.trim().contains(data[0])) {
                                    System.out.println("goes to change Boolean");

                                    System.out.println(list);

                                    writ = false;
                                }
                            }

                            if (writ) {

                                FileWriter writer = new FileWriter("ips.txt", true);
                                writer.write(ips[0] + ":" + ips[3] + ":");
                                writer.close();
                            }

                            //PrivateKey PrivaKey = Encryption.PrK(A);
                            //byte[] MessageEncrypt = data[1].getBytes();
                            //String MessageDecrypted = Encryption.DecryptionPart(MessageEncrypt, PrivaKey);
                            String encryptSignature = Encryption.Signature(data[1]);

                        }

                        if (isConnected == false) {

                            System.out.println("address main screen line 504: " + address);
                            for (x = 1; x < list.size();) {
                                String newip = list.get(x);
                                address = newip;
                                try {
                                    String encryptSignature = Encryption.Signature(input.getText());
                                    sock = new Socket(address, port);
                                    System.out.println("sock MainScreen 509: " + sock);
                                    InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                    reader = new BufferedReader(streamreader);
                                    writer = new PrintWriter(sock.getOutputStream());
                                    writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                    writer.flush();
                                    isConnected = true;
                                    uniqueId.setText(uID);

                                } catch (Exception ex) {
                                    x += 2;
                                    Onliners.setText("Online members :" + "\n");
                                    display.append("Retrying to connect to the next available coordinator!" + "\n");
                                    System.out.println("GOES HERE AND ADS X++++++++++++++++++++++" + newip);

                                }

                                ListenThread();

                            }
                        } else if (isConnected == true) {
                            display.append(currTime() + "You are already connected. \n");
                        }

                    } else if (data[2].equals(chat)) {

                        Scanner inFile1 = new Scanner(new File("ips.txt")).useDelimiter(":");
                        String joinedString = String.join(":", data);

                        ips = joinedString.split(":");
                        String op = ips[3];

                        // Original answer used LinkedList, but probably preferable to use ArrayList in most cases
                        // List<String> temps = new LinkedList<String>();
                        List<String> temps = new ArrayList<String>();
                        String token1 = "";
                        Boolean writ = true;
                        // while loop
                        while (inFile1.hasNext()) {
                            // find next line
                            token1 = inFile1.next();
                            temps.add(token1);
                        }
                        inFile1.close();

                        String[] tempsArray = temps.toArray(new String[0]);

                        for (String s : tempsArray) {
                            String[] hel = tempsArray;

                            List<String> list = Arrays.asList(hel);
                            System.out.println(Arrays.toString(hel));

                            for (String str : list) {
                                if (str.trim().contains(data[0])) {
                                    System.out.println("goes to change Boolean");
                                    writ = false;

                                }
                            }
                        }
                        if (writ) {

                            FileWriter writer = new FileWriter("ips.txt", true);
                            writer.write(ips[0] + ":" + ips[3] + ":");
                            writer.close();
                        }

                        //PrivateKey PrivaKey = Encryption.PrK(A);
                        //byte[] MessageEncrypt = data[1].getBytes();
                        //String MessageDecrypted = Encryption.DecryptionPart(MessageEncrypt, PrivaKey);
                        String encryptSignature = Encryption.Signature(data[1]);

                        if (i == 0) {
                            display.append(currTime() + data[0] + ": " + data[1] + "\n");
                            display.setCaretPosition(display.getDocument().getLength());

                        } else if (encryptSignature.equals(data[4])) {
                            display.append(currTime() + data[0] + ": " + data[1] + "\n");
                            display.setCaretPosition(display.getDocument().getLength());
                            System.out.println(encryptSignature + "\n" + data[4]);

                        } else {
                            display.append(currTime() + data[0] + ": " + data[1] + " !messsage has been modified! " + "\n");
                            System.out.println(Arrays.toString(data));
                            display.setCaretPosition(display.getDocument().getLength());
                            System.out.println(encryptSignature + "\n" + data[4]);
                        }
                        i++;
                    } else if (data[2].equals(connect)) {

                        display.removeAll();
                        userAdd(data[0]);
                        String conne = Arrays.toString(people);
                        String replace = conne.replace(",", "");
                        String replace1 = replace.replace("Connect", "");
                        String replace2 = replace1.replace(" ", "");

                        Onliners.setText("Online Members" + "\n");
                        combobox.removeAllItems();
                        Onliners.append(users.get(0) + "Coordinator");
                        Onliners.append("\n");

                        for (String current_user : users) {
                            combobox.addItem(current_user);
                        }
                        for (int z = 1; z < users.size(); z++) {
                            Onliners.append(users.get(z));
                            Onliners.append("\n");

                        }

                    } else if (data[2].equals(disconnect)) {

                        userRemove(data[0]);
                        String conne = Arrays.toString(people);
                        String replace = conne.replace(",", "");
                        String replace1 = replace.replace("Connect", "");
                        String replace2 = replace1.replace(" ", "");
                        users.remove(replace2);
                        combobox.removeAllItems();
                        Onliners.append(users.get(0) + "Coordinator");
                        Onliners.append("\n");

                        for (String current_user : users) {
                            combobox.removeItem(current_user);
                        }
                        for (int z = 1; z < users.size(); z++) {
                            Onliners.append(users.get(z));
                            Onliners.append("\n");

                        }
                    } else if (data[2].equals(done)) {

                        //users.setText("");
                        writeUsers();
                        users.clear();

                    }

                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        display = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        Onliners = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        serverInfo = new javax.swing.JTextArea();
        uniqueId = new javax.swing.JLabel();
        sendText = new javax.swing.JButton();
        input = new javax.swing.JTextField();
        tf_address = new javax.swing.JTextField();
        lb_address = new javax.swing.JLabel();
        lb_port = new javax.swing.JLabel();
        tf_port = new javax.swing.JTextField();
        b_connect1 = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        combobox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        display.setEditable(false);
        display.setColumns(20);
        display.setRows(5);
        jScrollPane1.setViewportView(display);

        Onliners.setEditable(false);
        Onliners.setColumns(1);
        Onliners.setRows(5);
        Onliners.setText("Online Members");
        jScrollPane3.setViewportView(Onliners);

        serverInfo.setEditable(false);
        serverInfo.setColumns(1);
        serverInfo.setRows(5);
        serverInfo.setText("My IP address:");
        jScrollPane4.setViewportView(serverInfo);

        uniqueId.setText("Unique ID");
        uniqueId.setRequestFocusEnabled(false);
        uniqueId.setVerifyInputWhenFocusTarget(false);

        sendText.setText("Send");
        sendText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendTextActionPerformed(evt);
            }
        });

        input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputActionPerformed(evt);
            }
        });
        input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputKeyPressed(evt);
            }
        });

        try
        {
            tf_address.setText(NetInterface.IpUser());
        }catch (Exception ex)
        {

        }
        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_addressActionPerformed(evt);
            }
        });

        lb_address.setText("Address : ");

        lb_port.setText("Port :");

        tf_port.setText("7721");
        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_portActionPerformed(evt);
            }
        });

        b_connect1.setText("Connect");
        b_connect1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connect1ActionPerformed(evt);
            }
        });

        b_disconnect.setText("Disconnect");
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });

        combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxActionPerformed(evt);
            }
        });

        jLabel1.setText("Private chat: ");

        jButton3.setText("Chat");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(uniqueId)
                        .addGap(18, 18, 18)
                        .addComponent(input))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lb_address, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lb_port, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(b_connect1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(b_disconnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendText)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(uniqueId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sendText)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_address, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_port, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(b_connect1)
                        .addComponent(b_disconnect)
                        .addComponent(combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3)))
        );

        uniqueId.getAccessibleContext().setAccessibleName("uniqueIdLabel");
        uniqueId.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void sendTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendTextActionPerformed

            String nothing = "";
            if ((input.getText()).equals(nothing)) {
                input.setText("");
                input.requestFocus();
            } else {
                try {

                    //KeyPair A = Encryption.GenerateKey1();
                    String messageToEncrypt = input.getText();
                    //PublicKey PubliKey = Encryption.PuK(A);
                    //PrivateKey PrivaKey = Encryption.PrK(A);

                    //byte[] MessageEncrypted = Encryption.EncryptionPart(messageToEncrypt, PubliKey);
                    String encryptSignature = Encryption.Signature(input.getText());

                    writer.println(username + ":" + input.getText() /*Arrays.toString(MessageEncrypted)*/ + ":" + "Chat" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                    writer.flush(); // flushes the buffer

                } catch (Exception ex) {
                    display.append("Message was not sent. \n");
                }
                input.setText("");
                input.requestFocus();
            }

            input.setText("");
            input.requestFocus();


        }//GEN-LAST:event_sendTextActionPerformed

        private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_addressActionPerformed

        }//GEN-LAST:event_tf_addressActionPerformed

        private void tf_portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_portActionPerformed

        }//GEN-LAST:event_tf_portActionPerformed

        private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_disconnectActionPerformed
            sendDisconnect();
            Disconnect();
            Onliners.setText("Online Members:");
            combobox.removeAllItems();
        }//GEN-LAST:event_b_disconnectActionPerformed

        private void b_connect1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_connect1ActionPerformed

            address = tf_address.getText();
            if (isConnected == false) {

                System.out.println("address main screen line 504: " + address);

                try {
                    String encryptSignature = Encryption.Signature(input.getText());
                    sock = new Socket(address, port);
                    System.out.println("sock MainScreen 509: " + sock);
                    InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(sock.getOutputStream());
                    writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                    writer.flush();
                    isConnected = true;
                    uniqueId.setText(uID);
                    Connected = true;
                    FileWriter writer = new FileWriter("ips.txt", false);
                    writer.write("");
                    writer.close();
                    if (Connected) {
                        Ipnew = address;
                    }

                } catch (Exception ex) {
                    display.append("Cannot Connect! The server is Offline! Become a coordinator! \n");

                }

                ListenThread();

            } else if (isConnected == true) {
                display.append(currTime() + "You are already connected. \n");
            }


        }//GEN-LAST:event_b_connect1ActionPerformed

        private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_inputActionPerformed

        private void comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_comboboxActionPerformed

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

            String selected = combobox.getSelectedItem().toString();

            try {

                BufferedReader br = new BufferedReader(new FileReader("ips.txt"));
                String dan = "";
                String dp = "";
                String jak = "";
                String jp = "";
                String danny = "";
                String dap = "";
                String bar = "";
                String bp = "";

                Scanner inFile1 = new Scanner(new File("ips.txt")).useDelimiter(":");

                // Original answer used LinkedList, but probably preferable to use ArrayList in most cases
                // List<String> temps = new LinkedList<String>();
                List<String> temps = new ArrayList<String>();
                String token1 = "";

                while (inFile1.hasNext()) {
                    // find next line
                    token1 = inFile1.next();
                    temps.add(token1);
                }
                inFile1.close();

                String[] tempsArray = temps.toArray(new String[0]);

                for (String s : tempsArray) {
                    String[] hel = tempsArray;

                    List<String> list = Arrays.asList(hel);
                    System.out.println(Arrays.toString(hel));

                    for (String str : list) {
                        int ip;

                        if (str.trim().contains("dm5376y")) {
                            System.out.println("goes Daniels");
                            ip = list.indexOf("dm5376y");
                            dan = hel[ip];
                            dp = hel[ip + 1];
                        } else if (str.trim().contains("jn9942d")) {
                            System.out.println("goes Jake");
                            ip = list.indexOf("jn9942d");
                            jak = hel[ip];
                            jp = hel[ip + 1];
                        } else if (str.trim().contains("dr3344j")) {
                            System.out.println("goes Danny");
                            ip = list.indexOf("dr3344j");
                            danny = hel[ip];
                            dap = hel[ip + 1];
                        } else if (str.trim().contains("bm4904f")) {
                            System.out.println("goes Barney");
                            ip = list.indexOf("bm4904f");
                            bar = hel[ip];
                            bp = hel[ip + 1];
                        }
                    }

                }
                System.out.println(dan + "<------------------------------>" + dp);
                System.out.println(jak + "<------------------------------>" + jp);
                System.out.println(danny + "<------------------------------>" + dap);
                System.out.println(bar + "<------------------------------>" + bp);

                if (selected.equals(username)) {
                    System.out.println("ERRROOOR!!!!!!!");

                } else if (selected.equals("dm5376y") && dan.equals(selected)) {
                    sendDisconnect();
                    Disconnect();
                    Onliners.setText("Online Members:");
                    combobox.removeAllItems();
                    address = dp;

                    try {
                        br = new BufferedReader(new FileReader(filepath1));

                        String line;

                        while ((line = br.readLine()) != null) {

                            if (line.equals(uID)) {
                                if (isConnected == false) {

                                    System.out.println("address main screen line 429: " + address);

                                    try {
                                        String encryptSignature = Encryption.Signature(input.getText());
                                        sock = new Socket(address, port);
                                        System.out.println("sock MainScreen 426: " + sock);
                                        InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                        reader = new BufferedReader(streamreader);
                                        writer = new PrintWriter(sock.getOutputStream());
                                        writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                        writer.flush();
                                        isConnected = true;
                                        uniqueId.setText(uID);
                                    } catch (Exception ex) {
                                        display.append("Cannot Connect! The server is Offline! Become a coordinator! \n");

                                    }

                                    ListenThread();

                                } else if (isConnected == true) {
                                    display.append(currTime() + "You are already connected. \n");
                                }
                                break;

                            }
                        }
                        br.close();
                        if (line == null) {
                            JOptionPane.showMessageDialog(new JFrame(), "Error! Entered Unique ID doesn't exist in the database.");
                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

                    }
                } else if (selected.equals("dr3344j") && danny.equals(selected)) {
                    sendDisconnect();
                    Disconnect();
                    Onliners.setText("Online Members:");
                    combobox.removeAllItems();

                    address = dp;
                    try {
                        br = new BufferedReader(new FileReader(filepath1));

                        String line;

                        while ((line = br.readLine()) != null) {

                            if (line.equals(uID)) {
                                if (isConnected == false) {

                                    System.out.println("address main screen line 429: " + address);

                                    try {
                                        String encryptSignature = Encryption.Signature(input.getText());
                                        sock = new Socket(address, port);
                                        System.out.println("sock MainScreen 426: " + sock);
                                        InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                        reader = new BufferedReader(streamreader);
                                        writer = new PrintWriter(sock.getOutputStream());
                                        writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                        writer.flush();
                                        isConnected = true;
                                        uniqueId.setText(uID);
                                    } catch (Exception ex) {
                                        display.append("Cannot Connect! The server is Offline! Become a coordinator! \n");

                                    }

                                    ListenThread();

                                } else if (isConnected == true) {
                                    display.append(currTime() + "You are already connected. \n");
                                }
                                break;

                            }
                        }
                        br.close();
                        if (line == null) {
                            JOptionPane.showMessageDialog(new JFrame(), "Error! Entered Unique ID doesn't exist in the database.");
                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

                    }
                } else if (selected.equals("jn9942d") && jak.equals(selected)) {
                    sendDisconnect();
                    Disconnect();
                    Onliners.setText("Online Members:");
                    combobox.removeAllItems();

                    address = jp;
                    try {
                        br = new BufferedReader(new FileReader(filepath1));

                        String line;

                        while ((line = br.readLine()) != null) {

                            if (line.equals(uID)) {
                                if (isConnected == false) {

                                    System.out.println("address main screen line 429: " + address);

                                    try {
                                        String encryptSignature = Encryption.Signature(input.getText());
                                        sock = new Socket(address, port);
                                        System.out.println("sock MainScreen 426: " + sock);
                                        InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                        reader = new BufferedReader(streamreader);
                                        writer = new PrintWriter(sock.getOutputStream());
                                        writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                        writer.flush();
                                        isConnected = true;
                                        uniqueId.setText(uID);
                                    } catch (Exception ex) {
                                        display.append("Cannot Connect! The server is Offline! Become a coordinator! \n");

                                    }

                                    ListenThread();

                                } else if (isConnected == true) {
                                    display.append(currTime() + "You are already connected. \n");
                                }
                                break;

                            }
                        }
                        br.close();
                        if (line == null) {
                            JOptionPane.showMessageDialog(new JFrame(), "Error! Entered Unique ID doesn't exist in the database.");
                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

                    }
                } else if (selected.equals("bm4904f") && bar.equals(selected)) {
                    sendDisconnect();
                    Disconnect();
                    Onliners.setText("Online Members:");
                    combobox.removeAllItems();
                    address = bp;
                    try {
                        br = new BufferedReader(new FileReader(filepath1));

                        String line;

                        while ((line = br.readLine()) != null) {

                            if (line.equals(uID)) {
                                if (isConnected == false) {

                                    System.out.println("address main screen line 429: " + address);

                                    try {
                                        String encryptSignature = Encryption.Signature(input.getText());
                                        sock = new Socket(address, port);
                                        System.out.println("sock MainScreen 426: " + sock);
                                        InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                        reader = new BufferedReader(streamreader);
                                        writer = new PrintWriter(sock.getOutputStream());
                                        writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                        writer.flush();
                                        isConnected = true;
                                        uniqueId.setText(uID);
                                    } catch (Exception ex) {
                                        display.append("Cannot Connect! The server is Offline! Become a coordinator! \n");

                                    }

                                    ListenThread();

                                } else if (isConnected == true) {
                                    display.append(currTime() + "You are already connected. \n");
                                }
                                break;

                            }
                        }
                        br.close();
                        if (line == null) {
                            JOptionPane.showMessageDialog(new JFrame(), "Error! Entered Unique ID doesn't exist in the database.");
                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);

                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

                }//GEN-LAST:event_jButton3ActionPerformed

                private void inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputKeyPressed
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                        String nothing = "";
                        if ((input.getText()).equals(nothing)) {
                            input.setText("");
                            input.requestFocus();
                        } else {
                            try {

                                //KeyPair A = Encryption.GenerateKey1();
                                String messageToEncrypt = input.getText();
                                //PublicKey PubliKey = Encryption.PuK(A);
                                //PrivateKey PrivaKey = Encryption.PrK(A);

                                //byte[] MessageEncrypted = Encryption.EncryptionPart(messageToEncrypt, PubliKey);
                                String encryptSignature = Encryption.Signature(input.getText());

                                writer.println(username + ":" + input.getText() /*Arrays.toString(MessageEncrypted)*/ + ":" + "Chat" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                writer.flush(); // flushes the buffer

                            } catch (Exception ex) {
                                display.append("Message was not sent. \n");
                            }
                            input.setText("");
                            input.requestFocus();
                        }

                        input.setText("");
                        input.requestFocus();
                    }
                }//GEN-LAST:event_inputKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Component frame = null;

        if (JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to close this window?", "Close Window?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            sendDisconnect();
            Disconnect();
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing
    //Function to set the unique id.
    public void setUniqueId(String uId) {
        uniqueId.setText(uId);
    }

    //Function to get the current time.
    public String currTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("[HH.mm.ss] ");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
                    * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
                MainScreen temp = new MainScreen();
                temp.setResizable(false);
                temp.setLocationRelativeTo(null);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Onliners;
    private javax.swing.JButton b_connect1;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JComboBox<String> combobox;
    private javax.swing.JTextArea display;
    private javax.swing.JTextField input;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_port;
    private javax.swing.JButton sendText;
    private javax.swing.JTextArea serverInfo;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_port;
    public javax.swing.JLabel uniqueId;
    // End of variables declaration//GEN-END:variables

}
