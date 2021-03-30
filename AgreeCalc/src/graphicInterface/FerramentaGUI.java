/*
    Este arquivo é parte do programa AgreeCalc
    
    AgreeCalc é um software livre; você pode redistribui-lo e/ou 

    modifica-lo dentro dos termos da Licença Pública Geral GNU como 

    publicada pela Fundação do Software Livre (FSF); na versão 2 da 

    Licença, ou qualquer versão.



    Este programa é distribuido na esperança que possa ser  util, 

    mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUAÇÂO a qualquer

    MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a

    Licença Pública Geral GNU para maiores detalhes.



    Você deve ter recebido uma cópia da Licença Pública Geral GNU

    junto com este programa, se não, escreva para a Fundação do Software

    Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 
 */


package graphicInterface;

import ferramentaic.RUser;
import ferramentaic.Report;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import workers.AgreeCalcWorker;
import workers.ApplyChanges;
import workers.RTester;
import workers.ReportBuilder;

/**
 *
 * @author Alexandre
 */
public class FerramentaGUI extends javax.swing.JFrame {
    private static int coeficientesEscolhidos = 0;
    private static String[] TodosChecks;
    private static boolean[] TodosChecksBackup;
    private static String[] TodosChecksDocs;
    private static boolean[] TodosChecksDocsBackup;
    static DefaultListModel modelodesc = new DefaultListModel();
    static DefaultListModel modelocons = new DefaultListModel();
    static DefaultListModel modelodescDoc = new DefaultListModel();
    static DefaultListModel modeloconsDoc = new DefaultListModel();
    static int relatorios = 0;
    public static FerramentaGUI eu;
    private ImageIcon icon;
    
    private static boolean starting = true;
   
    
    public Locale local = new Locale("en");
    public int first = 0;
    ResourceBundle bundle;
    
   
    private boolean canUndo, canRedo;
    
    public void atualizaChecksDocs(){//dos docs
        
        TodosChecksDocs = new String[RUser.getInstance().resumosOriginais.size()];
        TodosChecksDocsBackup = new boolean[RUser.getInstance().resumosOriginais.size()];
        
        modelodescDoc = new DefaultListModel();
        Documentos_Desconsiderados.setModel(modelodescDoc);
        
        modeloconsDoc = new DefaultListModel();
        Documentos_considerados.setModel(modeloconsDoc);
        
        //Documentos_considerados.setFixedCellWidth(Anotadores_considerados.getFixedCellWidth());
        
        Iterator<String> it = RUser.getInstance().resumosOriginais.iterator();
        int contador = 0;
        while(it.hasNext()){
            String resumo = it.next();
            
            TodosChecksDocs[contador] = resumo;  
            
            if(RUser.getInstance().resumos.contains(resumo)){
                modeloconsDoc.addElement(resumo);
                TodosChecksDocsBackup[contador] = true;
            }else {
                TodosChecksDocsBackup[contador] = false;
                modelodescDoc.addElement(resumo);
            }
            
            contador++;
            
        }
    }
    
    
    public void atualizaChecksAnotadores(){//dos anotadores
        modelodesc = new DefaultListModel();
        Anotadores_desconsiderados.setModel(modelodesc);
        
        modelocons = new DefaultListModel();
        Anotadores_considerados.setModel(modelocons);
        
        TodosChecks = new String[RUser.getInstance().anotadoresOriginais.size()];
        TodosChecksBackup = new boolean[RUser.getInstance().anotadoresOriginais.size()];
        
        Iterator<String> it = RUser.getInstance().anotadoresOriginais.iterator();
        int contador = 0;
        while(it.hasNext()){
            String anotador = it.next();
            TodosChecks[contador] = anotador;  
    
            if(RUser.getInstance().anotadores.contains(anotador)){
               TodosChecksBackup[contador] = true;
               modelocons.addElement(anotador);
            }else modelodesc.addElement(anotador);
            
            contador++;
        }
    }   
    
    public static FerramentaGUI getInstance(){
        if(eu==null){
            eu = new FerramentaGUI();
        }
        return eu;
    }
    /**
     * Creates new form FerramentaGUI
     */
    public FerramentaGUI() {
        initComponents();
        setLocationRelativeTo(null);
        exibirInfos();
        icon = new ImageIcon(getClass().getResource("myicon.png"));
        setIconImage(icon.getImage());
        
        //Adicionando validador (para não apagar as alterações do usuario
        //quando ele clicar em outras abas sem querer)
        VetoableChangeListener validator = new VetoableChangeListener() {

            @Override
            public void vetoableChange(PropertyChangeEvent evt)
                    throws PropertyVetoException {
                int oldSelection = (int) evt.getOldValue();
                if ((oldSelection == -1) || isValidTab(oldSelection)) return;

                throw new PropertyVetoException("change not valid", evt);

            }

            private boolean isValidTab(int oldSelection) {
                if(lastChange!=null){
                    if(oldSelection==1 || oldSelection==2){
                        JCheckBox check = new JCheckBox(bundle.getString("DontShowAgain"));
                        if(MainGUI.showDidntApplyChangesMsg){
                            int diagResp = JOptionPane.showOptionDialog(null,
                                            bundle.getString("QuestionForgetChanges"),
                                            bundle.getString("Attention"),
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.WARNING_MESSAGE,
                                            null,
                                            new Object[]{check,bundle.getString("Yes"),bundle.getString("No")},
                                            bundle.getString("No"));
                            if(check.isSelected()){
                                MainGUI.showDidntApplyChangesMsg=false;
                                MainGUI.gravarConfigs();
                            }
                            if(diagResp != 1) //posição do Yes)
                                return false;
                        }
                        return true;
                    }
                }
                return true;
            }
        };
        VetoableSingleSelectionModel model = new VetoableSingleSelectionModel();
        
        model.addVetoableChangeListener(validator);
        Abas.setModel(model);
        
        
        //setSize(new java.awt.Dimension(672, 500));
        canUndo = canRedo = false;
        updateEditMenuAvailability();
    }
    
