/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ba.easyautoproject.DTO;

public class UsuarioDTO {

    private int id;
    private String nome_usuario,
            email_usuario,
            senha_usuario,
            cargo_usuario,
            login_usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome_usuario
     */
    public String getNome_usuario() {
        return nome_usuario;
    }

    /**
     * @param nome_usuario the nome_usuario to set
     */
    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    /**
     * @param email_usuario the email_usuario to set
     */
    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    /**
     * @param senha_usuario the senha_usuario to set
     */
    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

    /**
     * @return the cargo_usuario
     */
    public String getCargo_usuario() {
        return cargo_usuario;
    }

    /**
     * @param cargo_usuario the cargo_usuario to set
     */
    public void setCargo_usuario(String cargo_usuario) {
        this.cargo_usuario = cargo_usuario;
    }

    /**
     * @return the login_usuario
     */
    public String getLogin_usuario() {
        return login_usuario;
    }

    /**
     * @param login_usuario the login_usuario to set
     */
    public void setLogin_usuario(String login_usuario) {
        this.login_usuario = login_usuario;
    }

}
