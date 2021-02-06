package com.example.hbnt.model.metadata;

import com.example.hbnt.model.metadatatemplate.Type;

import java.util.LinkedList;
import java.util.List;

/**
 * @author tao.lin
 * @date 2021/2/5
 */
public class ListMetadata extends Metadata<List<List<Metadata<?>>>> {

    public ListMetadata(String name) {
        super(name, new LinkedList<>(), Type.LIST);
    }

    public void add(List<Metadata<?>> metadata) {
        value.add(metadata);
    }
}
