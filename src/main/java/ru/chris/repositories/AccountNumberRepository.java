package ru.chris.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chris.model.AccountNumbers;

import java.util.List;

@Repository
public interface AccountNumberRepository extends JpaRepository<AccountNumbers, Long> {
    AccountNumbers findById(long id);

    AccountNumbers findByNumber(String number);


}
