package controller;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTableRepository extends JpaRepository<UserTable, Integer>
{
	public List<UserTable> findByUserIdContains(String userId);
}