package com.example.sosbackend.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = MultipartFileListValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMultipartFileList {
  String message()

  default "Invalid file(s) uploaded.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String[] allowed();

  boolean optional() default false;
}
