package com.ac2.petcare.service;

import com.ac2.petcare.dto.VacinaRequest;
import com.ac2.petcare.entity.Consulta;
import com.ac2.petcare.entity.StatusConsulta;
import com.ac2.petcare.entity.Vacina;
import com.ac2.petcare.exception.RecursoNaoEncontradoException;
import com.ac2.petcare.exception.RegraDeNegocioException;
import com.ac2.petcare.repository.ConsultaRepository;
import com.ac2.petcare.repository.VacinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VacinaService {

    private final VacinaRepository vacinaRepository;
    private final ConsultaRepository consultaRepository;

    public VacinaService(VacinaRepository vacinaRepository, ConsultaRepository consultaRepository) {
        this.vacinaRepository = vacinaRepository;
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public Vacina registrar(VacinaRequest request) {
        Consulta consulta = consultaRepository.findById(request.consultaId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada com id: " + request.consultaId()));
        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new RegraDeNegocioException("Não é possível registrar vacina em consulta cancelada");
        }
        Vacina vacina = new Vacina(request.nome(), request.dataAplicacao(), request.dataReforco(), request.lote(), consulta);
        return vacinaRepository.save(vacina);
    }

    @Transactional(readOnly = true)
    public List<Vacina> listarPorAnimal(Long animalId) {
        return vacinaRepository.findByAnimalId(animalId);
    }

    @Transactional(readOnly = true)
    public List<Vacina> listarPorConsulta(Long consultaId) {
        return vacinaRepository.findByConsultaId(consultaId);
    }
}
