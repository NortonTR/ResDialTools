package tseg.segmentacao;

import java.util.ArrayList;

import tseg.controle.Controle;

public class SegmentadorUtil {
	public int adicionaUnidade(int selecaoInicio, int selecaoFim,
			boolean independente, StringBuilder textoSegmentacao) {
		ArrayList<Unidade> listaNovasUnidades = Controle
				.getControladorUnidades().criaUnidade(selecaoInicio,
						selecaoFim, independente);

		textoSegmentacao.insert(selecaoInicio, listaNovasUnidades.get(0)
				.getIdentificador());

		textoSegmentacao.insert(selecaoFim
				+ listaNovasUnidades.get(0).getIdentificador().length(),
				listaNovasUnidades.get(1).getIdentificador());

		return (selecaoFim
				+ listaNovasUnidades.get(0).getIdentificador().length()
				+ listaNovasUnidades.get(1).getIdentificador().length() + 1);
	}

	public static boolean verificaForaDeTag(int selecaoInicio, int selecaoFim) {
		ControladorUnidades controladorUnidades = Controle
				.getControladorUnidades();
		ArrayList<Unidade> unidadesInicio = controladorUnidades
				.getUnidadesInicio();

		for (int i = 0; i < unidadesInicio.size(); i++) {
			Unidade unidade = unidadesInicio.get(i);

			if (selecaoInicio > unidade.getPosicao()
					&& selecaoInicio < unidade.getPosicao()
							+ unidade.getIdentificador().length()) {
				return false;
			}

			if (selecaoFim > unidade.getPosicao()
					&& selecaoFim < unidade.getPosicao()
							+ unidade.getIdentificador().length()) {
				return false;
			}

			ArrayList<Unidade> unidadesFim = controladorUnidades
					.getUnidadesFim();

			unidade = unidadesFim.get(i);

			if (selecaoInicio > unidade.getPosicao()
					&& selecaoInicio < unidade.getPosicao()
							+ unidade.getIdentificador().length() - 1) {
				return false;
			}

			if (selecaoFim > unidade.getPosicao()
					&& selecaoFim < unidade.getPosicao()
							+ unidade.getIdentificador().length()) {
				return false;
			}
		}

		return true;
	}
        
        public static boolean verificaForaTagDocument(int selecaoInicio, int selecaoFim) {
                String texto = Controle.getJanelaPrincipal().getAbaSegmentaca()
                                .getTextoTxaSegmentacao();

                ControladorUnidades controladorUnidades = Controle
                                .getControladorUnidades();

                String tagDocumentInicio = controladorUnidades
                                .getTagDocumentInicio();
                String tagDocumentFim = controladorUnidades.getTagDocumentFim();

                if ((selecaoInicio < tagDocumentInicio.length() + 1)
                                || (selecaoInicio > texto.length()
                                                - tagDocumentFim.length() - 1)) {
                        return false;
                }

                if (selecaoFim > texto.length() - tagDocumentFim.length() - 1) {
                        return false;
                }
                return true;
        }

}
