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
    String userId;
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
        users.add(data); //adds user to the users ArrayList used for Online members
    }

    public void userRemove(String data) {
        display.append(data + " is now offline.\n"); //tells people someone is now offline
    }

    public void writeUsers() {
        String[] tempList = new String[(users.size())]; //writes users into the array
        users.toArray(tempList);
    }

    public void sendserverdisconnect() { // sends server disconnect signal
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


    public void Disconnect() { //sends client disconnect signal
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

    public class IncomingReader implements Runnable { // used for all info that is coming from Server

        @Override
        public void run() {
            String[] data;
            String people[];
            String[] ips;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat", ClosedWind = "has disconnected";
            try {
                address = "";
                int i = 0;
                int x = 0;
                while ((stream = reader.readLine()) != null) {                                       
                    data = stream.split(":");
                    people = stream.split(":");

                    if (data[2].equals(chat) && data[1].equals(ClosedWind)) { // this if is used if the Coordinator disconnects
                        isConnected = false;                                    
                        String encryptSignature = Encryption.Signature(data[1]);
                    if (i == 0) { // used with digital sign messages and to display messages in all clients
                            display.append(currTime() + data[0] + ": " + data[1] + "\n");
                            display.setCaretPosition(display.getDocument().getLength());

                        } else if (encryptSignature.equals(data[4])) {
                            display.append(currTime() + data[0] + ": " + data[1] + "\n");
                            display.setCaretPosition(display.getDocument().getLength());

                        } else {
                            display.append(currTime() + data[0] + ": " + data[1] + " !messsage has been modified! " + "\n");                         
                            display.setCaretPosition(display.getDocument().getLength());
                         
                        }
                        i++;       
String newcon = "";
int ip;
users.clear();
display.append("Server closed! Reconnecting to the next available coordinator! \n");
Onliners.setText("Onliner members:" + "\n");
serverInfo.setText("");
List<String> list = null ;

                BufferedReader br = new BufferedReader(new FileReader("ips.txt"));
                Scanner inFile1 = new Scanner(new File("ips.txt")).useDelimiter(":");

                // Original answer used LinkedList, but probably preferable to use ArrayList in most cases
                // List<String> temps = new LinkedList<String>();
                List<String> temps = new ArrayList<String>();
                String token1 = "";
                while (inFile1.hasNext()) { //reads text file and adds to the list string all components
                    // find next line
                    token1 = inFile1.next();
                    temps.add(token1);
                }
                inFile1.close();
                String[] tempsArray = temps.toArray(new String[0]);
                        String[] hel = null;                        
                for (String s : tempsArray) { 
                    hel = tempsArray;
                    list = Arrays.asList(hel);                
                }
                       address = newcon;        
                for (x = 1; x < list.size(); ){ // loop for getting all Ips from connected clients to reconnect to the next ip address
                            ip = temps.indexOf(list.get(x));                           
                             newcon = hel[ip];                            
                             x+=2;
                   address = newcon;
            if (isConnected == false) {

                try {                   
                    sock = new Socket(address, port);                    
                    InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(sock.getOutputStream());
                    writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                    writer.flush();
                    isConnected = true;
                    uniqueId.setText(userId);
                    serverInfo.setText("");
                    serverInfo.append("Host:" + newcon + "\n");
                    serverInfo.append("My IP:" + NetInterface.IpUser() + "\n");
                    Connected = true;
                    FileWriter writer = new FileWriter("ips.txt", false);
                    writer.write("");
                    writer.close();
                    if (Connected) {
                        Ipnew = address;
                    }
                } catch (Exception ex) {                   
                }
                ListenThread();
            } else if (isConnected == true) {
                display.append(currTime() + "You are already connected. \n");
            }
         
                }
      
                    } else if (data[2].equals(chat)) { //used to receive text from server that needs to be displayed

                        Scanner inFile1 = new Scanner(new File("ips.txt")).useDelimiter(":");
                        String joinedString = String.join(":", data);

                        ips = joinedString.split(":");
                        String op = ips[3];

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
                           

                            for (String str : list) {
                                if (str.trim().contains(data[0])) {
                                
                                    writ = false;
                                }
                            }
                        }
                        if (writ) {

                            FileWriter writer = new FileWriter("ips.txt", true);
                            writer.write(ips[0] + ":" + ips[3] + ":");
                            writer.close();
                        }

                        String encryptSignature = Encryption.Signature(data[1]);

                        if (i == 0) {
                            display.append(currTime() + data[0] + ": " + data[1] + "\n");
                            display.setCaretPosition(display.getDocument().getLength());

                        } else if (encryptSignature.equals(data[4])) {
                            display.append(currTime() + data[0] + ": " + data[1] + "\n");
                            display.setCaretPosition(display.getDocument().getLength());
                           

                        } else {
                            display.append(currTime() + data[0] + ": " + data[1] + " !messsage has been modified! " + "\n");
                        
                            display.setCaretPosition(display.getDocument().getLength());
                          
                        }
                        i++;
                    } else if (data[2].equals(connect)) { // receives connect signal from server and adds user to the system

                        display.removeAll();
                        userAdd(data[0]);
                        String conne = Arrays.toString(people);
                        String replace = conne.replace(",", "");
                        String replace1 = replace.replace("Connect", "");
                        String replace2 = replace1.replace(" ", "");

                        Onliners.setText("Online Members" + "\n");
                        cbOnliners.removeAllItems();
                        Onliners.append(users.get(0) + "Coordinator");
                        Onliners.append("\n");

                        for (String current_user : users) {
                            cbOnliners.addItem(current_user);
                        }
                        for (int z = 1; z < users.size(); z++) {
                            Onliners.append(users.get(z));
                            Onliners.append("\n");

                        }

                    } else if (data[2].equals(disconnect)) { //receives disconnect of one client from the server and removes him.

                        userRemove(data[0]);
                        String conne = Arrays.toString(people);
                        String replace = conne.replace(",", "");
                        String replace1 = replace.replace("Connect", "");
                        String replace2 = replace1.replace(" ", "");
                        users.remove(replace2);
                        cbOnliners.removeAllItems();
                        Onliners.append(users.get(0) + "Coordinator");
                        Onliners.append("\n");

                        for (String current_user : users) {
                            cbOnliners.removeItem(current_user);
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
        btnsendtxt = new javax.swing.JButton();
        input = new javax.swing.JTextField();
        JtxtAdress = new javax.swing.JTextField();
        lb_address = new javax.swing.JLabel();
        lb_port = new javax.swing.JLabel();
        JtxtPort = new javax.swing.JTextField();
        btnconnect = new javax.swing.JButton();
        btndisconnect = new javax.swing.JButton();
        cbOnliners = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnChat = new javax.swing.JButton();

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
        jScrollPane4.setViewportView(serverInfo);

        uniqueId.setText("Unique ID");
        uniqueId.setRequestFocusEnabled(false);
        uniqueId.setVerifyInputWhenFocusTarget(false);

        btnsendtxt.setText("Send");
        btnsendtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsendtxtActionPerformed(evt);
            }
        });

        input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputKeyPressed(evt);
            }
        });

        try
        {
            JtxtAdress.setText(NetInterface.IpUser());
        }catch (Exception ex)
        {

        }

        lb_address.setText("Address : ");

        lb_port.setText("Port :");

        JtxtPort.setText("7721");

        btnconnect.setText("Connect");
        btnconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconnectActionPerformed(evt);
            }
        });

        btndisconnect.setText("Disconnect");
        btndisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndisconnectActionPerformed(evt);
            }
        });

        jLabel1.setText("Private chat: ");

        btnChat.setText("Chat");
        btnChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb_address, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(JtxtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lb_port, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JtxtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnconnect)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btndisconnect))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(uniqueId)))
                        .addGap(0, 461, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbOnliners, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(input)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnChat)
                                    .addComponent(btnsendtxt))))))
                .addContainerGap())
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
                            .addComponent(uniqueId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(input, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                .addComponent(btnsendtxt))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChat)
                    .addComponent(cbOnliners, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnconnect)
                    .addComponent(btndisconnect)
                    .addComponent(JtxtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_port, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JtxtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_address, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        uniqueId.getAccessibleContext().setAccessibleName("uniqueIdLabel");
        uniqueId.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void btnsendtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsendtxtActionPerformed
// sends message to the server and other clients
            String nothing = "";
            if ((input.getText()).equals(nothing)) {
                input.setText("");
                input.requestFocus();
            } else {
                try {
                    String messageToEncrypt = input.getText();
                    String encryptSignature = Encryption.Signature(input.getText());
                    writer.println(username + ":" + input.getText() + ":" + "Chat" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                    writer.flush(); // flushes the buffer

                } catch (Exception ex) {
                    display.append("Message was not sent. \n");
                }
                input.setText("");
                input.requestFocus();
            }

            input.setText("");
            input.requestFocus();


        }//GEN-LAST:event_btnsendtxtActionPerformed

        private void btndisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndisconnectActionPerformed
            //disconnect button to disconnect from the server
            sendserverdisconnect();
            Disconnect();
            Onliners.setText("Online Members:");
            cbOnliners.removeAllItems();
        }//GEN-LAST:event_btndisconnectActionPerformed

        private void btnconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconnectActionPerformed
