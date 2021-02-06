package com.example.hbnt.model.metadata;

import com.example.hbnt.model.metadatatemplate.Type;

/**
 * @author tao.lin
 * @date 2021/2/6
 */
public class BoolMetadata extends Metadata<String> {

    public BoolMetadata(String name, String value) {
        super(name, value, Type.BOOL);
    }
}
