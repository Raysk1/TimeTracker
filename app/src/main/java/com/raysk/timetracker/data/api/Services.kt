package com.raysk.timetracker.data.api

import com.raysk.timetracker.data.Horario
import com.raysk.timetracker.data.Materia
import com.raysk.timetracker.data.Usuario
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.*


class Services {

    private val apiService = RetrofitClient.getInstance().getApi()

    /**
     * Obtiene una [List] de [Usuario]s desde el API
     * @return [List] de [Usuario]
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun getUsuarios(): List<Usuario>? {
        val response = apiService.getUsuarios().execute()


        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }

    }

    /**
     * Obtiene el usuario indicado desde el API
     * @param [username] El [Usuario] a buscar
     * @return El [Usuario] encontrado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun getUsuario(username: String): Usuario? {
        val response = apiService.getUsuario(username).execute()

        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Elimina a el usuario indicado desde el API
     * @param [username] El [Usuario] a eliminar
     * @return Si el usuario se elimino correctamente
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun deleteUsuario(username: String): Boolean {
        val response = apiService.deleteUsuario(username).execute()
        if (response.isSuccessful) {
            return true
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Crea un [Usuario] desde el API
     * @param username El nombre de [Usuario]
     * @param password La contraseña del [Usuario]
     * @param nombre Nombre del [Usuario]
     * @param apellidos Apellidos del [Usuario]
     * @return El [Usuario] creado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun createUsuario(
        username: String,
        password: String,
        nombre: String,
        apellidos: String,
    ): Usuario? {
        val user = Usuario(username, password, Usuario.USER, nombre, apellidos)
        val response = apiService.createUsuario(user).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Actualiza un [Usuario] desde el API
     * @param username El nombre de [Usuario] a actualizar
     * @param password Nueva contraseña del [Usuario]
     * @param nombre Nuevo nombre del [Usuario]
     * @param apellidos Nuevos apellidos del [Usuario]
     * @return El [Usuario] actualizado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun updateUsuario(
        username: String,
        password: String,
        nombre: String,
        apellidos: String,
    ): Usuario? {
        val user = Usuario(username, password, Usuario.USER, nombre, apellidos)
        val response = apiService.updateUsuario(user).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Actualiza un [Usuario] desde el API.
     *
     * El [Usuario.username] sera tomado como [Usuario] a actualizar por el API
     * @param user [Usuario] a actualizar
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun updateUsuario(user: Usuario): Usuario? {
        val response = apiService.updateUsuario(user).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Crea un [Usuario] desde el API
     * @param user El [Usuario] a crear
     * @return El [Usuario] creado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun createUsuario(user: Usuario): Usuario? {
        val response = apiService.createUsuario(user).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Obtiene una [List] de [Horario]s desde el API
     * @return [List] de [Horario]
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun getHorarios(): List<Horario>? {
        val response = apiService.getHorarios().execute()


        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }

    }

    /**
     * Obtine el [Horario] indicado desde el API
     * @param id El id del [Horario] a buscar
     * @return El [Horario] encontrado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun getHorario(id: Int): Horario? {
        val response = apiService.getHorario(id).execute()
        val body = response.body()
        if (response.isSuccessful) {
            return body
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Elimina el [Horario] indicado desde el API
     * @param [id] El id del [Horario] a eliminar
     * @return Si el [Horario] se elimino correctamente
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun deleteHorario(id: Int): Boolean {
        val response = apiService.deleteHorario(id).execute()
        if (response.isSuccessful) {
            return true
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Crea un [Horario] desde el API
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @param idUsuario Nombre de usuario que creo este [Horario]
     * @param idMateria ID de la materia a la que pertenece
     * @return El [Horario] creado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun createHorario(
        fechaInicio: Date,
        fechaFin: Date,
        idUsuario: String,
        idMateria: Int,
    ): Horario? {
        val horario = Horario(fechaFin, fechaInicio, 0, idUsuario, idMateria)
        val response = apiService.createHorario(horario).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Crea un [Horario] desde el API
     * @param horario [Horario] a crear
     * @return El [Horario] creado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun createHorario(horario: Horario): Horario? {
        val response = apiService.createHorario(horario).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Actualiza un [Horario] desde el API
     * @param id ID del [Horario] a actualizar
     * @param fechaInicio Nueva fecha de inicio
     * @param fechaFin Nueva Fecha de fin
     * @param idUsuario Nuevo nombre de usuario que creo este [Horario]
     * @param idMateria Nuevo ID de la materia a la que pertenece
     * @return El [Horario] actualizado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun updateHorario(
        id: Int,
        fechaInicio: Date,
        fechaFin: Date,
        idUsuario: String,
        idMateria: Int,
    ): Horario? {
        val horario = Horario(fechaFin, fechaInicio, id, idUsuario, idMateria)
        val response = apiService.updateHorario(horario).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Actualiza un [Horario] desde el API
     * @param horario [Horario] a actualizar
     * @return El [Horario] actualizado
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun updateHorario(horario: Horario): Horario? {
        val response = apiService.updateHorario(horario).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Obtiene una [List] de [Materia]s desde el API
     * @return Una[List] de [Materia]
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun getMaterias(): List<Materia>? {
        val response = apiService.getMaterias().execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Obtiene la [Materia] indicada desde el API
     * @param id El id de la [Materia] a buscar
     * @return La [Materia] encontrada
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun getMateria(id: Int): Materia? {
        val response = apiService.getMateria(id).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Elimina la [Materia] indicada desde el API
     * @param id El id de la [Materia] a eliminar
     * @return Retorna true si se elimino correctamente
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun deleteMateria(id: Int): Boolean {
        val response = apiService.deleteMateria(id).execute()
        if (response.isSuccessful) {
            return true
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Crea una [Materia] desde el API
     * @param nombre El nombre de la materia
     * @param idCarrera El id de la carrera
     * @param grupo El grupo al que pertenece la materia
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun createMateria(
        nombre: String,
        idCarrera: Int,
        grupo: Int,
    ): Materia? {
        val materia = Materia(0, nombre, idCarrera, grupo)
        val response = apiService.createMateria(materia).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Crea una [Materia] desde el API
     * @param materia [Materia] a crear
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun createMateria(materia: Materia): Materia? {
        val response = apiService.createMateria(materia).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Actualiza una [Materia] desde el API
     * @param id El id de la [Materia] a actualizar
     * @param nombre Nuevo nombre de la materia
     * @param idCarrera Nuevo id de la carrera
     * @param grupo Nuevo grupo al que pertenece la materia
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun updateMateria(
        id: Int,
        nombre: String,
        idCarrera: Int,
        grupo: Int,
    ): Materia? {
        val materia = Materia(id, nombre, idCarrera, grupo)
        val response = apiService.updateMateria(materia).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    /**
     * Actualiza una [Materia] desde el API
     * @param materia La [Materia] a actualizar
     * @throws [HttpException] Cuando la respuesta del servidor no es valida
     * @throws [SocketTimeoutException] Cuando se agota el tiempo de espera
     */
    @Throws(HttpException::class, SocketTimeoutException::class)
    fun updateMateria(materia: Materia): Materia? {
        val response = apiService.updateMateria(materia).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

}