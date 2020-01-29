
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
        ArrayList<String> data = new ArrayList<>();
        
        for (NetworkInterface Ips1 : NiList)  
        { 
             
             Enumeration<InetAddress> ipList = Ips1.getInetAddresses();
             
                while (ipList.hasMoreElements())  
                {   
                        String Intel = ipList.nextElement().toString();
                        data.add(Intel);
                } 
        }
       
        int SizeList = data.size();
        int i = 0;
        int j = 0;
        String LoopBackAdre=data.get(SizeList - 1);
        
        ArrayList<String> ipArray = new ArrayList<String>();
        
        while(i != SizeList)
        {
            String itemChecked = data.get(i);
            
            if(itemChecked.contains(".") && itemChecked != LoopBackAdre) 
            {
                ipArray.add(data.get(i));
                
            }
            
            i++;
        }
        System.out.println("ipArray: "+ ipArray);
        
    }
    
    
    
}



