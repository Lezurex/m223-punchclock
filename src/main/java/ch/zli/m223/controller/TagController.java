package ch.zli.m223.controller;

import java.util.List;
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
import org.eclipse.microprofile.openapi.annotations.Operation;
import ch.zli.m223.model.Tag;
import ch.zli.m223.service.TagService;

@Path("/tags")
@org.eclipse.microprofile.openapi.annotations.tags.Tag(name = "Categories",
    description = "Handling of categories.")
public class TagController {

  @Inject
  TagService tagService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
  public List<Tag> index() {
    return tagService.findAll();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Creates a new tag.",
      description = "Creates a new tag and returns the newly added tag.")
  public Tag create(Tag tag) {
    return tagService.createTag(tag);
  }

  @DELETE
  @Operation(summary = "Deletes an tag.", description = "Deletes an tag irrecoverarble.")
  @Path("/{id}")
  public void delete(@PathParam("id") long id) {
    tagService.deleteTag(id);
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Updates an tag.", description = "Updates an tag.")
  @Path("/{id}")
  public Tag update(Tag tag, @PathParam("id") long id) {
    tag.setId(id);
    return tagService.updateTag(tag);
  }

}