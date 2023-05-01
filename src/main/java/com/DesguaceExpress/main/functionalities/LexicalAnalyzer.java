package com.DesguaceExpress.main.functionalities;

import com.DesguaceExpress.main.exception.custom.InvalidExpressionException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * clase para validar expresiones regulares que llegan por http, tiene una funcion sobre cargada con diferentes
 * tipos de cadenas para analizar, si hay discrepancias con la cadena genera un throw, si no hay problemas
 * continua con la ejecucion, no se devuelve ningun valor
 */
@NoArgsConstructor
@AllArgsConstructor
public class LexicalAnalyzer {

    private String regex;
    private Pattern pat;
    private Matcher math;
    private String format;

    /**
     * recibe la cadena a nalizar, el tipo y si puede ser nulo, verifica si la cadena es null, si esta permitido
     * continua, caso contrario genera un throw informando que la cadena no puede ser null
     * @param cadena String
     * @param tipo String
     * @param nullable Boolean
     */
    public void validateRegularExpression(String cadena, String tipo, Boolean nullable) {
        if (!nullable && cadena == null) {
            throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED, "error, el campo de " + tipo + " no puede ser nulo");
        }
        RegularExpression(String.valueOf(cadena) , tipo);
    }
    /**
     * Funcion sobrecargada
     * recibe la cadena a nalizar, el tipo y si puede ser nulo, verifica si la cadena es null, si esta permitido
     * continua, caso contrario genera un throw informando que la cadena no puede ser null
     * @param cadena Long
     * @param tipo String
     * @param nullable Boolean
     */
    public void validateRegularExpression(Long cadena, String tipo, Boolean nullable) {
        if (!nullable && cadena == null) {
            throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED, "error, el campo de " + tipo + " no puede ser nulo");
        }
        RegularExpression(String.valueOf(cadena) , tipo);
    }
    /**
     * Funcion sobrecargada
     * recibe la cadena a nalizar, el tipo y si puede ser nulo, verifica si la cadena es null, si esta permitido
     * continua, caso contrario genera un throw informando que la cadena no puede ser null
     * @param cadena Integer
     * @param tipo String
     * @param nullable Boolean
     */
    public void validateRegularExpression(Integer cadena, String tipo, Boolean nullable) {
        if (!nullable && cadena == null) {
            throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED, "error, el campo de " + tipo + " no puede ser nulo");
        }
        RegularExpression(String.valueOf(cadena) , tipo);
    }
    /**
     * Funcion sobrecargada
     * recibe la cadena a nalizar, el tipo y si puede ser nulo, verifica si la cadena es null, si esta permitido
     * continua, caso contrario genera un throw informando que la cadena no puede ser null
     * @param cadena Float
     * @param tipo String
     * @param nullable Boolean
     */
    public void validateRegularExpression(Float cadena, String tipo, Boolean nullable) {
        if (!nullable && cadena == null) {
            throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED, "error, el campo de " + tipo + " no puede ser nulo");
        }
        RegularExpression(String.valueOf(cadena) , tipo);
    }

    /**
     * funcion interna para validar las expreciones regulares que se reciven desde autenticacion o registro
     * si la cadena es aceptada continuara, de ser negativa se crea una expepcion InvalidExpressionExcepction
     * y se envia al usuario el tipo que fallo
     * @param cadena la cadena que va verificar (name, phone, email...)
     * @param tipo el tipo de cadena que corresponde
     */
    private void RegularExpression(String cadena, String tipo){
        switch (tipo) {
            case "id", "parkingId", "locationId", "memebersId" -> regex = "^([0-9]{1,100000000})$";
            case "currentCapacity", "maxCapacity" -> regex = "^([0-9]{1,1000})$";
            case "costHour" -> regex = "^([0-9]{1,100000000})([.]{0,1})([0-9]{0,100})$";
            case "email" -> regex = "^([A-Za-z0-9]{1})([-_\\.A-Za-z0-9]{0,})([A-Za-z0-9]{1})@([A-Za-z0-9 \\.]+)*([A-Za-z]{2,})$";
            case "licensePlate" -> regex = "^([A-Za-z0-9]{6})$";
            case "phone" -> regex = "^([0-9]{3,})$";
            case "make", "model", "type" -> regex = "^([A-Za-z0-9 \\,\\'\\.-]{1,30})$";
            case "parkingName", "state", "country", "ubication", "city" -> regex = "^([A-Za-z0-9 \\,\\'-]{4,30})$";
            case "firstName", "lastName" -> regex = "^([A-Za-z]{4,30})$";
            case "date" -> regex = "^([0-9]{2})([-])([0-9]{2})([-])([0-9]{4})$";
            case "document" -> regex = "^([A-Za-z]{0,3})(-)([0-9]{4,30})$";
            case "year" -> regex = "^([0-9]{4})$";
            case "partiaLicensePlate" -> regex = "^([A-Za-z0-9]{1,6})$";
            default -> regex = null;
        }

        if(tipo.equalsIgnoreCase("type")){
            if(!(cadena.equalsIgnoreCase("carro")||cadena.equalsIgnoreCase("moto"))){
                throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED, "error, el campo de " + tipo + " "+validFormat(tipo));
            }
        }
        //si tipo no es valido continua
        if (regex != null && cadena!=null) {
            pat = Pattern.compile(regex);
            math = pat.matcher(cadena);
            if (!math.find()) {
                throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED, "error, el campo de " + tipo + " "+validFormat(tipo));
            }
        }
    }

    /**
     * funcion interna, cuando la expresion regular no es correcta muestra un texto con un ejemplo de
     * como deve ser la expresion regular
     * @param tipo String tipo de dato
     * @return String mensaje personalizado
     */
    private String validFormat(String tipo){
        switch (tipo) {
            case "id", "parkingId", "locationId", "memebersId", "currentCapacity", "maxCapacity" -> format = "numero, ejemplo 123";
            case "costHour" -> format = "fraccion, ejemplo 123.45";
            case "email" -> format = "email, ejemplo algo.hola@gma.co";
            case "licensePlate" -> format = "cadena de 6 caracteres, ejemplo 123ABC";
            case "phone" -> format = "numero, ejemplo 123456789";
            case "make", "model" -> format = "letras, ejemplo twingo";
            case "type" -> format = "vehiculo (carro, moto)";
            case "parkingName", "state", "country", "ubication", "city" -> format = "letras, ejemplo San Cristobal";
            case "firstName", "lastName" -> format = "letras, ejemplo Chavez";
            case "date" -> format = "fecha, ejemplo 01-01-2000";
            case "document" -> format = "documento, ejemplo CC-123456789";
            case "year" -> format = "numero de 4 digitos, ejemplo 2000";
            case "partiaLicensePlate" -> format = "cadena de 1 a 6 caracteres, ejemplo 12AB";
            default -> format = null;
        }
        return "tiene que ser de tipo "+format;
    }
}
