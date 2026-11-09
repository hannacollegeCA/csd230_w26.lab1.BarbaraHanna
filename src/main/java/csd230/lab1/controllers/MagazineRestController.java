package csd230.lab1.controllers;


import csd230.lab1.entities.MagazineEntity;
import csd230.lab1.repositories.MagazineEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Magazine REST API",
        description = "JSON API for managing magazines")
@RestController
@RequestMapping("/api/rest/magazines")
public class MagazineRestController {

    private final MagazineEntityRepository repository;

    public MagazineRestController(MagazineEntityRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Get all magazines")
    @ApiResponse(responseCode = "200",
            description = "Successfully retrieved magazines")
    @GetMapping
    public List<MagazineEntity> all() {
        return repository.findAll();
    }

    @Operation(summary = "Get one magazine")
    @ApiResponse(responseCode = "200",
            description = "Magazine found")
    @ApiResponse(responseCode = "404",
            description = "Magazine not found")
    @GetMapping("/{id}")
    public MagazineEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MagazineNotFoundException(id));
    }

    @Operation(summary = "Create magazine")
    @PostMapping
    public MagazineEntity create(@RequestBody MagazineEntity magazine) {
        return repository.save(magazine);
    }

    @Operation(summary = "Update magazine")
    @PutMapping("/{id}")
    public MagazineEntity update(
            @RequestBody MagazineEntity newMagazine,
            @PathVariable Long id) {

        return repository.findById(id)
                .map(magazine -> {
                    magazine.setTitle(newMagazine.getTitle());
                    magazine.setAuthor(newMagazine.getAuthor());
                    magazine.setPrice(newMagazine.getPrice());
                    magazine.setCopies(newMagazine.getCopies());
                    magazine.setPublisher(newMagazine.getPublisher());
                    magazine.setIssueNumber(newMagazine.getIssueNumber());

                    return repository.save(magazine);
                })
                .orElseThrow(() -> new MagazineNotFoundException(id));
    }

    @Operation(summary = "Delete magazine")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}