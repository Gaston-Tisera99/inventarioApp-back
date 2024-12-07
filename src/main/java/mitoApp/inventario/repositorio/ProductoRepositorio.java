package mitoApp.inventario.repositorio;

import mitoApp.inventario.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
    public interface ProductoRepository extends JpaRepository<Producto, Integer> {
        @Query("SELECT p FROM Producto p JOIN FETCH p.categoria")
        List<Producto> listarProductos();
    }

}
