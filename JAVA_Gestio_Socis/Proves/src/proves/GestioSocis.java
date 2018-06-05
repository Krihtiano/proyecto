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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import java.util.HashSet;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import model.EstadisticaModalitat;

/**
 *
 * @author Usuari
 */
public class GestioSocis extends javax.swing.JFrame {

    private static IBillar pmysql = null;
    private static DefaultTableModel dtm;
    private static DefaultTableModel dtm2;
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
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Segur que vols sortir del programa?", "Sortir",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                }else{
                    return;
                }
            }
        });
        
        
        btnEditar.setEnabled(false);
        jTableSocis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableInactivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Estos setEnabled estan puestos porque no se edita el coeficiente/carambolas/entradas.
        txtCaramboles.setEnabled(false);
        txtEntrades.setEnabled(false);
        txtCoeficient.setEnabled(false);

        if (nomFitxerPropietats == null) {
            nomFitxerPropietats = "mysql.txt";
        }
        Properties props = new Properties();
        try {
            props.load(new FileReader(nomFitxerPropietats));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No es troba el fitxer de propietats.");
            throw new BillarException("No es troba fitxer de propietats", ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error en carregar fitxer de propietats.");
            throw new BillarException("Error en carregar fitxer de propietats", ex);
        }
        String component = props.getProperty("component");
        if (component == null) {
            JOptionPane.showMessageDialog(null, "El driver del fitxer de propietats es null.");
            throw new BillarException("El driver del fitxer de propietats es null");
        }
        String parametre = props.getProperty("parametre");
        if (parametre == null) {
            JOptionPane.showMessageDialog(null, "El parametre del fitxer de propietats es null.");
            System.exit(1);
            throw new BillarException("El parametre del fitxer de propietats es null");
        }

        try {
            pmysql = BillarPersistence.getInstance(component, parametre);
        } catch (BillarException ex) {
            if (ex.getMessage() != null) {
                System.out.println("Info: " + ex.getMessage());
            }
            JOptionPane.showMessageDialog(null, "No s'han trobat les taules a la BD");
            System.exit(1);
        }

        try {
            cargarListaSocis();
            cargarListaSocisInactius();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al iniciar l'aplicació.");
            System.exit(1);
        }

        cargarListaModalitats();
        posarCampsDisabledEnabled(false);

    }

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
        btnEsborrarFoto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSocis = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableInactivos = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        btnRecuperar = new javax.swing.JButton();

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
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

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

        btnEsborrarFoto.setText("Esborrar foto");
        btnEsborrarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsborrarFotoActionPerformed(evt);
            }
        });

        jTableSocis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIF", "Nom", "Cognom", "Cognom 2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableSocis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSocisMouseClicked(evt);
            }
        });
        jTableSocis.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTableSocisPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSocis);

        jTableInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIF", "Nom", "Cognom", "Cognom 2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableInactivos);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setText("Socis inactius");

        btnRecuperar.setText("Recuperar Soci");
        btnRecuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecuperarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(480, 480, 480)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
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
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnEsborrarFoto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSeleccionarFoto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnRecuperar)
                                                .addGap(179, 179, 179))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(247, 247, 247)
                                        .addComponent(jLabel11)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCrear)
                            .addComponent(btnEliminar)
                            .addComponent(btnEditar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnDescartar)
                                .addComponent(btnGuardar))
                            .addGap(40, 40, 40)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jCmbModalitat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(txtCoeficient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(txtCaramboles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEntrades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccionarFoto)
                    .addComponent(btnRecuperar))
                .addGap(14, 14, 14)
                .addComponent(btnEsborrarFoto)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        cargarFoto(null);
        vaciarCampos();
        btnGuardar.setEnabled(true);
        btnDescartar.setEnabled(true);
        btnCrear.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        jTableSocis.setEnabled(false);
        posarCampsDisabledEnabled(true);
        btnRecuperar.setEnabled(false);
        creando = true;
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (jTableSocis.getSelectedRow() != -1) {
            int response = JOptionPane.showConfirmDialog(null, "Segur que vols esborrar aquest soci?", "Confirmació", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {

            } else if (response == JOptionPane.YES_OPTION) {
                cargarFoto(null);
                btnEditar.setEnabled(false);
                try {
                    ArrayList<Soci> socis = pmysql.getSocisValids();
                    int index = jTableSocis.getSelectedRow();
                    int id = socis.get(index).getId();
                    pmysql.removeSoci(id);
                    vaciarCampos();
                    JOptionPane.showMessageDialog(null, "Soci esborrat correctament.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al esborrar un soci.");
                }
                cargarListaSocis();
                cargarListaSocisInactius();
            } else if (response == JOptionPane.CLOSED_OPTION) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un soci.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (creando == true) {
            try {
                int errorDni = pmysql.comprovacioDni(txtNif.getText());
                if (errorDni == 1) {
                    JOptionPane.showMessageDialog(null, "DNI duplicat a la BD.");
                    throw new Exception("Error, dni duplicat");
                }

                String nom = txtNom.getText();
                String nif = txtNif.getText();
                String cognom1 = txtCognom.getText();
                String cognom2 = txtCognom2.getText();
                String password = txtPassword.getText();
                Date d = new Date();
                java.sql.Date sqlDate = new java.sql.Date(d.getTime());
                byte[] fotoEnBytes = null;
                try {
                    fotoEnBytes = getByteArrayFromFile(jfc.getSelectedFile());
                } catch (Exception e) {
                }

                Soci s = null;

                try {
                    s = new Soci(nif, nom, cognom1, cognom2, sqlDate, password, fotoEnBytes, 1);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "No s'ha pogut crear el soci: " + e.getMessage());
                    creando = false;
                    throw new Exception("");
                }

                Modalitat m = pmysql.getModalitat(jCmbModalitat.getSelectedItem().toString());

                Float coeficient;
                Integer caramboles, entrades;
                try {
                    coeficient = Float.parseFloat(txtCoeficient.getText());
                } catch (Exception e) {
                    coeficient = 0f;
                }

                try {
                    caramboles = Integer.parseInt(txtCaramboles.getText());
                } catch (Exception e) {
                    caramboles = 0;
                }

                try {
                    entrades = Integer.parseInt(txtEntrades.getText());
                } catch (Exception e) {
                    entrades = 0;
                }

                pmysql.addSoci(s, m, coeficient, caramboles, entrades);
                restaurarBotones();
                creando = false;
                jfc.setSelectedFile(null);
                jlab.setIcon(new ImageIcon(""));
                btnRecuperar.setEnabled(true);
                btnEditar.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Soci afegit correctament.");
                cargarListaSocis();
            } catch (Exception e) {
                restaurarBotones();
                creando = false;
                jfc.setSelectedFile(null);
                jlab.setIcon(new ImageIcon(""));
                btnRecuperar.setEnabled(true);
                btnEditar.setEnabled(false);
                cargarListaSocis();
                throw new BillarException("Problemes en crear un soci");
            }
        }
        if (editando == true) {
            try {

                ArrayList<Soci> socis = pmysql.getSocisValids();
                int index = jTableSocis.getSelectedRow();
                int idSoci = socis.get(index).getId();
                Soci soci = pmysql.getSoci(idSoci);
                int idModalitat = pmysql.getIdModalitat(jCmbModalitat.getSelectedItem().toString());
                Modalitat mod = pmysql.getModalitat(jCmbModalitat.getSelectedItem().toString());
                EstadisticaModalitat emod = new EstadisticaModalitat(soci, mod, 0f, 0, 0);
                try {
                    emod = pmysql.getEstadisticaModalitat(idSoci, idModalitat);
                } catch (Exception ex) {

                }

                int errorDni = pmysql.comprovacioDni(txtNif.getText(), soci.getNif());
                if (errorDni == 1) {
                    JOptionPane.showMessageDialog(null, "DNI duplicat a la BD.");
                    throw new Exception("Error, dni duplicat");
                }

                soci.setNom(txtNom.getText());
                soci.setNif(txtNif.getText());
                soci.setCognom1(txtCognom.getText());
                soci.setCognom2(txtCognom2.getText());
                if (txtPassword.getText() != null && txtPassword.getText().length() >= 1) {
                    soci.setPasswordHash(getHashFromPassowrd(txtPassword.getText()));
                }
                byte[] fotoEnBytes = soci.getFoto();
                try {
                    fotoEnBytes = getByteArrayFromFile(jfc.getSelectedFile());
                } catch (Exception e) {

                }

                soci.setFoto(fotoEnBytes);

                Float coeficient;
                Integer caramboles, entrades;
                try {
                    emod.setCoeficientBase(Float.parseFloat(txtCoeficient.getText()));
                } catch (Exception e) {
                    emod.setCoeficientBase(0f);
                }

                try {
                    emod.setTotalCarambolesTemporadaActual(Integer.parseInt(txtCaramboles.getText()));
                } catch (Exception e) {
                    emod.setTotalCarambolesTemporadaActual(0);
                }

                try {
                    emod.setTotalEntradesTemporadaActual(Integer.parseInt(txtEntrades.getText()));
                } catch (Exception e) {
                    emod.setTotalEntradesTemporadaActual(0);
                }

                pmysql.editarEM(emod);
                jTableSocis.setEnabled(true);
                pmysql.editarSoci(soci);
                restaurarBotones();
                editando = false;
                jfc.setSelectedFile(null);
                jlab.setIcon(new ImageIcon(""));
                btnRecuperar.setEnabled(true);
                btnEditar.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Soci editat correctament.");
                cargarListaSocis();
            } catch (Exception e) {
                btnEditar.setEnabled(false);
                restaurarBotones();
                jfc.setSelectedFile(null);
                jTableSocis.setEnabled(true);
                editando = false;
                jlab.setIcon(new ImageIcon(""));
                btnRecuperar.setEnabled(true);
                cargarListaSocis();
                throw new BillarException("Problemes en editar un soci");
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnDescartarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescartarActionPerformed
        posarCampsDisabledEnabled(false);
        vaciarCampos();
        btnCrear.setEnabled(true);
        btnEliminar.setEnabled(true);
        editando = false;
        creando = false;
        jTableSocis.setEnabled(true);
        btnRecuperar.setEnabled(true);
        jfc.setSelectedFile(null);
        jlab.setIcon(new ImageIcon(""));
        cargarListaSocis();
        btnEditar.setEnabled(false);
    }//GEN-LAST:event_btnDescartarActionPerformed

    private void jCmbModalitatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbModalitatItemStateChanged

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            try {
                vaciarEstadisticas();
                ArrayList<Soci> socis = pmysql.getSocisValids();
                int index = jTableSocis.getSelectedRow();
                int idSoci = socis.get(index).getId();
                int idModalitat = pmysql.getIdModalitat(jCmbModalitat.getSelectedItem().toString());
                carregarEstadistiquesModalitat(idSoci, idModalitat);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jCmbModalitatItemStateChanged

    private void btnSeleccionarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarFotoActionPerformed
        if (jfc.showOpenDialog(jLabel1) == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            jlab.setIcon(new ImageIcon(f.toString()));
            jlab.setHorizontalAlignment(JLabel.CENTER);
            jSP.getViewport().add(jlab);
        }
    }//GEN-LAST:event_btnSeleccionarFotoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            ArrayList<Soci> socis = pmysql.getSocisValids();
            int index = jTableSocis.getSelectedRow();
            int idSoci = socis.get(index).getId();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Selecciona un Soci");
            return;
        }

        jTableSocis.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnDescartar.setEnabled(true);
        btnCrear.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        posarCampsDisabledEnabled(true);
        btnRecuperar.setEnabled(false);
        editando = true;
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEsborrarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsborrarFotoActionPerformed
        jfc.setSelectedFile(null);
        jlab.setIcon(new ImageIcon(""));
        if (editando == true) {
            ArrayList<Soci> socis = pmysql.getSocisValids();
            int index = jTableSocis.getSelectedRow();
            int idSoci = socis.get(index).getId();
            Soci soci = pmysql.getSoci(idSoci);
            soci.setFoto(null);
        }
    }//GEN-LAST:event_btnEsborrarFotoActionPerformed

    private void jTableSocisPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTableSocisPropertyChange

    }//GEN-LAST:event_jTableSocisPropertyChange

    private void jTableSocisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSocisMouseClicked
        btnEditar.setEnabled(true);
        posarCampsDisabledEnabled(false);
        vaciarCampos();
        carregarValorsSoci();
        btnCrear.setEnabled(true);
        btnEliminar.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableSocisMouseClicked

    private void btnRecuperarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecuperarActionPerformed

        if (jTableInactivos.getSelectedRow() != -1) {
            try {
                ArrayList<Soci> socisInactius = pmysql.getSocisInactius();
                int index = jTableInactivos.getSelectedRow();
                int idSoci = socisInactius.get(index).getId();
                Soci soci = pmysql.getSoci(idSoci);

                pmysql.setSociActiu(soci);
                cargarListaSocis();
                cargarListaSocisInactius();
                JOptionPane.showMessageDialog(null, "Soci reactivat correctament");
            } catch (Exception ex) {
                throw new BillarException("Problemes en reinserir un soci");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un Soci inactiu");
        }
    }//GEN-LAST:event_btnRecuperarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnDescartar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEsborrarFoto;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRecuperar;
    private javax.swing.JButton btnSeleccionarFoto;
    private javax.swing.JComboBox<String> jCmbModalitat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jSP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableInactivos;
    private javax.swing.JTable jTableSocis;
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
        String[] columnNames = {"NIF", "Nom", "Cognom", "Cognom 2"};
        dtm = new DefaultTableModel(columnNames, 0);
        for (Soci s : socis) {
            Vector v = new Vector();
            v.add(s.getNif());
            v.add(s.getNom());
            v.add(s.getCognom1());
            v.add(s.getCognom2());
            dtm.addRow(v);
        }
        jTableSocis.setModel(dtm);
        jTableSocis.setEnabled(true);
    }

    private void cargarListaModalitats() {
        ArrayList<Modalitat> modalitats = pmysql.getModalitats();
        for (Modalitat m : modalitats) {
            jCmbModalitat.addItem(m.getDescription());
        }
    }

    private void carregarValorsSoci() {
        ArrayList<Soci> socis = pmysql.getSocisValids();
        int index = jTableSocis.getSelectedRow();
        int idSoci = socis.get(index).getId();
        int idModalitat = pmysql.getIdModalitat(jCmbModalitat.getSelectedItem().toString());

        Soci s = pmysql.getSoci(idSoci);
        txtNom.setText(s.getNom());
        txtNif.setText(s.getNif());
        txtCognom.setText(s.getCognom1());
        txtCognom2.setText(s.getCognom2());
        cargarFoto(s.getFoto());
        carregarEstadistiquesModalitat(idSoci, idModalitat);
    }

    private void carregarEstadistiquesModalitat(int idSoci, int idModalitat) {
        EstadisticaModalitat emod = null;
        emod = pmysql.getEstadisticaModalitat(idSoci, idModalitat);

        txtCoeficient.setText(emod.getCoeficientBase() + "");
        txtCaramboles.setText(emod.getTotalCarambolesTemporadaActual() + "");
        txtEntrades.setText(emod.getTotalEntradesTemporadaActual() + "");
    }

    private void posarCampsDisabledEnabled(Boolean b) {
        if (txtNif.isEnabled() == !b) {
            txtNif.setEnabled(b);
        }
        if (txtNom.isEnabled() == !b) {
            txtNom.setEnabled(b);
        }
        if (txtCognom.isEnabled() == !b) {
            txtCognom.setEnabled(b);
        }
        if (txtCognom2.isEnabled() == !b) {
            txtCognom2.setEnabled(b);
        }
        if (txtPassword.isEnabled() == !b) {
            txtPassword.setEnabled(b);
        }
        //Isidre me dijo que al final no se editaban los coeficientes/carambolas/entradas de los socios, 
        //si quiero editarlo deberia activar esto y quitar los 3 setEnabled(false) de arriba del programa.

        /*if (txtCaramboles.isEnabled() == !b) {
            txtCaramboles.setEnabled(b);
        }
        if (txtEntrades.isEnabled() == !b) {
            txtEntrades.setEnabled(b);
        }
        if (txtCoeficient.isEnabled() == !b) {
            txtCoeficient.setEnabled(b);
        }*/
        if (btnGuardar.isEnabled() == !b) {
            btnGuardar.setEnabled(b);
        }
        if (btnDescartar.isEnabled() == !b) {
            btnDescartar.setEnabled(b);
        }
        if (btnSeleccionarFoto.isEnabled() == !b) {
            btnSeleccionarFoto.setEnabled(b);
        }
        if (btnEsborrarFoto.isEnabled() == !b) {
            btnEsborrarFoto.setEnabled(b);
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

    private String getHashFromPassowrd(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = password.getBytes("UTF-8");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] thedigest = md5.digest(bytesOfMessage);

        String encryptedString = thedigest.toString();
        return encryptedString;
    }

    private void cargarFoto(byte[] foto) {
        if (foto == null) {
            jlab.setIcon(null);
        }
        try {
            jlab.setIcon(new ImageIcon(foto));
            jlab.setHorizontalAlignment(JLabel.CENTER);
            jSP.getViewport().add(jlab);
        } catch (Exception e) {

        }
    }

    private byte[] getByteArrayFromFile(File selectedFile) throws FileNotFoundException {
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

    private void restaurarBotones() {
        btnGuardar.setEnabled(false);
        btnDescartar.setEnabled(false);
        btnCrear.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnEditar.setEnabled(false);
        posarCampsDisabledEnabled(false);
        btnRecuperar.setEnabled(true);
        vaciarCampos();
    }

    private void cargarListaSocisInactius() {
        ArrayList<Soci> socisInactius = pmysql.getSocisInactius();
        String[] columnNames = {"NIF", "Nom", "Cognom", "Cognom 2"};
        dtm2 = new DefaultTableModel(columnNames, 0);
        for (Soci s : socisInactius) {
            Vector v = new Vector();
            v.add(s.getNif());
            v.add(s.getNom());
            v.add(s.getCognom1());
            v.add(s.getCognom2());
            dtm2.addRow(v);
        }
        jTableInactivos.setModel(dtm2);
        jTableInactivos.setEnabled(true);
    }
}
