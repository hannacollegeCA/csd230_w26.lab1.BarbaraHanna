package csd230.lab1.controllers;


import csd230.lab1.entities.DiscMagEntity;
import csd230.lab1.repositories.DiscMagEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DiscMag REST API",
        description = "JSON API for managing disc magazines")
@RestController
@RequestMapping("/api/rest/discmags")
public class DiscMagRestController {

    private final DiscMagEntityRepository repository;

    public DiscMagRestController(DiscMagEntityRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Get all disc magazines")
    @ApiResponse(responseCode = "200",
            description = "Successfully retrieved disc magazines")
    @GetMapping
    public List<DiscMagEntity> all() {
        return repository.findAll();
    }

    @Operation(summary = "Get one disc magazine")
    @ApiResponse(responseCode = "200",
            description = "Disc magazine found")
    @ApiResponse(responseCode = "404",
            description = "Disc magazine not found")
    @GetMapping("/{id}")
    public DiscMagEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DiscMagNotFoundException(id));
    }

    @Operation(summary = "Create disc magazine")
    @PostMapping
    public DiscMagEntity create(@RequestBody DiscMagEntity discMag) {
        return repository.save(discMag);
    }

    @Operation(summary = "Update disc magazine")
    @PutMapping("/{id}")
    public DiscMagEntity update(
            @RequestBody DiscMagEntity newDiscMag,
            @PathVariable Long id) {

        return repository.findById(id)
                .map(discMag -> {
                    discMag.setTitle(newDiscMag.getTitle());
                    discMag.setAuthor(newDiscMag.getAuthor());
                    discMag.setPrice(newDiscMag.getPrice());
                    discMag.setCopies(newDiscMag.getCopies());
                    discMag.setPublisher(newDiscMag.getPublisher());
                    discMag.setIssueNumber(newDiscMag.getIssueNumber());
                    discMag.setHasDisc(newDiscMag.isHasDisc());

                    return repository.save(discMag);
                })
                .orElseThrow(() -> new DiscMagNotFoundException(id));
    }

    @Operation(summary = "Delete disc magazine")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}