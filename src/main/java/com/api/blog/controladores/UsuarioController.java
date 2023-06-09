package com.api.blog.controladores;
import com.api.blog.entidades.Rol;
import com.api.blog.entidades.Usuario;
import com.api.blog.excepciones.NotFoundException;
import com.api.blog.servicios.RolService;
import com.api.blog.servicios.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
     private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private RolService rolService;
    
    @GetMapping("/")
    public ResponseEntity<List<Usuario>> obtenerUsuarios(){
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }
    
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long usuarioId) throws NotFoundException{
        Usuario usuario = usuarioService.obtenerUsuario(usuarioId);
        if(usuario != null){
             return ResponseEntity.ok(usuario);
        }
       return  ResponseEntity.notFound().build();
    }
    
    @PostMapping("/")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario){
            Rol rol = rolService.obtenerRolPorNombre("usuario");
            usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
            if(rol != null){
                usuario.setRol(rol);
                usuarioService.guardarUsuario(usuario);
                return ResponseEntity.ok(usuario);
            }else{
                Rol rolACrear = new Rol();
                rolACrear.setNombre("usuario");
                rolACrear.setRolId(2L);
                rolService.guardarRol(rolACrear);
                usuario.setRol(rolACrear);
                usuarioService.guardarUsuario(usuario);
                return ResponseEntity.ok(usuario);
            } 
    }
    
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long usuarioId) throws NotFoundException{
        Usuario usuario = usuarioService.obtenerUsuario(usuarioId);
        if(usuario != null){
            usuarioService.eliminarUsuario(usuarioId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
        
    }
    
    
    
    
}
