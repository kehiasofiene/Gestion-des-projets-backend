package tn.esprit.gestiondesmanagers.Services;

import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.IGenericService;

public interface IDepartementService extends IGenericService<Department,Integer>  {
    Department addandaffectDepartmenttoUser(Integer dept_id, Integer user_id);
}
