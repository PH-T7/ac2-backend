package com.ac2.projetos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AC2 - Parte 1: Sistema de Controle de Projetos
 * Acesse o console H2 em: http://localhost:8080/h2-console
 * JDBC URL: jdbc:h2:mem:projetos_db
 */
@SpringBootApplication
public class ProjetosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjetosApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("  Sistema de Projetos rodando na porta 8080  ");
        System.out.println("  H2 Console: http://localhost:8080/h2-console");
        System.out.println("  JDBC URL: jdbc:h2:mem:projetos_db           ");
        System.out.println("==============================================\n");
    }
}
