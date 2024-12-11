package org.kevin.garrett.service;

import org.kevin.garrett.model.User;
import org.kevin.garrett.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getTeamSummary() {
        // Fetch all users with the EMPLOYEE role
        return userRepository.findUsersByRoleName("EMPLOYEE");
    }
}
