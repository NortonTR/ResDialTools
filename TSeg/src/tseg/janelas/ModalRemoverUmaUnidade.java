/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tseg.janelas;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import tseg.controle.Controle;
import tseg.segmentacao.SegmentadorManual;
import tseg.segmentacao.Unidade;

/**
 *
 * @author ctcca
 */
public class ModalRemoverUmaUnidade extends javax.swing.JDialog {

    /**
     * Creates new form ModalRemoverUmaUnidade
     */
    public ModalRemoverUmaUnidade(boolean portugues) {
        initComponents();
        initComponents2();
        
        //Coisas feitas por fora
        setModal(true);
        setLocationRelativeTo(null);
        translateDescr(portugues);
        
        //Carregar as unidades na lista
        this.carregarUnidades();
        
        setVisible(true);
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
        listUnidadesFrom = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listUnidadesRemover = new javax.swing.JList<>();
        btnPassar = new javax.swing.JButton();
        btnPassarTudo = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnVoltarTudo = new javax.swing.JButton();
        btnRemoverUnidades = new javax.swing.JButton();
        lblUnidadesTexto = new javax.swing.JLabel();
        lblUnidadesRemover = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txpDica = new javax.swing.JTextPane();

        setTitle("Remover uma unidade");
        setResizable(false);

        jScrollPane1.setViewportView(listUnidadesFrom);

        jScrollPane2.setViewportView(listUnidadesRemover);

        btnPassar.setText(">");
        btnPassar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPassarActionPerformed(evt);
            }
        });

        btnPassarTudo.setText(">>");
        btnPassarTudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPassarTudoActionPerformed(evt);
            }
        });

        btnVoltar.setText("<");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnVoltarTudo.setText("<<");
        btnVoltarTudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarTudoActionPerformed(evt);
            }
        });

        btnRemoverUnidades.setText("Remover unidades");
        btnRemoverUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverUnidadesActionPerformed(evt);
            }
        });

        lblUnidadesTexto.setText("Todas as unidades");

        lblUnidadesRemover.setText("Unidades a remover");

        txpDica.setEditable(false);
        txpDica.setBackground(new java.awt.Color(240, 240, 240));
        txpDica.setText("Dica: você também pode remover uma unidade clicando com o botão direito do mouse sobre o texto e apontando para \"Remover Unidade...\"");
        jScrollPane3.setViewportView(txpDica);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnPassar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnPassarTudo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnVoltarTudo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblUnidadesTexto))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUnidadesRemover)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnRemoverUnidades, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUnidadesTexto)
                    .addComponent(lblUnidadesRemover))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnPassarTudo)
                        .addGap(18, 18, 18)
                        .addComponent(btnPassar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVoltar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVoltarTudo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoverUnidades)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initComponents2()
    {
        this.modelFrom = new DefaultListModel();
        this.modelRemover = new DefaultListModel();
        
        this.listUnidadesFrom.setModel(this.modelFrom);
        this.listUnidadesRemover.setModel(this.modelRemover);
    }
    
    private void btnPassarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPassarActionPerformed
        // TODO add your handling code here:
        
        int[] indices;
        int j;
        indices = this.listUnidadesFrom.getSelectedIndices();
        
        for(int i=indices.length-1; i >= 0; i--)
        {
            j = indices[i];
            this.inserirOrdenado((Unidade) this.modelFrom.get(j), this.modelRemover);
            this.modelFrom.remove(j);
        }
        
    }//GEN-LAST:event_btnPassarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // TODO add your handling code here:
        
        int[] indices;
        int j;
        indices = this.listUnidadesRemover.getSelectedIndices();
        
        for(int i=indices.length-1; i >= 0; i--)
        {
            j = indices[i];
            this.inserirOrdenado((Unidade) this.modelRemover.get(j), this.modelFrom);
            this.modelRemover.remove(j);
        }
        
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnPassarTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPassarTudoActionPerformed
        // TODO add your handling code here:
        
        for(int i=this.modelFrom.getSize()-1; i>=0; i--)
        {
            this.inserirOrdenado((Unidade) this.modelFrom.get(i), this.modelRemover);
            this.modelFrom.remove(i);
        }
        
    }//GEN-LAST:event_btnPassarTudoActionPerformed

    private void btnVoltarTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarTudoActionPerformed
        // TODO add your handling code here:
        
        for(int i=this.modelRemover.getSize()-1; i>=0; i--)
        {
            this.inserirOrdenado((Unidade) this.modelRemover.get(i), this.modelFrom);
            this.modelRemover.remove(i);
        }
        
    }//GEN-LAST:event_btnVoltarTudoActionPerformed

    private void btnRemoverUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverUnidadesActionPerformed
        // TODO add your handling code here:
        
        //Obter lista de unidades destinadas à remoção
        ArrayList<Unidade> listaRemover = new ArrayList<Unidade>();
        String novoTexto;
        
        for(int i=this.modelRemover.getSize()-1; i>=0; i--)
        {
            listaRemover.add((Unidade) this.modelRemover.get(i));
            this.modelRemover.remove(i);
        }
        
        novoTexto = SegmentadorManual.removeListaUnidades(Controle.getJanelaPrincipal().getAbaSegmentaca().getTextoTxaSegmentacao(), listaRemover);
        Controle.getJanelaPrincipal().getAbaSegmentaca().setTextoTxaSegmentacao(novoTexto);
        
        Controle.setCursorInicioTexto();
    }//GEN-LAST:event_btnRemoverUnidadesActionPerformed

    private void inserirOrdenado(Unidade u, DefaultListModel modelo)
    {
        boolean inseriu = false;
        
        for(int i = 0; i < modelo.getSize(); i++)
        {
            if(u.getId() < ((Unidade) modelo.get(i)).getId())
            {
                modelo.add(i, u);
                inseriu = true;
                break;
            }
        }
        
        if(!inseriu)
            modelo.addElement(u);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPassar;
    private javax.swing.JButton btnPassarTudo;
    private javax.swing.JButton btnRemoverUnidades;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton btnVoltarTudo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblUnidadesRemover;
    private javax.swing.JLabel lblUnidadesTexto;
    private javax.swing.JList<String> listUnidadesFrom;
    private javax.swing.JList<String> listUnidadesRemover;
    private javax.swing.JTextPane txpDica;
    // End of variables declaration//GEN-END:variables
    private DefaultListModel modelFrom, modelRemover;
    
    private void translateDescr(boolean portugues)
    {
        if(!portugues)
        {
            this.setTitle("Remove a unit");
            this.lblUnidadesTexto.setText("All units");
            this.lblUnidadesRemover.setText("Units to remove");
            this.btnRemoverUnidades.setText("Remove units");
        }
    }
    
    private void carregarUnidades()
    {
        ArrayList<Unidade> unidades = Controle.getControladorUnidades().getUnidadesInicio();
        
        for(Unidade unit : unidades)
        {
            this.modelFrom.addElement(unit);
        }
    }
}
