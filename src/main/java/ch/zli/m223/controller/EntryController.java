package ch.zli.m223.controller;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;
    @Inject
    @RequestScoped
    JsonWebToken jwt;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user"})
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
    public List<Entry> index() {
        return entryService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user"})
    @Operation(summary = "Creates a new entry.",
            description = "Creates a new entry and returns the newly added entry.")
    public Entry create(Entry entry) {
        return entryService.createEntry(entry);
    }

    @DELETE
    @RolesAllowed({"user"})
    @Operation(summary = "Deletes an entry.", description = "Deletes an entry irrecoverarble.")
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        entryService.deleteEntry(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user"})
    @Operation(summary = "Updates an entry.", description = "Updates an entry.")
    @Path("/{id}")
    public Entry update(Entry entry, @PathParam("id") long id) {
        entry.setId(id);
        return entryService.updateEntry(entry);
    }
}
