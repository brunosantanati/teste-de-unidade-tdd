package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;

    @Before
    public void setUp() {
        this.leiloeiro = new Avaliador();
        System.out.println("inicializando teste!"); //O método anotado com Before é executado antes de cada teste da classe. 
    }
    
    @After
    public void finaliza() {
    	System.out.println("fim"); //Ao contrário do @Before, métodos anotados com @After são executados após a execução do método de teste.
    	
    	/*
    	 * Utilizamos métodos @After quando nossos testes consomem recursos que precisam ser finalizados. 
    	 * Exemplos podem ser testes que acessam banco de dados, abrem arquivos, abrem sockets, e etc.
		 * (Apesar desses testes não serem mais considerados testes de unidade, afinal eles falam com outros sistemas, 
		 * desenvolvedores utilizam JUnit para escrever testes de integração. 
		 * Os mesmos são discutidos no curso online de Testes de Integração).
    	 */
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        // cenario: 3 lances em ordem crescente
        Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(maria,250.0));
        leilao.propoe(new Lance(joao,300.0));
        leilao.propoe(new Lance(jose,400.0));

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double maiorEsperado = 400;
        double menorEsperado = 250;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }
    
    @Test
    public void deveCalcularAMedia() {
    	Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(maria,300.0));
        leilao.propoe(new Lance(joao,400.0));
        leilao.propoe(new Lance(jose,500.0));
        
        leiloeiro.avalia(leilao);
        
        assertEquals(400.0, leiloeiro.getMedia(), 0.0001);
    }
    
	@Test
	public void testaMediaDeZeroLance() {

		Leilao leilao = new Leilao("Iphone 7");

		leiloeiro.avalia(leilao);

		assertEquals(0, leiloeiro.getMedia(), 0.0001);
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Usuario joao = new Usuario("Joao"); 
        Leilao leilao = new Leilao("Playstation 3 Novo");
        
        leilao.propoe(new Lance(joao, 200.0));
        
        leiloeiro.avalia(leilao);
        
        assertEquals(200.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(200.0, leiloeiro.getMenorLance(), 0.0001);
	}
	
	@Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        Usuario joao = new Usuario("Joao"); 
        Usuario maria = new Usuario("Maria"); 
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao,200.0));
        leilao.propoe(new Lance(maria,450.0));
        leilao.propoe(new Lance(joao,120.0));
        leilao.propoe(new Lance(maria,700.0));
        leilao.propoe(new Lance(joao,630.0));
        leilao.propoe(new Lance(maria,230.0));

        leiloeiro.avalia(leilao);

        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
    }
	
	@Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        Usuario joao = new Usuario("Joao"); 
        Usuario maria = new Usuario("Maria"); 
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao,400.0));
        leilao.propoe(new Lance(maria,300.0));
        leilao.propoe(new Lance(joao,200.0));
        leilao.propoe(new Lance(maria,100.0));

        leiloeiro.avalia(leilao);

        assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
    }
	
    @Test
    public void deveEncontrarOsTresMaioresLances() {
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(400, maiores.get(0).getValor(), 0.00001);
        assertEquals(300, maiores.get(1).getValor(), 0.00001);
        assertEquals(200, maiores.get(2).getValor(), 0.00001);
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(2, maiores.size());
        assertEquals(200, maiores.get(0).getValor(), 0.00001);
        assertEquals(100, maiores.get(1).getValor(), 0.00001);
    }

    @Test
    public void deveDevolverListaVaziaCasoNaoHajaLances() {
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(0, maiores.size());
    }
}
