package Services;


import Controllers.UsersJpaController;
import Models.Users;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class UsersService {
       
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("match4padel-apirestPU");
        
        UsersJpaController dao = new UsersJpaController(emf);
        List<Users> usersList = dao.findUsersEntities();
        emf.close();
        
        Response response;
        
        response = Response
                .status(Response.Status.OK)
                .entity(usersList)
                .build();
        
        return response;
    }
}
