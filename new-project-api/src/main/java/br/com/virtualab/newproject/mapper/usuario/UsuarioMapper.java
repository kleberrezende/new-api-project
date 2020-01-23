package br.com.virtualab.newproject.mapper.usuario;

import br.com.virtualab.newproject.mapper.autoridade.AutoridadeMapper;
import br.com.virtualab.newproject.model.usuario.Usuario;
import br.com.virtualab.newproject.viewmodel.usuario.UsuarioVM;
import java.util.List;
import java.util.stream.Collectors;

public final class UsuarioMapper {

    public static Usuario fromVM(UsuarioVM usuarioVM) {
        final Usuario usuario = new Usuario();
        usuario.setId(usuarioVM.getId());
        usuario.setLogin(usuarioVM.getLogin());
        usuario.setNome(usuarioVM.getNome());
        usuario.setSobreNome(usuarioVM.getSobreNome());
        usuario.setEmail(usuarioVM.getEmail());
        usuario.setAtivo(usuarioVM.isAtivo());
        usuario.setAtivado(usuarioVM.isAtivado());
        usuario.setDataCriacao(usuarioVM.getDataCriacao());
        usuario.setDataUltimoLogin(usuarioVM.getDataUltimoLogin());
        usuario.setDataReset(usuarioVM.getDataReset());
        usuario.setAutoridades(AutoridadeMapper.fromVM(usuarioVM.getAutoridades()));
        return usuario;
    }

    public static List<Usuario> fromVM(List<UsuarioVM> usuarioVMList) {
        return usuarioVMList.stream().map(UsuarioMapper::fromVM).collect(Collectors.toList());
    }

    public static UsuarioVM toVM(Usuario usuario) {
        final UsuarioVM usuarioVM = new UsuarioVM();
        usuarioVM.setId(usuario.getId());
        usuarioVM.setLogin(usuario.getLogin());
        usuarioVM.setNome(usuario.getNome());
        usuarioVM.setSobreNome(usuario.getSobreNome());
        usuarioVM.setEmail(usuario.getEmail());
        usuarioVM.setAtivo(usuario.isAtivo());
        usuarioVM.setAtivado(usuario.isAtivado());
        usuarioVM.setDataCriacao(usuario.getDataCriacao());
        usuarioVM.setDataUltimoLogin(usuario.getDataUltimoLogin());
        usuarioVM.setDataReset(usuario.getDataReset());
        usuarioVM.setAutoridades(AutoridadeMapper.toVM(usuario.getAutoridades()));
        usuarioVM.setBloqueadoTentativaLogin(usuario.isBloqueadoTentativaLogin());
        return usuarioVM;
    }

    public static List<UsuarioVM> toVM(List<Usuario> usuarioList) {
        return usuarioList.stream().map(UsuarioMapper::toVM).collect(Collectors.toList());
    }

}
