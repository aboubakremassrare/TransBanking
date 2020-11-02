package ma.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.project.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
