package mitoApp.inventario.controlador;

import mitoApp.inventario.excepcion.RecursoNoEncontradoExcepcion;
import mitoApp.inventario.modelo.Producto;
import mitoApp.inventario.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//localhost:8080/inventario-app
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    //inyectamos el servicio
    @Autowired private ProductoServicio productoServicio;

    //http://localhost:8080/inventario-app/productos

    //obtener todos los productos
    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        List<Producto> productos = this.productoServicio.listarProductos();
        logger.info("Productos obtenidos");
        productos.forEach((producto-> logger.info(producto.toString())));
        return productos;
    }

    //agregar un nuevo producto

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto){
        logger.info("Producto a agregar: " + producto);
        return this.productoServicio.guardarProducto(producto);
    }

    //buscar producto por id
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id){
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if(producto != null)
        return ResponseEntity.ok(producto);
        else
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);

    }

    //actualizar un producto

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable int id,
            @RequestBody Producto productoRecibido){
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if(producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        producto.setDescripcion(productoRecibido.getDescripcion());
        producto.setPrecio(productoRecibido.getPrecio());
        producto.setStock(productoRecibido.getStock());
        this.productoServicio.guardarProducto(producto);
        return ResponseEntity.ok(producto);
    }

    //eliminar un producto

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarProducto(@PathVariable int id){
        Producto producto = productoServicio.buscarProductoPorId(id);
        if(producto == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        this.productoServicio.eliminarProductoPorId(producto.getIdProducto());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}
