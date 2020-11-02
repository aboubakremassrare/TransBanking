package ma.project.metier;

import org.springframework.data.domain.Page;
import ma.project.entities.Compte;
import ma.project.entities.Operation;


public interface IBankMetier {

	
    public Compte consulterCompter(String codeCpte);
    public void verser(String codeCpte , double montant);
    public void retirer(String codeCpte , double montant);
    public void virement(String codeCpte1 , String codeCpte2 , double montant);
    public Page<Operation> listOperation(String codeCpte , int page,int size);
  
}
