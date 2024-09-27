package mitoApp.inventario.repositorio;

import mitoApp.inventario.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
}
