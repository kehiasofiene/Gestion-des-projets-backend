package tn.esprit.gestiondesmanagers.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gestiondesmanagers.Services.IUploadFileService;
import tn.esprit.gestiondesmanagers.Services.IUserService;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.GenericController;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/User")
public class UserController extends GenericController<User,Integer> {
    private final IUserService userService;

    private final UserRepository userRepository;

    private  final IUploadFileService uploadFileService;

    @PostMapping("/setimage/{iduser}")
    public User setUserImageByUser(@RequestParam MultipartFile file, @PathVariable int iduser) throws IOException {
        return  userService.setUserImage(file,iduser);
    }

    @GetMapping("/image/{iduser}")
    public ResponseEntity<Resource> retriveFile(@PathVariable int iduser, HttpServletRequest request) throws IOException {

        String contentType = null;
        Resource resource =userService.retriveFile(iduser) ;
        //System.out.println(request);
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        //System.out.println(contentType);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/projectusers/{project_id}")
    public List<User> userproject(@PathVariable Integer project_id){
        return userRepository.userproject(project_id);
    }

    @PutMapping("/forget_password")
    public ResponseEntity<String> forgotpassword(@RequestParam String email){
        return new ResponseEntity<>(userService.forgotpassword(email), HttpStatus.OK);
    }

    @GetMapping("/userphone")
    private String getUserPhoneNumberFromProject(@RequestBody Project project){
        return userRepository.getUserPhoneNumberFromProject(project);
    }

    @PutMapping("/{dept_id}")
    public User updateandaffectusertodept(@RequestBody User user,@PathVariable Integer dept_id){
        return userService.updateandaffectusertodept(user, dept_id);
    }

    @PostMapping("/uploadImage/{userId}")
    public void uploadImage(@PathVariable int userId, @RequestParam("file") MultipartFile file) throws IOException {
        userService.uploadImage(userId, file);
    }
/*
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageName) throws IOException {
        Resource resource = userService.downloadImage(imageName);
        return ResponseEntity.ok().body(resource);
    }*/

    @PutMapping("/set_password")
    public ResponseEntity<String> setPassword(@RequestParam String email,@RequestHeader String newPassword){
        return new ResponseEntity<>(userService.setPassword(email,newPassword),HttpStatus.OK);
    }
}
