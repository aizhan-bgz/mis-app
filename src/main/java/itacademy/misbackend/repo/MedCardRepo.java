package itacademy.misbackend.repo;

import itacademy.misbackend.entity.MedCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedCardRepo extends JpaRepository<MedCard, Long> {

    MedCard findByDeletedAtIsNullAndId (Long id);
    List<MedCard> findAllByDeletedAtIsNull();

}

