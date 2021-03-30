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
package workers;

import java.util.ResourceBundle;
import javax.swing.SwingWorker;

/**
 *
 * @author matheus
 */
public abstract class AgreeCalcWorker extends SwingWorker{
    /*
    classe que representa um processamento na ferramenta que pode ser: 
    geração de relatórios, análise do corpus e alteração dos objetos para análse (atualmente - versão 1.1)
    a diferença para o SwingWorker é a adição de uma string (localizada) que representa o estado do processamento 
    que pode ser exibida num diálogo
    */
    
    protected ResourceBundle bundle;
    private String statusMessage;
    
    public AgreeCalcWorker(ResourceBundle bundle){
        super();
        this.bundle = bundle;
        statusMessage = "";
    }
    
    protected void setStatusMessage(String message){
        String old = statusMessage;
        statusMessage = message;
        firePropertyChange("statusMessage", old, statusMessage);
    }
    
    public String getStatusMessage(){
        return statusMessage;
    }
    
    public abstract Object getResponse();
    
}
