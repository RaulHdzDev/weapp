package com.example.inicio_3.Formulas

class formulas {

    private val PI : Double = 3.1416

    fun volumenCisterna(radio:Double, altura:Double):Double{
        return PI * radio * radio * altura
    }

    fun volumenContenedor(largo:Double, ancho:Double, alto:Double):Double{
        return largo * ancho * alto
    }

    fun porcentaje(total:Double, volumen:Double):Double{
        return 100 * volumen / total
    }

    fun litros(volumen:Double, capacidad:Double):Double{
        return capacidad - volumen * 1000
    }

}