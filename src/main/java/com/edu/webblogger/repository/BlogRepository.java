package com.edu.webblogger.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.webblogger.entity.Blog;

@Repository
public interface  BlogRepository  extends JpaRepository<Blog,Integer>{
}
