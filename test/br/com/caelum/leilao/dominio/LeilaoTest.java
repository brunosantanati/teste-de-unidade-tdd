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

	
	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("PS5");
		
		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 600.0));
		
		leilao.propoe(new Lance(joao, 700.0));
		leilao.propoe(new Lance(maria, 800.0));
		
		leilao.propoe(new Lance(joao, 900.0));
		leilao.propoe(new Lance(maria, 1000.0));
		
		leilao.propoe(new Lance(joao, 1100.0));
		leilao.propoe(new Lance(maria, 1200.0));
		
		leilao.propoe(new Lance(joao, 1300.0));
		leilao.propoe(new Lance(maria, 1400.0));
		
		//deve ignorar esse lance
		leilao.propoe(new Lance(joao, 1500.0));
		
		assertEquals(10, leilao.getLances().size());
		int ultimo = leilao.getLances().size() - 1;
        assertEquals(1400.0, leilao.getLances().get(ultimo).getValor(), 0.00001);
	}
	
	@Test
	public void deveDobrarOUltimoLanceDado() {
		Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.dobraLance(steveJobs);

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
	}
	
    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");

        leilao.dobraLance(steveJobs);

        assertEquals(0, leilao.getLances().size());
    }	
	
}
