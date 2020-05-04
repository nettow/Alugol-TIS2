package service;

import dao.ProprietarioDAO;
import dao.QuadraDAO;
import model.Proprietario;
import model.Quadra;
import org.json.JSONArray;
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

    public Object add(Request request, Response response) {
        int idProp = Integer.parseInt(request.queryParams("idProp"));
        String nomeQuadra = request.queryParams("nomeQuadra");
        String rua = request.queryParams("rua");
        String bairro = request.queryParams("bairro");
        String cidade = request.queryParams("cidade");
        String modalidade = request.queryParams("modalidade");
        int id = quadraDAO.getMaxId() + 1;
        Quadra quadra = new Quadra(id, idProp,nomeQuadra,rua,bairro,cidade,modalidade);

        quadraDAO.add(quadra);

        response.status(201); // 201 Created
        return id;
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Quadra quadra = quadraDAO.get(id);

        if (quadra != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<quadra>\n" +
                    "\t<id> " + quadra.getId() + "</id>\n" +
                    "\t<idProp> " + quadra.getIdProp() + "</idProp>\n" +
                    "\t<nomeQuadra> " + quadra.getNomeQuadra() + "</nomeQuadra>\n" +
                    "\t<rua> " + quadra.getRua() + "</rua>\n" +
                    "\t<bairro> " + quadra.getBairro() + "</bairro>\n" +
                    "\t<cidade> " + quadra.getCidade() + "</cidade>\n" +
                    "\t<modalidade> " + quadra.getModalidade() + "</modalidade>\n" +
                    "</quadra>\n";
        } else {
            response.status(404); // 404 Not found
            return "Quadra " + id + " não encontrado.";
        }

    }



    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Quadra quadra = (Quadra) quadraDAO.get(id);

        if (quadra != null) {
            quadra.setNomeQuadra(request.queryParams("nomeQuadra"));
            quadra.setRua(request.queryParams("rua"));
            quadra.setBairro(request.queryParams("bairro"));
            quadra.setCidade(request.queryParams("cidade"));
            quadraDAO.update(quadra);

            return id;
        } else {
            response.status(404); // 404 Not found
            return "Proprietario não encontrado.";
        }

    }

    public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Quadra quadra = (Quadra) quadraDAO.get(id);

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
}
