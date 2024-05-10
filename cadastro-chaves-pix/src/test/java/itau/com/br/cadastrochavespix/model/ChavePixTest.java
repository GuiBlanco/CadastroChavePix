package itau.com.br.cadastrochavespix.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChavePixTest {

    @Test
    void testGetterAndSetter() {

        ChavePix chavePix = new ChavePix();


        UUID id = UUID.randomUUID();
        String tipoChave = "CPF";
        String valorChave = "12345678900";
        String tipoConta = "corrente";
        int numeroAgencia = 1234;
        int numeroConta = 567890;
        String nomeCorrentista = "João";
        String sobrenomeCorrentista = "Silva";
        LocalDateTime dataHoraInclusaoChave = LocalDateTime.now();
        LocalDateTime dataHoraInativacaoChave = LocalDateTime.now().plusDays(1);

        chavePix.setId(id);
        chavePix.setTipoChave(tipoChave);
        chavePix.setValorChave(valorChave);
        chavePix.setTipoConta(tipoConta);
        chavePix.setNumeroAgencia(numeroAgencia);
        chavePix.setNumeroConta(numeroConta);
        chavePix.setNomeCorrentista(nomeCorrentista);
        chavePix.setSobrenomeCorrentista(sobrenomeCorrentista);
        chavePix.setDataHoraInclusaoChave(dataHoraInclusaoChave);
        chavePix.setDataHoraInativacaoChave(dataHoraInativacaoChave);


        assertEquals(id, chavePix.getId());
        assertEquals(tipoChave, chavePix.getTipoChave());
        assertEquals(valorChave, chavePix.getValorChave());
        assertEquals(tipoConta, chavePix.getTipoConta());
        assertEquals(numeroAgencia, chavePix.getNumeroAgencia());
        assertEquals(numeroConta, chavePix.getNumeroConta());
        assertEquals(nomeCorrentista, chavePix.getNomeCorrentista());
        assertEquals(sobrenomeCorrentista, chavePix.getSobrenomeCorrentista());
        assertEquals(dataHoraInclusaoChave, chavePix.getDataHoraInclusaoChave());
        assertEquals(dataHoraInativacaoChave, chavePix.getDataHoraInativacaoChave());
    }
}
