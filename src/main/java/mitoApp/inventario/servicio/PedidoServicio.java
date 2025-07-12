package mitoApp.inventario.servicio;

import lombok.RequiredArgsConstructor;
import mitoApp.inventario.dto.DetallePedidoDTO;
import mitoApp.inventario.dto.PedidoDTO;
import mitoApp.inventario.excepcion.RecursoNoEncontradoExcepcion;
import mitoApp.inventario.modelo.Cliente;
import mitoApp.inventario.modelo.DetallePedido;
import mitoApp.inventario.modelo.Producto;
import mitoApp.inventario.repositorio.ClienteRepositorio;
import mitoApp.inventario.repositorio.PedidoRepositorio;
import mitoApp.inventario.repositorio.ProductoRepositorio;
import org.springframework.stereotype.Service;

import mitoApp.inventario.modelo.Pedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServicio {

    private final PedidoRepositorio pedidoRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final ProductoRepositorio productoRepositorio;

    public Pedido crearPedido(PedidoDTO dto){

        Cliente cliente = clienteRepositorio.findById(dto.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaCreacion(new Date());

        List<DetallePedido> detalles  = new ArrayList<>();
        double total = 0.0;

        for(DetallePedidoDTO detalleDTO: dto.getDetalles()){
            Producto producto = productoRepositorio.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Producto no encontrado"));

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setSubtotal(producto.getPrecio() * detalleDTO.getCantidad());
            detalle.setPedido(pedido);

            total += detalle.getSubtotal();
            detalles.add(detalle);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        return pedidoRepositorio.save(pedido);
    }
}
