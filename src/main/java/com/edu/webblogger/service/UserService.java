package com.edu.webblogger.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.webblogger.entity.Blog;
import com.edu.webblogger.entity.Role;
import com.edu.webblogger.entity.User;
import com.edu.webblogger.repository.UserRepository;
import com.edu.webblogger.repository.BlogRepository;

@Service
public class UserService {

	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	UserRepository userRepository;
	public boolean isUserPresent(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
	public boolean isUserPresent(long mobile) {
        return userRepository.findByMobile(mobile).isPresent();
    }
	public User addUser(User user1 ) {
		 if (user1.getRole().equals(Role.ADMIN)) {
		        // Use a repository method to check if an admin already exists
		        if (userRepository.existsByRole(Role.ADMIN)) {
		            return null;  // Prevent adding another admin
		        }
		}
		return userRepository.save(user1);
	}
	public User findUserByEmailAndPassword(String email, String password) {
		return userRepository.findUserByEmailAndPassword(email,password);
	}
	public User updateService(User user) {
		return userRepository.save(user);
	}
	public User deleteUser(int id) {
		User user = userRepository.findById(id).get();
		 userRepository.delete(user);
		 return user;
	}
	public User mapProductToUser(int userId, int blogId) {
			User user = userRepository.findById(userId).get();
			Blog blog = blogRepository.findById(blogId).get();
			List<Blog> blogs = user.getBlogs();
			blogs.add(blog);
			user.setBlogs(blogs);
			return userRepository.save(user);
	}
	public List<User> findAllUser() {
		return userRepository.findAll();
	}
	public User blockUser(int userId) {
		Optional<User> optUser=userRepository.findById(userId);
		if(optUser.isPresent()) {
			User user = optUser.get();
			user.setBlocked(true);
			return userRepository.save(user);
		}
		else {
			return null;
		}
	}
	public User unBlockUser(int userId) {
		Optional<User> optUser=userRepository.findById(userId);
		if(optUser.isPresent()) {
			User user = optUser.get();
			user.setBlocked(false);
			return userRepository.save(user);
		}
		else {
			return null;
		}
	}

}
