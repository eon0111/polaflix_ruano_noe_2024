package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.util.ArrayList;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.*;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios.*;

@Transactional
@Component
public class AppFeeder implements CommandLineRunner {
	
	@Autowired
	protected UsuarioRepository ur;

	@Autowired
	protected SerieRepository sr;
	
	@Autowired
	protected TemporadaRepository tr;
	
	@Autowired
	protected CapituloRepository cr;

	/* -- Series -- */
	private Serie s1 = new Serie("Mr. Robot", CategoriaSerie.GOLD, "Mr. Robot is a techno thriller that follows Elliot, a young programmer...");
	private Serie s2 = new Serie("Young Sheldon", CategoriaSerie.SILVER, "For 9-year-old Sheldon Cooper, being a once-in-a-generation mind...");
	private Serie s3 = new Serie("The Office", CategoriaSerie.ESTANDAR, "In this US adaptation of iconic British sitcom `The Office'...");

	@Override
	public void run(String... args) throws Exception {
		feedUsuarios();
		feedSeries();

		System.out.println("[*] Application feeded successfully!");
		System.out.println("\n==========================================");

		System.out.println(">>>>> Test SerieRepository >>>>>>>>>>>>>>>\n");
		testSerieRepository();
		System.out.println("\n==========================================");
/*
		System.out.println(">>>>> Test TemporadaRepository >>>>>>>>>>>\n");
		testTemporadaRepository();
		System.out.println("\n==========================================");

		System.out.println(">>>>> Test CapituloRepository >>>>>>>>>>>>\n");
		testCapituloRepository();
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
*/
	}

	/****** TEST SerieRepository **********************************************/

