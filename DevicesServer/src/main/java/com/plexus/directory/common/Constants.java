package com.plexus.directory.common;

public class Constants {
    public static final String INVALID_REQUEST = "Your request was invalid: ";
    public static final String DB_COMMUNICATION_ERROR = "There was an error during database commmunication: ";

    public static final String DBURL = "jdbc:sqlite:fpdb.db";
    public static final String INVISIBLE = "No se va a ver esto nunca, antes salta exception";
    public static final String ERROR_A_MEDIAS_DELETE_DEVICE = "Error a medias, la lista de ids a borrar es mas grande de la lista de ids borrados, por lo que puede que hayas mandado ids que no existen.";
    public static final String UPDATING_ERROR = "UpdatingError";
    public static final String EMPTY_VALIDATED_DEVICES_LIST = "La lista validada de devices a actualizar esta vacia";

    private Constants(){}
}