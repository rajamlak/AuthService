package com.book.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.book.store.controllers.AuthController;
import com.book.store.dao.UserDAO;
import com.book.store.dto.BasicResponseDTO;
import com.book.store.dto.RegisterRequestDTO;
import com.book.store.dto.RegisterResponseDTO;
import com.book.store.enums.UserRoleEnum;
import com.book.store.models.User;
import com.book.store.services.UserDetailsService;
import com.book.store.utils.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceEbookApplicationTests {
	@Mock
	private UserDAO userDAO;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private UserDetailsService userDetailsService;

	@Mock
	private JWTUtil jwtUtil;

	@InjectMocks
	private AuthController authController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testRegisterUser_SuccessfulRegistration() {
		// Prepare test data
		RegisterRequestDTO requestDTO = new RegisterRequestDTO();
		requestDTO.setFirstName("John");
		requestDTO.setLastName("Doe");
		requestDTO.setEmail("johndoe@example.com");
		requestDTO.setPassword("password");
		requestDTO.setConfirmPassword("password");
		requestDTO.setRole(UserRoleEnum.AUTHOR);

		User user = new User();
		user.setFirstName(requestDTO.getFirstName());
		user.setLastName(requestDTO.getLastName());
		user.setEmail(requestDTO.getEmail());
		user.setRole(requestDTO.getRole());
		user.setActive(true);
		user.setPassword("hashedPassword");

		// Configure mock objects
		when(userDAO.existsByEmail("johndoe@example.com")).thenReturn(false);
		when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
		when(userDAO.save(any(User.class))).thenReturn(user);
		when(userDetailsService.loadUserByUsername("johndoe@example.com")).thenReturn(Mockito.mock(UserDetails.class));
		when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("jwtToken");

		// Perform the registration
		ResponseEntity<BasicResponseDTO<RegisterResponseDTO>> response = authController.registerUser(requestDTO);

		// Verify the response
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		BasicResponseDTO<RegisterResponseDTO> responseBody = response.getBody();
		assertEquals(true, responseBody.isSuccess());
		assertEquals("jwtToken", responseBody.getData().getToken());
		assertEquals("johndoe@example.com", responseBody.getData().getEmail());
		assertEquals("John", responseBody.getData().getName());

	}
}
