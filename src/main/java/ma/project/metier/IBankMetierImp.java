package ma.project.metier;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.project.dao.CompteRepository;
import ma.project.dao.OperationRepository;
import ma.project.entities.Compte;
import ma.project.entities.CompteCourant;
import ma.project.entities.Operation;
import ma.project.entities.Retrait;
import ma.project.entities.Versement;

@Service
@Transactional

public class IBankMetierImp implements IBankMetier {
	@Autowired
	private CompteRepository compterepository;
	@Autowired
	private OperationRepository operationRepository;

	@Override
	public Compte consulterCompter(String codeCpte) {
		Compte cp=compterepository.findById(codeCpte).orElse(null);
		
		if(cp==null) throw new RuntimeException("Compte introuvable");
		return cp;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp=consulterCompter(codeCpte);
		operationRepository.save(new Versement(new Date(),montant,cp));
		cp.setSolde(cp.getSolde()+montant);
		compterepository.save(cp);
		
		
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		
		Compte cp=consulterCompter(codeCpte);
		Double facilitesCaisse=0.0;
		if(cp instanceof CompteCourant)
			facilitesCaisse=((CompteCourant) cp).getDecouvert();
		if(cp.getSolde()+facilitesCaisse<montant)
			throw new RuntimeException("Solde insuffisant");
			
		
		operationRepository.save(new Retrait(new Date(),montant,cp));
		cp.setSolde(cp.getSolde()-montant);
		compterepository.save(cp);
		
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double montant) {
		
		if(codeCpte1.equals(codeCpte2))
			throw new RuntimeException("Transaction invalide");
		retirer(codeCpte1, montant);
		verser(codeCpte2, montant);
		
	}

	@Override
	public Page<Operation> listOperation(String codeCpte,int page, int size) {
		
		return operationRepository.listOperatiion(codeCpte,PageRequest.of(page, size));
	}



}
