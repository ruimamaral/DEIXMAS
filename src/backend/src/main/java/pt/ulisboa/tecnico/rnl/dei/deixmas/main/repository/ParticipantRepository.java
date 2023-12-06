package pt.ulisboa.tecnico.rnl.dei.deixmas.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain.Participant;

@Repository
@Transactional
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}