// connect button to connect to the server
            address = JtxtAdress.getText();
            if (isConnected == false) {

                try {
                    String encryptSignature = Encryption.Signature(input.getText());
                    sock = new Socket(address, port);                
                    InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(sock.getOutputStream());
                    writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                    writer.flush();
                    isConnected = true;
                    uniqueId.setText(userId);
                    serverInfo.setText("");
                    serverInfo.append("Host:" + address + "\n");
                    serverInfo.append("My IP:" + NetInterface.IpUser() + "\n");
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

        }//GEN-LAST:event_btnconnectActionPerformed

        private void btnChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatActionPerformed
//btn to make a private chat with other person that is connected to the same server
            String selected = cbOnliners.getSelectedItem().toString();

            try {
                BufferedReader br = new BufferedReader(new FileReader("ips.txt"));
                String dan = "", dp = "", jak = "", jp = "", danny = "", dap = "", bar = "", bp = "";
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
                    for (String str : list) {
                        int ip;

                        if (str.trim().contains("dm5376y"))//finds the ip of dm5376y 
                        {
                           
                            ip = list.indexOf("dm5376y"); //gets location of dm5376y
                            dan = hel[ip]; //gets his location in txt file
                            dp = hel[ip + 1];//gets his ip location in txt file
                        } else if (str.trim().contains("jn9942d")) { //find the ip of jn9942d
                        
                            ip = list.indexOf("jn9942d");//gets location of  jn9942d
                            jak = hel[ip];//gets his location in txt file
                            jp = hel[ip + 1];//gets his ip location in txt file
                        } else if (str.trim().contains("dr3344j")) { //find the ip of dr3344j
                         
                            ip = list.indexOf("dr3344j");//gets location of  dr3344j
                            danny = hel[ip];//gets his location in txt file
                            dap = hel[ip + 1];//gets his ip location in txt file
                        } else if (str.trim().contains("bm4904f")) { //find the ip of bm4404f
                          
                            ip = list.indexOf("bm4904f");//gets location of bm4904f 
                            bar = hel[ip];//gets his location in txt file
                            bp = hel[ip + 1];//gets his ip location in txt file
                        }
                    }
                }
                if (selected.equals(username)) { //catches if someone wants to start a private messages with himself
                    Component frame = null;
                    JOptionPane.showMessageDialog(frame, "You can't private chat with yourself", "Error", JOptionPane.ERROR_MESSAGE);

                } else if (selected.equals("dm5376y") && dan.equals(selected)) { //starts a private message with dm5376y
                    sendserverdisconnect();
                    Disconnect();
                    Onliners.setText("Online Members:");
                    cbOnliners.removeAllItems();
                    address = dp;
                    try {
                        br = new BufferedReader(new FileReader(filepath1));

                        String line;

                        while ((line = br.readLine()) != null) {

                            if (line.equals(userId)) {
                                if (isConnected == false) {//connects to the user

                                    try {
                                        String encryptSignature = Encryption.Signature(input.getText());
                                        sock = new Socket(address, port);
                                     
                                        InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                        reader = new BufferedReader(streamreader);
                                        writer = new PrintWriter(sock.getOutputStream());
                                        writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                        writer.flush();
                                        isConnected = true;
                                        uniqueId.setText(userId);
                                        serverInfo.setText("");
                                        serverInfo.append("Host:" + address + "\n");
                                        serverInfo.append("My IP:" + NetInterface.IpUser() + "\n");
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
                } else if (selected.equals("dr3344j") && danny.equals(selected)) {//starts a private message with dr3344j
                    sendserverdisconnect();
                    Disconnect();
                    Onliners.setText("Online Members:");
                    cbOnliners.removeAllItems();

                    address = dp;
                    try {
                        br = new BufferedReader(new FileReader(filepath1));

                        String line;

                        while ((line = br.readLine()) != null) {

                            if (line.equals(userId)) {
                                if (isConnected == false) {//connects to the user

                                    try {
                                        String encryptSignature = Encryption.Signature(input.getText());
                                        sock = new Socket(address, port);                                     
                                        InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                        reader = new BufferedReader(streamreader);
                                        writer = new PrintWriter(sock.getOutputStream());
                                        writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                        writer.flush();
                                        isConnected = true;
                                        uniqueId.setText(userId);
                                        serverInfo.setText("");
                                        serverInfo.append("Host:" + address + "\n");
                                        serverInfo.append("My IP:" + NetInterface.IpUser() + "\n");
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
                } else if (selected.equals("jn9942d") && jak.equals(selected)) {//starts a private message with jn9942d
                    sendserverdisconnect();
                    Disconnect();
                    Onliners.setText("Online Members:");
                    cbOnliners.removeAllItems();
                    address = jp;
                    try {
                        br = new BufferedReader(new FileReader(filepath1));

                        String line;

                        while ((line = br.readLine()) != null) {

                            if (line.equals(userId)) {
                                if (isConnected == false) {//connects to the user

                                    try {
                                        String encryptSignature = Encryption.Signature(input.getText());
                                        sock = new Socket(address, port);
                                    
                                        InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                        reader = new BufferedReader(streamreader);
                                        writer = new PrintWriter(sock.getOutputStream());
                                        writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                        writer.flush();
                                        isConnected = true;
                                        uniqueId.setText(userId);
                                        serverInfo.setText("");
                                        serverInfo.append("Host:" + address + "\n");
                                        serverInfo.append("My IP:" + NetInterface.IpUser() + "\n");
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
                } else if (selected.equals("bm4904f") && bar.equals(selected)) {//starts a private message with bm4904f
                    sendserverdisconnect();
                    Disconnect();
                    Onliners.setText("Online Members:");
                    cbOnliners.removeAllItems();
                    address = bp;
                    try {
                        br = new BufferedReader(new FileReader(filepath1));

                        String line;

                        while ((line = br.readLine()) != null) {

                            if (line.equals(userId)) {
                                if (isConnected == false) { //connects to the user

                                    try {
                                        String encryptSignature = Encryption.Signature(input.getText());
                                        sock = new Socket(address, port);                                  
                                        InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                                        reader = new BufferedReader(streamreader);
                                        writer = new PrintWriter(sock.getOutputStream());
                                        writer.println(username + ":has connected.:Connect" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
                                        writer.flush();
                                        isConnected = true;
                                        uniqueId.setText(userId);
                                        serverInfo.setText("");
                                        serverInfo.append("Host:" + address + "\n");
                                        serverInfo.append("My IP:" + NetInterface.IpUser() + "\n");
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

                }//GEN-LAST:event_btnChatActionPerformed

                private void inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputKeyPressed
                 //sends message on the ENTER key press
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        String nothing = "";
                        if ((input.getText()).equals(nothing)) {
                            input.setText("");
                            input.requestFocus();
                        } else {
                            try {
                                String messageToEncrypt = input.getText();  
                                String encryptSignature = Encryption.Signature(input.getText());
                                writer.println(username + ":" + input.getText() + ":" + "Chat" + ":" + NetInterface.IpUser() + ":" + encryptSignature);
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
        //window closing event to show confirm dialog and also send server the needable messages
        Component frame = null;
        if (JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to close this window?", "Close Window?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            sendserverdisconnect();
            Disconnect();
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing
    //Function to set the unique id.
    public void setUniqueId(String uId) {
        uniqueId.setText(uId);
        ID = uniqueId.getText();
        userId = uniqueId.getText();
        username = uniqueId.getText();
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
        java.awt.EventQueue.invokeLater(() -> {
            new MainScreen().setVisible(true);
            MainScreen temp = new MainScreen();
            temp.setResizable(false);
            temp.setLocationRelativeTo(null);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JtxtAdress;
    private javax.swing.JTextField JtxtPort;
    private javax.swing.JTextArea Onliners;
    private javax.swing.JButton btnChat;
    private javax.swing.JButton btnconnect;
    private javax.swing.JButton btndisconnect;
    private javax.swing.JButton btnsendtxt;
    private javax.swing.JComboBox<String> cbOnliners;
    private javax.swing.JTextArea display;
    private javax.swing.JTextField input;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_port;
    private javax.swing.JTextArea serverInfo;
    public javax.swing.JLabel uniqueId;
    // End of variables declaration//GEN-END:variables

}
