package tn.esprit.gestiondesmanagers.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.IGenericService;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUserService extends IGenericService<User,Integer>  {
    public User setUserImage(MultipartFile file, int idStudent) throws IOException;
    public Resource retriveFile(int idStudent) throws MalformedURLException;

    public User updateandaffectusertodept(User user,Integer dept_id);

    public boolean emailExists(String email);

    public String forgotpassword(String email);

    public String setPassword(String email, String newPassword);

    String uploadImage(int userId, MultipartFile file) throws IOException;

    Resource downloadImage(String imageName) throws IOException;
}
