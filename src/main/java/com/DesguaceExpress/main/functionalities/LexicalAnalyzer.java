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
    public void validateRegularExpression(String cadena, String tipo) {

        switch (tipo) {
            case "id" -> regex = "^([0-9]{1,2000000})$";
            case "email" -> regex = "^([A-Za-z0-9]{1})([-_\\.A-Za-z0-9]{0,})([A-Za-z0-9]{1})@([A-Za-z0-9\\.]+)*([A-Za-z]{2,})$";
            case "licensePlate" -> regex = "^([A-Za-z0-9]{6})$";
            case "phone" -> regex = "^([0-9]{3,})$";
            case "parkingName" -> regex = "^([A-Za-z0-9\\,\\' -]{4,30})$";
            case "firstName", "lastName", "location" -> regex = "^([A-Za-z]{4,30})$";
            case "date" -> regex = "^([0-9]{1,2})([-])([0-9]{1,2})([-])([0-9]{2,4})$";
            case "document" -> regex = "^([0-9]{4,30})$";
            case "year" -> regex = "^([0-9]{4})$";
            default -> regex = null;
        }
        //si tipo no es valido continua
        if (regex != null && cadena!=null) {
            pat = Pattern.compile(regex);
            math = pat.matcher(cadena);
            if (!math.find()) {
                throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED, "el campo de " + tipo + " no es valido");
            }
        }
    }
}
