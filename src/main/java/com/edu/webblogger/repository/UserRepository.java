package com.edu.webblogger.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.webblogger.entity.Role;
import com.edu.webblogger.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer>{

	User findUserByEmailAndPassword(String email, String password);


	Optional<User> findByEmail(String email);


	Optional<User> findByMobile(long mobile);


	boolean existsByRole(Role admin);

}
