package ru.clevertec.cheque.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
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
@ContextConfiguration(initializers = {ChequeControllerTest.Initializer.class})
@AutoConfigureMockMvc
public class ChequeControllerTest {
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
    public void createChequeTest_shouldReturnChequeWithZeroDiscount() throws Exception {
        //given
        float total = 3 * 1.73f + 5 * 2.32f;
        float firstProductTotal = 5 * 2.32f;
        float secondProductTotal = 3 * 1.73f;

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cheque?1=5&2=3"))
                .andExpect(jsonPath("$.products").isArray())

                .andExpect(jsonPath("$.products[0].quantity").value(5))
                .andExpect(jsonPath("$.products[0].itemName").value("Eggs"))
                .andExpect(jsonPath("$.products[0].price").value(2.32))
                .andExpect(jsonPath("$.products[0].total").value(firstProductTotal))

                .andExpect(jsonPath("$.products[1].quantity").value(3))
                .andExpect(jsonPath("$.products[1].itemName").value("Milk"))
                .andExpect(jsonPath("$.products[1].price").value(1.73))
                .andExpect(jsonPath("$.products[1].total").value(secondProductTotal))

                .andExpect(jsonPath("$.products[2]").doesNotExist())

                .andExpect(jsonPath("$.total").value(total))
                .andExpect(jsonPath("$.discount").value(0))
                .andExpect(jsonPath("$.totalWithDiscount").value(total))

                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void createChequeTest_shouldReturnChequeWithCardAndSaleDiscount() throws Exception {
        //given
        float firstProductTotal = 10 * 2.32f;
        float secondProductTotal = 3 * 1.73f;
        float total = firstProductTotal * 0.9f + secondProductTotal;

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cheque?1=10&2=3&card=13"))
                .andExpect(jsonPath("$.products").isArray())

                .andExpect(jsonPath("$.products[0].quantity").value(10))
                .andExpect(jsonPath("$.products[0].itemName").value("Eggs"))
                .andExpect(jsonPath("$.products[0].price").value(2.32))
                .andExpect(jsonPath("$.products[0].total").value(firstProductTotal * 0.9f))

                .andExpect(jsonPath("$.products[1].quantity").value(3))
                .andExpect(jsonPath("$.products[1].itemName").value("Milk"))
                .andExpect(jsonPath("$.products[1].price").value(1.73))
                .andExpect(jsonPath("$.products[1].total").value(secondProductTotal))

                .andExpect(jsonPath("$.products[2]").doesNotExist())

                .andExpect(jsonPath("$.total").value(total))
                .andExpect(jsonPath("$.discount").value(total * 0.1f))
                .andExpect(jsonPath("$.totalWithDiscount").value(total * 0.9f))

                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}
