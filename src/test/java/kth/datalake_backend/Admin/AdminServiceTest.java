package kth.datalake_backend.Admin;

import kth.datalake_backend.Entity.Nodes.Patient;
import kth.datalake_backend.Entity.User.ERole;
import kth.datalake_backend.Entity.User.User;
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
import java.util.ArrayList;
import java.util.List;

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
    @DisplayName("Update allowed values of a user")
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
    }


    @Test
    @DisplayName("Updating databases")
    public void validUpdateDatabases(){
        UpdateUserRequest_Admin update = new UpdateUserRequest_Admin();
        Patient patient = new Patient();
        patient.setSubjectId(12);
        patient.setDataset("261");
        patientR

        Patient patient1 = new Patient();
        patient.setSubjectId(12);
        patient.setDataset("266");


        List<String> databases = patientRepository.findByDatabase();
        update.setAvailableDatabases(databases);
        user.setAvailableDatabases(databases);
        assertNotNull(user.getAvailableDatabases());
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
