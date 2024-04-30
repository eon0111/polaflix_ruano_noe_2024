package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.*;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios.*;

@Component
public class AppFeeder implements CommandLineRunner {
	
	@Autowired
	protected UsuarioRepository ur;

	@Autowired
	protected SerieRepository sr;

	@Autowired
	protected ReciboRepository rr;
	
	/* -- Series -- */
	private ArrayList<Serie> series;
	private Serie s1 = new Serie("Mr. Robot", CategoriaSerie.GOLD, "Mr. Robot is a techno thriller that follows Elliot, a young programmer...");;
	private Serie s2 = new Serie("Young Sheldon", CategoriaSerie.SILVER, "For 9-year-old Sheldon Cooper, being a once-in-a-generation mind...");;
	private Serie s3 = new Serie("The Office", CategoriaSerie.ESTANDAR, "In this US adaptation of iconic British sitcom `The Office'...");;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		feedUsuarios();
		feedSeries();

		series = new ArrayList<Serie>();
		series.add(s1); series.add(s2); series.add(s3);

		System.out.println("[*] Application feeded successfully!");
		System.out.println("\n==========================================");

		System.out.println(">>>>> Test SerieRepository >>>>>>>>>>>>>>>\n");
		testSerieRepository();
		System.out.println("\n==========================================");

		System.out.println(">>>>> Test UsuarioRepository >>>>>>>>>>>>>\n");
		testUsuarioRepository();
		System.out.println("\n==========================================");

		System.out.println(">>>>> Test Usuario (SIN cuota fija) >>>>>>\n");
		testUsuarioNormal();
		System.out.println("\n==========================================");

		System.out.println(">>>>> Test Usuario (CON cuota fija) >>>>>>\n");
		testUsuarioCuotaFija();
		System.out.println("\n==========================================");

