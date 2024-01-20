package org.devices.specifications.api.service.services.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class ApplicationServiceImpl {

    private String password;
    private String selfUrl;

    public boolean isPasswordCorrect(final String password) {
        if(password != null && this.password != null) {
            if(!password.trim().isEmpty() && !this.password.trim().isEmpty()) {
                return password.equals(this.password);
            }
        }
        return false;
    }
}
