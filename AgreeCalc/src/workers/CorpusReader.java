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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javax.swing.JOptionPane;

/**
 *
 * @author matheus
 */
public class CorpusReader extends AgreeCalcWorker{

    private final String corpusPath;
    private boolean response;
    
    public CorpusReader(ResourceBundle bundle, String corpusPath) {
        super(bundle);
        this.corpusPath = corpusPath;
    }

    @Override
    @SuppressWarnings("static-access")
    protected Object doInBackground() throws Exception { 
        RUser r = RUser.getInstance();
        this.setStatusMessage(bundle.getString("DialogAnalyze"));
        response = r.setDados(corpusPath);
        return response;
        /* 
        \/\/\/ DEIXA O CÓDIGO DUPLICADO (CÓPIA DE setDados() EM RUser, MAS NÃO FICA RODANDO A 
        ANIMAÇÃO DA JProgressBar
        
        código retirado de setDados em RUser. Progresso adicionado simbolicamente 
        (só pro usuário saber que o programa está rodando ainda).
        */
        /*
        //metodo que desencadeia todo o resto, precisa ser chamado passando o caminho onde se encontra o corpus
        RUser r = RUser.getInstance();
        r.setRscript();
        r.diretorio = corpusPath;

        //System.out.println(this.diretorio);
        if(!r.ultimoDiretorio.equals(r.diretorio)){
            r.ultimoDiretorio = r.diretorio;
            r.primeiraVez = true;
        }

        if(r.primeiraVez){
            //descobre a qtd de anotadores do corpus
            this.setStatusMessage(bundle.getString("DialogAnalyze"));
            if(!r.descobreDados()){
                response = false;
                return null;
            }
            this.setProgress(40);
            r.primeiraVez = false;
        }
        
        this.setStatusMessage(bundle.getString("DialogLoadStructures"));
        r.inicializaPares();
        this.setProgress(50);
        
        if(r.anotadores.isEmpty()){
            //ERRO: nao ha info de anotadores
            JOptionPane.showMessageDialog(null, bundle.getString("CorpusAnnotatorError") + "\n" + bundle.getString("CorpusError"), bundle.getString("CorpusAnalysis") ,JOptionPane.ERROR_MESSAGE);
            response = false;
            return null;
        }
        r.lista_ocorrencias = new LinkedList<TreeMap<Integer, String>>();//lista para relacionar as string das categorias em inteiros
        for(String a:r.categorias){
            r.lista_ocorrencias.add(new TreeMap<Integer, String>());////cria n listas para relacao String-Integer, onde n eh o numero de categorias
        }
        this.setProgress(60);

        //lista de listas de dados. cada lista corresponde a uma categoria
        r.dadosMatriz = (LinkedList<ArrayList<String>>) r.recolheAnotacoes(r.anotadores.size());
        if(r.dadosMatriz==null) {
            response = false;
            return null;
        }
        this.setProgress(90);

        this.setStatusMessage(bundle.getString("DialogFinishing"));
        r.calculaCoeficiente();
        this.setProgress(100);
        
        response = true;
        return response;*/
    }

    @Override
    public Object getResponse() {
        return response;
    }

}
