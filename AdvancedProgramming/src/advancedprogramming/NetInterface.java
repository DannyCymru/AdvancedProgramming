
package advancedprogramming;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
*
* @author barnabe
*/

public class NetInterface{



    public static void main(String args[]) throws Exception
    {
        //IpUser();
        //PulicIp(); 
        CheckIps();
    }


    static ArrayList<String> GetIp() throws SocketException
    {


        //grab the network interface and store as a list

        ArrayList<NetworkInterface> NiList = Collections.list(NetworkInterface.getNetworkInterfaces());

        // network interface is a class it s made up of names and list of IP addresses

        ArrayList<String> data = new ArrayList<>();

        for (NetworkInterface Ips1 : NiList)
        {
            /*InetAddress is a method used to get IP address of any hostname which in
            this case would be the IPs of where the code run*/

            Enumeration<InetAddress> ipList = Ips1.getInetAddresses();

            while (ipList.hasMoreElements())
            {
                // here i am changing all the data from ipList which are of type InetAddress and changing
                //them to string as it is easier for me to manipulate

                String Intel = ipList.nextElement().toString();

                // and then add them to an arraylist
                //System.out.println("Intel:"+Intel);
                data.add(Intel);
            }
        }

        int SizeList = data.size();
        int i = 0;

        /*the minus one here is used to avoid an outof bound error though i wish to make
        LoopBackAdre = /127.0.0.1 to remove the loopbackaddress from the list
        but it appear to not recogonize it when i do so  (will improve when i can )*/

        String LoopBackAdre=data.get(SizeList - 1);

        ArrayList<String> ipArray = new ArrayList<String>();

        /*this while loop should be removing all the wrong addresses and the loopback address
        by removing all address with "." in and by removing the last item of the list data
        (which is technically the loopback though i need to check*/

        while(i != SizeList)
        {
            String itemChecked = data.get(i);

            if(itemChecked.contains(".") && itemChecked != LoopBackAdre)
            {
                ipArray.add(data.get(i));

            }

            i++;
        }

        if(ipArray.contains("/127.0.0.1"))
        {
            //System.out.println("before loopback: "+ipArray);
            int LoopBak = ipArray.indexOf("/127.0.0.1");
            ipArray.remove(LoopBak);
            //System.out.println("after loopback: "+ipArray);
        }

        //System.out.println("ipArray: "+ ipArray);
        return ipArray;



    }


    static String IpUser() throws Exception
    /* this method as for purpose to remove the "/" character and
    to turn the arraylist string into just a string */
    {
        ArrayList<String> yolo = new ArrayList<String>() ;
        try {
            yolo = NetInterface.GetIp();
        } catch (SocketException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(yolo);
        String UsIp = yolo.get(0);
        //System.out.println(UsIp);
        UsIp = UsIp.replace("/", "");
        //System.out.println("print right before the return: "+UsIp);
        return UsIp;
    }

    static String PulicIp() throws MalformedURLException, IOException
    {       
        //get public ip using a website 
        URL PublicIp = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(PublicIp.openStream()));
        String ip = in.readLine();
        //System.out.println("public ip: "+ip);
        return ip; 
    }

    
    static String CheckIps() throws FileNotFoundException, IOException
    {
        Scanner s = new Scanner(new File("ips.txt"));
        ArrayList<String> ipsArray = new ArrayList<String>();
        
        
        /*this loop import the content of ips.txt to the array ipsArray*/
        while (s.hasNext())
        {
            ipsArray.add(s.next());
        }
        s.close();
        
        //System.out.println(ipsArray);
        
        int sizeArray = ipsArray.size()-1;
  
        //System.out.println("sizeArray: "+sizeArray);
        
        
        /*this double loop is going through the array comparing each string 
        to ssee if they are similar and if they are they replace it by ""*/
        for(int i = 0; i <= sizeArray; i++)
        {
            //System.out.println("i: "+i+"\n");
            for(int j = 0; j <= sizeArray; j++)
            {
                if(ipsArray.get(i).equals(ipsArray.get(j)) && i!=j)
                {
                    ipsArray.set(j,"");
                    //System.out.println("went here2");
                }
                //System.out.print(" j: "+j);
 
                else
                {
                    //System.out.println("went here");
                }
            } 
            
        }
        
        /*here we romve the "" character from the list*/
        ipsArray.removeAll(Collections.singleton(""));
        //System.out.println(ipsArray);
        
        FileWriter writer = new FileWriter("ips.txt"); 
        
        sizeArray = ipsArray.size()-1;
        
        
        /*and here we write the modify array back to the ips.txt file */
        for(int l = 0; l <= sizeArray; l++) 
        {
        writer.write(ipsArray.get(l) + System.lineSeparator());
        }
        writer.close();
        
        
     
        return null;
    }
    
    
}
