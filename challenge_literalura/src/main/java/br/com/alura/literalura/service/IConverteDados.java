package br.com.alura.literalura.service;

public interface IConverteDados {
    <T> T converterDados(String json, Class<T> classe);
}
