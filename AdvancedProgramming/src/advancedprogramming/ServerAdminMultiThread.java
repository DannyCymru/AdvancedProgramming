package clientserver;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

// Very simple multithreaded server that spins a thread
// for each client connection.

class HandleConnection extends Thread {

  Socket s = null;
  JTextArea display;
  public HandleConnection(Socket s, JTextArea display) {
  this.s = s;
  this.display = display;
}
public void run() {
    display.append("New thread started\n");
    try {
    InputStream is = s.getInputStream();
    BufferedReader br = new BufferedReader (new InputStreamReader(is));
    String str = br.readLine();
    while (str != null) {
          display.append("received from client : " + str + "\n");
          str = br.readLine();
      }
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    display.append("Thread ending\n");
} // end of run()
}
class MultiThreadedServer extends Thread {
  private JTextArea display;
  public MultiThreadedServer(JTextArea display) {
      this.display = display;
  }
  public void run() {
      display.append("Server starting\n");
      try {
          // wait for a client connection
          ServerSocket ss = new ServerSocket(2000);
          // then a spin off a thread to handle it
          while (true) {
             Socket mySocket = ss.accept();
             HandleConnection hc = new HandleConnection(mySocket, display);
             hc.start();
          }
      }
      catch (IOException ioe) {
          ioe.printStackTrace();
      }
      display.append("Server closing\n");
  }
}
public class ServerAdminMultiThread extends JFrame implements ActionListener {
    private JButton one = new JButton("Start Server");
    private JTextArea display = new JTextArea(10,40);
    private Thread theServer = new MultiThreadedServer(display);
    public ServerAdminMultiThread() {
        Container appletContent = this.getContentPane();
        appletContent.setLayout(new FlowLayout());
        appletContent.add(one);
        one.addActionListener(this);
        appletContent.add(new JScrollPane(display));
    }
    public void actionPerformed(ActionEvent event) {
          theServer.start();
    }
    public static void main(String[] args) {
        ServerAdminMultiThread sa = new ServerAdminMultiThread();
        sa.setSize(500,300);
        sa.setVisible(true);
        sa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
