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



package ferramentaic;
import com.itextpdf.text.*;
import graphicInterface.FerramentaGUI;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;


import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.Document;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static ferramentaic.RUser.bundle;
import static ferramentaic.RUser.local;


public class Report {
	static String paraString(TreeSet<String> dados){
		Iterator<String> it = dados.iterator();
		String resp = "";
		while(it.hasNext()) resp += it.next() + ";";
		resp = resp.substring(0, resp.length()-1);
		return resp;
	}
        
        private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD); 
        private static Font tFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL); 
        private static Font tFont2 = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL); 
	private static Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED); 
	private static Font subFont = new Font(Font.FontFamily.HELVETICA, 16,	Font.BOLD); 
	private static Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,Font.BOLD); 
        
        public static Locale local = new Locale("en");
        public static ResourceBundle bundle;
        
        public static void internacionaliza(String loca1){
            if(loca1.equals("en")) local = new Locale(loca1, "US");
            else local = new Locale(loca1, "BR"); 
            bundle = ResourceBundle.getBundle("i18n.MyBundle", local);
        }
        
        public static boolean exportaHTML(LinkedList<String> alphas, LinkedList<String> fleisss, LinkedList<String> cohens,
                LinkedList<String> percentages, LinkedList<String> robinsons, LinkedList<String> kendalls,
                 boolean paresAlpha, boolean paresFleiss, boolean paresCohen, boolean paresPercentage, boolean paresRobinson, boolean paresKendall) 
                throws IOException{
//  for(int k=0; k<RUser.categorias.size();k++)     teste = new Paragraph(RUser.categorias.get(k)+"\n", subFont);
             Calendar ca = GregorianCalendar.getInstance();
                ca.setTime(new java.util.Date());
		java.util.Date data = ca.getTime();
            int numPares = calcNumPares(RUser.anotadores.size());
            
               String x = ca.getTime().toString();
               x = x.replaceAll(":", "-");
            JFileChooser open = new JFileChooser();
            //open.setFileSelectionMode(1);
            open.setApproveButtonText(bundle.getString("Select"));
            open.setDialogTitle(bundle.getString("ReportPath"));
            open.setSelectedFile(new File("AgreeCalc "+bundle.getString("Report")+" - HTML - "+x));
            
            int op = open.showOpenDialog(FerramentaGUI.getInstance());  
            File arq = null;
            if(op == JFileChooser.APPROVE_OPTION){  
               arq = open.getSelectedFile();  
            
            if(arq!=null){
                String caminho = "";
                caminho = arq.getAbsolutePath();//+"//";
                Report fake = new Report();
                fake.copiarImagemLogo(arq);
                
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(new File(caminho+".html")));// + "Log de Resultados - data - hora.html")));

                    int qtd = 0;
                    if(!alphas.isEmpty()) qtd++;
                    if(!fleisss.isEmpty()) qtd++;
                    if(!cohens.isEmpty()) qtd++;
                    if(!percentages.isEmpty()) qtd++;

                    bw.write("<?xml version='1.0' encoding='utf-8'?>\n" +
    "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.1//EN' 'http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd'>\n" +
    "<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='cs' lang='cs'>\n" +
    "<head><meta http-equiv='content-type' content='text/html; charset=utf-8' /><title>AgreeCalc: Relatório de resultados - "+x+"</title>\n" +
    "<style type='text/css'> "
                            + ".textinho {position:absolute; left:10%; top:450px; width:80%; font-family:Verdana, Geneva, sans-serif; font-size:20px; background-color:#FFFFFF; border-radius:5px; height:4000px;} \n" +
    "p {font-family:Verdana, Geneva, sans-serif; font-size: 28px;} h1{font-family:Verdana, Geneva, sans-serif; font-size: 24px;font-weight:normal;} \n" +
    ".titulo{position:absolute; left:10%; top:150px; width:80%; font-family:Verdana, Geneva, sans-serif; font-size:32px; text-align:center;font-weight:bold;\n" +
    "background-color:#FFFFFF;border-radius:5px; height:280px;}\n" +
    " b{color: #0C6;}\n" +
    " body{background-color: #0C6;}\n" +
    " #fundo{\n" +
    "	 width:80%;\n" +
    "	 left:10%;\n" +
    "	 position:absolute;\n" +
    "	 height:4600px;\n" +
    "	 background-color:#0C6;\n" +
    "	 border-radius:5px;\n" +
    "	 top:0px;\n" +
    "	 height:4000px;\n" +
    " }\n" +
    " #cabecalho{\n" +
    "	 position:absolute;\n" +
    "	 width:80%;\n" +
    "	 left:10%;\n" +
    "	 height:150px;\n" +
    "	 display:table-cell; vertical-align:middle; text-align:center\n" +
    " }\n" +
    " .esquerda{\n" +
    "	 position:absolute;\n" +
    "	 left:0;\n" +
    "	 top:120px;\n" +
    "	 width:100%;\n" +
    "	 background-color:#FFFFFF;\n" +
    " }\n" +
    "  .lalala{\n" +
    "	 position:absolute;\n" +
    "	 right:0;\n" +
    "	 top:120px;\n" +
    "	 width:50%;\n" +
    "\n" +
    " }\n" +
    "	 \n" +
    "table {  border-collapse: collapse;   border: none;   font: normal 16px helvetica, verdana, arial, sans-serif;   background-repeat: repeat;\n" +
    "border-spacing: 2px; align:center; }\n" +
    "td, th {   border: none;  padding: .8em;    color: #5E5E5E; text-align:center; } \n" +
    "tbody td a {   background: transparent;  text-decoration: none;  color: #FFFFFF;  }\n" +
    "tbody td a:hover {  background: transparent;  color: #FFFFFF;  } \n" +
    "tbody th a {  font: bold 11px helvetica, verdana, arial, sans-serif;  background: transparent; text-decoration: none;  font-weight:normal;  color: #FFFFFF;  }\n" +
    "tbody td+td+td+td a {    padding-right: 14px;}    \n" +
    "tbody td+td+td+td a:hover {        padding-right: 14px;  }\n" +
    "tbody th a:hover {  background: transparent;  color: #FFFFFF;  }tbody th, tbody td {  vertical-align: top;  text-align: center;  }\n" +
    "tbody tr:hover {  background: #ecfcbf; color:#FFFFFF; }tbody tr:hover th,tbody tr.odd:hover th {  background: #ecfcbf; color:#FFFFFF; }\n" +
    "</style>\n" +
    "</head><body>\n" +
    "<div id=\"fundo\">\n" +
    "	<div id=\"cabecalho\"><img src=\"img/logo_extenso.png\" align=\"middle\" /></div>\n" +
    "	<div class='titulo'>\n" +
    "    <table align=\"center\" style=\"top:30px\">\n" +
    "    <tr ><td colspan=\"2\" align=\"center\" style=\"font-size:18px;\"><b>"+ bundle.getString("CorpusDescription") + "</b></td></tr>\n" +
    "    <tr align=\"left\"><td>"+ bundle.getString("Scheme")+"</td><td>"+RUser.scheme+"</td></tr>\n" +
    "    <tr align=\"left\"><td>"+ bundle.getString("SourceCorpus") + "</td><td>"+RUser.source_corpus+"</td></tr>\n" +
    "	<tr align=\"left\"><td>"+ bundle.getString("AnalyzedUnits") +"</td><td>"+RUser.qtdUnits.size()+"</td></tr>\n" +
    "    <tr align=\"left\"><td>"+  bundle.getString("Annotators")+"</td><td>"+RUser.anotadores.size()+"</td></tr>\n" +
    "    <tr align=\"left\"><td >" +  bundle.getString("AnalyzedDocuments")+"</td><td>"+RUser.resumos.size()+"</td></tr>\n" +
    "	</table>\n" +
    "    </div>\n" +
    "	<div class='textinho'></br></br>\n" +
    "    <p align=\"center\" style=\"font-size:18px\"><b>"+ bundle.getString("AgreementCoefficients") +"</b></p>\n" +
    "</br></br>\n" +
    "<div class=\"esquerda\">");

                    if(qtd>2){
                        //pode ser os dois primeiros na esquerda e o resto na esquerda
                        if(!alphas.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("Alpha") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+alphas.get(k)+"</td></tr>\n";

                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);

                        }
                        if(paresAlpha){
                             if(alphas.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("Alpha") +"</p>\n" +
    "<table align=\"center\">\n");

                             for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalAlfa[k]/numPares)+"</td></tr>\n" +
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresAlfa[k][0]+";"+RUser.maiorParAnotadoresAlfa[k][1]+";"+RUser.maiorParAlfa[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresAlfa[k][0]+";"+RUser.menorParAnotadoresAlfa[k][1]+";"+RUser.menorParAlfa[k]+"</td></tr>\n");

                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresAlfa.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresAlfa.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresAlfa.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>");
                             }
                        }

                        if(!fleisss.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KappaFleiss") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+fleisss.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);

                        }

                        if(paresFleiss){
                            if(fleisss.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KappaFleiss") +"</p>\n" +
    "<table align=\"center\">\n");

                            for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalKappa[k]/numPares)+"</td></tr>\n" +                                     
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresKappa[k][0]+";"+RUser.maiorParAnotadoresKappa[k][1]+";"+RUser.maiorParKappa[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresKappa[k][0]+";"+RUser.menorParAnotadoresKappa[k][1]+";"+RUser.menorParKappa[k]+"</td></tr>\n");

                                 TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresKappa.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKappa.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKappa.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>");
                            }

                        }



                        //bw.write("</div>\n" +"<div class=\"esquerda\">");

                         if(!cohens.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KappaCohen") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+cohens.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);


                        }

                        if(paresCohen){
                             if(cohens.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KappaCohen") +"</p>\n" +
    "<table align=\"center\">\n");

                            for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalKappaCohen[k]/numPares)+"</td></tr>\n" +                                     
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresKappaCohen[k][0]+";"+RUser.maiorParAnotadoresKappaCohen[k][1]+";"+RUser.maiorParKappaCohen[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresKappaCohen[k][0]+";"+RUser.menorParAnotadoresKappaCohen[k][1]+";"+RUser.menorParKappaCohen[k]+"</td></tr>\n");

                                 TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresKappaCohen.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKappaCohen.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKappaCohen.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>");

                             }
                        } 


                        if(!percentages.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PercentageAgreement") + "</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+percentages.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);


                        }

                        if(paresPercentage){
                             if(percentages.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("Percentage Agreement") + "</p>\n" +
    "<table align=\"center\">\n");

                             for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalPercentage[k]/numPares)+"</td></tr>\n" +
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresPercentage[k][0]+";"+RUser.maiorParAnotadoresPercentage[k][1]+";"+RUser.maiorParPercentage[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresPercentage[k][0]+";"+RUser.menorParAnotadoresPercentage[k][1]+";"+RUser.menorParPercentage[k]+"</td></tr>\n");

                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresPercentage.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresPercentage.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresPercentage.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>");

                             }
                        }


                        if(!robinsons.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("RobinsonsA") + "</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+robinsons.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);


                        }
                        //pares robinsons aqui
                        if(paresRobinson){
                             if(robinsons.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("RobinsonsA") + "</p>\n" +
    "<table align=\"center\">\n");

                             for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalRobinson[k]/numPares)+"</td></tr>\n" +
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresRobinson[k][0]+";"+RUser.maiorParAnotadoresRobinson[k][1]+";"+RUser.maiorParRobinson[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresRobinson[k][0]+";"+RUser.menorParAnotadoresRobinson[k][1]+";"+RUser.menorParRobinson[k]+"</td></tr>\n");

                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresRobinson.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresRobinson.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresRobinson.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>");

                             }
                        }

                        if(!kendalls.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KendallsW") + "</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+kendalls.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);


                        }
                        //pares kendalls aqui
                        if(paresKendall){
                             if(kendalls.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KendallsW") + "</p>\n" +
    "<table align=\"center\">\n");

                             for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalKendall[k]/numPares)+"</td></tr>\n" +
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresKendall[k][0]+";"+RUser.maiorParAnotadoresKendall[k][1]+";"+RUser.maiorParKendall[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresKendall[k][0]+";"+RUser.menorParAnotadoresKendall[k][1]+";"+RUser.menorParKendall[k]+"</td></tr>\n");

                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresKendall.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKendall.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKendall.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>");

                             }
                        }


                    }else{
                        //um na esquerda e se tiver outro, na esquerda

                        if(!alphas.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("Alpha") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+alphas.get(k)+"</td></tr>\n";

                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);

                            qtd--;
                        }
                        if(paresAlpha){
                             if(alphas.isEmpty()){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("Alpha") +"</p>\n" +
    "<table align=\"center\">\n");
                                 qtd--;
                             }
                             for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalAlfa[k]/numPares)+"</td></tr>\n" +
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresAlfa[k][0]+";"+RUser.maiorParAnotadoresAlfa[k][1]+";"+RUser.maiorParAlfa[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresAlfa[k][0]+";"+RUser.menorParAnotadoresAlfa[k][1]+";"+RUser.menorParAlfa[k]+"</td></tr>\n");
                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresAlfa.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresAlfa.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresAlfa.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>");

                             }
                        }


                        //if(qtd<=1&&qtd>0)bw.write("</div>\n" +"<div class=\"esquerda\">");

                        if(!fleisss.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KappaFleiss") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+fleisss.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);
                            qtd--;
                        }

                        if(paresFleiss){
                            if(fleisss.isEmpty()){
                                bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KappaFleiss") +"</p>\n" +
    "<table align=\"center\">\n");
                                qtd--;
                            }
                            for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalKappa[k]/numPares)+"</td></tr>\n" +
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresKappa[k][0]+";"+RUser.maiorParAnotadoresKappa[k][1]+";"+RUser.maiorParKappa[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresKappa[k][0]+";"+RUser.menorParAnotadoresKappa[k][1]+";"+RUser.menorParKappa[k]+"</td></tr>\n");

                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresKappa.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKappa.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKappa.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>");
                             }

                        }



                        //if(qtd<=1&&qtd>0)bw.write("</div>\n" +"<div class=\"esquerda\">");

                         if(!cohens.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KappaCohen") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+cohens.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);

                            qtd--;
                        }

                        if(paresCohen){
                             if(cohens.isEmpty()){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KappaCohen") +"</p>\n" +
    "<table align=\"center\">\n");
                                 qtd--;
                             }
                            for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">Avalia&ccedil;&atilde;o por pares:</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalKappaCohen[k]/numPares)+"</td></tr>\n" +                                     
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresKappaCohen[k][0]+";"+RUser.maiorParAnotadoresKappaCohen[k][1]+";"+RUser.maiorParKappaCohen[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresKappaCohen[k][0]+";"+RUser.menorParAnotadoresKappaCohen[k][1]+";"+RUser.menorParKappaCohen[k]+"</td></tr>\n");
                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresKappaCohen.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKappaCohen.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKappaCohen.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>"); 

                            }

                        } 

                        //if(qtd<=1&&qtd>0)bw.write("</div>\n" +"<div class=\"esquerda\">");
                        if(!percentages.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PercentageAgreement") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+percentages.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);


                        }

                        if(paresPercentage){
                             if(percentages.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PercentageAgreement") +"</p>\n" +
    "<table align=\"center\">\n");

                             for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalPercentage[k]/numPares)+"</td></tr>\n" +                                     
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresPercentage[k][0]+";"+RUser.maiorParAnotadoresPercentage[k][1]+";"+RUser.maiorParPercentage[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresPercentage[k][0]+";"+RUser.menorParAnotadoresPercentage[k][1]+";"+RUser.menorParPercentage[k]+"</td></tr>\n");

                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresPercentage.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresPercentage.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresPercentage.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>"); 
                             }
                        }


                        //if(qtd<=1&&qtd>0)bw.write("</div>\n" +"<div class=\"esquerda\">");
                        if(!robinsons.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("RobinsonsA") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+robinsons.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);

                            qtd--;
                        }
                        //pares robinsons aqui
                        if(paresRobinson){
                             if(robinsons.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("RobinsonsA") +"</p>\n" +
    "<table align=\"center\">\n");
                             qtd--;
                             for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalRobinson[k]/numPares)+"</td></tr>\n" +                                     
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresRobinson[k][0]+";"+RUser.maiorParAnotadoresRobinson[k][1]+";"+RUser.maiorParRobinson[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresRobinson[k][0]+";"+RUser.menorParAnotadoresRobinson[k][1]+";"+RUser.menorParRobinson[k]+"</td></tr>\n");

                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresRobinson.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresRobinson.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresRobinson.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>"); 
                             }
                        }

                        //if(qtd<=1&&qtd>0)bw.write("</div>\n" +"<div class=\"esquerda\">");
                        if(!kendalls.isEmpty()){
                              bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KendallsW") +"</p>\n" +
    "<table align=\"center\">\n");
                            String content = "";
                            for(int k=0; k<RUser.categorias.size();k++){
                                String classe = "";
                                if(k%2!=0) classe = " class = 'odd'";
                                    content += "<tr"+classe+"><td><b>"+RUser.categorias.get(k)+"</b>"
                                    + "</td><td align=center>"+kendalls.get(k)+"</td></tr>\n";


                            }
                            content += "</table>\n" + "</br>\n";
                            bw.write(content);


                        }
                        //pares kendalls aqui
                        if(paresKendall){
                             if(kendalls.isEmpty()) bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("KendallsW") +"</p>\n" +
    "<table align=\"center\">\n");

                             for(int k=0; k<RUser.categorias.size();k++){
                                 bw.write("<p align=\"center\" style=\"font-size:18px; margin-left:20px\">"+bundle.getString("PairwiseEvaluation") +"</p>\n" +
    "<table align=\"center\">\n" +
    "<tr class='odd'><td colspan=2><b>"+RUser.categorias.get(k)+"</b></td></tr>\n" +
    "<tr><td >"+bundle.getString("Average") +"</td><td align=center>"+(RUser.totalKendall[k]/numPares)+"</td></tr>\n" +                                     
    "<tr class='odd'><td >"+bundle.getString("Highest") +"</td><td align=center>"+RUser.maiorParAnotadoresKendall[k][0]+";"+RUser.maiorParAnotadoresKendall[k][1]+";"+RUser.maiorParKendall[k]+"</td></tr>\n" +
    "<tr><td >"+bundle.getString("Lowest") +"</td><td align=center>"+RUser.menorParAnotadoresKendall[k][0]+";"+RUser.menorParAnotadoresKendall[k][1]+";"+RUser.menorParKendall[k]+"</td></tr>\n");

                                TreeSet<String> anotadores = new TreeSet<String>();
                                anotadores.addAll(RUser.paresKendall.get(k).keySet());

                                boolean odd = true;
                                for(String par:anotadores){
                                   if(par.equals("INITIALIZING")) continue;
                                   String ann1 = par.substring(0, par.indexOf(" ;"));
                                   String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                   if(odd){
                                       bw.write("<tr class='odd'><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKendall.get(k).get(par) +"</td></tr>\n");
                                       odd = false;
                                   } else {
                                       bw.write("<tr><td >"+bundle.getString("Pair") +"</td><td align=center>"+ ann1 +";"+ ann2 +";"+ RUser.paresKendall.get(k).get(par) +"</td></tr>\n");
                                       odd = true;
                                   }
                               }

                               bw.write("</table></br>"); 
                             }
                        }
                        


                    }


                    bw.write("</div>\n" +
    "</div></div></body></html>");
                    bw.close();

                } catch (IOException ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return true;
            }
            return false;
        }
        
        public static boolean exportaPDF(LinkedList<String> alphas, LinkedList<String> fleisss, LinkedList<String> cohens, LinkedList<String> percentages,
                 LinkedList<String> robinsons, LinkedList<String> kendalls,
                 boolean paresAlpha, boolean paresFleiss, boolean paresCohen, boolean paresPercentage, boolean paresRobinson, boolean paresKendall){
            
             Calendar ca = GregorianCalendar.getInstance();
                ca.setTime(new java.util.Date());
		java.util.Date data = ca.getTime();
            
            
               String x = ca.getTime().toString();
               x = x.replaceAll(":", "-");
            
            JFileChooser open = new JFileChooser();
            open.setDialogTitle(bundle.getString("ReportPath"));
            open.setApproveButtonText(bundle.getString("Select"));
            open.setSelectedFile(new File("AgreeCalc "+ bundle.getString("Report") +" - PDF - "+x));
            int op = open.showOpenDialog(FerramentaGUI.getInstance());  
            File arq = null;
            if(op == JFileChooser.APPROVE_OPTION){  
               arq = open.getSelectedFile();  
             
            
            String caminho = "";
            if(arq!=null) caminho = arq.getAbsolutePath()+".pdf";
            
            
            FileOutputStream fos;
        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream(caminho));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document, alphas, fleisss, cohens, percentages, robinsons, kendalls, 
                    paresAlpha, paresFleiss, paresCohen, paresPercentage, paresRobinson, paresKendall);
            document.close();
            
            
        }catch (Exception e){
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } 
            return true;
            }
            return false;
        }
        
        // iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(com.itextpdf.text.Document document) {
		document.addTitle("AgreeCalc: "+bundle.getString("Report"));
		//document.addSubject("Using iText");
		document.addKeywords("AgreeCalc, PDF, iText");
		document.addAuthor(System.getProperty("user.name"));
		document.addCreator("AgreeCalc");
	}

            
        static String duasCasas(int i){
            String st = String.valueOf(i);
            if(st.length()==2) return st;
            st = "0"+st;
            return st;
            
        }
        
	private static void addTitlePage(com.itextpdf.text.Document document) throws com.itextpdf.text.DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		
		addEmptyLine(preface, 1);
		document.add(preface);
		// Lets write a big header
		Paragraph header = new Paragraph("AgreeCalc", catFont);
		header.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		document.add(header);
		
		preface = new Paragraph();
		addEmptyLine(preface, 1);
		document.add(preface);
		
               // java.util.Date DataTeste;
                Calendar ca = GregorianCalendar.getInstance();
                ca.setTime(new java.util.Date());
		java.util.Date data = ca.getTime();
		//DataTeste = data;
             
                ca = GregorianCalendar.getInstance();
                ca.setTime(new java.util.Date());
		data = ca.getTime();
            
            
               String x = ca.getTime().toString();
               x = x.replaceAll(":", "-");
		// Will create: Report generated by: _name, _date
                //System.out.println(data.getYear());
		Paragraph r = new Paragraph(bundle.getString("ReportCreated")+ x,tFont);
		r.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		document.add(r);

		preface = new Paragraph();
		addEmptyLine(preface, 2);
		document.add(preface);
		
                
                Paragraph desc = new Paragraph(bundle.getString("CorpusDescription") +": ", subFont);
                document.add(desc);
                
                String st = "";
                st += "\t\t\t\t - "+ bundle.getString("Scheme") + ": "+RUser.scheme+"\n";
                st += "\t\t\t\t - "+ bundle.getString("SourceCorpus") +": "+RUser.source_corpus+"\n";
                st += "\t\t\t\t - "+ bundle.getString("AnalyzedUnits") +": "+RUser.qtdUnits.size()+"\n";
                st += "\t\t\t\t - "+ bundle.getString("Annotators")+":\n\t\t\t\t\t\t\t\t\t";
                for(String a:RUser.anotadores){
                    st += ""+a+"; ";
                }
                st += "\n\t\t\t - "+ bundle.getString("AnalyzedDocuments") +":\n\t\t\t\t\t\t\t\t\t";
                for(String a:RUser.resumos){
                    st += ""+a+"; ";
                }
                
		desc = new Paragraph(	st, tFont);
		document.add(desc);
		// Start a new page
		document.newPage();
	}

	private static void addContent(com.itextpdf.text.Document document, LinkedList<String> alphas, LinkedList<String> fleisss, LinkedList<String> cohens, LinkedList<String> percentages,
                 LinkedList<String> robinsons, LinkedList<String> kendalls, 
                 boolean paresAlpha, boolean paresFleiss, boolean paresCohen, boolean paresPercentage, boolean paresRobinson, boolean paresKendall) 
                throws com.itextpdf.text.DocumentException {
		Paragraph teste;
                
                for(int k=0; k<RUser.categorias.size();k++){
                    teste = new Paragraph(RUser.categorias.get(k)+"\n", subFont);
                    teste.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    document.add(teste);
                    
                        teste = new Paragraph();
                    // Add a table de coeficientes
                    createTable(teste, alphas, fleisss, cohens, percentages, robinsons, kendalls, k);
                    
                    document.add(teste);
                    if(paresAlpha || paresCohen || paresFleiss || paresPercentage || paresRobinson || paresKendall){
                        teste = new Paragraph(bundle.getString("PairwiseEvaluation"), smallBold);
                        teste.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                        document.add(teste);
                    
                        teste = new Paragraph();
                        // Add a table de coeficientes
                        createTablePair(teste, paresAlpha, paresFleiss, paresCohen, paresPercentage, paresRobinson, paresKendall, k);
                    }
                    
                    document.add(teste);
                }

		// Now add all this to the document
		

		
	}

        
        private static void createTablePair(Paragraph subCatPart,boolean paresAlpha, boolean paresFleiss, boolean paresCohen, boolean paresPercentage,
                boolean paresRobinson, boolean paresKendall, int k) throws BadElementException {
        try {
            PdfPTable table= new PdfPTable(4);//colunas
            float[] f = new float[4];
            f[0] = (float) 0.3;
            f[1] = (float) 0.2;
            f[2] = (float) 0.2;
            f[3] = (float) 0.5;
            PdfPCell c1;
            if(paresAlpha){
               table = new PdfPTable(4);//colunas

                // t.setBorderColor(BaseColor.GRAY);
                // t.setPadding(4);
            
                // t.setBorderWidth(1);
                table.setSpacingBefore(15f);
                table.setSpacingAfter(25f);
               
                table.setWidths(f);
                c1 = new PdfPCell(new Phrase(bundle.getString("Coefficient"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase(bundle.getString("Type"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                table.setHeaderRows(1);
                table.setHeaderRows(2);
                table.setHeaderRows(5);
                
                TreeSet<String> anotadores = new TreeSet<String>();
                anotadores.addAll(RUser.paresAlfa.get(k).keySet());
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Alpha"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Nominal", tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(4);
                table.addCell(c1);
                
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Average"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.totalAlfa[k]/Report.calcNumPares(RUser.anotadores.size())), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Agreement"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Annotators"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Highest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresAlfa[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresAlfa[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.maiorParAlfa[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Lowest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresAlfa[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresAlfa[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.menorParAlfa[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                
                                    
                for(String par:anotadores){
                    if(par.equals("INITIALIZING")) continue;
                    String ann1 = par.substring(0, par.indexOf(" ;"));
                    String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                    
                    c1 = new PdfPCell(new Phrase(bundle.getString("Pair"), tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase(ann1, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(ann2, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(RUser.paresAlfa.get(k).get(par) + "", tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                }
                //
                subCatPart.add(table);
            }
            if(paresFleiss){
                table = new PdfPTable(4);//colunas

                // t.setBorderColor(BaseColor.GRAY);
                // t.setPadding(4);
            
                // t.setBorderWidth(1);
                table.setSpacingBefore(15f);
                table.setSpacingAfter(25f);
               
                table.setWidths(f);
                c1 = new PdfPCell(new Phrase(bundle.getString("Coefficient"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase(bundle.getString("Type"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                table.setHeaderRows(1);
                table.setHeaderRows(2);
                table.setHeaderRows(5);
                
                TreeSet<String> anotadores = new TreeSet<String>();
                anotadores.addAll(RUser.paresKappa.get(k).keySet());
                
                c1 = new PdfPCell(new Phrase(bundle.getString("KappaFleiss"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase("Nominal", tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(4);
                table.addCell(c1);
                
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Average"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.totalKappa[k]/Report.calcNumPares(RUser.anotadores.size())), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Agreement"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Annotators"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Highest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresKappa[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresKappa[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.maiorParKappa[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Lowest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresKappa[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresKappa[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.menorParKappa[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                
                                    
                for(String par:anotadores){
                    if(par.equals("INITIALIZING")) continue;
                    String ann1 = par.substring(0, par.indexOf(" ;"));
                    String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                    
                    c1 = new PdfPCell(new Phrase(bundle.getString("Pair"), tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase(ann1, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(ann2, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(RUser.paresKappa.get(k).get(par) + "", tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                }
                subCatPart.add(table);
            }
            if(paresCohen){
                table = new PdfPTable(4);//colunas

                // t.setBorderColor(BaseColor.GRAY);
                // t.setPadding(4);
            
                // t.setBorderWidth(1);
                table.setSpacingBefore(15f);
                table.setSpacingAfter(25f);
               
                table.setWidths(f);
                c1 = new PdfPCell(new Phrase(bundle.getString("Coefficient"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase(bundle.getString("Type"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                table.setHeaderRows(1);
                table.setHeaderRows(2);
                table.setHeaderRows(5);
                
                TreeSet<String> anotadores = new TreeSet<String>();
                anotadores.addAll(RUser.paresKappaCohen.get(k).keySet());
                
                c1 = new PdfPCell(new Phrase(bundle.getString("KappaCohen"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase("Nominal", tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(4);
                table.addCell(c1);
                
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Average"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.totalKappaCohen[k]/Report.calcNumPares(RUser.anotadores.size())), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Agreement"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Annotators"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Highest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresKappaCohen[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresKappaCohen[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.maiorParKappaCohen[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Lowest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresKappaCohen[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresKappaCohen[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.menorParKappaCohen[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                
                                    
                for(String par:anotadores){
                    if(par.equals("INITIALIZING")) continue;
                    String ann1 = par.substring(0, par.indexOf(" ;"));
                    String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                    
                    c1 = new PdfPCell(new Phrase(bundle.getString("Pair"), tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase(ann1, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(ann2, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(RUser.paresKappaCohen.get(k).get(par) + "", tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                }
                subCatPart.add(table);
            }
            if(paresPercentage){
                table = new PdfPTable(4);//colunas

                // t.setBorderColor(BaseColor.GRAY);
                // t.setPadding(4);
            
                // t.setBorderWidth(1);
                table.setSpacingBefore(15f);
                table.setSpacingAfter(25f);
               
                table.setWidths(f);
                c1 = new PdfPCell(new Phrase(bundle.getString("Coefficient"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase(bundle.getString("Type"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                table.setHeaderRows(1);
                table.setHeaderRows(2);
                table.setHeaderRows(5);
                
                TreeSet<String> anotadores = new TreeSet<String>();
                anotadores.addAll(RUser.paresPercentage.get(k).keySet());
                
                c1 = new PdfPCell(new Phrase(bundle.getString("PercentageAgreement"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase("Nominal", tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(4);
                table.addCell(c1);
                
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Average"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.totalPercentage[k]/Report.calcNumPares(RUser.anotadores.size())), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Agreement"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Annotators"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Highest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresPercentage[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresPercentage[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.maiorParPercentage[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Lowest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresPercentage[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresPercentage[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.menorParPercentage[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                
                                    
                for(String par:anotadores){
                    if(par.equals("INITIALIZING")) continue;
                    String ann1 = par.substring(0, par.indexOf(" ;"));
                    String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                    
                    c1 = new PdfPCell(new Phrase(bundle.getString("Pair"), tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase(ann1, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(ann2, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(RUser.paresPercentage.get(k).get(par) + "", tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                }
                subCatPart.add(table);
            }
        
            if(paresRobinson){
                table = new PdfPTable(4);//colunas

                // t.setBorderColor(BaseColor.GRAY);
                // t.setPadding(4);
            
                // t.setBorderWidth(1);
                table.setSpacingBefore(15f);
                table.setSpacingAfter(25f);
               
                table.setWidths(f);
                c1 = new PdfPCell(new Phrase(bundle.getString("Coefficient"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase(bundle.getString("Type"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                table.setHeaderRows(1);
                table.setHeaderRows(2);
                table.setHeaderRows(5);
                
                TreeSet<String> anotadores = new TreeSet<String>();
                anotadores.addAll(RUser.paresRobinson.get(k).keySet());
                
                c1 = new PdfPCell(new Phrase(bundle.getString("RobinsonsA"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase("-", tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(4);
                table.addCell(c1);
                
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Average"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.totalRobinson[k]/Report.calcNumPares(RUser.anotadores.size())), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Agreement"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Annotators"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Highest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresRobinson[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresRobinson[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.maiorParRobinson[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Lowest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresRobinson[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresRobinson[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.menorParRobinson[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                
                                    
                for(String par:anotadores){
                    if(par.equals("INITIALIZING")) continue;
                    String ann1 = par.substring(0, par.indexOf(" ;"));
                    String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                    
                    c1 = new PdfPCell(new Phrase(bundle.getString("Pair"), tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase(ann1, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(ann2, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(RUser.paresRobinson.get(k).get(par) + "", tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                }
                subCatPart.add(table);
            }
            
            if(paresKendall){
                table = new PdfPTable(4);//colunas

                // t.setBorderColor(BaseColor.GRAY);
                // t.setPadding(4);
            
                // t.setBorderWidth(1);
                table.setSpacingBefore(15f);
                table.setSpacingAfter(25f);
               
                table.setWidths(f);
                c1 = new PdfPCell(new Phrase(bundle.getString("Coefficient"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                c1 = new PdfPCell(new Phrase(bundle.getString("Type"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);

                table.setHeaderRows(1);
                table.setHeaderRows(2);
                table.setHeaderRows(5);
                
                TreeSet<String> anotadores = new TreeSet<String>();
                anotadores.addAll(RUser.paresKendall.get(k).keySet());
                
                c1 = new PdfPCell(new Phrase(bundle.getString("KendallsW"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase("-", tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(4);
                table.addCell(c1);
                
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Average"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                c1.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.totalKendall[k]/Report.calcNumPares(RUser.anotadores.size())), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Agreement"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Annotators"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                c1.setColspan(2);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(bundle.getString("Highest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresKendall[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.maiorParAnotadoresKendall[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.maiorParKendall[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(bundle.getString("Lowest"), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresKendall[k][0], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(RUser.menorParAnotadoresKendall[k][1], tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(String.valueOf(RUser.menorParKendall[k]), tFont2));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                
                
                                    
                for(String par:anotadores){
                    if(par.equals("INITIALIZING")) continue;
                    String ann1 = par.substring(0, par.indexOf(" ;"));
                    String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                    
                    c1 = new PdfPCell(new Phrase(bundle.getString("Pair"), tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase(ann1, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(ann2, tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(RUser.paresKendall.get(k).get(par) + "", tFont2));
                    c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    table.addCell(c1);
                }
                subCatPart.add(table);
            }
            
        } catch (DocumentException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

	}
	private static void createTable(Paragraph subCatPart, LinkedList<String> alphas, LinkedList<String> fleisss, LinkedList<String> cohens,
                LinkedList<String> percentages,  LinkedList<String> robinsons, LinkedList<String> kendalls, int k) throws BadElementException {
        try {
            PdfPTable table = new PdfPTable(3);//colunas

            // t.setBorderColor(BaseColor.GRAY);
            // t.setPadding(4);
            
            // t.setBorderWidth(1);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(25f);
            float[] f = new float[3];
            f[0] = (float) 0.4;
            f[1] = (float) 0.2;
            f[2] = (float) 0.4;
            table.setWidths(f);
            PdfPCell c1 = new PdfPCell(new Phrase(bundle.getString("Coefficient"), smallBold));
            c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(bundle.getString("Type"), smallBold));
            c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(bundle.getString("Value"), smallBold));
            c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);

            if(!alphas.isEmpty()){
                c1 = new PdfPCell(new Phrase(bundle.getString("Alpha"), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Nominal", tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(alphas.get(k), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            if(!fleisss.isEmpty()){
                c1 = new PdfPCell(new Phrase(bundle.getString("KappaFleiss"), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Nominal", tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(fleisss.get(k), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            if(!cohens.isEmpty()){
                c1 = new PdfPCell(new Phrase(bundle.getString("KappaCohen"), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Nominal", tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(cohens.get(k), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            if(!percentages.isEmpty()){
                c1 = new PdfPCell(new Phrase(bundle.getString("PercentageAgreement"), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Nominal", tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(percentages.get(k), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            if(!robinsons.isEmpty()){
                c1 = new PdfPCell(new Phrase(bundle.getString("RobinsonsA"), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("-", tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(robinsons.get(k), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            if(!kendalls.isEmpty()){
                c1 = new PdfPCell(new Phrase(bundle.getString("KendallsW"), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("-", tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(kendalls.get(k), tFont));
                c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table.addCell(c1);
            }

            subCatPart.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

	}


	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
        
         public static boolean exportaXML(LinkedList<String> alphas, LinkedList<String> fleisss, LinkedList<String> cohens,
                 LinkedList<String> percentages, LinkedList<String> robinsons, LinkedList<String> kendalls,
                 boolean paresAlpha, boolean paresFleiss, boolean paresCohen, boolean paresPercentage, boolean paresRobinson, boolean paresKendall) throws IOException{
                java.util.Date DataTeste;
                Calendar ca = GregorianCalendar.getInstance();
		java.util.Date data = ca.getTime();
		DataTeste = data;
              String x = ca.getTime().toString();
               x = x.replaceAll(":", "-");
             
             
             JFileChooser open = new JFileChooser();
            
            open.setDialogTitle(bundle.getString("ReportPath"));
            open.setApproveButtonText(bundle.getString("Select"));
            open.setSelectedFile(new File("AgreeCalc "+ bundle.getString("Report") +" - XML - "+x));
            int op = open.showOpenDialog(FerramentaGUI.getInstance());  
            File arq = null;
            if(op == JFileChooser.APPROVE_OPTION){  
               arq = open.getSelectedFile();  
             
            
            String caminho = "";
            if(arq!=null) caminho = arq.getAbsolutePath()+".xml";
             
                Element annotation = new Element("report");//cria tag 'document'
		//aqui vao os infos..
                RUser r = RUser.getInstance();
		Element info;
	
                info = new Element("info");
		info.setAttribute("type","datetime");
		info.setAttribute("value",x);
		annotation.addContent(info);
                
                info = new Element("info");
		info.setAttribute("type","scheme");
		info.setAttribute("value",r.scheme);
		annotation.addContent(info);
		
                
                Element annotators = new Element("annotators");
                
		Iterator<String> it = r.anotadores.iterator();
		while(it.hasNext()){
			Element ann = new Element("annotator");
			ann.setAttribute("id", it.next());
			annotators.addContent(ann);
		}
                annotation.addContent(annotators);
			
                Element sources = new Element("sources");
                sources.setAttribute("corpus",r.source_corpus);
                
		it = r.resumos.iterator();
		while(it.hasNext()){
			Element s = new Element("source");
			s.setAttribute("id",it.next());
			sources.addContent(s);
		}
                annotation.addContent(sources);
			
		Element coef;
                for(int k=0;k<r.categorias.size();k++){
                    Element category = new Element("category");
                    category.setAttribute("id",r.categorias.get(k));
                    if(!alphas.isEmpty()){
                        coef = new Element("coefficient");
                        coef.setAttribute("type","Krippendorff's Alpha");
                        coef.setAttribute("variation","nominal");
                        coef.setAttribute("value",alphas.get(k));
                        category.addContent(coef);
                    }
                    if(!fleisss.isEmpty()){
                        coef = new Element("coefficient");
                        coef.setAttribute("type","Fleiss' Kappa");
                        coef.setAttribute("variation","nominal");
                        coef.setAttribute("value",fleisss.get(k));
                        category.addContent(coef);
                    }
                    if(!cohens.isEmpty()){
                        coef = new Element("coefficient");
                        coef.setAttribute("type","Cohen's Kappa");
                        coef.setAttribute("variation","nominal");
                        coef.setAttribute("value",cohens.get(k));
                        category.addContent(coef);
                    }
                    if(!percentages.isEmpty()){
                        coef = new Element("coefficient");
                        coef.setAttribute("type","Percentage Agreement");
                        coef.setAttribute("variation","nominal");
                        coef.setAttribute("value",percentages.get(k));
                        category.addContent(coef);
                    }
                    if(!robinsons.isEmpty()){
                        coef = new Element("coefficient");
                        coef.setAttribute("type","Robinson's A");
                        coef.setAttribute("variation","nominal");
                        coef.setAttribute("value",robinsons.get(k));
                        category.addContent(coef);
                    }
                    if(!kendalls.isEmpty()){
                        coef = new Element("coefficient");
                        coef.setAttribute("type","Kendall's W");
                        coef.setAttribute("variation","nominal");
                        coef.setAttribute("value",kendalls.get(k));
                        category.addContent(coef);
                    }
                    
                    //faz os pares
                    if(paresAlpha || paresCohen || paresFleiss || paresPercentage || paresRobinson || paresKendall){
                        //se ele escolheu por pares
                        //Element pairwise = new Element("pairwise-evaluation");
                            if(paresAlpha){
                                //alpha
                                Element alpha = new Element("pairwise-coefficient");
                                alpha.setAttribute("type","Krippendorff's Alpha");
                                alpha.setAttribute("variation","nominal");
                                    Element evaluation = new Element("agreement");
                                    evaluation.setAttribute("type", "average-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.totalAlfa[k]/r.totalParidades));
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","highest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.maiorParAlfa[k]));
                                    evaluation.setAttribute("anotator1", r.maiorParAnotadoresAlfa[k][0]);
                                    evaluation.setAttribute("anotator2", r.maiorParAnotadoresAlfa[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","lowest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.menorParAlfa[k]));
                                    evaluation.setAttribute("anotator1", r.menorParAnotadoresAlfa[k][0]);
                                    evaluation.setAttribute("anotator2", r.menorParAnotadoresAlfa[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    
                                    TreeSet<String> anotadores = new TreeSet<String>();
                                    anotadores.addAll(r.paresAlfa.get(k).keySet());
                                    
                                    
                                    for(String par:anotadores){
                                        if(par.equals("INITIALIZING")) continue;
                                        String ann1 = par.substring(0, par.indexOf(" ;"));
                                        String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                        //System.out.println(ann1);
                                        //System.out.println(ann2);
                                        //System.out.println(par + " = " + r.paresAlfa.get(k).get(par));
                                        
                                        evaluation = new Element("agreement");
                                        evaluation.setAttribute("type", "pair-agreement");
                                        evaluation.setAttribute("value", r.paresAlfa.get(k).get(par) + "");
                                        evaluation.setAttribute("annotator1", ann1);
                                        evaluation.setAttribute("annotator2", ann2);
                                        
                                        alpha.addContent(evaluation);
                                    }
                                    
                                category.addContent(alpha);
                            }
                            
                            if(paresFleiss){
                                //fleiss
                                Element alpha = new Element("pairwise-coefficient");
                                alpha.setAttribute("type","Fleiss' Kappa");
                                alpha.setAttribute("variation","nominal");
                                    Element evaluation = new Element("agreement");
                                    
                                    evaluation.setAttribute("type", "average-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.totalKappa[k]/r.totalParidades));
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","highest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.maiorParKappa[k]));
                                    evaluation.setAttribute("anotator1", r.maiorParAnotadoresKappa[k][0]);
                                    evaluation.setAttribute("anotator2", r.maiorParAnotadoresKappa[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","lowest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.menorParKappa[k]));
                                    evaluation.setAttribute("anotator1", r.menorParAnotadoresKappa[k][0]);
                                    evaluation.setAttribute("anotator2", r.menorParAnotadoresKappa[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    
                                    
                                    TreeSet<String> anotadores = new TreeSet<String>();
                                    anotadores.addAll(r.paresKappa.get(k).keySet());
                                    
                                    
                                    for(String par:anotadores){
                                        if(par.equals("INITIALIZING")) continue;
                                        String ann1 = par.substring(0, par.indexOf(" ;"));
                                        String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                        //System.out.println(ann1);
                                        //System.out.println(ann2);
                                        //System.out.println(par + " = " + r.paresAlfa.get(k).get(par));
                                        
                                        evaluation = new Element("agreement");
                                        evaluation.setAttribute("type", "pair-agreement");
                                        evaluation.setAttribute("value", r.paresKappa.get(k).get(par) + "");
                                        evaluation.setAttribute("annotator1", ann1);
                                        evaluation.setAttribute("annotator2", ann2);
                                        
                                        alpha.addContent(evaluation);
                                    }
                                    
                                category.addContent(alpha);
                            }
                            
                            
                            if(paresCohen){
                                //cohen
                                Element alpha = new Element("pairwise-coefficient");
                                alpha.setAttribute("type","Cohen's Kappa");
                                alpha.setAttribute("variation","nominal");
                                    Element evaluation = new Element("agreement");
                                    
                                    evaluation.setAttribute("type", "average-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.totalKappaCohen[k]/r.totalParidades));
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","highest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.maiorParKappaCohen[k]));
                                    evaluation.setAttribute("anotator1", r.maiorParAnotadoresKappaCohen[k][0]);
                                    evaluation.setAttribute("anotator2", r.maiorParAnotadoresKappaCohen[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","lowest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.menorParKappaCohen[k]));
                                    evaluation.setAttribute("anotator1", r.menorParAnotadoresKappaCohen[k][0]);
                                    evaluation.setAttribute("anotator2", r.menorParAnotadoresKappaCohen[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    
                                    
                                    TreeSet<String> anotadores = new TreeSet<String>();
                                    anotadores.addAll(r.paresKappaCohen.get(k).keySet());
                                    
                                    
                                    for(String par:anotadores){
                                        if(par.equals("INITIALIZING")) continue;
                                        String ann1 = par.substring(0, par.indexOf(" ;"));
                                        String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                        //System.out.println(ann1);
                                        //System.out.println(ann2);
                                        //System.out.println(par + " = " + r.paresAlfa.get(k).get(par));
                                        
                                        evaluation = new Element("agreement");
                                        evaluation.setAttribute("type", "pair-agreement");
                                        evaluation.setAttribute("value", r.paresKappaCohen.get(k).get(par) + "");
                                        evaluation.setAttribute("annotator1", ann1);
                                        evaluation.setAttribute("annotator2", ann2);
                                        
                                        alpha.addContent(evaluation);
                                    }
                                    
                                category.addContent(alpha);
                            }
                            
                            if(paresPercentage){
                                //percentage
                                Element alpha = new Element("pairwise-coefficient");
                                alpha.setAttribute("type","Percentage");
                                alpha.setAttribute("variation","nominal");
                                    Element evaluation = new Element("agreement");
                                    
                                    evaluation.setAttribute("type", "average-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.totalPercentage[k]/r.totalParidades));
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","highest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.maiorParPercentage[k]));
                                    evaluation.setAttribute("anotator1", r.maiorParAnotadoresPercentage[k][0]);
                                    evaluation.setAttribute("anotator2", r.maiorParAnotadoresPercentage[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","lowest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.menorParPercentage[k]));
                                    evaluation.setAttribute("anotator1", r.menorParAnotadoresPercentage[k][0]);
                                    evaluation.setAttribute("anotator2", r.menorParAnotadoresPercentage[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    
                                    
                                    TreeSet<String> anotadores = new TreeSet<String>();
                                    anotadores.addAll(r.paresPercentage.get(k).keySet());
                                    
                                    
                                    for(String par:anotadores){
                                        if(par.equals("INITIALIZING")) continue;
                                        String ann1 = par.substring(0, par.indexOf(" ;"));
                                        String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                        //System.out.println(ann1);
                                        //System.out.println(ann2);
                                        //System.out.println(par + " = " + r.paresAlfa.get(k).get(par));
                                        
                                        evaluation = new Element("agreement");
                                        evaluation.setAttribute("type", "pair-agreement");
                                        evaluation.setAttribute("value", r.paresPercentage.get(k).get(par) + "");
                                        evaluation.setAttribute("annotator1", ann1);
                                        evaluation.setAttribute("annotator2", ann2);
                                        
                                        alpha.addContent(evaluation);
                                    }
                                    
                                category.addContent(alpha);
                            }
                            
                            if(paresRobinson){
                                //Robinsons
                                Element alpha = new Element("pairwise-coefficient");
                                alpha.setAttribute("type","Robinson's A");
                                alpha.setAttribute("variation","-");
                                    Element evaluation = new Element("agreement");
                                    
                                    evaluation.setAttribute("type", "average-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.totalRobinson[k]/r.totalParidades));
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","highest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.maiorParRobinson[k]));
                                    evaluation.setAttribute("anotator1", r.maiorParAnotadoresRobinson[k][0]);
                                    evaluation.setAttribute("anotator2", r.maiorParAnotadoresRobinson[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","lowest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.menorParRobinson[k]));
                                    evaluation.setAttribute("anotator1", r.menorParAnotadoresRobinson[k][0]);
                                    evaluation.setAttribute("anotator2", r.menorParAnotadoresRobinson[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    
                                    
                                    TreeSet<String> anotadores = new TreeSet<String>();
                                    anotadores.addAll(r.paresRobinson.get(k).keySet());
                                    
                                    
                                    for(String par:anotadores){
                                        if(par.equals("INITIALIZING")) continue;
                                        String ann1 = par.substring(0, par.indexOf(" ;"));
                                        String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                        //System.out.println(ann1);
                                        //System.out.println(ann2);
                                        //System.out.println(par + " = " + r.paresAlfa.get(k).get(par));
                                        
                                        evaluation = new Element("agreement");
                                        evaluation.setAttribute("type", "pair-agreement");
                                        evaluation.setAttribute("value", r.paresRobinson.get(k).get(par) + "");
                                        evaluation.setAttribute("annotator1", ann1);
                                        evaluation.setAttribute("annotator2", ann2);
                                        
                                        alpha.addContent(evaluation);
                                    }
                                    
                                category.addContent(alpha);
                            }

                            
                            if(paresKendall){
                                //Kendalls
                                Element alpha = new Element("pairwise-coefficient");
                                alpha.setAttribute("type","Kendall's W");
                                alpha.setAttribute("variation","-");
                                    Element evaluation = new Element("agreement");
                                    
                                    evaluation.setAttribute("type", "average-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.totalKendall[k]/r.totalParidades));
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","highest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.maiorParKendall[k]));
                                    evaluation.setAttribute("anotator1", r.maiorParAnotadoresKendall[k][0]);
                                    evaluation.setAttribute("anotator2", r.maiorParAnotadoresKendall[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    evaluation = new Element("agreement");
                                    evaluation.setAttribute("type","lowest-agreement");
                                    evaluation.setAttribute("value",String.valueOf(r.menorParKendall[k]));
                                    evaluation.setAttribute("anotator1", r.menorParAnotadoresKendall[k][0]);
                                    evaluation.setAttribute("anotator2", r.menorParAnotadoresKendall[k][1]); 
                                    alpha.addContent(evaluation);
                                    
                                    
                                    
                                    TreeSet<String> anotadores = new TreeSet<String>();
                                    anotadores.addAll(r.paresKendall.get(k).keySet());
                                    
                                    
                                    for(String par:anotadores){
                                        if(par.equals("INITIALIZING")) continue;
                                        String ann1 = par.substring(0, par.indexOf(" ;"));
                                        String ann2 = par.substring(par.lastIndexOf(" ") + 1, par.length());
                                        //System.out.println(ann1);
                                        //System.out.println(ann2);
                                        //System.out.println(par + " = " + r.paresAlfa.get(k).get(par));
                                        
                                        evaluation = new Element("agreement");
                                        evaluation.setAttribute("type", "pair-agreement");
                                        evaluation.setAttribute("value", r.paresKendall.get(k).get(par) + "");
                                        evaluation.setAttribute("annotator1", ann1);
                                        evaluation.setAttribute("annotator2", ann2);
                                        
                                        alpha.addContent(evaluation);
                                    }
                                    
                                category.addContent(alpha);
                            }
                    }
                    annotation.addContent(category);
                    
                }
                //criando arquivo
		Document doc = new Document();  
		doc.setRootElement(annotation);  
		
		try {
                    String nome = caminho;
                    Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nome), "UTF8"));  
                    XMLOutputter xout = new XMLOutputter();  
                    xout.setFormat(Format.getPrettyFormat());
                    xout.output(doc,out);  
                    out.close();
                    return true;
                }catch(Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
         }
         
        
        
	public static boolean topMarksXml(ArrayList<String> tops, TreeSet<String> anotadores, TreeSet<String> resumos, String tipo){
		int tags = 0;
		//fazer unit inicial..
		
		Element annotation = new Element("annotation");//cria tag 'document'
		//aqui vao os infos..
                RUser r = RUser.getInstance();
                int id = r.qtdMajs + 1;
		Element info = new Element("info");
		info.setAttribute("type","id");
		info.setAttribute("value","maj00"+id);
		annotation.addContent(info);
		
                info = new Element("info");
		info.setAttribute("type","multiple");
		info.setAttribute("value",tipo);
		annotation.addContent(info);
                    
                info = new Element("info");
		info.setAttribute("type","scheme");
		info.setAttribute("value",r.scheme);
		annotation.addContent(info);
		
		 Element annotators = new Element("annotators");
                
		Iterator<String> it = r.anotadores.iterator();
		while(it.hasNext()){
			Element ann = new Element("annotator");
			ann.setAttribute("id", it.next());
			annotators.addContent(ann);
		}
                annotation.addContent(annotators);
			
		Element sources = new Element("sources");
                sources.setAttribute("corpus",r.source_corpus);
                
		it = r.resumos.iterator();
		while(it.hasNext()){
			Element s = new Element("source");
			s.setAttribute("id",it.next());
			sources.addContent(s);
		}
                annotation.addContent(sources);
		
		Element mark;
		Element ann;
		//Iterator<String> itTops = tops.iterator();
		Object[] arrayTop = tops.toArray();
		Iterator<String> iteratorUnits = r.qtdUnits.iterator();
                int i = 0;
                while(iteratorUnits.hasNext()){
                    String unit = iteratorUnits.next();
		//for(int i = 0;i<(arrayTop.length)/3;i++,k++){
			mark = new Element("mark");
			mark.setAttribute("unit", unit);
			
                        for(int k = 0; k<r.categorias.size();k++){
                            ann = new Element("ann");
                            ann.setAttribute("type",r.categorias.get(k));
                            ann.setAttribute("value", (String) arrayTop[i+(k*r.qtdUnits.size())]);
                            mark.addContent(ann);
			
                        }
			
			annotation.addContent(mark);
			
                        i++;
		}
		
		
			
		//criando arquivo
		Document doc = new Document();  
		doc.setRootElement(annotation);  
		
		try {
			String nome;
			if(tags==1)  nome = r.diretorio+"\\ann_maj00"+id+"_"+r.scheme +"_"+r.source_corpus+"_"+paraString(resumos)+"_M.xml";
			else nome = r.diretorio+"\\ann_maj00"+id+"_"+r.scheme+"_"+r.source_corpus+"_M_M.xml";
                        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nome), "UTF8"));  
                        XMLOutputter xout = new XMLOutputter();  
                        xout.setFormat(Format.getPrettyFormat());
                        xout.output(doc,out);  
                        out.close();
                        r.qtdMajs++;
              return true;
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();
        } catch (IOException e) {  
            e.printStackTrace();
        }
                return false;
	}

    private void copiarImagemLogo(File arq) throws IOException {
        File pasta = arq.getParentFile();
        if(pasta.isDirectory()){
            File imgDir = new File(pasta.getAbsolutePath()+"/img");
            if(!imgDir.exists()){
                imgDir.mkdir();
            }
            File img = new File(imgDir.getAbsolutePath()+"/logo_extenso.png");
            img.createNewFile();
            InputStream is=null;
            FileOutputStream os=null;
            try {
                is = getClass().getResourceAsStream("../imgs/logo_extenso.png");
                os = new FileOutputStream(img);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } finally {
                if(is!=null && os!=null){
                    is.close();
                    os.close();
                }
            }
        }
    }

    private static int calcNumPares(int anotadores){
        int a, b;
        a = fatorial(anotadores);
        b = fatorial(anotadores-2);
        return (a/(2*b));
    }
    
    private static int fatorial(int n){
        int resp=1;
        for(int a=n; a>1; a--){
            resp*=a;
        }
        return resp;
    }
}

