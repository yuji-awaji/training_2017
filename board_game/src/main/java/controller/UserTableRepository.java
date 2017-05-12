package controller;
import org.springframework.data.jpa.repository.JpaRepository;

import controller.UserTable;

public interface UserTableRepository extends JpaRepository<UserTable, Integer>
{

}
