package tn.esprit.gestiondesmanagers.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondesmanagers.Services.IReclamationService;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Reclamation;
import tn.esprit.gestiondesmanagers.generic.GenericController;
import tn.esprit.gestiondesmanagers.repositories.ReclamationRepository;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Reclamation")
public class ReclamationController  extends GenericController<Reclamation,Integer> {
    private final IReclamationService reclamationService;

    private final ReclamationRepository reclamationRepository;

@PostMapping("addandaffect/{user_id}/{project_id}")
    public Reclamation addandaffectReclamationtouser(@RequestBody Reclamation reclamation,@PathVariable Integer user_id,@PathVariable Integer project_id) throws Exception {
        return reclamationService.addandaffectReclamationtouser(reclamation, user_id,project_id);
    }

    @GetMapping("reclamationuser/{user_id}")
    public List<Reclamation> reclamationsofuser(@PathVariable Integer user_id){
      return reclamationRepository.reclamationsofuser(user_id);
    }

    @GetMapping("reclamationList/{project_id}")
    public List<Reclamation> getReclamationsByProjectreclamation(@PathVariable Integer project_id){
    return reclamationRepository.getReclamationsByProjectreclamation(project_id);
    }
}