	private void testSerieRepository() {
		/*
		testFindByTitulo("Mr. Robot", true);
		testFindByTitulo("Young Sheldon", true);
		testFindByTitulo("The Office", true);
		testFindByTitulo("invent", false);
		*/

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

	void testAddRemoveSerie() {
		System.out.println("[*] Anhadiendo serie \"Test\"");

		/* Registra una nueva serie en el repositorio */
		Serie serieTest = new Serie("Test", CategoriaSerie.ESTANDAR, "empty");
		sr.save(serieTest);
		Temporada temporadaTest = new Temporada(1, serieTest);
		tr.save(temporadaTest);
		Capitulo capituloTest = new Capitulo("CapTest", "empty", "empty", temporadaTest, 1);
		cr.save(capituloTest);

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

		// TODO: eliminar la serie y ver cómo quedan los repositorios de series, temporadas y capítulos
	}

	/****** TEST TemporadaRepository ******************************************/

	private void testTemporadaRepository() {
		/* findByIdSerieAndIndice */
		testFindByIdSerieAndIndice(1, 1, true);
		testFindByIdSerieAndIndice(1, 4, false);	// Índice por encima del rango [0,3]
		testFindByIdSerieAndIndice(1, -1, false);	// Índice por debajo del rango [0,3]
		testFindByIdSerieAndIndice(-1, 1, false);	// ID no válido
		testFindByIdSerieAndIndice(-1, 10, false);	// ID e índice no válidos

		/* findByIdSerieAndIndiceTemporada */
		testFindByTituloSerieAndIndice("Mr. Robot", 1, true);
		testFindByTituloSerieAndIndice("Mr. Robot", 4, false);
		testFindByTituloSerieAndIndice("Mr. Robot", -1, false);
		testFindByTituloSerieAndIndice("Invent", 1, false);
		testFindByTituloSerieAndIndice("Invent", -1, false);
	}

	private void testFindByIdSerieAndIndice(long idSerie, int indice, boolean esperado) {
		System.out.println("[*] Buscando temporada -> idSerie: " + idSerie + "\tindice: " + indice);
		Temporada t = tr.findByIdSerieAndIndice(idSerie, indice);

		if (t != null && t.getSerie().getId() == idSerie && t.getIndice() == indice)
			System.out.println(((esperado) ? "-- PASS --" : "-- FAIL --"));
		else
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
	}

	private void testFindByTituloSerieAndIndice(String titulo, int indice, boolean esperado) {
		System.out.println("[*] Buscando temporada -> serie: \"" + titulo + "\"\tindice: " + indice);
		Temporada t = tr.findByTituloSerieAndIndice(titulo, indice);

		if (t != null && t.getSerie().getTitulo().equals(titulo) && t.getIndice() == indice)
			System.out.println(((esperado) ? "-- PASS --" : "-- FAIL --"));
		else
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
	}

	/****** TEST CapituloRepository *******************************************/

	private void testCapituloRepository() {
		/* finByTituloSerieAndIndTemp */
		testFindByTituloSerieAndIndTemp("Mr. Robot", 1, true);
		testFindByTituloSerieAndIndTemp("Mr. Robot", 4, false);
		testFindByTituloSerieAndIndTemp("Mr. Robot", -1, false);
		testFindByTituloSerieAndIndTemp("Invent", 1, false);
		testFindByTituloSerieAndIndTemp("Invent", -1, false);

		/* finByTituloSerieAndIndTempAndIndCap */
		testFindByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 1, true);
		testFindByTituloSerieAndIndTempAndIndCap("Mr. Robot", -1, 1, false);
		testFindByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, -1, false);
		testFindByTituloSerieAndIndTempAndIndCap("Invent", 1, 1, false);
	}

	private void testFindByTituloSerieAndIndTemp(String titulo, int indice, boolean esperado) {
		System.out.println("[*] Buscando capitulos -> serie: \"" + titulo + "\"\tindice: " + indice);
		ArrayList<Capitulo> cs = cr.findByTituloSerieAndIndTemp(titulo, indice);

		if (cs.isEmpty())
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
		else if (esperado)
			for(Capitulo c: cs)
				System.out.println("-- Titulo: \"" + c.getTitulo() + "\"");
		else
			System.out.println("-- FAIL --");
	}

	private void testFindByTituloSerieAndIndTempAndIndCap(String titulo, int indTemp,
			int indCap, boolean esperado) {
		System.out.println("[*] Buscando capitulos -> serie: \"" + titulo +
				"\"\ttemporada: " + indTemp + "\tcapitulo: " + indCap);
		Capitulo c = cr.findByTituloSerieAndIndTempAndIndCap(titulo, indTemp, indCap);

		if (c != null && c.getTemporada().getIndice() == indTemp &&
				c.getTemporada().getSerie().getTitulo().equals(titulo))
			System.out.println(((esperado) ? "-- PASS --" : "-- FAIL --"));
		else
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
	}

	/****** TEST UsuarioRepository *******************************************/

	private void testUsuarioRepository() {
		testFindByNombre("pacoloco", true);
		testFindByNombre("lolaloca", true);
		testFindByNombre("invent", false);
	}

	private void testFindByNombre(String nombre, boolean esperado) {
		System.out.println("[*] Buscando usuario -> nombre: \"" + nombre + "\"");
		Usuario u = ur.findByNombre(nombre);

		if (u != null && u.getNombre().equals(nombre))
			System.out.println(((esperado) ? "-- PASS --" : "-- FAIL --"));
		else
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
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
				cTmp = cr.getReferenceById(c.getIdCapitulo());
				sTmp = cTmp.getTemporada().getSerie();
				catTmp = sTmp.getCategoria();

				System.out.println("    - \"" + sTmp.getTitulo() + "\" (" +
						((catTmp == CategoriaSerie.ESTANDAR) ? "ESTANDAR" :
						((catTmp == CategoriaSerie.GOLD) ? "GOLD": "SILVER")) +
						") " + cr.getReferenceById(c.getIdCapitulo()).getTitulo() +
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
			cTmp = cr.getReferenceById(idCapitulo);
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
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 1));
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 2));
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 3));

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
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Young Sheldon", 1, 1));
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Young Sheldon", 2, 1));
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Young Sheldon", 3, 3));

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
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 1));
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 2));
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Mr. Robot", 1, 3));

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
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Young Sheldon", 1, 1));
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Young Sheldon", 2, 1));
		u.registraVisualizacion(cr.findByTituloSerieAndIndTempAndIndCap("Young Sheldon", 3, 3));

		System.out.println("\n[*] Emitiendo último recibo");
		u.addRecibo();

		/* Comprueba las visualizaciones y los recibos del usuario, así como sus
		 * series con objeto de comprobar si se ha registrado la serie como
		 * terminada, al haber sido visualizado su último capítulo */
		muestraRecibosUsuario(u);
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
		/* Registro de las series en la BD */
		sr.save(s1); sr.save(s2); sr.save(s3);

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

		/* Registro de las temporadas en la BD */
		tr.save(t1s1); tr.save(t2s1); tr.save(t3s1);
		tr.save(t1s2); tr.save(t2s2); tr.save(t3s2);
		tr.save(t1s3); tr.save(t2s3); tr.save(t3s3);

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

		/* Registro de los capítulos en la BD */
		cr.save(c1t1s1); cr.save(c2t1s1); cr.save(c3t1s1);
		cr.save(c1t2s1); cr.save(c2t2s1); cr.save(c3t2s1);
		cr.save(c1t3s1); cr.save(c2t3s1); cr.save(c3t3s1);

		cr.save(c1t1s2); cr.save(c2t1s2); cr.save(c3t1s2);
		cr.save(c1t2s2); cr.save(c2t2s2); cr.save(c3t2s2);
		cr.save(c1t3s2); cr.save(c2t3s2); cr.save(c3t3s2);

		cr.save(c1t1s3); cr.save(c2t1s3); cr.save(c3t1s3);
		cr.save(c1t2s3); cr.save(c2t2s3); cr.save(c3t2s3);
		cr.save(c1t3s3); cr.save(c2t3s3); cr.save(c3t3s3);

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
	}
}
