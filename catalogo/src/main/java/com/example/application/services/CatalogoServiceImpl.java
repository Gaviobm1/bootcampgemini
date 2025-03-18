package com.example.application.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.contracts.CatalogoService;
import com.example.application.models.NovedadesDTO;
import com.example.domains.contracts.services.ActorsService;
import com.example.domains.contracts.services.CategorysService;
import com.example.domains.contracts.services.FilmsService;
import com.example.domains.contracts.services.LanguagesService;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.domains.entities.dtos.ActorDTO;


@Service
public class CatalogoServiceImpl implements CatalogoService {
	@Autowired
	private FilmsService filmSrv;
	@Autowired
	private ActorsService artorSrv;
	@Autowired
	private CategorysService categorySrv;
	@Autowired
	private LanguagesService languageSrv;

	@Override
	public NovedadesDTO novedades(Timestamp fecha) {
		if(fecha == null)
			fecha = Timestamp.from(Instant.now().minusSeconds(36000));
		return new NovedadesDTO(
				filmSrv.novedades(fecha).stream().map(item -> new FilmShortDTO(item.getFilmId(), item.getTitle())).toList(), 
				artorSrv.novedades(fecha).stream().map(item -> ActorDTO.from(item)).toList(), 
				categorySrv.novedades(fecha), 
				languageSrv.novedades(fecha)
				);
	}

}