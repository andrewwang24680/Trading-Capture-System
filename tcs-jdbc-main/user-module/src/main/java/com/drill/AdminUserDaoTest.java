package com.drill;

import com.drill.dao.AdminDao;
import com.drill.dao.impl.AdminDaoImpl;
import com.drill.dto.UserCreationDto;
import com.drill.entity.Role;
import com.drill.entity.User;
import java.util.Arrays;
import java.util.List;

public class AdminUserDaoTest {
    public static void main(String[] args) {
        AdminDao adminDao = new AdminDaoImpl();

        System.out.println("Testing createUser...");
        UserCreationDto newUser = new UserCreationDto();
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setEmail("test.user@example.com");
        newUser.setPassword("password123");
        newUser.setRoleIds(Arrays.asList(1, 2)); // Admin and Investor roles

        Integer createdUserId = adminDao.createUser(newUser);
        System.out.println("Created User ID: " + createdUserId);

        // Test getUserById
        System.out.println("\nTesting getUserById...");
        User fetchedUser = adminDao.getUserById(createdUserId);
        System.out.println("Fetched User: " + fetchedUser);
        if (fetchedUser != null) {
            System.out.println("Roles:");
            for (Role role : fetchedUser.getRoles()) {
                System.out.println(role);
            }
        }

        // Test login
        System.out.println("\nTesting login...");
        User loggedInUser = adminDao.login("test.user@example.com", "password123");
        System.out.println("Logged In User: " + loggedInUser);

        // Test updateUser
        System.out.println("\nTesting updateUser...");
        fetchedUser.setFirstName("Updated");
        fetchedUser.setLastName("User");
        fetchedUser.setPassword("newpassword456");
        boolean isUpdated = adminDao.updateUser(createdUserId, fetchedUser);
        System.out.println("User updated: " + isUpdated);

        // Test getAllUsers
        System.out.println("\nTesting getAllUsers...");
        List<User> allUsers = adminDao.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }

        // Test isAdmin
        System.out.println("\nTesting isAdmin...");
        boolean isAdmin = adminDao.isAdmin(createdUserId);
        System.out.println("Is Admin: " + isAdmin);

        System.out.println("\nAll tests completed.");
    }
}
