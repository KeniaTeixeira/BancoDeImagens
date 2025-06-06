package com.bancodeimagens.controller;
// Importações necessárias
import java.util.List; // Para lidar com listas de objetos

import org.springframework.beans.factory.annotation.Autowired; // Injeção de dependência
import org.springframework.http.ResponseEntity; // Representa respostas HTTP
import org.springframework.web.bind.annotation.DeleteMapping; // Mapeia requisições DELETE
import org.springframework.web.bind.annotation.GetMapping; // Mapeia requisições GET
import org.springframework.web.bind.annotation.PathVariable; // Captura parâmetros da URL
import org.springframework.web.bind.annotation.PostMapping; // Mapeia requisições POST
import org.springframework.web.bind.annotation.PutMapping; // Mapeia requisições PUT
import org.springframework.web.bind.annotation.RequestBody; // Indica que o corpo da requisição é o objeto
import org.springframework.web.bind.annotation.RequestMapping; // Mapeia o caminho base da API
import org.springframework.web.bind.annotation.RestController; // Indica que esta classe é um Controller REST
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bancodeimagens.model.ImagemModel; // Importa o modelo de imagem
import com.bancodeimagens.service.ImagemService; // Importa o serviço de imagem

// Anotação que define esta classe como um Controller REST
@RestController

@CrossOrigin(origins = "*")
// Caminho base para todas as requisições deste controller
@RequestMapping("/api/imagens")

public class ImagemController {
    // Injeção de dependência do serviço que vai realizar a lógica de negócio
    @Autowired
    private ImagemService service;

    @GetMapping
    public List<ImagemModel> listarTodos() {
        // Chama o método do service que retorna a lista
        return service.listarTodos();
    }

    /**
     * Método GET para buscar uma imagem específica pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImagemModel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                     .map(ResponseEntity::ok) // Converte o resultado em ResponseEntity com status 200
                     .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrar
    }

    /**
     * Método POST para salvar
     */
    @PostMapping
    public ImagemModel salvar(@RequestBody ImagemModel imagemModel) {
        // Chama o método de salvar do service e retorna o objeto persistido
        return service.salvar(imagemModel);
    }

    /**
     * Método PUT para atualizar
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImagemModel> atualizar(@PathVariable Long id, @RequestBody ImagemModel imagemModel) {
        // Verifica se o ID existe no banco de dados
        if (!service.buscarPorId(id).isPresent()) {
            // Se não encontrar, retorna 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
        // Define o ID no objeto (caso não tenha sido passado no corpo)
        imagemModel.setId(id);
        // Salva o objeto atualizado e retorna 200 (OK)
        return ResponseEntity.ok(service.salvar(imagemModel));
    }

    /**
     * Método DELETE para excluir
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Verifica se o ID existe no banco de dados
        if (!service.buscarPorId(id).isPresent()) {
            // Se não encontrar, retorna 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
        // Se encontrar, chama o método para deletar
        service.deletar(id);
        // Retorna 204 (No Content), que significa que foi deletado com sucesso, mas sem conteúdo
        return ResponseEntity.noContent().build();
    }
}
