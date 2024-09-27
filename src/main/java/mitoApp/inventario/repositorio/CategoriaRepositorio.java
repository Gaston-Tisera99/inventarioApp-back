package mitoApp.inventario.repositorio;

import mitoApp.inventario.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {
}
