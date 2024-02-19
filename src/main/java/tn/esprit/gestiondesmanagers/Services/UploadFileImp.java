package tn.esprit.gestiondesmanagers.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class UploadFileImp implements IUploadFileService{
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String path_Directory="C:\\Users\\ASUS\\IdeaProjects\\Gestiondesmanagers - Copie (security)\\src\\main\\resources\\static\\files";
        Path path= Paths.get(path_Directory+ File.separator+file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return String.valueOf(path);
    }
}
