package br.ba.easyautoproject.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.util.List;

import br.ba.easyautoproject.DTO.UsuarioDTO;





public class UsuarioDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacao;
	
	//registrar produto
	public long inserirUsuario(UsuarioDTO usuario) throws SQLException {
		String sql = null;
		long id_gerado = 0;
		estadoOperacao = false;
		connection = obterConexao();
		
		try {
			connection.setAutoCommit(false);
			sql = "INSERT INTO usuario(id_usuario, nome, email, senha, cargo, login) VALUES(?, ?, ?, ?, ?, ?)";
			
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, null);
			statement.setString(2, usuario.getNome_usuario());
			statement.setString(3, usuario.getEmail_usuario());
			statement.setString(4, usuario.getSenha_usuario());
                        statement.setString(5, usuario.getCargo_usuario());
                        statement.setString(6,usuario.getLogin_usuario());
			
			estadoOperacao =  statement.executeUpdate() > 0;
			if (estadoOperacao == false) {
				throw new SQLException("Falha na criação do usuário");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id_gerado = generatedKeys.getLong(1);
				}
				else {
					throw new SQLException("Falha na criação do usuário, nenhum ID obtido.");
				}
			}
			
			connection.commit();
			statement.close();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}finally{
			System.out.println("fechou");
			connection.close();
		}
		
		return id_gerado;
	}
	//editar produto
	public boolean alterarUsuario(UsuarioDTO usuario) throws SQLException {
		String sql = null;
		estadoOperacao = false;
		connection = obterConexao();

		try {
			connection.setAutoCommit(false);
			sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, cargo = ?, login = ?,   WHERE id_usuario = ?";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, usuario.getNome_usuario());
			statement.setString(2, usuario.getEmail_usuario());
			statement.setString(3, usuario.getSenha_usuario());
                        statement.setString(4, usuario.getCargo_usuario());
			statement.setString(5, usuario.getLogin_usuario());
                        statement.setInt(6, usuario.getId());
			
			estadoOperacao = statement.executeUpdate() > 0 ;
			connection.commit();
			statement.close();
			
			
		}
		catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}finally{
			System.out.println("fechou");
			connection.close();
		}
		
		
		return estadoOperacao;
	}
		
	//deleta determinado produto
	public boolean deletarUsuario(int id_usuario) throws SQLException {
		String sql = null;
		estadoOperacao = false;
		connection = obterConexao();

		try {
			connection.setAutoCommit(false);
			sql = "DELETE FROM usuario WHERE id_usuario = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id_usuario);
			
			estadoOperacao = statement.executeUpdate() > 0 ;
			connection.commit();
			statement.close();
			
			
		}
		catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}finally{
			System.out.println("fechou");
			connection.close();
		}
		
		
		return estadoOperacao;
	}
	
	//receber a lista dos usuarios
	public List<UsuarioDTO> listarUsuarios() throws SQLException {
		ResultSet resultSet = null;
		List<UsuarioDTO> listaUsuarios = new ArrayList<>();
		
		String sql = null;
		estadoOperacao = false;
		connection = obterConexao();

		try {
			sql = "SELECT * FROM usuario ORDER BY nome ASC";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				UsuarioDTO u = new UsuarioDTO();
				u.setId(resultSet.getInt(1));
				u.setNome_usuario(resultSet.getString(2));
				u.setEmail_usuario(resultSet.getString(3));
				u.setSenha_usuario(resultSet.getString(4));
                                u.setCargo_usuario(resultSet.getString(5));
                                u.setLogin_usuario(resultSet.getString(sql));
				
				listaUsuarios.add(u);
				
			}
			statement.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			System.out.println("fechou");
			connection.close();
		}
		
		return listaUsuarios;
	}
	
	//receber determinado produto
	public UsuarioDTO listarUsuario(int id_usuario) throws SQLException {
		ResultSet resultSet = null;
		UsuarioDTO u=new UsuarioDTO();

		String sql = null;
		estadoOperacao = false;
		connection = obterConexao();

		try {
			sql = "SELECT * FROM usuario WHERE id_usuario =?";
			statement=connection.prepareStatement(sql);
			statement.setInt(1, id_usuario);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {				
				u.setId(resultSet.getInt(1));
				u.setNome_usuario(resultSet.getString(2));
				u.setEmail_usuario(resultSet.getString(3));
				u.setSenha_usuario(resultSet.getString(4));
			}
			statement.close();
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			System.out.println("fechou");
			connection.close();
		}

		return u;
	}
	public UsuarioDTO login(UsuarioDTO u) throws SQLException {
		ResultSet resultSet = null;

		UsuarioDTO usuario = new UsuarioDTO();

		String sql = null;
		estadoOperacao = false;
		connection = obterConexao();

		try {
			sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
			statement=connection.prepareStatement(sql);
			statement.setString(1, u.getLogin_usuario());
			statement.setString(2, u.getSenha_usuario());
			resultSet = statement.executeQuery();

			if(resultSet.next()) {				
				usuario.setId(resultSet.getInt(1));
				usuario.setNome_usuario(resultSet.getString(2));
				usuario.setEmail_usuario(resultSet.getString(3));
				usuario.setSenha_usuario(resultSet.getString(4));
			}
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
                    usuario.setId(0);
                    e.printStackTrace();
		}finally{
			System.out.println("fechou");
			connection.close();
		}
                
		return usuario;
                
	}
	
	//obter conexao 
	private Connection obterConexao() throws SQLException {
		return ConexaoMySQL.getConexaoMySQL();
	}
	
}