    void internacionaliza(String loca1){
        if(loca1.equals("en")) local = new Locale(loca1, "US");
        else local = new Locale(loca1, "BR");
        
        bundle = ResourceBundle.getBundle("i18n.MyBundle", local);
        
        this.setTitle("AgreeCalc: "+bundle.getString("CorpusDescription"));
        OptionMenu.setText(bundle.getString("Options"));
        ChooseCorpus.setText(bundle.getString("MenuChangeCorpus")+"...");
        CloseApplicationMenu.setText(bundle.getString("CloseApplication"));
        
        RPath.setText(bundle.getString("MenuChangeRPath")+"...");
        LanguageMenu.setText(bundle.getString("Language"));
        enableConfirmDialog.setText(bundle.getString("EnableDialogsMenu"));
        disableConfirmDialogs.setText(bundle.getString("DisableDialogsMenu"));
        
        editMenu.setText(bundle.getString("EditMenu"));
        undoMenuItem.setText(bundle.getString("UndoMenu"));
        redoMenuItem.setText(bundle.getString("RedoMenu"));
        
        menuAjuda.setText(bundle.getString("HelpMenu"));
        menuItemSobre.setText(bundle.getString("About"));
        menuItemManual.setText(bundle.getString("Manual"));
        
        if(loca1.equals("en")) {
            Language1.setText(bundle.getString("English"));
            Language2.setText(bundle.getString("Portuguese"));
        } else {
            Language1.setText(bundle.getString("Portuguese"));
            Language2.setText(bundle.getString("English"));
        }
        
        Abas.setTitleAt(0, bundle.getString("CorpusDescription"));
        Abas.setTitleAt(1, bundle.getString("Annotators"));
        Abas.setTitleAt(2, bundle.getString("Documents"));
        Abas.setTitleAt(3, bundle.getString("AgreementCoefficients"));
        Abas.setTitleAt(4, bundle.getString("Reports"));
        
        if(starting){
            Abas.setSelectedIndex(0);
            starting=false;
        }
        
        Botao_CalculaCoeficiente.setText(bundle.getString("GenerateReports"));
        Botao_AplicarAnotadores.setText(bundle.getString("Apply"));
        Botao_AplicarDocs.setText(bundle.getString("Apply"));
        Botao_CancelarAnotadores.setText(bundle.getString("Cancel"));
        Botao_CancelarDocs.setText(bundle.getString("Cancel"));
        Botao_LimparAnotadores.setText(bundle.getString("Clear"));
        Botao_LimparDocs.setText(bundle.getString("Clear"));
        Botao_TodosAnotadores.setText(bundle.getString("SelectAll"));
        Botao_TodosDocs.setText(bundle.getString("SelectAll"));
        
        jLabel1.setText(bundle.getString("DiskLocation"));
        jLabel2.setText(bundle.getString("Scheme"));
        jLabel3.setText(bundle.getString("SourceCorpus"));
        jLabel4.setText(bundle.getString("AnalyzedUnits"));
        jLabel5.setText(bundle.getString("AmountAnnotators"));
        jLabel6.setText(bundle.getString("AnalyzedDocuments"));
        
        jLabel9.setText(bundle.getString("ConsideredAnnotators"));
        jLabel10.setText(bundle.getString("UnconsideredAnnotators"));
        
        jLabel11.setText(bundle.getString("UnconsideredDocuments"));
        jLabel12.setText(bundle.getString("ConsideredDocuments"));
        
        jLabel13.setText(bundle.getString("AvailableCoefficients"));
        Check_Alfa.setText(bundle.getString("Alpha"));
        Check_Alfa_Pares.setText(bundle.getString("Alpha"));
        
        Check_KappaCohen.setText(bundle.getString("KappaCohen"));
        Check_KappaCohen_Pares.setText(bundle.getString("KappaCohen"));
        
        Check_KappaFleiss.setText(bundle.getString("KappaFleiss"));
        Check_KappaFleiss_Pares.setText(bundle.getString("KappaFleiss"));
        
        Check_Percentage.setText(bundle.getString("PercentageAgreement"));
        Check_Percentage_Pares.setText(bundle.getString("PercentageAgreement"));
        
        Check_Robinsons.setText(bundle.getString("RobinsonsA"));
        Check_Robinsons_Pares.setText(bundle.getString("RobinsonsA"));
        
        Check_Kendalls.setText(bundle.getString("KendallsW"));
        Check_Kendalls_Pares.setText(bundle.getString("KendallsW"));
        
        jLabel7.setText(bundle.getString("EnablePairwise"));
        jLabel8.setText(bundle.getString("PairwiseWarning"));
        
        jLabel15.setText(bundle.getString("GeneralAnnotations"));
        jLabel16.setText(bundle.getString("GeneralAnnotationsExplain1"));
        jLabel17.setText(bundle.getString("GeneralAnnotationsExplain2"));
        checkModa.setText(bundle.getString("GeneralAnnotationsOp1"));
        checkMaioria.setText(bundle.getString("GeneralAnnotationsOp2"));
        jLabel14.setText(bundle.getString("ReportFormat"));
        JComponent.setDefaultLocale(local);
        UIManager.put("FileChooser.acceptAllFileFilterText",bundle.getString("FileChooserAllFilesTooltip"));
    }
    
