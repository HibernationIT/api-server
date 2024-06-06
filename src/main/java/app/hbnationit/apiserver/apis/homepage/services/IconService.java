package app.hbnationit.apiserver.apis.homepage.services;

import app.hbnationit.apiserver.apis.homepage.models.HpIcon;
import app.hbnationit.apiserver.apis.homepage.models.dto.AddHpIconRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyHpIconRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.HpIconResponse;
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

    public Page<HpIcon> findIcons(Pageable pageable, String name) {
        Page<HpIcon> icons;
        if (name == null) { icons = repository.findAll(pageable); }
        else { icons = repository.findByNameLike(pageable, name); }

        return icons;
    }

    public Page<HpIconResponse> findIconsVo(Pageable pageable, String name) {
        Page<HpIcon> icons;
        if (name == null) { icons = repository.findByViewIsTrue(pageable); }
        else { icons = repository.findByViewIsTrueAndNameLike(pageable, name); }

        return icons
                .map(dao -> HpIconResponse.builder()
                        .name(dao.getName())
                        .svg(dao.getSvg())
                        .createdAt(dao.getCreatedAt())
                        .build()
                );
    }

    @Transactional
    public HpIcon addIcon(AddHpIconRequest dto) {
        if (repository.findById(dto.getName()).isPresent()) {
            throw new EntityExistsException("Icon is already exist");
        }

        HpIcon dao = HpIcon.builder()
                .name(dto.getName())
                .svg(dto.getSvg())
                .view(dto.getView())
                .build();
        return repository.save(dao);
    }

    @Transactional
    public HpIcon modifyIcon(String name, ModifyHpIconRequest dto) {
        HpIcon dao = repository.findById(name).orElseThrow(() ->
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
