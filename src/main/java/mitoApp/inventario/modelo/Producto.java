package mitoApp.inventario.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    public Boolean validate(){ // <-- create validation method for prevent from null issue
        return this.idProducto == null;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    @JsonManagedReference
    private Categoria categoria;
    private String codigo;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
