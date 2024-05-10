package itau.com.br.cadastrochavespix.dto;


import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ResponsePixAlteracaoDTOTest {

    @Test
    void testGetterAndSetter() {
        ResponsePixAlteracaoDTO responseDTO = new ResponsePixAlteracaoDTO();

        UUID id = UUID.randomUUID();
        String tipoChave = "CPF";
        String valorChave = "12345678901";
        String tipoConta = "corrente";
        int numeroAgencia = 1234;
        int numeroConta = 567890;
        String nomeCorrentista = "João";
        String sobrenomeCorrentista = "Silva";
        String dataHoraInclusaoChave = "2024-05-06T12:00:00";

        responseDTO.setId(id);
        responseDTO.setTipoChave(tipoChave);
        responseDTO.setValorChave(valorChave);
        responseDTO.setTipoConta(tipoConta);
        responseDTO.setNumeroAgencia(numeroAgencia);
        responseDTO.setNumeroConta(numeroConta);
        responseDTO.setNomeCorrentista(nomeCorrentista);
        responseDTO.setSobrenomeCorrentista(sobrenomeCorrentista);
        responseDTO.setDataHoraInclusaoChave(dataHoraInclusaoChave);

        assertEquals(id, responseDTO.getId());
        assertEquals(tipoChave, responseDTO.getTipoChave());
        assertEquals(valorChave, responseDTO.getValorChave());
        assertEquals(tipoConta, responseDTO.getTipoConta());
        assertEquals(numeroAgencia, responseDTO.getNumeroAgencia());
        assertEquals(numeroConta, responseDTO.getNumeroConta());
        assertEquals(nomeCorrentista, responseDTO.getNomeCorrentista());
        assertEquals(sobrenomeCorrentista, responseDTO.getSobrenomeCorrentista());
        assertEquals(dataHoraInclusaoChave, responseDTO.getDataHoraInclusaoChave());
    }
}
