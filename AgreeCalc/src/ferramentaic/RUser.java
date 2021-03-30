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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

import rcaller.RCaller;
import rcaller.RCode;
public class RUser {	

    //moda das anotacoes dos anotadores e documentos selecionados
    private static ArrayList<String> topsMode = new ArrayList<String>();
    //maioria
    private static ArrayList<String> topsMajority = new ArrayList<String>();

    private static TreeSet<File> anotacoesValidas = new TreeSet<File>();//changed
    public static TreeSet<String> qtdUnits = new TreeSet<String>();
    public static TreeSet<String> resumos = new TreeSet<String>();
    public static TreeSet<String> resumosOriginais = new TreeSet<String>();
    public static String diretorio = "";
    public static String scheme = "";
    public static String source_corpus = "";
    public static TreeSet<String> anotadores = new TreeSet<String>();
    public static TreeSet<String> anotadoresOriginais = new TreeSet<String>();

    public static LinkedList<ArrayList<String>> dadosMatriz = new LinkedList<ArrayList<String>>();
    public static LinkedList<String> categorias = new LinkedList<String>();//controle das categorias aceitas no sistema
    public static List<TreeMap<Integer, String>> lista_ocorrencias;
    //public static int[] track = new int[20];

    public static Locale local = new Locale("en");
    public static ResourceBundle bundle;


    public static boolean primeiraVez = true;
    public static String ultimoDiretorio = "";
    public static String Rscript = "";
    public static String[] matrizesAlpha = new String[3];
    private RUser(){}
    private static RUser eu;
    public static int qtdMajs = 0;
    public static String[][] auxMatrix;
    public static ArrayList<String[][]> listaDeAuxMatrix = new ArrayList<String[][]>();

    public static boolean pares = false;//true


    //analise de 2 em 2 anotadores
    public static int totalParidades = 0;
    //Alfa de Krippendorf
    public static ArrayList<HashMap<String, Double>> paresAlfa;
    public static double[] totalAlfa;// = new double[6];
    public static double[] menorParAlfa;// = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
    public static String[][] menorParAnotadoresAlfa;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    public static double[] maiorParAlfa;// = {Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE};
    public static String[][] maiorParAnotadoresAlfa;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    //kappa de Fleiss
    public static ArrayList<HashMap<String, Double>> paresKappa;
    public static double[] totalKappa;// = new double[6];
    public static double[] menorParKappa;// = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
    public static String[][] menorParAnotadoresKappa;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    public static double[] maiorParKappa;// = {Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE};
    public static String[][] maiorParAnotadoresKappa;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    //Kappa de Cohen
    public static ArrayList<HashMap<String, Double>> paresKappaCohen;
    public static double[] totalKappaCohen;// = new double[6];
    public static double[] menorParKappaCohen;// = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
    public static String[][] menorParAnotadoresKappaCohen;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    public static double[] maiorParKappaCohen;// = {Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE};
    public static String[][] maiorParAnotadoresKappaCohen;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    //Percentage
    public static ArrayList<HashMap<String, Double>> paresPercentage;
    public static double[] totalPercentage;// = new double[6];
    public static double[] menorParPercentage;// = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
    public static String[][] menorParAnotadoresPercentage;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    public static double[] maiorParPercentage;// = {Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE};
    public static String[][] maiorParAnotadoresPercentage;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    //Robinsons A
    public static ArrayList<HashMap<String, Double>> paresRobinson;
    public static double[] totalRobinson;// = new double[6];
    public static double[] menorParRobinson;// = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
    public static String[][] menorParAnotadoresRobinson;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    public static double[] maiorParRobinson;// = {Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE};
    public static String[][] maiorParAnotadoresRobinson;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    //Kendalls W
    public static ArrayList<HashMap<String, Double>> paresKendall;
    public static double[] totalKendall;// = new double[6];
    public static double[] menorParKendall;// = {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
    public static String[][] menorParAnotadoresKendall;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    public static double[] maiorParKendall;// = {Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE};
    public static String[][] maiorParAnotadoresKendall;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
    
    public static String[] correspondencias;
         
    public static RUser getInstance(){
            if(eu==null){
                eu = new RUser();
            }
            return eu;
        }
    
        
    public void reset(){ 
        /*
        ocorria um problema ao trocar de corpus durante a execução (provavelmente algumas variaveis
        continuavam populadas), por isso o método reseta todas as variáveis que dizem respeito ao corpus atual.
        */
        topsMode = new ArrayList<String>();
        topsMajority = new ArrayList<String>();
        
        anotacoesValidas = new TreeSet<File>();
        qtdUnits = new TreeSet<String>();
	resumos = new TreeSet<String>();
        resumosOriginais = new TreeSet<String>();
        diretorio = "";
        scheme = "";
        source_corpus = "";
        anotadores = new TreeSet<String>();
        anotadoresOriginais = new TreeSet<String>();
        
        dadosMatriz = new LinkedList<ArrayList<String>>();
        categorias = new LinkedList<String>();
        lista_ocorrencias=null;
        //public static int[] track = new int[20];
	primeiraVez = true;
        ultimoDiretorio = "";
        matrizesAlpha = new String[3];
        qtdMajs = 0;
        auxMatrix=null;
        listaDeAuxMatrix = new ArrayList<String[][]>();
        
        pares = false;
        
        //analise de 2 em 2 anotadores
        totalParidades = 0;
        //Alfa de Krippendorf
        paresAlfa=null;
        totalAlfa=null;
        menorParAlfa=null;
        menorParAnotadoresAlfa=null;
        maiorParAlfa=null;
        maiorParAnotadoresAlfa=null;
        //kappa de Fleiss
        paresKappa=null;
        totalKappa=null;// = new double[6];
        menorParKappa=null;
        menorParAnotadoresKappa=null;
        maiorParKappa=null;
        maiorParAnotadoresKappa=null;
        //Kappa de Cohen
        paresKappaCohen=null;
        totalKappaCohen=null;
        menorParKappaCohen=null;
        menorParAnotadoresKappaCohen=null;
        maiorParKappaCohen=null;
        maiorParAnotadoresKappaCohen=null;// = {{"",""},{"",""},{"",""},{"",""},{"",""},{"",""}};
        //Percentage
        paresPercentage=null;
        totalPercentage=null;
        menorParPercentage=null;
        menorParAnotadoresPercentage=null;
        maiorParPercentage=null;
        maiorParAnotadoresPercentage=null;
        //Robinsons A
        paresRobinson=null;
        totalRobinson=null;
        menorParRobinson=null;
        menorParAnotadoresRobinson=null;
        maiorParRobinson=null;
        maiorParAnotadoresRobinson=null;
        //Kendalls W
        paresKendall=null;
        totalKendall=null;
        menorParKendall=null;
        menorParAnotadoresKendall=null;
        maiorParKendall=null;
        maiorParAnotadoresKendall=null;
        
        correspondencias=null;
    }
    
