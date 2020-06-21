package br.com.caelum.matematica;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MatematicaMalucaTest {
	
	MatematicaMaluca matematicaMaluca;
	
	@Before
	public void init() {
		matematicaMaluca = new MatematicaMaluca();
	}
	
	@Test
	public void deveMultiplicarNumerosMaioresQue30(){
		int numero = 40;
		assertEquals(numero*4, matematicaMaluca.contaMaluca(numero));
	}

	@Test
	public void deveMultiplicarNumerosMaioresQue10EMenoresQue30(){
		int numero = 20;
		assertEquals(numero*3, matematicaMaluca.contaMaluca(numero));
	}
	
	@Test
	public void deveMultiplicarNumerosMenoresQue10() {
		int numero = 5;
		assertEquals(numero*2, matematicaMaluca.contaMaluca(numero));
	}
}
