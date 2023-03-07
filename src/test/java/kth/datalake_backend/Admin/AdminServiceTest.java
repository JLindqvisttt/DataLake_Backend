package kth.datalake_backend.Admin;

import kth.datalake_backend.Entity.Nodes.Patient;
import kth.datalake_backend.Entity.User.ERole;
import kth.datalake_backend.Entity.User.User;
import kth.datalake_backend.Payload.Request.RemoveUserRequest;
import kth.datalake_backend.Payload.Request.SignUpRequest;
import kth.datalake_backend.Payload.Request.UpdateUserRequest_Admin;
import kth.datalake_backend.Repository.Nodes.PatientRepository;
import kth.datalake_backend.Repository.User.AdminRepository;
import kth.datalake_backend.Service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    Neo4jTemplate neo4jTemplate;

    @Autowired
    PasswordEncoder encoder;

    User user = null;

    User admin = null;

    @BeforeEach
    public void init() {
        adminRepository.deleteAll();
        List<String> databases = new ArrayList<>();
        user = new User(1l,"user12", "user123", "user@gmail.com", encoder.encode("user12"),databases, ERole.valueOf("ROLE_USER"));
        adminRepository.save(user);
        admin = new User(2l,"admin12", "admim123", "admin@gmail.com", encoder.encode("admin12"), databases, ERole.valueOf("ROLE_ADMIN"));
        adminRepository.save(admin);
    }

    @Test
    @DisplayName("Retrieving correct amount of users")
    public void shouldRetrieveAllUsers() {
        assertEquals(2, adminService.getAllUser().size());
        adminRepository.save(new User("asdf", "asdfsadf", "dssdfssdsd@gmail.com", encoder.encode("user"), ERole.valueOf("ROLE_USER")));
        assertEquals(3,adminService.getAllUser().size());
    }

    @Test
    @DisplayName("updateUser allowed values ")
    public void validUpdateUser(){
        UpdateUserRequest_Admin update = new UpdateUserRequest_Admin();
        String response;
        User updateCheck;

        //set role user to admin
        update.setId(user.getId());
        update.setRole(ERole.ROLE_ADMIN);
        response = adminService.updateUser(update).getBody().toString();
        assertEquals("Successfully updated user", response);

        //set role admin to user
        update.setRole(ERole.ROLE_USER);
        response = adminService.updateUser(update).getBody().toString();
        assertEquals("Successfully updated user", response);

        //Controls that the password dose not change if none is provided
        updateCheck = adminRepository.findById(user.getId()).get();
        assertTrue(encoder.matches("user12", updateCheck.getPassword()));

        update.setPassword("AAABBB");
        response = adminService.updateUser(update).getBody().toString();
        assertEquals("Successfully updated user",  response);

        update.setPassword("Ob7wi7MK2N38XJ4pGdT9");
        response = adminService.updateUser(update).getBody().toString();
        assertEquals("Successfully updated user",  response);

        updateCheck = adminRepository.findById(user.getId()).get();
        assertEquals(ERole.ROLE_USER, updateCheck.getRole());

        Patient patient = new Patient();
        patient.setSubjectId(11);
        patient.setDataset("261");
        patientRepository.save(patient);

        List<String> databases = patientRepository.findByDatabase();
        update.setAvailableDatabases(databases);
        user.setAvailableDatabases(databases);
        assertEquals(databases.size(),user.getAvailableDatabases().size());

        //adds another dataset
        patient.setSubjectId(12);
        patient.setDataset("266");
        patientRepository.save(patient);

        databases = patientRepository.findByDatabase();
        update.setAvailableDatabases(databases);
        user.setAvailableDatabases(databases);
        assertEquals(databases.size(),user.getAvailableDatabases().size());
    }

    @Test
    @DisplayName("updateUser invalid values")
    public void invalidUpdateUser(){
        UpdateUserRequest_Admin update = new UpdateUserRequest_Admin();
        String response;

        //update password that is too short
        update.setId(user.getId());
        update.setPassword("AAABB");
        response = adminService.updateUser(update).getBody().toString();
        assertEquals("Is not a valid password, must be more the 6 and max 20 characters", response);

        //update password that is too long
        update.setId(user.getId());
        update.setPassword("PRh2Jq4z4oVHcx1n4hV2C");
        response = adminService.updateUser(update).getBody().toString();
        assertEquals("Is not a valid password, must be more the 6 and max 20 characters", response);
    }

    @Test
    @DisplayName("Register user valid")
    public void validRegisterUser(){
        SignUpRequest register = new SignUpRequest();
        String response;

        register.setFirstname("AA");
        register.setLastname("BB");
        register.setPassword("AAABBB");
        register.setUsername("A@gmail.com");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("User registered successfully", response);

        register.setFirstname("AzWF5Br6c82iTrCOwEPw");
        register.setLastname("05D5bkD6j5iG3Nhwz2uH");
        register.setPassword("AAABBB");
        register.setUsername("Gv5omnP5q7dIShdLLwL09J9pGPrQSa@gmail.com");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("User registered successfully", response);
    }

    @Test
    @DisplayName("Register user invalid")
    public void invalidRegisterUser(){
        SignUpRequest register = new SignUpRequest();
        String response;

        //Last name short
        register.setFirstname("A");
        register.setLastname("BB");
        register.setPassword("AAABBB");
        register.setUsername("A@gmail.com");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("Invalid first name must be at least 2 characters and max 20 characters", response);

        //First name long
        register.setFirstname("hUhMR7ywBINPg4P4GjPPp");
        register.setLastname("BB");
        register.setPassword("AAABBB");
        register.setUsername("A@gmail.com");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("Invalid first name must be at least 2 characters and max 20 characters", response);

        //Last name short
        register.setFirstname("AA");
        register.setLastname("B");
        register.setPassword("AAABBB");
        register.setUsername("A@gmail.com");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("Invalid last name must be at least 2 characters and max 20 characters", response);

        //Last name long
        register.setFirstname("AA");
        register.setLastname("BUhMR7ywBINPg4P4GjPPp");
        register.setPassword("AAABBB");
        register.setUsername("A@gmail.com");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("Invalid last name must be at least 2 characters and max 20 characters", response);

        //Password short
        register.setFirstname("AA");
        register.setLastname("BB");
        register.setPassword("AAABB");
        register.setUsername("A@gmail.com");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("Invalid password must be at least 6 and max 20 characters", response);

        //Password long
        register.setFirstname("AA");
        register.setLastname("BB");
        register.setPassword("rRWegUQB7wDklZlwOTXIo");
        register.setUsername("A@gmail.com");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("Invalid password must be at least 6 and max 20 characters", response);

        //email short
        register.setFirstname("AA");
        register.setLastname("BB");
        register.setPassword("AAABBB");
        register.setUsername("A");

        response = adminService.registerUser(register).getBody().toString();
        assertEquals("User registered successfully", response);

        //email long

        //email same
    }

    @Test
    @DisplayName("Remove User")
    public void removeUser(){
        RemoveUserRequest userRequest = new RemoveUserRequest();
        String response;

        //no user with ID found
        userRequest.setId(5l);
        response = adminService.removeUser(userRequest).getBody().toString();
        assertEquals("Could not find the user", response);

        //remove user successful
        Optional<User> getUser1 = adminRepository.findById(user.getId());
        userRequest.setId(getUser1.get().getId());
        response = adminService.removeUser(userRequest).getBody().toString();
        assertEquals("Successfully removed user", response);
        assertEquals(1, adminRepository.findAll().size());
    }


    @Test
    @DisplayName("Invalid ID")
    public void invalidID() {
        UpdateUserRequest_Admin update = new UpdateUserRequest_Admin();
        String response;
        update.setId(9L);
        update.setFirstname(user.getFirstName());
        update.setLastname(user.getLastName());
        update.setPassword("user12");
        response = adminService.updateUser(update).getBody().toString();
        assertEquals("Could not find the user", response);
    }
}
