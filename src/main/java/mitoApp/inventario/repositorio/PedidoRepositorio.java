package mitoApp.inventario.repositorio;

import mitoApp.inventario.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> {
}
