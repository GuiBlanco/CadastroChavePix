package itau.com.br.cadastrochavespix.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChavePixConsultaDTOTest {
    @Test
    void testGetterAndSetter() {

        ChavePixConsultaDTO chavePixConsultaDTO = new ChavePixConsultaDTO();

        String id = "123e4567-e89b-12d3-a456-426614174000";
        String tipoChave = "cpf";
        String valorChave = "12345678901";
        String tipoConta = "corrente";
        int numeroAgencia = 1234;
        int numeroConta = 567890;
        String nomeCorrentista = "João";
        String sobrenomeCorrentista = "Silva";
        String dataHoraInativacaoChave = "2024-05-05";
        String dataHoraInclusaoChave = "2024-05-01";

        chavePixConsultaDTO.setId(id);
        chavePixConsultaDTO.setTipoChave(tipoChave);
        chavePixConsultaDTO.setValorChave(valorChave);
        chavePixConsultaDTO.setTipoConta(tipoConta);
        chavePixConsultaDTO.setNumeroAgencia(numeroAgencia);
        chavePixConsultaDTO.setNumeroConta(numeroConta);
        chavePixConsultaDTO.setNomeCorrentista(nomeCorrentista);
        chavePixConsultaDTO.setSobrenomeCorrentista(sobrenomeCorrentista);
        chavePixConsultaDTO.setDataHoraInativacaoChave(dataHoraInativacaoChave);
        chavePixConsultaDTO.setDataHoraInclusaoChave(dataHoraInclusaoChave);


        assertEquals(id, chavePixConsultaDTO.getId());
        assertEquals(tipoChave, chavePixConsultaDTO.getTipoChave());
        assertEquals(valorChave, chavePixConsultaDTO.getValorChave());
        assertEquals(tipoConta, chavePixConsultaDTO.getTipoConta());
        assertEquals(numeroAgencia, chavePixConsultaDTO.getNumeroAgencia());
        assertEquals(numeroConta, chavePixConsultaDTO.getNumeroConta());
        assertEquals(nomeCorrentista, chavePixConsultaDTO.getNomeCorrentista());
        assertEquals(sobrenomeCorrentista, chavePixConsultaDTO.getSobrenomeCorrentista());
        assertEquals(dataHoraInativacaoChave, chavePixConsultaDTO.getDataHoraInativacaoChave());
        assertEquals(dataHoraInclusaoChave, chavePixConsultaDTO.getDataHoraInclusaoChave());
    }
}
