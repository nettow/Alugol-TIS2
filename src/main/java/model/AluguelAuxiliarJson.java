package model;

public class AluguelAuxiliarJson {

    private int idCliente;
    private int idQuadra;
    private float valorAluguel;
    private String dataEHora;

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

    public String getDataEHora() {
        return dataEHora;
    }

    public void setDataEHora(String dataEHora) {
        this.dataEHora = dataEHora;
    }

    public AluguelAuxiliarJson(int idCliente, int idQuadra, float valorAluguel, String dataEHora) {
        this.idCliente = idCliente;
        this.idQuadra = idQuadra;
        this.valorAluguel = valorAluguel;
        this.dataEHora = dataEHora;
    }
}
