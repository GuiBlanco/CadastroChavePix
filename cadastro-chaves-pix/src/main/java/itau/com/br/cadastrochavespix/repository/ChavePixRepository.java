package itau.com.br.cadastrochavespix.repository;

import itau.com.br.cadastrochavespix.model.ChavePix;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface ChavePixRepository extends JpaRepository<ChavePix, UUID>, JpaSpecificationExecutor<ChavePix> {




   Optional<List<ChavePix>> findByNumeroAgenciaAndNumeroContaAndNomeCorrentistaAndSobrenomeCorrentista
           (int numeroAgencia, int numeroConta, String nomeCorrentista, String sobrenomeCorrentista);
}