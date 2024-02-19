package tn.esprit.gestiondesmanagers.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gestiondesmanagers.Emails.EmailUtil;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.IGenericServiceImp;
import tn.esprit.gestiondesmanagers.repositories.DepartmentRepository;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;


import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImp extends IGenericServiceImp<User,Integer> implements IUserService  {

    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private EmailUtil emailUtil;

    private static final String UPLOAD_DIR = "C:\\Users\\ASUS\\IdeaProjects\\Gestiondesmanagers - Copie (security)\\src\\main\\resources\\static\\files";
    public String uploadImage(int userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        user.setImage(userId+fileName); // Stockez le chemin de l'image dans le champ "image" de l'utilisateur
        userRepository.save(user);

        // Enregistrez le fichier dans le répertoire spécifié (UPLOAD_DIR)
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(userId+fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return "/api/users/" + userId + "/image";

    }
    @Override
    public Resource downloadImage(String imageName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR ).resolve(imageName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        return resource;
    }

    @Override
    public User setUserImage(MultipartFile file, int iduser) throws IOException {
        User user =userRepository.findById(iduser).orElse(null);
        if(user!=null){
            user.setImage(uploadFileService.uploadFile(file));
            userRepository.save(user);
        }
        return user;
    }

    public Resource retriveFile(int iduser) throws MalformedURLException {
        User user =userRepository.findById(iduser).orElse(null);
        Path fileStorageLocation;

        if(user!=null){
            Path filePath = Paths.get(user.getImage()).normalize();
            System.out.println(user.getImage());
            System.out.println(filePath);

            Resource resource = new UrlResource(filePath.toUri());
            return resource;
        }
        return null;
    }

    @Override
    public User updateandaffectusertodept(User user, Integer dept_id) {
        Department dept=departmentRepository.findById(dept_id).orElse(null);
        if(user!=null && dept!=null){
            user.setDepartment(dept);
          return userRepository.save(user);
        }
        return null;
    }
    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String forgotpassword(String email) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(
                        ()->new RuntimeException("User not found with this email " +email));
        try{
            emailUtil.sendSetpasswordEmail(email);
        }catch (MessagingException e){
            throw new RuntimeException("unable to send set password email please try again");
        }
        return "Please check your email to set new password to your account";
    }

    @Override
    public String setPassword(String email, String newPassword) {
       String pass=passwordEncoder.encode(newPassword);
        User user=userRepository.findByEmail(email)
                .orElseThrow(
                        ()->new RuntimeException("User not found with this email " +email));
        user.setPassword(pass);
       userRepository.save(user);
       return "New password set successfully login with new password";
    }

}
