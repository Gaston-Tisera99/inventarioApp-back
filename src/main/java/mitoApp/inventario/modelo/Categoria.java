package mitoApp.inventario.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private Date datacreated;
    private Integer status;

    //relación de uno a muchos con Producto
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Producto> productos;
}
