package ma.project.web;


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
import ma.project.entities.Client;


@Controller
public class ClientController {

	@Autowired
	private ClientRepository clientrepository;
	
	@RequestMapping("/clients")
	public String listClients(Model model,
			@RequestParam(name="page",defaultValue = "0")int p,
			@RequestParam(name="size",defaultValue = "10")int s
			) {
		
		Page<Client> listClients=clientrepository.findAll(PageRequest.of(p,s));
		model.addAttribute("listClients", listClients);
		model.addAttribute("client", new Client());
      System.out.println("cc");
		int[] pages=new int[listClients.getTotalPages()];
		model.addAttribute("pages",pages);
		model.addAttribute("size",s);
		model.addAttribute("pageCourante",p);
		
		return "listClients";
	}
	
	@RequestMapping(value="/clients/delete",method=RequestMethod.GET)
	public String deleteCllient(Long id,int page,int size){
		
		clientrepository.deleteById(id);
		return "redirect:/clients?page="+page+"&size="+size;
		
	}
	
	
	@RequestMapping(value="/clients/edit",method=RequestMethod.GET)
	public String vueEdit(Model model,Long id){
		
		Optional<Client> cl=clientrepository.findById(id);
		  if(cl.isPresent()) {
		  Client client = cl.get();
		  model.addAttribute("client",client);
		  }
		return "vueEditClient";
		
	}
	
	@RequestMapping(value="/clients/save",method=RequestMethod.POST)
	public String saveClient(Model model,Client client){
		
		clientrepository.save(client); 
		return "redirect:/clients";

	}
	
	

}

