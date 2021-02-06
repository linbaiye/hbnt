package com.example.hbnt.dao;

import com.example.hbnt.dataobject.PsProductMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * @author tao.lin
 * @date 2021/2/3
 */
public interface PsProductMetadataDao extends JpaRepository<PsProductMetadata, Long> {

    /**
     */
    Set<PsProductMetadata> findByKeyIn(Set<String> keys);

}