    public void exibirInfos(){
        Text_caminho.setText(RUser.getInstance().diretorio);
        Text_scheme.setText(RUser.getInstance().scheme);
        Text_sourcecorpus.setText(RUser.getInstance().source_corpus); 
        Text_anotadores.setText(String.valueOf(RUser.getInstance().anotadores.size()));
        Text_documentos.setText(String.valueOf(RUser.getInstance().resumos.size()));
        Text_units.setText(String.valueOf(RUser.getInstance().qtdUnits.size()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Botao_CalculaCoeficiente = new javax.swing.JButton();
        Abas = new javax.swing.JTabbedPane();
        Aba_descricao = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Text_caminho = new javax.swing.JLabel();
        Text_scheme = new javax.swing.JLabel();
        Text_sourcecorpus = new javax.swing.JLabel();
        Text_units = new javax.swing.JLabel();
        Text_anotadores = new javax.swing.JLabel();
        Text_documentos = new javax.swing.JLabel();
        Aba_anotadores = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Anotadores_considerados = new javax.swing.JList();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        Botao_add = new javax.swing.JButton();
        Botao_remove = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Anotadores_desconsiderados = new javax.swing.JList();
        jLabel10 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        Botao_TodosAnotadores = new javax.swing.JButton();
        Botao_LimparAnotadores = new javax.swing.JButton();
        Botao_CancelarAnotadores = new javax.swing.JButton();
        Botao_AplicarAnotadores = new javax.swing.JButton();
        Aba_docs = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Botao_AplicarDocs = new javax.swing.JButton();
        Botao_CancelarDocs = new javax.swing.JButton();
        Botao_LimparDocs = new javax.swing.JButton();
        Botao_TodosDocs = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        Botao_addDoc = new javax.swing.JButton();
        Botao_removerDoc = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Documentos_Desconsiderados = new javax.swing.JList();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Documentos_considerados = new javax.swing.JList();
        jLabel12 = new javax.swing.JLabel();
        Aba_coef = new javax.swing.JPanel();
        Check_Alfa = new javax.swing.JCheckBox();
        Check_KappaFleiss = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        Check_Pares = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        Check_KappaFleiss_Pares = new javax.swing.JCheckBox();
        Check_Alfa_Pares = new javax.swing.JCheckBox();
        Check_KappaCohen = new javax.swing.JCheckBox();
        Check_Percentage = new javax.swing.JCheckBox();
        Check_KappaCohen_Pares = new javax.swing.JCheckBox();
        Check_Percentage_Pares = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        Check_Robinsons = new javax.swing.JCheckBox();
        Check_Kendalls = new javax.swing.JCheckBox();
        Check_Robinsons_Pares = new javax.swing.JCheckBox();
        Check_Kendalls_Pares = new javax.swing.JCheckBox();
        Aba_relatorio = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        checkHTML = new javax.swing.JCheckBox();
        checkXML = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        checkModa = new javax.swing.JCheckBox();
        checkMaioria = new javax.swing.JCheckBox();
        checkPDF = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        ChooseCorpus = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        CloseApplicationMenu = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        redoMenuItem = new javax.swing.JMenuItem();
        OptionMenu = new javax.swing.JMenu();
        RPath = new javax.swing.JMenuItem();
        LanguageMenu = new javax.swing.JMenu();
        Language1 = new javax.swing.JMenuItem();
        Language2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        enableConfirmDialog = new javax.swing.JMenuItem();
        disableConfirmDialogs = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        menuItemManual = new javax.swing.JMenuItem();
        menuItemSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(672, 474));
        setResizable(false);

        Botao_CalculaCoeficiente.setText("Gerar Relatório(s)");
        Botao_CalculaCoeficiente.setEnabled(false);
        Botao_CalculaCoeficiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_CalculaCoeficienteActionPerformed(evt);
            }
        });

        Abas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                AbasStateChanged(evt);
            }
        });
        Abas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbasMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Localização em disco:");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel2.setText("scheme:");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel3.setText("source-corpus");

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel4.setText("Quantidade de units:");

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel5.setText("Quantidade de anotadores:");

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel6.setText("Quantidade de documentos anotados:");

        Text_caminho.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Text_caminho.setText(" ");

        Text_scheme.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Text_scheme.setText("Not Found!");

        Text_sourcecorpus.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Text_sourcecorpus.setText("Not Found!");

        Text_units.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Text_units.setText(" ");

        Text_anotadores.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Text_anotadores.setText(" ");

        Text_documentos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Text_documentos.setText(" ");

        javax.swing.GroupLayout Aba_descricaoLayout = new javax.swing.GroupLayout(Aba_descricao);
        Aba_descricao.setLayout(Aba_descricaoLayout);
        Aba_descricaoLayout.setHorizontalGroup(
            Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aba_descricaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Aba_descricaoLayout.createSequentialGroup()
                        .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Text_anotadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Text_units, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Text_sourcecorpus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Text_scheme)
                            .addComponent(Text_documentos, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 268, Short.MAX_VALUE))
                    .addComponent(Text_caminho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        Aba_descricaoLayout.setVerticalGroup(
            Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aba_descricaoLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Text_caminho)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Text_scheme))
                .addGap(18, 18, 18)
                .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Text_sourcecorpus))
                .addGap(18, 18, 18)
                .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Text_units))
                .addGap(18, 18, 18)
                .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Text_anotadores))
                .addGap(18, 18, 18)
                .addGroup(Aba_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Text_documentos))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        Abas.addTab("Descrição do Corpus", Aba_descricao);

        Aba_anotadores.setPreferredSize(new java.awt.Dimension(630, 320));
        Aba_anotadores.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Aba_anotadoresFocusGained(evt);
            }
        });
        Aba_anotadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Aba_anotadoresMouseClicked(evt);
            }
        });
        Aba_anotadores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setViewportView(Anotadores_considerados);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 250, 230));

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel9.setText("Anotadores Considerados");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, -1));

        jPanel7.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 250, 280));

        Botao_add.setText(">>");
        Botao_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_addActionPerformed(evt);
            }
        });

        Botao_remove.setText("<<");
        Botao_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_removeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Botao_add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Botao_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addComponent(Botao_add, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Botao_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 20, 110, 280));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setViewportView(Anotadores_desconsiderados);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 49, 250, 231));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel10.setText("Anotadores Desconsiderados");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, -1));

        jPanel7.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 20, 250, 280));

        jPanel10.setMaximumSize(new java.awt.Dimension(630, 50));

        Botao_TodosAnotadores.setText("Selecionar todos");
        Botao_TodosAnotadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_TodosAnotadoresActionPerformed(evt);
            }
        });

        Botao_LimparAnotadores.setText("Limpar");
        Botao_LimparAnotadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_LimparAnotadoresActionPerformed(evt);
            }
        });

        Botao_CancelarAnotadores.setText("Cancelar");
        Botao_CancelarAnotadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_CancelarAnotadoresActionPerformed(evt);
            }
        });

        Botao_AplicarAnotadores.setText("Aplicar mudanças");
        Botao_AplicarAnotadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_AplicarAnotadoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(Botao_TodosAnotadores, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Botao_LimparAnotadores, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Botao_CancelarAnotadores, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Botao_AplicarAnotadores, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Botao_TodosAnotadores, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Botao_LimparAnotadores, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Botao_CancelarAnotadores, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Botao_AplicarAnotadores, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel7.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 630, 50));

        jPanel9.add(jPanel7, java.awt.BorderLayout.CENTER);

        Aba_anotadores.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 370));

        Abas.addTab("Anotadores", Aba_anotadores);

        Aba_docs.setLayout(new java.awt.BorderLayout());

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(647, 47));

        Botao_AplicarDocs.setText("Aplicar mudanças");
        Botao_AplicarDocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_AplicarDocsActionPerformed(evt);
            }
        });

        Botao_CancelarDocs.setText("Cancelar");
        Botao_CancelarDocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_CancelarDocsActionPerformed(evt);
            }
        });

        Botao_LimparDocs.setText("Limpar");
        Botao_LimparDocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_LimparDocsActionPerformed(evt);
            }
        });

        Botao_TodosDocs.setText("Selecionar todos");
        Botao_TodosDocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_TodosDocsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addComponent(Botao_TodosDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Botao_LimparDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Botao_CancelarDocs, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Botao_AplicarDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Botao_AplicarDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Botao_CancelarDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Botao_LimparDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Botao_TodosDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel11.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        Botao_addDoc.setText(">>");
        Botao_addDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_addDocActionPerformed(evt);
            }
        });

        Botao_removerDoc.setText("<<");
        Botao_removerDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Botao_removerDocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Botao_addDoc, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
            .addComponent(Botao_removerDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(125, Short.MAX_VALUE)
                .addComponent(Botao_addDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Botao_removerDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        jPanel11.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setViewportView(Documentos_Desconsiderados);

        jPanel5.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 240, 238));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel11.setText("Documentos Desconsiderados");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel11.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 262, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setViewportView(Documentos_considerados);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 240, 240));

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel12.setText("Documentos Considerados");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel11.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 259, 290));

        Aba_docs.add(jPanel11, java.awt.BorderLayout.CENTER);

        Abas.addTab("Documentos", Aba_docs);

        Check_Alfa.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_Alfa.setText("Alfa de Krippendorff");
        Check_Alfa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_AlfaActionPerformed(evt);
            }
        });

        Check_KappaFleiss.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_KappaFleiss.setText("Kappa de Fleiss");
        Check_KappaFleiss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_KappaFleissActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setText("Habilitar cálculo de concordância entre pares de anotadores? ");

        Check_Pares.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Check_Pares.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Check_Pares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_ParesActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel8.setText("(Implicará num aumento significativo do tempo de resposta)");

        Check_KappaFleiss_Pares.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_KappaFleiss_Pares.setText("Kappa de Fleiss");
        Check_KappaFleiss_Pares.setEnabled(false);
        Check_KappaFleiss_Pares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_KappaFleiss_ParesActionPerformed(evt);
            }
        });

        Check_Alfa_Pares.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_Alfa_Pares.setText("Alfa de Krippendorf");
        Check_Alfa_Pares.setEnabled(false);
        Check_Alfa_Pares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_Alfa_ParesActionPerformed(evt);
            }
        });

        Check_KappaCohen.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_KappaCohen.setText("Kappa de Cohen");
        Check_KappaCohen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_KappaCohenActionPerformed(evt);
            }
        });

        Check_Percentage.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_Percentage.setText("Percentage Agreement");
        Check_Percentage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_PercentageActionPerformed(evt);
            }
        });

        Check_KappaCohen_Pares.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_KappaCohen_Pares.setText("Kappa de Cohen");
        Check_KappaCohen_Pares.setEnabled(false);
        Check_KappaCohen_Pares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_KappaCohen_ParesActionPerformed(evt);
            }
        });

        Check_Percentage_Pares.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_Percentage_Pares.setText("Percentage Agreement");
        Check_Percentage_Pares.setEnabled(false);
        Check_Percentage_Pares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_Percentage_ParesActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel13.setText("Coeficientes disponíveis:");

        Check_Robinsons.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_Robinsons.setText("A de Robinson");
        Check_Robinsons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_RobinsonsActionPerformed(evt);
            }
        });

        Check_Kendalls.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_Kendalls.setText("W de Kendall");
        Check_Kendalls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_KendallsActionPerformed(evt);
            }
        });

        Check_Robinsons_Pares.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_Robinsons_Pares.setText("A de Robinson");
        Check_Robinsons_Pares.setEnabled(false);
        Check_Robinsons_Pares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_Robinsons_ParesActionPerformed(evt);
            }
        });

        Check_Kendalls_Pares.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Check_Kendalls_Pares.setText("W de Kendall");
        Check_Kendalls_Pares.setEnabled(false);
        Check_Kendalls_Pares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Check_Kendalls_ParesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Aba_coefLayout = new javax.swing.GroupLayout(Aba_coef);
        Aba_coef.setLayout(Aba_coefLayout);
        Aba_coefLayout.setHorizontalGroup(
            Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aba_coefLayout.createSequentialGroup()
                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Aba_coefLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(Aba_coefLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Check_Pares))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Aba_coefLayout.createSequentialGroup()
                                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Check_Alfa)
                                    .addComponent(Check_KappaFleiss)
                                    .addComponent(Check_KappaCohen))
                                .addGap(53, 53, 53)
                                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Check_Kendalls)
                                    .addComponent(Check_Robinsons)
                                    .addComponent(Check_Percentage)))))
                    .addGroup(Aba_coefLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Check_Alfa_Pares)
                            .addComponent(Check_KappaFleiss_Pares)
                            .addComponent(Check_KappaCohen_Pares))
                        .addGap(29, 29, 29)
                        .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Check_Percentage_Pares)
                            .addComponent(Check_Robinsons_Pares)
                            .addComponent(Check_Kendalls_Pares))))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        Aba_coefLayout.setVerticalGroup(
            Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aba_coefLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Check_Alfa)
                    .addComponent(Check_Percentage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Check_KappaFleiss)
                    .addComponent(Check_Robinsons))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Check_KappaCohen)
                    .addComponent(Check_Kendalls))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Check_Pares)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Check_Alfa_Pares)
                    .addComponent(Check_Percentage_Pares))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Check_Robinsons_Pares)
                    .addComponent(Check_KappaFleiss_Pares))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Aba_coefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Check_Kendalls_Pares)
                    .addComponent(Check_KappaCohen_Pares))
                .addGap(35, 35, 35))
        );

        Abas.addTab("Coeficientes de Concordância", Aba_coef);

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel14.setText("Formato do relatório dos cálculos:");

        checkHTML.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        checkHTML.setText("HTML");
        checkHTML.setEnabled(false);
        checkHTML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkHTMLActionPerformed(evt);
            }
        });

        checkXML.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        checkXML.setText("XML");
        checkXML.setEnabled(false);
        checkXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkXMLActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel15.setText("Anotações Gerais:");

        checkModa.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        checkModa.setText("Conjunto modal de anotações (XML)");
        checkModa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkModaActionPerformed(evt);
            }
        });

        checkMaioria.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        checkMaioria.setText("Conjunto majoritário de anotações (XML)");
        checkMaioria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMaioriaActionPerformed(evt);
            }
        });

        checkPDF.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        checkPDF.setText("PDF");
        checkPDF.setEnabled(false);
        checkPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPDFActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel16.setText("(Os seguintes arquivos de anotações gerais - feitas por sub-conjuntos de anotadores -");

        jLabel17.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel17.setText(" serão armazenados no CORPUS, no formato de uma anotação simples)");

        javax.swing.GroupLayout Aba_relatorioLayout = new javax.swing.GroupLayout(Aba_relatorio);
        Aba_relatorio.setLayout(Aba_relatorioLayout);
        Aba_relatorioLayout.setHorizontalGroup(
            Aba_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aba_relatorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Aba_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addGroup(Aba_relatorioLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(Aba_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkXML)
                            .addComponent(checkHTML)
                            .addComponent(checkPDF)
                            .addComponent(checkMaioria)
                            .addComponent(checkModa)))
                    .addComponent(jLabel17))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        Aba_relatorioLayout.setVerticalGroup(
            Aba_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aba_relatorioLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(checkHTML)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkXML)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkPDF)
                .addGap(43, 43, 43)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGap(11, 11, 11)
                .addComponent(checkModa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkMaioria)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        Abas.addTab("Relatórios", Aba_relatorio);

        jMenu1.setText("Corpus");

        ChooseCorpus.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        ChooseCorpus.setText("Escolher Corpus..");
        ChooseCorpus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseCorpusActionPerformed(evt);
            }
        });
        jMenu1.add(ChooseCorpus);
        jMenu1.add(jSeparator1);

        CloseApplicationMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        CloseApplicationMenu.setText("Sair");
        CloseApplicationMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseApplicationMenuActionPerformed(evt);
            }
        });
        jMenu1.add(CloseApplicationMenu);

        jMenuBar1.add(jMenu1);

        editMenu.setText("Editar");

        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoMenuItem.setText("Desfazer");
        undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(undoMenuItem);

        redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoMenuItem.setText("Refazer");
        redoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(redoMenuItem);

        jMenuBar1.add(editMenu);

        OptionMenu.setText("Opções");

        RPath.setText("Caminho do R");
        RPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RPathActionPerformed(evt);
            }
        });
        OptionMenu.add(RPath);

        LanguageMenu.setText("Language");

        Language1.setText("jMenuItem1");
        Language1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Language1ActionPerformed(evt);
            }
        });
        LanguageMenu.add(Language1);

        Language2.setText("jMenuItem2");
        Language2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Language2ActionPerformed(evt);
            }
        });
        LanguageMenu.add(Language2);

        OptionMenu.add(LanguageMenu);
        OptionMenu.add(jSeparator2);

        enableConfirmDialog.setText("Ativar diálogos de confirmação");
        enableConfirmDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableConfirmDialogActionPerformed(evt);
            }
        });
        OptionMenu.add(enableConfirmDialog);

        disableConfirmDialogs.setText("Desativar diálogos de confirmação");
        disableConfirmDialogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disableConfirmDialogsActionPerformed(evt);
            }
        });
        OptionMenu.add(disableConfirmDialogs);

        jMenuBar1.add(OptionMenu);

        menuAjuda.setText("Ajuda");

        menuItemManual.setText("Manual");
        menuItemManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemManualActionPerformed(evt);
            }
        });
        menuAjuda.add(menuItemManual);

        menuItemSobre.setText("Sobre");
        menuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSobreActionPerformed(evt);
            }
        });
        menuAjuda.add(menuItemSobre);

        jMenuBar1.add(menuAjuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Botao_CalculaCoeficiente, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addComponent(Abas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Abas, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Botao_CalculaCoeficiente, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/MyBundle_en_US"); // NOI18N
        Abas.getAccessibleContext().setAccessibleName(bundle.getString("CorpusDescription")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AbasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AbasMouseClicked

    private void AbasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_AbasStateChanged
        // TODO add your handling code here:
        if(first<1) {
            first++;
            return;
        }
      
        lastChange=head=null; //reseta a lista de alterações
        canUndo=false;
        canRedo=false;
        this.updateEditMenuAvailability();
    
      JTabbedPane tp = (JTabbedPane) evt.getSource();
      int index = tp.getSelectedIndex(); 
      if(index==0){
          exibirInfos();
          this.setTitle("AgreeCalc: "+bundle.getString("CorpusDescription"));
      }
      if(index==1){
          atualizaChecksAnotadores();
          this.setTitle("AgreeCalc: "+bundle.getString("Annotators"));
      }
      if(index==2){
          atualizaChecksDocs();
          this.setTitle("AgreeCalc: "+bundle.getString("Documents"));
      }
      if(index==3){ 
          if(RUser.getInstance().anotadores.size()>=2){ 
              Check_KappaCohen.setEnabled(false);
              Check_KappaCohen.setSelected(false);
          } else Check_KappaCohen.setEnabled(true);
          this.setTitle("AgreeCalc: "+bundle.getString("AgreementCoefficients"));
      }
      if(index==4)this.setTitle("AgreeCalc: "+bundle.getString("Reports"));
      
      //adjustSize();
    }//GEN-LAST:event_AbasStateChanged

    private boolean aplicarMudancasAnotadores(){
        boolean podeAplicar;
        TreeSet<String> novosAnotadores = new TreeSet<String>();
        for(int i=0;i<Anotadores_considerados.getModel().getSize();i++){
         //   System.out.println(i);
          //  if(TodosChecks[i].isSelected()){
           //     System.out.println("check "+i+" entrou na lista ");
                novosAnotadores.add((String) Anotadores_considerados.getModel().getElementAt(i));//TodosChecks[i].getText());
            //}
        }
        podeAplicar = !(novosAnotadores.size()<=1);
        if(!podeAplicar){ //se o tamanho da lista de anotadores é menor do que 1, nenhum relatório pode ser gerado
            JOptionPane.showMessageDialog(null,bundle.getString("AnnotatorQuantityError")+"\n"+bundle.getString("ChangesNotApplied"),bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
        } else {
            RUser.getInstance().anotadores = novosAnotadores;
            this.applyChanges(RUser.getInstance().diretorio);
        }
        //System.out.println();
        return podeAplicar;
        /*
        if(RUser.getInstance().anotadores.size()<=1) Botao_CalculaCoeficiente.setEnabled(false);
        else  Botao_CalculaCoeficiente.setEnabled(true);*/
    }
    
    private void Check_AlfaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_AlfaActionPerformed
        if(Check_Alfa.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
        
    }//GEN-LAST:event_Check_AlfaActionPerformed

    private void Check_KappaFleissActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_KappaFleissActionPerformed
        // TODO add your handling code here:
        if(Check_KappaFleiss.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
        
    }//GEN-LAST:event_Check_KappaFleissActionPerformed

    private void Check_ParesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_ParesActionPerformed
        // TODO add your handling code here:
        if(Check_Pares.isSelected()){
            Check_KappaFleiss_Pares.setEnabled(true);
            Check_Alfa_Pares.setEnabled(true);
            Check_KappaCohen_Pares.setEnabled(true);
            Check_Percentage_Pares.setEnabled(true);
            Check_Robinsons_Pares.setEnabled(true);
            Check_Kendalls_Pares.setEnabled(true);
            
        }else{
            if(Check_KappaFleiss_Pares.isSelected()){
                coeficientesEscolhidos--;
                if(coeficientesEscolhidos==0){
                    disableReports();
                }
                Check_KappaFleiss_Pares.setSelected(false);
            }
            if(Check_Alfa_Pares.isSelected()){
                coeficientesEscolhidos--;
                if(coeficientesEscolhidos==0){
                    disableReports();
                }
                Check_Alfa_Pares.setSelected(false);
            }
            if(Check_KappaCohen_Pares.isSelected()){
                coeficientesEscolhidos--;
                if(coeficientesEscolhidos==0){
                    disableReports();
                }
                Check_KappaCohen_Pares.setSelected(false);
            }
            if(Check_Percentage_Pares.isSelected()){
                coeficientesEscolhidos--;
                if(coeficientesEscolhidos==0){
                    disableReports();
                }
                Check_Percentage_Pares.setSelected(false);
            }
            if(Check_Robinsons_Pares.isSelected()){
                coeficientesEscolhidos--;
                if(coeficientesEscolhidos==0){
                    disableReports();
                }
                Check_Robinsons_Pares.setSelected(false);
            }
            if(Check_Kendalls_Pares.isSelected()){
                coeficientesEscolhidos--;
                if(coeficientesEscolhidos==0){
                    disableReports();
                }
                Check_Kendalls_Pares.setSelected(false);
            }
            
            Check_Percentage_Pares.setEnabled(false);
            Check_KappaCohen_Pares.setEnabled(false);
            Check_KappaFleiss_Pares.setEnabled(false);
            Check_Alfa_Pares.setEnabled(false);
            Check_Kendalls_Pares.setEnabled(false);
            Check_Robinsons_Pares.setEnabled(false);
            //if(coeficientesEscolhidos==0) Botao_CalculaCoeficiente.setEnabled(false);
        }
        
    }//GEN-LAST:event_Check_ParesActionPerformed

    private void Check_Alfa_ParesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_Alfa_ParesActionPerformed
        // TODO add your handling code here:
        if(Check_Alfa_Pares.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_Alfa_ParesActionPerformed

    private void Check_KappaFleiss_ParesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_KappaFleiss_ParesActionPerformed
        // TODO add your handling code here:
        if(Check_KappaFleiss_Pares.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_KappaFleiss_ParesActionPerformed

    private void Botao_CalculaCoeficienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_CalculaCoeficienteActionPerformed
        //checar se pode gerar um relatório (provavelmente dividiremos em dois botões, mas por hora...)
        AgreeCalcWorker worker;
        if(canCreateReport()){
            if(canRedo || canUndo){
                JCheckBox check = new JCheckBox(bundle.getString("DontShowAgain"));
                if(MainGUI.showIgnoreChangesMsg){
                    int diagResp = JOptionPane.showOptionDialog(null,
                                    bundle.getString("QuestionIgnoreChanges"),
                                    bundle.getString("Attention"),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE,
                                    null,
                                    new Object[]{check,bundle.getString("Yes"),bundle.getString("No")},
                                    bundle.getString("No"));
                    if(check.isSelected()){
                        MainGUI.showIgnoreChangesMsg=false;
                        MainGUI.gravarConfigs();
                    }
                    if(diagResp != 1) //posicao do Yes em options[]
                        return;
                }
            }
            
            JCheckBox check = new JCheckBox(bundle.getString("DontShowAgain"));
            if(MainGUI.showLongProcessMsg){
                int diagResp = JOptionPane.showOptionDialog(null,
                                bundle.getString("QuestionLongProcess"),
                                bundle.getString("Attention"),
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE,
                                null,
                                new Object[]{check,bundle.getString("Yes"),bundle.getString("No")},
                                bundle.getString("No"));
                if(check.isSelected()){
                    MainGUI.showLongProcessMsg=false;
                    MainGUI.gravarConfigs();
                }
                if(diagResp != 1) //posicao do Yes em options[]
                    return;
            }
            
            worker = new ReportBuilder(bundle, RUser.local,Check_Alfa.isSelected(), 
                    Check_KappaFleiss.isSelected(), Check_KappaCohen.isSelected(),
                    Check_Percentage.isSelected(),Check_Robinsons.isSelected(),
                    Check_Kendalls.isSelected(), Check_Pares.isSelected(),
                    Check_KappaFleiss_Pares.isSelected(), Check_Alfa_Pares.isSelected(), 
                    Check_KappaCohen_Pares.isSelected(), Check_Percentage_Pares.isSelected(),
                    Check_Robinsons_Pares.isSelected(), Check_Kendalls_Pares.isSelected(),
                    checkXML.isSelected(),checkHTML.isSelected(),checkPDF.isSelected(),
                    checkMaioria.isSelected(),  checkModa.isSelected());
            DialogoProgresso diag = new DialogoProgresso(this.getRootPane(),worker,bundle);
            diag.setVisible(true);

            if(diag.getResponse().getClass().equals(Boolean.class)){
                boolean resp =(boolean) diag.getResponse();
                if(resp){
                    JOptionPane.showMessageDialog(null, bundle.getString("AnalysisComplete"), bundle.getString("InformationTitle"),JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        
    }//GEN-LAST:event_Botao_CalculaCoeficienteActionPerformed

    private void Check_KappaCohenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_KappaCohenActionPerformed
        // TODO add your handling code here:
        if(Check_KappaCohen.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_KappaCohenActionPerformed

    private void Check_PercentageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_PercentageActionPerformed
        // TODO add your handling code here:
        if(Check_Percentage.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_PercentageActionPerformed

    private void Check_KappaCohen_ParesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_KappaCohen_ParesActionPerformed
        // TODO add your handling code here:
        if(Check_KappaCohen_Pares.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_KappaCohen_ParesActionPerformed

    private void Check_Percentage_ParesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_Percentage_ParesActionPerformed
        // TODO add your handling code here:
        if(Check_Percentage_Pares.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_Percentage_ParesActionPerformed

    private void checkHTMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkHTMLActionPerformed
        // TODO add your handling code here:
        if(checkHTML.isSelected()){
            relatorios++;
            Botao_CalculaCoeficiente.setEnabled(true);
        }else{
            relatorios--;
            if(relatorios==0){
                Botao_CalculaCoeficiente.setEnabled(false);
            }
        }
    }//GEN-LAST:event_checkHTMLActionPerformed

    private void checkXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkXMLActionPerformed
        // TODO add your handling code here:
        if(checkXML.isSelected()){
            relatorios++;
            Botao_CalculaCoeficiente.setEnabled(true);
        }else{
            relatorios--;
            if(relatorios==0){
                Botao_CalculaCoeficiente.setEnabled(false);
            }
        }
    }//GEN-LAST:event_checkXMLActionPerformed

    private void checkPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPDFActionPerformed
        // TODO add your handling code here:
        if(checkPDF.isSelected()){
            relatorios++;
            Botao_CalculaCoeficiente.setEnabled(true);
        }else{
            relatorios--;
            if(relatorios==0){
                Botao_CalculaCoeficiente.setEnabled(false);
            }
        }
    }//GEN-LAST:event_checkPDFActionPerformed

    private void checkModaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkModaActionPerformed
        // TODO add your handling code here:
        if(checkModa.isSelected()){
            relatorios++;
            Botao_CalculaCoeficiente.setEnabled(true);
        }else{
            relatorios--;
            if(relatorios==0){
                Botao_CalculaCoeficiente.setEnabled(false);
            }
        }
    }//GEN-LAST:event_checkModaActionPerformed

    private void checkMaioriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMaioriaActionPerformed
        // TODO add your handling code here:
        if(checkMaioria.isSelected()){
            relatorios++;
            Botao_CalculaCoeficiente.setEnabled(true);
        }else{
            relatorios--;
            if(relatorios==0){
                Botao_CalculaCoeficiente.setEnabled(false);
            }
        }
    }//GEN-LAST:event_checkMaioriaActionPerformed

    private void RPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RPathActionPerformed
        // TODO add your handling code here:
        JFileChooser open = new JFileChooser();
         
         open.setFileSelectionMode(0);
         open.setApproveButtonText(bundle.getString("Select"));
         open.setDialogTitle(bundle.getString("RScriptSelect"));
         
        boolean bool = true;
        do{
            int op = open.showOpenDialog(this);  
            if(op == JFileChooser.APPROVE_OPTION){ 
                
                AgreeCalcWorker w = new RTester(bundle,open.getSelectedFile());
                DialogoProgresso d = new DialogoProgresso(this.getRootPane(),w,bundle);
                d.setVisible(true);
                bool = (Boolean) d.getResponse();
                
                if(bool){
                    JOptionPane.showMessageDialog(null, bundle.getString("RpathChangeSuccessful"),bundle.getString("InformationTitle"),JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("RpathChangeUnsuccessful"),bundle.getString("InformationTitle"),JOptionPane.ERROR_MESSAGE);
                }
            } 
        }while(!bool);
    }//GEN-LAST:event_RPathActionPerformed

    private void Language1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Language1ActionPerformed
        // TODO add your handling code here:
        if(Language1.getText().equals("English")) {
            internacionaliza("en");
            RUser r = RUser.getInstance();
            r.internacionaliza("en");
        } else {
            internacionaliza("pt");
            RUser r = RUser.getInstance();
            r.internacionaliza("pt");
        }
    }//GEN-LAST:event_Language1ActionPerformed

    private void Language2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Language2ActionPerformed
        // TODO add your handling code here:
        if(Language2.getText().equals("English")) {
            internacionaliza("en");
            RUser r = RUser.getInstance();
            r.internacionaliza("en");
        } else {
            internacionaliza("pt");
            RUser r = RUser.getInstance();
            r.internacionaliza("pt");
        }
    }//GEN-LAST:event_Language2ActionPerformed

    private void Check_RobinsonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_RobinsonsActionPerformed
        // TODO add your handling code here:
        if(Check_Robinsons.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_RobinsonsActionPerformed

    private void Check_KendallsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_KendallsActionPerformed
        // TODO add your handling code here:
        if(Check_Kendalls.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_KendallsActionPerformed

    private void Check_Robinsons_ParesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_Robinsons_ParesActionPerformed
        // TODO add your handling code here:
        if(Check_Robinsons_Pares.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_Robinsons_ParesActionPerformed

    private void Check_Kendalls_ParesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Check_Kendalls_ParesActionPerformed
        // TODO add your handling code here:
        if(Check_Kendalls_Pares.isSelected()){
            coeficientesEscolhidos++;
            if(RUser.getInstance().anotadores.size()>1){
                enableReports();
            }
        }else{
            coeficientesEscolhidos--;
            if(coeficientesEscolhidos==0){
                disableReports();
            }
        }
    }//GEN-LAST:event_Check_Kendalls_ParesActionPerformed

    private void Botao_removerDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_removerDocActionPerformed
        // TODO add your handling code here:
        if(Documentos_considerados.getSelectedValue() != null){
            //adiciona as mudanças no historico
            this.addChange(Documentos_considerados,true,false);
            
            List<String> selecionados = (List<String>) Documentos_considerados.getSelectedValuesList();
            
            for(String el : selecionados){
                modeloconsDoc.removeElement(el);
                modelodescDoc.addElement(el);
            }
        }
    }//GEN-LAST:event_Botao_removerDocActionPerformed

    private void Botao_addDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_addDocActionPerformed
        // TODO add your handling code here:
        if(Documentos_Desconsiderados.getSelectedValue() != null){
            //adiciona as mudanças no historico
            this.addChange(Documentos_Desconsiderados,false,false);
            
            List<String> selecionados = (List<String>) Documentos_Desconsiderados.getSelectedValuesList();
            
            for(String el : selecionados){
                modelodescDoc.removeElement(el);
                modeloconsDoc.addElement(el);
            }
        }
    }//GEN-LAST:event_Botao_addDocActionPerformed

    private void Botao_AplicarDocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_AplicarDocsActionPerformed
        JCheckBox check = new JCheckBox(bundle.getString("DontShowAgain"));
        if(MainGUI.showCantInterruptMessage){
            int diagResp = JOptionPane.showOptionDialog(null,
                            bundle.getString("QuestionInterruptProcess"),
                            bundle.getString("Attention"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            new Object[]{check,bundle.getString("Yes"),bundle.getString("No")},
                            bundle.getString("No"));
            if(check.isSelected()){
                MainGUI.showCantInterruptMessage=false;
                MainGUI.gravarConfigs();
            }
            if(diagResp != 1) //posicao do Yes em options[]
                return;
        }
        
        // TODO add your handling code here:
        TreeSet<String> novosresumos = new TreeSet<String>();
        for(int i=0;i<Documentos_considerados.getModel().getSize();i++){
            novosresumos.add((String) Documentos_considerados.getModel().getElementAt(i));
        }
        //RUser.anotadores = novosAnotadores;
        
        if(novosresumos.size()<1){ //não faz sentido fazer análise sem documentos
            JOptionPane.showMessageDialog(null,
                    bundle.getString("DocumentQuantityError")+"\n"+bundle.getString("ChangesNotApplied"),
                    bundle.getString("Warning"),
                    JOptionPane.WARNING_MESSAGE);
        } else {
            RUser.getInstance().resumos = novosresumos;
        
            this.applyChanges(RUser.getInstance().diretorio);

            //se as mudanãs foram aplicadas, esquece a sequência de alterações nas listas
            lastChange = head = null;
            canUndo=false;
            canRedo=false;
            this.updateEditMenuAvailability();

            JOptionPane.showMessageDialog(null, bundle.getString("ChangesApplied"), 
                    bundle.getString("InformationTitle"),JOptionPane.INFORMATION_MESSAGE);

            Abas.setSelectedIndex(3);
        }
        
    }//GEN-LAST:event_Botao_AplicarDocsActionPerformed

    private void Botao_CancelarDocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_CancelarDocsActionPerformed
        // TODO add your handling code here:
        modeloconsDoc.clear();
        modelodescDoc.clear();
        for(int i=0;i<TodosChecksDocs.length;i++){
            if(TodosChecksDocsBackup[i]) modeloconsDoc.addElement(TodosChecksDocs[i]);
            else modelodescDoc.addElement(TodosChecksDocs[i]);

        }
        //assumi que ao cancelar todas as alterações, o usuário não queria mais o hitórico então...
        lastChange = head = null;
        canUndo=false;
        canRedo=false;
        this.updateEditMenuAvailability();
    }//GEN-LAST:event_Botao_CancelarDocsActionPerformed

    private void Botao_LimparDocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_LimparDocsActionPerformed
        // TODO add your handling code here:
        int cont = modeloconsDoc.getSize();
        //se for ocorrer alguma, adiciona a mudança no historico
        if(cont>0)
            this.addChange(Documentos_considerados,true,true);
        for(int i=0;i<cont;i++){
            // TodosChecks[i].setSelected(false);
            //  System.out.println("limpando "+i);
            String resumo = (String) modeloconsDoc.get(0);
            modeloconsDoc.remove(0);
            modelodescDoc.addElement(resumo);
            //Botao_removeActionPerformed(evt);

        }
    }//GEN-LAST:event_Botao_LimparDocsActionPerformed

    private void Botao_TodosDocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_TodosDocsActionPerformed
        // TODO add your handling code here:
        int cont = modelodescDoc.getSize();
        //se for ocorrer alguma, adiciona a mudança no historico
        if(cont>0)
            this.addChange(Documentos_Desconsiderados,false,true);
        for(int i=0;i<cont;i++){
            // TodosChecks[i].setSelected(false);
            //  System.out.println("limpando "+i);
            String resumo = (String) modelodescDoc.get(0);
            modelodescDoc.remove(0);
            modeloconsDoc.addElement(resumo);
            //Botao_removeActionPerformed(evt);

        }
    }//GEN-LAST:event_Botao_TodosDocsActionPerformed

    private void ChooseCorpusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseCorpusActionPerformed
        String[] args = new String[2];
        args[0] = local.getLanguage();
        args[1] = this.getClass().getCanonicalName();
        SelectionWindowGUI.main(args);
        //RUser.getInstance().anotadores.clear();
        //RUser.getInstance().primeiraVez = true;
        this.dispose();
    }//GEN-LAST:event_ChooseCorpusActionPerformed

    private void CloseApplicationMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseApplicationMenuActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_CloseApplicationMenuActionPerformed

    private void redoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoMenuItemActionPerformed
        this.redoChange();
    }//GEN-LAST:event_redoMenuItemActionPerformed

    private void undoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMenuItemActionPerformed
        this.undoChange();
    }//GEN-LAST:event_undoMenuItemActionPerformed

    private void Aba_anotadoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Aba_anotadoresMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_Aba_anotadoresMouseClicked

    private void Aba_anotadoresFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Aba_anotadoresFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_Aba_anotadoresFocusGained

    private void Botao_TodosAnotadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_TodosAnotadoresActionPerformed
        // TODO add your handling code here:
        int cont = modelodesc.getSize();
        //se for ocorrer alguma, adiciona a mudança no historico
        if(cont>0)
        this.addChange(Anotadores_desconsiderados,false,true);

        for(int i=0;i<cont;i++){
            // TodosChecks[i].setSelected(false);
            // System.out.println("limpando "+i);
            String anotador = (String) modelodesc.get(0);
            modelodesc.remove(0);
            modelocons.addElement(anotador);
            //Botao_removeActionPerformed(evt);

        }
    }//GEN-LAST:event_Botao_TodosAnotadoresActionPerformed

    private void Botao_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_removeActionPerformed
        // TODO add your handling code here:
        if(Anotadores_considerados.getSelectedValue() != null){
            //adiciona as mudanças no historico
            this.addChange(Anotadores_considerados,true,false);

            List<String> selecionados = (List<String>) Anotadores_considerados.getSelectedValuesList();

            for (String el : selecionados) {
                modelodesc.addElement(el);
                modelocons.removeElement(el);
            }
        }

    }//GEN-LAST:event_Botao_removeActionPerformed

    private void Botao_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_addActionPerformed
        // TODO add your handling code here:
        if(Anotadores_desconsiderados.getSelectedValue() != null){
            //adiciona as mudanças no historico
            this.addChange(Anotadores_desconsiderados,false,false);

            List<String> selecionados = (List<String>) Anotadores_desconsiderados.getSelectedValuesList();

            for(String el : selecionados){
                modelodesc.removeElement(el);
                modelocons.addElement(el);
            }
        }
    }//GEN-LAST:event_Botao_addActionPerformed

    private void Botao_CancelarAnotadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_CancelarAnotadoresActionPerformed
        // TODO add your handling code here:
        modelocons.clear();

        modelodesc.clear();
        for(int i=0;i<TodosChecks.length;i++){
            if(TodosChecksBackup[i]) modelocons.addElement(TodosChecks[i]);
            else modelodesc.addElement(TodosChecks[i]);

        }

        //assumi que ao cancelar todas as alterações, o usuário não queria mais o hitórico então...
        lastChange = head = null;
        canUndo=false;
        canRedo=false;
        this.updateEditMenuAvailability();
    }//GEN-LAST:event_Botao_CancelarAnotadoresActionPerformed

    private void Botao_LimparAnotadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_LimparAnotadoresActionPerformed
        // TODO add your handling code here:
        int cont = modelocons.getSize();
        //se for ocorrer alguma, adiciona a mudança no historico
        if(cont>0)
        this.addChange(Anotadores_considerados,true,true);
        for(int i=0;i<cont;i++){
            // TodosChecks[i].setSelected(false);
            //  System.out.println("limpando "+i);
            String anotador = (String) modelocons.get(0);
            modelocons.remove(0);
            modelodesc.addElement(anotador);
            //Botao_removeActionPerformed(evt);

        }

    }//GEN-LAST:event_Botao_LimparAnotadoresActionPerformed

    private void Botao_AplicarAnotadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Botao_AplicarAnotadoresActionPerformed
        JCheckBox check = new JCheckBox(bundle.getString("DontShowAgain"));
        if(MainGUI.showCantInterruptMessage){
            int diagResp = JOptionPane.showOptionDialog(null,
                bundle.getString("QuestionInterruptProcess"),
                bundle.getString("Attention"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new Object[]{check,bundle.getString("Yes"),bundle.getString("No")},
                bundle.getString("No"));
            if(check.isSelected()){
                MainGUI.showCantInterruptMessage=false;
                MainGUI.gravarConfigs();
            }
            if(diagResp != 1) //posicao do Yes em options[]
            return;
        }

        if(aplicarMudancasAnotadores()){
            //se as mudanças foram aplicadas, esquece a sequência de mudanças
            lastChange = head = null;
            canUndo=false;
            canRedo=false;
            this.updateEditMenuAvailability();

            JOptionPane.showMessageDialog(null, bundle.getString("ChangesApplied"),
                bundle.getString("InformationTitle"),JOptionPane.INFORMATION_MESSAGE);

            Abas.setSelectedIndex(2);
        }
    }//GEN-LAST:event_Botao_AplicarAnotadoresActionPerformed

    private void menuItemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSobreActionPerformed
        JDialog sobre = new DialogoSobre(this.getRootPane(),bundle);
        sobre.setVisible(true);
    }//GEN-LAST:event_menuItemSobreActionPerformed

    private void menuItemManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemManualActionPerformed
        String filename = bundle.getString("ManualFileName");
        File manual = new File("./"+filename);
        
        if(!manual.exists()){
            try {
                InputStream is = getClass().getResourceAsStream(bundle.getString("projectManualSrc"));
                manual.createNewFile();
                Files.copy(is, manual.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(FerramentaGUI.class.getName()).log(Level.SEVERE, null, ex);
                if(manual.exists()) manual.delete();
                System.out.println("Não consegui criar o arquivo.");
            }
        }
        
        //Desktop.getDesktop().open(tempOutput.toFile());
        if(manual.exists()){
            try {
                Desktop.getDesktop().open(manual);
            } catch (IOException ex) {
                Logger.getLogger(FerramentaGUI.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        } else {
            //manual nao encontrado
            System.out.println("Manual não encontrado");
        }
    }//GEN-LAST:event_menuItemManualActionPerformed

    private void enableConfirmDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableConfirmDialogActionPerformed
        setConfirmDialogOptions(true);
        JOptionPane.showMessageDialog(null, bundle.getString("ConfirmDialogEnabled"),
                bundle.getString("InformationTitle"),JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_enableConfirmDialogActionPerformed

    private void disableConfirmDialogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disableConfirmDialogsActionPerformed
        setConfirmDialogOptions(false);
        JOptionPane.showMessageDialog(null, bundle.getString("ConfirmDialogDisabled"),
                bundle.getString("InformationTitle"),JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_disableConfirmDialogsActionPerformed

    private void setConfirmDialogOptions(boolean option){
        MainGUI.showCantInterruptMessage = option;
        MainGUI.showDidntApplyChangesMsg = option;
        MainGUI.showForgetCorpusMsg = option;
        MainGUI.showIgnoreChangesMsg = option;
        MainGUI.showLongProcessMsg = option;
        
        MainGUI.gravarConfigs();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FerramentaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FerramentaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FerramentaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FerramentaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FerramentaGUI().setVisible(true);
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Aba_anotadores;
    private javax.swing.JPanel Aba_coef;
    private javax.swing.JPanel Aba_descricao;
    private javax.swing.JPanel Aba_docs;
    private javax.swing.JPanel Aba_relatorio;
    private javax.swing.JTabbedPane Abas;
    private javax.swing.JList Anotadores_considerados;
    private javax.swing.JList Anotadores_desconsiderados;
    private javax.swing.JButton Botao_AplicarAnotadores;
    private javax.swing.JButton Botao_AplicarDocs;
    private javax.swing.JButton Botao_CalculaCoeficiente;
    private javax.swing.JButton Botao_CancelarAnotadores;
    private javax.swing.JButton Botao_CancelarDocs;
    private javax.swing.JButton Botao_LimparAnotadores;
    private javax.swing.JButton Botao_LimparDocs;
    private javax.swing.JButton Botao_TodosAnotadores;
    private javax.swing.JButton Botao_TodosDocs;
    private javax.swing.JButton Botao_add;
    private javax.swing.JButton Botao_addDoc;
    private javax.swing.JButton Botao_remove;
    private javax.swing.JButton Botao_removerDoc;
    private javax.swing.JCheckBox Check_Alfa;
    private javax.swing.JCheckBox Check_Alfa_Pares;
    private javax.swing.JCheckBox Check_KappaCohen;
    private javax.swing.JCheckBox Check_KappaCohen_Pares;
    private javax.swing.JCheckBox Check_KappaFleiss;
    private javax.swing.JCheckBox Check_KappaFleiss_Pares;
    private javax.swing.JCheckBox Check_Kendalls;
    private javax.swing.JCheckBox Check_Kendalls_Pares;
    private javax.swing.JCheckBox Check_Pares;
    private javax.swing.JCheckBox Check_Percentage;
    private javax.swing.JCheckBox Check_Percentage_Pares;
    private javax.swing.JCheckBox Check_Robinsons;
    private javax.swing.JCheckBox Check_Robinsons_Pares;
    private javax.swing.JMenuItem ChooseCorpus;
    private javax.swing.JMenuItem CloseApplicationMenu;
    private javax.swing.JList Documentos_Desconsiderados;
    private javax.swing.JList Documentos_considerados;
    private javax.swing.JMenuItem Language1;
    private javax.swing.JMenuItem Language2;
    private javax.swing.JMenu LanguageMenu;
    private javax.swing.JMenu OptionMenu;
    private javax.swing.JMenuItem RPath;
    private javax.swing.JLabel Text_anotadores;
    private javax.swing.JLabel Text_caminho;
    private javax.swing.JLabel Text_documentos;
    private javax.swing.JLabel Text_scheme;
    private javax.swing.JLabel Text_sourcecorpus;
    private javax.swing.JLabel Text_units;
    private javax.swing.JCheckBox checkHTML;
    private javax.swing.JCheckBox checkMaioria;
    private javax.swing.JCheckBox checkModa;
    private javax.swing.JCheckBox checkPDF;
    private javax.swing.JCheckBox checkXML;
    private javax.swing.JMenuItem disableConfirmDialogs;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem enableConfirmDialog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenuItem menuItemManual;
    private javax.swing.JMenuItem menuItemSobre;
    private javax.swing.JMenuItem redoMenuItem;
    private javax.swing.JMenuItem undoMenuItem;
    // End of variables declaration//GEN-END:variables

    private boolean canCreateReport() {
        //verifica se o estado dos objetos (anotadores e documentos) é válido para iniciar os cálculos
        boolean resp=true;
        if(RUser.anotadores.size()<=1){
            JOptionPane.showMessageDialog(null, bundle.getString("AnnotatorQuantityError"),bundle.getString("CreateReportErrorTitle"), JOptionPane.ERROR_MESSAGE);
            resp=false;
        }
        //resumos precisam ter pelo menos 1 de tamanho pra não dar fatal error ao rodar o script R
        if(RUser.resumos.size()<1){
            JOptionPane.showMessageDialog(null, bundle.getString("DocumentQuantityError"),bundle.getString("CreateReportErrorTitle"), JOptionPane.ERROR_MESSAGE);
            resp=false;
        }
        
        return resp;
    }

    private void disableReports() {
        //desabilita os relatórios que dependem da seleção de um coeficiente de concordância
        checkHTML.setEnabled(false);
        checkXML.setEnabled(false);
        checkPDF.setEnabled(false);
        
        //o programa usa esse inteiro para habilitar e desabilitar o botao de gerar relatórios,
        //por isso temos que verificar se estavam marcados antes de desmarcar 
        if(checkHTML.isSelected()) relatorios--;
        if(checkXML.isSelected()) relatorios--;
        if(checkPDF.isSelected()) relatorios--;
        
        //desmarca todos eles pra grantir
        checkHTML.setSelected(false);
        checkXML.setSelected(false);
        checkPDF.setSelected(false);
        
        //desabilita o botão de cálculo caso os relatorios de maioria e moda não estejam marcados
        if(!checkMaioria.isSelected() && !checkModa.isSelected()){
            this.Botao_CalculaCoeficiente.setEnabled(false);
        }
    }
    
    private void enableReports(){
        //habilita os relatórios que dependem da seleção de um coeficiente de concordância
        checkHTML.setEnabled(true);
        checkXML.setEnabled(true);
        checkPDF.setEnabled(true);
    }

    private void updateEditMenuAvailability() {
        this.undoMenuItem.setEnabled(canUndo);
        this.redoMenuItem.setEnabled(canRedo);
        
        pack();
        this.revalidate();
    }
    
    private Change lastChange, head;
    int changeCount, undoCount;

    private void addChange(JList source, boolean sourceIsConsideredList, boolean moveAll) {
        
        Change c = new Change(source, sourceIsConsideredList, moveAll, lastChange);
        
        if(head==null){
            head = new Change(true);
            head.setNext(c);
            c.setPrevious(head);
            lastChange = c;
            changeCount=1;
            undoCount=0;
        } else {
            if(lastChange!=null){
                //se a ultima mudança já tinha uma próxima, quebra a ponta que ficaria inutilizada
                if(lastChange.next!=null){
                    lastChange.next.setPrevious(null);
                }
                lastChange.next = c;
                changeCount = changeCount-undoCount;
                undoCount=0;
            }

            lastChange = c;
            changeCount++;
            
            if(changeCount>10){ 
                //faz um left shift nas mudanças
                //head c1 c2 c3 c4 c5 c6 c7 c8 c9 c10 c11
                //head c2 c3 c4 c5 c6 c7 c8 c9 c10 c11
                Change aux = head.next;
                aux.previous = null; // c1 previous = null
                head.next = aux.next; // head next = c2
                aux.next = null; //c1 next = null
                head.next.previous = head; // c2 previous = head
                changeCount--;
            }
        }
        this.canUndo = true;
        this.canRedo = false;
        
        //System.out.println(changeCount + " <-c undo-> "+undoCount);
        this.updateEditMenuAvailability();
    }
    
    private void undoChange(){
        if(lastChange!=head){
            //assume que este metodo nem será exeutado se o usuario nao estiver nas abas 1 e 2
            //anotadores e documentos respectivamente
            boolean isCurrentTabAnnotators = (Abas.getSelectedIndex()==1);
            
            DefaultListModel changeSrc, changeDst;
            
            if(isCurrentTabAnnotators){
                if(lastChange.wasFromConsideredList()){
                    changeSrc = modelocons;
                    changeDst = modelodesc;
                } else {
                    changeSrc = modelodesc;
                    changeDst = modelocons;
                }
            } else {
                if(lastChange.wasFromConsideredList()){
                    changeSrc = modeloconsDoc;
                    changeDst = modelodescDoc;
                } else {
                    changeSrc = modelodescDoc;
                    changeDst = modeloconsDoc;
                }
            }
            
            int [] originalPositions = lastChange.getOriginalPositions();
            String [] names = lastChange.getNames();

            for(int i=0; i<originalPositions.length; i++){
                changeSrc.add(originalPositions[i], names[i]);
                changeDst.removeElement(names[i]);
            }
            
            lastChange = lastChange.getPrevious();
            
            canUndo = (lastChange!=head);
            canRedo = true;
            undoCount++;
            //System.out.println(changeCount + " <-c undo-> "+undoCount);
            this.updateEditMenuAvailability();
        }
    }
    
    private void redoChange(){
        if(lastChange.getNext()!=null){
            lastChange = lastChange.getNext();
            
            //assume que este metodo nem será exeutado se o usuario nao estiver nas abas 1 e 2
            //anotadores e documentos respectivamente
            boolean isCurrentTabAnnotators = (Abas.getSelectedIndex()==1);
            
            DefaultListModel changeSrc, changeDst;
            
            if(isCurrentTabAnnotators){
                if(lastChange.wasFromConsideredList()){
                    changeSrc = modelocons;
                    changeDst = modelodesc;
                } else {
                    changeSrc = modelodesc;
                    changeDst = modelocons;
                }
            } else {
                if(lastChange.wasFromConsideredList()){
                    changeSrc = modeloconsDoc;
                    changeDst = modelodescDoc;
                } else {
                    changeSrc = modelodescDoc;
                    changeDst = modeloconsDoc;
                }
            }
            
            String [] names = lastChange.getNames();

            for (String name : names) {
                changeDst.addElement(name);
                changeSrc.removeElement(name);
            }
            
            undoCount--;
            
            canUndo = true;
            canRedo = lastChange.getNext()!=null;
            //System.out.println(changeCount + " <-c undo-> "+undoCount);
            this.updateEditMenuAvailability();
        }
    }

    private boolean applyChanges(String diretorio) {
        AgreeCalcWorker worker = new ApplyChanges(bundle, diretorio);
        DialogoProgresso d = new DialogoProgresso(this.getRootPane(),worker,bundle);
        d.setVisible(true);
        return (Boolean) d.getResponse();
    }

    //private void adjustSize() {
    //    setSize(new java.awt.Dimension(672, 474));
    //}
    
    private class Change {
        private String[] names; //nome dos elementos que foram alterados
        private int[] originalPositions; //posicoes originais na lista
        
        private final boolean wasConsidered; //indica de qual lista faziam parte
        private boolean isHead; //precisamos de uma cabeça vazia pra fazer redo quando voltarmos ao começo
        
        //montando uma lista ligada na mão - a alteracao anterior nunca muda
        private Change previous; 
        private Change next;
        
        public Change(boolean isHead){
            this.isHead=isHead;
            wasConsidered = false;
        }
        
        //antes de criar a alteracao, checar se a ação foi válida (se tinham indices para mover)
        public Change(JList source, boolean sourceIsConsideredList, boolean moveAllAction, Change previous){
            this.wasConsidered = sourceIsConsideredList;
            
            this.previous = previous;
            this.next = null;
            
            if(moveAllAction){
                source.setSelectionInterval(0, source.getModel().getSize()-1);
            }
            
            //cópia profunda para não criar problemas
            int[] selectedIndex = Arrays.copyOf(source.getSelectedIndices(), source.getSelectedIndices().length);
            
            List<String> selectedValues = source.getSelectedValuesList();
            
            if(selectedIndex.length > 0){
                names = new String[selectedIndex.length];
                originalPositions = new int[selectedIndex.length];
                
                Iterator it = selectedValues.iterator();

                for(int i=0; i<selectedIndex.length; i++){
                    if(it.hasNext()){
                        names[i] =(String) it.next();
                        originalPositions[i]= selectedIndex[i];
                    } 
                }
            }
        }
        
        public Change getNext(){
            return next;
        }
        
        public Change getPrevious(){
            return previous;
        }
        
        public void setNext(Change a){
            next = a;
        }
        
        public void setPrevious(Change a){ //usado para desconectar a lista
            previous = a;
        }
                
        public boolean wasFromConsideredList(){
            return wasConsidered;
        }
        
        public int[] getOriginalPositions(){
            return originalPositions;
        }
        
        public String[] getNames(){
            return names;
        }
    
        public void setHead(boolean isHead){
            this.isHead = isHead;
        }
        
        public boolean isHead(){
            return isHead;
        }
    }
    
}
