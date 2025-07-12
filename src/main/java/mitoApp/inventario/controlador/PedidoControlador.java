package mitoApp.inventario.controlador;

import mitoApp.inventario.dto.PedidoDTO;
import mitoApp.inventario.modelo.Pedido;
import mitoApp.inventario.servicio.PedidoServicio;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inventario-app/pedidos")
@CrossOrigin(value = "http://localhost:4200")

public class PedidoControlador {

    @Autowired private PedidoServicio pedidoServicio;
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido nuevoPedido = pedidoServicio.crearPedido(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

}
