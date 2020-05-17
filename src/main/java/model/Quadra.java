package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Quadra implements Serializable,JsonFormatter{
    private static final long serialVersionUID = 1L;
    private int id;
    private int idProp;
    private String nomeQuadra;
    private String urlEspaco;
    private int capacidade;
    private String resumo;
    private String descricao;
    private float valorReserva;
    private String rua;
    private String bairro;
    private String cidade;
    private String modalidade;


    public String getUrlEspaco() {
        return urlEspaco;
    }

    public void setUrlEspaco(String urlEspaco) {
        this.urlEspaco = urlEspaco;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValorReserva() {
        return valorReserva;
    }

    public void setValorReserva(float valorReserva) {
        this.valorReserva = valorReserva;
    }

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

    public Quadra(int id, int idProp, String nomeQuadra, String urlEspaco, int capacidade, String resumo, String descricao, float valorReserva, String rua, String bairro, String cidade, String modalidade) {
        this.setId(id);
        this.setIdProp(idProp);
        this.setNomeQuadra(nomeQuadra);
        this.setUrlEspaco(urlEspaco);
        this.setCapacidade(capacidade);
        this.setResumo(resumo);
        this.setDescricao(descricao);
        this.setValorReserva(valorReserva);
        this.setRua(rua);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setModalidade(modalidade);
    }

    @Override
    public String toString() {
        return "Quadra{" +
                "id=" + id +
                ", idProp=" + idProp +
                ", nomeQuadra='" + nomeQuadra + '\'' +
                ", urlEspaco='" + urlEspaco + '\'' +
                ", capacidade=" + capacidade +
                ", resumo='" + resumo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valorReserva=" + valorReserva +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", modalidade='" + modalidade + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Quadra) obj).getId());
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("idProp", this.getIdProp());
        obj.put("nomeQuadra", this.getNomeQuadra());
        obj.put("urlEspaco",this.getUrlEspaco());
        obj.put("capacidade",this.getCapacidade());
        obj.put("resumo",this.getResumo());
        obj.put("descricao",this.getDescricao());
        obj.put("valorReserva",this.getValorReserva());
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
