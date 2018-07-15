package com.robertrakoski.news;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTest.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTest.sql")
})
public class UserServiceTest extends AbstractTest {

	@Autowired
	UserService userService;
	
	@Test
	public void testGetById() {
		Long id = 3L;
		
		User user = userService.getById(id);
		
		assertNotNull("expected not null", user);
		assertEquals("expected id attribute match", id, user.getId());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testGetById_whenIdNotFound_shouldthrowEntityNotFoundException() {
		Long id = Long.MAX_VALUE;
		
		userService.getById(id);
	}
	
	@Test
	public void testCreate() {
		User user = new User(null, "New", "password", "technology");
		
		User createdUser = userService.create(user);
		
		assertEquals("expected toString to match", user.toString(), createdUser.toString());
	}
	
	@Test(expected = EntityExistsException.class)
	public void testCreate_whenEntityWithGivenIdAlreadyExist_shouldThrowEntityExistsException() {
		User user = new User(1L, "New", "password", "technology");
		
		userService.create(user);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreate_whenInvalidPasswordLength_shouldThrowSqlException() {
		User user = new User(null, "New", "pass", "technology");
		
		userService.create(user);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreate_whenNoUsername_shouldThrowSqlException() {
		User user = new User(null, null, "password", "technology");
		
		userService.create(user);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreate_whenInvalidUsernameLength_shouldThrowSqlException() {
		User user = new User(null, "ThisIsVeryLongNameForThrowingExceptionPurposes", "password", "technology");
		
		userService.create(user);
	}
	
}
