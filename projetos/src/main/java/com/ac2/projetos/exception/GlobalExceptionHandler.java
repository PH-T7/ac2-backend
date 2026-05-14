package com.ac2.projetos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global de exceções — centraliza o tratamento de erros da API.
 * Isso evita try-catch espalhado nos controllers e garante respostas padronizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata RecursoNaoEncontradoException -> HTTP 404
     */
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    /**
     * Trata RegraDeNegocioException -> HTTP 400
     */
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<Map<String, Object>> handleRegraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * Trata erros de validação Bean Validation (@NotBlank, @NotNull, etc.) -> HTTP 422
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> errosCampos = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errosCampos.put(fe.getField(), fe.getDefaultMessage());
        }
        Map<String, Object> body = buildError(HttpStatus.UNPROCESSABLE_ENTITY, "Erro de validação nos campos");
        body.put("campos", errosCampos);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    /**
     * Captura qualquer outra exceção não tratada -> HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenerico(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno: " + ex.getMessage()));
    }

    private Map<String, Object> buildError(HttpStatus status, String mensagem) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("erro", status.getReasonPhrase());
        body.put("mensagem", mensagem);
        return body;
    }
}
