package com.edu.webblogger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.webblogger.entity.Blog;
import com.edu.webblogger.entity.Response;
import com.edu.webblogger.service.BlogService;

@RestController
public class BlogController {
	
	@Autowired
	protected BlogService blogService;
	
	@PostMapping(value="/blog")
	private ResponseEntity<Response<Blog>> addBlog(@RequestBody Blog blog){
		Blog addedBlog = blogService.addBlog(blog);
		Response<Blog> response = new Response<>();
		response.setMessage("Blog added");
		response.setHttpStatusCode(HttpStatus.CREATED.value());
		response.setData(addedBlog);
		return new ResponseEntity<Response<Blog>>(response, HttpStatus.CREATED);
	}
	@GetMapping(value="/blogs")
	private ResponseEntity<Response<List<Blog>>> findAllBlogs(){
		List<Blog> allBlogs = blogService.findAllBlogs();
		Response<List<Blog>> response = new Response<>();
		response.setMessage("Blogs found ");
		response.setHttpStatusCode(HttpStatus.FOUND.value());
		response.setData(allBlogs);
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
	@GetMapping(value="/myblogs")
	private ResponseEntity<Response<List<Blog>>> findMyBlogs(@RequestParam(name = "user_id")int userId){
		List<Blog> myBlogs = blogService.findMyBlogs(userId);
		Response<List<Blog>> response = new Response<>();
		if(!myBlogs.isEmpty()) {
			response.setMessage("Blogs found ");
			response.setHttpStatusCode(HttpStatus.FOUND.value());
			response.setData(myBlogs);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
		}
		else {
			response.setMessage("Your Blog List is empty");
			response.setHttpStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<>(response,HttpStatus.FOUND);
		}
	}
	@GetMapping(value="/blog/{id}")
	private ResponseEntity<Response<Blog>> findBlogById(@PathVariable(name="id")int blog_id){
		Blog blog = blogService.findBlogById(blog_id);
		Response<Blog> response = new Response<>();
		if(blog != null) {
		response.setMessage("Blog found ");
		response.setHttpStatusCode(HttpStatus.FOUND.value());
		response.setData(blog);
		return new ResponseEntity<>(response,HttpStatus.FOUND);
		}
		else {
			response.setMessage("Blog not found ");
			response.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}	
	}
	@PutMapping(value="/blog")
	private ResponseEntity<Response<Blog>> updateBlogs(@RequestBody Blog blog){
		Blog updatedBlog = blogService.updateBlogs(blog);
		Response<Blog> response = new Response<>();
		response.setMessage("Blog Updated");
		response.setHttpStatusCode(HttpStatus.OK.value());
		response.setData(updatedBlog);
		return new ResponseEntity<Response<Blog>>(response, HttpStatus.OK);
	}
	@DeleteMapping(value="/blog")
	private ResponseEntity<Response<Blog>> deleteBlog(@RequestParam (name="blog_Id") int blogId,@RequestParam(name="user_Id") int userId){
		Blog deletedBlog = blogService.deleteBlog(blogId, userId);
		Response<Blog> response = new Response<>();
		response.setMessage(" Blog Deleted ");
		response.setHttpStatusCode(HttpStatus.OK.value());
		response.setData(deletedBlog);
		return new ResponseEntity<Response<Blog>>(response, HttpStatus.OK);
	}
	@GetMapping(value="/blog/sort/{Index}")
	private ResponseEntity<Response<List<Blog>>> sortBlog(@PathVariable(name="Index")int index) {
	    Response<List<Blog>> response = new Response<>(); // Create a new response object
	    List<Blog> sortedBlogs = blogService.sortBlog(index); // Call the existing sortBlog method

	    if (sortedBlogs.isEmpty()) {
	        response.setMessage("No blogs found");
	        response.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
	        response.setData(null); // No data to return
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Return NOT FOUND status
	    } else {
	        response.setMessage("Blogs sorted successfully");
	        response.setHttpStatusCode(HttpStatus.OK.value());
	        response.setData(sortedBlogs); // Set the sorted blogs in response
	        return new ResponseEntity<>(response, HttpStatus.OK); // Return OK status
	    }
	}
}
