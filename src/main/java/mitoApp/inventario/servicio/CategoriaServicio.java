package mitoApp.inventario.servicio;

import mitoApp.inventario.modelo.Categoria;
import mitoApp.inventario.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicio implements ICategoriaServicio{


    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Override
    public List<Categoria> listarCategoria(){
        return this.categoriaRepositorio.findAll();
    }

    @Override
    public Categoria buscarCategoriaPorId(Integer idCategoria){
        Categoria categoria = this.categoriaRepositorio.findById(idCategoria).orElse(null);
        return categoria;
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria){
        return this.categoriaRepositorio.save(categoria);
    }

    @Override
    public void eliminarCategoriaPorId(Integer idCategoria){
        this.categoriaRepositorio.deleteById(idCategoria);
    }
}
