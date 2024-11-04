package dat.security.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author laith kaseb
 **/


@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;

}
