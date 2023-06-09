
package com.api.blog.servicios;

import com.api.blog.entidades.Comentario;
import com.api.blog.entidades.ComentarioDTO;
import com.api.blog.entidades.Publicacion;
import com.api.blog.entidades.Usuario;
import com.api.blog.excepciones.NotFoundException;
import java.util.List;


public interface ComentarioService {
    
    public List<Comentario> obtenerComentariosDelUsuario(Usuario usuario);
    public List<Comentario> obtenerComentariosDePublicacion(Publicacion publicacion);
    public List<ComentarioDTO> obtenerComentariosDePublicacionConAutor(Publicacion publicacion)  throws NotFoundException;
    public Comentario obtenerComentario(Long comentarioId) throws NotFoundException;
    public Comentario guardarComentario(Comentario comentario,Publicacion publicacion);
    public Comentario actualizarComentario(Comentario comentario) throws NotFoundException;
    public Comentario eliminarComentario(Long comentarioId) throws NotFoundException;
    
}
