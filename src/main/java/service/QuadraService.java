package service;

import com.google.gson.Gson;
import dao.QuadraDAO;
import model.Quadra;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class QuadraService {
    private QuadraDAO quadraDAO;

    public QuadraService() {
        try {
            quadraDAO = new QuadraDAO("quadra.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getQuadraNome(int id){
        for (Quadra quadra : quadraDAO.getAll()) {
            if(quadra.getId()==id){
                Quadra quadra1 = quadra;
                return quadra1.getNomeQuadra();
            }
        }

        return "";
    }

    public String getRuaQuadra(int id){
        for (Quadra quadra : quadraDAO.getAll()) {
            if(quadra.getId()==id){
                Quadra quadra1 = quadra;
                return quadra1.getRua();
            }
        }

        return "";
    }

    public String getBairroQuadra(int id){
        for (Quadra quadra : quadraDAO.getAll()) {
            if(quadra.getId()==id){
                Quadra quadra1 = quadra;
                return quadra1.getBairro();
            }
        }

        return "";
    }

    public String getCidadeQuadra(int id){
        for (Quadra quadra : quadraDAO.getAll()) {
            if(quadra.getId()==id){
                Quadra quadra1 = quadra;
                return quadra1.getCidade();
            }
        }

        return "";
    }

    public boolean isQuadraDoProp(int idQuadra,int idProp){
        Quadra quadra = quadraDAO.get(idQuadra);
        if(quadra.getIdProp()==idProp)return true;
        else return false;
    }

    public Object add(Request request, Response response) {
        System.out.println(request.queryParams("capacidadeQuadra"));
        int idProp = Integer.parseInt(request.queryParams("idProp"));
        String nomeQuadra = request.queryParams("nomeQuadra");
        String linkImagem = request.queryParams("linkImagem");
        int capacidadeQuadra = Integer.parseInt(request.queryParams("capacidadeQuadra"));
        String resumoQuadra = request.queryParams("resumoQuadra");
        String descricaoQuadra = request.queryParams("descricaoQuadra");
        float valorReserva = Float.parseFloat(request.queryParams("valorReserva"));
        String rua = request.queryParams("rua");
        String bairro = request.queryParams("bairro");
        String cidade = request.queryParams("cidade");
        String modalidade = request.queryParams("modalidade");
        int id = quadraDAO.getMaxId() + 1;

        Quadra quadra = new Quadra(id,idProp,nomeQuadra,linkImagem,capacidadeQuadra,resumoQuadra,descricaoQuadra,valorReserva,rua,bairro,cidade,modalidade);

        quadraDAO.add(quadra);

        response.status(201); // 201 Created
        return id;
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Quadra quadra = quadraDAO.get(id);

        if (quadra != null) {
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");
            JSONObject result = new JSONObject();
            result.put("nomeQuadra",quadra.getNomeQuadra());
            result.put("idQuadra",quadra.getId());
            result.put("urlEspaco",quadra.getUrlEspaco());
            result.put("capacidade",quadra.getCapacidade());
            result.put("descricao",quadra.getDescricao());
            result.put("valorReserva",quadra.getValorReserva());
            result.put("rua",quadra.getRua());
            result.put("bairro",quadra.getBairro());
            result.put("cidade",quadra.getCidade());
            result.put("modalidade",quadra.getModalidade());

            return result;
        } else {
            response.status(404); // 404 Not found
            return "Quadra " + id + " não encontrado.";
        }

    }



    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Gson g = new Gson();
        Quadra quadraAux = g.fromJson(request.body(), Quadra.class);
        System.out.println(quadraAux.getCapacidade());
        System.out.println(quadraAux);
        String nomeQuadra = quadraAux.getNomeQuadra();
        String urlEspaco = quadraAux.getUrlEspaco();
        int capacidade = quadraAux.getCapacidade();
        String resumo = quadraAux.getResumo();
        String descricao = quadraAux.getDescricao();
        float valorReserva= quadraAux.getValorReserva();
        String rua = quadraAux.getRua();
        String bairro = quadraAux.getBairro();
        String cidade= quadraAux.getCidade();
        String modalidade = quadraAux.getModalidade();

        Quadra quadra = quadraDAO.get(id);

        if (quadra != null) {
            quadra.setNomeQuadra(nomeQuadra);
            quadra.setUrlEspaco(urlEspaco);
            quadra.setCapacidade(capacidade);
            quadra.setResumo(resumo);
            quadra.setDescricao(descricao);
            quadra.setValorReserva(valorReserva);
            quadra.setRua(rua);
            quadra.setBairro(bairro);
            quadra.setCidade(cidade);
            quadra.setModalidade(modalidade);
            quadraDAO.update(quadra);
            System.out.println(quadra);
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Quadra não encontrada.";
        }

    }

    public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.body());
        System.out.println(id);
        Quadra quadra = quadraDAO.get(id);

        if (quadra != null) {

            quadraDAO.remove(quadra);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "quadra não encontrado.";
        }
    }

    public Object getAll(Request request, Response response) {
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        JSONArray allProds = new JSONArray();
        for (Quadra quadra : quadraDAO.getAll()) {
            Quadra quadra1 = quadra;
            allProds.put(quadra1.toJson());
        }
        return allProds;
    }

    public Object getQuadrasProp(Request request, Response response) {
        int idProp = Integer.parseInt(request.params(":idProp"));
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        JSONArray allProds = new JSONArray();
        for (Quadra quadra : quadraDAO.getAll()) {
            if(quadra.getIdProp()==idProp){
                Quadra quadra1 = quadra;
                allProds.put(quadra1.toJson());
            }
        }
        return allProds;
    }

    public void pesquisa(Request request,Response response){
        String termo = request.params("palavra");
        System.out.println(termo);
    }
}
