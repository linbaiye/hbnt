package com.example.hbnt.model.metadata;

import com.example.hbnt.model.metadatatemplate.Type;

import java.util.List;

/**
 * @author tao.lin
 * @date 2021/2/5
 */
public class ListMetadata extends Metadata<List<Metadata<?>>> {

    public ListMetadata(String name, List<Metadata<?>> value) {
        super(name, value, Type.LIST);
    }

    public void add(Metadata<?> metadata) {
        value.add(metadata);
    }
}
