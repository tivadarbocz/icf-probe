package com.icf.backend;

import com.vaadin.flow.templatemodel.ModelEncoder;

import java.util.Optional;

public class LongToStringEncoder implements ModelEncoder<Long, String> {

    @Override
    public String encode(Long modelValue) {
        return Optional.ofNullable(modelValue).map(Object::toString)
                .orElse(null);
    }

    @Override
    public Long decode(String presentationValue) {
        return Optional.ofNullable(presentationValue).map(Long::valueOf)
                .orElse(null);
    }

}
