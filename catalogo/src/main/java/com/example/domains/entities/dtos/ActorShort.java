package com.example.domains.entities.dtos;

import org.springframework.beans.factory.annotation.Value;

public interface ActorShort {
	int getActorID();
	@Value("#{target.firstName + ' ' + target.lastName}")
	String getNombre();
}
