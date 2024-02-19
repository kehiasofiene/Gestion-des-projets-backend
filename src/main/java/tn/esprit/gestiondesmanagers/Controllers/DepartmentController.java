package tn.esprit.gestiondesmanagers.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondesmanagers.Services.IDepartementService;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.GenericController;

@RestController
@AllArgsConstructor
@RequestMapping("/Department")
public class DepartmentController extends GenericController<Department,Integer> {
 private final IDepartementService departementService;

 @PostMapping("addandaffect/{dept_id}/{user_id}")
 public Department addandaffectDepartmenttoUser(@PathVariable Integer dept_id, @PathVariable Integer user_id){
  return departementService.addandaffectDepartmenttoUser(dept_id, user_id);
 }
}
