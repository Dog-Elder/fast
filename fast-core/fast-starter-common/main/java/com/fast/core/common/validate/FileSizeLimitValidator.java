package com.fast.core.common.validate;

import com.fast.core.common.validate.annotation.FileSizeLimit;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class FileSizeLimitValidator implements ConstraintValidator<FileSizeLimit, Object> {
    private String max;

    @Override
    public void initialize(FileSizeLimit constraintAnnotation) {
        max = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean b = false;

        if (value == null) {
            return true;
        }
        String desc = max.toUpperCase();
        Integer number = Integer.parseInt(desc.replace("M", "").replace("K", ""));

        Integer realSize = desc.contains("M") ? number * 1024 * 1024 : number * 1024;

        MultipartFile file = (MultipartFile) value;
        if (file.getSize() > realSize) {
            b = false;
        } else {
            b = true;
        }

        return b;
    }
}
