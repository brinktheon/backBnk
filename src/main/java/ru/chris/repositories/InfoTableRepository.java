package ru.chris.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chris.model.InfoTable;

@Repository
public interface InfoTableRepository  extends JpaRepository<InfoTable, Long> {
}
