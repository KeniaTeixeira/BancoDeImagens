package com.bancodeimagens.model;

// Importações necessárias para o mapeamento JPA e Lombok
import jakarta.persistence.Column;           // Define colunas no banco de dados
import jakarta.persistence.Entity;           // Define esta classe como uma entidade JPA
import jakarta.persistence.GeneratedValue;   // Define geração automática de IDs
import jakarta.persistence.GenerationType;   // Define a estratégia de geração de IDs
import jakarta.persistence.Id;               // Define o identificador (Primary Key) da entidade
import jakarta.persistence.PrePersist;       // Executa lógica antes de salvar no banco de dados
import jakarta.persistence.Table;            // Define o nome da tabela no banco de dados
import lombok.Getter;                        // Lombok - Gera os métodos GET automaticamente
import lombok.NoArgsConstructor;             // Lombok - Gera um construtor sem argumentos
import lombok.Setter;                        // Lombok - Gera os métodos SET automaticamente

// Anotação para informar que esta classe representa uma entidade do banco de dados
@Entity

// Anotação para definir o nome da tabela que será criada no banco
@Table(name = "imagens") 

@Getter
@Setter

// Lombok - Gera um construtor sem argumentos
@NoArgsConstructor
public class ImagemModel {   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String url;
}
