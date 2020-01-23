package br.com.virtualab.newproject.mapper.autoridade;

import br.com.virtualab.newproject.model.autoridade.Autoridade;
import br.com.virtualab.newproject.viewmodel.autoridade.AutoridadeVM;
import java.util.List;
import java.util.stream.Collectors;

public final class AutoridadeMapper {

    public static Autoridade fromVM(AutoridadeVM autoridadeVM) {
        final Autoridade autoridade = new Autoridade();
        autoridade.setNome(autoridadeVM.getNome());
        autoridade.setDescricao(autoridadeVM.getDescricao());
        return autoridade;
    }

    public static List<Autoridade> fromVM(List<AutoridadeVM> autoridadeVMs) {
        return autoridadeVMs.stream().map(AutoridadeMapper::fromVM).collect(Collectors.toList());
    }

    public static AutoridadeVM toVM(Autoridade autoridade) {
        final AutoridadeVM autoridadeVM = new AutoridadeVM();
        autoridadeVM.setNome(autoridade.getNome());
        autoridadeVM.setDescricao(autoridade.getDescricao());
        return autoridadeVM;
    }

    public static List<AutoridadeVM> toVM(List<Autoridade> autoridades) {
        return autoridades.stream().map(AutoridadeMapper::toVM).collect(Collectors.toList());
    }

}
