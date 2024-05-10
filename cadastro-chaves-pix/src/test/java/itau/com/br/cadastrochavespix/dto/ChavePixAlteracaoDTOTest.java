package itau.com.br.cadastrochavespix.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChavePixAlteracaoDTOTest {
    @Test
    void testGetterAndSetter() {
        ChavePixAlteracaoDTO chavePixAlteracaoDTO = new ChavePixAlteracaoDTO();

        UUID id = UUID.randomUUID();
        String tipoConta = "corrente";
        int numeroAgencia = 1234;
        int numeroConta = 567890;
        String nomeCorrentista = "João";
        String sobrenomeCorrentista = "Silva";

        chavePixAlteracaoDTO.setId(id);
        chavePixAlteracaoDTO.setTipoConta(tipoConta);
        chavePixAlteracaoDTO.setNumeroAgencia(numeroAgencia);
        chavePixAlteracaoDTO.setNumeroConta(numeroConta);
        chavePixAlteracaoDTO.setNomeCorrentista(nomeCorrentista);
        chavePixAlteracaoDTO.setSobrenomeCorrentista(sobrenomeCorrentista);


        assertEquals(id, chavePixAlteracaoDTO.getId());
        assertEquals(tipoConta, chavePixAlteracaoDTO.getTipoConta());
        assertEquals(numeroAgencia, chavePixAlteracaoDTO.getNumeroAgencia());
        assertEquals(numeroConta, chavePixAlteracaoDTO.getNumeroConta());
        assertEquals(nomeCorrentista, chavePixAlteracaoDTO.getNomeCorrentista());
        assertEquals(sobrenomeCorrentista, chavePixAlteracaoDTO.getSobrenomeCorrentista());
    }
}
