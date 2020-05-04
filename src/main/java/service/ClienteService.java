package service;

import dao.ClienteDAO;
import model.Cliente;
import org.json.JSONArray;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class ClienteService {

	private ClienteDAO clienteDAO;

	public ClienteService() {
		try {
			clienteDAO = new ClienteDAO("cliente.dat");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean contaExiste(Request request){
		String email = request.queryParams("email");
		String cpf = request.queryParams("CPF");
		return clienteDAO.verificaContaExiste(email,cpf);
	}
	public boolean loginCorreto(Request request){
		String email = request.queryParams("email");
		String senha = request.queryParams("password");
		return clienteDAO.verificaLogin(email,senha);
	}
	public Object add(Request request, Response response) {
		String nome = request.queryParams("nome");
		String CPF = request.queryParams("CPF");
		String email = request.queryParams("email");
		String senha = request.queryParams("password");
		int idade = Integer.parseInt(request.queryParams("idade"));

		int id = clienteDAO.getMaxId() + 1;
		Cliente cliente = new Cliente(id,CPF,nome,email,senha,idade);

		clienteDAO.add(cliente);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Cliente cliente = (Cliente) clienteDAO.get(id);
		
        if (cliente != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<cliente>\n" +
            		"\t<id> " + cliente.getId() + "</id>\n" +
					"\t<cpf> " + cliente.getCPF() + "</cpf>\n" +
            		"\t<nome> " + cliente.getNome() + "</nome>\n" +
            		"\t<email> " + cliente.getEmail() + "</email>\n" +
            		"\t<senha> " + cliente.getSenha() + "</senha>\n" +
            		"\t<idade> " + cliente.getIdade() + "</idade>\n" +
            		"</cliente>\n";
        } else {
            response.status(404); // 404 Not found
            return "Cliente " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

		Cliente cliente = (Cliente) clienteDAO.get(id);

        if (cliente != null) {
        	cliente.setCPF(request.queryParams("CPF"));
        	cliente.setNome(request.queryParams("nome"));
        	cliente.setEmail(request.queryParams("email"));
        	cliente.setSenha(request.queryParams("senha"));
        	cliente.setIdade(Integer.parseInt(request.queryParams("idade")));

        	clienteDAO.update(cliente);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Bem de consumo não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
		System.out.println("Entrou aqui");
        int id = Integer.parseInt(request.params(":id"));

		Cliente bemDeConsumo = (Cliente) clienteDAO.get(id);

        if (bemDeConsumo != null) {

        	clienteDAO.remove(bemDeConsumo);

        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Bem de consumo não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");
		JSONArray allProds = new JSONArray();
		for (Cliente cliente : clienteDAO.getAll()) {
			Cliente cliente1 = cliente;
			allProds.put(cliente1.toJson());
		}
		return allProds;
	}

}
