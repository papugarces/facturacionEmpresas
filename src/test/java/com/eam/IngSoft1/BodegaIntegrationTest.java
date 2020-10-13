package com.eam.IngSoft1;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eam.IngSoft1.domain.Bodega;
import com.eam.IngSoft1.IRepository.IBodegaRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class BodegaIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	IBodegaRepository repository;
	
}