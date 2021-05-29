package app.utic.appventas;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ParametrosGlobales {
    public static Integer idUsuario;

    public static String completarFecha(String formato){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getTimeZone("America/Asuncion"));
        return sdf.format(date);
    }
}

