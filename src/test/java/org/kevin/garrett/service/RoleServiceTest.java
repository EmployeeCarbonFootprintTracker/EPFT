package org.kevin.garrett.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kevin.garrett.model.Role;
import org.kevin.garrett.repository.RoleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRole() {
        Role role = new Role("ADMIN");
        when(roleRepository.save(role)).thenReturn(role);

        Role savedRole = roleService.saveRole(role);

        assertEquals("ADMIN", savedRole.getName());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testGetAllRoles() {
        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        List<Role> roles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> fetchedRoles = roleService.getAllRoles();

        assertEquals(2, fetchedRoles.size());
        assertEquals("USER", fetchedRoles.get(0).getName());
        assertEquals("ADMIN", fetchedRoles.get(1).getName());
        verify(roleRepository, times(1)).findAll();
    }
}
