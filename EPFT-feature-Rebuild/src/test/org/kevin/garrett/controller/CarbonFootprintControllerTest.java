package org.kevin.garrett.controller;

import org.junit.jupiter.api.Test;
import org.kevin.garrett.model.CarbonFootprintEntry;
import org.kevin.garrett.model.User;
import org.kevin.garrett.service.CarbonFootprintService;
import org.kevin.garrett.service.UserService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarbonFootprintControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarbonFootprintService service;

    @MockBean
    private UserService userService;

    /**
     * Test updating an existing Carbon Footprint Entry.
     */
    @Test
    public void testUpdateEntry() throws Exception {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("employee");

        CarbonFootprintEntry existingEntry = new CarbonFootprintEntry();
        existingEntry.setId(1L);
        existingEntry.setActivityType("Car");
        existingEntry.setDistance(20.0);

        // Mock UserService and CarbonFootprintService behavior
        Mockito.when(userService.getUserByUsername("employee")).thenReturn(mockUser);
        Mockito.when(service.getEntryByIdAndUser(1L, mockUser)).thenReturn(existingEntry);

        // Act & Assert
        mockMvc.perform(post("/employee/carbon-footprint/update/1")
                        .param("activityType", "Bike")
                        .param("distance", "15.0")
                        .with(SecurityMockMvcRequestPostProcessors.user("employee").roles("EMPLOYEE")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/carbon-footprint/dashboard"));

        // Verify that the updateEntry method was called with the expected data
        Mockito.verify(service).updateEntry(Mockito.eq(1L), Mockito.argThat(entry ->
                "Bike".equals(entry.getActivityType()) && entry.getDistance() == 15.0
        ), Mockito.eq(mockUser));
    }

    /**
     * Test creating a new Carbon Footprint Entry.
     */
    @Test
    public void testCreateEntry() throws Exception {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("employee");

        Mockito.when(userService.getUserByUsername("employee")).thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(post("/employee/carbon-footprint/create")
                        .param("activityType", "Carpool")
                        .param("distance", "10.0")
                        .param("degreeChange", "2")
                        .with(SecurityMockMvcRequestPostProcessors.user("employee").roles("EMPLOYEE")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/carbon-footprint/dashboard"));

        // Verify saveEntry was called with correct data
        Mockito.verify(service).saveEntry(Mockito.argThat(entry ->
                "Carpool".equals(entry.getActivityType()) &&
                        entry.getDistance() == 10.0 &&
                        entry.getDegreeChange() == 2
        ));
    }
}
