package service;

import com.google.gson.Gson;
import dao.AluguelDAO;
import model.Aluguel;
import model.AluguelAuxiliarJson;
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

    public Object getAlugueisCliente(Request request,Response response){
        int idCliente = Integer.parseInt(request.params(":id"));
        QuadraService quadraService = new QuadraService();
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        JSONArray allAlugueis = new JSONArray();
        String dataETime="";
        for (Aluguel aluguel : aluguelDAO.getAll()) {
            Aluguel aluguel1 = aluguel;
            JSONObject result = new JSONObject();
            if(aluguel1.getIdCliente()==idCliente){
                result.put("nomeQuadra",quadraService.getQuadraNome(aluguel1.getIdQuadra()));
                result.put("ruaQuadra",quadraService.getRuaQuadra(aluguel1.getIdQuadra()));
                result.put("bairroQuadra",quadraService.getBairroQuadra(aluguel1.getIdQuadra()));
                result.put("cidadeQuadra",quadraService.getCidadeQuadra(aluguel1.getIdQuadra()));
                result.put("valorAluguel",aluguel1.getValorAluguel());
                System.out.println(aluguel1.getDataEHoraAluguel());
                dataETime = aluguel1.getDataEHoraAluguel().substring(8,10)+"/"+aluguel1.getDataEHoraAluguel().substring(5,7)+"/"+aluguel1.getDataEHoraAluguel().substring(0,4)+" as "+aluguel1.getDataEHoraAluguel().substring(11,16);
                result.put("dataEHora",dataETime);
                allAlugueis.put(result);
            }
        }
        return allAlugueis;

    }

    public float getFaturamentoProprietario(int id) {
        QuadraService quadraService = new QuadraService();
        float sum=0.0F;
        for (Aluguel aluguel : aluguelDAO.getAll()) {
            Aluguel aluguel1 = aluguel;
            if (quadraService.isQuadraDoProp(aluguel1.getIdQuadra(), id)) {
                sum+=aluguel1.getValorAluguel();
            }
        }
        return sum;
    }

    public Object getAlugueisProp(Request request,Response response){
        int idProp = Integer.parseInt(request.params(":id"));
        QuadraService quadraService = new QuadraService();
        ClienteService clienteService = new ClienteService();
        response.header("Content-Type", "application/json");
        response.header("Content-Encoding", "UTF-8");
        JSONArray allAlugueis = new JSONArray();
        String dataETime="";
        for (Aluguel aluguel : aluguelDAO.getAll()) {
            Aluguel aluguel1 = aluguel;
            JSONObject result = new JSONObject();
            if(quadraService.isQuadraDoProp(aluguel1.getIdQuadra(),idProp)){
                result.put("nomeQuadra",quadraService.getQuadraNome(aluguel1.getIdQuadra()));
                result.put("ruaQuadra",quadraService.getRuaQuadra(aluguel1.getIdQuadra()));
                result.put("bairroQuadra",quadraService.getBairroQuadra(aluguel1.getIdQuadra()));
                result.put("cidadeQuadra",quadraService.getCidadeQuadra(aluguel1.getIdQuadra()));
                result.put("valorAluguel",aluguel1.getValorAluguel());
                dataETime = aluguel1.getDataEHoraAluguel().substring(8,10)+"/"+aluguel1.getDataEHoraAluguel().substring(5,7)+"/"+aluguel1.getDataEHoraAluguel().substring(0,4)+" as "+aluguel1.getDataEHoraAluguel().substring(11,16);
                result.put("dataEHora",dataETime);
                result.put("nomeCliente",clienteService.getNomeCliente(aluguel1.getIdCliente()));
                allAlugueis.put(result);
            }
        }
        return allAlugueis;

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
