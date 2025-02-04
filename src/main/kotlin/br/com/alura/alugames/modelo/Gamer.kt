package br.com.alura.alugames.modelo

import br.com.alura.alugames.utilitario.formatoComDuasCasasDecimais
import main
import java.time.LocalDate
import java.util.Scanner
import kotlin.random.Random

data class Gamer(var nome:String, var email:String): Recomendavel {
    var dataNascimento:String? = null
    var usuario:String? = null
        set(value) {
            field = value
            if (idInterno.isNullOrBlank()) {
                criarIdInterno()
            }
        }
    var idInterno:String? = null
        private set
    var plano: Plano = PlanoAvuso("BRONZE")
    val jogosBuscados = mutableListOf<Jogo?>()
    val jogosAlugados = mutableListOf<Aluguel>()
    private val listaNotas: MutableList<Int> = mutableListOf<Int>()
    val jogosRecomendados: MutableList<Jogo> = mutableListOf<Jogo>()

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

    fun recomendarJogo(jogo: Jogo, nota: Int) {
        jogo.recomendar(nota)
        jogosRecomendados.add(jogo)
    }

    constructor(nome: String, email: String, dataNascimento:String, usuario:String):
            this(nome, email) {
                this.dataNascimento = dataNascimento
                this.usuario = usuario
                criarIdInterno()
            }

    /*init {
        if (nome.isNullOrBlank()) {
            throw IllegalArgumentException("Nome está em branco")
        }
        this.email = validarEmail()
    }*/

    override fun toString(): String {
        return "Gamer" +
                "\nNome: $nome" +
                "\nEmail: $email" +
                "\nDataNascimento: $dataNascimento" +
                "\nUsuário: $usuario" +
                "\nId Interno: $idInterno" +
                "\nReputação: $media"
    }

    fun criarIdInterno() {
        val numero = Random.nextInt(10000)
        val tag = String.format("%04d", numero)

        idInterno = "$usuario#$tag"
    }

    fun validarEmail(): String {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}")
        if (regex.matches(email)) {
            return email
        } else {
            throw IllegalArgumentException("Email inválido")
        }
    }

    fun alugaJogo(jogo: Jogo, periodo: Periodo): Aluguel {
        val aluguel = Aluguel(this, jogo, periodo)
        jogosAlugados.add(aluguel)

        return aluguel
    }

    fun jogosDoMes(mes:Int): List<Jogo> {
        return jogosAlugados.filter { aluguel -> aluguel.periodo.dataInicial.monthValue == mes }.map { aluguel -> aluguel.jogo }
    }

    companion object {
        fun criarGame(leitura: Scanner): Gamer {
            println("Boas vindas ao AluGames!\nVamos fazer seu cadastro.\nDigite seu nome:")
            val nome = leitura.nextLine()
            println("Digite seu e-mail:")
            val email = leitura.nextLine()
            println("Deseja completar seu cadastro com usuário e data de nascimento? (S/N)")
            val opcao = leitura.nextLine()

            if (opcao.equals("s", ignoreCase = true)) {
                println("Digite sua data de nascimento(DD/MM/AAAA):")
                val nascimento = leitura.nextLine()
                println("Digite seu nome de usuário:")
                val usuario = leitura.nextLine()

                return Gamer(nome, email, nascimento, usuario)
            } else {
                return Gamer(nome, email)
            }
        }
    }
}