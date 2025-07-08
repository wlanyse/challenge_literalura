package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository <Autor, Long> {

    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoFalecimento >= :anoProcurado")
    List<Autor> listaAutoresEmDeterminadoAno(Integer anoProcurado);

    @Query("SELECT l FROM Livro l WHERE l.idioma ILIKE %:idioma%")
    List<Livro> ListaLivrosEmDeterminadoIdioma(String idioma);

    @Query("SELECT l from Livro l ORDER BY l.totalDownloads DESC LIMIT 5")
    List<Livro> buscarTop5LivrosMaisBaixados();

    @Query("SELECT l " +
            "FROM Livro l " +
            "JOIN Autor a ON l.autor.id = a.id " +
            "WHERE a.nome ILIKE %:nomeAutor%")
    List<Livro> listaLivrosPorAutor(String nomeAutor);
}