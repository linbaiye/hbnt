package com.example.hbnt.model;


/**
 * @author tao.lin
 * @date 2021/1/31
 */
public interface MetadataTemplateRepository  {

    MetadataTemplate<?> findById(Long id);
}
