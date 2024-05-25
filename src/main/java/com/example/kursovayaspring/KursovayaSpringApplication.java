package com.example.kursovayaspring;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication


public class KursovayaSpringApplication {

	public static void main(String[] args) throws JSONException {
		SpringApplication.run(KursovayaSpringApplication.class, args);
		String str = "8 1\n" +
				"4\n" +
				"0 0 2   2  3\n" +
				"3 2 1   4\n" +
				"4 3 1   5\n" +
				"2 4 1   6\n" +
				"2 4 1   7\n" +
				"1 3 1   8\n" +
				"4 2 1   8\n" +
				"0 0 0";
		var str1 =ParserJSONFromTXT.parse(str).toString(4);
		System.out.println(str1);



	}


}
