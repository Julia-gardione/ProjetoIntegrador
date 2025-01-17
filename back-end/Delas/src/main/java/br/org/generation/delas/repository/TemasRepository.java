package br.org.generation.delas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.generation.delas.model.Temas;

public interface TemasRepository extends JpaRepository<Temas, Integer> {
	
	public List<Temas> findAllByCategoriasContainingIgnoreCase(String categorias);

	public List<Temas> findAllByDescricaoContainingIgnoreCase(String descricao);

}
