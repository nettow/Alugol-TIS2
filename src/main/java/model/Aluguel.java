package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Aluguel implements Serializable,JsonFormatter{

    private int idAluguel;
    private int idCliente;
    private int idQuadra;
    private float valorAluguel;
    private String dataEHoraAluguel;

    public Aluguel(int idAluguel, int idCliente, int idQuadra, float valorAluguel,String dataEHoraAluguel) {
        this.setIdAluguel(idAluguel);
        this.setIdCliente(idCliente);
        this.setIdQuadra(idQuadra);
        this.setValorAluguel(valorAluguel);
        this.setDataEHoraAluguel(dataEHoraAluguel);
    }

    public String getDataEHoraAluguel() {
        return dataEHoraAluguel;
    }

    public void setDataEHoraAluguel(String dataEHoraAluguel) {
        this.dataEHoraAluguel = dataEHoraAluguel;
    }

    public int getIdAluguel() {
        return idAluguel;
    }

    public void setIdAluguel(int idAluguel) {
        this.idAluguel = idAluguel;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdQuadra() {
        return idQuadra;
    }

    public void setIdQuadra(int idQuadra) {
        this.idQuadra = idQuadra;
    }

    public float getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(float valorAluguel) {
        this.valorAluguel = valorAluguel;
    }


    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("idAluguel", this.getIdAluguel());
        obj.put("idCliente", this.getIdCliente());
        obj.put("idQuadra", this.getIdQuadra());
        obj.put("valorAluguel", this.getValorAluguel());
        obj.put("dataEHoraAluguel",this.getDataEHoraAluguel());
        return obj;
    }

    @Override
    public JSONArray toJsonArray() {
        return null;
    }
}
