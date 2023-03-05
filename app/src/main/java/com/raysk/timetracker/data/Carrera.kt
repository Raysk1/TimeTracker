package com.raysk.timetracker.data

class Carrera {
    companion object {
        /**
         * Ingeniria en Sistemas Computacionales
         */
        val ISC = 1

        /**
         * Ingenieria en Idustrias Alimentarias
         */
        val IIAL = 2

        /**
         * Ingenieria Industrial
         */
        val INN = 3

        /**
         * Ingenieria en Gestion Empresarial
         */
        val IGE = 4

        /**
         * Ingenieria en Innvocion Agricola Sustentable
         */
        val IIAS = 5

        fun getCarreraLargeName(carrera:Int): String?{
            return when(carrera){
                ISC -> "Ingeniria en Sistemas Computacionales"
                IIAL -> "Ingenieria en Idustrias Alimentarias"
                INN -> "Ingenieria Industrial"
                IGE -> "Ingenieria en Gestion Empresarial"
                IIAS -> "Ingenieria en Innvocion Agricola Sustentable"
                else -> null
            }
        }
        fun getCarreraShortName(carrera:Int): String?{
            return when(carrera){
                ISC -> "ISC"
                IIAL -> "IIAL"
                INN -> "INN"
                IGE -> "IGE"
                IIAS -> "IIAS"
                else -> null
            }
        }
    }
}