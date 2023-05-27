package com.movie.backend.service;

import com.movie.backend.entity.Setting;
import com.movie.backend.entity.TypeSetting;
import com.movie.backend.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public List<Setting> listByType(TypeSetting type){
        return settingRepository.listByType(type);
    }

    public List<Setting> saveAll(Iterable<Setting> list){
        return settingRepository.saveAll(list);
    }

    public List<Setting> listAll() {
        return settingRepository.findAll();
    }
    public int getPort(){
        return Integer.parseInt(settingRepository.getByKey("MAIL_PORT"));
    }
    public String getHost(){
        return settingRepository.getByKey("MAIL_HOST");
    }
    public String getUsername(){
        return settingRepository.getByKey("MAIL_USERNAME");
    }
    public String getPassword(){
        return settingRepository.getByKey("MAIL_PASSWORD");
    }
    public String getSmtpAuth(){
        return settingRepository.getByKey("SMTP_AUTH");
    }
    public String getSmtpSecured(){
        return settingRepository.getByKey("SMTP_SECURED");
    }
    public String getFromAddress(){
        return settingRepository.getByKey("MAIL_FROM");
    }
    public String getSenderName(){
        return settingRepository.getByKey("MAIL_SENDER_NAME");
    }
    public String getCustomerVerifySubject(){
        return settingRepository.getByKey("CUSTOMER_VERIFY_SUBJECT");
    }
    public String getCustomerVerifyContent(){
        return settingRepository.getByKey("CUSTOMER_VERIFY_CONTENT");
    }

}
