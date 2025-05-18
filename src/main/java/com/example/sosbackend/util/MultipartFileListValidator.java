package com.example.sosbackend.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipartFileListValidator implements ConstraintValidator<ValidMultipartFileList, List<MultipartFile>> {
  private static final long MAX_SIZE = 10 * 1024 * 1024; // 10 mb

  private String[] allowedTypes;

  private boolean isOptional;

  private String message;

  @Override
  public void initialize(ValidMultipartFileList constraintAnnotation) {
    this.allowedTypes = constraintAnnotation.allowed();
    this.isOptional = constraintAnnotation.optional();
    this.message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {

    // if (!(files instanceof List<MultipartFile>)) {
    // buildViolation(context, "No Files provided");
    // return false;
    // }

    // If field is optional and no files are provided, it's valid
    if (isOptional && (files == null || files.isEmpty())) {
      return true;
    }

    if (files == null || files.isEmpty()) {
      buildViolation(context, "At least one file must be provided");
      return false;
    }

    for (MultipartFile file : files) {

      // check if file is empty
      if (file.isEmpty()) {
        buildViolation(context, "File “" + file.getOriginalFilename() + "” is empty.");
        return false;
      }

      // validate file size
      if (file.getSize() > MAX_SIZE) {
        buildViolation(context,
            "File “" + file.getOriginalFilename() + "” exceeds max size of 10 MB.");
        return false;
      }

      String type = file.getContentType();

      if (!(Arrays.asList(allowedTypes).contains(type))) {
        buildViolation(context, "File “" + file.getOriginalFilename() +
            "” has invalid type “" + type +
            "”." + this.message);
        return false;
      }

    }

    return true;

  }

  private void buildViolation(ConstraintValidatorContext context, String msg) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(msg)
        .addConstraintViolation();
  }
}
