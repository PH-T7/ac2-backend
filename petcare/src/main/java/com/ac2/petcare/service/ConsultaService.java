package com.ac2.petcare.service;

import com.ac2.petcare.dto.ConsultaRequest;
import com.ac2.petcare.dto.ConsultaResponse;
import com.ac2.petcare.entity.*;
import com.ac2.petcare.exception.RecursoNaoEncontradoException;
import com.ac2.petcare.exception.RegraDeNegocioException;
import com.ac2.petcare.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Service de Consultas — principais regras de negócio do PetCare:
 * REGRA 1: Não permitir conflito de agenda do veterinário
 * REGRA 2: Veterinário atende apenas sua especialidade
 * REGRA 3: Consulta não pode ser agendada no passado
 */
@Service
public class ConsultaService {

    private static final Logger log = LoggerFactory.getLogger(ConsultaService.class);

    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;
    private final VeterinarioRepository veterinarioRepository;

    public ConsultaService(ConsultaRepository consultaRepository,
                           AnimalRepository animalRepository,
                           VeterinarioRepository veterinarioRepository) {
        this.consultaRepository = consultaRepository;
        this.animalRepository = animalRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @Transactional
    public ConsultaResponse agendar(ConsultaRequest request) {
        Animal animal = animalRepository.findById(request.animalId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Animal não encontrado: " + request.animalId()));

        Veterinario vet = veterinarioRepository.findById(request.veterinarioId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Veterinário não encontrado: " + request.veterinarioId()));

        // REGRA 1: Não pode agendar no passado
        if (request.dataHora().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("Não é possível agendar consulta para uma data/hora no passado");
        }

        // REGRA 2: Verificar compatibilidade de especialidade
        validarEspecialidade(vet, animal);

        // REGRA 3: Verificar conflito de agenda (intervalo mínimo: 30 min)
        LocalDateTime inicio = request.dataHora().minusMinutes(29);
        LocalDateTime fim = request.dataHora().plusMinutes(29);
        List<Consulta> conflitos = consultaRepository.findConflitosDeAgenda(vet.getId(), inicio, fim);
        if (!conflitos.isEmpty()) {
            throw new RegraDeNegocioException(
                "Veterinário " + vet.getNome() + " já possui consulta próxima ao horário " +
                request.dataHora() + ". Intervalo mínimo: 30 minutos."
            );
        }

        Consulta consulta = new Consulta(request.dataHora(), request.motivo(), animal, vet);
        Consulta salva = consultaRepository.save(consulta);
        log.info("Consulta agendada: animal={}, vet={}", animal.getNome(), vet.getNome());
        return ConsultaResponse.from(salva);
    }

    private void validarEspecialidade(Veterinario vet, Animal animal) {
        Especialidade esp = vet.getEspecialidade();
        String especie = animal.getEspecie().toLowerCase();

        if (esp == Especialidade.CLINICO_GERAL) return;

        if (esp == Especialidade.FELINO) {
            if (!especie.contains("gato") && !especie.contains("felino") && !especie.contains("gata")) {
                throw new RegraDeNegocioException(
                    "Vet. " + vet.getNome() + " é especialista em FELINOS e não atende " + animal.getEspecie()
                );
            }
        } else if (esp == Especialidade.CANINO) {
            if (!especie.contains("cão") && !especie.contains("cachorro") && !especie.contains("canino") && !especie.contains("cao")) {
                throw new RegraDeNegocioException(
                    "Vet. " + vet.getNome() + " é especialista em CANINOS e não atende " + animal.getEspecie()
                );
            }
        } else if (esp == Especialidade.EXOTICO) {
            if (especie.contains("cão") || especie.contains("cachorro") || especie.contains("gato") || especie.contains("gata")) {
                throw new RegraDeNegocioException(
                    "Vet. " + vet.getNome() + " é especialista em EXÓTICOS. Para cães/gatos, use clínico geral."
                );
            }
        }
    }

    @Transactional
    public ConsultaResponse finalizarConsulta(Long id, String diagnostico, String prescricao) {
        Consulta consulta = consultaRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada: " + id));
        if (consulta.getStatus() == StatusConsulta.CANCELADA)
            throw new RegraDeNegocioException("Não é possível finalizar uma consulta cancelada");
        if (consulta.getStatus() == StatusConsulta.REALIZADA)
            throw new RegraDeNegocioException("Esta consulta já foi realizada");
        consulta.setDiagnostico(diagnostico);
        consulta.setPrescricao(prescricao);
        consulta.setStatus(StatusConsulta.REALIZADA);
        return ConsultaResponse.from(consultaRepository.save(consulta));
    }

    @Transactional
    public ConsultaResponse cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada: " + id));
        if (consulta.getStatus() != StatusConsulta.AGENDADA)
            throw new RegraDeNegocioException("Só é possível cancelar consultas AGENDADAS");
        consulta.setStatus(StatusConsulta.CANCELADA);
        return ConsultaResponse.from(consultaRepository.save(consulta));
    }

    @Transactional(readOnly = true)
    public ConsultaResponse buscarPorId(Long id) {
        return consultaRepository.findByIdWithDetails(id)
            .map(ConsultaResponse::from)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> historicoAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId))
            throw new RecursoNaoEncontradoException("Animal não encontrado: " + animalId);
        return consultaRepository.findHistoricoByAnimalId(animalId).stream().map(ConsultaResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> agendaFuturaVeterinario(Long vetId) {
        if (!veterinarioRepository.existsById(vetId))
            throw new RecursoNaoEncontradoException("Veterinário não encontrado: " + vetId);
        return consultaRepository.findAgendaFutura(vetId, LocalDateTime.now()).stream().map(ConsultaResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarTodas() {
        return consultaRepository.findAll().stream().map(ConsultaResponse::from).toList();
    }
}