    public void internacionaliza(String loca1){
        if(loca1.equals("en")) local = new Locale(loca1, "US");
        else local = new Locale(loca1, "BR"); 
        bundle = ResourceBundle.getBundle("i18n.MyBundle", local);
    }
        
    public List<ArrayList<String>> recolheAnotacoes(int anotadores) {
        qtdUnits.clear();
        List<ArrayList<String>> lista = new LinkedList<ArrayList<String>>();//"matriz" das categorias e seus dados
        //lista_ocorrencias = new LinkedList<TreeMap<Integer, String>>();//lista para relacionar as string das categorias em inteiros
        
        for(String a:categorias){
            lista.add(new ArrayList<String>());//cria n listas dentro da matriz, onde n eh o numero de categorias
            //lista_ocorrencias.add(new TreeMap<Integer, String>());////cria n listas para relacao String-Integer, onde n eh o numero de categorias
        }
       
        Iterator<File> iteradorAnotacoes = anotacoesValidas.iterator();
        
        while (iteradorAnotacoes.hasNext()) {
            File ann = iteradorAnotacoes.next();
            //System.out.println(ann.getName());
            //usando o jdom, carrega o xml
            Document doc = null;
            SAXBuilder builder = new SAXBuilder();
            try {
                doc = builder.build(ann.getAbsoluteFile());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, bundle.getString("LoadFileError") +ann.getName()+"\n" + bundle.getString("XMLError"), bundle.getString("CorpusAnalysis"), JOptionPane.ERROR_MESSAGE);
                return null;
            }
            boolean valido = true;
            
            //pega o elemento raiz
            Element annotation = doc.getRootElement();
            //pega os ids dos resumos
            

            List<Element> listaInfos = annotation.getChildren("info");
            for (Element e : listaInfos) {
                //se for a info sobre o annotator.. adiciona a treeset
                if (e.getAttributeValue("type").equals("annotator")) {
                    if(!this.anotadores.contains(e.getAttribute("value").getValue())) valido = false;
                }
                if(e.getAttributeValue("type").equals("source")){
                    if(!this.resumos.contains(e.getAttributeValue("value"))) valido = false;
                }
            }
            if(valido) {
            //faz uma lista com os filhos "mark"
            @SuppressWarnings("unchecked")
            List<Element> listaChildren = annotation.getChildren("mark");
            for (Element e : listaChildren) {
                qtdUnits.add(e.getAttribute("unit").getValue());

                @SuppressWarnings("unchecked")
                List<Element> listaMarks = e.getChildren();//lista dos "filhos" de mark (categorias)
                for (Element mark : listaMarks) {

                    //descobre qual o codigo da categoria..
                    Iterator iterador = categorias.iterator();
                    int cod = 0;
                    String valor = "";
                    while(iterador.hasNext()){
                        valor = (String) iterador.next();
                        if(valor.equals(mark.getAttributeValue("type"))){
                            break;
                        }
                        cod++;
                    }
                    //adiciona a matriz na linha do cod..
                    valor = mark.getAttributeValue("value");
                    lista.get(cod).add(retornaCodigo(valor, cod));
                    //System.out.println(retornaCodigo(valor, cod));
                }
            }
            }
        }
        
