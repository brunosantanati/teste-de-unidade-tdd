package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	private Usuario maria;
    private Usuario joao;
    
    //Download Hamcrest: https://code.google.com/archive/p/hamcrest/downloads
    //Doc Hamcrest: https://code.google.com/archive/p/hamcrest/wikis/Tutorial.wiki

    @Before
    public void setUp() {
        this.leiloeiro = new Avaliador();
        
        this.joao = new Usuario("João");
        this.maria = new Usuario("Maria");
        
        //System.out.println("inicializando teste!"); //O método anotado com Before é executado antes de cada teste da classe. 
    }
    
    @After
    public void finaliza() {
    	//System.out.println("fim"); //Ao contrário do @Before, métodos anotados com @After são executados após a execução do método de teste.
    	
    	/*
    	 * Utilizamos métodos @After quando nossos testes consomem recursos que precisam ser finalizados. 
    	 * Exemplos podem ser testes que acessam banco de dados, abrem arquivos, abrem sockets, e etc.
		 * (Apesar desses testes não serem mais considerados testes de unidade, afinal eles falam com outros sistemas, 
		 * desenvolvedores utilizam JUnit para escrever testes de integração. 
		 * Os mesmos são discutidos no curso online de Testes de Integração).
    	 */
    }
    
    @BeforeClass
    public static void testandoBeforeClass() {
		//Métodos anotados com @BeforeClass são executados apenas uma vez, antes de todos os métodos de teste.
		//System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
    	//O método anotado com @AfterClass, por sua vez, é executado uma vez, após a execução do último método de teste da classe.
    	//System.out.println("after class");
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
    		.lance(new Usuario("João"), 250.0)
    		.lance(new Usuario("José"), 300.0)
    		.lance(new Usuario("Maria"), 400.0)
    		.constroi();

        leiloeiro.avalia(leilao);

        double maiorEsperado = 400;
        double menorEsperado = 250;

        assertThat(leiloeiro.getMaiorLance(), equalTo(maiorEsperado));
        assertThat(leiloeiro.getMenorLance(), equalTo(menorEsperado));
    }
    
    @Test
    public void deveCalcularAMedia() {
    	
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
    		.lance(new Usuario("Maria"), 300.0)
    		.lance(new Usuario("João"), 400.0)
    		.lance(new Usuario("José"), 500.0)
    		.constroi();
        
        leiloeiro.avalia(leilao);
        
        assertThat(leiloeiro.getMedia(), equalTo(400.00));
    }
    
//	@Test
//	public void testaMediaDeZeroLance() {
//
//		Leilao leilao = new Leilao("Iphone 7");
//
//		leiloeiro.avalia(leilao);
//
//		assertEquals(0, leiloeiro.getMedia(), 0.0001);
//	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
        
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
    		.lance(new Usuario("João"), 200.0)
    		.constroi();
        
        leiloeiro.avalia(leilao);
        
        assertThat(leiloeiro.getMaiorLance(), equalTo(200.00));
        assertThat(leiloeiro.getMenorLance(), equalTo(200.00));
	}
	
	@Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
    		.lance(joao, 200.0)
    		.lance(maria, 450.0)
    		.lance(joao, 120.0)
    		.lance(maria, 700.0)
    		.lance(joao, 630.0)
    		.lance(maria, 230.0)
    		.constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMaiorLance(), equalTo(700.00));
        assertThat(leiloeiro.getMenorLance(), equalTo(120.00));
    }
	
	@Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
    		.lance(joao, 400.0)
    		.lance(maria, 300.0)
    		.lance(joao, 200.0)
    		.lance(maria, 100.0)
    		.constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMaiorLance(), equalTo(400.00));
        assertThat(leiloeiro.getMenorLance(), equalTo(100.00));
    }
	
    @Test
    public void deveEncontrarOsTresMaioresLances() {
        
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
    		.lance(joao, 100.0)
    		.lance(maria, 200.0)
    		.lance(joao, 300.0)
    		.lance(maria, 400.0)
    		.constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertThat(maiores.size(), equalTo(3));
        assertThat(maiores, hasItems(
            new Lance(maria, 400), 
            new Lance(joao, 300),
            new Lance(maria, 200)
        ));
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
        
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
    		.lance(new Usuario("João"), 100.0)
    		.lance(new Usuario("Maria"), 200.0)
    		.constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertThat(maiores.size(), equalTo(2));
        assertThat(maiores.get(0).getValor(), equalTo(200.00));
        assertThat(maiores.get(1).getValor(), equalTo(100.00));
    }

//    @Test
//    public void deveDevolverListaVaziaCasoNaoHajaLances() {
//        Leilao leilao = new Leilao("Playstation 3 Novo");
//
//        leiloeiro.avalia(leilao);
//
//        List<Lance> maiores = leiloeiro.getTresMaiores();
//
//        assertEquals(0, maiores.size());
//    }
    
    @Test(expected=RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
        Leilao leilao = new CriadorDeLeilao()
            .para("Playstation 3 Novo")
            .constroi();

        leiloeiro.avalia(leilao);
    }
}
