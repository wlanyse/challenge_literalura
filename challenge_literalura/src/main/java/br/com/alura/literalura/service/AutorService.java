package br.com.alura.literalura.service;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    private Scanner leitura = new Scanner(System.in);

    public void salvar (Autor autor) {
        autorRepository.save(autor);
    }


    public void listaLivrosPorAutor () {
        System.out.println("Por favor digite o nome do autor procurado: ");
        var nomeAutor = leitura.nextLine();
        var listaDeLivros = autorRepository.listaLivrosPorAutor(nomeAutor);
        listaDeLivros.forEach(l ->
                System.out.println(
                        "------------ LIVRO ---------------" + "\n" +
                                "Titulo: " + l.getTitulo() +"\n" +
                                "Autor: " + l.getAutor().getNome() +"\n" +
                                "Idioma: " + l.getIdioma() +"\n" +
                                "Numero de Downloads: " + l.getTotalDownloads() +"\n"));
    }

    public void listarAutoresCadastrados () {
        autorRepository.findAll().forEach(a ->
                System.out.println(
                        "Nome: " + a.getNome() + "\n" +
                                "Ano de Nascimento: " + a.getAnoNascimento() + "\n" +
                                "Ano de Falecimento: : " + a.getAnoFalecimento() + "\n" +
                                "Livros: " + a.listaTituloLivros() + "\n"));
    }

    public void listarLivrosCadastrados () {
        autorRepository.findAll().stream()
                .forEach(a -> a.getLivros().stream()
                        .forEach(l ->
                                System.out.println(
                                        "------------ LIVRO ---------------" + "\n" +
                                                "Titulo: " + l.getTitulo() +"\n" +
                                                "Autor: " + l.getAutor().getNome() +"\n" +
                                                "Idioma: " + l.getIdioma() +"\n" +
                                                "Numero de Downloads: " + l.getTotalDownloads() +"\n")));
    }

    public void salvarLivroDeAutorJaCadastrado(Autor autor, Livro livro) {
        var autorProcurado = autorRepository.findByNomeContainingIgnoreCase(autor.getNome());
        if (autorProcurado.isPresent()) {
            var autorEncontrado = autorProcurado.get();
            autorEncontrado.getLivros().stream()
                    .forEach(l ->
                            System.out.println(l.getAutor().listaTituloLivros()));
            autorEncontrado.setLivros(livro);
            salvar(autorEncontrado);
        } else {
            salvar(autor);
        }
    }

    public void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Por favor digite o ano desejado: ");
        var anoProcurado = leitura.nextInt();
        leitura.nextLine();
        var listaDeAutoresProcurados = autorRepository.listaAutoresEmDeterminadoAno(anoProcurado);
        if (listaDeAutoresProcurados.isEmpty()) {
            System.out.println("Nenhum autor encontrado para o ano de " + anoProcurado);
        } else {
            listaDeAutoresProcurados.forEach(autor ->
                    System.out.println(autor.getNome() + " \n " + autor.getAnoNascimento() + " \n " +
                            autor.getAnoFalecimento() + " \n " +  autor.listaTituloLivros() + " \n "));
        }
    }

    public void ListaLivrosEmDeterminadoIdioma() {
        System.out.println("Por favor digite o idioma procurado: ");
        var idiomaProcurado = leitura.nextLine();
        var listaDeLivros = autorRepository.ListaLivrosEmDeterminadoIdioma(idiomaProcurado.toLowerCase().trim());

        if (listaDeLivros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma " + idiomaProcurado);
        } else {
            listaDeLivros.forEach(l ->
                    System.out.println(l.getTitulo() + " \n " +"Autor: " + l.getAutor().getNome() + " \n " +
                            "Total de Downloads: " + l.getTotalDownloads() + " \n "));
        }
    }

    public void buscarTop5LivroMaisBaixados() {
        var top5List = autorRepository.buscarTop5LivrosMaisBaixados();
        top5List.forEach(l ->
                System.out.println(
                        "Titulo: " + l.getTitulo() + "\n" +
                                "Autor: " + l.getAutor().getNome() + "\n" +
                                "Total de Downloads: " + l.getTotalDownloads() + "\n"
                ));
    }
}