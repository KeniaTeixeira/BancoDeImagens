package com.bancodeimagens.service;

// Importações necessárias para manipulação de listas e objetos opcionais
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;  // Injeção de dependência
import org.springframework.stereotype.Service;               // Define a classe como um Service do Spring

import com.bancodeimagens.model.ImagemModel; // Importa o modelo de imagem
import com.bancodeimagens.repository.ImagemRepository; // Importa o repositório de imagens

@Service 
public class ImagemService {
     @Autowired
    private ImagemRepository repository;

    public List<ImagemModel> listarTodos() {
        // Chama o método findAll() do repository para buscar todos os alunos
        return repository.findAll();
    }
    public Optional<ImagemModel> buscarPorId(Long id) {
        // Chama o método findById() do repository para buscar a pessoa pelo ID
        return repository.findById(id);
    }
    public ImagemModel salvar(ImagemModel imagemModel) {
        // Chama o método save() do repository para salvar os dados no banco
        return repository.save(imagemModel);
    }
    public ImagemModel atualizar(ImagemModel imagemModel) {
        // Chama o método save() do repository para atualizar os dados no banco
        return repository.save(imagemModel);
    }
    public void deletar(Long id) {
        // Chama o método deleteById() do repository para remover o registro
        repository.deleteById(id);
    }
}
