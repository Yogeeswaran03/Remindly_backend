package com.example.hivemindbackend.repository;

import com.example.hivemindbackend.model.Template;
import com.example.hivemindbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findByUser(User user);


    List<Template> findByUserAndIsDefaultTemplate(User user, boolean isDefaultTemplate);
}
