package com.drill;


import com.drill.dao.InvestorDao;
import com.drill.dao.impl.InvestorDaoImpl;
import com.drill.dto.UserCreationDto;
import com.drill.entity.User;

public class InvestorUserDaoTest {
    public static void main(String[] args) {
        InvestorDao investorDao = new InvestorDaoImpl();

        // Test createUser
        System.out.println("Testing createUser...");
        UserCreationDto newUser = new UserCreationDto();
        newUser.setFirstName("Investor");
        newUser.setLastName("User");
        newUser.setEmail("investor.user@example.com");
        newUser.setPassword("password123");

        Integer createdUserId = investorDao.createUser(newUser);
        System.out.println("Created User ID: " + createdUserId);

        // Test login
        System.out.println("\nTesting login...");
        User loggedInUser = investorDao.login("investor.user@example.com", "password123");
        if (loggedInUser != null) {
            System.out.println("Logged In User: " + loggedInUser);
        } else {
            System.out.println("Login failed for user.");
        }

        // Test updateUser
        System.out.println("\nTesting updateUser...");
        if (loggedInUser != null) {
            loggedInUser.setFirstName("UpdatedInvestor");
            loggedInUser.setLastName("User");
            loggedInUser.setPassword("newpassword456");
            boolean isUpdated = investorDao.updateUser(loggedInUser.getUserId(), loggedInUser);
            System.out.println("User updated: " + isUpdated);

            User updatedUser = investorDao.login("investor.user@example.com", "newpassword456");
            System.out.println("Updated User after login: " + updatedUser);
        }

        System.out.println("\nAll tests completed.");
    }
}

