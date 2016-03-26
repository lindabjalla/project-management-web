package se.grouprich.projectmanagement.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.grouprich.projectmanagement.model.TeamData;

public interface TeamRepository extends PagingAndSortingRepository<TeamData, Long> {}
