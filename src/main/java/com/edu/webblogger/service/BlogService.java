package com.edu.webblogger.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.edu.webblogger.entity.Blog;
import com.edu.webblogger.entity.User;
import com.edu.webblogger.repository.BlogRepository;
import com.edu.webblogger.repository.UserRepository;

@Service
public class BlogService {
	
	@Autowired
	protected BlogRepository blogRepository;

	@Autowired
	protected UserRepository userRepository;

	public Blog addBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	public List<Blog> findAllBlogs() {
		return blogRepository.findAll();
	}

	public List<Blog> findMyBlogs(int userId) {
		//using optional we can't get the specific user so get otherwise in nextLine we able bale call getList of blogs
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			List<Blog> blogs = user.get().getBlogs();
			return blogs;
		}else {
			return new ArrayList<>();
		}
	}

	public Blog findBlogById(int blog_id) {
	    Optional<Blog> blog = blogRepository.findById(blog_id);
	    //return blog.orElse(null);
	    if(blog.isPresent()) {
	    	return blog.get();
	    }
	    else {
	    	return null;
	    }
	    
	}
	public Blog updateBlogs(Blog blog) {
		return blogRepository.save(blog);
	}

	public Blog deleteBlog(int blogId,int userId) {
		User user = userRepository.findById(userId).get();
		Blog blog = blogRepository.findById(blogId).get();
		List<Blog> blogs = user.getBlogs();
		blogs.remove(blog);
		user.setBlogs(blogs);
		userRepository.save(user);
		blogRepository.delete(blog);
		return blog;
	}
	public List<Blog> sortBlog(int index) {
		List<Blog> blogs = blogRepository.findAll();
		Collections.sort(blogs);
		if (index == 1) {
			Collections.reverse(blogs);
			return blogs;
		} else {
			return blogs;
		}
	}


}
