package model;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Cliente implements Serializable,JsonFormatter {

	private static final long serialVersionUID = 1L;
	private int id;
	private String CPF;
	private String nome;
	private String email;
	private String senha;
	private int idade;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Cliente(int id, String CPF,String nome, String email, String senha, int idade) {
		this.setId(id);
		this.setCPF(CPF);
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
		this.setIdade(idade);
	}

	/**
	 * M�todo sobreposto da classe Object. � executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Cliente:\n"+"Nome: "+this.getNome()+"\nEmail: "+this.getEmail()+"\nCPF:"+this.getCPF()+"\nSenha: "+this.getSenha()+"\nIdade: "+this.getIdade();
	}

	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Cliente) obj).getId());
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.getId());
		obj.put("cpf", this.getCPF());
		obj.put("nome", this.getNome());
		obj.put("email", this.getEmail());
		obj.put("senha", this.getSenha());
		obj.put("idade", this.getIdade());
		return obj;
	}

	@Override
	public JSONArray toJsonArray() {
		return null;
	}
}



