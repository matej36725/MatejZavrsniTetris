/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hr.ets.matej.tetris;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import net.proteanit.sql.DbUtils;

/**
 *
 * @author jakovljevic
 */
@SuppressWarnings("serial")
public class Bodovi extends JDialog {
    
    Connection conn;
    ResultSet rs;   //podaci iz baze
    PreparedStatement pst;

    /**
     * Creates new form Bodovi
     */
    public Bodovi(JFrame parent) {
    	super(parent, "Top lista bodova", true);
        initComponents();
        conn = JavaConnect.connectDB();
    }

    public void prikaziBodove() {
        try {
            String sql = "select row_number() over(ORDER BY Bodovi DESC) as '#', * from Igrac ORDER BY Bodovi DESC LIMIT 15"; //sql dohvaća listu rezultata po traženim uvjetima
            pst = conn.prepareStatement(sql);//priprema sql naredbe
            rs = pst.executeQuery();//rs(skup traženih redaka iz baze);
            tablica.setModel(DbUtils.resultSetToTableModel(rs));//puni tablicu svim rezultatima pretrage
        } catch (Exception e) {//uhvati i prikaži pogrešku
            JOptionPane.showMessageDialog(null, e);
        }
        
        tablica.getColumnModel().getColumn(0).setPreferredWidth(20);
        tablica.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablica.getColumnModel().getColumn(3).setPreferredWidth(140);
    }
    
    public void dodajBodoveNaListu(String ime, long bodovi, String trajanje){
        try {
            String sql = "Insert into Igrac (Username, Bodovi, Vrijeme, Trajanje) values (?,?,datetime('now','localtime'),?)"; //sql za upis nove rezultata
            pst = conn.prepareStatement(sql); //priprema sql naredbe
            
            //zadavanje parametara sql naredbe
            pst.setString(1, ime);
            pst.setLong(2, bodovi);
            pst.setString(3, trajanje);
           
            //izvođenje naredbe
            pst.execute();
            pst.close();

            //conn.close(); //zatvaranje konekcije na bazu
        } catch (Exception e) { //poruka u slučaju greške
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablica = new javax.swing.JTable();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        //setType(java.awt.Window.Type.POPUP);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BODOVI LISTA");
        jLabel1.setToolTipText("");

        tablica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Ime", "Bodovi", "Vrijeme", "Trajanje"
            }
        ) {
            Class<?>[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablica.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tablica);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablica;
    // End of variables declaration//GEN-END:variables
}