package app.hbnationit.apiserver.apis.homepage.services;

import app.hbnationit.apiserver.apis.homepage.models.Icon;
import app.hbnationit.apiserver.apis.homepage.models.dto.AddIconRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyIconRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.IconResponse;
import app.hbnationit.apiserver.apis.homepage.repositories.IconRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IconService {
    private final IconRepository repository;

    public IconService(IconRepository repository) {
        this.repository = repository;
    }

    public Page<Icon> findIcons(Pageable pageable, String name) {
        Page<Icon> icons;
        if (name == null) { icons = repository.findAll(pageable); }
        else { icons = repository.findByNameLike(name); }

        return icons;
    }

    public Page<IconResponse> findIconsVo(Pageable pageable, String name) {
        return (Page<IconResponse>) findIcons(pageable, name)
                .filter(Icon::getView)
                .map(dao -> IconResponse.builder()
                        .name(dao.getName())
                        .svg(dao.getSvg())
                        .build()
                );
    }

    @Transactional
    public Icon addIcon(AddIconRequest dto) {
        if (repository.findById(dto.getName()).isPresent()) {
            throw new EntityExistsException("Icon is already exist");
        }

        Icon dao = Icon.builder()
                .name(dto.getName())
                .svg(dto.getSvg())
                .view(dto.getView())
                .build();
        return repository.save(dao);
    }

    @Transactional
    public Icon modifyIcon(String name, ModifyIconRequest dto) {
        Icon dao = repository.findById(name).orElseThrow(() ->
                new EntityNotFoundException("Not found Icon"));
        dao.setName(dto.getName());
        dao.setSvg(dto.getSvg());
        dao.setView(dto.getView());

        return dao;
    }

    @Transactional
    public void removeIcon(String name) {
        repository.deleteById(name);
    }
}
