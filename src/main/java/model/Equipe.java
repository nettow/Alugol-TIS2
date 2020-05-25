package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Equipe implements Serializable,JsonFormatter{
    private static final long serialVersionUID = 1L;
    private int id;
    private int idDonoEquipe;
    private String nomeEquipe;
    
    private String[] membros;

    public String[] getMembros() {
        return membros;
    }

    public void setMembros(String[] membros) {
        this.membros = membros;
    }

    public int getIdDonoEquipe() {
        return idDonoEquipe;
    }

    public void setIdDonoEquipe(int idDonoEquipe) {
        this.idDonoEquipe = idDonoEquipe;
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

    public Equipe(String nome,int idDonoEquipe, int id,String[] membros) {
        this.setId(id);
        this.setIdDonoEquipe(idDonoEquipe);
        this.setNomeEquipe(nome);
        this.setMembros(membros);
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", IdDonoEquipe=" + idDonoEquipe +
                ", nomeEquipe='" + nomeEquipe + '\'' +
                ", idMembros ='" + membros + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Equipe) obj).getId());
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        System.out.println(this.getMembros());
        obj.put("nomeEquipe", this.getNomeEquipe());
        obj.put("idEquipe", this.getId());
        obj.put("idDono", this.getIdDonoEquipe());
        obj.put("membros", this.getMembros());
        
        System.out.println(obj);

        return obj;
    }

    @Override
    public JSONArray toJsonArray() {
        return null;
    }
}
