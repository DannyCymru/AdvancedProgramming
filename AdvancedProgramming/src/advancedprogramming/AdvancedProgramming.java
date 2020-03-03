/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedprogramming;

import advancedprogramming.MainScreen;
import java.io.IOException;
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
         Thread starter = new Thread(new MainScreen.ServerStart());
        starter.start();
        
    }
    
}