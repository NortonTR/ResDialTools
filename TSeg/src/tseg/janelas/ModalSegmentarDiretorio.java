/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tseg.janelas;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import tseg.acoes.AcaoJanelaPrincipal;
import tseg.arquivo.FiltroArquivo;
import tseg.arquivo.TratamentoArquivo;
import tseg.configuracoes.Configuracoes;
import tseg.controle.Controle;
import tseg.segmentacao.GerenciadorAcoes;
import tseg.segmentacao.SegmentadorAutomatico;

/**
 *
 * @author ctcca
 */
public class ModalSegmentarDiretorio extends javax.swing.JDialog {

    /**
     * Creates new form ModalSegmentarDiretorio
     */
    public ModalSegmentarDiretorio(boolean portugues) {
        initComponents();
        
        //Coisas feitas por fora
        setModal(true);
        setLocationRelativeTo(null);
        translateDescr(portugues);
        setDefaultEncoding();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        lblDiretorio = new javax.swing.JLabel();
        txtSelecionar = new javax.swing.JTextField();
        btnSelecionar = new javax.swing.JButton();
        lblFormat = new javax.swing.JLabel();
        rbtPalavras = new javax.swing.JRadioButton();
        rbtSentencas = new javax.swing.JRadioButton();
        rbtParagrafos = new javax.swing.JRadioButton();
        btnSegmentar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblStatusTask = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();
        lblStatusDetalhe = new javax.swing.JLabel();
        cmbCodificacao = new javax.swing.JComboBox<>();
        lblCodificacao = new javax.swing.JLabel();

        setTitle("Segmentar todos os arquivos de um diretório");
        setName("modalSegmentarDiretorio"); // NOI18N
        setPreferredSize(new java.awt.Dimension(464, 253));
        setResizable(false);

        lblDiretorio.setText("Diretório:");

        txtSelecionar.setEditable(false);

        btnSelecionar.setText("Buscar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        lblFormat.setText("Formato de Segmentação:");

        buttonGroup1.add(rbtPalavras);
        rbtPalavras.setSelected(true);
        rbtPalavras.setText("Palavras");

        buttonGroup1.add(rbtSentencas);
        rbtSentencas.setText("Sentenças");

        buttonGroup1.add(rbtParagrafos);
        rbtParagrafos.setText("Parágrafos");

        btnSegmentar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSegmentar.setText("Segmentar");
        btnSegmentar.setEnabled(false);
        btnSegmentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSegmentarActionPerformed(evt);
            }
        });

        lblStatusTask.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatusTask.setText("Status da tarefa:");

        barraProgresso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));

        lblStatusDetalhe.setText("pronto para iniciar");

        cmbCodificacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UTF-8", "ISO-8859-1", "UTF-16", "US-ASCII" }));
        cmbCodificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodificacaoActionPerformed(evt);
            }
        });

        lblCodificacao.setText("Codificação:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(125, 125, 125)
                            .addComponent(btnSegmentar, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblStatusTask)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblStatusDetalhe))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(barraProgresso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDiretorio)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSelecionar))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(lblCodificacao)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cmbCodificacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(lblFormat)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rbtPalavras)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbtSentencas)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbtParagrafos)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiretorio)
                    .addComponent(txtSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFormat)
                    .addComponent(rbtPalavras)
                    .addComponent(rbtSentencas)
                    .addComponent(rbtParagrafos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCodificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodificacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSegmentar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatusTask)
                    .addComponent(lblStatusDetalhe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barraProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = AcaoJanelaPrincipal.getFileChooserCustomizado();
                                        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					TratamentoArquivo arquivo = new TratamentoArquivo();
					Configuracoes configuracoes = new Configuracoes();
					
					String abrir;
					if (tseg.janelas.JanelaPrincipal.portugues)
					{
						abrir = "Selecionar diretório";
					}	
					else
					{
						abrir = "Select directory";
					}
					int retornoFc = fc.showDialog(Controle.getJanelaPrincipal().getFrame(), abrir);

					if (retornoFc == JFileChooser.APPROVE_OPTION) {
						this.diretorioCorrente = fc.getSelectedFile();;
                                                this.txtSelecionar.setText(this.diretorioCorrente.getAbsolutePath());
                                                this.btnSegmentar.setEnabled(true);
                                        }
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void btnSegmentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSegmentarActionPerformed
        // TODO add your handling code here:
        
        File[] arquivos;
        List<File> arquivosFiltrado;
        String mensagem, head;
        int opcao;
        
        //Listar todos os arquivos do diretório corrente
        arquivos = this.diretorioCorrente.listFiles();
        
        //Filtrar arquivos de acordo com o formato
        arquivosFiltrado = FiltroArquivo.filtrarArquivos(arquivos);
        
        if (arquivosFiltrado.isEmpty())
        {
            //Dar mensagem de erro
            if(tseg.janelas.JanelaPrincipal.portugues)
            {
                mensagem = "Não existem arquivos de texto aptos para segmentação no diretório escolhido.";
                head = "Erro";
            }
            else
            {
                mensagem = "There are not text files suitable to segmentation at chosen directory";
                head = "Error";
            }

            JOptionPane.showMessageDialog(null, mensagem, head, JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            //Obter a opcao selecionada
            opcao = this.getFormatoSelecionado();
            
            GerenciadorAcoes.guardarContextoGeral();
            this.segmentarDiretorio(this.diretorioCorrente, arquivosFiltrado, opcao);
            GerenciadorAcoes.refazerContextoGeral();
        }
    }//GEN-LAST:event_btnSegmentarActionPerformed

    private void cmbCodificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodificacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCodificacaoActionPerformed

    private int getFormatoSelecionado()
    {
        if(this.rbtPalavras.isSelected())
            return SegmentadorAutomatico.PALAVRAS;
        
        if(this.rbtSentencas.isSelected())
            return SegmentadorAutomatico.SENTENCAS;
        
        if(this.rbtParagrafos.isSelected())
            return SegmentadorAutomatico.PARAGRAGOS;
        
        return 0;
    }
    
    private void translateDescr(boolean portugues)
    {
        if(!portugues)
        {
            btnSegmentar.setText("Segment");
            btnSelecionar.setText("Find");
            lblDiretorio.setText("Directory:");
            lblFormat.setText("Segmentation format:");
            lblStatusDetalhe.setText("ready to start");
            lblStatusTask.setText("Task status:");
            rbtPalavras.setText("Words");
            rbtParagrafos.setText("Paragraphs");
            rbtSentencas.setText("Sentences");
            lblCodificacao.setText("Encoding:");
            
            setTitle("Segment all files from a directory");
        }
    }
    
    private void setStatusDetalhe(int id, File arquivo)
    {
        boolean portugues = Controle.getJanelaPrincipal().isPortugues();
        
        if(portugues)
        {
            switch(id)
            {
                case 1:
                    this.lblStatusDetalhe.setText("segmentando " + arquivo.getName());
                    break;
                case 2:
                    this.lblStatusDetalhe.setText("segmentação finalizada");
                    break;
                case 3:
                    this.lblStatusDetalhe.setText("pronto para iniciar");
                    break;
            }
        }
        else
        {
            switch(id)
            {
                case 1:
                    this.lblStatusDetalhe.setText("segmenting " + arquivo.getName());
                    break;
                case 2:
                    this.lblStatusDetalhe.setText("segmentation finished");
                    break;
                case 3:
                    this.lblStatusDetalhe.setText("ready to start");
                    break;
            }
        }
        
        paintComponents(this.getGraphics());
    }
    
    public void segmentarDiretorio(File diretorio, List<File> arquivos, int opcao)
    {
        int ind;
        float unidProgresso, progresso;
        File arqLer;
        String textoArq, nomeRet;
        String[] nomeRetorno;
        Map<File, String> arquivosTexto = new HashMap<File, String>();
        TratamentoArquivo arquivo = new TratamentoArquivo();

        unidProgresso = 100/arquivos.size();
        progresso = 0;
        
        Charset AntEncoding = Configuracoes.ENCODING_ATUAL;
        Configuracoes.ENCODING_ATUAL = getDefaultEncoding();

        for(ind=0; ind<arquivos.size(); ind++)
        {              
            File retorno;

            arqLer = arquivos.get(ind);

            //Mudar o texto do status para indicar qual arquivo está sendo segmentado
            this.setStatusDetalhe(1, arqLer);
            
            //Ler o arquivo e segmentar conforme a opção
            textoArq = arquivo.leArquivo(arqLer);

            //Segmentar automaticamente o texto conforme a opcao
            textoArq = SegmentadorAutomatico.segmentar(textoArq, Integer.valueOf(opcao));

            //Abrir um arquivo segmentado e salvar
            nomeRet = arqLer.getName();
            nomeRetorno = nomeRet.split("\\.");

            retorno = new File(diretorio.getAbsolutePath() + File.separator + nomeRetorno[0] + "_tseg." + nomeRetorno[1]);

            arquivosTexto.put(retorno, textoArq);

            //Mudar a barra de progresso para indicar progressao
            progresso = progresso + unidProgresso;
            this.setProgresso(progresso);
        }
        
        //Pede para a classe responsável pela gravação dos arquivos gravar o lote de arquivos
        this.setProgresso(100);
        arquivo.gravaDiretorio(arquivosTexto);
        this.setStatusDetalhe(2, null);
        
        Configuracoes.ENCODING_ATUAL = AntEncoding;
    }
    
    //Aumentar o progresso
    private void setProgresso(float progresso)
    {
        int progress;
        
        progress = Math.round(progresso);
        
        if(progress > 100)
            progress = 100;
        
        this.barraProgresso.setValue(progress);
        paintComponents(this.getGraphics());
    }
    
    private void setDefaultEncoding()
    {
        if(Configuracoes.ENCODING_ATUAL.equals(StandardCharsets.UTF_8))
            cmbCodificacao.setSelectedIndex(0);
        
        if(Configuracoes.ENCODING_ATUAL.equals(StandardCharsets.ISO_8859_1))
            cmbCodificacao.setSelectedIndex(1);
                
        if(Configuracoes.ENCODING_ATUAL.equals(StandardCharsets.UTF_16))
            cmbCodificacao.setSelectedIndex(2);
                        
        if(Configuracoes.ENCODING_ATUAL.equals(StandardCharsets.US_ASCII))
            cmbCodificacao.setSelectedIndex(3);
    }
    
    private Charset getDefaultEncoding()
    {
        switch(cmbCodificacao.getSelectedIndex())
        {
            case 0:
                return StandardCharsets.UTF_8;
            case 1:
                return StandardCharsets.ISO_8859_1;
            case 2:
                return StandardCharsets.UTF_16;
            case 3:
                return StandardCharsets.US_ASCII;
        }
        
        return StandardCharsets.UTF_8;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnSegmentar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbCodificacao;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCodificacao;
    private javax.swing.JLabel lblDiretorio;
    private javax.swing.JLabel lblFormat;
    private javax.swing.JLabel lblStatusDetalhe;
    private javax.swing.JLabel lblStatusTask;
    private javax.swing.JRadioButton rbtPalavras;
    private javax.swing.JRadioButton rbtParagrafos;
    private javax.swing.JRadioButton rbtSentencas;
    private javax.swing.JTextField txtSelecionar;
    // End of variables declaration//GEN-END:variables

    private java.io.File diretorioCorrente;
}
