package kth.datalake_backend.User;


import kth.datalake_backend.Entity.User.ERole;
import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Payload.Request.UpdateUserPasswordRequest;

import kth.datalake_backend.Payload.Request.UpdateUserFirstnameAndLastnameRequest;
import kth.datalake_backend.Repository.User.UserRepository;

import kth.datalake_backend.Service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    User user = null;

    User admin = null;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        List<String> databases = new ArrayList<>();
        user = new User(1l, "user12", "user123", "user@gmail.com", encoder.encode("user12"), databases, ERole.valueOf("ROLE_USER"));
        userRepository.save(user);
        admin = new User(2l, "admin12", "admim123", "admin@gmail.com", encoder.encode("admin12"), databases, ERole.valueOf("ROLE_ADMIN"));
        userRepository.save(admin);
    }

    @Test
    @DisplayName("Invalid Password Update")
    public void invalidUpdatePassword() {
        UpdateUserPasswordRequest update = new UpdateUserPasswordRequest();
        UpdateUserPasswordRequest updateAdmin = new UpdateUserPasswordRequest();
        String response;

        //null password update -- USER
        update.setId(user.getId());
        update.setCheckPassword("user12");
        update.setPassword("");
        response = userService.updateUserPassword(update).getBody().toString();
        assertEquals("Invalid password must be at least 6 and max 20 characters", response);

        //null password update -- ADMIN
        updateAdmin.setId(admin.getId());
        updateAdmin.setCheckPassword("admin12");
        updateAdmin.setPassword("");
        response = userService.updateUserPassword(updateAdmin).getBody().toString();
        assertEquals("Invalid password must be at least 6 and max 20 characters", response);

        //too long password -- USER
        update.setPassword("1234567890qwertyuiopq");
        response = userService.updateUserPassword(update).getBody().toString();
        assertEquals("Invalid password must be at least 6 and max 20 characters", response);

        //too long password -- ADMIN
        updateAdmin.setPassword("1234567890qwertyuiopq");
        response = userService.updateUserPassword(updateAdmin).getBody().toString();
        assertEquals("Invalid password must be at least 6 and max 20 characters", response);
    }

    @Test
    @DisplayName("Invalid Firstname Update")
    public void invalidUpdateFirstname() {
        UpdateUserFirstnameAndLastnameRequest update = new UpdateUserFirstnameAndLastnameRequest();
        UpdateUserFirstnameAndLastnameRequest updateAdmin = new UpdateUserFirstnameAndLastnameRequest();
        String response;

        //null firstname update -- USER
        update.setId(user.getId());
        update.setFirstname("");
        update.setLastname(user.getLastName());
        response = userService.updateUserName(update).getBody().toString();
        assertEquals("Invalid first name must be at least 2 characters and max 20 characters", response);

        //null firstname update -- ADMIN
        updateAdmin.setId(admin.getId());
        updateAdmin.setFirstname("");
        updateAdmin.setLastname(admin.getLastName());
        response = userService.updateUserName(updateAdmin).getBody().toString();
        assertEquals("Invalid first name must be at least 2 characters and max 20 characters", response);

        //too long firstname update -- USER
        update.setFirstname("kasperlindstromandviktorlindstrom");
        response = userService.updateUserName(update).getBody().toString();
        assertEquals("Invalid first name must be at least 2 characters and max 20 characters", response);

        //too long firstname update -- ADMIN
        updateAdmin.setFirstname("kasperlindstromandviktorlindstrom");
        response = userService.updateUserName(updateAdmin).getBody().toString();
        assertEquals("Invalid first name must be at least 2 characters and max 20 characters", response);

        //null lastname update -- USER
        update.setId(user.getId());
        update.setFirstname(user.getFirstName());
        update.setLastname("");
        response = userService.updateUserName(update).getBody().toString();
        assertEquals("Invalid last name must be at least 2 characters and max 20 characters", response);

        //null lastname update -- ADMIN
        updateAdmin.setId(admin.getId());
        updateAdmin.setFirstname(admin.getFirstName());
        updateAdmin.setLastname("");
        response = userService.updateUserName(updateAdmin).getBody().toString();
        assertEquals("Invalid last name must be at least 2 characters and max 20 characters", response);

        //too long lastname update -- USER
        update.setLastname("kasperlindstromandviktorlindstrom");
        response = userService.updateUserName(update).getBody().toString();
        assertEquals("Invalid last name must be at least 2 characters and max 20 characters", response);

        //too long lastname update -- ADMIN
        updateAdmin.setLastname("kasperlindstromandviktorlindstrom");
        response = userService.updateUserName(update).getBody().toString();
        assertEquals("Invalid last name must be at least 2 characters and max 20 characters", response);
    }

    @Test
    @DisplayName("Inputting valid update password values")
    public void validUpdateFirstLastName() {
        UpdateUserFirstnameAndLastnameRequest update = new UpdateUserFirstnameAndLastnameRequest();
        UpdateUserFirstnameAndLastnameRequest updateAdmin = new UpdateUserFirstnameAndLastnameRequest();
        String response;

        //short first and last name update -- USER
        update.setId(user.getId());
        update.setFirstname("AA");
        update.setLastname("BB");
        response = userService.updateUserName(update).getBody().toString();
        assertEquals("Successfully update firstname and lastname", response);

        //short first and last name update -- ADMIN
        updateAdmin.setId(admin.getId());
        updateAdmin.setFirstname("AA");
        updateAdmin.setLastname("BB");
        response = userService.updateUserName(updateAdmin).getBody().toString();
        assertEquals("Successfully update firstname and lastname", response);


        //long first and last name update -- USER
        update.setId(user.getId());
        update.setFirstname("AMpEE3omTtgkPncI9WYs");
        update.setLastname("BMpEE3omTtgkPncI9WYs");
        response = userService.updateUserName(update).getBody().toString();
        assertEquals("Successfully update firstname and lastname", response);

        //long first and last name update -- ADMIN
        updateAdmin.setId(admin.getId());
        updateAdmin.setFirstname("AMpEE3omTtgkPncI9WYs");
        updateAdmin.setLastname("BMpEE3omTtgkPncI9WYs");
        response = userService.updateUserName(updateAdmin).getBody().toString();
        assertEquals("Successfully update firstname and lastname", response);
    }

    @Test
    @DisplayName("Inputting valid update password values")
    public void validUpdatePassword() {
        UpdateUserPasswordRequest update = new UpdateUserPasswordRequest();
        UpdateUserPasswordRequest updateAdmin = new UpdateUserPasswordRequest();
        String response;

        //random password update -- USER
        update.setId(user.getId());
        update.setCheckPassword("user12");
        update.setPassword("TestPassword");
        response = userService.updateUserPassword(update).getBody().toString();
        assertEquals("Successfully update the password", response);

        //random password update -- ADMIN
        updateAdmin.setId(admin.getId());
        updateAdmin.setCheckPassword("admin12");
        updateAdmin.setPassword("TestPassword");
        response = userService.updateUserPassword(updateAdmin).getBody().toString();
        assertEquals("Successfully update the password", response);

        //long password -- USER
        update.setId(user.getId());
        update.setCheckPassword("TestPassword");
        update.setPassword("ozfdqnhuqmgvlqikeoco");
        response = userService.updateUserPassword(update).getBody().toString();
        assertEquals("Successfully update the password", response);

        //long password -- ADMIN
        updateAdmin.setId(admin.getId());
        updateAdmin.setCheckPassword("TestPassword");
        updateAdmin.setPassword("ozfdqnhuqmgvlqikeoco");
        response = userService.updateUserPassword(updateAdmin).getBody().toString();
        assertEquals("Successfully update the password", response);

        //short password -- USER
        update.setId(user.getId());
        update.setCheckPassword("ozfdqnhuqmgvlqikeoco");
        update.setPassword("sigxqt");
        response = userService.updateUserPassword(update).getBody().toString();
        assertEquals("Successfully update the password", response);

        //short password -- ADMIN
        updateAdmin.setId(admin.getId());
        updateAdmin.setCheckPassword("ozfdqnhuqmgvlqikeoco");
        updateAdmin.setPassword("sigxqt");
        response = userService.updateUserPassword(updateAdmin).getBody().toString();
        assertEquals("Successfully update the password", response);
    }

    @Test
    @DisplayName("Invalid ID")
    public void invalidID() {
        UpdateUserPasswordRequest update = new UpdateUserPasswordRequest();
        UpdateUserFirstnameAndLastnameRequest updateName = new UpdateUserFirstnameAndLastnameRequest();
        String response;

        //invalid ID -- USER
        update.setId(5L);
        update.setCheckPassword("user12");
        update.setPassword("TestPassword");
        response = userService.updateUserPassword(update).getBody().toString();
        assertEquals("Could not find the user", response);

        //invalid ID -- USER
        updateName.setId(5L);
        updateName.setFirstname(user.getFirstName());
        updateName.setLastname(user.getLastName());
        response = userService.updateUserName(updateName).getBody().toString();
        assertEquals("Could not find the user", response);
    }
}
