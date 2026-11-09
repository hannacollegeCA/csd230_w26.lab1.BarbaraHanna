package csd230.lab1.controllers;


import csd230.lab1.entities.TicketEntity;
import csd230.lab1.repositories.TicketEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Ticket REST API",
        description = "JSON API for managing tickets"
)
@RestController
@RequestMapping("/api/rest/tickets")
public class TicketRestController {

    private final TicketEntityRepository repository;

    public TicketRestController(TicketEntityRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Get all tickets")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved tickets"
    )
    @GetMapping
    public List<TicketEntity> all() {
        return repository.findAll();
    }

    @Operation(summary = "Get one ticket")
    @ApiResponse(responseCode = "200", description = "Ticket found")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    @GetMapping("/{id}")
    public TicketEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    @Operation(summary = "Create ticket")
    @PostMapping
    public TicketEntity create(@RequestBody TicketEntity ticket) {
        return repository.save(ticket);
    }

    @Operation(summary = "Update ticket")
    @PutMapping("/{id}")
    public TicketEntity update(
            @RequestBody TicketEntity newTicket,
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ticket -> {

                    ticket.setDescription(
                            newTicket.getDescription()
                    );

                    return repository.save(ticket);
                })
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    @Operation(summary = "Delete ticket")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}