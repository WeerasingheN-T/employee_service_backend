package net.javaguides.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import net.javaguides.springboot.model.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String>{
    Optional<User> findByEmail(String email);
    Optional<User> findByEmpId(String empId);
}
