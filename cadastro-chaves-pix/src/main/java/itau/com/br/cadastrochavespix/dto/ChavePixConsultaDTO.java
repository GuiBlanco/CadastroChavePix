package itau.com.br.cadastrochavespix.dto;


import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Getter
@Setter
public class ChavePixConsultaDTO {


    private String id;

    @Size(max =9)
    private String tipoChave;

    @Size(max =77)
    private String valorChave;


    @Size(max = 10)
    @Pattern(regexp = "corrente|poupanca")
    private String tipoConta;

    private int numeroAgencia;


    private int numeroConta;



    private String nomeCorrentista;


    private String sobrenomeCorrentista;

    private String dataHoraInativacaoChave;

    private String dataHoraInclusaoChave;
}
