package com.agriconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgriconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgriconnectApplication.class, args);
// 		System.out.println(
//     java.util.Base64.getEncoder().encodeToString(
//         io.jsonwebtoken.security.Keys.secretKeyFor(
//             io.jsonwebtoken.SignatureAlgorithm.HS256
//         ).getEncoded()
//     )
// );
	}

}
