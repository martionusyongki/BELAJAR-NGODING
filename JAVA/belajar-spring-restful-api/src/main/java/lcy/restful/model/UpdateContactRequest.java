package lcy.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateContactRequest {

    @JsonIgnore
    private String id;

    @Size(max = 100)
    private String firstname;

    @Size(max = 100)
    private String lastname;

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String phone;
}
