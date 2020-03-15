/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedprogramming;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarryMeBabY
 */
public class NetInterfaceTest {
    
    public NetInterfaceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of main method, of class NetInterface.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        NetInterface.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetIp method, of class NetInterface.
     */
    @Test
    public void testGetIp() throws Exception {
        System.out.println("GetIp");
        ArrayList<String> expResult = null;
        ArrayList<String> result = NetInterface.GetIp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IpUser method, of class NetInterface.
     */
    @Test
    public void testIpUser() throws Exception {
        System.out.println("IpUser");
        String expResult = "";
        String result = NetInterface.IpUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of PulicIp method, of class NetInterface.
     */
    @Test
    public void testPulicIp() throws Exception {
        System.out.println("PulicIp");
        String expResult = "";
        String result = NetInterface.PulicIp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CheckIps method, of class NetInterface.
     */
    @Test
    public void testCheckIps() throws Exception {
        System.out.println("CheckIps");
        String expResult = "";
        String result = NetInterface.CheckIps();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
