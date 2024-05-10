package itau.com.br.cadastrochavespix.dto;


import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.LowerCase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ChavePixDTO {

    @Size(max =9)
    @LowerCase
    private String tipoChave;

    @Size(max =77)
    @LowerCase
    private String valorChave;

    @NotBlank
    @Size(max = 10)
    @LowerCase
    @Pattern(regexp = "corrente|poupanca")
    private String tipoConta;

    @NotNull
    @Size(max =4)
    private int numeroAgencia;

    @NotNull
    @Size(max =8)
    private int numeroConta;

    @NotBlank
    @LowerCase
    @Size(max =30)
    private String nomeCorrentista;

    @LowerCase
    @Size(max =45)
    private String sobrenomeCorrentista;



}