package it.prova.gestionecompagniapatterndao.dao.impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.prova.gestionecompagniapatterndao.dao.AbstractMySQLDAO;
import it.prova.gestionecompagniapatterndao.model.Compagnia;
import it.prova.gestionecompagniapatterndao.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	public List<Impiegato> list() throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<Impiegato> result = new ArrayList<Impiegato>();

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from impiegato")) {

			while (rs.next()) {
				Impiegato impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("NOME"));
				impiegatoTemp.setCognome(rs.getString("COGNOME"));
				impiegatoTemp.setCodiceFiscale(rs.getString("CODICEFISCALE"));
				impiegatoTemp.setDataNascita(
						rs.getDate("DATANASCITA") != null ? rs.getDate("DATANASCITA").toLocalDate() : null);
				impiegatoTemp.setDataAssunzione(
						rs.getDate("DATAASSUNZIONE") != null ? rs.getDate("DATAASSUNZIONE").toLocalDate() : null);
				impiegatoTemp.setId(rs.getLong("ID"));
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public Impiegato get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Impiegato result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Impiegato();
					result.setNome(rs.getString("NOME"));
					result.setCognome(rs.getString("COGNOME"));
					result.setCodiceFiscale(rs.getString("CODICEFISCALE"));
					result.setDataNascita(
							rs.getDate("DATANASCITA") != null ? rs.getDate("DATANASCITA").toLocalDate() : null);
					result.setDataAssunzione(
							rs.getDate("DATAASSUNZIONE") != null ? rs.getDate("DATAASSUNZIONE").toLocalDate() : null);
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int update(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO impiegato (nome, cognome, codicefiscale, datanascita, dataassunzione, id_compagnia) VALUES (?, ?, ?, ?, ?, ?);")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, java.sql.Date.valueOf(input.getDataNascita()));
			ps.setDate(5, java.sql.Date.valueOf(input.getDataAssunzione()));
			ps.setLong(6, input.getCompagnia().getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int delete(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null || compagniaInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");
		List<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTemp = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id_azienda=?")) {

			ps.setLong(1, compagniaInput.getId());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					impiegatoTemp = new Impiegato();
					impiegatoTemp.setNome(rs.getString("NOME"));
					impiegatoTemp.setCognome(rs.getString("COGNOME"));
					impiegatoTemp.setCodiceFiscale(rs.getString("CODICEFISCALE"));
					impiegatoTemp.setDataNascita(
							rs.getDate("DATANASCITA") != null ? rs.getDate("DATANASCITA").toLocalDate() : null);
					impiegatoTemp.setDataAssunzione(
							rs.getDate("DATAASSUNZIONE") != null ? rs.getDate("DATAASSUNZIONE").toLocalDate() : null);
					impiegatoTemp.setId(rs.getLong("ID"));
					result.add(impiegatoTemp);
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int countByDataFondazioneCompagniaGreaterThan(LocalDate dateCreatedInput) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(int fatturatoInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

//		List<Compagnia> result = new ArrayList<Compagnia>();
		List<Impiegato> result = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from compagnia c left outer join impiegato i on c.id=i.id_compagnia where c.fatturato>?")) {

			ps.setInt(1, fatturatoInput);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					Impiegato impiegatoTemp = new Impiegato();
					impiegatoTemp.setNome(rs.getString("NOME"));
					impiegatoTemp.setCognome(rs.getString("COGNOME"));
					impiegatoTemp.setCodiceFiscale(rs.getString("CODICEFISCALE"));
					impiegatoTemp.setDataNascita(
							rs.getDate("DATANASCITA") != null ? rs.getDate("DATANASCITA").toLocalDate() : null);
					impiegatoTemp.setDataAssunzione(
							rs.getDate("DATAASSUNZIONE") != null ? rs.getDate("DATAASSUNZIONE").toLocalDate() : null);
					impiegatoTemp.setId(rs.getLong("ID"));
					result.add(impiegatoTemp);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return result;
		}
	}

	public List<Impiegato> findAllErroriAssunzione() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
