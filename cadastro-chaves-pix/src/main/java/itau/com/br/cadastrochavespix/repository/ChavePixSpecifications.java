package itau.com.br.cadastrochavespix.repository;

import itau.com.br.cadastrochavespix.exceptions.ValidationsRulesException;
import itau.com.br.cadastrochavespix.model.ChavePix;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

 public interface ChavePixSpecifications {
     static Specification<ChavePix> consultaCombinada(String id, String tipoChave, String agencia,
                                                     String conta, String nomeCorrentista,
                                                     String sobrenomeCorrentista, LocalDate dataHoraInclusaoChave,
                                                     LocalDate dataHoraInativacaoChave) {
        return (root, query, criterioBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (id != null && (tipoChave != null || agencia != null || conta != null || nomeCorrentista != null)) {
                throw new ValidationsRulesException("Se o ID for informado, não é aceito outros parâmetros");
            }

            if (dataHoraInclusaoChave != null && dataHoraInativacaoChave != null)
                throw new ValidationsRulesException("A busca não pode ser realizada utilizando os paramentos  " +
                        "dataHoraAtivacao e dataHoraInativacao ao mesmo tempo!!!");

            if (id != null) {
                predicates.add(criterioBuilder.equal(root.get("id"), UUID.fromString(id)));
            } else {

                if (tipoChave != null) {
                    predicates.add(criterioBuilder.equal(root.get("tipoChave"), tipoChave));
                }

                if (agencia != null) {
                    predicates.add(criterioBuilder.equal(root.get("numeroAgencia"), agencia));
                }

                if (conta != null) {
                    predicates.add(criterioBuilder.equal(root.get("numeroConta"), conta));
                }

                if (nomeCorrentista != null) {
                    predicates.add(criterioBuilder.equal(root.get("nomeCorrentista"), nomeCorrentista));
                }

                if (sobrenomeCorrentista != null) {
                    predicates.add(criterioBuilder.equal(root.get("sobreNomeCorrentista"), sobrenomeCorrentista));
                }

                if (dataHoraInclusaoChave != null) {
                    LocalDateTime startOfDay = dataHoraInclusaoChave.atStartOfDay();
                    LocalDateTime endOfDay = dataHoraInclusaoChave.atTime(23, 59, 59,
                            999999999);

                    predicates.add(criterioBuilder.between(root.get("dataHoraInclusaoChave"), startOfDay, endOfDay));
                }

                if(dataHoraInativacaoChave != null){
                    LocalDateTime startOfDay = dataHoraInativacaoChave.atStartOfDay();
                    LocalDateTime endOfDay = dataHoraInativacaoChave.atTime(23,59,59,
                            999999999);

                    predicates.add(criterioBuilder.between(root.get("dataHoraInativacaoChave"), startOfDay, endOfDay));
                }

            };

            return criterioBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
