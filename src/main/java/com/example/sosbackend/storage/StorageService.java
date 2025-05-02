package com.example.sosbackend.storage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

@Service
public class StorageService {

  public final String PICTURE_FOLDER_NAME = "pictures";

  public final String VIDEO_FOLDER_NAME = "videos";

  private final Cloudinary cloudinary;

  private final Environment env;

  public StorageService(Environment env) {

    this.env = env;

    this.cloudinary = new Cloudinary(this.env.getProperty("cloudinary.url"));
  }

  public Map uploadFile(MultipartFile file, String folderName) {

    try {
      // Upload the image
      Map params = ObjectUtils.asMap(
          "use_filename", true,
          "unique_filename", true,
          "overwrite", true,
          "folder", folderName,
          "resource_type", "auto");

      return this.cloudinary.uploader().upload(file.getBytes(),
          params);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public ApiResponse deleteFiles(List<String> filesKey, String resourceType) throws Exception {

    Map options = ObjectUtils.asMap("resource_type", resourceType);

    return this.cloudinary.api().deleteResources(filesKey, options);
  }
}
