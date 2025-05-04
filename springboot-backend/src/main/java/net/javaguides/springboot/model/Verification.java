package net.javaguides.springboot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document(collection = "verification")
public class Verification {
    @Id
    private String id;
    private String verificationCode;
    private boolean verificationStatus;
    private String email;

    public Verification() {}

    public Verification(String verification, boolean verificationStatus, String email) {
        this.verificationCode = verification;
        this.verificationStatus = verificationStatus;
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public boolean getVerificationStatus() {
        return verificationStatus;
    }

    public String getEmail() {  
        return email;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setVerificationStatus(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public void setEmail(String email) {  
        this.email = email;
    }
}
