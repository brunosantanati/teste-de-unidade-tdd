package br.com.caelum.anobissexto;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnoBissextoTest {
	
	@Test
	public void deveRetornarAnoBissexto() {        
	    AnoBissexto anoBissexto = new AnoBissexto();

	    assertEquals(true, anoBissexto.isAnoBissexto(2016));
	    assertEquals(true, anoBissexto.isAnoBissexto(2012));
	}

	@Test
	public void naoDeveRetornarAnoBissexto() {        
	    AnoBissexto anoBissexto = new AnoBissexto();        

	    assertEquals(false, anoBissexto.isAnoBissexto(2015));
	    assertEquals(false, anoBissexto.isAnoBissexto(2011));
	}

}
