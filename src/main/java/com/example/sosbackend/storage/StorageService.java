package com.example.sosbackend.storage;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import io.github.cdimascio.dotenv.Dotenv;

public class StorageService {

  private final Cloudinary cloudinary;

  private final Dotenv dotenv;

  public StorageService() {

    this.dotenv = Dotenv.load();

    this.cloudinary = new Cloudinary(this.dotenv.get("CLOUDINARY_URL"));
  }

  public Map uploadFile(File file) {

    try {

      // Upload the image
      Map params = ObjectUtils.asMap(
          "use_filename", true,
          "unique_filename", true,
          "overwrite", true);

      return this.cloudinary.uploader().upload(file,
          params);

    } catch (IOException e) {

      e.printStackTrace();

      return Collections.emptyMap();
    }
  }

  public ApiResponse deleteFile(List<String> fileIds, String resourceType) throws Exception {

    Map options = ObjectUtils.asMap("resource_type", resourceType);

    return this.cloudinary.api().deleteResources(fileIds, options);
  }
}