		System.out.println(">>>>> Test ReciboRepository >>>>>>\n");
		testReciboRepository();
		System.out.println("\n==========================================");
	}

	/**
	 * Busca en todas las series registradas en el repositorio, un capítulo cuyo
	 * ID coincida con el indicado.
	 * @param id el ID del capítulo
	 * @return un capítulo cuyo ID coincide con el indicado, o null si no se
	 * encuentra ningún capítulo con ese ID
	 */
	private Capitulo getCapituloById(long id) {
		for (Serie s: series)
			for (Temporada t: s.getTemporadas())
				for (Capitulo c: t.getCapitulos().values())
					if (c.getId() == id) return c;

		return null;
	}

	/**
	 * Realiza una búsqueda lineal del capítulo en base al nombre de la serie y
	 * el índice de la temporada a las que pertenece.
	 * @param titulo el título de la serie
	 * @param indTemp el índice de la temporada
	 * @param indCap el índice del capítulo
	 * @return el capítulo buscado o null en caso de no encontrarse un capítulo
	 * con esas características
	 */
	private Capitulo findCapByTituloSerieAndIndTempAndIndCap(String titulo, int indTemp, int indCap) {
		for (Serie s: series)
			if (s.getTitulo().equals(titulo))
				for (Temporada t: s.getTemporadas())
					if (t.getIndice() == indTemp)
						for (Capitulo c: t.getCapitulos().values())
							if (c.getIndice() == indCap) return c;
		return null;
	}

	/****** TEST SerieRepository **********************************************/

	private void testSerieRepository() {
		testFindByTitulo("Mr. Robot", true);
		testFindByTitulo("Young Sheldon", true);
		testFindByTitulo("The Office", true);
		testFindByTitulo("invent", false);

		testAddRemoveSerie();
	}

	private void testFindByTitulo(String titulo, boolean esperado) {
		System.out.println("[*] Buscando serie \"" + titulo + "\"");
		Serie s = sr.findByTitulo(titulo);

		if (s != null && s.getTitulo().equals(titulo))
			System.out.println(((esperado) ? "-- PASS --" : "-- FAIL --"));
		else
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
	}

	/**
	 * Anhade una serie al repositorio, junto con una temporada y un capítulo, y
	 * comprueba que la eliminación de la serie provoca la eliminación en cascada
	 * de su temporada y el capítulo.
	 */
	void testAddRemoveSerie() {
		System.out.println("\n[*] Anhadiendo serie \"Test\"");

		/* Registra una nueva serie en el repositorio */
		Serie serieTest = new Serie("Test", CategoriaSerie.ESTANDAR, "empty");
		sr.save(serieTest);
		Temporada temporadaTest = new Temporada(1, serieTest);
		Capitulo capituloTest = new Capitulo("CapTest", "empty", "empty", temporadaTest, 1);

		serieTest.addTemporada(temporadaTest);
		temporadaTest.addCapitulo(capituloTest);

		/* Comprueba que la serie, sus temporadas y los capítulos de estas se han
		 * registrado correctamente */
		System.out.println("[*] Serie registrada: \"" + sr.findByTitulo("Test").getTitulo() + "\"");
		for (Temporada t: sr.findByTitulo("Test").getTemporadas()) {
			System.out.println("  >> Temporada " + t.getIndice() + ":");
			for (Capitulo c: t.getCapitulos().values()) {
				System.out.println("    -- Capitulo: " + c.getIndice() + "\": " + c.getTitulo() + "\"");
			}
		}

		/* Elimina la serie y comprueba si sus temporadas y capítulos han sido
		 * eliminados en cascada */
		sr.delete(serieTest);
		System.out.println("[*] Serie \"Test\" eliminada");

		System.out.println("[*] Serie \"Test\" " +
				((sr.findByTitulo("Test") == null) ? "no" : "") +
				" encontrada");
	}

	/****** TEST UsuarioRepository ********************************************/

	private void testUsuarioRepository() {
		testFindByNombre("pacoloco", true);
		testFindByNombre("lolaloca", true);
		testFindByNombre("invent", false);

		testAddRemoveUsuario();
	}

	private void testFindByNombre(String nombre, boolean esperado) {
		System.out.println("[*] Buscando usuario -> nombre: \"" + nombre + "\"");
		Usuario u = ur.findByNombre(nombre);

		if (u != null && u.getNombre().equals(nombre))
			System.out.println(((esperado) ? "-- PASS --" : "-- FAIL --"));
		else
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
	}

	private void testAddRemoveUsuario() {
		/* Anhade un nuevo usuario de test al repositorio */
		System.out.println("\n[*] Anhadiendo nuevo usuario \"pepega\"");
		Usuario u = new Usuario("pepega", "pepega1234", false, "ES1295137562464862179358");
		ur.save(u);

		/* Anhade una nueva serie de test al repositorio */
		System.out.println("[*] Anhadiendo nueva serie \"Test\"");
		Serie serieTest = new Serie("Test", CategoriaSerie.ESTANDAR, "empty");
		Temporada temporadaTest = new Temporada(1, serieTest);
		Capitulo capituloTest = new Capitulo("CapTest", "empty", "empty", temporadaTest, 1);

		temporadaTest.addCapitulo(capituloTest);
		serieTest.addTemporada(temporadaTest);
		sr.save(serieTest);
		series.add(serieTest);

		/* Registra la visualización del capítulo de la serie de test (la serie
		 * debería quedar registrada en la lista de series terminadas del usuario,
		 * al haber sido visualizado el último capítulo de la serie) */
		u.registraVisualizacion(capituloTest);

		/* Comprueba las visualizaciones y las series del usuario */
		muestraVisualizacionesUsuario(u);
		muestraSeriesUsuario(u);

		/* Elimina al usuario para comprobar que su eliminación no altera el
		 * estado del repositorio de series */
		System.out.println("[*] Eliminando usuario \"pepega\"");
		ur.delete(u);

		System.out.println("[*] Usuario \"pepega\" " +
				((ur.findByNombre("pepega") != null) ? "no" : "") +
				" eliminado");
		System.out.println("[*] Serie \"Test\" " +
				((sr.findByTitulo("Test") == null) ? "no" : "") +
				" encontrada");
	}

	/****** TEST Usuario ******************************************************/
	
	/**
	 * Muestra los recibos de un usuario.
	 * @param u El usuario
	 */
	private void muestraRecibosUsuario(Usuario u) {
		System.out.println("\n[*] Recibos del usuario \"" + u.getNombre() + "\":");

		Capitulo cTmp;
		Serie sTmp;
		CategoriaSerie catTmp;

		for (Recibo r: u.getRecibos()) {
			System.out.println("  >> Creado: " + r.getFechaCreacion().toString() +
					" |#| Emitido: " +
					((r.getFechaEmision() == null) ? "PENDIENTE" : r.getFechaEmision()) + 
					" |#| Importe: " + r.getImporte() + " EUR");

			for (Cargo c: r.getCargos()) {
				cTmp = getCapituloById(c.getIdCapitulo());
				sTmp = cTmp.getTemporada().getSerie();
				catTmp = sTmp.getCategoria();

				System.out.println("    - \"" + sTmp.getTitulo() + "\" (" +
						((catTmp == CategoriaSerie.ESTANDAR) ? "ESTANDAR" :
						((catTmp == CategoriaSerie.GOLD) ? "GOLD": "SILVER")) +
						") " + getCapituloById(c.getIdCapitulo()).getTitulo() +
						": " + c.getImporte() + " EUR");
			}
		}
	}

	/**
	 * Muestra los capítulos visualizados por un usuario.
	 * @param u el usuario
	 */
	private void muestraVisualizacionesUsuario(Usuario u) {
		System.out.println("\n[*] Visualizaciones del usuario \"" + u.getNombre() + "\":");
		Capitulo cTmp;
		for (long idCapitulo: u.getCapitulosVistos()) {
			cTmp = getCapituloById(idCapitulo);
			System.out.println("  >> \"" +
					cTmp.getTemporada().getSerie().getTitulo() + "\": \"" +
					cTmp.getTitulo() + "\"");
		}
	}

	/**
	 * Muestra las series empezadas, terminadas y pendientes de un usuario.
	 * @param u el usuario
	 */
	private void muestraSeriesUsuario(Usuario u) {
		System.out.println("\n[*] Series del usuario \"" + u.getNombre() + "\":");

		System.out.println("  >> Series terminadas:");
		for (Serie s: u.getSeriesTerminadas().values())
			System.out.println("    - \"" + s.getTitulo() + "\"");

		System.out.println("  >> Series pendientes:");
		for (Serie s: u.getSeriesPendientes().values())
			System.out.println("    - \"" + s.getTitulo() + "\"");

		System.out.println("  >> Series empezadas:");
		for (Serie s: u.getSeriesEmpezadas().values())
			System.out.println("    - \"" + s.getTitulo() + "\"");

		System.out.println("");
	}

	/**
	 * Comprueba el correcto funcionamiento de todas las funcionalidades
	 * implementadas en la clase Usuario.
	 */
	private void testUsuarioNormal() {
		Usuario u = ur.findByNombre("pacoloco");
		System.out.println("Usuario: \"" + u.getNombre() + "\"");

		/* Nueva visualización de un capítulo de una serie GOLD (sin cuota fija) */
		System.out.println("[*] Registrando visualizaciones: Mr. Robot - (01x01, 01x02, 01x03)");
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 1));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 2));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 3));

		/* Comprueba las visualizaciones y los recibos del usuario */
		muestraVisualizacionesUsuario(u);
		muestraRecibosUsuario(u);

		System.out.println("\n==========================================");

		/* Emite un recibo */
		System.out.println("\n[*] Emitiendo último recibo");
		u.addRecibo();

		/* Comprueba los recibos del usuario para ver que la fecha de emisión se
		 * registra correctamente */
		muestraRecibosUsuario(u);

		System.out.println("\n==========================================");

		/* Muestra las series del usuario antes de registrar la visualización
		 * del último capítulo de una de ellas */
		muestraSeriesUsuario(u);

		/* Registra visualizaciones de capítulos de una serie SILVER */
		System.out.println("\n[*] Registrando visualizaciones: Young Sheldon - (01x01, 02x01, 03x03)");
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Young Sheldon", 1, 1));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Young Sheldon", 2, 1));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Young Sheldon", 3, 3));

		/* Comprueba las visualizaciones y los recibos del usuario, así como sus
		 * series con objeto de comprobar si se ha registrado la serie como
		 * terminada, al haber sido visualizado su último capítulo */
		muestraVisualizacionesUsuario(u);
		muestraRecibosUsuario(u);
		muestraSeriesUsuario(u);

		System.out.println("\n==========================================");

		/* Anhade una serie pendiente */
		System.out.println("\n[*] Anhadiendo serie pendiente: \"The Office\"");
		u.addSeriePendiente(sr.findByTitulo("The Office"));
		muestraSeriesUsuario(u);

		System.out.println("\n==========================================");

		/* Comprueba la última temporada vista de cada serie */
		System.out.println("\n[*] Últimas temporadas vistas:");
		Temporada tTmp = u.getUltimaTemporadaSerie(s1.getId());
		System.out.println("  >> \"Mr. Robot (empezada)\": " + ((tTmp != null) ? tTmp.getIndice() : "NONE"));
		tTmp = u.getUltimaTemporadaSerie(s2.getId());
		System.out.println("  >> \"Young Sheldon (terminada)\": " + ((tTmp != null) ? tTmp.getIndice() : "NONE"));
		tTmp = u.getUltimaTemporadaSerie(s3.getId());
		System.out.println("  >> \"The Office (serie pendiente)\": " + ((tTmp != null) ? tTmp.getIndice() : "NONE"));
	}

	/**
	 * Comprueba el correcto funcionamiento del sistema de facturación para los
	 * usuarios suscritos a la cuota fija.
	 */
	private void testUsuarioCuotaFija() {
		Usuario u = ur.findByNombre("lolaloca");
		System.out.println("Usuario: \"" + u.getNombre() + "\"");

		/* Nueva visualización de un capítulo de una serie GOLD (con cuota fija) */
		System.out.println("[*] Registrando visualizaciones: Mr. Robot - (01x01, 01x02, 01x03)");
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 1));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 2));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 3));

		/* Comprueba las visualizaciones y los recibos del usuario */
		muestraVisualizacionesUsuario(u);
		muestraRecibosUsuario(u);

		System.out.println("\n==========================================");

		/* Emite un recibo */
		System.out.println("\n[*] Emitiendo último recibo");
		u.addRecibo();

		/* Comprueba los recibos del usuario para ver que la fecha de emisión se
		 * registra correctamente */
		muestraRecibosUsuario(u);

		System.out.println("\n==========================================");

		/* Registra visualizaciones de capítulos de una serie SILVER y genera
		 * un nuevo recibo */
		System.out.println("\n[*] Registrando visualizaciones: Young Sheldon - (01x01, 02x01, 03x03)");
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Young Sheldon", 1, 1));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Young Sheldon", 2, 1));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Young Sheldon", 3, 3));

		System.out.println("\n[*] Emitiendo último recibo");
		u.addRecibo();

		/* Comprueba las visualizaciones y los recibos del usuario, así como sus
		 * series con objeto de comprobar si se ha registrado la serie como
		 * terminada, al haber sido visualizado su último capítulo */
		muestraRecibosUsuario(u);
	}

	/****** TEST ReciboRepository ********************************************/
	private void testReciboRepository() {
		Usuario u = new Usuario("bongocat", "bongocat12345", false, "ES1342134213423142134200");
		System.out.println("\nRegistrando nuevo usuario \"bongocat\"");
		ur.save(u);

		System.out.println("[*] Registrando visualizaciones: \"Mr. Robot\" (01x01, 01x02, 01x03)");
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 1));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 2));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 3));

		System.out.println("[*] Emitiendo recibo");
		u.addRecibo();
		muestraRecibosUsuario(u);

		System.out.println("[*] Registrando visualizaciones: \"The Office\" (01x01, 01x02, 01x03)");
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("The Office", 1, 1));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("The Office", 1, 2));
		u.registraVisualizacion(findCapByTituloSerieAndIndTempAndIndCap("The Office", 1, 3));

		System.out.println("[*] Emitiendo recibo");
		u.addRecibo();
		muestraRecibosUsuario(u);

		System.out.println("[*] Eliminando usuario \"bongocat\"");
		ur.delete(u);
		System.out.println("[*] Usuario \"bongocat\" " +
				((ur.findByNombre("bongocat") != null) ? "no" : "") +
				" eliminado");
		
		System.out.println("[*] Recibos registrados:");
		Capitulo cap;
		for (Recibo r: rr.findAll()) {
			if (r.getFechaEmision() != null) {
				System.out.println(">> Fecha: " + r.getFechaEmision().toString());
				for (Cargo c: r.getCargos()) {
					cap = getCapituloById(c.getIdCapitulo());
					System.out.println("    -- " + cap.getTemporada().getSerie().getTitulo() +
									   "\" (" + cap.getTitulo() + "): " + c.getImporte() +
									   " EUR");
				}
			}
		}
	}

	/****** FEED usuarios *****************************************************/
	private void feedUsuarios() {
		Usuario u1 = new Usuario("pacoloco", "pacoloco12345", false, "ES1234123412341234123400");
		Usuario u2 = new Usuario("lolaloca", "lolaloca12345", true, "ES5687568756785687567800");
		ur.save(u1);
		ur.save(u2);
	}

	/****** FEED series *******************************************************/
	private void feedSeries() {
		/* -- Temporadas -- */
		Temporada t1s1 = new Temporada(1, s1);
		Temporada t2s1 = new Temporada(2, s1);
		Temporada t3s1 = new Temporada(3, s1);

		Temporada t1s2 = new Temporada(1, s2);
		Temporada t2s2 = new Temporada(2, s2);
		Temporada t3s2 = new Temporada(3, s2);

		Temporada t1s3 = new Temporada(1, s3);
		Temporada t2s3 = new Temporada(2, s3);
		Temporada t3s3 = new Temporada(3, s3);

		/* -- Capitulos@s1 -- */
		Capitulo c1t1s1 = new Capitulo("01x01", "URLc1t1s1", "DESCc1t1s1", t1s1, 1);
		Capitulo c2t1s1 = new Capitulo("01x02", "URLc2t1s1", "DESCc2t1s1", t1s1, 2);
		Capitulo c3t1s1 = new Capitulo("01x03", "URLc3t1s1", "DESCc3t1s1", t1s1, 3);
		
		Capitulo c1t2s1 = new Capitulo("02x01", "URLc1t2s1", "DESCc1t2s1", t2s1, 1);
		Capitulo c2t2s1 = new Capitulo("02x02", "URLc2t2s1", "DESCc2t2s1", t2s1, 2);
		Capitulo c3t2s1 = new Capitulo("02x03", "URLc3t2s1", "DESCc3t2s1", t2s1, 3);

		Capitulo c1t3s1 = new Capitulo("03x01", "URLc1t3s1", "DESCc1t3s1", t3s1, 1);
		Capitulo c2t3s1 = new Capitulo("03x02", "URLc2t3s1", "DESCc2t3s1", t3s1, 2);
		Capitulo c3t3s1 = new Capitulo("03x03", "URLc3t3s1", "DESCc3t3s1", t3s1, 3);

		/* -- Capitulos@s2 -- */
		Capitulo c1t1s2 = new Capitulo("01x01", "URLc1t1s2", "DESCc1t1s2", t1s2, 1);
		Capitulo c2t1s2 = new Capitulo("01x02", "URLc2t1s2", "DESCc2t1s2", t1s2, 2);
		Capitulo c3t1s2 = new Capitulo("01x03", "URLc3t1s2", "DESCc3t1s2", t1s2, 3);

		Capitulo c1t2s2 = new Capitulo("02x01", "URLc1t2s2", "DESCc1t2s2", t2s2, 1);
		Capitulo c2t2s2 = new Capitulo("02x02", "URLc2t2s2", "DESCc2t2s2", t2s2, 2);
		Capitulo c3t2s2 = new Capitulo("02x03", "URLc3t2s2", "DESCc3t2s2", t2s2, 3);

		Capitulo c1t3s2 = new Capitulo("03x01", "URLc1t3s2", "DESCc1t3s2", t3s2, 1);
		Capitulo c2t3s2 = new Capitulo("03x02", "URLc2t3s2", "DESCc2t3s2", t3s2, 2);
		Capitulo c3t3s2 = new Capitulo("03x03", "URLc3t3s2", "DESCc3t3s2", t3s2, 3);

		/* -- Capitulos@s3 -- */
		Capitulo c1t1s3 = new Capitulo("01x01", "URLc1t1s3", "DESCc1t1s3", t1s3, 1);
		Capitulo c2t1s3 = new Capitulo("01x02", "URLc2t1s3", "DESCc2t1s3", t1s3, 2);
		Capitulo c3t1s3 = new Capitulo("01x03", "URLc3t1s3", "DESCc3t1s3", t1s3, 3);

		Capitulo c1t2s3 = new Capitulo("02x01", "URLc1t2s3", "DESCc1t2s3", t2s3, 1);
		Capitulo c2t2s3 = new Capitulo("02x02", "URLc2t2s3", "DESCc2t2s3", t2s3, 2);
		Capitulo c3t2s3 = new Capitulo("02x03", "URLc3t2s3", "DESCc3t2s3", t2s3, 3);

		Capitulo c1t3s3 = new Capitulo("03x01", "URLc1t3s3", "DESCc1t3s3", t3s3, 1);
		Capitulo c2t3s3 = new Capitulo("03x02", "URLc2t3s3", "DESCc2t3s3", t3s3, 2);
		Capitulo c3t3s3 = new Capitulo("03x03", "URLc3t3s3", "DESCc3t3s3", t3s3, 3);

		/* Anhade las temporadas a las series  */
		s1.addTemporada(t1s1); s1.addTemporada(t2s1); s1.addTemporada(t3s1);
		s2.addTemporada(t1s2); s2.addTemporada(t2s2); s2.addTemporada(t3s2);
		s3.addTemporada(t1s3); s3.addTemporada(t2s3); s3.addTemporada(t3s3);

		/* Anhade los capítulos a las temporadas */
		t1s1.addCapitulo(c1t1s1); t1s1.addCapitulo(c2t1s1); t1s1.addCapitulo(c3t1s1);
		t2s1.addCapitulo(c1t2s1); t2s1.addCapitulo(c2t2s1); t2s1.addCapitulo(c3t2s1);
		t3s1.addCapitulo(c1t3s1); t3s1.addCapitulo(c2t3s1); t3s1.addCapitulo(c3t3s1);
		
		t1s2.addCapitulo(c1t1s2); t1s2.addCapitulo(c2t1s2); t1s2.addCapitulo(c3t1s2);
		t2s2.addCapitulo(c1t2s2); t2s2.addCapitulo(c2t2s2); t2s2.addCapitulo(c3t2s2);
		t3s2.addCapitulo(c1t3s2); t3s2.addCapitulo(c2t3s2); t3s2.addCapitulo(c3t3s2);

		t1s3.addCapitulo(c1t1s3); t1s3.addCapitulo(c2t1s3); t1s3.addCapitulo(c3t1s3);
		t2s3.addCapitulo(c1t2s3); t2s3.addCapitulo(c2t2s3); t2s3.addCapitulo(c3t2s3);
		t3s3.addCapitulo(c1t3s3); t3s3.addCapitulo(c2t3s3); t3s3.addCapitulo(c3t3s3);

		/* Registro de las series en la BD */
		sr.save(s1); sr.save(s2); sr.save(s3);
	}
}
