package com.practica.SistemaGestionTareas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practica.SistemaGestionTareas.model.Tarea;



@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long>{

}
