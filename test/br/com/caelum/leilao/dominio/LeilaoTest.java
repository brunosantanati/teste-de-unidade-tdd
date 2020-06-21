package br.com.caelum.leilao.dominio;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeilaoTest {
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario(){
		Usuario joao = new Usuario("João");
		
		Leilao leilao = new Leilao("PS5");
		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(joao, 600.0));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(500.0, leilao.getLances().get(0).getValor(), 0.0001);
	}

}
