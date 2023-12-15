package com.centroinformacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Opcion;
import com.centroinformacion.entity.Rol;
import com.centroinformacion.entity.RolHasOpcion;
import com.centroinformacion.entity.RolHasOpcionPK;
import com.centroinformacion.repository.RolHasOpcionRepository;
import com.centroinformacion.repository.RolRepository;

@Service
public class RolServiceImp implements RolService {
	
	@Autowired
	private RolRepository rolrepository;
	

	@Override
	public List<Rol> listaRol() {
		return rolrepository.findAll();
	}





}
