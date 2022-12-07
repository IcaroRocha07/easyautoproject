/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ba.easyautoproject.persistencia;

import br.ba.easyautoproject.DTO.ClienteDTO;
import br.ba.easyautoproject.DTO.UsuarioDTO;
import br.ba.easyautoproject.util.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author icaro
 */
public class ClienteDAO {

    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacao;

    //registrar produto
    public long inserirCliente(ClienteDTO cliente) throws SQLException {
        String sql = null;
        long id_gerado = 0;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            connection.setAutoCommit(false);
            sql = "INSERT INTO cliente (id, nomeCliente, cpf, uf, endereco, email, telefone) VALUES(?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, null);
            statement.setString(2, cliente.getNomeCliente());
            statement.setString(3, cliente.getCpf());
            statement.setString(4, cliente.getUf());
            statement.setString(5, cliente.getEndereco());
            statement.setString(6, cliente.getEmail());
            statement.setString(7, cliente.getTelefone());
            estadoOperacao = statement.executeUpdate() > 0;
            if (estadoOperacao == false) {
                throw new SQLException("Falha na criação do cliente");
            }

            try ( ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id_gerado = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Falha na criação do cliente, nenhum ID obtido.");
                }
            }

            connection.commit();
            statement.close();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            System.out.println("fechou");
            connection.close();
        }

        return id_gerado;
    }
    //editar produto

    public boolean alterarCliente(ClienteDTO cliente) throws SQLException {
        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            connection.setAutoCommit(false);
            sql = "UPDATE cliente SET nomeCliente = ?, email = ?, endereco = ?, telefone = ?, uf = ?, cpf = ?   WHERE id = ?";

            statement = connection.prepareStatement(sql);

            statement.setString(1, cliente.getNomeCliente());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getEndereco());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getUf());
            statement.setInt(6, cliente.getId());

            estadoOperacao = statement.executeUpdate() > 0;
            connection.commit();
            statement.close();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            System.out.println("fechou");
            connection.close();
        }

        return estadoOperacao;
    }

    //deleta determinado produto
    public void deletarCliente(ClienteDTO objclientedto) {
        String sql = "delete from cliente where id = ?";

        Connection conexao = new ConexaoMySQL().getConexaoMySQL();

        try {

            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, objclientedto.getId());
            pstm.execute();
            pstm.close();
            
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "AluguelDAO Excluir" + erro);
        }

    }

    //receber a lista dos usuarios
    public List<ClienteDTO> listarClientes() throws SQLException {
        ResultSet resultSet = null;
        List<ClienteDTO> listacliente = new ArrayList<>();

        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            sql = "SELECT * FROM cliente ORDER BY nomeCliente ASC";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ClienteDTO u = new ClienteDTO();
                u.setId(resultSet.getInt(1));
                u.setNomeCliente(resultSet.getString(2));
                u.setEndereco(resultSet.getString(3));
                u.setUf(resultSet.getString(4));
                u.setTelefone(resultSet.getString(5));
                u.setCpf(resultSet.getString(6));
                u.setEmail(resultSet.getString(7));

                listacliente.add(u);

            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("fechou");
            connection.close();
        }

        return listacliente;
    }

    //receber determinado produto
    public ClienteDTO listarCliente(int id) throws SQLException {
        ResultSet resultSet = null;
        ClienteDTO u = new ClienteDTO();

        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            sql = "SELECT * FROM cliente WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                u.setId(resultSet.getInt(1));
                u.setNomeCliente(resultSet.getString(2));
                u.setEndereco(resultSet.getString(3));
                u.setUf(resultSet.getString(4));
                u.setTelefone(resultSet.getString(5));
                u.setCpf(resultSet.getString(6));
                u.setEmail(resultSet.getString(7));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("fechou");
            connection.close();
        }

        return u;
    }

    //obter conexao 
    private Connection obterConexao() throws SQLException {
        return ConexaoMySQL.getConexaoMySQL();
    }

}
