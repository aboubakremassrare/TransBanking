package ma.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.project.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte,String > {

}
