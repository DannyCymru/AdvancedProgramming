/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedprogramming;

<<<<<<< HEAD
import advancedprogramming.Serv;
import advancedprogramming.NetInterface;
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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
=======
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
>>>>>>> gui

/**
 *
 * @author dm5376y
 */
public class MainScreen extends javax.swing.JFrame {
<<<<<<< HEAD
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
   
=======

    String ID = "";
    String listPort = "";
    String conAddr = "";

    /**
     * Creates new form MainScreen
     */
    public MainScreen() {
        initComponents();
        sendText.setVisible(false);
        input.setEditable(true);
    }

// Very simple multithreaded server that spins a thread
// for each client connection.
     private PrintWriter pw;
    private Socket ss;

    public void contactServer() {
        try {
            ss = new Socket("127.0.0.1", 2000);
            OutputStream os = ss.getOutputStream();
            pw = new PrintWriter(os, true);
        } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(this, "You coudn't connect to the server successfully, possibly server is offline or your connection details are invalid", "Connection Error!", JOptionPane.OK_OPTION);

        }
    }

    public void sendText() {
        int inputLines = input.getLineCount();
        try {
            for (int i = 0; i < inputLines; i++) {
                int start = input.getLineStartOffset(i);
                int end = input.getLineEndOffset(i);
                pw.print(input.getText(start, end - start));
            }
            pw.println();
        } catch (BadLocationException ble) {
            ble.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (ss != null) {
                ss.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

>>>>>>> gui
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
<<<<<<< HEAD
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
=======
        connectButton = new javax.swing.JButton();
        uniqueId = new javax.swing.JLabel();
        sendText = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        input = new javax.swing.JTextArea();
        jbtnAdmin = new javax.swing.JButton();
>>>>>>> gui

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

<<<<<<< HEAD
=======
        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

>>>>>>> gui
        uniqueId.setText("Unique ID");
        uniqueId.setRequestFocusEnabled(false);
        uniqueId.setVerifyInputWhenFocusTarget(false);

        sendText.setText("Send");
        sendText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendTextActionPerformed(evt);
            }
        });

<<<<<<< HEAD
        tf_address.setText(NetInterface.IpUser());
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
=======
        input.setColumns(20);
        input.setRows(5);
        jScrollPane2.setViewportView(input);

        jbtnAdmin.setText("Admin Panel");
        jbtnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAdminActionPerformed(evt);
>>>>>>> gui
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
<<<<<<< HEAD
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
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(sendText)
                                .addGap(18, 18, 18)
                                .addComponent(b_connect1))
                            .addComponent(jScrollPane1))
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
=======
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jbtnAdmin)
                        .addGap(18, 18, 18)
                        .addComponent(uniqueId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendText)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(connectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
>>>>>>> gui
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
<<<<<<< HEAD
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
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
=======
                .addGap(298, 298, 298)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(uniqueId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendText)
                    .addComponent(jbtnAdmin))
                .addGap(32, 32, 32))
>>>>>>> gui
        );

        uniqueId.getAccessibleContext().setAccessibleName("uniqueIdLabel");
        uniqueId.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

<<<<<<< HEAD
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
            System.out.println("address main screen421: "+address);

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
                
                /* barney: i added this so that if server  inactive it 
                automaticaly launches the server panel*/
                Serv try1 = new Serv(); 
                Serv.main(null);
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
        
    }//GEN-LAST:event_jButton2ActionPerformed
=======
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        // connect button to show connect screen and remove button
        new ConScreen().setVisible(true);
        dispose();
    }//GEN-LAST:event_connectButtonActionPerformed

    private void sendTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendTextActionPerformed
        sendText();
        input.setText(null);
    }//GEN-LAST:event_sendTextActionPerformed

    private void jbtnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAdminActionPerformed
        new AdminPanel().setVisible(true);
    }//GEN-LAST:event_jbtnAdminActionPerformed
>>>>>>> gui

    //Function to set the unique id.
    public void setUniqueId(String uId) {
        uniqueId.setText(uId);
<<<<<<< HEAD
         ID = uniqueId.getText();
     
                
=======
        ID = uId;
                contactServer();
>>>>>>> gui

    }
    
    //Activates and allows for the use of sending text.
<<<<<<< HEAD

=======
    public void activateSendButton(){
        sendText.setVisible(true);
        input.setEditable(true);
        currTime();
    }
>>>>>>> gui
    
    //Function to create a new thread and connect to the server.
  
    
    //Function to get the current time.
    public String currTime() {
<<<<<<< HEAD
        SimpleDateFormat formatter= new SimpleDateFormat("[HH.mm.ss] ");
=======
        SimpleDateFormat formatter= new SimpleDateFormat("[HH:mm:ss] ");
>>>>>>> gui
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
        }
    

<<<<<<< HEAD
    
=======
    public void setlisteningPort(String port) {
        listPort= port;
    }

    //Saves the address in the pre initialised variable conAddr for future use
    //Then sets the server info to use the current address.
    public void setConAddress(String address) {
        conAddr=address;
        serverInfo.setText(conAddr);
        
    }

>>>>>>> gui
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
<<<<<<< HEAD
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Onliners;
    private javax.swing.JButton b_connect1;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JTextArea display;
    private javax.swing.JTextField input;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
=======
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Onliners;
    private javax.swing.JButton connectButton;
    private javax.swing.JTextArea display;
    private javax.swing.JTextArea input;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbtnAdmin;
    private javax.swing.JButton sendText;
    private javax.swing.JTextArea serverInfo;
>>>>>>> gui
    public javax.swing.JLabel uniqueId;
    // End of variables declaration//GEN-END:variables
}
