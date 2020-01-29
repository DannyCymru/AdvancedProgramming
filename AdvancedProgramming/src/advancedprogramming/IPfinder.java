package advancedprogramming;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPfinder{
    public static InetAddress getIPadress() throws SocketException
    {
        Enumeration<NetworkInterface> NI1 = NetworkInterface.getNetworkInterfaces();
        while( NI1.hasMoreElements() )
        {
            NetworkInterface IPadr = NI1.nextElement();
            Enumeration<InetAddress> IPadress = IPadr.getInetAddresses();
            while( IPadress.hasMoreElements() )
            {
                InetAddress IP = IPadress.nextElement();
                if( IP instanceof Inet4Address && !IP.isLoopbackAddress() )
                {
                    return IP;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws SocketException 
    {
        IPfinder.getIPadress();
    }
}


    