package cl.duoc.ms_jardin_ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // <-- ESTO ENCIENDE FEIGN
public class MsJardinVentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsJardinVentasApplication.class, args);
	}
}