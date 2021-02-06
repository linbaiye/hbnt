package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.dataobject.PsProductMetadata;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
public enum Type {
    /**
     * String 元数据模版
     */
    STRING {
        @Override
        public AbstractMetadataTemplate<?> createTemplate(PsProductMetadata metadata) {
            return new StringMetadataTemplate();
        }
    },

    DECIMAL {
        @Override
        public AbstractMetadataTemplate<?> createTemplate(PsProductMetadata metadata) {
            return new DecimalMetadataTemplate();
        }
    },

    INTEGER {
        @Override
        public AbstractMetadataTemplate<?> createTemplate(PsProductMetadata metadata) {
            return new IntegerMetadataTemplate();
        }
    },

    BOOL {
        @Override
        public AbstractMetadataTemplate<?> createTemplate(PsProductMetadata metadata) {
            return new BoolMetadataTemplate();
        }
    },

    ENUM {
        @Override
        public AbstractMetadataTemplate<?> createTemplate(PsProductMetadata metadata) {
            return new EnumMetadataTemplate();
        }
    },

    JSON {
        @Override
        public AbstractMetadataTemplate<?> createTemplate(PsProductMetadata metadata) {
            return null;
        }
    },

    LIST {
        @Override
        public AbstractMetadataTemplate<?> createTemplate(PsProductMetadata metadata) {
            return null;
        }
    };

    public abstract AbstractMetadataTemplate<?> createTemplate(PsProductMetadata metadata);

}
