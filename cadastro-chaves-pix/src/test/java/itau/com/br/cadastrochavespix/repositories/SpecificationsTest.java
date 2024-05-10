//package itau.com.br.cadastrochavespix.repositories;
//
//import itau.com.br.cadastrochavespix.exceptions.ValidationsRulesException;
//import itau.com.br.cadastrochavespix.model.ChavePix;
//import itau.com.br.cadastrochavespix.repository.ChavePixSpecifications;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.junit.jupiter.api.Test;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//
//public class SpecificationsTest {
//    @Test
//    public void testConsultaCombinada_IDWithOtherParameters_ThrowsValidationsRulesException() {
//        assertThrows(ValidationsRulesException.class, () -> {
//            ChavePixSpecifications.consultaCombinada("123", "CPF", "1234", "567890", "João", "Silva",
//                    LocalDate.of(2022, 5, 1), LocalDate.of(2022, 5, 2)).toPredicate(null, null, null);
//        });
//    }
//
//    @Test
//    public void testConsultaCombinada_DataInclusaoAndDataInativacao_ThrowsValidationsRulesException() {
//        assertThrows(ValidationsRulesException.class, () -> {
//            ChavePixSpecifications.consultaCombinada(null, null, null, null, null, null,
//                    LocalDate.of(2022, 5, 1), LocalDate.of(2022, 5, 2)).toPredicate(null, null, null);
//        });
//    }
//
//    @Test
//    public void testConsultaCombinada_NoID_CreatesPredicateCorrectly() {
//        Specification<ChavePix> specification = ChavePixSpecifications.consultaCombinada("920766b9-8edf-485b-a6db-24b52322fd05",
//                null, null, null, null, null,
//                null, null);
//        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
//        Root<ChavePix> root = mock(Root.class);
//        Predicate predicate = specification.toPredicate(root, null, criteriaBuilder);
//        assertNotNull(predicate);
//    }
//}
