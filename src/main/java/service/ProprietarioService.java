package service;

import com.google.gson.Gson;
import dao.ProprietarioDAO;

import model.LoginAux;
import model.Proprietario;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class ProprietarioService {
    private ProprietarioDAO proprietarioDAO;

    public ProprietarioService() {
        try {
            proprietarioDAO = new ProprietarioDAO("proprietario.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public Object loginCorreto(Request request,Response response)  {

        Gson g = new Gson();
        LoginAux login = g.fromJson(request.body(), LoginAux.class);

        String email = login.getEmail();
        String senha = login.getSenha();
        JSONObject result = new JSONObject();
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        if(proprietarioDAO.verificaLogin(email,senha)){
            result.put("loginCorreto",true);
        }
        else{
            result.put("loginCorreto",false);
        }
        result.put("idProp",proprietarioDAO.getIdProprietario(email,senha));
        return result;
    }


    public Object getInfoProprietario(Request request,Response response){
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        int idProp = Integer.parseInt(request.params(":id"));
        System.out.println(idProp);
        JSONObject result = new JSONObject();
        Proprietario proprietario = proprietarioDAO.getInfosProprietario(idProp);
        if(proprietario!=null){
            result.put("razaoSocial",proprietario.getRazaoSocial());
            result.put("emailProp",proprietario.getEmail());
            result.put("cpnjProp",proprietario.getCPNJ());
        }
        return result;
    }

    public boolean contaExiste(Request request){
        String email = request.queryParams("email");
        String cpnj = request.queryParams("CPNJ");
        return proprietarioDAO.verificaContaExiste(email,cpnj);
    }


    public Object add(Request request, Response response) {
        String razaoSocial = request.queryParams("razaoSocial");
        String CPNJ = request.queryParams("CPNJ");
        String email = request.queryParams("email");
        String senha = request.queryParams("senha");
        String telefone = request.queryParams("telefone");
        int id = proprietarioDAO.getMaxId() + 1;
        Proprietario proprietario = new Proprietario(id, CPNJ,razaoSocial,email,senha,telefone);

        proprietarioDAO.add(proprietario);

        //response.status(201); // 201 Created
        return id;
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Proprietario proprietario = proprietarioDAO.get(id);

        if (proprietario != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<proprietario>\n" +
                    "\t<id> " + proprietario.getId() + "</id>\n" +
                    "\t<cpnj> " + proprietario.getCPNJ() + "</cpnj>\n" +
                    "\t<razaoSocial> " + proprietario.getRazaoSocial() + "</razaoSocial>\n" +
                    "\t<email> " + proprietario.getEmail() + "</email>\n" +
                    "\t<senha> " + proprietario.getSenha() + "</senha>\n" +
                    "</proprietario>\n";
        } else {
            response.status(404); // 404 Not found
            return "Proprietario " + id + " não encontrado.";
        }

    }

    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Proprietario proprietario = (Proprietario) proprietarioDAO.get(id);

        if (proprietario != null) {
            proprietario.setCPNJ(request.queryParams("CPNJ"));
            proprietario.setRazaoSocial(request.queryParams("razaoSocial"));
            proprietario.setEmail(request.queryParams("email"));
            proprietario.setSenha(request.queryParams("senha"));

            proprietarioDAO.update(proprietario);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Proprietario não encontrado.";
        }

    }

    public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Proprietario proprietario = (Proprietario) proprietarioDAO.get(id);

        if (proprietario != null) {

            proprietarioDAO.remove(proprietario);

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
        for (Proprietario proprietario : proprietarioDAO.getAll()) {
            Proprietario proprietario1 = proprietario;
            allProds.put(proprietario1.toJson());
        }
        return allProds;
    }
}
