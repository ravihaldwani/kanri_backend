package com.kanrisoft.kanri.space;

import com.kanrisoft.kanri.space.domain.SpaceId;
import com.kanrisoft.kanri.space.domain.SpaceService;
import com.kanrisoft.kanri.space.model.AddUserToSpaceRequest;
import com.kanrisoft.kanri.space.model.SpaceDto;
import com.kanrisoft.kanri.space.model.SpaceRequest;
import com.kanrisoft.kanri.space.util.SpaceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/spaces")
public class SpaceController {

    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping
    ResponseEntity<SpaceDto> createSpace(@RequestBody SpaceRequest request) {
        var space = spaceService.createSpace(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SpaceUtils.mapToDto(space));
    }

    @PostMapping("/{spaceId}/add_user")
    ResponseEntity<Object> addUserToSpace(@PathVariable Long spaceId, @RequestBody AddUserToSpaceRequest request) {
        spaceService.addUserToSpace(SpaceId.of(spaceId), request);
        return ResponseEntity.ok(null);
    }
}
