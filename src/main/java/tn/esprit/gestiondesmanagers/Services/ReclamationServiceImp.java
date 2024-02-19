package tn.esprit.gestiondesmanagers.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondesmanagers.Emails.EmailUtil;
import tn.esprit.gestiondesmanagers.Emails.MailModel;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Reclamation;
import tn.esprit.gestiondesmanagers.entities.Reclamation_status;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.IGenericServiceImp;
import tn.esprit.gestiondesmanagers.repositories.ProjectRepository;
import tn.esprit.gestiondesmanagers.repositories.ReclamationRepository;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReclamationServiceImp  extends IGenericServiceImp<Reclamation,Integer> implements IReclamationService {
    private final ReclamationRepository reclamationRepository;
    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;
    private final String from = "mohamedsofien.kehia@esprit.tn";
    private final JavaMailSender javaMailSender;

    public void sendMail(User user, String message) throws Exception {
        MailModel mail = new MailModel();
        mail.setFrom("mohamedsofien.kehia@gmail.com");
        mail.setTo(user.getEmail());
        mail.setHTML(true);
        mail.setSubject("Reclamation");
        //mail.setHtmlContent(EmailUtil.htmlMailExemple());
        mail.setHtmlContent(message);
        sendMail(mail);
    }

    public void sendMail(MailModel mail) throws Exception {
        if (mail.isHTML()) {
            try {
                InternetAddress[] parsed;
                try {
                    parsed = InternetAddress.parse(mail.getTo());
                } catch (AddressException e) {
                    throw new AddressException("addresse invalid");
                }
                MimeMessage mailMessage = javaMailSender.createMimeMessage();
                mailMessage.setSubject("confimer votre compte", "UTF-8");
                MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");

                helper.setFrom(from);
                helper.setTo(parsed);
                helper.setSubject(mail.getSubject());
                helper.setText(mail.getHtmlContent(), true);
                javaMailSender.send(mailMessage);
            } catch (MessagingException ex) {
                throw new MessagingException("erreur lors de l'envoie du mail",ex);
            }
        } else {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(mail.getFrom());
            msg.setTo(mail.getTo());
            msg.setText(mail.getText());
            msg.setSubject(mail.getSubject());
            javaMailSender.send(msg);
        }
    }
    @Override
    public Reclamation addandaffectReclamationtouser(Reclamation reclamation, Integer user_id,Integer project_id) throws Exception{
        User user=userRepository.findById(user_id).orElse(null);
        Project project=projectRepository.findById(project_id).orElse(null);
        if(reclamation!=null&& user!=null && user.getRole().equals("Manager") && userRepository.userproject(project_id).contains(user) && project!=null){
            reclamation.setUser_reclamation(user);
            reclamation.setProjectreclamation(project);
            reclamationRepository.save(reclamation);
            this.sendMail(user,"you received a reclamation:\n"+reclamation.getDescription()+"\n");
        }
        return null;
    }

    @Scheduled(cron="* * 15 * * *")
    public void cleanup() {
        // récupérer toutes les reclamations avec le statut "Rejected"
        List<Reclamation> refusedReclamations = reclamationRepository.findBystatus(Reclamation_status.Rejected);
        // supprimer les reclamations
        reclamationRepository.deleteAll(refusedReclamations);
    }
}
