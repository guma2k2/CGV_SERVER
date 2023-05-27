package com.movie.backend.api;

import com.movie.backend.entity.Setting;
import com.movie.backend.entity.TypeSetting;
import com.movie.backend.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/setting")
@CrossOrigin("*")
public class SettingRestController {

    @Autowired
    private SettingService settingService;

    @GetMapping("/mailTemplate")
    public List<Setting> getMailTemplate() {
        return settingService.listByType(TypeSetting.MAIL_TEMPLATES);
    }

    @GetMapping
    public List<Setting> getAll() {
        return settingService.listAll();
    }
    @PostMapping("/save")
    public String saveAll(@RequestBody List<Setting> settings ) {
        settingService.saveAll(settings);
        return "success";
    }

    @GetMapping("/mailServer")
    public List<Setting> getMailServer() {
        return settingService.listByType(TypeSetting.MAIL_SERVER);
    }


}
