package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Proprietario implements Serializable,JsonFormatter {
    private static final long serialVersionUID = 1L;
    private int id;
    private String CPNJ;
    private String razaoSocial;
    private String email;
    private String senha;
    private String telefone;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getCPNJ() {
        return CPNJ;
    }

    public void setCPNJ(String CPNJ) {
        this.CPNJ = CPNJ;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



    public Proprietario(int id, String CPNJ, String razaoSocial, String email, String senha,String telefone) {
        this.setId(id);
        this.setCPNJ(CPNJ);
        this.setRazaoSocial(razaoSocial);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
    }

    /**
     * M�todo sobreposto da classe Object. � executado quando um objeto precisa
     * ser exibido na forma de String.
     */
    @Override
    public String toString() {
        return "Proprietario:\n"+"Nome: "+this.getRazaoSocial()+"\nEmail: "+this.getEmail()+"\nCPF:"+this.getCPNJ()+"\nSenha: "+this.getSenha()+"\nTelefone: "+this.getTelefone();
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Proprietario) obj).getId());
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("cpnj", this.getCPNJ());
        obj.put("razaoSocial", this.getRazaoSocial());
        obj.put("email", this.getEmail());
        obj.put("senha", this.getSenha());
        obj.put("telefone",this.getTelefone());
        return obj;
    }

    @Override
    public JSONArray toJsonArray() {
        return null;
    }
}
