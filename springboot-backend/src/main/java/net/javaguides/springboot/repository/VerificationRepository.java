package net.javaguides.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import net.javaguides.springboot.model.Verification;

public interface VerificationRepository extends MongoRepository<Verification,String> {

    Verification findByVerificationCode(String verificationCode);
    
}
