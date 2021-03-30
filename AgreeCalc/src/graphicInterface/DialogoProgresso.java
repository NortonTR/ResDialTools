/*
 * Copyright (C) 2018 matheus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import workers.AgreeCalcWorker;

/**
 *
 * @author matheus
 */
public class DialogoProgresso extends JDialog{
    //dialogo para exibir o status e o progresso de um processamento na ferramenta
    
    private AgreeCalcWorker worker; //objeto que representa o processamento que será realizado
    private JProgressBar progressBar; //barra de progresso do processamento
    private JLabel progressMessage; //label onde a mensagem de status do processamento será exibida
    private ResourceBundle bundle; //bundle para seleção das strings de mensagem segundo o idioma atual do sistema
    private Object response; //armazena a resposta do processamento
    
    public DialogoProgresso(JComponent parent, AgreeCalcWorker work, ResourceBundle bun){ 
        super(SwingUtilities.getWindowAncestor(parent), bun.getString("DialogProcessing"), DEFAULT_MODALITY_TYPE);
        this.worker=work;
        this.bundle = bun; 
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.worker.addPropertyChangeListener(new PropertyChangeListener(){ 
            //escuta as mudanças que ocorrem no processamento e forncece um feedback ao usuário
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                    switch (name) {
                        case "state": //ocorre quando o status da linha de processamento muda
                            switch (worker.getState()) {
                                case DONE: //só tratamos quando ela termina
                                    response = worker.getResponse();
                                    setVisible(false);
                                    break;
                            }
                            break;
                        case "progress": //ocorre quando o número que representa o progresso (0-100) mudou
                            if(progressBar.isIndeterminate()) progressBar.setIndeterminate(false);
                            progressBar.setValue(worker.getProgress());
                            break;
                        case "statusMessage": //ocorre quando a mensagem de status muda
                            progressMessage.setText(worker.getStatusMessage());
                            break;
                    }
            }
        });
        
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                //quando o diálogo começa, o processamento começa
                worker.execute();
            }

            @Override
            public void windowClosing(WindowEvent e) { 
                //por default (na versão 1.1), os processamentos não eram canceláveis (nem desfazíveis)
                //seguindo essa lógica, o diálogo que representa o processamento não pode ser fechado também
                JOptionPane.showMessageDialog(null, bundle.getString("CannotCloseDialog"));
            }

        });

        //posicionamento e exibição do diálogo na tela
        this.setPreferredSize(new Dimension(270,100));
        this.setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(200,100));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        LayoutManager lay = new BoxLayout(panel,BoxLayout.PAGE_AXIS);
        
        progressMessage = new JLabel("...",SwingConstants.LEFT);
        progressMessage.setLabelFor(progressBar); 
        Box  b = Box.createHorizontalBox();
        b.add( progressMessage );
        b.add( Box.createHorizontalGlue() );
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); 
        //por default o dialogo não sabe a duração do processo
        //então assume que é indeterminado, até o processo indicar algum progresso
        
        Box  box = Box.createHorizontalBox();
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(progressBar, BorderLayout.CENTER);
        box.add(p);
        box.add(Box.createHorizontalGlue());
        
        panel.setLayout(lay);
        panel.add(b);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(box);
        
        this.add(panel);
        setIconImage(new ImageIcon(getClass().getResource("myicon.png")).getImage());
        pack();
        setLocationRelativeTo(parent);
    }
    
    public Object getResponse(){
        return response;
    }
}
