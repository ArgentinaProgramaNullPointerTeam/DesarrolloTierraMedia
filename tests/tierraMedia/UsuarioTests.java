package tierraMedia;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UsuarioTests {

	Usuario usuario, u1;
	Atraccion a1, a2, a3, a4;
	Promocion p1, p2, p3, p4;
	Producto sugerencia1, sugerencia2, sugerencia3;

	@Before
	public void setup() {
		usuario = new Usuario("Sam", TipoAtraccion.AVENTURA, 50, 3);
		u1 = new Usuario("pepito", TipoAtraccion.AVENTURA, 100, 100);

		a1 = new Atraccion("a1", 10, 3, 20, TipoAtraccion.AVENTURA);
		a2 = new Atraccion("a2", 4, 2.5, 20, TipoAtraccion.AVENTURA);
		a3 = new Atraccion("a3", 2, 1, 20, TipoAtraccion.AVENTURA);
		a4 = new Atraccion("a4", 8, 4, 20, TipoAtraccion.AVENTURA);

		List<Atraccion> packUno = new ArrayList<Atraccion>();
		packUno.add(a1);
		packUno.add(a2);
		p1 = new PromocionAbsoluta("Pack uno", 2, packUno, "Absoluta", "10");

		List<Atraccion> packDos = new ArrayList<Atraccion>();
		packDos.add(a1);
		packDos.add(a3);
		p2 = new PromocionPorcentual("Pack dos", 2, packDos, "Porcentual", "0.3");

		List<Atraccion> packTres = new ArrayList<Atraccion>();
		packTres.add(a1);
		packTres.add(a3);
		packTres.add(a2);
		p3 = new PromocionAXB("Pack tres", 3, packTres, "AXB", "a3");

		List<Atraccion> packCuatro = new ArrayList<Atraccion>();
		packCuatro.add(a2);
		packCuatro.add(a4);
		p4 = new PromocionAbsoluta("Pack Cuatro", 2, packCuatro, "Absoluta", "5");
		
		sugerencia1 = new Atraccion("Edoras", 5, 0.5, 2, TipoAtraccion.AVENTURA);
		sugerencia2 = new Atraccion("Isengard", 5, 1, 2, TipoAtraccion.AVENTURA);
		sugerencia3 = new Atraccion("Rivendel", 10, 1, 2, TipoAtraccion.AVENTURA);
	}

	@After
	public void tearDown() {
		usuario = null;
		u1 = null;
		a1 = null;
		a2 = null;
		a3 = null;
		a4 = null;
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
		sugerencia1 = null;
		sugerencia2 = null;
		sugerencia3 = null;
	}

	@Test
	public void crearUsuarioTest() {
		assertNotNull(usuario);
	}

	@Test
	public void comprarTestDinero() {
		//Verifica que al comprar, descuente bien el dinero y el tiempo.
		Producto sugerenciaAceptada = new Atraccion("Rivendel", 20, 1.5, 2, TipoAtraccion.AVENTURA);
		usuario.comprar(sugerenciaAceptada);
		int dineroDispObtenido = usuario.getDineroDisponible();
		int dineroDispEsperado = 30;
		
		assertEquals(dineroDispEsperado, dineroDispObtenido);
	}
	@Test
	public void comprarTestTiempo() {
		//Verifica que al comprar, descuente bien el dinero y el tiempo.
		Producto sugerenciaAceptada = new Atraccion("Rivendel", 20, 1.5, 2, TipoAtraccion.AVENTURA);
		usuario.comprar(sugerenciaAceptada);
		double tiempoDispObtenido = usuario.getTiempoDisponible();
		double tiempoDispEsperado = 1.5;

		assertEquals(tiempoDispEsperado, tiempoDispObtenido, 0);
	}
	@Test
	public void puedeComprarTest() {
		//Verifica que el usuario pueda comprar una sugerencia.
		Producto sugerencia = new Atraccion("Hobbiton", 10, 0.5, 2, TipoAtraccion.AVENTURA);
		assertTrue(usuario.puedeComprar(sugerencia));
	}

	@Test
	public void noPuedeComprarTest() { // El usuario no tiene suficiente dinero
		Producto sugerencia = new Atraccion("Edoras", 60, 0.5, 2, TipoAtraccion.AVENTURA);
		assertFalse(usuario.puedeComprar(sugerencia));
	}

	@Test
	public void noPuedeComprarTest2() {// El usuario no tiene suficiente tiempo
		Producto sugerencia = new Atraccion("Isengard", 5, 4, 2, TipoAtraccion.AVENTURA);
		assertFalse(usuario.puedeComprar(sugerencia));
	}

	@Test
	public void gastoCorrectoTiempo() {
		//Verifica el gasto correcto de tiempo y dinero.
		usuario.comprar(sugerencia1);
		usuario.comprar(sugerencia2);
		usuario.comprar(sugerencia3);
		assertEquals(2.5, usuario.getTiempoGastado(), 0);
	}
	@Test
	public void gastoCorrectoMonedas() {
		//Verifica el gasto correcto de tiempo y dinero.
		usuario.comprar(sugerencia1);
		usuario.comprar(sugerencia2);
		usuario.comprar(sugerencia3);
		assertEquals(20, usuario.getMonedasGastadas());
	}

	@Test
	public void atraccionEnPromocion() {
		//Verifica que no pueda comprar atracciones o promociones repetidas.
		// Test nro1 - No puede comprar una atraccion que ya este en una promocion
		u1.comprar(p1);
		assertFalse(u1.puedeComprar(a2));
	}
	@Test
	public void atraccionesDelMismoTipo() {
		//Verifica que no pueda comprar atracciones o promociones repetidas.
		// Test nro1.5 (opcional si una promocion solo tiene atracciones del mismo tipo)
		u1.comprar(a1);
		assertFalse(u1.puedeComprar(p2));
	}
	@Test
	public void atraccionYaComprada() {
		//Verifica que no pueda comprar atracciones o promociones repetidas.
		// Test nro2 - No puede comprar una promocion que tenga una atraccion que ya
		// haya comprado
		u1.comprar(p1);
		assertFalse(u1.puedeComprar(p2));
	}
	@Test
	public void atraccionYaComprada2() {
		//Verifica que no pueda comprar atracciones o promociones repetidas.
		// Test nro3 - No puede comprar una promocion que tenga una atraccion que ya
		// haya comprado
		u1.comprar(p3);
		assertFalse(u1.puedeComprar(p4));
	}
}
