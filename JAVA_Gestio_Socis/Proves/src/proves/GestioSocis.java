/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proves;

import interficie.BillarException;
import interficie.BillarPersistence;
import interficie.IBillar;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Modalitat;
import model.Soci;
import persistencia.PersistenciaMySQL;
import java.security.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import model.EstadisticaModalitat;

/**
 *
 * @author Usuari
 */
public class GestioSocis extends javax.swing.JFrame {

    private static IBillar pmysql = null;
    private static DefaultListModel dlm = new DefaultListModel();
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Boolean editando = false;
    private Boolean creando = false;
    private JLabel jlab = new JLabel();
    JFileChooser jfc = new JFileChooser();
    
    public static void main(String[] args) {
      
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
            java.util.logging.Logger.getLogger(GestioSocis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestioSocis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestioSocis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestioSocis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestioSocis("mysql.txt").setVisible(true);
            }
        });
    }
    
    public GestioSocis(String nomFitxerPropietats) {
        initComponents();
             
        if (nomFitxerPropietats == null) {
            nomFitxerPropietats = "mysql.txt";
        }
        Properties props = new Properties();
        try {
            props.load(new FileReader(nomFitxerPropietats));
        } catch (FileNotFoundException ex) {
            throw new BillarException("No es troba fitxer de propietats", ex);
        } catch (IOException ex) {
            throw new BillarException("Error en carregar fitxer de propietats", ex);
        }
        String component = props.getProperty("component");
        if (component == null) {
            throw new BillarException("El driver del fitxer de propietats es null");
        }
        String parametre = props.getProperty("parametre");
        if (parametre == null) {
            throw new BillarException("El parametre del fitxer de propietats es null");
        }
        
        String tcomponent[] = {"persistencia.PersistenciaMySQL"};
        String tparametre[] = {"UP-MySQL"};
        
        try {
            //System.out.println("Prova de component " + component);
            pmysql = BillarPersistence.getInstance(component, parametre);
            //System.out.println("Component " + component + " creat");
        } catch (BillarException ex) {
            //System.out.println("Error en generar objecte " + parametre);
            if (ex.getMessage() != null) {
                System.out.println("Info: " + ex.getMessage());
            }
        }
        cargarListaSocis();
        cargarListaModalitats();
        posarCampsDisabledEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNif = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCognom = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCognom2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        btnEliminar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListSocis = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jCmbModalitat = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCoeficient = new javax.swing.JTextField();
        txtCaramboles = new javax.swing.JTextField();
        txtEntrades = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();
        btnDescartar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jSP = new javax.swing.JScrollPane();
        btnSeleccionarFoto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Gestió de Socis");

        jLabel2.setText("NIF");

        jLabel3.setText("Nom");

        jLabel4.setText("Cognom 1");

        jLabel5.setText("Cognom 2");

        jLabel6.setText("Password");

        btnCrear.setText("Crear Soci");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar Soci");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jListSocis.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListSocis.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jListSocisPropertyChange(evt);
            }
        });
        jListSocis.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListSocisValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListSocis);

        jLabel7.setText("Modalitat");

        jCmbModalitat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbModalitatItemStateChanged(evt);
            }
        });

        jLabel8.setText("Coeficient base");

        jLabel9.setText("Total caramboles t. actual");

        jLabel10.setText("Total entrades t. actual");

        btnEditar.setText("Editar Soci");

        btnDescartar.setText("Descartar");
        btnDescartar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescartarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSeleccionarFoto.setText("Seleccionar foto");
        btnSeleccionarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarFotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNom, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                            .addComponent(txtNif)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCognom, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jLabel5)
                            .addGap(4, 4, 4)
                            .addComponent(txtCognom2, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                                    .addComponent(jCmbModalitat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCoeficient)
                                    .addComponent(txtCaramboles)
                                    .addComponent(txtEntrades)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDescartar)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnSeleccionarFoto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                        .addComponent(jSP, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(27, 27, 27))
            .addGroup(layout.createSequentialGroup()
                .addGap(480, 480, 480)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCognom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCognom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCrear)
                                    .addComponent(btnEliminar)
                                    .addComponent(btnEditar)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDescartar)
                            .addComponent(btnGuardar))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jCmbModalitat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCoeficient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtCaramboles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSP, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEntrades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccionarFoto))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        vaciarCampos();
        btnGuardar.setEnabled(true);
        btnDescartar.setEnabled(true);
        btnCrear.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        posarCampsDisabledEnabled(true);
        creando = true;
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try{   
            ArrayList<Soci> socis = pmysql.getSocisValids(); 
            int index = jListSocis.getSelectedIndex();
            int id = socis.get(index).getId();
            pmysql.removeSoci(id);
            cargarListaSocis();
            vaciarCampos();
            JOptionPane.showMessageDialog(null, "Soci esborrat correctament.");
        }catch(Exception e){
        } 
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jListSocisPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jListSocisPropertyChange

    }//GEN-LAST:event_jListSocisPropertyChange

    private void jListSocisValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListSocisValueChanged
        if (!jListSocis.getValueIsAdjusting()) {
            posarCampsDisabledEnabled(false);
            vaciarCampos();
            carregarValorsSoci();
            btnCrear.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnEditar.setEnabled(true);
        }
    }//GEN-LAST:event_jListSocisValueChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       try{
            String nom = txtNom.getText();
            String nif = txtNif.getText();
            String cognom1 = txtCognom.getText();
            String cognom2 = txtCognom2.getText();
            String password = txtPassword.getText();
            Date d = new Date(); 
            java.sql.Date sqlDate = new java.sql.Date(d.getTime());
            byte [] fotoEnBytes = getByteArrayFromFile(jfc.getSelectedFile());

            Soci s = new Soci(nif,nom,cognom1,cognom2,sqlDate,getHashFromPassowrd(password),fotoEnBytes,1);
            Modalitat m = pmysql.getModalitat(jCmbModalitat.getSelectedItem().toString());
            
            Float coeficient = Float.parseFloat(txtCoeficient.getText());
            if(coeficient == null){ coeficient = 0f;}
            Integer caramboles = Integer.parseInt(txtCaramboles.getText());
            if(caramboles == null){ caramboles = 0;}
            Integer entrades = Integer.parseInt(txtEntrades.getText());
            if(entrades == null){ entrades = 0;}
            
            pmysql.addSoci(s, m, coeficient, caramboles, entrades);
            btnGuardar.setEnabled(false);
            btnDescartar.setEnabled(false);
            btnCrear.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnEditar.setEnabled(true);
            posarCampsDisabledEnabled(false);
            vaciarCampos();
            cargarListaSocis();
            creando = false;
            JOptionPane.showMessageDialog(null, "Soci afegit correctament.");
        }catch(Exception e){
        } 
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnDescartarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescartarActionPerformed
        posarCampsDisabledEnabled(false);
        vaciarCampos();
    }//GEN-LAST:event_btnDescartarActionPerformed

    private void jCmbModalitatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbModalitatItemStateChanged
        
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            try{
                vaciarEstadisticas();
                ArrayList<Soci> socis = pmysql.getSocisValids();
                int index = jListSocis.getSelectedIndex();
                int idSoci = socis.get(index).getId();
                int idModalitat = pmysql.getIdModalitat(jCmbModalitat.getSelectedItem().toString());
                carregarEstadistiquesModalitat(idSoci,idModalitat);
            } catch(Exception e){
            }
        }  
    }//GEN-LAST:event_jCmbModalitatItemStateChanged

    private void btnSeleccionarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarFotoActionPerformed
        if(jfc.showOpenDialog(jLabel1) == JFileChooser.APPROVE_OPTION){
            File f = jfc.getSelectedFile();
            jlab.setIcon(new ImageIcon(f.toString()));
            jlab.setHorizontalAlignment(JLabel.CENTER);
            jSP.getViewport().add(jlab);
        }
    }//GEN-LAST:event_btnSeleccionarFotoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnDescartar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSeleccionarFoto;
    private javax.swing.JComboBox<String> jCmbModalitat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private static javax.swing.JList<String> jListSocis;
    private javax.swing.JScrollPane jSP;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtCaramboles;
    private javax.swing.JTextField txtCoeficient;
    private javax.swing.JTextField txtCognom;
    private javax.swing.JTextField txtCognom2;
    private javax.swing.JTextField txtEntrades;
    private javax.swing.JTextField txtNif;
    private javax.swing.JTextField txtNom;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables

    private void cargarListaSocis() {
        ArrayList<Soci> socis = pmysql.getSocisValids(); 
        dlm = new DefaultListModel();
        for (Soci s : socis){
            dlm.addElement(s);
        }
        this.jListSocis.setModel(dlm);    
    }
    
    private void cargarListaModalitats() {
        ArrayList<Modalitat> modalitats = pmysql.getModalitats();
        for(Modalitat m : modalitats){
            jCmbModalitat.addItem(m.getDescription());   
        }
    }

    private void carregarValorsSoci() {
        ArrayList<Soci> socis = pmysql.getSocisValids(); 
        int index = jListSocis.getSelectedIndex();
        int idSoci = socis.get(index).getId();
        int idModalitat = pmysql.getIdModalitat(jCmbModalitat.getSelectedItem().toString());
        
        Soci s = pmysql.getSoci(idSoci);
        txtNom.setText(s.getNom());
        txtNif.setText(s.getNif());
        txtCognom.setText(s.getCognom1());
        txtCognom2.setText(s.getCognom2());
        cargarFoto(s.getFoto());
        carregarEstadistiquesModalitat(idSoci,idModalitat);  
    }

    private void carregarEstadistiquesModalitat(int idSoci, int idModalitat) {
        EstadisticaModalitat emod = null;
        emod = pmysql.getEstadisticaModalitat(idSoci,idModalitat);
        
        
        txtCoeficient.setText(emod.getCoeficientBase() +"");
        txtCaramboles.setText(emod.getTotalCarambolesTemporadaActual() + "");
        txtEntrades.setText(emod.getTotalEntradesTemporadaActual() + ""); 
    }
    
    private void posarCampsDisabledEnabled(Boolean b){
            if(txtNif.isEnabled() == !b){
                txtNif.setEnabled(b);
            }
            if(txtNom.isEnabled() == !b){
                txtNom.setEnabled(b);
            }
            if(txtCognom.isEnabled() == !b){
                txtCognom.setEnabled(b);
            }
            if(txtCognom2.isEnabled() == !b){
                txtCognom2.setEnabled(b);
            }
            if(txtPassword.isEnabled() == !b){
                txtPassword.setEnabled(b);
            }
            if(txtCaramboles.isEnabled() == !b){
                txtCaramboles.setEnabled(b);
            }
            if(txtEntrades.isEnabled() == !b){
                txtEntrades.setEnabled(b);
            }
            if(txtCoeficient.isEnabled() == !b){
                txtCoeficient.setEnabled(b);
            }
            if(btnGuardar.isEnabled() == !b){
                btnGuardar.setEnabled(b);
            }
            if(btnDescartar.isEnabled() == !b){
                btnDescartar.setEnabled(b);
            }
            if(btnSeleccionarFoto.isEnabled() == !b){
                btnSeleccionarFoto.setEnabled(b);
            }
    }

    private void vaciarCampos() {
        txtNif.setText("");
        txtNom.setText("");
        txtCognom.setText("");
        txtCognom2.setText("");
        txtPassword.setText("");
        txtCaramboles.setText("");
        txtEntrades.setText("");
        txtCoeficient.setText("");
    }
    
    private void vaciarEstadisticas() {
        txtCaramboles.setText("");
        txtEntrades.setText("");
        txtCoeficient.setText("");
    }
    
    
    private String getHashFromPassowrd(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        byte[] bytesOfMessage = password.getBytes("UTF-8");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] thedigest = md5.digest(bytesOfMessage);
        
        String encryptedString = thedigest.toString();
        return encryptedString;
    }

    private void cargarFoto(byte[] foto) {
        if(foto == null){
            jlab.setIcon(null);
        }
        try{
            jlab.setIcon(new ImageIcon(foto));
            jlab.setHorizontalAlignment(JLabel.CENTER);
            jSP.getViewport().add(jlab);  
        }catch(Exception e){
            
        }
    }

    private byte [] getByteArrayFromFile(File selectedFile) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(selectedFile);
 
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); 
            }
        } catch (IOException ex) {
            return null;
        }
 
        byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
