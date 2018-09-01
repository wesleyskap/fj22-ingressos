package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;

public class DescontoTest {

	private Filme filme;
	private Sala sala;
	private Sessao sessao;
	private Lugar lugar;
	
	@Before
	public void criaFilmeESala(){
		this.filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
		this.sala = new Sala("Eldorado - IMax", new BigDecimal("20.5"));
		this.sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
		this.lugar = new Lugar("A",1);
	}

	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO, lugar);
		
		BigDecimal precoEsperado = sala.getPreco().add(filme.getPreco());
		Assert.assertEquals( precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void naoDeveConcederDescontoDe30PorcentoParaIngressosDeClientesDeBancos() {
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.BANCO, lugar);
		
		BigDecimal precoEsperado = new BigDecimal("22.75");
		Assert.assertEquals( precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void naoDeveConcederDescontoDe50PorcentoParaIngressosDeEstudante() {
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.ESTUDANTE, lugar);
		
		BigDecimal precoEsperado = new BigDecimal("16.25");
		Assert.assertEquals( precoEsperado, ingresso.getPreco());
	}

}
