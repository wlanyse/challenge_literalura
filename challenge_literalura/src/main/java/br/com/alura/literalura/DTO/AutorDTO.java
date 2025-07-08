package br.com.alura.literalura.DTO;


import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorDTO (
        @JsonAlias("name")
        String nome,

        @JsonAlias("birth_year")
        String dataNascimento,

        @JsonAlias("death_year")
        String dataFalecimento
)
{
}
