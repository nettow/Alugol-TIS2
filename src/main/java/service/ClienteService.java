package service;

import com.google.gson.Gson;
import dao.ClienteDAO;
import model.Cliente;
import model.LoginAux;
import org.json.JSONArray;
import org.json.JSONObject;

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


	public String getNomeCliente(int id){
		for (Cliente cliente : clienteDAO.getAll()) {
			Cliente cliente1 = cliente;
			if(cliente1.getId()==id){
				return  cliente1.getNome();
			}
		}
		return "";
	}
	public boolean contaExiste(Request request){
		String email = request.queryParams("email");
		String cpf = request.queryParams("CPF");
		return clienteDAO.verificaContaExiste(email,cpf);
	}
	public Object loginCorreto(Request request,Response response)  {

		Gson g = new Gson();
		LoginAux login = g.fromJson(request.body(), LoginAux.class);

		String email = login.getEmail();
		String senha = login.getSenha();
		JSONObject result = new JSONObject();
		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");
		if(clienteDAO.verificaLogin(email,senha)){
			result.put("loginCorreto",true);
		}
		else{
			result.put("loginCorreto",false);
		}
		result.put("idCliente",clienteDAO.getIdCliente(email,senha));
		return result;
	}

	public Object getInfoCliente(Request request,Response response){
		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");
		int idCliente = Integer.parseInt(request.params(":id"));
		System.out.println(idCliente);
		JSONObject result = new JSONObject();
		Cliente cliente = clienteDAO.getInfosCliente(idCliente);
		if(cliente!=null){
			result.put("nomeCliente",cliente.getNome());
			result.put("emailCliente",cliente.getEmail());
			result.put("senhaCliente",cliente.getSenha());
			result.put("cpfCliente",cliente.getCPF());
			result.put("idadeCliente",cliente.getIdade());
			result.put("equipeCliente",cliente.getEquipe());
		}
		return result;

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
            return "Cliente " + id + " n�o encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Gson g = new Gson();
		Cliente clienteAux = g.fromJson(request.body(), Cliente.class);

		String nome = clienteAux.getNome();
		String cpf = clienteAux.getCPF();
		String email = clienteAux.getEmail();
		String senha = clienteAux.getSenha();
		int idade = clienteAux.getIdade();

		Cliente cliente = clienteDAO.get(id);
		JSONObject result = new JSONObject();
        if (cliente != null) {
        	cliente.setCPF(cpf);
        	cliente.setNome(nome);
        	cliente.setEmail(email);
        	cliente.setSenha(senha);
        	cliente.setIdade(idade);
        	clienteDAO.update(cliente);
        	result.put("atualizado",true);
        } else {
        	result.put("atualizado",false);
        }

        return result;

	}

	public void salvaEquipe(String nomeMembro,String nomeTime){
		Cliente cliente = clienteDAO.getCliente(nomeMembro);
		cliente.setEquipe(nomeTime);
		System.out.println(cliente.getNome() + " foi encontrado e sua equipe definida foi: " + nomeTime);
		clienteDAO.update(cliente);
	}

	public Object remove(Request request, Response response) {
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
