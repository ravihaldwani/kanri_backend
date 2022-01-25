package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.space.model.AddUserRequest;
import com.kanrisoft.kanri.space.model.SpaceRequest;

public interface SpaceService {
    Space createSpace(SpaceRequest request);

    void addUserToSpace(SpaceId spaceId, AddUserRequest request);
}
