package br.com.alura.literalura.principal;

import br.com.alura.literalura.DTO.ResponseDTO;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;
import br.com.alura.literalura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books?search=";

    @Autowired
    private AutorService service;

    public Principal (AutorService service) {
        this.service = service;
    };

    public void exibeMenu () {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em determinado ano
                5 - Listar livros em um determinado idioma
                6 - Listar top 5 livros mais baixados
                7 - Listar livros do autor buscado
                
                0 - Sair                                 
                """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    salvar();
                    break;
                case 2:
                    listaLivrosResgistrados();
                    break;
                case 3:
                    listaAutoresResgistrados();
                    break;

                case 4:
                    ListaAutoresVivosEmDeterminadoAno();
                    break;
                case 5:
                    ListaLivrosEmDeterminadoIdioma();
                    break;
                case 6:
                    listaTop5LivroMaisBaixados();
                    break;

                case 7:
                    listaLivrosPorAutor();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }

    private void listaLivrosPorAutor() {
        service.listaLivrosPorAutor();
    }

    private void listaTop5LivroMaisBaixados() {
        service.buscarTop5LivroMaisBaixados();
    }

    private void ListaLivrosEmDeterminadoIdioma() {
        service.ListaLivrosEmDeterminadoIdioma();
    }

    private void ListaAutoresVivosEmDeterminadoAno() {
        service.listarAutoresVivosEmDeterminadoAno();
    }


    private void listaAutoresResgistrados() {
        service.listarAutoresCadastrados();
    }

    private void listaLivrosResgistrados() {
        service.listarLivrosCadastrados();
    }

    private Livro getDadosLivroEAutor () {
        var responseDTO = getDados();
        var autor = new Autor(responseDTO.results().getFirst().autorDTOList().getFirst());
        var livro = new Livro(responseDTO.results().getFirst());
        autor.setLivros(livro);
        return livro;
    }


    private ResponseDTO getDados() {
        System.out.println("Digite o titulo do livro que deseja buscar: ");
        var livroBuscado = leitura.nextLine();
        var json = consumoAPI.obterDados(ENDERECO + livroBuscado.replace(" ", "%20"));
        ResponseDTO responseDTO = conversor.converterDados(json, ResponseDTO.class);
        return responseDTO;
    }


    private void salvar () {
        var livro = getDadosLivroEAutor();
        service.salvarLivroDeAutorJaCadastrado(livro.getAutor(), livro);
    }


}