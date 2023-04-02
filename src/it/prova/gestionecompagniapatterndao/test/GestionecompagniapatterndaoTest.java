package it.prova.gestionecompagniapatterndao.test;

import java.sql.Connection;
import java.time.LocalDate;

import it.prova.gestionecompagniapatterndao.connection.MyConnection;
import it.prova.gestionecompagniapatterndao.dao.Constants;
import it.prova.gestionecompagniapatterndao.dao.compagnia.CompagniaDAO;
import it.prova.gestionecompagniapatterndao.dao.compagnia.CompagniaDAOImpl;
import it.prova.gestionecompagniapatterndao.dao.impiegato.ImpiegatoDAO;
import it.prova.gestionecompagniapatterndao.dao.impiegato.ImpiegatoDAOImpl;
import it.prova.gestionecompagniapatterndao.model.Compagnia;
import it.prova.gestionecompagniapatterndao.model.Impiegato;

public class GestionecompagniapatterndaoTest {

	public static void main(String[] args) {

		ImpiegatoDAO impiegatoDAOInstance = null;
		CompagniaDAO compagniaDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante

			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);
			compagniaDAOInstance = new CompagniaDAOImpl(connection);

//			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");
//			
//			testInserimentoCompagnia(compagniaDAOInstance);
//			
//			System.out.println("In tabella user ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			System.out.println("In tabella impiegato ci sono " + impiegatoDAOInstance.list().size() + " elementi.");

			testInserimentoImpiegato(impiegatoDAOInstance);

			System.out.println("In tabella impiegato ci sono " + impiegatoDAOInstance.list().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testInserimentoImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testInserimentoNegozio inizio.............");
		int quantiImpiegatiInseriti = impiegatoDAOInstance.insert(new Impiegato("Mario", "Rossi", "MRRS0104H501S03",
				LocalDate.parse("2019-03-29"), LocalDate.parse("2019-03-29"),
				new Compagnia(1L, "MicroApp", 100000, LocalDate.parse("2019-03-29"))));
		if (quantiImpiegatiInseriti < 1)
			throw new RuntimeException("testInserimentoNegozio : FAILED");

		System.out.println(".......testInserimentoNegozio fine: PASSED.............");
	}

	private static void testInserimentoCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testInserimentoNegozio inizio.............");
		int quantiImpiegatiInseriti = compagniaDAOInstance
				.insert(new Compagnia("MicroApp", 100000, LocalDate.parse("2019-03-29")));
		if (quantiImpiegatiInseriti < 1)
			throw new RuntimeException("testInserimentoNegozio : FAILED");

		System.out.println(".......testInserimentoNegozio fine: PASSED.............");
	}
}
