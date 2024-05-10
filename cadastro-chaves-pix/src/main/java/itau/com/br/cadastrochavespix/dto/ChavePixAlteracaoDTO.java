package itau.com.br.cadastrochavespix.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

import java.util.UUID;

@Getter
@Setter
public class ChavePixAlteracaoDTO {

    @NotBlank
    private UUID id;

    @NotBlank
    @Size(max =10)
    @Pattern(regexp = "corrente|poupanca")
    private String tipoConta;

    @NotNull
    @Max(9999)
    private int numeroAgencia;

    @NotNull
    @Max(99999999)
    private int numeroConta;

    @NotBlank
    @Size(max =30)
    private String nomeCorrentista;

    @Size(max =45)
    private String sobrenomeCorrentista;


}