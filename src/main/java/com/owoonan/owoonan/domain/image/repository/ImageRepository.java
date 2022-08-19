package com.owoonan.owoonan.domain.image.repository;

import com.owoonan.owoonan.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
