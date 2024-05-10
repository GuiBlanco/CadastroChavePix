package itau.com.br.cadastrochavespix.controller;

import itau.com.br.cadastrochavespix.dto.ChavePixAlteracaoDTO;
import itau.com.br.cadastrochavespix.dto.ChavePixConsultaDTO;
import itau.com.br.cadastrochavespix.dto.ChavePixDTO;
import itau.com.br.cadastrochavespix.dto.ResponsePixAlteracaoDTO;
import itau.com.br.cadastrochavespix.exceptions.ValidationsRulesException;
import itau.com.br.cadastrochavespix.model.ChavePix;
import itau.com.br.cadastrochavespix.service.ChavePixService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ChavePixControllerTest {

    @Mock
    private ChavePixService chavePixService;

    @InjectMocks
    private ChavePixController chavePixController;

    static UUID chavePixId = UUID.randomUUID();


    @Test
    public void testIncluirChavePixSucesso() {
        when(chavePixService.incluirChavePix(any(ChavePixDTO.class))).thenReturn(Optional.of(criarChavePix()));

        ResponseEntity<UUID> responseEntity = chavePixController.incluirChavePix(criarChavePixDTO());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(chavePixId, responseEntity.getBody());
    }
    @Test
    public void testIncluirChavePixErro() {

        var exception = new ValidationsRulesException("Preencha todos os requisitos");
        when(chavePixService.incluirChavePix(any(ChavePixDTO.class)))
                .thenThrow(exception);


        assertThrows(ValidationsRulesException.class, () -> {
            chavePixController.incluirChavePix(criarChavePixDTO());
            assertEquals("Preencha todos os requisitos", exception.getMessage());
        });
    }
    @Test
    void testConsultaCombinadaNotFound() {

        when(chavePixService.consultaCombinada(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Optional.of(new ArrayList<>()));

        ResponseEntity<List<ChavePixConsultaDTO>> responseEntity = chavePixController.consultaCombinada(
                null, null, null, null, null, null, null, null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testConsultaCombinadaSuccesso() {

        List<ChavePixConsultaDTO> listaPix = new ArrayList<>();

        listaPix.add(new ChavePixConsultaDTO());
        when(chavePixService.consultaCombinada(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Optional.of(listaPix));


        ResponseEntity<List<ChavePixConsultaDTO>> responseEntity = chavePixController.consultaCombinada(
                null, null, null, null, null, null, null, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void testAlterarChavePixSuccesso() {
        ChavePixAlteracaoDTO chavePixDTO = new ChavePixAlteracaoDTO();

        when(chavePixService.alterarChavePix(chavePixDTO)).thenReturn(new ResponsePixAlteracaoDTO(/*dados de retorno esperados*/));

        ResponseEntity<ResponsePixAlteracaoDTO> response = chavePixController.alterarChavePix(chavePixDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInativarChavePix_Success() {
        UUID id = UUID.randomUUID();
        ChavePix chavePix = new ChavePix();
        when(chavePixService.deletarChavePix(id)).thenReturn(Optional.of(chavePix));
        ResponseEntity<ChavePix> response = chavePixController.inativarChavePix(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    private ChavePix criarChavePix() {
        ChavePix chavePix = new ChavePix();
        chavePix.setId(chavePixId);
        chavePix.setTipoChave("celular");
        chavePix.setValorChave("+5511999999999");
        chavePix.setTipoConta("corrente");
        chavePix.setNumeroAgencia(1234);
        chavePix.setNumeroConta(567890);
        chavePix.setNomeCorrentista("João");
        chavePix.setSobrenomeCorrentista("Silva");
        chavePix.setDataHoraInclusaoChave(LocalDateTime.now());
        return chavePix;
    }
    private ChavePixDTO criarChavePixDTO() {
        ChavePixDTO chavePixDTO = new ChavePixDTO();
        chavePixDTO.setTipoChave("celular");
        chavePixDTO.setValorChave("+55011999999999");
        chavePixDTO.setTipoConta("corrente");
        chavePixDTO.setNumeroAgencia(1234);
        chavePixDTO.setNumeroConta(567890);
        chavePixDTO.setNomeCorrentista("João");
        chavePixDTO.setSobrenomeCorrentista("Silva");
        return chavePixDTO;
    }
}
