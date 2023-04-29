package com.DesguaceExpress.main.functionalities;

import com.DesguaceExpress.main.exception.custom.InvalidExpressionException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * clase para validar expresiones regulares que llegan por http
 */
@NoArgsConstructor
@AllArgsConstructor
public class LexicalAnalyzer {

    private String regex;
    private Pattern pat;
    private Matcher math;

    /**
     * funcion para validar las expreciones regulares que se reciven desde autenticacion o registro
     * si la cadena es aceptada continuara, de ser negativa se crea una expepcion InvalidExpressionExcepction
     * y se envia al usuario el tipo que fallo
     * @param cadena la cadena que va verificar (name, phone, email...)
     * @param tipo el tipo de cadena que corresponde
     */
    public void validateRegularExpression(String cadena, String tipo,Boolean nullable) {

        switch (tipo) {
            case "id" -> regex = "^([0-9]{1,2000000})$";
            case "email" -> regex = "^([A-Za-z0-9]{1})([-_\\.A-Za-z0-9]{0,})([A-Za-z0-9]{1})@([A-Za-z0-9\\.]+)*([A-Za-z]{2,})$";
            case "licensePlate" -> regex = "^([A-Za-z0-9]{6})$";
            case "phone" -> regex = "^([0-9]{3,})$";
            case "make", "model", "type" -> regex = "^([A-Za-z0-9\\,\\' -]{4,30})$";
            case "parkingName", "state", "country", "ubication", "city" -> regex = "^([A-Za-z0-9\\,\\' -]{4,30})$";
            case "firstName", "lastName" -> regex = "^([A-Za-z]{4,30})$";
            case "date" -> regex = "^([0-9]{1,2})([-])([0-9]{1,2})([-])([0-9]{2,4})$";
            case "document" -> regex = "^([A-Za-z]{0,2})(-{0,1})([0-9]{4,30})$";
            case "year" -> regex = "^([0-9]{4})$";
            case "partiaLicensePlate" -> regex = "^([A-Za-z0-9]{1,6})$";
            default -> regex = null;
        }
        //si tipo no es valido continua
        if (regex != null) {
            pat = Pattern.compile(regex);
            math = pat.matcher(cadena);
            if (!math.find() && !nullable) {
                throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED, "el campo de " + tipo + " no es valido");
            }
        }
    }
}
