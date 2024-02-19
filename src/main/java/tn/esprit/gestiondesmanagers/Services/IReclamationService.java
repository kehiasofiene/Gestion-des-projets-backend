package tn.esprit.gestiondesmanagers.Services;

import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Reclamation;
import tn.esprit.gestiondesmanagers.generic.IGenericService;

public interface IReclamationService extends IGenericService<Reclamation,Integer> {
 public Reclamation addandaffectReclamationtouser(Reclamation reclamation,Integer user_id,Integer project_id) throws Exception;
}
