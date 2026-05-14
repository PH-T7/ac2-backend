package com.ac2.petcare.entity;

/**
 * Especialidade do veterinário.
 * Regra: veterinário só atende animais da sua especialidade.
 */
public enum Especialidade {
    CLINICO_GERAL,      // atende qualquer animal
    FELINO,             // especialista em gatos
    CANINO,             // especialista em cães
    EXOTICO,            // répteis, pássaros, etc.
    CIRURGIAO,          // cirurgias em geral
    DERMATOLOGISTA,     // pele e pelagem
    ORTOPEDISTA         // ossos e articulações
}
