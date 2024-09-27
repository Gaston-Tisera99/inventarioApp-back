package mitoApp.inventario.controlador;

import mitoApp.inventario.excepcion.RecursoNoEncontradoExcepcion;
import mitoApp.inventario.modelo.Cliente;
import mitoApp.inventario.modelo.Producto;
import mitoApp.inventario.servicio.ClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class ClienteControlador {

    private static final Logger logger = LoggerFactory.getLogger(ClienteControlador.class);

    @Autowired private ClienteServicio clienteServicio;


    //obtener todos los clientes
    @GetMapping("/clientes")
    public List<Cliente> obtenerClientes(){
        List<Cliente> clientes = this.clienteServicio.listarCliente();
        logger.info("Cliente obtenidos");
        clientes.forEach((cliente-> logger.info(cliente.toString())));
        return clientes;
    }

    //agregar un nuevo cliente
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente){
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        logger.info("Cliente a agregar: " + cliente);
        Cliente nuevoCliente = this.clienteServicio.guardarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }


    //buscar cliente por id

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable int id) {
        Cliente cliente = this.clienteServicio.buscarClientePorId(id);
        if(cliente != null){
            return ResponseEntity.ok(cliente);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        }
    }

    //actualizar un cliente
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable int id,
            @RequestBody Cliente clienteRecibido){
        Cliente cliente = this.clienteServicio.buscarClientePorId(id);
        if(cliente == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " +id);
        cliente.setNombre(clienteRecibido.getNombre());
        cliente.setApellido(clienteRecibido.getApellido());
        cliente.setEmail(clienteRecibido.getEmail());
        cliente.setCuit(clienteRecibido.getCuit());
        cliente.setTelefono(clienteRecibido.getTelefono());
        cliente.setDni(clienteRecibido.getDni());
        this.clienteServicio.guardarCliente(cliente);
        return ResponseEntity.ok(cliente);
    }

    //eliminar un cliente

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarCliente(@PathVariable int id){
        Cliente cliente = clienteServicio.buscarClientePorId(id);
        if(cliente == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        this.clienteServicio.eliminarClientePorId(cliente.getId());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminando", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
