package com.example.hbnt.model.metadata;

import com.example.hbnt.model.metadatatemplate.Type;

/**
 * @author tao.lin
 * @date 2021/2/5
 */
public class IntegerMetadata extends Metadata<Integer> {
    
    public IntegerMetadata(String name, Integer value) {
        super(name, value, Type.INTEGER);
    }
}
