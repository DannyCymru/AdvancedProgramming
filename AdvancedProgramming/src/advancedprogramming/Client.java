package clientserver;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame {
    private JButton one = new JButton("Contact Server"),
                    two = new JButton("Send text");
    private JTextArea input = new JTextArea(10,40);
    private PrintWriter pw;
    private Socket ss;
    private static Client c;
    public Client() {
        Container content = this.getContentPane();
        content.setLayout(new FlowLayout());
        content.add(one);
        one.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                contactServer();
            }
        });
        content.add(two);
        two.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                sendText();
            }
        });
        content.add(new JScrollPane(input));
    }
    public void contactServer() {
       try {
            ss = new Socket("127.0.0.1", 2000);
            OutputStream os = ss.getOutputStream();
            pw = new PrintWriter(os, true) ;
            pw.println("hello server");
       }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public void sendText() {
        int inputLines = input.getLineCount();
        try {
          for (int i = 0 ; i < inputLines; i++) {
                int start = input.getLineStartOffset(i);
                int end = input.getLineEndOffset(i);
                pw.print(input.getText(start, end - start));
          }
          pw.println();
        }
        catch (BadLocationException ble) {
          ble.printStackTrace();
        }
}
    public void closeConnection() {
        try {
            if (ss != null)
                ss.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public static void main(String[] args) {
        c = new Client();
        c.setSize(500,300);
        c.setVisible(true);
        c.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent wa) {
                c.closeConnection();
                System.exit(0);
            }
        });
    }
}
