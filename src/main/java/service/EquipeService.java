package service;

import com.google.gson.Gson;
import dao.EquipeDAO;
import model.Equipe;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import dao.ClienteDAO;
import java.io.IOException;

public class EquipeService {
    private EquipeDAO equipeDAO;
    private ClienteDAO clienteDAO;
	private static ClienteService clienteService = new ClienteService();
    public EquipeService() {
        try {
            equipeDAO = new EquipeDAO("equipe.dat");
            clienteDAO = new ClienteDAO("cliente.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Object add(Request request, Response response) {
        String[] membros = (request.queryParams("membros")).split(",");
        int IdCliente = Integer.parseInt(request.queryParams("idCliente"));
        String nomequipe = request.queryParams("nomeEquipe");
        
        String[] membrosSave = new String[membros.length];

        for (int i = 0 ; i < membros.length ; i++)
            membrosSave[i] = clienteDAO.getNameByEmail(membros[i]);
        
        int id = equipeDAO.getMaxId() + 1;
        Equipe equipe = new Equipe(nomequipe,IdCliente,id,membrosSave);

        equipeDAO.add(equipe);
        
        for (int i = 0; i < membros.length; i++){
            clienteService.salvaEquipe(membrosSave[i], equipe);
            System.out.println(membrosSave[i] + "foi adicionado na equipe " + equipe.getNomeEquipe());
        }

        response.status(201); // 201 Created
        return id;
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Equipe equipe = equipeDAO.get(id);

        if (equipe != null) {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");
            JSONObject result = new JSONObject();
            result.put("nomeEquipe",equipe.getNomeEquipe());

            return result;
        } else {
            response.status(404); // 404 Not found
            return "Equipe " + id + " não encontrado.";
        }

    }



    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Gson g = new Gson();
        Equipe equipeAux = g.fromJson(request.body(), Equipe.class);
        System.out.println(equipeAux);
        String nomeEquipe = equipeAux.getNomeEquipe();

        Equipe equipe = equipeDAO.get(id);

        if (equipe != null) {
            equipe.setNomeEquipe(nomeEquipe);
            equipeDAO.update(equipe);
            System.out.println(equipe);
            return id;
        } else {
            response.status(404); // 404 Not found
            return "equipe não encontrada.";
        }

    }

    public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.body());
        System.out.println(id);
        Equipe equipe = equipeDAO.get(id);

        if (equipe != null) {

            equipeDAO.remove(equipe);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "equipe não encontrado.";
        }
    }

    public Object getAll(Request request, Response response) {
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        JSONArray allProds = new JSONArray();
        for (Equipe equipe : equipeDAO.getAll()) {
            Equipe equipe1 = equipe;
            allProds.put(equipe1.toJson());
        }
        return allProds;
    }

    public Object getEquipesProp(Request request, Response response) {
        int IdCliente = Integer.parseInt(request.params(":IdCliente"));
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        JSONArray allProds = new JSONArray();
        for (Equipe equipe : equipeDAO.getAll()) {
            if(equipe.getIdDonoEquipe()==IdCliente){
                Equipe equipe1 = equipe;
                allProds.put(equipe1.toJson());
            }
        }
        return allProds;
    }

    public void pesquisa(Request request,Response response){
        String termo = request.params("palavra");
        System.out.println(termo);
    }
}