        return lista;
    }
	
	//responsavel por "atribuir" codigos aos diferentes valores da categoria 
	public static String retornaCodigo(String code, int codigo){
		if(code.equals("") || code.equals("NA")){
			return "NA";// se estiver vazio retorna NA (missing value)
		}
		
		if(lista_ocorrencias.get(codigo).isEmpty()){// se a lista (treemap) esta vazia
			// adiciona ao map
                        //System.out.println("tava vazio: "+code);
			lista_ocorrencias.get(codigo).put(1, code.toLowerCase());
                        /*if(codigo == 0){
                            track[1]++;
                            //System.out.println("\nAdicionando "+ code + " em "+ 1 +"\n"+lista_ocorrencias.get(codigo).entrySet());
                        }*/
			return String.valueOf(lista_ocorrencias.get(codigo).lastKey());
		}
		
		//ja tem um igual - retorna a chave que ja estava registrada
		if(lista_ocorrencias.get(codigo).containsValue(code.toLowerCase())){
			
                    
                        Iterator<String> it = lista_ocorrencias.get(codigo).values().iterator();
			int cont = 0;
			while(it.hasNext()){
				cont++;
				if(it.next().equals(code.toLowerCase())) break;
			}
                        /*if(codigo == 0){
                            track[cont]++;
                            //System.out.println("\nAdicionando "+ code + " em "+ cont +"\n"+lista_ocorrencias.get(codigo).entrySet());
                        }*/
			return String.valueOf(cont);
		}

		// ainda nao tinha no map
		lista_ocorrencias.get(codigo).put(lista_ocorrencias.get(codigo).lastKey() + 1, code.toLowerCase());
		/*if(codigo == 0){
                    track[lista_ocorrencias.get(codigo).lastKey()]++;
                    //System.out.println("\nAdicionando "+ code + " em "+ lista_ocorrencias.get(codigo).lastKey() +"\n"+lista_ocorrencias.get(codigo).entrySet());
                }*/
                return String.valueOf(lista_ocorrencias.get(codigo).lastKey());
	}
        
       
	
	
	//descobre o numero de anotadores do corpus, categorias, scheme e source-corpus
	public boolean descobreDados(){
		//int cont = 0;
		File[] anns =  (new File(diretorio)).listFiles();//array contendo todas os arquivos do corpus
		//TreeSet<String> anotadores = new TreeSet<String>();
                boolean ok = true;
                boolean primeiraVez = true;
		for(File ann:anns){//percorre cada um
			if(ann.isFile()){//verificando se trata-se de um xml
                            //System.out.println("verificando se trata-se de um xml");
                            if(!ann.getName().substring(ann.getName().length()-4,ann.getName().length()).equals(".xml")) ok = false;
                            else ok = true;
                            
                            //Verifica se eh do mesmo corpus.. blablabla
                            String[] infos = ann.getName().split("_");
                            //System.out.println("Numero de infos: "+infos.length);
                            if(primeiraVez && ok){
                                if(infos.length==6){
                                    if(infos[0].toLowerCase().equals("ann")){
                                        scheme = infos[2].toLowerCase();
                                        source_corpus = infos[3].toLowerCase();
                                        primeiraVez = false;
                                    }else ok = false;
                                   if(infos[5].substring(0, infos[5].length() -4).equals("M")) ok = false;
                                }else{
                                    ok = false;
                                }
                            }
                            //System.out.println("Tá ok? "+ok);
                            if (infos.length == 6 && ok) {
                                
                                //se nao se tratar de uma anotacao tipo M (criada a partir da analise da maioria das anotacoes,
                                //portanto nao valida para novas analises)
                                if (!(infos[5].substring(0, infos[5].length() - 4).equals("M"))) {
                                    anotacoesValidas.add(ann);

                                    if (infos.length == 6) {
                                        if (infos[0].toLowerCase().equals("ann")) {
                                            if (scheme.toLowerCase().equals(infos[2].toLowerCase())) {
                                                if (source_corpus.toLowerCase().equals(infos[3].toLowerCase())) {

                                                    //usando o jdom, carrega o xml
                                                    Document doc = null;
                                                    SAXBuilder builder = new SAXBuilder();
                                                    try {
                                                        doc = builder.build(ann.getAbsoluteFile());
                                                    } catch (Exception e) {
                                                        JOptionPane.showMessageDialog(null, bundle.getString("LoadFileError") +ann.getName()+"\n" + bundle.getString("XMLError"), bundle.getString("CorpusAnalysis"), JOptionPane.ERROR_MESSAGE);
                                                        return false;
                                                    }
                                                    //pega o elemento raiz
                                                    Element annotation = doc.getRootElement();
                                                    //pega os ids dos resumos

                                                    //faz uma lista com os filhos "info"
                                                    @SuppressWarnings("unchecked")
                                                    List<Element> listaChildren = annotation.getChildren("info");
                                                    for (Element e : listaChildren) {
                                                        //se for a info sobre o annotator.. adiciona a treeset
                                                        if (e.getAttributeValue("type").equals("annotator")) {
                                                            anotadores.add(e.getAttribute("value").getValue());
                                                            anotadoresOriginais.add(e.getAttribute("value").getValue());
                                                        }
                                                        if (e.getAttributeValue("type").equals("source")) {
                                                            resumosOriginais.add(e.getAttributeValue("value"));
                                                            resumos.add(e.getAttributeValue("value"));
                                                        }
                                                    }
                                                    
                                                    //verifica todas categorias possiveis do corpus
                                                    listaChildren = annotation.getChildren("mark");
                                                    for (Element e : listaChildren) {
                                                        List<Element> listaMark = e.getChildren("ann");
                                                        for(Element cat:listaMark){
                                                            if (! categorias.contains((String) cat.getAttributeValue("type"))){
                                                                categorias.add(cat.getAttributeValue("type"));
                                                            }
                                                        }
                                                    }
                                                    
                                                }
                                            }
                                        }
                                    }
                                    //else System.out.println("Problema ao extrair infos");
                                }else qtdMajs++;//se eh um xml de majority ou mode, atualiza a qtd deles existentes
                            }
                            
			}
		}
		
		return true;
	}
	
        
        public void paresConcordancia(int k1, int k2, int categoria,boolean bol_kappaFleiss, boolean bol_alfa, boolean bol_kappaCohen, 
                boolean bol_percentage, boolean bol_robinson, boolean bol_kendall, String[][] matriz) {
            String matrix = "";
            //for(int i=0;i<auxMatrix[k1].length;i++){
            for(int i=0;i<matriz[k1].length;i++){
               // matrix+=auxMatrix[k1][i] + ", ";
                matrix+=matriz[k1][i] + ", ";
            }
            
            //for(int i=0;i<auxMatrix[k2].length;i++){
            for(int i=0;i<matriz[k2].length;i++){
                //matrix+=auxMatrix[k2][i] + ", ";
                matrix+=matriz[k2][i] + ", ";
            }
            
            if(matrix.length()>2)matrix = matrix.substring(0, matrix.length() -2);
            
            
            //System.out.println(matrix.charAt(matrix.length()-1));
            if(bol_alfa){
                double alfa = Double.parseDouble(alpha(matrix,2));
                if(alfa!=-1) {
                    totalAlfa[categoria]+=alfa;

                    //guarda o valor para o par
                    paresAlfa.get(categoria).put(correspondencias[k1] + " ;;; " + correspondencias[k2], alfa);

                    if(alfa<menorParAlfa[categoria]){
                        menorParAlfa[categoria] = alfa;
                        menorParAnotadoresAlfa[categoria][0] = correspondencias[k1];
                        menorParAnotadoresAlfa[categoria][1] = correspondencias[k2];
                    }
                    if(alfa>maiorParAlfa[categoria]){
                        maiorParAlfa[categoria] = alfa;
                        maiorParAnotadoresAlfa[categoria][0] = correspondencias[k1];
                        maiorParAnotadoresAlfa[categoria][1] = correspondencias[k2];
                    }
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorKrippendorff") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
                }
            }
            
            if(bol_kappaFleiss){
                double kappa = Double.parseDouble(kappaFleiss(matrix, qtdUnits.size()));
                if(kappa!=-1){
                    paresKappa.get(categoria).put(correspondencias[k1] + " ;;; " + correspondencias[k2], kappa);
                    
                    totalKappa[categoria]+=kappa;
                    if(kappa<menorParKappa[categoria]){
                        menorParKappa[categoria] = kappa;
                        menorParAnotadoresKappa[categoria][0] = correspondencias[k1];
                        menorParAnotadoresKappa[categoria][1] = correspondencias[k2];
                    }
                    if(kappa>maiorParKappa[categoria]){
                        maiorParKappa[categoria] = kappa;
                        maiorParAnotadoresKappa[categoria][0] = correspondencias[k1];
                        maiorParAnotadoresKappa[categoria][1] = correspondencias[k2];
                
                    }
                }else{
                    JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorKappaFleiss") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
                }
            }
            
            if(bol_kappaCohen){
                double kappaCohen = Double.parseDouble(kappaCohen(matrix, qtdUnits.size()));
                if(kappaCohen!=-1) {
                    paresKappaCohen.get(categoria).put(correspondencias[k1] + " ;;; " + correspondencias[k2], kappaCohen);

                    totalKappaCohen[categoria]+=kappaCohen;
                    if(kappaCohen<menorParKappaCohen[categoria]){
                        menorParKappaCohen[categoria] = kappaCohen;
                        menorParAnotadoresKappaCohen[categoria][0] = correspondencias[k1];
                        menorParAnotadoresKappaCohen[categoria][1] = correspondencias[k2];
                    }
                    if(kappaCohen>maiorParKappaCohen[categoria]){
                        maiorParKappaCohen[categoria] = kappaCohen;
                        maiorParAnotadoresKappaCohen[categoria][0] = correspondencias[k1];
                        maiorParAnotadoresKappaCohen[categoria][1] = correspondencias[k2];

                    }
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorKappaCohen") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
                }
            }
            if(bol_percentage){
                double Percentage = Double.parseDouble(agreement(matrix));
                if(Percentage!=-1){
                    paresPercentage.get(categoria).put(correspondencias[k1] + " ;;; " + correspondencias[k2], Percentage);
                    totalPercentage[categoria]+=Percentage;
                    if(Percentage<menorParPercentage[categoria]){
                        menorParPercentage[categoria] = Percentage;
                        menorParAnotadoresPercentage[categoria][0] = correspondencias[k1];
                        menorParAnotadoresPercentage[categoria][1] = correspondencias[k2];
                    }
                    if(Percentage>maiorParPercentage[categoria]){
                        maiorParPercentage[categoria] = Percentage;
                        maiorParAnotadoresPercentage[categoria][0] = correspondencias[k1];
                        maiorParAnotadoresPercentage[categoria][1] = correspondencias[k2];

                    }
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorPercentage") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
                }
            }
            
            if(bol_robinson){
                double robinson = Double.parseDouble(robinson(matrix));
                if(robinson!=-1){
                    paresRobinson.get(categoria).put(correspondencias[k1] + " ;;; " + correspondencias[k2], robinson);
                    totalRobinson[categoria]+=robinson;
                    if(robinson<menorParRobinson[categoria]){
                        menorParRobinson[categoria] = robinson;
                        menorParAnotadoresRobinson[categoria][0] = correspondencias[k1];
                        menorParAnotadoresRobinson[categoria][1] = correspondencias[k2];
                    }
                    if(robinson>maiorParRobinson[categoria]){
                        maiorParRobinson[categoria] = robinson;
                        maiorParAnotadoresRobinson[categoria][0] = correspondencias[k1];
                        maiorParAnotadoresRobinson[categoria][1] = correspondencias[k2];

                    }
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorPercentage") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
                }
            }
            
            
            if(bol_kendall){
                double kendall = Double.parseDouble(kendall(matrix));
                if(kendall!=-1){
                    paresKendall.get(categoria).put(correspondencias[k1] + " ;;; " + correspondencias[k2], kendall);
                    totalKendall[categoria]+=kendall;
                    if(kendall<menorParKendall[categoria]){
                        menorParKendall[categoria] = kendall;
                        menorParAnotadoresKendall[categoria][0] = correspondencias[k1];
                        menorParAnotadoresKendall[categoria][1] = correspondencias[k2];
                    }
                    if(kendall>maiorParKendall[categoria]){
                        maiorParKendall[categoria] = kendall;
                        maiorParAnotadoresKendall[categoria][0] = correspondencias[k1];
                        maiorParAnotadoresKendall[categoria][1] = correspondencias[k2];

                    }
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("RScriptErrorPercentage") + "\n" + bundle.getString("RScriptError"),bundle.getString("Running R"),JOptionPane.ERROR_MESSAGE);
                }
            }
        }
	
	public void dataBuilder(ArrayList<String> matriz, int numAnotadores, int categoria) throws Exception{
		try{
		
                        correspondencias = new String[anotadoresOriginais.size()];
                        Iterator<String> itC = anotadores.iterator();
                        
                        for(int kC=0;kC<correspondencias.length;kC++){
                            if(itC.hasNext()) correspondencias[kC] = itC.next();
                            else correspondencias[kC] = "";
                        }
			//matriz de anotadores x units
			auxMatrix = new String[numAnotadores][qtdUnits.size()];
			//cria a string que corresponde a matriz que sera passada ao R
			String matrizAlpha = "";
			
			//preenchendo a matriz
                        //System.out.println("dados de todos os anotadores sobre a categoria "+categoria+": "+matriz.toString());
			Iterator<String> it = matriz.iterator();
			int contador = 0;
			int ann = 0;
                        
			while(it.hasNext()){
				if(contador>=qtdUnits.size()){
					ann++;
					contador = 0;
				}
                                
				auxMatrix[ann][contador] = it.next();
				matrizAlpha += auxMatrix[ann][contador]+", ";
				contador++;
				
			}
			
                        listaDeAuxMatrix.add(auxMatrix);
			HashMap<String, Integer> verificador = new HashMap<String, Integer>();
			for(int j=0;j<auxMatrix[0].length;j++){
				for(int i=0;i<auxMatrix.length;i++){
					if(verificador.containsKey(auxMatrix[i][j])){
						verificador.put(auxMatrix[i][j],verificador.get(auxMatrix[i][j])+1);
					}else{
						verificador.put(auxMatrix[i][j], 1);
					}
				}
				int maior = 0;
				String maiorChave = "";
				Iterator<Integer> ite = verificador.values().iterator();
				Iterator<String> ite2 = verificador.keySet().iterator();
				while(ite.hasNext()){
					int atual = ite.next();
					String atualChave = ite2.next();
					if(atual>maior){
						maior = atual;
						maiorChave = atualChave;
					}
				}
				//System.out.println(verificador.get(maiorChave));
				if(!maiorChave.equals("NA")) maiorChave = lista_ocorrencias.get(categoria).get(Integer.parseInt(maiorChave));
                                else maiorChave = "";                                        
				topsMode.add(maiorChave);
                                
                                if(maior > anotadores.size()/2) topsMajority.add(maiorChave);
                                else topsMajority.add("");
                                
				verificador = new HashMap<String, Integer>();
			}
			
			
			//retira o ", " das ultimas posicoes
			if(matrizAlpha.length()>2)matrizAlpha = matrizAlpha.substring(0, matrizAlpha.length() -2);
			
			matrizesAlpha[categoria] = matrizAlpha;
			
			}catch(Exception e){
                            JOptionPane.showMessageDialog(null, bundle.getString("CategoryError") +categorias.get(categoria) +". \n" + bundle.getString("CategoryErrorCheck"), bundle.getString("CategoryAnalysis"),JOptionPane.ERROR_MESSAGE);
                            throw new Exception(e);
                            //e.printStackTrace();
                //
   		}
   		
	}
	
        public void fazPares(int categoria, boolean kappaFleiss, boolean alfa, boolean kappaCohen, boolean percentage, boolean robinson, boolean kendall) {
            if(paresAlfa.size() > 0 ) paresAlfa.get(categoria).clear();
            paresAlfa.get(categoria).put("INITIALIZING", -1.0);
            menorParAlfa[categoria] = Double.MAX_VALUE;
            menorParAnotadoresAlfa[categoria][0] = "";
            menorParAnotadoresAlfa[categoria][1] = "";
            maiorParAlfa[categoria] = Double.MIN_VALUE;
            maiorParAnotadoresAlfa[categoria][0] = "";
            maiorParAnotadoresAlfa[categoria][1] = "";
            
            if(paresKappa.size() > 0 ) paresKappa.get(categoria).clear();
            paresKappa.get(categoria).put("INITIALIZING", -1.0);
            menorParKappa[categoria] = Double.MAX_VALUE;
            menorParAnotadoresKappa[categoria][0] = "";
            menorParAnotadoresKappa[categoria][1] = "";
            maiorParKappa[categoria] = Double.MIN_VALUE;
            maiorParAnotadoresKappa[categoria][0] = "";
            maiorParAnotadoresKappa[categoria][1] = "";
            
            if(paresKappaCohen.size() > 0 ) paresKappaCohen.get(categoria).clear();
            paresKappaCohen.get(categoria).put("INITIALIZING", -1.0);
            menorParKappaCohen[categoria] = Double.MAX_VALUE;
            menorParAnotadoresKappaCohen[categoria][0] = "";
            menorParAnotadoresKappaCohen[categoria][1] = "";
            maiorParKappaCohen[categoria] = Double.MIN_VALUE;
            maiorParAnotadoresKappaCohen[categoria][0] = "";
            maiorParAnotadoresKappaCohen[categoria][1] = "";
            
            if(paresPercentage.size() > 0 ) paresPercentage.get(categoria).clear();
            paresPercentage.get(categoria).put("INITIALIZING", -1.0);
            menorParPercentage[categoria] = Double.MAX_VALUE;
            menorParAnotadoresPercentage[categoria][0] = "";
            menorParAnotadoresPercentage[categoria][1] = "";
            maiorParPercentage[categoria] = Double.MIN_VALUE;
            maiorParAnotadoresPercentage[categoria][0] = "";
            maiorParAnotadoresPercentage[categoria][1] = "";
            
            if(paresRobinson.size() > 0 ) paresRobinson.get(categoria).clear();
            paresRobinson.get(categoria).put("INITIALIZING", -1.0);
            menorParRobinson[categoria] = Double.MAX_VALUE;
            menorParAnotadoresRobinson[categoria][0] = "";
            menorParAnotadoresRobinson[categoria][1] = "";
            maiorParRobinson[categoria] = Double.MIN_VALUE;
            maiorParAnotadoresRobinson[categoria][0] = "";
            maiorParAnotadoresRobinson[categoria][1] = "";
            
            if(paresKendall.size() > 0 ) paresKendall.get(categoria).clear();
            paresKendall.get(categoria).put("INITIALIZING", -1.0);
            menorParKendall[categoria] = Double.MAX_VALUE;
            menorParAnotadoresKendall[categoria][0] = "";
            menorParAnotadoresKendall[categoria][1] = "";
            maiorParKendall[categoria] = Double.MIN_VALUE;
            maiorParAnotadoresKendall[categoria][0] = "";
            maiorParAnotadoresKendall[categoria][1] = "";
            
            totalPercentage[categoria] = 0;
            totalAlfa[categoria] = 0;
            totalKappa[categoria] = 0;
            totalKappaCohen[categoria] = 0;
            totalRobinson[categoria] = 0;
            totalKendall[categoria] = 0;
            
            
            totalParidades = 0;

            String[][] matriz = listaDeAuxMatrix.get(categoria);
            
            //System.out.println(maiorParAlfa[categoria]);
            //System.out.println(numAnotadores);
            for (int i = 0; i < matriz.length; i++) {
                for (int j = matriz.length - 1; j > i; j--) {
                    totalParidades++;
                    paresConcordancia(i, j, categoria, kappaFleiss, alfa, kappaCohen, percentage, robinson, kendall, matriz);
                }
            //System.out.println(i);
            }
            //System.out.println(maiorParAlfa[categoria]);

    }

   		
        
        
	public String alpha(String matrix, int anotadores){
		
		// Creating RCaller
                RCaller caller = new RCaller();
		try{
        
        /*
         * Full path of the Rscript. Rscript is an executable file shipped with R.
         * It is something like C:\\Program File\\R\\bin.... in Windows
         */
                        
                        //System.out.println(Rscript);
			caller.setRscriptExecutable(Rscript);//"C:/Program Files/R/R-2.14.0/bin/RScript");
                        
        
        //PROBLEMA: EH NECESSARIO O USUARIO INSTALAR OS PACOTES RUNIVERSAL, RJAVA, IRR VIA R
        
        //Inserting codes in R to calculate Krippendorff's alpha..
			RCode r = new RCode();
			
			//  ALPHA  
			 
			 r.addRCode("library('irr')");
			 
			// System.out.println("\tKrippendorf's Alpha: ");
			
        	
			r.addRCode("nmm <- matrix(c( "+matrix+"),nrow="+anotadores+",byrow=TRUE)");
			r.addRCode("resp <- paste(c(kripp.alpha(nmm)$value))");//$value
			caller.setRCode(r);
			caller.runAndReturnResult("resp");
		
			/*BufferedWriter bf = new BufferedWriter(new FileWriter(new File("windows_alpha.r")));
                        bf.write("library('irr')\n");
                        bf.write("nmm <- matrix(c( "+matrix+"),nrow="+anotadores+",byrow=TRUE)\n");
                        bf.write("resp <- paste(c(kripp.alpha(nmm)$value))\n");
                        bf.close();*/
                        
                        
			String[] x = caller.getParser().getAsStringArray("resp");
			
			for(String result:x) {
                            //System.out.println("\t"+result + "\n");
                            return result;
                        }
                        
                        
                } catch(Exception e){
                    
                    e.printStackTrace();
                    caller.stopStreamConsumers();
                    //JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar executar o R para o cálculo do Alfa de Krippendorff! \nCertifique-se de que o local do Rscript, em Opções -> Caminho do R, está correto.","Executando o R",JOptionPane.ERROR_MESSAGE);
                }
                        return "-1";
	}
	
        public String agreement(String matrix){//percentage agreement
		
            try{
		// Creating RCaller
			RCaller caller = new RCaller();
        
        /*
         * Full path of the Rscript. Rscript is an executable file shipped with R.
         * It is something like C:\\Program File\\R\\bin.... in Windows
         */
			caller.setRscriptExecutable(Rscript);
        
        
        //PROBLEMA: EH NECESSARIO O USUARIO INSTALAR OS PACOTES RUNIVERSAL, RJAVA, IRR VIA R
        
        //Inserting codes in R to calculate Krippendorff's alpha..
			RCode r = new RCode();
			
			//  ALPHA  
			 
			 r.addRCode("library('irr')");
			 
			// System.out.println("\tKrippendorf's Alpha: ");
			
        	
			r.addRCode("nmm <- matrix(c( "+matrix+"),nrow="+qtdUnits.size()+")");
			r.addRCode("resp <- paste(c(agree(nmm,0)$value))");
			caller.setRCode(r);
			caller.runAndReturnResult("resp");
		
			
			String[] x = caller.getParser().getAsStringArray("resp");
			
			//for(String result:x) return result;// System.out.println("\t"+result + "\n");
			//robinson(matrix);
                        //kendall(matrix);
                        
                        return x[0];
                      } catch(Exception e){
                     //JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar executar o R para o cálculo do Percentage Agreement! \nCertifique-se de que o local do Rscript, em Opções -> Caminho do R, está correto.","Executando o R",JOptionPane.ERROR_MESSAGE);
                }
            return "-1";
	}
        
        public String robinson(String matrix){//percentage agreement
		
            try{
		// Creating RCaller
			RCaller caller = new RCaller();
        
        /*
         * Full path of the Rscript. Rscript is an executable file shipped with R.
         * It is something like C:\\Program File\\R\\bin.... in Windows
         */
			caller.setRscriptExecutable(Rscript);
        
        
        //PROBLEMA: EH NECESSARIO O USUARIO INSTALAR OS PACOTES RUNIVERSAL, RJAVA, IRR VIA R
        
        //Inserting codes in R to calculate Krippendorff's alpha..
			RCode r = new RCode();
			
			//  ALPHA  
			 
			 r.addRCode("library('irr')");
			 
			// System.out.println("\tKrippendorf's Alpha: ");
			
        	
			r.addRCode("nmm <- matrix(c( "+matrix+"),nrow="+qtdUnits.size()+")");
			r.addRCode("resp <- paste(c(robinson(nmm)$value))");
			caller.setRCode(r);
			caller.runAndReturnResult("resp");
		
			
			String[] x = caller.getParser().getAsStringArray("resp");
			
			for(String result:x) System.out.println("\t"+result + "\n");
			
                        return x[0];
                      } catch(Exception e){
                     //JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar executar o R para o cálculo do Percentage Agreement! \nCertifique-se de que o local do Rscript, em Opções -> Caminho do R, está correto.","Executando o R",JOptionPane.ERROR_MESSAGE);
                }
            return "-1";
	}
        
        public String kendall(String matrix){//percentage agreement
		
            try{
		// Creating RCaller
			RCaller caller = new RCaller();
        
        /*
         * Full path of the Rscript. Rscript is an executable file shipped with R.
         * It is something like C:\\Program File\\R\\bin.... in Windows
         */
			caller.setRscriptExecutable(Rscript);
        
        
        //PROBLEMA: EH NECESSARIO O USUARIO INSTALAR OS PACOTES RUNIVERSAL, RJAVA, IRR VIA R
        
        //Inserting codes in R to calculate Krippendorff's alpha..
			RCode r = new RCode();
			
			//  ALPHA  
			 
			 r.addRCode("library('irr')");
			 
			// System.out.println("\tKrippendorf's Alpha: ");
			
        	
			r.addRCode("nmm <- matrix(c( "+matrix+"),nrow="+qtdUnits.size()+")");
			r.addRCode("resp <- paste(c(kendall(nmm, TRUE)$value))");
			caller.setRCode(r);
			caller.runAndReturnResult("resp");
		
			
			String[] x = caller.getParser().getAsStringArray("resp");
			
			for(String result:x) System.out.println("\t"+result + "\n");
			
                        return x[0];
                      } catch(Exception e){
                     //JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar executar o R para o cálculo do Percentage Agreement! \nCertifique-se de que o local do Rscript, em Opções -> Caminho do R, está correto.","Executando o R",JOptionPane.ERROR_MESSAGE);
                }
            return "-1";
	}
        
        
        
        
         public String kappaCohen(String matrix, int anotadores){
		try{
		// Creating RCaller
			RCaller caller = new RCaller();
        
        /*
         * Full path of the Rscript. Rscript is an executable file shipped with R.
         * It is something like C:\\Program File\\R\\bin.... in Windows
         */
			caller.setRscriptExecutable(Rscript);
        
        
        //PROBLEMA: EH NECESSARIO O USUARIO INSTALAR OS PACOTES RUNIVERSAL, RJAVA, IRR VIA R
        
        //Inserting codes in R to calculate Krippendorff's alpha..
			RCode r = new RCode();
			
			//  ALPHA  
			 
			 r.addRCode("library('irr')");
			 
                       
			r.addRCode("nmm <- matrix(c("+matrix+"), nrow="+qtdUnits.size()+")");
			r.addRCode("resp <- paste(kappa2(nmm)$value)");//unweighted, equal, weighted
			caller.setRCode(r);
			caller.runAndReturnResult("resp");
		
			
			String[] x = caller.getParser().getAsStringArray("resp");
                        
			for(String result:x) return result;//System.out.println("\t"+result + "\n");
		} catch(Exception e){
                     //JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar executar o R para o cálculo do Kappa de Cohen! \nCertifique-se de que o local do Rscript, em Opções -> Caminho do R, está correto.","Executando o R",JOptionPane.ERROR_MESSAGE);
                }	
                        
                        return "-1";
                        
	}
         
         
         
        
        
	
         public String kappaFleiss(String matrix, int oracoes){
             try{
		// Creating RCaller
		RCaller caller = new RCaller();

		/*
		 * Full path of the Rscript. Rscript is an executable file shipped with
		 * R. It is something like C:\\Program File\\R\\bin.... in Windows
		 */
		caller.setRscriptExecutable(Rscript);

		// PROBLEMA: EH NECESSARIO O USUARIO INSTALAR OS PACOTES RUNIVERSAL,
		// RJAVA, IRR VIA R

		// Inserting codes in R to calculate Krippendorff's alpha..
		RCode r = new RCode();

		//System.out.println("\tFleiss' Kappa: ");

		r.addRCode("library('irr')");
			r.addRCode("mm <- matrix(c( "+matrix+"),nrow="+oracoes+",byrow=TRUE)");
		r.addRCode("library('irr')");
		r.addRCode("resp2 <- paste(c(kappam.fleiss(mm))$value)");//kappa de fleiss, m raters
		caller.setRCode(r);
		caller.runAndReturnResult("resp2");
		String[] x = caller.getParser().getAsStringArray("resp2");
		for(int i = 0;i<x.length;i++) 
			return x[i];//System.out.println("\t"+x[i]);
		//System.out.println();
                } catch(Exception e){
                     //JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar executar o R para o cálculo do Kappa de Fleiss! \nCertifique-se de que o local do Rscript, em Opções -> Caminho do R, está correto.","Executando o R",JOptionPane.ERROR_MESSAGE);
                }
                return "-1";
	}
	public void inicializaPares(){
            totalAlfa = new double[categorias.size()];
            totalKappa = new double[categorias.size()];
            totalKappaCohen = new double[categorias.size()];
            totalPercentage = new double[categorias.size()];
            totalRobinson = new double[categorias.size()];
            totalKendall = new double[categorias.size()];
            
                paresAlfa = new ArrayList<HashMap<String, Double>>();
                for(int i = 0; i< categorias.size(); i++) paresAlfa.add(new HashMap<String, Double>());
                
                menorParAlfa = new double[categorias.size()];
                menorParAnotadoresAlfa = new String[categorias.size()][2];
                maiorParAlfa = new double[categorias.size()];
                maiorParAnotadoresAlfa = new String[categorias.size()][2];
                
                //kappa de Fleiss
                paresKappa = new ArrayList<HashMap<String, Double>>();
                for(int i = 0; i< categorias.size(); i++) paresKappa.add(new HashMap<String, Double>());
                
                menorParKappa = new double[categorias.size()];
                menorParAnotadoresKappa = new String[categorias.size()][2];
                maiorParKappa = new double[categorias.size()];
                maiorParAnotadoresKappa = new String[categorias.size()][2];
                
                //Kappa de Cohen
                paresKappaCohen = new ArrayList<HashMap<String, Double>>();
                for(int i = 0; i< categorias.size(); i++) paresKappaCohen.add(new HashMap<String, Double>());
                
                menorParKappaCohen = new double[categorias.size()];
                menorParAnotadoresKappaCohen = new String[categorias.size()][2];
                maiorParKappaCohen = new double[categorias.size()];
                maiorParAnotadoresKappaCohen = new String[categorias.size()][2];
                
                //Percentage
                paresPercentage = new ArrayList<HashMap<String, Double>>();
                for(int i = 0; i< categorias.size(); i++) paresPercentage.add(new HashMap<String, Double>());
                
                menorParPercentage = new double[categorias.size()];
                menorParAnotadoresPercentage = new String[categorias.size()][2];
                maiorParPercentage = new double[categorias.size()];
                maiorParAnotadoresPercentage = new String[categorias.size()][2];
                
                //Robinsons
                paresRobinson = new ArrayList<HashMap<String, Double>>();
                for(int i = 0; i< categorias.size(); i++) paresRobinson.add(new HashMap<String, Double>());
                
                menorParRobinson = new double[categorias.size()];
                menorParAnotadoresRobinson = new String[categorias.size()][2];
                maiorParRobinson = new double[categorias.size()];
                maiorParAnotadoresRobinson = new String[categorias.size()][2];
                
                //Kendalls
                paresKendall = new ArrayList<HashMap<String, Double>>();
                for(int i = 0; i< categorias.size(); i++) paresKendall.add(new HashMap<String, Double>());
                
                menorParKendall = new double[categorias.size()];
                menorParAnotadoresKendall = new String[categorias.size()][2];
                maiorParKendall = new double[categorias.size()];
                maiorParAnotadoresKendall = new String[categorias.size()][2];
                
            for(int k=0;k<categorias.size();k++){
                //paresAlfa.get(k).put("INITIALIZING", -1.0);
                menorParAlfa[k] = Double.MAX_VALUE;
                menorParAnotadoresAlfa[k][0] = "";
                menorParAnotadoresAlfa[k][1] = "";
                maiorParAlfa[k] = Double.MIN_VALUE;
                maiorParAnotadoresAlfa[k][0] = "";
                maiorParAnotadoresAlfa[k][1] = "";
                
                //kappa de Fleiss
                //paresKappa.get(k).put("INITIALIZING", -1.0);
                menorParKappa[k] = Double.MAX_VALUE;
                menorParAnotadoresKappa[k][0] = "";
                menorParAnotadoresKappa[k][1] = "";
                maiorParKappa[k] = Double.MIN_VALUE;
                maiorParAnotadoresKappa[k][0] = "";
                maiorParAnotadoresKappa[k][1] = "";
                //Kappa de Cohen
                
                //paresKappaCohen.get(k).put("INITIALIZING", -1.0);
                menorParKappaCohen[k] = Double.MAX_VALUE;
                menorParAnotadoresKappaCohen[k][0] = "";
                menorParAnotadoresKappaCohen[k][1] = "";
                maiorParKappaCohen[k] = Double.MIN_VALUE;
                maiorParAnotadoresKappaCohen[k][0] = "";
                maiorParAnotadoresKappaCohen[k][1] = "";
                //Percentage
                //paresPercentage.get(k).put("INITIALIZING", -1.0);
                menorParPercentage[k] = Double.MAX_VALUE;
                menorParAnotadoresPercentage[k][0] = "";
                menorParAnotadoresPercentage[k][1] = "";
                maiorParPercentage[k] = Double.MIN_VALUE;
                maiorParAnotadoresPercentage[k][0] = "";
                maiorParAnotadoresPercentage[k][1] = "";
                
                //Robinsons
                menorParRobinson[k] = Double.MAX_VALUE;
                menorParAnotadoresRobinson[k][0] = "";
                menorParAnotadoresRobinson[k][1] = "";
                maiorParRobinson[k] = Double.MIN_VALUE;
                maiorParAnotadoresRobinson[k][0] = "";
                maiorParAnotadoresRobinson[k][1] = "";
                
                //Kendalls
                menorParKendall[k] = Double.MAX_VALUE;
                menorParAnotadoresKendall[k][0] = "";
                menorParAnotadoresKendall[k][1] = "";
                maiorParKendall[k] = Double.MIN_VALUE;
                maiorParAnotadoresKendall[k][0] = "";
                maiorParAnotadoresKendall[k][1] = "";
            }
        }
        public static void setRscript(){
            FileReader fr = null;
            try {
                fr = new FileReader("R_sourcepath.txt");
                BufferedReader br = new BufferedReader(fr);
                String s;
                while((s = br.readLine()) != null) { 
                    Rscript = s; 
                }
                fr.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, bundle.getString("RScriptRecord") + "\n" + bundle.getString("RScriptError"), bundle.getString("RScriptPath"), JOptionPane.ERROR_MESSAGE);
            }
        }
	public boolean setDados(String diretorio){
            //metodo que desencadeia todo o resto, precisa ser chamado passando o caminho onde se encontra o corpus
            setRscript();
            this.diretorio = diretorio;

            //System.out.println(this.diretorio);
            if(!ultimoDiretorio.equals(this.diretorio)){
                ultimoDiretorio = this.diretorio;
                primeiraVez = true;
            }

            if(primeiraVez){
                //descobre a qtd de anotadores do corpus
                if(!descobreDados()) return false;
                primeiraVez = false;
            }
            inicializaPares();

            if(anotadores.isEmpty()){
                //ERRO: nao ha info de anotadores
                JOptionPane.showMessageDialog(null, bundle.getString("CorpusAnnotatorError") + "\n" + bundle.getString("CorpusError"), bundle.getString("CorpusAnalysis") ,JOptionPane.ERROR_MESSAGE);
                return false;
            }
            lista_ocorrencias = new LinkedList<TreeMap<Integer, String>>();//lista para relacionar as string das categorias em inteiros
            for(String a:categorias){
                lista_ocorrencias.add(new TreeMap<Integer, String>());////cria n listas para relacao String-Integer, onde n eh o numero de categorias
            }


            //lista de listas de dados. cada lista corresponde a uma categoria
            dadosMatriz = (LinkedList<ArrayList<String>>) recolheAnotacoes(anotadores.size());
            if(dadosMatriz==null) return false;

        try {
            calculaCoeficiente();
            return true;
        } catch (Exception ex) {
            return false;
        }
		
	}
        public void calculaCoeficiente() throws Exception{
                
		
		//prepara as matrizes para todas as categorias
		Iterator<ArrayList<String>> it = dadosMatriz.iterator();
		matrizesAlpha = new String[categorias.size()];//numero de dimensoes avaliadas por anotacao
                listaDeAuxMatrix = new ArrayList<String[][]>();
		int categoria = 0;
		while(it.hasNext()){
			dataBuilder(it.next(), anotadores.size(), categoria);
			categoria++;
		}
        }
        
        public boolean fazReport(String tipo){
            if(tipo.equals("moda")) return Report.topMarksXml(topsMode,anotadores,resumos,"mode");
            if(tipo.equals("maioria")) return Report.topMarksXml(topsMajority,anotadores,resumos,"majority");;
            return false;
        }
        
        public boolean testRScript(){
            boolean resp = false;
            
            RCaller caller = new RCaller();
            
            caller.setRscriptExecutable(Rscript);
            
            RCode code = new RCode();
            
            code.addRCode("rTest <- 42");
            
            caller.setRCode(code);
            try{
                caller.runAndReturnResult("rTest");
            
                int[] x = caller.getParser().getAsIntArray("rTest");

                if(x.length>0)
                    if(x[0]==42)
                        resp=true;
            } catch(Exception ex){
                resp = false;
            }
            
            return resp;
        }
}