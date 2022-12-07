/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ba.easyautoproject.DTO;

/**
 *
 * @author icaro
 */
public class VeiculoDTO {

    private int qtdPortas, idveiculos, id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String placa,
            fabricante,
            modelo,
            acessorios,
            anoModelo;

    public int getIdveiculos() {
        return idveiculos;
    }

    public void setIdveiculos(int idveiculos) {
        this.idveiculos = idveiculos;
    }

    public String getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(String anoModelo) {
        this.anoModelo = anoModelo;
    }

    public int getQtdPortas() {
        return qtdPortas;
    }

    public void setQtdPortas(int qtdPortas) {
        this.qtdPortas = qtdPortas;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(String acessorios) {
        this.acessorios = acessorios;
    }

}
