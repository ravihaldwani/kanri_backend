package com.kanrisoft.kanri.space;

import com.kanrisoft.kanri.space.model.SpaceDto;
import com.kanrisoft.kanri.space.model.SpaceRequest;
import com.kanrisoft.kanri.space.domain.SpaceService;
import com.kanrisoft.kanri.space.util.SpaceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/spaces")
public class SpaceController {

    private final SpaceService service;

    public SpaceController(SpaceService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<SpaceDto> createSpace(@RequestBody SpaceRequest request) {
        var space = service.createSpace(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SpaceUtils.mapToDto(space));
    }
}
