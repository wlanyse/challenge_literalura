package br.com.alura.literalura.model;

import br.com.alura.literalura.DTO.AutorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    @Column(nullable = false)
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();


    public Autor (AutorDTO autorDTO) {
        this.nome = autorDTO.nome();
        this.anoNascimento = Integer.valueOf(autorDTO.dataNascimento());

        try {
            this.anoFalecimento = Integer.valueOf(autorDTO.dataFalecimento());
        } catch (NullPointerException e) {
            this.anoFalecimento = null;
        }
    }

    public List<String> listaTituloLivros () {
        var titulosDosLivrosListados = livros.stream()
                .map(l -> l.getTitulo())
                .collect(Collectors.toList());
        return titulosDosLivrosListados;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Livro livro) {
        livros.add(livro);
        livros.forEach(l -> l.setAutor(this));
        this.livros = livros;
    }
}
