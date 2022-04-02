package com.bootcamp.deliver.Model;

import java.util.HashMap;
import java.util.Map;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductByIdConverter implements Converter<String, Product> {
        private Map<String, Product> productMap = new HashMap<>();

        @Override
        public Product convert(String id) {
                return productMap.get(id);
        }

}
