/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.ets.matej.tetris;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author 204-09
 */
public class JavaConnect {

    Connection con; //uspostava konekcije na bazu

    public static Connection connectDB() {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Bodovi.db");

            return conn; //klasa vraća konekciju ako je spajanje na bazu uspješno

        } catch (Exception e) { //stvaranje informativnog prozora u slučaju iznimke
            e.printStackTrace();
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Problem kod spajanja na bazu!", "Greška", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }
}
