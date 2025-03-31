package com.plexus.directory.dao;

import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.error.NoContentException;
import com.plexus.directory.domain.error.StatusException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;

@Repository
@Profile("versionBase")
public abstract class GenericRepository {
    protected <T> T safeApiCall(Call<T> call) throws StatusException, DataBaseException, BadRequestException {
        try { Response<T> response = call.execute();

            if (!response.isSuccessful()) {
                String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error body vacío";

                handleErrorResponse(response.code(), errorBody);
            }

            if (response.body() == null) {
                throw new NoContentException("La respuesta de la API fue nula");
            }

            return response.body();

        } catch (IOException e) {
            throw new StatusException(Map.of("Error en la comunicación con la API", e.getMessage() != null ? e.getMessage() : "Sin mensaje de error"));
        }
    }

    private void handleErrorResponse(int statusCode, String errorBody) throws DataBaseException, BadRequestException, StatusException {
        switch (statusCode) {
            case 400: // BAD REQUEST
                throw new BadRequestException(errorBody);

            case 207: // MULTI STATUS
                throw new StatusException(Map.of("status", "casi bien", "message", errorBody, "details", "Respuesta con múltiples estados"));

            case 500: // INTERNAL SERVER ERROR
                throw new DataBaseException(errorBody);

            default:
                throw new DataBaseException("Error no manejado. Código: " + statusCode + ", Mensaje: " + errorBody);
        }
    }
}