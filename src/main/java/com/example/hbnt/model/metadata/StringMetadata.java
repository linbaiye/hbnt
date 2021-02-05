package com.example.hbnt.model.metadata;

import com.example.hbnt.model.metadatatemplate.Type;

/**
 * @author tao.lin
 * @date 2021/2/5
 */
public class StringMetadata extends Metadata<String> {

    public StringMetadata(String name, String value) {
        super(name, value, Type.STRING);
    }
}
