package mitoApp.inventario.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
@Data

public class PedidoDTO {
    private Integer clienteId;
    private List<DetallePedidoDTO> detalles;
}
