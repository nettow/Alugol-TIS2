package dao;

import model.Cliente;
import model.Equipe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
	private List<Cliente> clientes;
	private int maxId = 0;
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public int getMaxId() {
		return maxId;
	}

	public ClienteDAO(String filename) throws IOException {

		file = new File(filename);
		clientes = new ArrayList<Cliente>();
		if (file.exists()) {
			readFromFile();
		}

	}

	public void add(Cliente cliente) {
		try {
			clientes.add(cliente);
			this.maxId = (cliente.getId() > this.maxId) ? cliente.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o cliente '" + cliente.getNome() + "' no disco!");
		}
	}

	public Cliente get(int id) {
		for (Cliente cliente : clientes) {
			if (id == cliente.getId()) {
				return cliente;
			}
		}
		return null;
	}

	public boolean verificaContaExiste(String email, String cpf){
		for(Cliente cliente : clientes){
			if(cliente.getEmail().equals(email)&&cliente.getCPF().equals(cpf)){
				return true;
			}
		}
		return false;
	}

	public Cliente getInfosCliente(int id){
		for(Cliente cliente : clientes){
			if(cliente.getId()==id){
				return cliente;
			}
		}
		return null;
	}

	public int getIdCliente(String email,String senha){
		for(Cliente cliente : clientes){
			if(cliente.getEmail().equals(email)&&cliente.getSenha().equals(senha)){
				return cliente.getId();
			}
		}
		return -1;
	}

	// Para EquipeService
	public String getClienteByEmail(String email){
		for(Cliente cliente : clientes){
			if(cliente.getEmail().equals(email)){
				return cliente.getNome();
			}
		}
		return "Usuario nao existe";
	}

//	 public void salvaEquipe(String nome, String eqp){
//	 	for(Cliente cliente : clientes){
//	 		if (cliente.getNome().equals(nome)){
//	 			System.out.println(cliente.getNome() + " foi encontrado e sua equipe definida foi: " + eqp);
//	 			cliente.setEquipe(eqp);
//	 			this.saveToFile();
//	 		}
//	 	}
//	 }
	
	public Cliente getCliente(String nome){
		for(Cliente cliente : clientes){
			if(cliente.getNome().equals(nome)){
				return cliente;
			}
		}
		return null;
	}

	public boolean verificaLogin(String email,String senha){
		for(Cliente cliente : clientes){
			if(cliente.getEmail().equals(email)&&cliente.getSenha().equals(senha)){
				return true;
			}
		}
		return false;
	}

	public void update(Cliente p) {
		int index = clientes.indexOf(p);
		if (index != -1) {
			clientes.set(index, p);
			this.saveToFile();
		}
		System.out.println("O cliente: " + p.getNome() + " foi atualizado. " + p.getEquipe());
	}

	public void remove(Cliente p) {
		int index = clientes.indexOf(p);
		if (index != -1) {
			clientes.remove(index);
			this.saveToFile();
		}
	}

	public List<Cliente> getAll() {
		return clientes;
	}

	private List<Cliente> readFromFile() {
		clientes.clear();
		Cliente cliente = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				cliente = (Cliente) inputFile.readObject();
				clientes.add(cliente);
				maxId = (cliente.getId() > maxId) ? cliente.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar cliente no disco!");
			e.printStackTrace();
		}
		return clientes;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Cliente cliente : clientes) {
				outputFile.writeObject(cliente);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar cliente no disco!");
			e.printStackTrace();
		}
	}

	private void close() throws IOException {
		outputFile.close();
		fos.close();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			this.saveToFile();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao salvar a base de dados no disco!");
			e.printStackTrace();
		}
	}

}
