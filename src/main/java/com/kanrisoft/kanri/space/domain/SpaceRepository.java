package com.kanrisoft.kanri.space.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends CrudRepository<SpaceEntity, Long> {
}
