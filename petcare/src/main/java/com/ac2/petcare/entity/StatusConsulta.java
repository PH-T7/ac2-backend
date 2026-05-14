package com.ac2.petcare.entity;

/**
 * Status do ciclo de vida de uma consulta.
 */
public enum StatusConsulta {
    AGENDADA,    // Aguardando atendimento
    REALIZADA,   // Atendimento concluído
    CANCELADA    // Cancelada pelo tutor ou clínica
}
