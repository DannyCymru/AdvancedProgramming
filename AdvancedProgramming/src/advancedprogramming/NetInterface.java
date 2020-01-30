
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
        
        ArrayList<NetworkInterface> NiList = Collections.list(NetworkInterface.getNetworkInterfaces());//grab the network interface and store as a list
        ArrayList<String> data = new ArrayList<>();                                                    // network interface is a class it s made up of names and list of IP addresses 
        
        for (NetworkInterface Ips1 : NiList)  
        { 
             
             Enumeration<InetAddress> ipList = Ips1.getInetAddresses(); //InetAddress is a method used to get IP address of any hostname which in this case would be the IPs of where the code run
             
                while (ipList.hasMoreElements())  
                {   
                        String Intel = ipList.nextElement().toString(); // here i am changing all the data from ipList which are of type InetAddress and changing 
                                                                        //them to string as it is easier for me to manipulate
                        data.add(Intel);                                // and then add them to an arraylist
                } 
        }
       
        int SizeList = data.size();
        int i = 0;
        
        String LoopBackAdre=data.get(SizeList - 1);// the minus one here is used to avoid an outof bound error though i wish to make
                                                   //LoopBackAdre = /127.0.0.1 to remove the loopbackaddress from the list but it appear to not recogonize it when i do so  (will improve when i can )        
        ArrayList<String> ipArray = new ArrayList<String>();
        
        while(i != SizeList)                        // this while loop should be removing all the wrong addresses and the loopback address
                                                    // by removing all address with "." in and by removing the last item of the list data (which is technically the loopback though i need to check)
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



