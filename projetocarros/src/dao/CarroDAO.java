package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.AppConnection;
import entity.Carro;

public class CarroDAO {
	
	public List<Carro> listAll() throws Exception {
		ArrayList<Carro> list = new ArrayList<Carro>();

		try {
			Connection con = AppConnection.getConnection();
			Statement stmt = con.createStatement();
			  ResultSet rs = stmt.executeQuery(
			          "SELECT id, ano, modelo, fabricante FROM Carro"
					  );
			  while (rs.next()) {
			      Carro c = new Carro();
				  c.setId(rs.getInt("id"));
				  c.setAno(rs.getInt("ano"));
				  c.setModelo(rs.getString("modelo"));
				  c.setFabricante(rs.getString("fabricante"));
				  list.add(c);
			  }
			  stmt.close();
			  rs.close();
		} catch (Exception ex) {
			System.err.println("Erro ao obter os dados:" + ex);
			throw ex;
		}
		return list;
	}
	
	
	public Carro getCarro(int id) throws Exception {
		Carro c = null;
		try {
			Connection con = AppConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(
					"SELECT id, ano, modelo, fabricante FROM Carro where id = ?");
			stmt.setInt(1, id);
			  ResultSet rs = stmt.executeQuery();
			  while (rs.next()) {
			      c = new Carro();
			      c.setId(rs.getInt("id"));
				  c.setAno(rs.getInt("ano"));
				  c.setModelo(rs.getString("modelo"));
				  c.setFabricante(rs.getString("fabricante"));
			  }
			  stmt.close();
			  rs.close();
		} catch (Exception ex) {
			System.err.println("Erro ao obter os dados:" + ex);
			throw ex;
		}
		return c;
	}

	
	public Carro inserir(Carro carro) throws Exception {
		String sqlInsert =
			"INSERT INTO Carro (ID, ANO, MODELO, FABRICANTE) VALUES ( ?, ?, ?, ?)";
		valida(carro, true);
		try {
			Connection con = AppConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			ps.setInt(1, carro.getId());
			ps.setInt(2, carro.getAno());
			ps.setString(3, carro.getModelo());
			ps.setString(4, carro.getFabricante());
			int ret = ps.executeUpdate();
			if (ret != 1) {
				throw new Exception("Valor não foi inserido por erro de banco."); 
			}
		} catch (Exception ex) {
			System.err.println("Erro ao inserir os dados" + ex);
			throw ex;
		}
		return carro;
	}

	


	public Carro atualizar(Carro carro) throws Exception {
		String sqlInsert =
			"UPDATE Carro SET ano = ? , modelo = ? , fabricante = ? where id = ?";
		valida(carro, false);
		try {
			Connection con = AppConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			
			ps.setInt(1, carro.getAno());
			ps.setString(2, carro.getModelo());
			ps.setString(3, carro.getFabricante());
			ps.setInt(4, carro.getId());
			int ret = ps.executeUpdate();
			if (ret != 1) {
				throw new Exception("Valor não foi alterado por erro de banco."); 
			}
		} catch (Exception ex) {
			System.err.println("Erro ao obter os dados" + ex);
			throw ex;
		}
		return carro;
	}
	
	
	public Carro excluir(Carro carro) throws Exception {
		String sqlExcluir =
			"DELETE FROM Carro where id = ?";
		try {
			Connection con = AppConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlExcluir);
			ps.setInt(1, carro.getId());
			int ret = ps.executeUpdate();
			if (ret != 1) {
				throw new Exception("Valor não foi excluído por erro de banco."); 
			}
		} catch (Exception ex) {
			System.err.println("Erro ao obter os dados" + ex);
			throw ex;
		}
		return carro;
	}

	private void valida(Carro carro, boolean inserir) throws Exception {

		// Verifica se nome não é nulo ou vazio.
		/*if (imovel.getEndereco() == null || imovel.getEndereco().trim().length() == 0) {
			throw new Exception("Nome não pode ser nulo");
		}*/

		// Verifica se nome não é nulo ou vazio.
		/*if (imovel.getValor() == 0) {
			throw new Exception("Valor não pode ser vazio.");
		}*/
		
		try {
			int maxId = 0;
			Connection con = AppConnection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, ano, modelo, fabricante FROM Carro");
			while (rs.next()) {
				int id = rs.getInt("id");
				
				
				// Quando está atualizando, se o ID for igual, não precisa comparar
				if (!inserir) {
					if (id == carro.getId()) {
						continue;
					}
				}
				
				// Verifica o nome e cpf.
				/*if (imovel.getEndereco().equals(enderecoBD)) {
					throw new Exception("Nome já existe em " + id);
				}*/
				
				// Aproveita para calcular o max id.
				if (maxId < id) {
					maxId = id;
				}
			}
			
			// Só altera o ID se for inserir, atualizar não.
			if (inserir) {
				carro.setId(maxId + 1);
			}
			rs.close();
			stmt.close();

		} catch (Exception ex) {
			System.err.println("Erro de validação: " + ex);
			throw ex;
		}
	}
}
