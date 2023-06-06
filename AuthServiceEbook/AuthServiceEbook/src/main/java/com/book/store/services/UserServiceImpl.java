package com.book.store.services;
//
//import com.book.store.dao.BookDAO;
//import com.book.store.dao.BookRequestDAO;
import com.book.store.dao.UserDAO;
//import com.book.store.dto.UpdateAuthorDTO;
import com.book.store.exceptions.ResourceNotFoundException;
//import com.book.store.models.Book;
import com.book.store.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getAuthorDetails(Long userId) {
        return userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

	@Override
	public void deleteUser(Long userId) {
		
	}
}
