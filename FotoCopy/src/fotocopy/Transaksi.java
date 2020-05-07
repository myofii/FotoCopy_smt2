/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fotocopy;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jdk.nashorn.internal.scripts.JO;

/**
 *
 * @author asus
 */
public class Transaksi extends javax.swing.JFrame {

    /**
     * Creates new form Transaksi
     */
    DefaultTableModel tab = new DefaultTableModel(null, new String[]{"Kode Barang","Nama Barang","Jumlah Barang","Harga Barang","Subtotal Harga"});
    private String nota = "";
    private String desc="";
    private int totalharga=0;
    private boolean cekbayar=false;
    public Transaksi() {
        initComponents();
        this.nota = buatNota_jual();
        isiTable();
        table_jual.setModel(tab);
    }
    public Transaksi(String nota){
        initComponents();
        this.nota = nota;
        isiTable();
        table_jual.setModel(tab);
        hitung();
    }
    private String buatNota_jual(){
        String nota="";
        try {
            Connection con = koneksi.getkoneksi();
            Statement stmt = con.createStatement();
            ResultSet row = stmt.executeQuery("select count(distinct(nota)) as jumlah from transaksi where nota like 'PJ-%'");
            int jumlah=0;
            if (row.first()) {
                jumlah=row.getInt("jumlah");
            }
            int number=jumlah+1;
            nota="PJ-";
            if (number>jumlah) {
                String nomor=Integer.toString(number);
                nota+=nomor;
            }
        } catch (Exception e) {
        }
        return nota;
    }
    private void isiTable(){
        try {
            Connection con = koneksi.getkoneksi();
            String sql = "select t.kodeBarang,b.namaBarang,t.jumlahBarang,b.hargaJual,b.satuan,t.hargaBarang from barang b, transaksi t where t.kodeBarang=b.kodeBarang and t.nota='"+nota+"'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                desc+=""+rs.getString("b.namaBarang")+"\t\t\t\t\t"+rs.getString("t.jumlahBarang")+"\t\t\t\t\t"+rs.getString("b.satuan")+"\n";
                tab.addRow(new Object[]{rs.getString("t.kodeBarang"),rs.getString("b.namaBarang"),rs.getInt("t.jumlahBarang"),rs.getInt("b.hargaJual"),rs.getInt("t.hargaBarang")});
            }
        } catch (Exception e) {
        }
    }
    private void hitung(){
        int i=table_jual.getRowCount();
        table_jual.getModel();
        for (int j = 0; j < i; j++) {
            totalharga+=Integer.parseInt(table_jual.getValueAt(j,4).toString());
        }
        total.setText(""+totalharga);
    }
    SimpleDateFormat ft ;
    java.util.Date tgl= new java.util.Date(); 
    private void waktu(){
        ft = new SimpleDateFormat ("yyyy-MM-dd");
    }
    private void bayar(boolean cek){
        if (cek) {
            cekbayar=true;
            waktu();
            try {
                Connection con = koneksi.getkoneksi();
                String sql = "insert into jualbeli values(?,?,?,?,?)";
                    try (PreparedStatement prep = con.prepareStatement(sql)) {
                        prep.setString(1, nota);
                        prep.setString(2, desc);
                        prep.setString(3, ft.format(tgl));
                        prep.setInt(4, Integer.parseInt(total.getText()));
                        prep.setInt(5, 0);
                        prep.executeUpdate();
                    }
                JOptionPane.showMessageDialog(null,"Pembayaran Berhasil");
            } catch (Exception e) {
                cekbayar=false;
                JOptionPane.showMessageDialog(null,"Pembayaran gagal");
            }
        } else {
            cekbayar=false;
        }
    }
    private void kurangStok(){
        String kodebrg[]=new String[tab.getRowCount()];
        int jumlah[]=new int[tab.getRowCount()];
        for (int i = 0; i < kodebrg.length; i++) {
            kodebrg[i]=""+tab.getValueAt(i,0);
            jumlah[i]=Integer.parseInt(""+tab.getValueAt(i,2));
        }
        try {
            Connection con = koneksi.getkoneksi();
            Statement stmt = con.createStatement();
            for (int i = 0; i < kodebrg.length; i++) {
                String sql = "update barang set stokBarang = stokBarang-"+jumlah[i]+" where kodeBarang = '"+kodebrg[i]+"'";
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fotocopy = new javax.swing.JButton();
        print = new javax.swing.JButton();
        jilid = new javax.swing.JButton();
        barang = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        kembali = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        jumlahbayar = new javax.swing.JTextField();
        kembalian = new javax.swing.JTextField();
        bayar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_jual = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fotocopy.setText("Fotocopy");
        fotocopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fotocopyActionPerformed(evt);
            }
        });

        print.setText("Print");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        jilid.setText("Jilid");
        jilid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jilidActionPerformed(evt);
            }
        });

        barang.setText("Barang");
        barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barangActionPerformed(evt);
            }
        });

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        kembali.setText("Kembali");
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });

        jLabel1.setText("Total");

        jLabel2.setText("Bayar");

        jLabel3.setText("Kembalian");

        total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalActionPerformed(evt);
            }
        });

        bayar.setText("Bayar");
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });

        table_jual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Jumlah Barang", "Harga Barang", "Subtotal Harga"
            }
        ));
        table_jual.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                table_jualAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(table_jual);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(fotocopy, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jilid, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(barang, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jumlahbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reset))
                            .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bayar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fotocopy, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jilid, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bayar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jumlahbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reset)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void fotocopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fotocopyActionPerformed
        // TODO add your handling code here:
        formFotocopy menu = new formFotocopy(nota);
        menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_fotocopyActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        // TODO add your handling code here:
        formPrint menu = new formPrint(nota);
        menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_printActionPerformed

    private void jilidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jilidActionPerformed
        // TODO add your handling code here:
        formJilid menu = new formJilid(nota);
        menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_jilidActionPerformed

    private void barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barangActionPerformed
        // TODO add your handling code here:
        formBarang menu = new formBarang(nota);
        menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_barangActionPerformed

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
        new Menu().setVisible(true);
        dispose();
    }//GEN-LAST:event_kembaliActionPerformed

    private void table_jualAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_table_jualAncestorAdded
    }//GEN-LAST:event_table_jualAncestorAdded

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        // TODO add your handling code here:
        int jlh = Integer.parseInt(jumlahbayar.getText());
        int kmb = jlh-Integer.parseInt(total.getText());
        kembalian.setText(""+kmb);
        kurangStok();
        bayar(true);
    }//GEN-LAST:event_bayarActionPerformed

    private void totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        if (cekbayar==false) {
            try {
                Connection con = koneksi.getkoneksi();
                String sql = "delete from transaksi where nota = '"+nota+"'";
                PreparedStatement prep = con.prepareStatement(sql);
                prep.executeUpdate();
            } catch (Exception e) {
            }
            new Transaksi().setVisible(true);
            dispose();
        } else if(cekbayar==true) {
            new Transaksi().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_resetActionPerformed
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barang;
    private javax.swing.JButton bayar;
    private javax.swing.JButton fotocopy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jilid;
    private javax.swing.JTextField jumlahbayar;
    private javax.swing.JButton kembali;
    private javax.swing.JTextField kembalian;
    private javax.swing.JButton print;
    private javax.swing.JButton reset;
    private javax.swing.JTable table_jual;
    private javax.swing.JTextField total;
    // End of variables declaration//GEN-END:variables
}
