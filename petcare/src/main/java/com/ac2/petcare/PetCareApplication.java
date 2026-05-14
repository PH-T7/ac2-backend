package com.ac2.petcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AC2 - Parte 2: Sistema PetCare
 * Acesse o console H2 em: http://localhost:8081/h2-console
 * JDBC URL: jdbc:h2:mem:petcare_db
 */
@SpringBootApplication
public class PetCareApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetCareApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("  PetCare rodando na porta 8081               ");
        System.out.println("  H2 Console: http://localhost:8081/h2-console ");
        System.out.println("  JDBC URL: jdbc:h2:mem:petcare_db             ");
        System.out.println("==============================================\n");
    }
}
