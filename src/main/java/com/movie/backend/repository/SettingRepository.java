package com.movie.backend.repository;

import com.movie.backend.entity.Setting;
import com.movie.backend.entity.TypeSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {
    @Query("SELECT s FROM Setting s WHERE s.type = :type")
    public List<Setting> listByType(@Param("type") TypeSetting type);

    @Query("SELECT s.value FROM Setting s WHERE s.key = :key")
    public String getByKey(@Param("key") String key);
}
