package tseg.arquivo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FiltroExtencoes {

	private List<String> extencoes = Arrays.asList("txt","xml");
	private List<String> descricao = Arrays.asList("Documentos de Texto","Arquivos XML");

	public static String retornaExtencao(File CaminhoDoArquivo) {
		String nomeDoArquivo = CaminhoDoArquivo.getName();
		int comecoDaExtencao = nomeDoArquivo.lastIndexOf('.');
		String extencao = null;

		if (comecoDaExtencao > 0
				&& comecoDaExtencao < nomeDoArquivo.length() - 1) {
			extencao = nomeDoArquivo.substring(comecoDaExtencao + 1)
					.toLowerCase();
		}
		return extencao;
	}

        public void acceptsOnlyXML()
        {
            this.extencoes = Arrays.asList("xml");
            this.descricao = Arrays.asList("Arquivos XML");
        }
        
	public String getExtencao(int i) {
		return extencoes.get(i);
	}

	public String getDescricao(int i) {
		return descricao.get(i);
	}

	public int quantidadeExtencoes() {
		return extencoes.size() - 1;
	}

}
