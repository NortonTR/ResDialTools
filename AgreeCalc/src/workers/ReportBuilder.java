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
import ferramentaic.Report;
import graphicInterface.FerramentaGUI;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author matheus
 */
public class ReportBuilder extends AgreeCalcWorker{
    //contém a função que, anteriormente, estava no Botao_CalculaCoeficienteActionPerformed em ferramentaGUI
    //calcula os coeficientes e gera os relatórios requisitados
    
    private boolean response;
    
    private final boolean alfa, kappaFleiss, kappaCohen, percentage, robinsons, kendalls, pares,
            paresKappaFleiss, paresAlfa, paresKappaCohen, paresPercentage, 
            paresRobinsons, paresKendalls, reportXML, reportHTML, reportPDF,
            maioriaXML, modaXML;
    private Locale local;
    
    public ReportBuilder(ResourceBundle bundle, Locale local, boolean alfa,
           boolean kappaFleiss, boolean kappaCohen, boolean percentage,
           boolean robinsons, boolean kendalls, boolean pares,
           boolean paresKappaFleiss, boolean paresAlfa, boolean paresKappaCohen,
           boolean paresPercentage, boolean paresRobinsons, boolean paresKendalls,
           boolean reportXML, boolean reportHTML, boolean reportPDF,
           boolean maioriaXML, boolean modaXML) {
        super(bundle);
        this.local = local;
        this.alfa = alfa;
        this.kappaFleiss = kappaFleiss;
        this.kappaCohen = kappaCohen;
        this.percentage = percentage;
        this.robinsons = robinsons;
        this.kendalls = kendalls;
        this.pares = pares;
        this.paresKappaFleiss = paresKappaFleiss;
        this.paresAlfa = paresAlfa;
        this.paresKappaCohen = paresKappaCohen;
        this.paresPercentage = paresPercentage;
        this.paresRobinsons = paresRobinsons;
        this.paresKendalls = paresKendalls;
        this.reportXML = reportXML;
        this.reportHTML = reportHTML;
        this.reportPDF = reportPDF;
        this.maioriaXML = maioriaXML;
        this.modaXML = modaXML;
        
    }

    @Override
    public Object getResponse() {
       return response;
    }

    @Override
    protected Object doInBackground() throws Exception {
        LinkedList<String> resultadosAlpha = new LinkedList<String>();
        LinkedList<String> resultadosFleiss = new LinkedList<String>();
        LinkedList<String> resultadosCohen = new LinkedList<String>();
        LinkedList<String> resultadosPercentage = new LinkedList<String>();
        LinkedList<String> resultadosRobinsons = new LinkedList<String>();
        LinkedList<String> resultadosKendalls = new LinkedList<String>();
        
        
        if(alfa){
            this.setStatusMessage(bundle.getString("CalcKrippendorffAlpha"));
            for(int k = 0; k<RUser.categorias.size();k++){
                //System.out.println("string: "+RUser.matrizesAlpha[k]);
               String resultado = (RUser.getInstance().alpha(RUser.matrizesAlpha[k], RUser.anotadores.size()));
               if(!resultado.equals("-1")) resultadosAlpha.add(resultado);
               else JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorKrippendorff") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
            }
        }
        if(kappaFleiss){
            this.setStatusMessage(bundle.getString("CalcKappaFleiss"));
           for(int k = 0; k<RUser.categorias.size();k++){
                //System.out.println("string: "+RUser.matrizesAlpha[k]);
                String resultado = RUser.getInstance().kappaFleiss(RUser.matrizesAlpha[k], RUser.qtdUnits.size());
                if(!resultado.equals("-1")) resultadosFleiss.add(resultado);
                else JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorKappaFleiss") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
            }
            
        }
        if(kappaCohen){
            this.setStatusMessage(bundle.getString("CalcKappaCohen"));
            for(int k = 0; k<RUser.categorias.size();k++){
                
                String resultado = (RUser.getInstance().kappaCohen(RUser.matrizesAlpha[k], RUser.qtdUnits.size()));
                if(!resultado.equals("-1")) resultadosCohen.add(resultado);
                else JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorKappaCohen") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
            }
            
        }
        if(percentage){
            this.setStatusMessage(bundle.getString("CalcPercentageAgreement"));
            for(int k = 0; k<RUser.categorias.size();k++){
                
                String resultado = (RUser.getInstance().agreement(RUser.matrizesAlpha[k]));
                if(!resultado.equals("-1")) resultadosPercentage.add(resultado);
                else JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorPercentage") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
        if(robinsons){
            this.setStatusMessage(bundle.getString("CalcRobinsonsA"));
            for(int k = 0; k<RUser.categorias.size();k++){    
                String resultado = (RUser.getInstance().robinson(RUser.matrizesAlpha[k]));
                if(!resultado.equals("-1")) resultadosRobinsons.add(resultado);
                else JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorRobinson") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
            }   
        }
        
        if(kendalls){
            this.setStatusMessage(bundle.getString("CalcKendallsW"));
            for(int k = 0; k<RUser.categorias.size();k++){    
                String resultado = (RUser.getInstance().kendall(RUser.matrizesAlpha[k]));
                if(!resultado.equals("-1")) resultadosKendalls.add(resultado);
                else JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorKendall") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
            }   
        }
        //boolean paresKappaFleiss, boolean paresAlfa, boolean paresKappaCohen, boolean paresPercentage, boolean paresRobinsons, boolean paresKendalls 
        
        //VOLTAR AQUI
        if(pares){
            this.setStatusMessage(bundle.getString("CalcPairwise"));
            for(int categoria = 0; categoria<RUser.categorias.size();categoria++){
                //System.out.println(1);
                RUser.getInstance().fazPares(categoria,  paresKappaFleiss, paresAlfa, 
                        paresKappaCohen, paresPercentage,
                        paresRobinsons, paresKendalls);
            }
        }
        
        //
        
        try {
            //seleciona os relatorios;
            this.setStatusMessage(bundle.getString("GeneratingReports"));
            Report.internacionaliza(local.getLanguage());
            if(reportXML){
                Report.exportaXML(resultadosAlpha, resultadosFleiss, resultadosCohen, resultadosPercentage, 
                    resultadosRobinsons, resultadosKendalls,
                    paresAlfa,
                    paresKappaFleiss, paresKappaCohen, paresPercentage,
                    paresRobinsons, paresKendalls);
            }
            if(reportHTML){
                Report.exportaHTML(resultadosAlpha, resultadosFleiss, resultadosCohen, resultadosPercentage, 
                        resultadosRobinsons, resultadosKendalls, paresAlfa,
                    paresKappaFleiss, paresKappaCohen, paresPercentage,
                    paresRobinsons, paresKendalls);
            }
            if(reportPDF){
                    Report.exportaPDF(resultadosAlpha, resultadosFleiss, resultadosCohen, resultadosPercentage,
                    resultadosRobinsons, resultadosKendalls, paresAlfa,
                    paresKappaFleiss, paresKappaCohen, paresPercentage,
                    paresRobinsons, paresKendalls);
            }
            if(maioriaXML){
                RUser.getInstance().fazReport("maioria");
            }
            if(modaXML){
                RUser.getInstance().fazReport("moda");
            }
            response = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(FerramentaGUI.class.getName()).log(Level.SEVERE, null, ex);
            response=false;
        }
        return response;
    }
    
}
