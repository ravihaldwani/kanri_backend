package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.space.model.SpaceRequest;
import com.kanrisoft.kanri.user.UserModule;
import org.springframework.stereotype.Service;

@Service
class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository repository;
    private final UserModule userModule;

    SpaceServiceImpl(SpaceRepository repository, UserModule userModule) {
        this.repository = repository;
        this.userModule = userModule;
    }

    @Override
    public Space createSpace(SpaceRequest request) {
        var user = userModule.getCurrentUserId().get();
        var entity = SpaceEntity.of(request.domainName(), user);
        return repository.save(entity);
    }
}
