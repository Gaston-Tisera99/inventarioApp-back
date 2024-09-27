package mitoApp.inventario.servicio;

import mitoApp.inventario.modelo.Categoria;

import java.util.List;

public interface ICategoriaServicio {
    public List<Categoria> listarCategoria();

    public Categoria buscarCategoriaPorId(Integer idCategoria);

    public Categoria guardarCategoria(Categoria categoria);

    public void eliminarCategoriaPorId(Integer idCategoria);
}
