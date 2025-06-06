package com.bancodeimagens.repository;
// Importações necessárias para o Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository; // Interface que fornece métodos prontos para CRUD
import org.springframework.stereotype.Repository; // Anotação para identificar como um repositório

import com.bancodeimagens.model.ImagemModel; // Importa o modelo de imagem que será manipulado pelo repositório
public interface ImagemRepository extends JpaRepository<ImagemModel, Long>  {
    
}
