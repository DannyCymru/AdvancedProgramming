
package advancedprogramming;



import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 *
 * @author barnabe
 */

public class NetInterface{

    
public static void main(String args[]) throws SocketException  
    {
        
        ArrayList<NetworkInterface> NiList = Collections.list(NetworkInterface.getNetworkInterfaces()); 
        ArrayList<String> Data = new ArrayList<>();
        
        for (NetworkInterface Ips1 : NiList)  
        { 
             
             Enumeration<InetAddress> Iplist = Ips1.getInetAddresses();
             
                while (Iplist.hasMoreElements())  
                {   
                        String Intel = Iplist.nextElement().toString();
                        Data.add(Intel);
                } 
        }
       
        int SizeList = Data.size();
        int i = 0;
        int j = 0;
        String LoopBackAdre=Data.get(SizeList - 1);
        
        ArrayList<String> IPs = new ArrayList<String>();
        
        while(i != SizeList)
        {
            String ItemCheked = Data.get(i);
            
            if(ItemCheked.contains(".") && ItemCheked != LoopBackAdre) 
            {
                IPs.add(Data.get(i));
                
            }
            
            i++;
        }
        System.out.println("IPs: "+ IPs);
        
    }
    
    
    
}



