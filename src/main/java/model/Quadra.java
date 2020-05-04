package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Quadra implements Serializable,JsonFormatter{
    private static final long serialVersionUID = 1L;
    private int id;
    private int idProp;
    private String nomeQuadra;
    private String rua;
    private String bairro;
    private String cidade;
    private String modalidade;


    public int getIdProp() {
        return idProp;
    }

    public void setIdProp(int idProp) {
        this.idProp = idProp;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
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

    public String getNomeQuadra() {
        return nomeQuadra;
    }

    public void setNomeQuadra(String nomeQuadra) {
        this.nomeQuadra = nomeQuadra;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Quadra(int id, int idProp,String nomeQuadra, String rua, String bairro, String cidade,String modalidade) {
        this.setId(id);
        this.setIdProp(idProp);
        this.setNomeQuadra(nomeQuadra);
        this.setRua(rua);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setModalidade(modalidade);
    }


    /**
     * M�todo sobreposto da classe Object. � executado quando um objeto precisa
     * ser exibido na forma de String.
     */
    @Override
    public String toString() {
        return "Quadra:\n"+"Nome da Quadra: "+this.getNomeQuadra()+"\nRua: "+this.getRua()+"\nBairro: "+this.getBairro()+"\nCidade: "+this.getCidade()+"\nModalidade: "+this.getModalidade();
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Cliente) obj).getId());
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("idProp", this.getIdProp());
        obj.put("nomeQuadra", this.getNomeQuadra());
        obj.put("rua", this.getRua());
        obj.put("bairro", this.getBairro());
        obj.put("cidade", this.getCidade());
        obj.put("modalidade",this.getModalidade());
        return obj;
    }

    @Override
    public JSONArray toJsonArray() {
        return null;
    }
}
