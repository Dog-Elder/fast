package com.fast.core.common.validate;

import com.fast.core.common.validate.annotation.FileNotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class FileNotNullValidator implements ConstraintValidator<FileNotNull, Object> {
    @Override
    public void initialize(FileNotNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean b;

        //
        if (value == null) {
            b = false;
        } else {
            MultipartFile file = (MultipartFile) value;
            if (file.isEmpty() || file.getSize() == 0) {
                b = false;
            } else {
                b = true;
            }
        }

        return b;
    }
}
