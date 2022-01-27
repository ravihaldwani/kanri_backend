package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.space.model.AddUserToSpaceRequest;
import com.kanrisoft.kanri.space.model.SpaceRequest;

public interface SpaceService {
    Space createSpace(SpaceRequest request);

    void addUserToSpace(SpaceId spaceId, AddUserToSpaceRequest request);
}
