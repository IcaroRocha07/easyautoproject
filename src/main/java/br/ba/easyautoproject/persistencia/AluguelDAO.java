/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ba.easyautoproject.persistencia;

import br.ba.easyautoproject.DTO.AluguelDTO;
import br.ba.easyautoproject.util.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author icaro
 */
public class AluguelDAO {

    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacao;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //registrar produto
    public long inserirAluguel(AluguelDTO cliente) throws SQLException {
        String sql = null;
        long id_gerado = 0;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            connection.setAutoCommit(false);
            sql = "INSERT INTO aluguel (idaluguel, veiculo, dataAluguel, dataEntrega, cliente, entregue, observacao, valorPago) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, null);
            statement.setString(2, cliente.getVeiculo());
            statement.setString(3, cliente.getDataDeAluguel());
            statement.setString(4, cliente.getDataDeEntrega());
            statement.setString(5, cliente.getCliente());
            statement.setString(6, cliente.getEntregue());
            statement.setString(7, cliente.getObservacao());
            statement.setString(8, cliente.getValorPago());

            estadoOperacao = statement.executeUpdate() > 0;
            if (estadoOperacao == false) {
                throw new SQLException("Falha na criação do Aluguel");
            }

            try ( ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id_gerado = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Falha na criação do aluguel, nenhum ID obtido.");
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

    public boolean alterarAluguel(AluguelDTO aluguel) throws SQLException {
        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            connection.setAutoCommit(false);
            sql = "UPDATE aluguel SET veiculo = ?, dataAluguel = ?, dataEntrega = ?, cliente = ?, entregue = ?, observacao = ?, valorPago = ?   WHERE idveiculos = ?";

            statement = connection.prepareStatement(sql);

            statement.setString(1, aluguel.getDataDeAluguel());
            statement.setString(2, aluguel.getDataDeEntrega());
            statement.setString(3, aluguel.getCliente());
            statement.setString(4, aluguel.getEntregue());
            statement.setString(5, aluguel.getObservacao());
            statement.setString(6, aluguel.getValorPago());
            statement.setInt(7, aluguel.getId());

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
        public void deletarAluguel(AluguelDTO objalugueldto) {
        String sql = "delete from aluguel where id = ?";

        Connection conexao = new ConexaoMySQL().getConexaoMySQL();

        try {

            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, objalugueldto.getId());
            pstm.execute();
            pstm.close();
            
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "AluguelDAO Excluir" + erro);
        }

    }

    //receber a lista dos usuarios
    public List<AluguelDTO> listarAlugueis() throws SQLException {
        ResultSet resultSet = null;
        List<AluguelDTO> listaaluguel = new ArrayList<>();

        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            sql = "SELECT * FROM aluguel ORDER BY veiculo ASC";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                AluguelDTO u = new AluguelDTO();
                u.setId(resultSet.getInt(1));
                u.setVeiculo(resultSet.getString(2));
                u.setDataDeAluguel(resultSet.getString(3));
                u.setDataDeEntrega(resultSet.getString(4));
                u.setCliente(resultSet.getString(5));
                u.setEntregue(resultSet.getString(6));
                u.setObservacao(resultSet.getString(7));
                u.setValorPago(resultSet.getString(8));
                

                listaaluguel.add(u);

            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("fechou");
            connection.close();
        }

        return listaaluguel;
    }

    //receber determinado produto
    public AluguelDTO listarAluguel(int idaluguel) throws SQLException {
        ResultSet resultSet = null;
        AluguelDTO u = new AluguelDTO();

        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            sql = "SELECT * FROM aluguel WHERE idaluguel = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idaluguel);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                u.setId(resultSet.getInt(1));
                u.setVeiculo(resultSet.getString(2));
                u.setDataDeAluguel(resultSet.getString(3));
                u.setDataDeEntrega(resultSet.getString(4));
                u.setCliente(resultSet.getString(5));
                u.setEntregue(resultSet.getString(6));
                u.setObservacao(resultSet.getString(7));
                u.setValorPago(resultSet.getString(8));
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
    public ResultSet listarVeiculo() throws SQLException{
        connection = obterConexao();
        String sql = "SELECT * FROM veiculo ORDER BY modelo;";
                
          try {
            statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
     public ResultSet listarCliente() throws SQLException{
        connection = obterConexao();
        String sql = "SELECT * FROM cliente ORDER BY nomeCliente;";
                
          try {
            statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    
    
    }
    //obter conexao 
    private Connection obterConexao() throws SQLException {
        return ConexaoMySQL.getConexaoMySQL();
    }

}
