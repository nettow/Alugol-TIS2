package service;

import com.google.gson.Gson;
import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.QuadraDAO;
import model.Aluguel;
import model.AluguelAuxiliarJson;
import model.Cliente;
import model.LoginAux;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class AluguelService {
    private AluguelDAO aluguelDAO;

    public AluguelService() {
        try {
            aluguelDAO = new AluguelDAO("aluguel.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public Object add(Request request, Response response) throws IOException {
        Gson g = new Gson();
        AluguelAuxiliarJson aluguelAux = g.fromJson(request.body(), AluguelAuxiliarJson.class);
        int idCliente = aluguelAux.getIdCliente();
        int idQuadra = aluguelAux.getIdQuadra();
        float valorAluguel = aluguelAux.getValorAluguel();
        String dataEHora = aluguelAux.getDataEHora();
        System.out.println(valorAluguel);
        JSONObject result = new JSONObject();
        boolean quadraJaAlugada = quadraJaEstaAlugada(idQuadra,dataEHora);

        if(!quadraJaAlugada){
            int id = aluguelDAO.getMaxId() + 1;
            Aluguel aluguel = new Aluguel(id,idCliente,idQuadra,valorAluguel,dataEHora);
            aluguelDAO.add(aluguel);
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");
            result.put("aluguelFeito",true);
        }
        else{
            result.put("aluguelFeito",false);
        }

        return result;

    }

    public boolean quadraJaEstaAlugada(int idQuadra,String dataEHora){
        for (Aluguel aluguel : aluguelDAO.getAll()) {
                if(aluguel.getDataEHoraAluguel().equals(dataEHora) && aluguel.getIdQuadra()==idQuadra) {
                    return true;
                }
        }
        return false;
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Aluguel aluguel =  aluguelDAO.get(id);

        if (aluguel != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<aluguel>\n" +
                    "\t<id> " + aluguel.getIdAluguel() + "</id>\n" +
                    "\t<idCliente> " + aluguel.getIdCliente() + "</idCliente>\n" +
                    "\t<idQuadra> " + aluguel.getIdQuadra() + "</idQuadra>\n" +
                    "\t<valorAluguel> " + aluguel.getValorAluguel() + "</valorAluguel>\n" +
                    "\t<dataEHora> " + aluguel.getDataEHoraAluguel() + "</dataEHora>\n" +
                    "</aluguel>\n";
        } else {
            response.status(404); // 404 Not found
            return "Aluguel " + id + " n�o encontrado.";
        }

    }


    public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Aluguel aluguel = aluguelDAO.get(id);

        if (aluguel != null) {
            aluguelDAO.remove(aluguel);
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Aluguel não encontrado.";
        }
    }

    public Object getAll(Request request, Response response) {
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        JSONArray allAlugueis = new JSONArray();
        for (Aluguel aluguel : aluguelDAO.getAll()) {
            Aluguel aluguel1 = aluguel;
            allAlugueis.put(aluguel1.toJson());
        }
        return allAlugueis;
    }
}
