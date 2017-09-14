package com.formento.projections.example;

import com.formento.projections.config.SecurityUtils;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class ApplicationInit {

    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ApplicationInit(EmployeeRepository employeeRepository, ItemRepository itemRepository) {
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
    }

    @PostConstruct
    public void init() {

        employeeRepository.save(new Employee(null, "Bilbo", "Baggins", "thief"));
        employeeRepository.save(new Employee(null, "Frodo", "Baggins", "ring bearer"));
        employeeRepository.save(new Employee(null, "Gandalf", "the Wizard", "servant of the Secret Fire"));

        /**
         * Due to method-level protections on {@link example.company.ItemRepository}, the security context must be loaded
         * with an authentication token containing the necessary privileges.
         */
        SecurityUtils.runAs("system", "system", "ROLE_ADMIN");

        itemRepository.save(new Item(null, "Sting"));
        itemRepository.save(new Item(null, "the one ring"));

        SecurityContextHolder.clearContext();
    }

}
