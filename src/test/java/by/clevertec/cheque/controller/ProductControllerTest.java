package by.clevertec.cheque.controller;

import by.clevertec.cheque.model.entity.DiscountCard;
import by.clevertec.cheque.model.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {ProductControllerTest.Initializer.class})
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testPostgres")
            .withUsername("postgres")
            .withPassword("postgres");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of(
                            "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void findByIdTest_shouldReturnProduct() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/products/5"))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("Bread"))
                .andExpect(jsonPath("$.price").value(1.46))
                .andExpect(jsonPath("$.onSale").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findAllTest_shouldReturnProducts() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[9]").exists())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void addTest_shouldReturnProductWithId() throws Exception {
        //given
        Product productWithoutId = new Product();
        productWithoutId.setName("Apple");
        productWithoutId.setPrice(3.02f);
        productWithoutId.setOnSale(false);
        ObjectMapper mapper = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .content(mapper.writeValueAsString(productWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(11))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.price").value(3.02))
                .andExpect(jsonPath("$.onSale").value(false))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void updateTest_shouldReturnUpdatedProduct() throws Exception {
        //given
        ObjectMapper mapper = new ObjectMapper();

        Product product = new Product(9L, "Test", 1.11f, false);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/products")
                        .content(mapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.price").value(1.11))
                .andExpect(jsonPath("$.onSale").value(false))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void deleteTest_shouldReturnTrue() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/products/10"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}
