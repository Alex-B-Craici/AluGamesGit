package br.com.alura.alugames.modelo

import br.com.alura.alugames.utilitario.formatoComDuasCasasDecimais
import com.google.gson.annotations.Expose
import java.math.BigDecimal

data class Jogo(@Expose val titulo:String,
                @Expose val capa:String): Recomendavel {

    var descricao:String? = null
    var preco = 0.0
    private val listaNotas = mutableListOf<Int>()

    constructor(titulo: String, capa: String, preco: Double, descricao: String): this(titulo, capa) {
        this.preco = preco
        this.descricao = descricao
    }

    override val media: Double
        get() = listaNotas.average().formatoComDuasCasasDecimais()

    override fun recomendar(nota: Int) {
        listaNotas.add(nota)
        if (nota < 1 || nota > 10) {
            println("Nota inválida. Insira uma nota entre 1 e 10")
        } else {
            listaNotas.add(nota)
        }
    }

    override fun toString(): String {
        return "\nMeu Jogo" +
                "\nTítulo: $titulo" +
                "\nCapa: $capa" +
                "\nDescricao: $descricao" +
                "\nReputação: $media" +
                "\nPreço: $preco"
    }

}