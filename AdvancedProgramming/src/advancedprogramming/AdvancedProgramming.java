/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedprogramming;

import advancedprogramming.MainScreen;
import java.io.BufferedReader;
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
/**
 *
 * @author dm5376y
 */
public class AdvancedProgramming {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        new MainScreen().setVisible(true);
        Thread starter;
        
        
        
    }
    
    
    
    
     ArrayList clientOutputStreams;
     ArrayList<String> users; // user1 is for the server users that it receives (Trying to combine them with client users breaks everything)
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
                
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex)
            {
                
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
                    
                    data = message.split(":");

                    if (data[2].equals(connect))
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);

                    }
                    else if (data[2].equals(disconnect))
                    {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    }
                    else if (data[2].equals(chat))
                    {
                        tellEveryone(message);
                    }
                    else
                    {
                        
                    }
                }
            }
            catch (Exception ex)
            {
                
                ex.printStackTrace();
                clientOutputStreams.remove(client);
            }
        }
    }
    
    
    
    
      //SERVER START!!!! THIS IS THE PART WHERE SERVER IS MADE AND IS CALLED FROM THE START BUTTON ATM.
     
    public class ServerStart implements Runnable
    {
        @Override
        public void run()
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();

            try
            {
                InetAddress IpAddrs = InetAddress.getByName(NetInterface.IpUser());
                
                ServerSocket serverSock = new ServerSocket(7721, 50, IpAddrs);
                
                System.out.println("serverSock.getInetAddress(): "+serverSock.getInetAddress());

                while (true)
                {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    
                }
            }
            catch (Exception ex)
            {
                
            }
        }
    }
    
    
        public void userAdd (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        
        users.add(name);
        
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void userRemove (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void tellEveryone(String message)
    {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext())
        {
            try
            {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
                

            }
            catch (Exception ex)
            {
                
            }
        }
    }
    
    public String currTime() {
        SimpleDateFormat formatter= new SimpleDateFormat("[HH.mm.ss] ");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    
}