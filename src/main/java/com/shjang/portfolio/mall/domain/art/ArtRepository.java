package com.shjang.portfolio.mall.domain.art;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtRepository extends JpaRepository<Art,Long> {

    @Query("SELECT a FROM Art a ORDER BY a.id DESC")
    List<Art> findAllDesc();



}
