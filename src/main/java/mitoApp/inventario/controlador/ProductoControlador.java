package mitoApp.inventario.controlador;

import mitoApp.inventario.excepcion.RecursoNoEncontradoExcepcion;
import mitoApp.inventario.modelo.Categoria;
import mitoApp.inventario.modelo.Producto;
import mitoApp.inventario.repositorio.CategoriaRepositorio;
import mitoApp.inventario.servicio.CategoriaServicio;
import mitoApp.inventario.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//localhost:8080/inventario-app
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    //inyectamos el servicio
    @Autowired private ProductoServicio productoServicio;
    @Autowired private CategoriaRepositorio categoriaRepositorio;

    //http://localhost:8080/inventario-app/productos

    //obtener todos los productos
    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        List<Producto> productos = this.productoServicio.listarProductos();
        logger.info("Productos obtenidos");
        productos.forEach((producto-> logger.info(producto.toString())));
        return productos;
    }

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Map<String, Object> requestData) {
        try {
            // Convierte `categoriaId` a `Integer`
            Integer categoriaId = Integer.parseInt(requestData.get("categoriaId").toString());

            // Recuperar la categoría de la base de datos
            Categoria categoria = categoriaRepositorio.findById(categoriaId)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));

            Producto producto = new Producto();
            producto.setCodigo((String) requestData.get("codigo"));
            producto.setDescripcion((String) requestData.get("descripcion"));

            // Convertir el precio de String a Double
            Double precio = Double.valueOf(requestData.get("precio").toString());
            producto.setPrecio(precio);

            // Convertir el stock de String a Integer
            Integer stock = Integer.valueOf(requestData.get("stock").toString());
            producto.setStock(stock);

            // Asigna la categoría
            producto.setCategoria(categoria);

            // Guarda el producto
            return this.productoServicio.guardarProducto(producto);

        } catch (NumberFormatException e) {
            throw new RuntimeException("Error en la conversión de datos: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al agregar el producto: " + e.getMessage());
        }
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
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id, @RequestBody Map<String, Object> productoRecibido) {
        Producto productoExistente = this.productoServicio.buscarProductoPorId(id);

        if (productoExistente == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el producto con ID: " + id);
        }

        String descripcion = (String) productoRecibido.get("descripcion");
        String codigo = (String) productoRecibido.get("codigo");
        Double precio = Double.valueOf(productoRecibido.get("precio").toString());
        Integer stock = Integer.valueOf(productoRecibido.get("stock").toString());

        // Actualizar campos
        productoExistente.setPrecio(precio);
        productoExistente.setDescripcion(descripcion);
        productoExistente.setStock(stock);
        productoExistente.setCodigo(codigo);

        // Recuperar la categoría
        Object categoriaIdObj = productoRecibido.get("categoria_id");
        final Integer categoriaId;  // Declarar como final

        if (categoriaIdObj instanceof String) {
            categoriaId = Integer.parseInt((String) categoriaIdObj);  // Si es String, lo convertimos a Integer
        } else if (categoriaIdObj instanceof Integer) {
            categoriaId = (Integer) categoriaIdObj;  // Si ya es Integer, lo usamos directamente
        } else {
            throw new RuntimeException("Tipo de categoria_id inesperado: " + categoriaIdObj.getClass().getName());
        }

        Categoria categoria = categoriaRepositorio.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));
        productoExistente.setCategoria(categoria);

        Producto productoActualizado = this.productoServicio.guardarProducto(productoExistente);
        return ResponseEntity.ok(productoActualizado);
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
