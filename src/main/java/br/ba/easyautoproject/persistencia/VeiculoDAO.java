/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ba.easyautoproject.persistencia;



import br.ba.easyautoproject.DTO.VeiculoDTO;
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
public class VeiculoDAO {
    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacao;

    //registrar produto
    public long inserirVeiculo(VeiculoDTO veiculo) throws SQLException {
        String sql = null;
        long id_gerado = 0;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            connection.setAutoCommit(false);
            sql = "INSERT INTO veiculo (idveiculos, placa, fabricante, modelo, anoModelo, qtdPortas, acessorios) VALUES(?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, null);
            statement.setString(2, veiculo.getPlaca());
            statement.setString(3, veiculo.getFabricante());
            statement.setString(4, veiculo.getModelo());
            statement.setString(5, veiculo.getAnoModelo());
            statement.setInt(6, veiculo.getQtdPortas());
            statement.setString(7, veiculo.getAcessorios());
            estadoOperacao = statement.executeUpdate() > 0;
            if (estadoOperacao == false) {
                throw new SQLException("Falha na criação do cliente");
            }

            try ( ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id_gerado = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Falha na criação do veiculo, nenhum ID obtido.");
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

    public boolean alterarVeiculo(VeiculoDTO veiculo) throws SQLException {
        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            connection.setAutoCommit(false);
            sql = "UPDATE veiculo SET placa = ?, fabricante = ?, modelo = ?, anoModelo = ?, qtdPortas = ?, acessorios = ?    WHERE idveiculos = ?";

            statement = connection.prepareStatement(sql);

            statement.setString(1, veiculo.getPlaca());
            statement.setString(2, veiculo.getFabricante());
            statement.setString(3, veiculo.getModelo());
            statement.setString(4, veiculo.getAnoModelo());
            statement.setInt(5, veiculo.getQtdPortas());
            statement.setString(6, veiculo.getAcessorios());
            statement.setInt(7, veiculo.getIdveiculos());

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
     public void deletarCliente(VeiculoDTO objveiculodto) {
        String sql = "delete from veiculo where idveiculos = ?";

        Connection conexao = new ConexaoMySQL().getConexaoMySQL();

        try {

            PreparedStatement pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, objveiculodto.getId());
            pstm.execute();
            pstm.close();
            
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "VeiculoDAO Excluir" + erro);
        }

    }

    //receber a lista dos usuarios
    public List<VeiculoDTO> listarVeiculos() throws SQLException {
        ResultSet resultSet = null;
        List<VeiculoDTO> listaveiculo = new ArrayList<>();

        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            sql = "SELECT * FROM veiculo ORDER BY modelo ASC";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                VeiculoDTO u = new VeiculoDTO();
                u.setIdveiculos(resultSet.getInt(1));
                u.setPlaca(resultSet.getString(2));
                u.setFabricante(resultSet.getString(3));
                u.setModelo(resultSet.getString(4));
                u.setAnoModelo(resultSet.getString(5));
                u.setQtdPortas(resultSet.getInt(6));
                u.setAcessorios(resultSet.getString(7));

                listaveiculo.add(u);

            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("fechou");
            connection.close();
        }

        return listaveiculo;
    }

    //receber determinado produto
    public VeiculoDTO listarVeiculo(int idveiculos) throws SQLException {
        ResultSet resultSet = null;
        VeiculoDTO u = new VeiculoDTO();

        String sql = null;
        estadoOperacao = false;
        connection = obterConexao();

        try {
            sql = "SELECT * FROM veiculo WHERE idveiculos = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idveiculos);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                u.setIdveiculos(resultSet.getInt(1));
                u.setPlaca(resultSet.getString(2));
                u.setFabricante(resultSet.getString(3));
                u.setModelo(resultSet.getString(4));
                u.setAnoModelo(resultSet.getString(5));
                u.setQtdPortas(resultSet.getInt(6));
                u.setAcessorios(resultSet.getString(7));
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
