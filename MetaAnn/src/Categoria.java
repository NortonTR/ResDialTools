package MetaAnn;




import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.DefaultListModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiago Missão
 */
public class Categoria {
    
    /* Identifica o Tipo da Categoria
     * 0 - Opções Mutualmente Exclusivas
     * 1 - Lista Simples de Opções
     * 2 - Campo Textual
    */
    
    public final static int BRANCO = -1;
    public final static int CINZA = 0;
    public final static int PRETO = 1;
    
    public int idNum;
    private int TIPO_DA_CATEGORIA;
    private String Identificador_da_Categoria;
    private String Nome_da_Categoria;
    private String Texto_da_Categoria;
    private ArrayList<Categoria> Dependencias = new ArrayList<Categoria>();
    private ArrayList<Opcao> Opcoes = new ArrayList<Opcao>();
    private DefaultListModel OPCOES_MODEL;
    //Marca utilizada para busca em profundidade
    private int marca;
    
    public int getTipo_da_Categoria(){
        return this.TIPO_DA_CATEGORIA;
    }
    
    public String getNome_da_Categoria(){    
        return this.Nome_da_Categoria;
    }
    
    public String getTexto_da_Categoria(){
        return this.Texto_da_Categoria;
    }
    
    public String getIdentificador_da_Categoria(){
        return this.Identificador_da_Categoria;
    }
    
    public ArrayList<Categoria> getDependencias(){
        return this.Dependencias;
    }
    
    public ArrayList<Opcao> getOpcoes(){
        return this.Opcoes;
    }
    
    public DefaultListModel getOpcoesModel(){
        return this.OPCOES_MODEL;
    }
    
    
    public void setTipo_da_Categoria(int Tipo_da_Categoria){
        this.TIPO_DA_CATEGORIA = Tipo_da_Categoria;
    }
    
    public void setNome_da_Categoria(String Nome_da_Categoria){
        this.Nome_da_Categoria = Nome_da_Categoria;
    }
    
    public void setTexto_da_Categoria(String Texto_da_Categoria){
        this.Texto_da_Categoria = Texto_da_Categoria;
    }
    
    public void setIdentificador_da_Categoria(String Identificador_da_Categoria){
        this.Identificador_da_Categoria = Identificador_da_Categoria;
    }
    
    public void setOpcoes(ArrayList<Opcao> opcoes){
        this.Opcoes = opcoes;
    }
    
    public void setOpcoesModel(DefaultListModel Opcoes_Model){
        this.OPCOES_MODEL = Opcoes_Model;
    }
    
    public DefaultListModel getCloneOpcoesModel()
    {
        DefaultListModel retorno = new DefaultListModel();
        Enumeration el = this.OPCOES_MODEL.elements();
        
        while(el.hasMoreElements())
            retorno.addElement(el.nextElement());
        
        return retorno;
    }
    
    public void setDependencias(Categoria cate){
        this.Dependencias.add(cate);
    }
    
    public int getMarca(){
        return this.marca;
    }
    
    public void setMarca(int marca){
        this.marca = marca;
    }
    
    public String toString()
    {
        return this.Nome_da_Categoria;
    }
}
