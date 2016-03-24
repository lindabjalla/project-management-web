package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import se.grouprich.projectmanagement.model.TeamData;
import se.grouprich.projectmanagement.model.UserData;

public interface UserRepository extends PagingAndSortingRepository<UserData, Long>
{
	UserData findByControlNumber(Long controlNumber);

	UserData findByFirstNameAndLastNameAndUsername(String firstName, String lastName, String username);

	List<UserData> findAllByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username);

	List<UserData> findByTeam(TeamData team);
}
