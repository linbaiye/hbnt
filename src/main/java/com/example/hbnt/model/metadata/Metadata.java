package com.example.hbnt.model.metadata;

import com.example.hbnt.model.metadatatemplate.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author tao.lin
 * @date 2021/2/5
 */
@SuperBuilder
@AllArgsConstructor
@Getter
public abstract class Metadata<T> {
    protected String name;
    protected T value;
    protected Type type;
}
