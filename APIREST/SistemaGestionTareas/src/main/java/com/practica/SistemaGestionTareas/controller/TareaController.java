package com.practica.SistemaGestionTareas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practica.SistemaGestionTareas.model.Tarea;
import com.practica.SistemaGestionTareas.repository.TareaRepository;



@RestController
@CrossOrigin("*")
public class TareaController {

	@Autowired(required = true)
	private TareaRepository tareaRepository;

	
	@GetMapping("/tareas")
	public ResponseEntity<List<Tarea>> getAllTareas() {
		try {
			List<Tarea> tareas = new ArrayList<>();
			tareaRepository.findAll().forEach(tareas::add);
			
			if (tareas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(tareas, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@GetMapping("/tareas/{id}")
	public ResponseEntity<Tarea> getTareaById(@PathVariable Long id) {
		Optional<Tarea> tarea =	tareaRepository.findById(id);
		
		if (tarea.isPresent()) {
			return new ResponseEntity<>(tarea.get(),HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	
	@PostMapping("/tareas")
	public ResponseEntity<Tarea> addTarea(@RequestBody Tarea tareaBody) {
		Tarea tarea = new Tarea(); 
		tarea.setTitulo(tareaBody.getTitulo());
		tarea.setDescripcion(tareaBody.getDescripcion());
		tarea.setFechaVencimiento(tareaBody.getFechaVencimiento());
		tarea.setCompletada(false);
		tareaRepository.save(tarea);
		return new ResponseEntity<>(tarea, HttpStatus.OK);
	}
	
	
	@PutMapping("/tareas/{id}")
	public ResponseEntity<Tarea> updateTareaById(@PathVariable Long id, @RequestBody Tarea nuevaTarea) {
		Optional<Tarea> viejaTarea = tareaRepository.findById(id);
		
		if (viejaTarea.isPresent()) {
			Tarea modificarTarea = viejaTarea.get();
			modificarTarea.setTitulo(nuevaTarea.getTitulo());
			modificarTarea.setDescripcion(nuevaTarea.getDescripcion());
			modificarTarea.setFechaVencimiento(nuevaTarea.getFechaVencimiento());
			modificarTarea.setCompletada(nuevaTarea.getCompletada());
			
			Tarea tarea =  tareaRepository.save(modificarTarea);
			return new ResponseEntity<>(tarea, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@DeleteMapping("/tareas/{id}")
	public ResponseEntity<HttpStatus> deleteTareaById(@PathVariable Long id) {
		tareaRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	
	@PutMapping("/completada/{id}")
	public ResponseEntity<Tarea> completadaTarea(@PathVariable Long id) {
		Optional<Tarea> viejaTarea = tareaRepository.findById(id);
		
		if (viejaTarea.isPresent()) {
			Tarea modificarTarea = viejaTarea.get();
			modificarTarea.setCompletada(true);
			
			
			Tarea tarea =  tareaRepository.save(modificarTarea);
			return new ResponseEntity<>(tarea, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
