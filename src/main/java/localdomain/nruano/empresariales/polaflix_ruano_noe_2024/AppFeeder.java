package localdomain.nruano.empresariales.polaflix_ruano_noe_2024;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.Capitulo;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.CategoriaSerie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.Serie;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.Temporada;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.dominio.Usuario;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios.CapituloRepository;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios.SerieRepository;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios.TemporadaRepository;
import localdomain.nruano.empresariales.polaflix_ruano_noe_2024.repositorios.UsuarioRepository;

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

	@Override
	public void run(String... args) throws Exception {
		feedUsuarios();
		feedSeries();

		System.out.println("<<<<<<<< Test SerieRepository >>>>>>>>>>>>");
		testSerieRepository();
		System.out.println("==========================================");

		System.out.println("\n<<<<<<<< Test TemporadaRepository >>>>>>>>");
		testTemporadaRepository();
		System.out.println("==========================================");

		System.out.println("\n<<<<<<<< Test CapituloRepository >>>>>>>>>");
		testCapituloRepository();
		System.out.println("==========================================");

		System.out.println("\n<<<<<<<< Test UsuarioRepository >>>>>>>>>>");
		testUsuarioRepository();
		System.out.println("==========================================");

		System.out.println("\n<<<<<<<< Test Usuario >>>>>>>>>>>>>>>>>>>>");
		testUsuario();
		System.out.println("==========================================");
		
		System.out.println("Application feeded");
	}

	private void feedUsuarios() {
		Usuario u1 = new Usuario("pacoloco", "pacoloco12345", false, "ES1234123412341234123400");
		Usuario u2 = new Usuario("lolaloca", "lolaloca12345", true, "ES5687568756785687567800");
		ur.save(u1);
		ur.save(u2);
	}
	
	private void feedSeries() {
		Serie s1 = new Serie("Mr. Robot", CategoriaSerie.GOLD, "Mr. Robot is a techno thriller that follows Elliot, a young programmer...");
		Serie s2 = new Serie("Young Sheldon", CategoriaSerie.SILVER, "For 9-year-old Sheldon Cooper, being a once-in-a-generation mind...");
		Serie s3 = new Serie("The Office", CategoriaSerie.ESTANDAR, "In this US adaptation of iconic British sitcom `The Office'...");
		sr.save(s1); sr.save(s2); sr.save(s3);

		/* -- Temporadas -- */
		Temporada t1s1 = new Temporada(1, s1);
		Temporada t2s1 = new Temporada(2, s1);
		Temporada t3s1 = new Temporada(3, s1);
		tr.save(t1s1); tr.save(t2s1); tr.save(t3s1);

		Temporada t1s2 = new Temporada(1, s2);
		Temporada t2s2 = new Temporada(2, s2);
		Temporada t3s2 = new Temporada(3, s2);
		tr.save(t1s2); tr.save(t2s2); tr.save(t3s2);

		Temporada t1s3 = new Temporada(1, s3);
		Temporada t2s3 = new Temporada(2, s3);
		Temporada t3s3 = new Temporada(3, s3);
		tr.save(t1s3); tr.save(t2s3); tr.save(t3s3);

		/* -- Capitulos@s1 -- */
		Capitulo c1t1s1 = new Capitulo("01x01", "URLc1t1s1", "DESCc1t1s1", t1s1);
		Capitulo c2t1s1 = new Capitulo("02x01", "URLc2t1s1", "DESCc2t1s1", t1s1);
		Capitulo c3t1s1 = new Capitulo("03x01", "URLc3t1s1", "DESCc3t1s1", t1s1);
		cr.save(c1t1s1); cr.save(c2t1s1); cr.save(c3t1s1);

		Capitulo c1t2s1 = new Capitulo("01x02", "URLc1t2s1", "DESCc1t2s1", t2s1);
		Capitulo c2t2s1 = new Capitulo("02x02", "URLc2t2s1", "DESCc2t2s1", t2s1);
		Capitulo c3t2s1 = new Capitulo("03x02", "URLc3t2s1", "DESCc3t2s1", t2s1);
		cr.save(c1t2s1); cr.save(c2t2s1); cr.save(c3t2s1);

		Capitulo c1t3s1 = new Capitulo("01x03", "URLc1t3s1", "DESCc1t3s1", t3s1);
		Capitulo c2t3s1 = new Capitulo("02x03", "URLc2t3s1", "DESCc2t3s1", t3s1);
		Capitulo c3t3s1 = new Capitulo("03x03", "URLc3t3s1", "DESCc3t3s1", t3s1);
		cr.save(c1t3s1); cr.save(c2t3s1); cr.save(c3t3s1);

		/* -- Capitulos@s2 -- */
		Capitulo c1t1s2 = new Capitulo("01x01", "URLc1t1s2", "DESCc1t1s2", t1s2);
		Capitulo c2t1s2 = new Capitulo("02x01", "URLc2t1s2", "DESCc2t1s2", t1s2);
		Capitulo c3t1s2 = new Capitulo("03x01", "URLc3t1s2", "DESCc3t1s2", t1s2);
		cr.save(c1t1s2); cr.save(c2t1s2); cr.save(c3t1s2);

		Capitulo c1t2s2 = new Capitulo("01x02", "URLc1t2s2", "DESCc1t2s2", t2s2);
		Capitulo c2t2s2 = new Capitulo("02x02", "URLc2t2s2", "DESCc2t2s2", t2s2);
		Capitulo c3t2s2 = new Capitulo("03x02", "URLc3t2s2", "DESCc3t2s2", t2s2);
		cr.save(c1t2s2); cr.save(c2t2s2); cr.save(c3t2s2);

		Capitulo c1t3s2 = new Capitulo("01x03", "URLc1t3s2", "DESCc1t3s2", t3s2);
		Capitulo c2t3s2 = new Capitulo("02x03", "URLc2t3s2", "DESCc2t3s2", t3s2);
		Capitulo c3t3s2 = new Capitulo("03x03", "URLc3t3s2", "DESCc3t3s2", t3s2);
		cr.save(c1t3s2); cr.save(c2t3s2); cr.save(c3t3s2);

		/* -- Capitulos@s3 -- */
		Capitulo c1t1s3 = new Capitulo("01x01", "URLc1t1s3", "DESCc1t1s3", t1s3);
		Capitulo c2t1s3 = new Capitulo("02x01", "URLc2t1s3", "DESCc2t1s3", t1s3);
		Capitulo c3t1s3 = new Capitulo("03x01", "URLc3t1s3", "DESCc3t1s3", t1s3);
		cr.save(c1t1s3); cr.save(c2t1s3); cr.save(c3t1s3);

		Capitulo c1t2s3 = new Capitulo("01x02", "URLc1t2s3", "DESCc1t2s3", t2s3);
		Capitulo c2t2s3 = new Capitulo("02x02", "URLc2t2s3", "DESCc2t2s3", t2s3);
		Capitulo c3t2s3 = new Capitulo("03x02", "URLc3t2s3", "DESCc3t2s3", t2s3);
		cr.save(c1t2s3); cr.save(c2t2s3); cr.save(c3t2s3);

		Capitulo c1t3s3 = new Capitulo("01x03", "URLc1t3s3", "DESCc1t3s3", t3s3);
		Capitulo c2t3s3 = new Capitulo("02x03", "URLc2t3s3", "DESCc2t3s3", t3s3);
		Capitulo c3t3s3 = new Capitulo("03x03", "URLc3t3s3", "DESCc3t3s3", t3s3);
		cr.save(c1t3s3); cr.save(c2t3s3); cr.save(c3t3s3);
	}

	/****** TEST SerieRepository **********************************************/

	private void testSerieRepository() {
		testFindByTitulo("Mr. Robot", true);
		testFindByTitulo("Young Sheldon", true);
		testFindByTitulo("The Office", true);
		testFindByTitulo("invent", false);
	}

	private void testFindByTitulo(String titulo, boolean esperado) {
		System.out.println("[*] Buscando serie \"" + titulo + "\"");
		Serie s = sr.findByTitulo(titulo);

		if (s != null && s.getTitulo().equals(titulo))
			System.out.println(((esperado) ? "-- PASS --" : "-- FAIL --"));
		else
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
	}

	/****** TEST TemporadaRepository ******************************************/

	private void testTemporadaRepository() {
		/* findByIdSerieAndIndice */
		testFindByIdSerieAndIndice(1, 1, true);
		testFindByIdSerieAndIndice(1, 4, false);	// Índice por encima del rango [0,3]
		testFindByIdSerieAndIndice(1, -1, false);	// Índice por debajo del rango [0,3]
		testFindByIdSerieAndIndice(-1, 1, false);	// ID no válido
		testFindByIdSerieAndIndice(-1, 10, false);	// ID e índice no válidos

		/* findByIdSerieAndIndice */
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
		testFindByTituloSerieAndIndiceTemporada("Mr. Robot", 1, true);
		testFindByTituloSerieAndIndiceTemporada("Mr. Robot", 4, false);
		testFindByTituloSerieAndIndiceTemporada("Mr. Robot", -1, false);
		testFindByTituloSerieAndIndiceTemporada("Invent", 1, false);
		testFindByTituloSerieAndIndiceTemporada("Invent", -1, false);
	}

	private void testFindByTituloSerieAndIndiceTemporada(String titulo, int indice, boolean esperado) {
		System.out.println("[*] Buscando capitulos -> serie: \"" + titulo + "\"\tindice: " + indice);
		ArrayList<Capitulo> cs = cr.findByTituloSerieAndIndiceTemporada(titulo, indice);

		if (cs.isEmpty())
			System.out.println(((!esperado) ? "-- PASS --" : "-- FAIL --"));
		else if (esperado)
			for(Capitulo c: cs)
				System.out.println("-- Titulo: \"" + c.getTitulo() + "\"");
		else
			System.out.println("-- FAIL --");
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
	private void testUsuario() {
		Usuario u = ur.findByNombre("pacoloco");

		/* Nueva visualización de un capítulo de Mr. Robot */
		u.registraVisualizacion(cr.findByTitSerieAndIndTempAndIndCap("Mr. Robot", 1, 1));
	}

/*	
	private void testViajeRepository() {
		
		SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy");
		Date sample = null;
		try {
			sample = dateParser.parse("01-01-2020");
		} catch (ParseException e) {
			System.out.println("Crujo parseando fecha");
			e.printStackTrace();
		}
		
		// Set<Viaje> viajes = vr.findByOrigenCiudadAndDestinoCiudad("Santander","Cadiz");
		Set<Viaje> viajes = vr.findByOrigenAndDestino("Santander","Cadiz");
		
		System.out.println("Viajes recuperados = " + viajes.size());
	
		for(Viaje v : viajes) {
			System.out.println("Viaje in " + v.getFecha());
		}
		
		viajes = vr.findByOrigen_CiudadAndFechaBeforeOrderByPrecio("Santander", sample);

		System.out.println("================================");
		
		System.out.println("Viajes recuperados = " + viajes.size());
		
		
//		Usuario paco = ur.findByEmail("paco@carSharing.es"); 
//		
//		System.out.println("Paco = " + paco.getNombre() + ":" + paco.getEmail());
//		
//		Set<Usuario> usuarios = ur.findByFechaAltaAfter(sample);
//		for(Usuario u : usuarios) {
//			System.out.println("Usuario " + u.getNombre() + ":" + u.getEmail());
//		}
		
	}*/

}