/* Classe que implementa o FileFilter de arquivo XML */
package MetaAnn.file;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.filechooser.FileFilter;

public class FiltroArquivo{
    
    public static FileFilter criaFiltro(Locale ferramentaLocale){
        
        return new FileFilter(){
    
            public boolean accept(File CaminhoArquivo)
            {
                if(CaminhoArquivo.isDirectory())
                    return true;
                
                String nomeArquivo = CaminhoArquivo.getName();
                String[] fileParts = nomeArquivo.split("\\.");
                
                if(fileParts.length < 2)
                    return false;
                else
                {
                    if(fileParts[fileParts.length-1].toLowerCase().equals("xml"))
                        return true;
                }
                
                return false;
            }
            
            public String getDescription()
            {
                ResourceBundle bundle = ResourceBundle.getBundle("MetaAnn.Idioma.MetaAnn.MetaAnn", ferramentaLocale);
                return bundle.getString("arquivo_xml") + " (.xml)";
            }
        };
    }
    
}
