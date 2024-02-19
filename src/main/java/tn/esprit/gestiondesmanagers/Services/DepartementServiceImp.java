package tn.esprit.gestiondesmanagers.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.IGenericServiceImp;
import tn.esprit.gestiondesmanagers.repositories.DepartmentRepository;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class DepartementServiceImp extends IGenericServiceImp<Department,Integer> implements IDepartementService{

    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;


    @Override
    public Department addandaffectDepartmenttoUser(Integer dept_id, Integer user_id) {
        Department department=departmentRepository.findById(dept_id).orElse(null);
        User user=userRepository.findById(user_id).orElse(null);
        if(department!=null && user!=null){
            user.setDepartment(department);
            userRepository.save(user);
        }
        return null;
    }
}

