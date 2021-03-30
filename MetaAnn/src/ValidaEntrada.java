/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MetaAnn;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Tiago
 */
class ValidaEntrada extends PlainDocument{
    public void insertString(int offset,String str, AttributeSet attr)throws BadLocationException{
        super.insertString(offset, str.replaceAll("[^a-z|^A-Z|^0-9|^_|^$]", ""), attr);
    }
}

class ValidaCaracteres extends PlainDocument{
    int numCaracteres;
    
    public ValidaCaracteres(int numCaracteres)
    {
        this.numCaracteres = numCaracteres;
    }
    
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
        if(offset < this.numCaracteres)
            super.insertString(offset, str, attr);
    }
}

class ValidaEntradaComCaracteres extends PlainDocument{
    int numCaracteres;
    
    public ValidaEntradaComCaracteres(int numCaracteres)
    {
        this.numCaracteres = numCaracteres;
    }
    
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
        if(offset < this.numCaracteres)
            super.insertString(offset, str.replaceAll("[^a-z|^A-Z|^0-9|^_|^$]", ""), attr);
    }
}