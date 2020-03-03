/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedprogramming;

import advancedprogramming.Serv;
import advancedprogramming.NetInterface;
import advancedprogramming.NetInterface;
import advancedprogramming.Serv;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
    
    String username, address;
    ArrayList<String> users = new ArrayList();
    int port = 7721;
    Boolean isConnected = false;
    
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    
    
    //--------------------------//
    
    public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    //--------------------------//
    
    public void userAdd(String data) 
    {
         users.add(data);
    }
    
    //--------------------------//
    
    public void userRemove(String data) 
    {
         display.append(data + " is now offline.\n");
    }
    
    //--------------------------//
    
    public void writeUsers() 
    {
         String[] tempList = new String[(users.size())];
         users.toArray(tempList);
         for (String token:tempList) 
         {
             //users.append(token + "\n");
         }
    }
    
    //--------------------------//
    
    public void sendDisconnect() 
    {
        String bye = (username + ": :Disconnect");
        try
        {
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e) 
        {
            display.append(currTime() + "Could not send Disconnect message.\n");
        }
    }

    //--------------------------//
    
    public void Disconnect() 
    {
        try 
        {
            display.append(currTime() + "Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
            display.append(currTime() + "Failed to disconnect. \n");
        }
        isConnected = false;
        tf_username.setEditable(true);

    }
    

    public MainScreen() {

        initComponents();   
        
    }
    
 public class IncomingReader implements Runnable
    {
        @Override
        public void run() 
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try 
            {
                while ((stream = reader.readLine()) != null) 
                {
                     data = stream.split(":");

                     if (data[2].equals(chat)) 
                     {
                        display.append(currTime() + data[0] + ": " + data[1] + "\n");
                        display.setCaretPosition(display.getDocument().getLength());
                     } 
                     else if (data[2].equals(connect))
                     {
                        display.removeAll();
                        userAdd(data[0]);
                     } 
                     else if (data[2].equals(disconnect)) 
                     {
                         userRemove(data[0]);
                     } 
                     else if (data[2].equals(done)) 
                     {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                     }
                }
           }catch(Exception ex) { }
        }
    }
    
 
    ArrayList clientOutputStreams;
     ArrayList<String> user1; // user1 is for the server users that it receives (Trying to combine them with client users breaks everything)
// This is server part Handling client
    public class ClientHandler implements Runnable
    {
        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter user)
        {
            client = user;
            try
            {
                sock = clientSocket;

                //System.out.println("client socket: "+sock);
                display.append(currTime() + "client socket: "+sock+"\n");

                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex)
            {
                display.append(currTime() + "Unexpected error... \n");
            }

        }

        @Override
        public void run()
        {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try
            { //looks into every situation that it gets from the server if it is connect or sent message or dissconnect
                while ((message = reader.readLine()) != null)
                {
                    display.append(currTime() + "Received: " + message + "\n");
                    data = message.split(":");

                    if (data[2].equals(connect))
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd1(data[0]);

                    }
                    else if (data[2].equals(disconnect))
                    {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove1(data[0]);
                    }
                    else if (data[2].equals(chat))
                    {
                        tellEveryone(message);
                    }
                    else
                    {
                        display.append(currTime() + "No Conditions were met. \n");
                    }
                }
            }
            catch (Exception ex)
            {
                display.append(currTime() + "Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
            }
        }
    }

    /**
    * Creates new form AdminPanel
    */
 
    
     


