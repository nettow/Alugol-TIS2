package app;


import service.ClienteService;
import service.EquipeService;
import service.ProprietarioService;
import service.QuadraService;

import static spark.Spark.*;

public class Aplicacao {
	
	private static ClienteService clienteService = new ClienteService();
	private static ProprietarioService proprietarioService = new ProprietarioService();
    private static QuadraService quadraService = new QuadraService();
    private static EquipeService equipeService = new EquipeService();
    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");


        get("/",(request, response) -> {
            response.redirect("index.html");
            return null;
        });

        get("/loginCliente",(request, response) -> {
           response.redirect("login.html");
           return null;
        });

        post("/homeCliente",(request, response) -> clienteService.loginCorreto(request,response));

        post("/homeProprietario",(request, response) -> proprietarioService.loginCorreto(request,response));

        get("/cadastrarCliente",(request, response) -> {
             response.redirect("cadastrar-cliente.html");
             return null;
        });

        post("/clientes", (request, response) -> {
            boolean contaJaExiste = clienteService.contaExiste(request);
            if(!contaJaExiste){
                clienteService.add(request, response);
                response.redirect("perfil-cliente.html");
            }
            return null;
        });


        post("/proprietarios",(request, response) ->{
            boolean contaJaExiste = proprietarioService.contaExiste(request);
            if(!contaJaExiste){
                proprietarioService.add(request,response);
                response.redirect("login.html");
            }
            return null;
        });

        get("/cadastrarEspaco",(request, response) -> {
            response.redirect("cadastrar-espaco.html");
            return null;
        });

        post("/deletaQuadra",(request, response) -> quadraService.remove(request,response));

        get("/recuperaDadosCliente/:id",(request, response) -> clienteService.getInfoCliente(request,response));

        get("/recuperaDadosProprietario/:id",(request, response) -> proprietarioService.getInfoProprietario(request,response));

        post("/quadras",(request, response) -> {
            quadraService.add(request,response);
            response.redirect("consulta-quadras.html");
            return null;
        });

        post("/equipes",(request, response) -> {
            equipeService.add(request,response);
            response.redirect("perfil-cliente.html");
            return null;
        });

        get("/equipes/:id",((request, response) -> equipeService.get(request,response)));

        get("/recuperaDetalhesQuadra/:id", (request, response) -> quadraService.get(request,response));

        get("/clientes/:id", (request, response) -> clienteService.get(request, response));

        get("/proprietarios/:id",((request, response) -> proprietarioService.get(request,response)));

        get("/quadras/:id",((request, response) -> quadraService.get(request,response)));

        get("/consultaQuadras/:idProp",(request, response) -> quadraService.getQuadrasProp(request,response));

        get("/pesquisaQuadras",(request, response) -> {
             quadraService.pesquisa(request, response);
             return null;
        });

        post("/editarQuadras/:id", (request, response) -> quadraService.update(request, response));

        //delete("/clientes/:id", (request, response) -> clienteService.remove(request, response));

        get("/getJsonTodasQuadras", (request, response) -> quadraService.getAll(request,response));

        get("/clientes",(request, response) -> clienteService.getAll(request,response));
        get("/proprietarios",((request, response) -> proprietarioService.getAll(request,response)));

        get("/quadras",((request, response) -> quadraService.getAll(request,response)));
        get("/equipes",((request, response) -> equipeService.getAll(request,response)));

        //System.out.println("Pressione alguma tecla para terminar...");
        
        //stop();
        
        //System.exit(0);
        
    }
}

/*
function pesquisaQuadras(){
  console.log(palavra)
  request.open('GET', requestURL);
  request.responseType = 'json';
  request.send();
  request.onload = pesquisa($('#myInput').val());
}
function pesquisa(palavra){
  var quadras = request.response;
  const div = document.getElementById('cardsCollection');
  for (var i = 0; i <= quadras.length;i++){
  if(${quadras[i].nomeQuadra}.includes(palavra) || ${quadras[i].cidade}.includes(palavra)||${quadras[i].rua}.includes(palavra)||${quadras[i].bairro}.includes(palavra)){
    div.innerHTML += `<div class="card" style="width: 18rem;">
    <div class="card-body">
      <h3 class="card-title">${quadras[i].nomeQuadra}</h3>
      <h5>${quadras[i].modalidade}</h5>
      <p class="card-title text-warning">Localizacao</p>
      <p class="card-text">Rua: ${quadras[i].rua}</p>
      <p class="card-text">Bairro: ${quadras[i].bairro}</p>
      <p class="card-text">Cidade: ${quadras[i].cidade}</p>
      <a href="#" class="btn btn-success">Ver mais +</a>
    </div>
  </div>`;
 }
    console.log(quadras);
 }
}
 */