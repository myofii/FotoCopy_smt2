/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fotocopy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author M. Yofi Indrawan
 */
public class pengeluaran extends javax.swing.JFrame {

    /**
     * Creates new form pengeluaran
     */
    DefaultTableModel tab = new DefaultTableModel(null, new String[]{"Kode Barang","Keterangan","Jumlah Barang","Subtotal Harga"});
    private String nota = "";
    private String desc="";
    private int totalharga=0;
    private int ceknota=0;
    public pengeluaran() {
        initComponents();
        this.nota = buatNota_jual();
        isiTable();
        table_beli.setModel(tab);
        total.setText("Total Harga  : Rp."+totalharga);
    }
    public pengeluaran(String nota,int cek) {
        initComponents();
        this.nota = nota;
        isiTable();
        hitung();
        ceknota=cek;
    }
    private boolean r=false;
    private String buatNota_jual(){
        String nota="";
        try {
            Connection con = koneksi.getkoneksi();
            Statement stmt = con.createStatement();
            ResultSet row = stmt.executeQuery("select count(distinct(nota)) as jumlah from transaksi where nota like 'PB-%'");
            int jumlah=0;
            if (row.first()) {
                jumlah=row.getInt("jumlah");
            }
            int number=jumlah+1;
            nota="PB-";
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
            String sql = "select keterangan,jumlahBarang,hargaBarang,kodeBarang from transaksi where nota='"+nota+"'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                desc+=""+rs.getString("keterangan")+"\t\t\t\t\t"+rs.getString("jumlahBarang")+"\n";
                tab.addRow(new Object[]{rs.getString("kodeBarang"),rs.getString("keterangan"),rs.getInt("jumlahBarang"),rs.getInt("hargaBarang")});
            }
            table_beli.setModel(tab);
        } catch (Exception e) {
        }
    }
    private void hitung(){
        int i=table_beli.getRowCount();
        table_beli.getModel();
        for (int j = 0; j < i; j++) {
            totalharga+=Integer.parseInt(table_beli.getValueAt(j,3).toString());
        }
        total.setText("Total Harga  : Rp."+totalharga);
    }
    SimpleDateFormat ft ;
    java.util.Date tgl= new java.util.Date(); 
    private void waktu(){
        ft = new SimpleDateFormat ("yyyy-MM-dd");
    }
    private boolean cekselesai=false;
    private void selesai(boolean cek){
        if (cek) {
            cekselesai=true;
            waktu();
            try {
                Connection con = koneksi.getkoneksi();
                String sql = "insert into jualbeli values(?,?,?,?,?)";
                    try (PreparedStatement prep = con.prepareStatement(sql)) {
                        prep.setString(1, nota);
                        prep.setString(2, desc);
                        prep.setString(3, ft.format(tgl));
                        prep.setInt(4, 0);
                        prep.setInt(5, totalharga);
                        prep.executeUpdate();
                    }
                JOptionPane.showMessageDialog(null,"Pembayaran Berhasil");
            } catch (Exception e) {
                cekselesai=false;
                JOptionPane.showMessageDialog(null,"Pembayaran gagal");
            }
        } else {
            cekselesai=false;
        }
    }
    private void nambahStok(){
        String [] kodeb=new String[table_beli.getRowCount()];
        int [] jumlah=new int[table_beli.getRowCount()];
        boolean cek = false;
        for (int j = 0; j < kodeb.length; j++) {
            kodeb[j]=""+tab.getValueAt(j,0);
            jumlah[j]=Integer.parseInt(""+tab.getValueAt(j,2));
        }
        try {
            Connection con = koneksi.getkoneksi();
            Statement stmt = con.createStatement();
            for (int k = 0; k < kodeb.length; k++) {
                String sql = "update barang set stokBarang = stokBarang+"+jumlah[k]+" where kodeBarang ='"+kodeb[k]+"'";
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
        }
    }
    private void resetData(){
        String [] kodeb = null;
        int i = 0 ; 
        try {
            Connection con = koneksi.getkoneksi();
            String sql = "select count(kodeBarang) as jumlah from transaksi where keterangan like 'Penambahan Barang%' and nota = '"+nota+"'";
            Statement st = con.createStatement();
            ResultSet row = st.executeQuery(sql);
            while (row.next()) {
                i=row.getInt("jumlah");
            }
            kodeb=new String[i];
            sql="select kodeBarang from transaksi where keterangan like 'Penambahan Barang%' and nota = '"+nota+"'";
            ResultSet rs=st.executeQuery(sql);
            int j = 0 ;
            while (rs.next()) {                
                kodeb[j] = rs.getString("kodeBarang");
                j++;
            }
        } catch (Exception e) {
        }
        try {
            Connection con = koneksi.getkoneksi();
            String sql = "delete from transaksi where nota = '"+nota+"'";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.executeUpdate();
        } catch (Exception e) {
        }
        try {
            Connection con = koneksi.getkoneksi();
            for (int j = 0; j < kodeb.length; j++) {
                String sql = "delete from barang where kodeBarang = '"+kodeb[j]+"'";
                PreparedStatement prep = con.prepareStatement(sql);
                prep.executeUpdate();
                System.out.println(kodeb[j]);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        table_jual = new javax.swing.JTable();
        tambahstok = new javax.swing.JButton();
        tambar = new javax.swing.JButton();
        bayarlistrik = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_beli = new javax.swing.JTable();
        kembali = new javax.swing.JButton();
        total = new javax.swing.JLabel();
        selesai = new javax.swing.JButton();
        reset = new javax.swing.JButton();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tambahstok.setText("Penambahan Stok");
        tambahstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahstokActionPerformed(evt);
            }
        });

        tambar.setText("Tambah Barang");
        tambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambarActionPerformed(evt);
            }
        });

        bayarlistrik.setText("Bayar Listrik");
        bayarlistrik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarlistrikActionPerformed(evt);
            }
        });

        table_beli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Keterangan", "Jumlah Barang", "Subtotal Harga"
            }
        ));
        table_beli.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                table_beliAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(table_beli);

        kembali.setText("Kembali");
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });

        total.setText("jLabel1");

        selesai.setText("Tambah");
        selesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selesaiActionPerformed(evt);
            }
        });

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tambahstok)
                        .addGap(29, 29, 29)
                        .addComponent(tambar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(bayarlistrik, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(selesai, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)))
                        .addGap(19, 19, 19)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bayarlistrik, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(tambar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tambahstok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selesai, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(kembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tambahstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahstokActionPerformed
        // TODO add your handling code here:
        if (ceknota!=1) {
            new tambahStok(nota,ceknota).setVisible(true);
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Tidak dapat menambahkan transaksi tambah stok barang ke nota");
        }
    }//GEN-LAST:event_tambahstokActionPerformed

    private void bayarlistrikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarlistrikActionPerformed
        // TODO add your handling code here:
        if (ceknota!=2) {
            new bayarListrik(nota,ceknota).setVisible(true);
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Tidak dapat menambahkan transaksi bayar listrik ke nota");
        }
    }//GEN-LAST:event_bayarlistrikActionPerformed

    private void table_jualAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_table_jualAncestorAdded

    }//GEN-LAST:event_table_jualAncestorAdded

    private void table_beliAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_table_beliAncestorAdded

    }//GEN-LAST:event_table_beliAncestorAdded

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
        if (cekselesai==false) {
            resetData();
            new Menu().setVisible(true);
            dispose();
        } else if(cekselesai==true) {
            new Menu().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_kembaliActionPerformed

    private void tambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambarActionPerformed
        // TODO add your handling code here:
        if (ceknota!=1) {
            new tambahBarang(nota,ceknota).setVisible(true);
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Tidak dapat menambahkan transaksi tambah barang baru ke nota");
        }
    }//GEN-LAST:event_tambarActionPerformed

    private void selesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selesaiActionPerformed
        // TODO add your handling code here:
        selesai(true);
        nambahStok();
    }//GEN-LAST:event_selesaiActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        if (cekselesai==false) {
            resetData();
            new pengeluaran().setVisible(true);
            dispose();
        } else if(cekselesai==true) {
            new pengeluaran().setVisible(true);
            dispose();
        }//*/
        
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
            java.util.logging.Logger.getLogger(pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pengeluaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bayarlistrik;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton kembali;
    private javax.swing.JButton reset;
    private javax.swing.JButton selesai;
    private javax.swing.JTable table_beli;
    private javax.swing.JTable table_jual;
    private javax.swing.JButton tambahstok;
    private javax.swing.JButton tambar;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
