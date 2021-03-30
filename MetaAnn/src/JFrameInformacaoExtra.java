/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MetaAnn;

import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class JFrameInformacaoExtra extends javax.swing.JDialog {

    /**
     * Creates new form JFrameInformacaoExtra
     */
    public JFrameInformacaoExtra(JFrame parent) {
        super(parent);
        initComponents();
        setIcon();
        setModal(true);
    }

    JFrameInformacaoExtra(String valor, MetaAnn metaAnn, int index, int lista, Locale locale) {
        
        this(metaAnn);
        this.VALOR = valor;
        this.META_ANN = metaAnn;
        this.LISTA = lista;
        this.LOCALE = locale;
        this.INDEX = index;
        carregaEstado();
        
        ResourceBundle bundle = ResourceBundle.getBundle("MetaAnn.Idioma.MetaAnn.MetaAnn", this.LOCALE);
        setTitle(bundle.getString("tituloDialogo"));
        this.Label_Titulo_Informacao_Extra.setText(bundle.getString("tituloInfoExtra"));
    }

    String VALOR;
    MetaAnn META_ANN;
    int LISTA;
    int INDEX;
    Locale LOCALE;
    
    private void carregaEstado(){
        
        Text_Titulo_Informacao_Extra.setDocument(new ValidaCaracteres(20));
        Text_Titulo_Informacao_Extra.setText(VALOR);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        Label_Titulo_Informacao_Extra = new javax.swing.JLabel();
        Text_Titulo_Informacao_Extra = new javax.swing.JTextField();
        Botao_Cancelar = new javax.swing.JButton();
        Botao_Salvar = new javax.swing.JButton();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Label_Titulo_Informacao_Extra.setText("Título da Informação Extra");

        Text_Titulo_Informacao_Extra.setText("jTextField1");

        Botao_Cancelar.setText("Cancelar");
        Botao_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_CancelarActionPerformed(evt);
            }
        });

        Botao_Salvar.setText("Salvar");
        Botao_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_SalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Label_Titulo_Informacao_Extra)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(Text_Titulo_Informacao_Extra)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 242, Short.MAX_VALUE)
                        .addComponent(Botao_Salvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Botao_Cancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label_Titulo_Informacao_Extra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Text_Titulo_Informacao_Extra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Botao_Cancelar)
                    .addComponent(Botao_Salvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Botao_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_CancelarActionPerformed
        
        META_ANN.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_Botao_CancelarActionPerformed

    private void Botao_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_SalvarActionPerformed

        ResourceBundle bundle = ResourceBundle.getBundle("MetaAnn.Idioma.MetaAnn.MetaAnn", LOCALE);
        
        //Validar se o título da informação extra está preenchido
        if(Text_Titulo_Informacao_Extra.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(this, bundle.getString("erroCampoObrig"), bundle.getString("tituloErro"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        META_ANN.EditaValorInformacaoExtra(INDEX, Text_Titulo_Informacao_Extra.getText().toUpperCase(), LISTA);
        META_ANN.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_Botao_SalvarActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameInformacaoExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameInformacaoExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameInformacaoExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameInformacaoExtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameInformacaoExtra(null).setVisible(true);
            }
        });
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Botao_Cancelar;
    private javax.swing.JButton Botao_Salvar;
    private javax.swing.JLabel Label_Titulo_Informacao_Extra;
    private javax.swing.JTextField Text_Titulo_Informacao_Extra;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables
}