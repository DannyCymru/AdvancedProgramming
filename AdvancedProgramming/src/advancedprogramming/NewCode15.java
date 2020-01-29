/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedprogramming;



import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author barnabe
 */

public class NewCode15{

    
public static void main(String args[]) throws SocketException  
    {
        
        ArrayList<NetworkInterface> NiList = Collections.list(NetworkInterface.getNetworkInterfaces()); 
        ArrayList<String> Data = new ArrayList<String>();
        
        for (NetworkInterface Ips1 : NiList)  
        { 
             
             Enumeration<InetAddress> Iplist = Ips1.getInetAddresses();
             
                while (Iplist.hasMoreElements())  
                {   
                        System.out.println("/1");
                        String Intel = Iplist.nextElement().toString();
                        System.out.println(Intel);
                        Data.add(Intel);
                        System.out.println("/2");
                        
                } 
                
                
        }
        System.out.println("data: "+Data);
        System.out.println("IP: "+ Data.get(7));
    }
    
    
    
}



