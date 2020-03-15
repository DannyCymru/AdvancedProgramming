/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedprogramming;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class MainScreenTest {
    
    public MainScreenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of ListenThread method, of class MainScreen.
     */
    @Test
    public void testListenThread() {
        System.out.println("ListenThread");
        MainScreen instance = new MainScreen();
        instance.ListenThread();
 
    }

    /**
     * Test of userAdd method, of class MainScreen.
     */
    @Test
    public void testUserAdd() {
        System.out.println("userAdd");
        String data = "";
        MainScreen instance = new MainScreen();
        instance.userAdd(data);
 
    }

    /**
     * Test of userRemove method, of class MainScreen.
     */
    @Test
    public void testUserRemove() {
        System.out.println("userRemove");
        String data = "";
        MainScreen instance = new MainScreen();
        instance.userRemove(data);
 
    }

    /**
     * Test of writeUsers method, of class MainScreen.
     */
    @Test
    public void testWriteUsers() {
        System.out.println("writeUsers");
        MainScreen instance = new MainScreen();
        instance.writeUsers();
    
    }

    /**
     * Test of sendserverdisconnect method, of class MainScreen.
     */
    @Test
    public void testSendserverdisconnect() {
        System.out.println("sendserverdisconnect");
        MainScreen instance = new MainScreen();
        instance.sendserverdisconnect();

    }

    /**
     * Test of Disconnect method, of class MainScreen.
     */
    @Test
    public void testDisconnect() {
        System.out.println("Disconnect");
        MainScreen instance = new MainScreen();
        instance.Disconnect();
    }

    /**
     * Test of setUniqueId method, of class MainScreen.
     */
    @Test
    public void testSetUniqueId() {
        System.out.println("setUniqueId");
        String uId = "";
        MainScreen instance = new MainScreen();
        instance.setUniqueId(uId);
    }

    /**
     * Test of currTime method, of class MainScreen.
     */
    @Test
    public void testCurrTime() {
        System.out.println("currTime");
        MainScreen instance = new MainScreen();
        String expResult = instance.currTime();
        String result = instance.currTime();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of main method, of class MainScreen.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        MainScreen.main(args);
    }
    
}
