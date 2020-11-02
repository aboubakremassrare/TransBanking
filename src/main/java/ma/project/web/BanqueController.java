package ma.project.web;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ma.project.dao.ClientRepository;
import ma.project.dao.CompteRepository;
import ma.project.entities.Client;
import ma.project.entities.Compte;
import ma.project.entities.CompteCourant;
import ma.project.entities.CompteEpargne;
import ma.project.entities.Operation;
import ma.project.metier.IBankMetier;

@Controller
public class BanqueController {
	@Autowired
	private IBankMetier banqueMetier;
	@Autowired
	private ClientRepository clientrepository;
	@Autowired
	private CompteRepository compteRepository;
	
	@RequestMapping("/")
	public String home() {
		 return "redirect:/comptes";
	}
	
	@RequestMapping("/comptes")
	public String index() {
		return "comptes";
	}
	
	@RequestMapping("/consultercompte")
	public String consulter(Model model,
			@RequestParam(name="page",defaultValue = "0")int p,
			@RequestParam(name="size",defaultValue = "10")int s,
			String codeCpte) {
		    model.addAttribute("codeCpte", codeCpte);

		try {
			Compte cp=banqueMetier.consulterCompter(codeCpte);
			Page<Operation> pageOperations=banqueMetier.listOperation(codeCpte, p,s);
			model.addAttribute("listOperation", pageOperations.getContent());
			model.addAttribute("compte",cp);
			
			int[] pages=new int[pageOperations.getTotalPages()];
			model.addAttribute("pages",pages);
			model.addAttribute("size",s);
			model.addAttribute("pageCourante",p);
			
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
	
		
		return "comptes";
	}
	
	@RequestMapping(value="/saveOperation",method=RequestMethod.POST)
	public String saveOperation(Model model,String typeOperation,String codeCompte,Double montant,String codeCompte2) {
				  try {
						switch(typeOperation) {
						case "VERS":
							banqueMetier.verser(codeCompte, montant);
							break;
					    case "RETR":
					    	banqueMetier.retirer(codeCompte, montant);
							break;
					    case "VIR":
							banqueMetier.virement(codeCompte, codeCompte2, montant);
							break;
						}
					
				  } catch (Exception e) {
				        model.addAttribute("error", e);
				        return "redirect:/consultercompte?codeCpte="+codeCompte+"&error="+e.getMessage();
				  }
				  return "redirect:/consultercompte?codeCpte="+codeCompte;
	}
	
	
	@RequestMapping("/listofcompte")
	public String listClients(Model model,
			@RequestParam(name="page",defaultValue = "0")int p,
			@RequestParam(name="size",defaultValue = "10")int s
			) {
		
		Page<Compte> listComptes=compteRepository.findAll(PageRequest.of(p,s));
		model.addAttribute("listComptes", listComptes);
		
		List<Client> listClients=clientrepository.findAll();
		model.addAttribute("clients",listClients);
		model.addAttribute("compteCourant", new CompteCourant());
		model.addAttribute("compteEpargne", new CompteEpargne());

		
		int[] pages=new int[listComptes.getTotalPages()];
		model.addAttribute("pages",pages);
		model.addAttribute("size",s);
		model.addAttribute("pageCourante",p);
		
		return "listComptes";
	}
	
	@RequestMapping(value="/compteCourant/save",method=RequestMethod.POST)
	public String compteCourantSave(Model model,@RequestParam Long clientCode,CompteCourant compte) {
			
	      Optional<Client> c1=clientrepository.findById(clientCode);
		  if(c1.isPresent()) {
		  Client client = c1.get();
		  compte.setClient(client);
		  compte.setDateCreation(new Date());
		  compteRepository.save(compte);
		  }
		  		
		return "redirect:/listofcompte";
	}

	
	@RequestMapping(value="/compteEpargne/save",method=RequestMethod.POST)
	public String compteEpargneSave(Model model,@RequestParam Long clientCode,CompteEpargne compte) {
		
	      Optional<Client> c1=clientrepository.findById(clientCode);
		  if(c1.isPresent()) {
		  Client client = c1.get();
		  compte.setClient(client);
		  compte.setDateCreation(new Date());
		  compteRepository.save(compte);
		  }
		  		
		return "redirect:/listofcompte";
	}

}
