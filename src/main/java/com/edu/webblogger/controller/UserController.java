package com.edu.webblogger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.webblogger.entity.Response;
import com.edu.webblogger.entity.User;
import com.edu.webblogger.service.UserService;

@RestController
public class UserController {
		
	@Autowired
	protected UserService userService;
	
	@PostMapping(value = "/user")
	protected ResponseEntity<Response<User>> addUser(@RequestBody User user) {
	    if (userService.addUser(user) == null) {
	        Response<User> response = new Response<>();
	        response.setMessage("Admin is already present");
	        response.setHttpStatusCode(HttpStatus.UNAUTHORIZED.value()); 
	        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	    }
	    else if(userService.isUserPresent(user.getEmail()) || userService.isUserPresent(user.getMobile())){
	        Response<User> response = new Response<>();
	        response.setMessage("User with same Email or Mobile is already present");
	        response.setHttpStatusCode(HttpStatus.CONFLICT.value()); 
	        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	    }
	    else {
	        User createdUser = userService.addUser(user);
	        Response<User> response = new Response<>();
	        response.setMessage("User SignUp Successfully");
	        response.setData(createdUser); 
	        response.setHttpStatusCode(HttpStatus.CREATED.value()); 
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }
	}

	@GetMapping(value = "/user")
	protected ResponseEntity<Response<User>> findUserByEmailAndPassword(@RequestBody User user) {
	    if (userService.isUserPresent(user.getEmail())) {
	        User loggedInUser = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
	        if (loggedInUser != null) {
	            if (loggedInUser.isBlocked()) {
	                Response<User> response = new Response<>();
	                response.setMessage("User is blocked.");
	                response.setHttpStatusCode(HttpStatus.OK.value()); 
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }

	            Response<User> response = new Response<>();
	            response.setMessage("User Found");
	            response.setData(loggedInUser);
	            response.setHttpStatusCode(HttpStatus.FOUND.value()); 
	            return new ResponseEntity<>(response, HttpStatus.FOUND);
	        } else {
	            // Response for incorrect password
	            Response<User> response = new Response<>();
	            response.setMessage("Invalid password");
	            response.setHttpStatusCode(HttpStatus.UNAUTHORIZED.value()); 
	            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	        }
	    } else {
	        Response<User> response = new Response<>();
	        response.setMessage("User not found");
	        response.setHttpStatusCode(HttpStatus.NOT_FOUND.value()); // 404 Not Found
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	@PutMapping(value="/user")
	protected ResponseEntity<Response<User>> updateUser(@RequestBody User user){
		User updatedUser = userService.updateService(user);
		 Response<User> response = new Response<>();
         response.setMessage("User updated..");
         response.setData(updatedUser);
         response.setHttpStatusCode(HttpStatus.OK.value()); 
         return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@DeleteMapping(value="/user")
	protected ResponseEntity<Response<User>> deleteUser(@RequestParam(name="id")int Id){
		User deletedUser = userService.deleteUser(Id);
		 Response<User> response = new Response<>();
         response.setMessage("User deleted..");
         response.setData(deletedUser);
         response.setHttpStatusCode(HttpStatus.OK.value()); 
         return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PatchMapping(value = "/user")
	protected ResponseEntity<Response<User>> mapProductToUser(@RequestParam(name = "user_id")int userId,@RequestParam(name="blog_id")int blogId){
		User user = userService.mapProductToUser(userId,blogId);
		Response<User> response = new Response<>();
		response.setMessage("Blog mapped to user");
		response.setHttpStatusCode(HttpStatus.CREATED.value());
		response.setData(user);
		return new ResponseEntity<Response<User>>(response, HttpStatus.CREATED);
	}
	@GetMapping(value="/users")
	protected ResponseEntity<Response<List<User>>> findAllUser(){
		List<User> user = userService.findAllUser();
		Response<List<User>> response = new Response<>();
		response.setMessage("All Blogs Are found");
		response.setHttpStatusCode(HttpStatus.FOUND.value());
		response.setData(user);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	@GetMapping(value="/user/block")
	protected ResponseEntity<Response<User>> blockUser(@RequestParam(name = "user_id")int userId){
	    Response<User> response = new Response<>();
	        User blockedUser = userService.blockUser(userId);
	        if (blockedUser != null) {
	            response.setData(blockedUser);
	            response.setMessage("User blocked ..");
	            response.setHttpStatusCode(HttpStatus.OK.value());
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else {
	            response.setMessage("User not found.");
	            response.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	}
	@GetMapping(value="/user/unBlock")
	protected ResponseEntity<Response<User>> unBlockUser(@RequestParam(name = "user_id")int userId){
	    Response<User> response = new Response<>();
	        User unBlockedUser = userService.unBlockUser(userId);
	        if (unBlockedUser != null) {
	            response.setData(unBlockedUser);
	            response.setMessage("User unBlocked ..");
	            response.setHttpStatusCode(HttpStatus.OK.value());
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else {
	            response.setMessage("User not found.");
	            response.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	}
}
	
