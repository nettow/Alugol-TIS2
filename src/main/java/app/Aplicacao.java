package app;


import service.*;

import static spark.Spark.*;

public class Aplicacao {
	
	private static ClienteService clienteService = new ClienteService();
	private static ProprietarioService proprietarioService = new ProprietarioService();
    private static QuadraService quadraService = new QuadraService();
    private static EquipeService equipeService = new EquipeService();
    private static AluguelService aluguelService = new AluguelService();

    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");

        get("/",(request, response) -> {
            response.redirect("index.html");
            return null;
        });

        // Requisições CLIENTE

        get("/cadastrarCliente",(request, response) -> {
            response.redirect("cadastrar-cliente.html");
            return null;
        });

        get("/loginCliente",(request, response) -> {
           response.redirect("login.html");
           return null;
        });

        get("/clientes/:id", (request, response) -> clienteService.get(request, response));

        post("/clientes", (request, response) -> {
            boolean contaJaExiste = clienteService.contaExiste(request);
            if(!contaJaExiste){
                clienteService.add(request, response);
                response.redirect("perfil-cliente.html");
            }
            return null;
        });

        post("/homeCliente",(request, response) -> clienteService.loginCorreto(request,response));

        get("/clientes",(request, response) -> clienteService.getAll(request,response));

        //  delete("/clientes/:id", (request, response) -> clienteService.remove(request, response));

        //  post("/editarCliente/:id", (request, response) -> clienteService.update(request, response));

        get("/recuperaDadosCliente/:id",(request, response) -> clienteService.getInfoCliente(request,response));

        // Requisições PROPRIETÁRIO

        post("/homeProprietario",(request, response) -> proprietarioService.loginCorreto(request,response));

        //  post("/editarProprietario/:id", (request, response) -> proprietarioService.update(request, response));

        get("/cadastrarProprietario",(request, response) -> {
            response.redirect("cadastrar-proprietario.html");
            return null;
        });

        post("/aluguel",(request, response) -> aluguelService.add(request,response));

        post("/proprietarios",(request, response) ->{
            boolean contaJaExiste = proprietarioService.contaExiste(request);
            if(!contaJaExiste){
                proprietarioService.add(request,response);
                response.redirect("login.html");
            }
            return null;
        });

        get("/recuperaDadosProprietario/:id",(request, response) -> proprietarioService.getInfoProprietario(request,response));

        get("/cadastrarEspaco",(request, response) -> {
            response.redirect("cadastrar-espaco.html");
            return null;
        });

        get("/proprietarios",((request, response) -> proprietarioService.getAll(request,response)));

        get("/proprietarios/:id",((request, response) -> proprietarioService.get(request,response)));

        // Requisições QUADRA

        post("/quadras",(request, response) -> {
            quadraService.add(request,response);
            response.redirect("consulta-quadras.html");
            return null;
        });

        get("/quadras",((request, response) -> quadraService.getAll(request,response)));

        get("/recuperaDetalhesQuadra/:id", (request, response) -> quadraService.get(request,response));

        get("/quadras/:id",((request, response) -> quadraService.get(request,response)));

        get("/consultaQuadras/:idProp",(request, response) -> quadraService.getQuadrasProp(request,response));

        get("/pesquisaQuadras",(request, response) -> {
            quadraService.pesquisa(request, response);
            return null;
        });

        post("/editarQuadras/:id", (request, response) -> quadraService.update(request, response));

        get("/getJsonTodasQuadras", (request, response) -> quadraService.getAll(request,response));

        post("/deletaQuadra",(request, response) -> quadraService.remove(request,response));

        // Requisições EQUIPES

        post("/equipes",(request, response) -> {
            equipeService.add(request,response);
            response.redirect("perfil-cliente.html");
            return null;
        });

        get("/equipes",((request, response) -> equipeService.getAll(request,response)));

        get("/equipes/:id",((request, response) -> equipeService.get(request,response)));

        //System.out.println("Pressione alguma tecla para terminar...");
        //stop();
        //System.exit(0);
    }
}