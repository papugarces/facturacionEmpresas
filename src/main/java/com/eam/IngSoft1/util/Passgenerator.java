package com.eam.IngSoft1.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Passgenerator {

	/*
	  public static void main(String[] args) { BCryptPasswordEncoder
	  bCryptPasswordEncoder = new BCryptPasswordEncoder(4); // El String que
	  //mandamos al metodo encode es el password que queremos // encriptar.
	  System.out.println(bCryptPasswordEncoder.encode("123")); }
	 */
	 
	
	public String enciptarPassword(String cadenaPass) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		// El String que mandamos al metodo encode es el password que queremos
		// encriptar.
		return bCryptPasswordEncoder.encode(cadenaPass);
	}
}
