package jp.com.xpower.app2017.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTableRepository extends JpaRepository<UserTable, Integer>
{
	public List<UserTable> findByUserId(String userId);
}