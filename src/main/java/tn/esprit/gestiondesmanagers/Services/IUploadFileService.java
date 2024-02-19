package tn.esprit.gestiondesmanagers.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadFileService {
    public String uploadFile(MultipartFile file) throws IOException;
}
