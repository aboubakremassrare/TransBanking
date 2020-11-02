package ma.project;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class JeeSpringDataJpaThymleafBanqueApplication implements CommandLineRunner {


	public static void main(String[] args) {
		 ApplicationContext ctx= SpringApplication.run(JeeSpringDataJpaThymleafBanqueApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}

}
