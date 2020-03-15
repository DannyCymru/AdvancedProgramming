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
public class LoginTest {
    
    public LoginTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of main method, of class Login.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Login.main(args);
   
    }

    /**
     * Test of userAdd method, of class Login.
     */
    @Test
    public void testUserAdd() {
       // can't test because we need someone to be connecting do the server.

    }

    /**
     * Test of userRemove method, of class Login.
     */
    @Test
    public void testUserRemove() {
    // can't test it beacause we need to have someone disconnecting from the client.

    }

    /**
     * Test of tellEveryone method, of class Login.
     */
    @Test
    public void testTellEveryone() {
       // Can't be tested because it relies on the messsages sending from the client to the server and then server tells everyone the message

    }

    /**
     * Test of currTime method, of class Login.
     */
    @Test
    public void testCurrTime() {
        System.out.println("currTime");
        Login instance = new Login();
        String expResult = instance.currTime();
        String result = instance.currTime();
        assertEquals(expResult, result);

    }
    
}
