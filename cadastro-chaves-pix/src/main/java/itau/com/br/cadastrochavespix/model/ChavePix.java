package itau.com.br.cadastrochavespix.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
public class ChavePix {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tipo_chave", nullable = false)
    private String tipoChave;

    @Column(name = "valor_chave", nullable = false, unique = true)
    private String valorChave;

    @Column(name = "tipo_conta", nullable = false)
    private String tipoConta;

    @Column(name = "numero_agencia", nullable = false, length=4)
    private int numeroAgencia;

    @Column(name = "numero_conta", nullable = false, length=8)
    private int numeroConta;

    @Column(name = "nome_correntista", nullable = false)
    private String nomeCorrentista;

    @Column(name = "sobrenome_correntista", nullable = true)
    private String sobrenomeCorrentista;

    @Column(name = "data_hora_inclusao_chave")
    private LocalDateTime dataHoraInclusaoChave;

    @Column(name = "data_hora_inativacao_chave")
    private LocalDateTime dataHoraInativacaoChave;

}