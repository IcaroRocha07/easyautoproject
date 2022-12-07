
package br.ba.easyautoproject.util;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

/**
 *
 * @author icaro
 */
public class ConexaoMySQL {
    
private static String status = "Não conectou...";

//Método Construtor da Classe//
    public ConexaoMySQL() {

    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        ConexaoMySQL.status = status;
    }



//Método de Conexão//

public static java.sql.Connection getConexaoMySQL() {

        Connection connection = null;          //atributo do tipo Connection



try {

// Carregando o JDBC Driver padrão

String driverName = "com.mysql.jdbc.Driver";

Class.forName(driverName);



// Configurando a nossa conexão com um banco de dados//

        String serverName = "localhost:3306";    //caminho do servidor do BD

        String mydatabase ="aluguel_veiculo";        //nome do seu banco de dados

        String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

        String username = "root";        //nome de um usuário de seu BD

        String password = "baancpupce";      //sua senha de acesso
         
      
        connection = DriverManager.getConnection(url, username, password);

       

        //Testa sua conexão//

        if (connection != null) {

            setStatus("STATUS--->Conectado com sucesso!");

        } else {

            setStatus("STATUS--->Não foi possivel realizar conexão");

        }



        return connection;



        } catch (ClassNotFoundException e) {  //Driver não encontrado



            System.out.println("O driver expecificado nao foi encontrado.");

            return null;

        } catch (SQLException e) {

//Não conseguindo se conectar ao banco

            System.out.println("Nao foi possivel conectar ao Banco de Dados.");

            return null;

        }



    }



    //Método que retorna o status da sua conexão//

    public static String statusConection() {

        return getStatus();
    }


   //Método que fecha sua conexão//

    public static boolean FecharConexao() {

        try {

            ConexaoMySQL.getConexaoMySQL().close();

            return true;

        } catch (SQLException e) {

            return false;

        }



    }



   //Método que reinicia sua conexão//

    public static java.sql.Connection ReiniciarConexao() {

        FecharConexao();
        
        return ConexaoMySQL.getConexaoMySQL();

    }

    

}
