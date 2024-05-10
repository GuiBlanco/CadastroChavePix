package itau.com.br.cadastrochavespix.service;

import itau.com.br.cadastrochavespix.dto.ChavePixAlteracaoDTO;
import itau.com.br.cadastrochavespix.dto.ChavePixConsultaDTO;
import itau.com.br.cadastrochavespix.dto.ChavePixDTO;
import itau.com.br.cadastrochavespix.dto.ResponsePixAlteracaoDTO;
import itau.com.br.cadastrochavespix.exceptions.ResourceNotFoundException;
import itau.com.br.cadastrochavespix.exceptions.ValidationsRulesException;
import itau.com.br.cadastrochavespix.model.ChavePix;
import itau.com.br.cadastrochavespix.repository.ChavePixRepository;
import itau.com.br.cadastrochavespix.repository.ChavePixSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class ChavePixService {

    private final Logger logger = Logger.getLogger(ChavePixService.class.getName());

    private final ChavePixRepository chavePixRepository;

    @Autowired
    public ChavePixService(ChavePixRepository chavePixRepository) {
        this.chavePixRepository = chavePixRepository;
    }


    public Optional<ChavePix> incluirChavePix(ChavePixDTO chavePixDTO) {
        logger.info("Iniciando  inclusao de chave pix");

        validarCamposObrigatoriosInclusao(chavePixDTO);
        validarTipoConta(chavePixDTO.getTipoConta());
        validarTipoEValorChave(chavePixDTO.getTipoChave(), chavePixDTO.getValorChave());
        validarLimiteChaves(chavePixDTO);

        var chavePix = new ChavePix();
        chavePix.setTipoChave(chavePixDTO.getTipoChave());
        chavePix.setValorChave(chavePixDTO.getValorChave());
        chavePix.setTipoConta(chavePixDTO.getTipoConta());
        chavePix.setNumeroAgencia(chavePixDTO.getNumeroAgencia());
        chavePix.setNumeroConta(chavePixDTO.getNumeroConta());
        chavePix.setNomeCorrentista(chavePixDTO.getNomeCorrentista());

        chavePix.setSobrenomeCorrentista(chavePixDTO.getSobrenomeCorrentista() != null
                && !chavePixDTO.getSobrenomeCorrentista().isEmpty() ?
                chavePixDTO.getSobrenomeCorrentista() : null);

        chavePix.setDataHoraInclusaoChave(LocalDateTime.now());

        logger.info("Iniciando Save da chave");
        try {
            return Optional.of(chavePixRepository.save(chavePix));
        } catch (DataIntegrityViolationException e) {
            logger.info("Erro ao salvar a chave PIX: ");
            throw new ValidationsRulesException("Erro ao salvar a chave PIX: Chave PIX já existe.");
        }

    }


    public ResponsePixAlteracaoDTO alterarChavePix(ChavePixAlteracaoDTO chavePixDTO) {

        if (chavePixDTO.getId() == null) {
            throw new ResourceNotFoundException("Informe a Chave Pix.");
        }

        var optionalChavePix = chavePixRepository.findById(chavePixDTO.getId());

        if (optionalChavePix.isEmpty()) {
            throw new ResourceNotFoundException("Chave PIX não encontrada.");
        }
        var chavePix = optionalChavePix.get();

        if (chavePix.getDataHoraInativacaoChave() != null) {
            throw new ValidationsRulesException("Essa chave esta inativa e não pode ser alterada.");
        }

        validarAlteracao(chavePixDTO);

        chavePix.setNomeCorrentista(chavePixDTO.getNomeCorrentista());
        chavePix.setNumeroAgencia(chavePixDTO.getNumeroAgencia());
        chavePix.setNumeroConta(chavePixDTO.getNumeroConta());
        chavePix.setTipoConta(chavePixDTO.getTipoConta());
        chavePix.setTipoChave(chavePixDTO.getSobrenomeCorrentista());
        chavePix.setSobrenomeCorrentista(chavePixDTO.getSobrenomeCorrentista());
        var x = chavePixRepository.save(chavePix);
        return converterChavePixEmResponsePixAlteracaoDto(x);
    }

    public Optional<ChavePix> deletarChavePix(UUID id) {
        Optional<ChavePix> optionalChavePix = chavePixRepository.findById(id);
        if (optionalChavePix.isPresent()) {
            ChavePix chavePix = optionalChavePix.get();
            if (chavePix.getDataHoraInativacaoChave() != null) {
                throw new ValidationsRulesException("Chave PIX já foi inativada anteriormente.");
            }
            chavePix.setDataHoraInativacaoChave(LocalDateTime.now());

            var chavePixInativada = chavePixRepository.save(chavePix);
            return  Optional.of(chavePixInativada);

        } else {
            throw new ResourceNotFoundException("Chave PIX não encontrada.");
        }
    }


    public Optional<List<ChavePixConsultaDTO>> consultaCombinada(String id, String tipoChave, String agencia,
                                                                 String conta, String nomeCorrentista,
                                                                 String sobrenomeCorrentista, String dataHoraInclusaoChave,
                                                                 String dataHoraInativacaoChave) {

        var optionalChavePixList = Optional.of(chavePixRepository.findAll
                (ChavePixSpecifications.consultaCombinada(id, tipoChave, agencia, conta, nomeCorrentista,
                        sobrenomeCorrentista,parseData(dataHoraInclusaoChave),parseData(dataHoraInativacaoChave))));

       return converterChavePixEmChavePixConsultaDTO(optionalChavePixList);
    }

    private void validarAlteracao(ChavePixAlteracaoDTO chavePix){
        validarCamposObrigatoriosAlteracao(chavePix.getTipoConta(),chavePix.getNumeroAgencia(),
                chavePix.getNumeroConta(),chavePix.getNomeCorrentista());
        validarTipoConta(chavePix.getTipoConta());
        validarTamanhoMaximo(chavePix.getNomeCorrentista(),30);
        validarTamanhoMaximo(String.valueOf(chavePix.getNumeroAgencia()),4);
        validarTamanhoMaximo(String.valueOf(chavePix.getNumeroConta()),8);
        if(chavePix.getSobrenomeCorrentista() != null) validarTamanhoMaximo(chavePix.getSobrenomeCorrentista(),45);
    }

    private void validarLimiteChaves(ChavePixDTO chavePixDTO) {
        int numeroMaximoChaves = (chavePixDTO.getSobrenomeCorrentista() == null || chavePixDTO.getSobrenomeCorrentista().isEmpty()) ? 20 : 5;

        var optionalChavesExistentes = chavePixRepository.findByNumeroAgenciaAndNumeroContaAndNomeCorrentistaAndSobrenomeCorrentista(
                chavePixDTO.getNumeroAgencia(), chavePixDTO.getNumeroConta(), chavePixDTO.getNomeCorrentista(), chavePixDTO.getSobrenomeCorrentista());

        var chavesExistentes = optionalChavesExistentes.orElse(Collections.emptyList());

        if (chavesExistentes.size() >= numeroMaximoChaves) {
            throw new ValidationsRulesException("Limite máximo de chaves atingido para esta conta.");
        }
    }

    private void validarCamposObrigatoriosInclusao(ChavePixDTO chavePixDTO) {
        if (chavePixDTO.getTipoChave() == null || chavePixDTO.getValorChave() == null ||
                chavePixDTO.getTipoConta() == null || chavePixDTO.getNumeroAgencia() == 0 ||
                chavePixDTO.getNumeroConta() == 0 || chavePixDTO.getNomeCorrentista() == null) {
            throw new ValidationsRulesException("Todos os campos obrigatórios devem ser preenchidos.");
        }
        validarTamanhoMaximo(chavePixDTO.getNomeCorrentista(),30);
        validarTamanhoMaximo(String.valueOf(chavePixDTO.getNumeroAgencia()),4);
        validarTamanhoMaximo(String.valueOf(chavePixDTO.getNumeroConta()),8);
        validarTamanhoMaximo(chavePixDTO.getTipoConta(),10);
        if(chavePixDTO.getSobrenomeCorrentista() != null)
            validarTamanhoMaximo(chavePixDTO.getSobrenomeCorrentista(),45);

    }
    private void validarCamposObrigatoriosAlteracao(
            String tipoConta,Integer numeroAgencia, Integer numeroConta, String nomeCorrentista) {
        if (tipoConta == null || numeroAgencia == 0 ||
                numeroConta == 0 || nomeCorrentista == null) {
            throw new ValidationsRulesException("Todos os campos obrigatórios devem ser preenchidos.");
        }
    }

    private void validarTipoConta(String tipoConta) {
        if (!"corrente".equalsIgnoreCase(tipoConta) && !"poupanca".equalsIgnoreCase(tipoConta)) {
            throw new ValidationsRulesException("O tipo de conta deve ser 'corrente' ou 'poupanca'.");
        }
    }

    private void validarTipoEValorChave(String tipoChave, String valorChave) {
        switch (tipoChave.toLowerCase()) {
            case "celular":
                validarCelular(valorChave);
                logger.info("Chave tipo Celular");
                break;
            case "email":
                validarEmail(valorChave);
                logger.info("Chave tipo Email");
                break;
            case "cpf":
                validarCpf(valorChave);
                logger.info("Chave tipo CPF");
                break;
            case "cnpj":
                validarCnpj(valorChave);
                logger.info("Chave tipo CNPJ");
                break;
            case "aleatorio":
                validarChaveAleatoria(valorChave);
                logger.info("Chave tipo Aleatoria");
                break;
            default:
                throw new ValidationsRulesException("Tipo de chave inválido.");
        }
    }


    // todo melhorar validacao
    private void validarCelular(String valorChave) {
        if (!valorChave.startsWith("+") ||
                valorChave.length() != 15 ||
                !isNumeric(valorChave.substring(1, 4)) ||
                !isNumeric(valorChave.substring(4))) {
            throw new ValidationsRulesException("Número de celular inválido usar formato (DDD(+55) +COD AREA(TRES DIGITOS)+NUM ||Exemplo +55011987878787");
        }
    }

    private void validarEmail(String valorChave) {
        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (!valorChave.contains("@") || valorChave.length() > 77 || !valorChave.matches(regexEmail)) {
            throw new ValidationsRulesException("E-mail inválido.");
        }
    }

    private void validarCpf(String valorChave) {
        valorChave = valorChave.replaceAll("[^0-9]", "");
        if (!valorChave.matches("[0-9]+") || valorChave.length() != 11 || !validarCPF(valorChave)) {
            throw new ValidationsRulesException("CPF inválido.");
        }
    }

    private void validarCnpj(String valorChave) {

        valorChave = valorChave.replaceAll("[^0-9]", "");

        if (valorChave.length() != 14 || !verificarDigitosVerificadores(valorChave)) {
            throw new ValidationsRulesException("CNPJ inválido.");
        }
    }

    private void validarChaveAleatoria(String valorChave) {
        if (!valorChave.matches("[a-zA-Z0-9]{1,36}")) {
            throw new ValidationsRulesException("Chave aleatória inválida.");
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private boolean validarCPF(String cpf) {
        int d1 = calcularDigitoVerificadorCPF(cpf.substring(0, 9), 10);
        int d2 = calcularDigitoVerificadorCPF(cpf.substring(0, 9) + d1, 11);
        return cpf.equals(cpf.substring(0, 9) + String.valueOf(d1) + String.valueOf(d2));
    }

    private int calcularDigitoVerificadorCPF(String cpfParcial, int peso) {
        int soma = 0;
        for (int i = 0; i < cpfParcial.length(); i++) {
            soma += (cpfParcial.charAt(i) - '0') * peso--;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    private static boolean verificarDigitosVerificadores(String cnpj) {
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int digito1 = calcularDigitoVerificadorCNPJ(cnpj, pesos1);

        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int digito2 = calcularDigitoVerificadorCNPJ(cnpj, pesos2);

        return digito1 == Character.getNumericValue(cnpj.charAt(12)) &&
                digito2 == Character.getNumericValue(cnpj.charAt(13));
    }

    private static int calcularDigitoVerificadorCNPJ(String cnpj, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    public static ChavePixConsultaDTO converterChavePixEmChavePixConsultaDto(ChavePix chavePix) {
        ChavePixConsultaDTO dto = new ChavePixConsultaDTO();

        dto.setId(chavePix.getId().toString());
        dto.setTipoChave(chavePix.getTipoChave());
        dto.setValorChave(chavePix.getValorChave());
        dto.setTipoConta(chavePix.getTipoConta());
        dto.setNumeroAgencia(chavePix.getNumeroAgencia());
        dto.setNumeroConta(chavePix.getNumeroConta());
        dto.setNomeCorrentista(chavePix.getNomeCorrentista());

        dto.setSobrenomeCorrentista(chavePix.getSobrenomeCorrentista()
                != null ? chavePix.getSobrenomeCorrentista() : "");

        dto.setDataHoraInclusaoChave(chavePix.getDataHoraInclusaoChave()
                != null ? formatarData(chavePix.getDataHoraInclusaoChave()) : "");

        dto.setDataHoraInativacaoChave(chavePix.getDataHoraInativacaoChave()
                != null ? formatarData(chavePix.getDataHoraInativacaoChave()) : "");


        return dto;
    }

    public ResponsePixAlteracaoDTO converterChavePixEmResponsePixAlteracaoDto(ChavePix chavePix) {
        ResponsePixAlteracaoDTO dto = new ResponsePixAlteracaoDTO();

        dto.setId(chavePix.getId());
        dto.setTipoConta(chavePix.getTipoConta());
        dto.setNumeroAgencia(chavePix.getNumeroAgencia());
        dto.setNumeroConta(chavePix.getNumeroConta());
        dto.setNomeCorrentista(chavePix.getNomeCorrentista());
        dto.setSobrenomeCorrentista(chavePix.getSobrenomeCorrentista());
        dto.setDataHoraInclusaoChave(formatarData(chavePix.getDataHoraInclusaoChave()));
        dto.setTipoChave(chavePix.getTipoChave());
        dto.setValorChave(chavePix.getValorChave());
        return dto;
    }

    private static String formatarData(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDateTime.format(formatter);
    }

    public static Optional<List<ChavePixConsultaDTO>> converterChavePixEmChavePixConsultaDTO(Optional<List<ChavePix>> optionalChavePixList) {
        return optionalChavePixList.map(chavePixList ->
                chavePixList.stream()
                        .map(ChavePixService::converterChavePixEmChavePixConsultaDto)
                        .collect(Collectors.toList())
        );
    }

     static LocalDate parseData(String dataString) {

        String padrao1 = "\\d{4}/\\d{2}/\\d{2}";
        String padrao2 = "\\d{4}-\\d{2}-\\d{2}";
        String padrao3 = "\\d{2}/\\d{2}/\\d{4}";

        if (dataString == null) {
            return null;
        }

        if (dataString.matches(padrao1)) {
            return LocalDate.parse(dataString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } else if (dataString.matches(padrao2)) {
            return LocalDate.parse(dataString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (dataString.matches(padrao3)) {
            return LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        throw new ValidationsRulesException("formato da data invalido");
    }

    public void validarTamanhoMaximo(String texto, int limite) {
        if (texto.length() > limite) {
            throw new ValidationsRulesException("Há parametros que não respeitam o limite de caracteres.");
        }
    }


}