package lcy.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lcy.restful.entity.Address;
import lcy.restful.entity.Contact;
import lcy.restful.entity.User;
import lcy.restful.model.AddressResponse;
import lcy.restful.model.CreateAddressRequest;
import lcy.restful.model.UpdateAddressRequest;
import lcy.restful.model.WebResponse;
import lcy.restful.repository.AddressRepository;
import lcy.restful.repository.ContactRepository;
import lcy.restful.repository.UserRepository;
import lcy.restful.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setName("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setToken("test");
        user.setToken_expired_at(System.currentTimeMillis() + 10000000000L);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("test");
        contact.setUser(user);
        contact.setFirstname("Martionus");
        contact.setLastname("Yongki");
        contact.setEmail("email@example.com");
        contact.setPhone("0812345678");
        contactRepository.save(contact);
    }

    @Test
    void createAddressBadRequest() throws Exception{
        CreateAddressRequest request = new CreateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
                post("/api/contacts/test/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
           assertNotNull(response.getErrors());
        });
    }

    @Test
    void createAddressSuccess() throws Exception{
        CreateAddressRequest request = new CreateAddressRequest();
        request.setStreet("jalan");
        request.setCity("kota");
        request.setProvince("provinsi");
        request.setCountry("negara");
        request.setPostalCode("123123");

        mockMvc.perform(
                post("/api/contacts/test/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("jalan", response.getData().getStreet());
            assertEquals("kota", response.getData().getCity());
            assertEquals("provinsi", response.getData().getProvince());
            assertEquals("negara", response.getData().getCountry());
            assertEquals("123123", response.getData().getPostalCode());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void getAddressNotFound() throws Exception{
        mockMvc.perform(
                get("/api/contacts/test/addresses/123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setStreet("jalan");
        address.setCity("kota");
        address.setProvince("provinsi");
        address.setCountry("negara");
        address.setPostalCode("123123");
        address.setContact(contact);
        addressRepository.save(address);

        mockMvc.perform(
                get("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(address.getId(),response.getData().getId());
            assertEquals(address.getStreet(),response.getData().getStreet());
            assertEquals(address.getCity(),response.getData().getCity());
            assertEquals(address.getProvince(),response.getData().getProvince());
            assertEquals(address.getCountry(),response.getData().getCountry());
            assertEquals(address.getPostalCode(),response.getData().getPostalCode());
        });
    }

    @Test
    void updateAddressBadRequest() throws Exception{
        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
                put("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setStreet("jalan");
        address.setCity("kota");
        address.setProvince("provinsi");
        address.setCountry("negara");
        address.setPostalCode("123123");
        address.setContact(contact);
        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setStreet("jalan");
        request.setCity("kota");
        request.setProvince("provinsi");
        request.setCountry("negara baru");
        request.setPostalCode("123123");

        mockMvc.perform(
                put("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(request.getStreet(),response.getData().getStreet());
            assertEquals(request.getCity(),response.getData().getCity());
            assertEquals(request.getProvince(),response.getData().getProvince());
            assertEquals(request.getCountry(),response.getData().getCountry());
            assertEquals(request.getPostalCode(),response.getData().getPostalCode());
        });
    }

    @Test
    void deleteAddressNotFound() throws Exception{
        mockMvc.perform(
                delete("/api/contacts/test/addresses/123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void deleteAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setStreet("jalan");
        address.setCity("kota");
        address.setProvince("provinsi");
        address.setCountry("negara");
        address.setPostalCode("123123");
        address.setContact(contact);
        addressRepository.save(address);

        mockMvc.perform(
                delete("/api/contacts/test/addresses/test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
        });
    }

    @Test
    void getAddressListNotFound() throws Exception{
        mockMvc.perform(
                get("/api/contacts/salah/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getAddressListSuccess() throws Exception{
        Contact contact = contactRepository.findById("test").orElseThrow();

        for (int i = 0; i < 100 ; i++) {
            Address address = new Address();
            address.setId("test " + i);
            address.setStreet("jalan");
            address.setCity("kota");
            address.setProvince("provinsi");
            address.setCountry("negara");
            address.setPostalCode("123123");
            address.setContact(contact);
            addressRepository.save(address);
        }

        mockMvc.perform(
                get("/api/contacts/test/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<AddressResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(100, response.getData().size());
        });
    }
}