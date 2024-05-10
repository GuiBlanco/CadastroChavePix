package itau.com.br.cadastrochavespix.service;

import itau.com.br.cadastrochavespix.dto.ChavePixAlteracaoDTO;
import itau.com.br.cadastrochavespix.dto.ChavePixConsultaDTO;
import itau.com.br.cadastrochavespix.dto.ChavePixDTO;
import itau.com.br.cadastrochavespix.exceptions.ResourceNotFoundException;
import itau.com.br.cadastrochavespix.exceptions.ValidationsRulesException;
import itau.com.br.cadastrochavespix.model.ChavePix;
import itau.com.br.cadastrochavespix.repository.ChavePixRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChavePixServiceTest {

    @Mock
    private ChavePixRepository chavePixRepository;

    @InjectMocks
    private ChavePixService chavePixService;

    @Test
    void incluirChavePixSucessoCelular() {
        var chavePixDTO = criarChavePixDTO();
        var chavePix = criarChavePix();

        List<ChavePix> chavesExistentes = Collections.nCopies(2, new ChavePix());

        when(chavePixRepository.findByNumeroAgenciaAndNumeroContaAndNomeCorrentistaAndSobrenomeCorrentista(
                chavePixDTO.getNumeroAgencia(), chavePixDTO.getNumeroConta(), chavePixDTO.getNomeCorrentista(),
                chavePixDTO.getSobrenomeCorrentista())).thenReturn(Optional.of(chavesExistentes));
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        Optional<ChavePix> resultado = chavePixService.incluirChavePix(chavePixDTO);

        assertTrue(resultado.isPresent());
        assertEquals(chavePix, resultado.get());
    }

    @Test
    void incluirChavePixFalhaCelular() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setValorChave("55011977593632");

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void incluirChavePixSucessoEmail() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("email");
        chavePixDTO.setValorChave("teste@itau-unibanco.com.br");

        var chavePix = criarChavePix();
        chavePix.setTipoChave("email");
        chavePix.setValorChave("teste@itau-unibanco.com.br");

        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        Optional<ChavePix> resultado = chavePixService.incluirChavePix(chavePixDTO);

        assertTrue(resultado.isPresent());
        assertEquals(chavePix, resultado.get());
    }

    @Test
    void incluirChavePixFalhaEmail() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("email");
        chavePixDTO.setValorChave("testeitau-unibanco.com.br");

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void incluirChavePixSucessoCPF() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("cpf");
        chavePixDTO.setValorChave("43036076816");

        var chavePix = criarChavePix();
        chavePix.setTipoChave("cpf");
        chavePix.setValorChave("43036076816");

        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        Optional<ChavePix> resultado = chavePixService.incluirChavePix(chavePixDTO);

        assertTrue(resultado.isPresent());
        assertEquals(chavePix, resultado.get());
    }

    @Test
    void incluirChavePixFalhaCPF() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("cpf");
        chavePixDTO.setValorChave("43036076815");

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void incluirChavePixSucessoCNPJ() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("cnpj");
        chavePixDTO.setValorChave("09934939000106");

        var chavePix = criarChavePix();
        chavePix.setTipoChave("cnpj");
        chavePix.setValorChave("09934939000106");

        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        Optional<ChavePix> resultado = chavePixService.incluirChavePix(chavePixDTO);

        assertTrue(resultado.isPresent());
        assertEquals(chavePix, resultado.get());
    }

    @Test
    void incluirChavePixFalhaCNPJ() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("cnpj");
        chavePixDTO.setValorChave("09934939000107");

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void incluirChavePixSucessoAleatorio() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("aleatorio");
        chavePixDTO.setValorChave("ABCD1234S2");

        var chavePix = criarChavePix();
        chavePix.setTipoChave("aleatorio");
        chavePix.setValorChave("ABCD1234S2");

        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        Optional<ChavePix> resultado = chavePixService.incluirChavePix(chavePixDTO);

        assertTrue(resultado.isPresent());
        assertEquals(chavePix, resultado.get());
    }

    @Test
    void incluirChavePixFalhaAleatorio() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("aleatorio");
        chavePixDTO.setValorChave("ABCD1234S2@");

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void incluirChavePixFalhaCamposObrigatorios() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setNomeCorrentista(null);

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void incluirChavePixDuplicada() {
        ChavePixDTO chavePixDTO = criarChavePixDTO();

        when(chavePixRepository.save(any(ChavePix.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void incluirChavePixFalhaTipoChaveInvalido() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoChave("telefone");

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void incluirChavePixFalhaTipoContaInvalido() {
        var chavePixDTO = criarChavePixDTO();
        chavePixDTO.setTipoConta("credito");

        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }


    @Test
    void testValidarLimiteChavesExcedeLimitePessoaFisica() {

        ChavePixDTO chavePixDTO = criarChavePixDTO();

        ChavePixRepository chavePixRepository = mock(ChavePixRepository.class);
        List<ChavePix> chavesExistentes = Collections.nCopies(20, new ChavePix());
        when(chavePixRepository.findByNumeroAgenciaAndNumeroContaAndNomeCorrentistaAndSobrenomeCorrentista(
                chavePixDTO.getNumeroAgencia(), chavePixDTO.getNumeroConta(), chavePixDTO.getNomeCorrentista(),
                chavePixDTO.getSobrenomeCorrentista())).thenReturn(Optional.of(chavesExistentes));

        ChavePixService chavePixService = new ChavePixService(chavePixRepository);
        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }

    @Test
    void testValidarLimiteChavesExcedeLimitePessoaJuridica() {

        ChavePixDTO chavePixDTO = criarChavePixPjDTO();

        ChavePixRepository chavePixRepository = mock(ChavePixRepository.class);
        List<ChavePix> chavesExistentes = Collections.nCopies(22, new ChavePix());
        when(chavePixRepository.findByNumeroAgenciaAndNumeroContaAndNomeCorrentistaAndSobrenomeCorrentista(
                chavePixDTO.getNumeroAgencia(), chavePixDTO.getNumeroConta(), chavePixDTO.getNomeCorrentista(),
                chavePixDTO.getSobrenomeCorrentista())).thenReturn(Optional.of(chavesExistentes));


        ChavePixService chavePixService = new ChavePixService(chavePixRepository);
        assertThrows(ValidationsRulesException.class, () -> chavePixService.incluirChavePix(chavePixDTO));
    }



    @Test
    void testConsultaCombinadaSuccesso() {
        List<ChavePix> listaChavesPix = new ArrayList<>();
        when(chavePixRepository.findAll(any(Specification.class))).thenReturn(listaChavesPix);

        Optional<List<ChavePixConsultaDTO>> resultadoConsulta = chavePixService.consultaCombinada(null,
                "celular", "897", "45785", "nomeCorrentista",
                "sobrenomeCorrentista", "09/05/2024", null);
        assertTrue(resultadoConsulta.isPresent());
        assertEquals(listaChavesPix.size(), resultadoConsulta.get().size());
    }

    @Test
    public void testAlterarChavePixFalhaRecurceNotFound() {
        ChavePixAlteracaoDTO chavePixDTO = new ChavePixAlteracaoDTO();
        assertThrows(ResourceNotFoundException.class, () -> {
            chavePixService.alterarChavePix(chavePixDTO);
        });
    }

    @Test
    public void testAlterarChavePixFalhaResourceNotFOund() {
        UUID id = UUID.randomUUID();
        ChavePixAlteracaoDTO chavePixDTO = new ChavePixAlteracaoDTO();
        chavePixDTO.setId(id);

        when(chavePixRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            chavePixService.alterarChavePix(chavePixDTO);
        });
    }

    @Test
    public void testAlterarChavePixFalhaChaveInativa() {
        UUID id = UUID.randomUUID();
        ChavePixAlteracaoDTO chavePixDTO = new ChavePixAlteracaoDTO();
        chavePixDTO.setId(id);

        ChavePix chavePix = new ChavePix();
        chavePix.setDataHoraInativacaoChave(LocalDateTime.now());

        when(chavePixRepository.findById(id)).thenReturn(Optional.of(chavePix));

        assertThrows(ValidationsRulesException.class, () -> {
            chavePixService.alterarChavePix(chavePixDTO);
        });
    }

    @Test
    public void testAlterarChavePixFalhaChaveInvalidParams() {
        UUID id = UUID.randomUUID();
        ChavePixAlteracaoDTO chavePixDTO = new ChavePixAlteracaoDTO();
        chavePixDTO.setId(id);

        ChavePix chavePix = new ChavePix();
        chavePix.setNomeCorrentista(null);

        when(chavePixRepository.findById(id)).thenReturn(Optional.of(chavePix));

        assertThrows(ValidationsRulesException.class, () -> {
            chavePixService.alterarChavePix(chavePixDTO);
        });
    }

    @Test
    public void testAlterarChavePixSucesso() {
        UUID id = UUID.randomUUID();
        ChavePixAlteracaoDTO chavePixDTO = criarChavePixAlt();
        chavePixDTO.setId(id);
        ChavePix chavePix = criarChavePix();
        chavePix.setId(id);
        when(chavePixRepository.findById(id)).thenReturn(Optional.of(chavePix));
        when(chavePixRepository.save(chavePix)).thenReturn(chavePix);
        chavePixService.alterarChavePix(chavePixDTO);
        verify(chavePixRepository, times(1)).save(chavePix);
    }

    @Test
    public void testDeletarChavePixOK() {
        UUID id = UUID.randomUUID();
        ChavePix chavePix = new ChavePix();
        chavePix.setId(id);
        when(chavePixRepository.findById(id)).thenReturn(Optional.of(chavePix));
        when(chavePixRepository.save(chavePix)).thenReturn(chavePix);

        Optional<ChavePix> resultado = chavePixService.deletarChavePix(id);

        assertTrue(resultado.isPresent());
        assertNotNull(resultado.get().getDataHoraInativacaoChave());
    }

    @Test
    public void testDeletarChavePixChaveNotFound() {
        UUID id = UUID.randomUUID();
        when(chavePixRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            chavePixService.deletarChavePix(id);
        });
    }

    @Test
    public void testDeletarChavePixChavenInativa() {
        UUID id = UUID.randomUUID();
        ChavePix chavePix = new ChavePix();
        chavePix.setId(id);
        chavePix.setDataHoraInativacaoChave(LocalDateTime.now());
        when(chavePixRepository.findById(id)).thenReturn(Optional.of(chavePix));
        assertThrows(ValidationsRulesException.class, () -> {
            chavePixService.deletarChavePix(id);
        });
    }
    @Test
    public void testConverterChavePixEmChavePixConsultaDtSucess() {

        ChavePix chavePix = new ChavePix();
        chavePix.setId(UUID.randomUUID());
        chavePix.setTipoChave("CPF");
        chavePix.setValorChave("12345678900");
        chavePix.setTipoConta("Corrente");
        chavePix.setNumeroAgencia(1234);
        chavePix.setNumeroConta(567890);
        chavePix.setNomeCorrentista("João");
        chavePix.setSobrenomeCorrentista("Silva");
        LocalDateTime dataInclusao = LocalDateTime.of(2022, 5, 1, 10, 30);
        chavePix.setDataHoraInclusaoChave(dataInclusao);
        LocalDateTime dataInativacao = LocalDateTime.of(2022, 5, 2, 10, 30);
        chavePix.setDataHoraInativacaoChave(dataInativacao);

        ChavePixConsultaDTO dto = ChavePixService.converterChavePixEmChavePixConsultaDto(chavePix);

        assertEquals(chavePix.getId().toString(), dto.getId());
        assertEquals(chavePix.getTipoChave(), dto.getTipoChave());
        assertEquals(chavePix.getValorChave(), dto.getValorChave());
        assertEquals(chavePix.getTipoConta(), dto.getTipoConta());
        assertEquals(chavePix.getNumeroAgencia(), dto.getNumeroAgencia());
        assertEquals(chavePix.getNumeroConta(), dto.getNumeroConta());
        assertEquals(chavePix.getNomeCorrentista(), dto.getNomeCorrentista());
        assertEquals(chavePix.getSobrenomeCorrentista(), dto.getSobrenomeCorrentista());
        assertEquals("01/05/2022", dto.getDataHoraInclusaoChave());
        assertEquals("02/05/2022", dto.getDataHoraInativacaoChave());
    }

    @Test
    public void testConverterChavePixEmChavePixConsultaDtoNullOptionsFildsOk() {

        ChavePix chavePix = new ChavePix();
        chavePix.setId(UUID.randomUUID());
        chavePix.setTipoChave("CPF");
        chavePix.setValorChave("12345678900");
        chavePix.setTipoConta("Corrente");
        chavePix.setNumeroAgencia(1234);
        chavePix.setNumeroConta(567890);
        chavePix.setNomeCorrentista("João");
        chavePix.setDataHoraInclusaoChave(LocalDateTime.of(2022, 5, 1, 10, 30));

        ChavePixConsultaDTO dto = ChavePixService.converterChavePixEmChavePixConsultaDto(chavePix);

        assertEquals("", dto.getSobrenomeCorrentista());
        assertEquals("01/05/2022", dto.getDataHoraInclusaoChave());
        assertEquals("", dto.getDataHoraInativacaoChave());
    }

    @Test
    public void testParseDataOK() {
        LocalDate data = ChavePixService.parseData("2022/05/01");
        assertEquals(LocalDate.of(2022, 5, 1), data);
    }

    @Test
    public void testParseDataOK2() {
        LocalDate data = ChavePixService.parseData("2022-05-01");
        assertEquals(LocalDate.of(2022, 5, 1), data);
    }

    @Test
    public void testParseDataOk3() {
        LocalDate data = ChavePixService.parseData("01/05/2022");
        assertEquals(LocalDate.of(2022, 5, 1), data);
    }

    @Test
    public void testParseDataErroValidationsRulesException() {
        assertThrows(ValidationsRulesException.class, () -> {
            ChavePixService.parseData("01-05-2022");
        });
    }

    @Test
    public void testParseData_NullString_ReturnsNull() {
        // Execução do método sob teste
        LocalDate data = ChavePixService.parseData(null);
        // Verificação
        assertNull(data);
    }


    @Test
    public void testConverterChavePixEmChavePixConsultaDtoThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            ChavePixConsultaDTO dto = ChavePixService.converterChavePixEmChavePixConsultaDto(null);
        });
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

    private ChavePixDTO criarChavePixPjDTO() {
        ChavePixDTO chavePixDTO = new ChavePixDTO();
        chavePixDTO.setTipoChave("celular");
        chavePixDTO.setValorChave("+55011999999999");
        chavePixDTO.setTipoConta("corrente");
        chavePixDTO.setNumeroAgencia(1234);
        chavePixDTO.setNumeroConta(567890);
        chavePixDTO.setNomeCorrentista("João");
        return chavePixDTO;
    }


    private ChavePix criarChavePix() {
        ChavePix chavePix = new ChavePix();
        chavePix.setId(UUID.randomUUID());
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

    private ChavePixAlteracaoDTO criarChavePixAlt() {
        ChavePixAlteracaoDTO chavePix = new ChavePixAlteracaoDTO();
        chavePix.setTipoConta("corrente");
        chavePix.setNumeroAgencia(1234);
        chavePix.setNumeroConta(567890);
        chavePix.setNomeCorrentista("João");
        chavePix.setSobrenomeCorrentista("Silva");

        return chavePix;
    }

    @Test
    public void testValidarTamanhoMaximoErroThrowsValidationsRulesException() {
        assertThrows(ValidationsRulesException.class, () -> {
            chavePixService.validarTamanhoMaximo("Texto com mais de 10 caracteres", 10);
        });
    }
}