package itau.com.br.cadastrochavespix.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChavePixDTOTest {


    @Test
    void testGetterAndSetter() {

        ChavePixDTO chavePixDTO = new ChavePixDTO();


        String tipoChave = "cpf";
        String valorChave = "12345678901";
        String tipoConta = "corrente";
        int numeroAgencia = 1234;
        int numeroConta = 567890;
        String nomeCorrentista = "João";
        String sobrenomeCorrentista = "Silva";

        chavePixDTO.setTipoChave(tipoChave);
        chavePixDTO.setValorChave(valorChave);
        chavePixDTO.setTipoConta(tipoConta);
        chavePixDTO.setNumeroAgencia(numeroAgencia);
        chavePixDTO.setNumeroConta(numeroConta);
        chavePixDTO.setNomeCorrentista(nomeCorrentista);
        chavePixDTO.setSobrenomeCorrentista(sobrenomeCorrentista);


        assertEquals(tipoChave, chavePixDTO.getTipoChave());
        assertEquals(valorChave, chavePixDTO.getValorChave());
        assertEquals(tipoConta, chavePixDTO.getTipoConta());
        assertEquals(numeroAgencia, chavePixDTO.getNumeroAgencia());
        assertEquals(numeroConta, chavePixDTO.getNumeroConta());
        assertEquals(nomeCorrentista, chavePixDTO.getNomeCorrentista());
        assertEquals(sobrenomeCorrentista, chavePixDTO.getSobrenomeCorrentista());
    }
}
