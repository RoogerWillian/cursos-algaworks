package com.algaworks.brewer.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Teste {
	
	private static final List<Integer> INTEGERS = new ArrayList<>(); 

	
	public static void main(String[] args) {
		Random random = new Random();
		for(int i =0 ; i < 100; i++){			
			INTEGERS.add(random.nextInt(101));
		}
		
		INTEGERS.sort((num1, num2) -> num1.compareTo(num2));
		System.out.println(INTEGERS);
	}

}
