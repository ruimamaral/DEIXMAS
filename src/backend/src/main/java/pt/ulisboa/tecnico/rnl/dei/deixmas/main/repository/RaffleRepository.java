package pt.ulisboa.tecnico.rnl.dei.deixmas.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain.Raffle;

@Repository
@Transactional
public interface RaffleRepository extends JpaRepository<Raffle, Long> {

}