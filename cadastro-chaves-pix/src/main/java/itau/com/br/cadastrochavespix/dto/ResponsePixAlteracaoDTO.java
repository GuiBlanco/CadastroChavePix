package itau.com.br.cadastrochavespix.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Setter
@Getter
public class ResponsePixAlteracaoDTO {
    private UUID id;
    private String tipoChave;
    private String valorChave;
    private String tipoConta;
    private int numeroAgencia;
    private int numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private String dataHoraInclusaoChave;
}