//server side for user Adding
    public void userAdd1 (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        display.append(currTime() +"Before " + name + " added. \n");
        user1.add(name);
        display.append(currTime() +"After " + name + " added. \n");
        String[] tempList = new String[(user1.size())];
        user1.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
//Server side for removing users
    public void userRemove1 (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        user1.remove(name);
        String[] tempList = new String[(user1.size())];
        user1.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
//tell everyone is smth that server is using to send to the client
    public void tellEveryone(String message)
    {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext())
        {
            try
            {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                display.append(currTime() + "Sending: " + message + "\n");
                writer.flush();
                display.setCaretPosition(display.getDocument().getLength());

            }
            catch (Exception ex)
            {
                display.append(currTime() +"Error telling everyone. \n");
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
        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField();
        b_connect1 = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        serverInfo.setText("Host");
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

        try
        {
            tf_address.setText(NetInterface.PulicIp());
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

        lb_username.setText("Username :");

        tf_username.setText("dm5376y");
        tf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usernameActionPerformed(evt);
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

        jButton2.setText("online ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Start");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(uniqueId)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(sendText)
                                        .addGap(18, 18, 18)
                                        .addComponent(b_connect1)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b_disconnect)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb_username)
                                .addGap(18, 18, 18)
                                .addComponent(tf_username, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(lb_address, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lb_port, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(589, 589, 589)
                                .addComponent(jButton2)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(uniqueId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendText)
                            .addComponent(b_connect1)
                            .addComponent(b_disconnect)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_address, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_port, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_username, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
               writer.println(username + ":" + input.getText() + ":" + "Chat");
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

    private void tf_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_usernameActionPerformed

    }//GEN-LAST:event_tf_usernameActionPerformed

    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_disconnectActionPerformed
        sendDisconnect();
        Disconnect();
    }//GEN-LAST:event_b_disconnectActionPerformed

    private void b_connect1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_connect1ActionPerformed
         
         
    String uId = tf_username.getText();
    address =  tf_address.getText();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath1));

            String line;

            while ((line = br.readLine()) != null) {

                if (line.equals(uId)) {
                    if (isConnected == false) 
        {
            username = tf_username.getText();
            tf_username.setEditable(false);
            System.out.println("address main screen line 429: "+address);

            try 
            {
                sock = new Socket(address, port);
                System.out.println("sock MainScreen 426: "+sock);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true;
                uniqueId.setText(uId);
            } 
            catch (Exception ex) 
            {
                display.append("Cannot Connect! The server is Offline! Become a coordinator! \n");
                tf_username.setEditable(true);
                
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
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
            
        }catch (IOException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        
    }//GEN-LAST:event_b_connect1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Onliners.append("\n " + currTime() + "Online users : \n");
        for (String current_user : user1)
        {
            Onliners.append(current_user);
            Onliners.append("\n");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       Thread starter = new Thread(new ServerStart());
        starter.start();
        display.append("Server Started...");
    }//GEN-LAST:event_jButton3ActionPerformed
    //Function to set the unique id.
    public void setUniqueId(String uId) {
        uniqueId.setText(uId);
         ID = uniqueId.getText();
     
                

    }
    
    //Activates and allows for the use of sending text.

    
    //Function to create a new thread and connect to the server.
  
    
    //Function to get the current time.
    public String currTime() {
        SimpleDateFormat formatter= new SimpleDateFormat("[HH.mm.ss] ");
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
            }
                       
        });
        
        
        
       
    }
    
    
    
    
       
   
    //SERVER START!!!! THIS IS THE PART WHERE SERVER IS MADE AND IS CALLED FROM THE START BUTTON ATM.
     
    public class ServerStart implements Runnable
    {
        @Override
        public void run()
        {
            clientOutputStreams = new ArrayList();
            user1 = new ArrayList();

            try
            {
                InetAddress IpAddrs = InetAddress.getByName(NetInterface.IpUser());
                
                ServerSocket serverSock = new ServerSocket(7721, 50, IpAddrs);
                display.append(currTime() +"Socket: "+ serverSock +"\n");
                System.out.println("serverSock.getInetAddress(): "+serverSock.getInetAddress());

                while (true)
                {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    display.append(currTime() +"Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                display.append(currTime() +"Error making a connection. \n");
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Onliners;
    private javax.swing.JButton b_connect1;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JTextArea display;
    private javax.swing.JTextField input;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_username;
    private javax.swing.JButton sendText;
    private javax.swing.JTextArea serverInfo;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_port;
    private javax.swing.JTextField tf_username;
    public javax.swing.JLabel uniqueId;
    // End of variables declaration//GEN-END:variables
}
