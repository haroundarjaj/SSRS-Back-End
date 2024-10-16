package com.haroun.ssrs.utils;

import com.haroun.ssrs.model.AppRole;
import com.haroun.ssrs.model.AppUser;
import com.haroun.ssrs.model.DatabaseSequence;
import com.haroun.ssrs.repository.AppRoleRepository;
import com.haroun.ssrs.repository.AppUserRepository;
import com.haroun.ssrs.repository.DatabaseSequenceRepository;
import com.haroun.ssrs.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

@Transactional
@Component
public class DataGenerator {

    private final AppRoleRepository roleRepository;
    private final AppUserRepository userRepository;
    private final DatabaseSequenceRepository databaseSequenceRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SequenceGeneratorService sequenceGenerator;

    public DataGenerator(
            AppRoleRepository roleRepository,
            AppUserRepository userRepository,
            DatabaseSequenceRepository databaseSequenceRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            SequenceGeneratorService sequenceGenerator
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.databaseSequenceRepository = databaseSequenceRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sequenceGenerator = sequenceGenerator;
    }

    public void  generateData() {
        System.out.println("start generation");
        DatabaseSequence roleSequence = databaseSequenceRepository.findById("roles_sequence").orElse(null);
        if(roleSequence == null) {
            roleSequence = DatabaseSequence.builder()
                    .id("roles_sequence")
                    .seq(1)
                    .build();
            databaseSequenceRepository.save(roleSequence);
        }
        DatabaseSequence userSequence = databaseSequenceRepository.findById("users_sequence").orElse(null);
        if(userSequence == null) {
            userSequence = DatabaseSequence.builder()
                    .id("users_sequence")
                    .seq(1)
                    .build();
            databaseSequenceRepository.save(userSequence);
        }

        AppRole role = roleRepository.findByRole("ADMIN");

        if(role == null) {
            role = AppRole.builder()
                    .role("ADMIN")
                    .roleId(sequenceGenerator.generateSequence(AppRole.SEQUENCE_NAME))
                    .build();
            role = roleRepository.save(role);
        }

        AppUser user = userRepository.findByEmail("admin@admin.com");

        if(user == null) {
            user = AppUser.builder()
                    .userId(sequenceGenerator.generateSequence(AppUser.SEQUENCE_NAME))
                    .email("admin@admin.com")
                    .creationTime(new Date())
                    .role(role)
                    .username("admin")
                    .password(bCryptPasswordEncoder.encode("ssrsAdmin"))
                    .activated(true)
                    .build();
            userRepository.save(user);
        }

    }
}
