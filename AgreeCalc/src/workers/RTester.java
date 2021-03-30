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

import ferramentaic.RUser;
import graphicInterface.MainGUI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author matheus
 */
public class RTester extends AgreeCalcWorker{

    private File f;
    private boolean resp;
    
    public RTester(ResourceBundle bundle, File selectedFile) {
        super(bundle);
        this.f = selectedFile;
        resp = false;
    }

    @Override
    public Object getResponse() {
        return resp;
    }

    @Override
    protected Object doInBackground() throws Exception {
        this.setStatusMessage(bundle.getString("TestingRscript"));
        File arq = f;  
        String path = arq.getAbsolutePath();
        //aqui você testa se a string recebeu o caminho do arquivo  

        FileWriter f1; 
        try {
           f1 = new FileWriter("R_sourcepath.txt");
           f1.write(path); 
           f1.close(); 
        } catch (IOException ex) {
           Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
           return false;
        }
        RUser.setRscript();
        resp = RUser.getInstance().testRScript();
        
        return resp;
    }
    
}
