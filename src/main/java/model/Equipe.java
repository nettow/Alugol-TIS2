package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Equipe implements Serializable,JsonFormatter{
    private static final long serialVersionUID = 1L;
    private int id;
    private int idCliente;
    private String nomeEquipe;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public Equipe(String nome,int idCliente) {
        this.setId(id);
        this.setIdCliente(idCliente);
        this.setNomeEquipe(nome);
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", nomeTime='" + nomeEquipe + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Equipe) obj).getId());
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("idCliente", this.getIdCliente());
        obj.put("nomeTime", this.getNomeEquipe());
        return obj;
    }

    @Override
    public JSONArray toJsonArray() {
        return null;
    }
}
