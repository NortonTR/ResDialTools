/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MetaAnn;

import MetaAnn.file.FiltroArquivo;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/**
 *
 * @author Tiago
 */
public class ConfiguracaoFerramenta extends javax.swing.JFrame {

    /**
     * Creates new form Configuracao_Ferramenta
     */
    
    Locale locale;
    MetaAnn metaAnn;
    
    public ConfiguracaoFerramenta() {
        initComponents();
        setIcon();
        
        //Lista global de idiomas
        mapaIdiomas = new HashMap<String, String>();
        mapaIdiomas.put("Português", "pt_BR");
        mapaIdiomas.put("Portuguese", "pt_BR");
        mapaIdiomas.put("Inglês", "en_US");
        mapaIdiomas.put("English", "en_US");
    }

    ConfiguracaoFerramenta(Locale locale,MetaAnn metaAnn) {
        this();
        this.locale = locale;
        this.metaAnn = metaAnn;
        jTextNomeProjeto.setDocument(new ValidaEntrada());
        internacionaliza();
    }

    private void internacionaliza() {
        ResourceBundle bundle = ResourceBundle.getBundle("MetaAnn.Idioma.ConfiguracaoFerramenta.ConfiguracaoFerramenta", locale);
        
        setTitle("MetaAnn - " + bundle.getString("definicaoFerramenta"));
        jLabelNomeProjeto.setText(bundle.getString("nomeProjeto"));
        Text_Principal.setText(bundle.getString("mensagem"));
        Botao_Cancelar.setText(bundle.getString("cancelar"));
        Botao_Terminar.setText(bundle.getString("concluir"));
        lblDiretorioSaida.setText(bundle.getString("diretorioSaida"));
        btnBuscar.setText(bundle.getString("btnBuscar"));
        
        Combo_Idioma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { bundle.getString("portugues"), bundle.getString("ingles") }));
    }
    
  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Text_Principal = new javax.swing.JLabel();
        Combo_Idioma = new javax.swing.JComboBox();
        Botao_Cancelar = new javax.swing.JButton();
        Botao_Terminar = new javax.swing.JButton();
        jLabelNomeProjeto = new javax.swing.JLabel();
        jTextNomeProjeto = new javax.swing.JTextField();
        lblDiretorioSaida = new javax.swing.JLabel();
        txtDiretorioSaida = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Text_Principal.setText("Em qual Linguaguem deseja gerar a ferramenta ?");

        Combo_Idioma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Português", "English" }));
        Combo_Idioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_IdiomaActionPerformed(evt);
            }
        });

        Botao_Cancelar.setText("jButton1");
        Botao_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_CancelarActionPerformed(evt);
            }
        });

        Botao_Terminar.setText("jButton2");
        Botao_Terminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_TerminarActionPerformed(evt);
            }
        });

        jLabelNomeProjeto.setText("jLabel1");

        jTextNomeProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomeProjetoActionPerformed(evt);
            }
        });

        lblDiretorioSaida.setText("Diretório de saída");

        txtDiretorioSaida.setEditable(false);
        txtDiretorioSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiretorioSaidaActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Text_Principal, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                            .addComponent(Combo_Idioma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNomeProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextNomeProjeto)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(Botao_Cancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Botao_Terminar))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblDiretorioSaida)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDiretorioSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabelNomeProjeto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Text_Principal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Combo_Idioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDiretorioSaida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiretorioSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Botao_Cancelar)
                    .addComponent(Botao_Terminar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Botao_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_CancelarActionPerformed
        metaAnn.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Botao_CancelarActionPerformed

    private void Botao_TerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_TerminarActionPerformed
        
        ResourceBundle bundle = ResourceBundle.getBundle("MetaAnn.Idioma.ConfiguracaoFerramenta.ConfiguracaoFerramenta", locale);
        
        String nomeProjeto = jTextNomeProjeto.getText().trim();
        String diretorioProjeto = txtDiretorioSaida.getText().trim();
        
        if(validarNomeProjeto() && validarDiretorioSaida()){
            String[] selecionado = mapaIdiomas.get(Combo_Idioma.getSelectedItem().toString()).split("_");
            Locale ferramentaLocale = new Locale(selecionado[0],selecionado[1]);
            metaAnn.createProjectFolder(diretorioProjeto, nomeProjeto);
            metaAnn.ConstroiClasses(ferramentaLocale, nomeProjeto);
            
            JOptionPane.showMessageDialog(this, bundle.getString("ferramentaGerada"), bundle.getString("tituloFinalizar"), JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
            metaAnn.setVisible(true);
        }
    }//GEN-LAST:event_Botao_TerminarActionPerformed

    private boolean validarNomeProjeto()
    {
        String nomeProjeto = jTextNomeProjeto.getText().trim();
        
        if(nomeProjeto.length()!=0)
            return true;
        else
        {
            ResourceBundle bundle = ResourceBundle.getBundle("MetaAnn.Idioma.ConfiguracaoFerramenta.ConfiguracaoFerramenta", locale);
            JOptionPane.showMessageDialog(this, bundle.getString("erroNomeProjeto"), "MetaAnn", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private boolean validarDiretorioSaida()
    {
        String diretorioSaida = txtDiretorioSaida.getText().trim();
        
        if(diretorioSaida.length()!=0)
            return true;
        else
        {
            ResourceBundle bundle = ResourceBundle.getBundle("MetaAnn.Idioma.ConfiguracaoFerramenta.ConfiguracaoFerramenta", locale);
            JOptionPane.showMessageDialog(this, bundle.getString("erroDiretorioObrigatorio"), "MetaAnn", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       Botao_CancelarActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    private void jTextNomeProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNomeProjetoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNomeProjetoActionPerformed

    private void txtDiretorioSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiretorioSaidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiretorioSaidaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        
        ResourceBundle bundle = ResourceBundle.getBundle("MetaAnn.Idioma.ConfiguracaoFerramenta.ConfiguracaoFerramenta", locale);
        
        JFileChooser fc;
        fc = new JFileChooser();
        
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setApproveButtonToolTipText(bundle.getString("selecionar"));
        fc.setDialogTitle(bundle.getString("buscarDiretorioSaida"));
        fc.setLocale(locale);
        
        SwingUtilities.updateComponentTreeUI(fc);
        int retornoFc = fc.showDialog(this, bundle.getString("selecionar"));
        
        if(retornoFc==JFileChooser.APPROVE_OPTION)
        {
            this.txtDiretorioSaida.setText(fc.getSelectedFile().getAbsolutePath());
        }
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void Combo_IdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_IdiomaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Combo_IdiomaActionPerformed

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
            java.util.logging.Logger.getLogger(ConfiguracaoFerramenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfiguracaoFerramenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfiguracaoFerramenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfiguracaoFerramenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfiguracaoFerramenta().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Botao_Cancelar;
    private javax.swing.JButton Botao_Terminar;
    private javax.swing.JComboBox Combo_Idioma;
    private javax.swing.JLabel Text_Principal;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabelNomeProjeto;
    private javax.swing.JTextField jTextNomeProjeto;
    private javax.swing.JLabel lblDiretorioSaida;
    private javax.swing.JTextField txtDiretorioSaida;
    // End of variables declaration//GEN-END:variables
    private Map<String, String> mapaIdiomas;

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
    }
    
}
