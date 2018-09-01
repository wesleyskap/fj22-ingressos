package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessoes {

	private List<Sessao> sessoesDaSala;

	public GerenciadorDeSessoes(List<Sessao> sessoesDaSala) {
		this.sessoesDaSala = sessoesDaSala;
	}

	private boolean conflitaHorario(Sessao sessao1, Sessao sessao2) {
		LocalDate hoje = LocalDate.now();
		LocalDateTime inicioSessao1 = sessao1.getHorario().atDate(hoje);
		LocalDateTime inicioSessao2 = sessao2.getHorario().atDate(hoje);
		boolean sessao1ComecaAntesDaSessao2 = inicioSessao1.isBefore(inicioSessao2);

		if (sessao1ComecaAntesDaSessao2) {
			LocalDateTime fimSessao1 = inicioSessao1.plus(sessao1.getFilme().getDuracao());
			return fimSessao1.isAfter(inicioSessao2);
		} else {
			LocalDateTime fimSessao2 = inicioSessao2.plus(sessao2.getFilme().getDuracao());
			return fimSessao2.isAfter(inicioSessao1);
		}

	}

	public boolean cabe(Sessao sessaoAtual) {
		return sessoesDaSala.stream().map(sessaoExistente -> !conflitaHorario(sessaoExistente, sessaoAtual)).reduce(Boolean::logicalAnd)
				.orElse(true);
	}

}
