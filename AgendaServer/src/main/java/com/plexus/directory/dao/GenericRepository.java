package com.plexus.directory.dao;

import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.error.StatusException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public abstract class GenericRepository {
    /**
     * Ejecuta una llamada API de forma segura y maneja los errores adecuadamente
     *
     * @param call La llamada Retrofit a ejecutar
     * @param <T> Tipo del objeto de respuesta esperado
     * @return El cuerpo de la respuesta si es exitosa
     * @throws DataBaseException Si la respuesta no es exitosa
     * @throws StatusException Si ocurre un error durante la ejecución
     */
    public <T> T safeApiCall(Call<T> call) {
        try {
            Response<T> response = call.execute();

            if (!response.isSuccessful()) {
                String errorBody = response.errorBody() != null ?
                        response.errorBody().string() : "Error body vacío";
                throw new DataBaseException(
                        "Error en la API. Código: " + response.code() +
                                ", Mensaje: " + errorBody
                );
            }

            if (response.body() == null) {
                throw new DataBaseException("La respuesta de la API fue nula");
            }

            return response.body();

        } catch (IOException e) {
            throw new StatusException(
                    Map.of("Error en la comunicación con la API",
                            e.getMessage()
                                    != null ? e.getMessage() : "Sin mensaje de error")
            );
        }
    }

    /**
     * Versión sobrecargada para cuando solo necesitas verificar el éxito sin el cuerpo
     */
    public void safeApiCallWithoutResponse(Call<Void> call) {
        try {
            Response<Void> response = call.execute();

            if (!response.isSuccessful()) {
                String errorBody = response.errorBody() != null ?
                        response.errorBody().string() : "Error body vacío";
                throw new DataBaseException(
                        "Error en la API. Código: " + response.code() +
                                ", Mensaje: " + errorBody
                );
            }

        } catch (IOException e) {
            throw new StatusException(
                    Collections.singletonMap(
                            "Error en la comunicación con la API",
                            e.getMessage() != null ? e.getMessage() : "Sin mensaje de error"
                    )
            );
        }
    }
}