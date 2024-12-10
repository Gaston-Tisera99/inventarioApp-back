package mitoApp.inventario.controlador;

import mitoApp.inventario.excepcion.RecursoNoEncontradoExcepcion;
import mitoApp.inventario.modelo.Categoria;
import mitoApp.inventario.servicio.CategoriaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class CategoriaControlador {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaControlador.class);

    @Autowired private CategoriaServicio categoriaServicio;

    @GetMapping("/categorias")
    public List<Categoria> obtenerCategoria(){ 
        List<Categoria> categorias = this.categoriaServicio.listarCategoria();
        logger.info("Categoria obtenidos");
        categorias.forEach((categoria -> logger.info(categoria.toString())));
        return categorias;
    }

    @PostMapping(value = "/categorias", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria categoria) {
        logger.info("Request recibida: " + categoria);

        // Validación de los campos de la categoría
        if (categoria.getNombre().isEmpty() || categoria.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("El nombre/descripcion de la categoria no puede estar vacio");
        }

        // Establecer la fecha de creación de la categoría
        categoria.setDatacreated(new Date());

        // Guardar la nueva categoría
        Categoria nuevaCategoria = this.categoriaServicio.guardarCategoria(categoria);

        // Devolver la respuesta con la nueva categoría
        return ResponseEntity.ok(nuevaCategoria);
    }



    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Map<String,Boolean>>
    eliminarCategoria(@PathVariable int id){
        Categoria categoria = categoriaServicio.buscarCategoriaPorId(id);
        if(categoria == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        this.categoriaServicio.eliminarCategoriaPorId(categoria.getId());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminando", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}
